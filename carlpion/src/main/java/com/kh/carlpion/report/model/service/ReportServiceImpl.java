package com.kh.carlpion.report.model.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.auth.model.service.AuthService;
import com.kh.carlpion.exception.exceptions.NotFindException;
import com.kh.carlpion.exception.exceptions.UnauthorizedException;
import com.kh.carlpion.file.service.FileService;
import com.kh.carlpion.report.model.dao.ReportMapper;
import com.kh.carlpion.report.model.dto.ReportDTO;
import com.kh.carlpion.report.model.vo.ReportVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {
	
	private final ReportMapper reportMapper;	
	private final AuthService authService;
	private final FileService fileService;

	@Override
	@Transactional
	public void save(ReportDTO reportDTO, List<MultipartFile> files) {		
		/*사용자 인증 구간*/
		Long userNo = authService.getUserDetails().getUserNo();
		
		if( !userNo.equals(reportDTO.getUserNo())) {
			throw new UnauthorizedException("사용자 정보가 일치하지 않습니다.");
		}
		
		ReportVO requestData = ReportVO.builder()
									   .userNo(userNo)
									   .title(reportDTO.getTitle())
									   .content(reportDTO.getContent())
									   .build();
		reportMapper.save(requestData);
		
		if(files != null && !files.isEmpty()) {
			for(MultipartFile file : files) {
				
				if( !file.isEmpty()) {					
					String filePath = fileService.storage(file);
					
					ReportVO requestFileData = ReportVO.builder()
													   .reportNo(requestData.getReportNo())
													   .fileUrl(filePath)
													   .build();
					reportMapper.saveFile(requestFileData);
				}
			}
		}
//		log.info("save: {}", requestData);
	}

	@Override
	public List<ReportDTO> findAll(int pageNo) {
		int pageSize = 10;
		RowBounds rowBounds = new RowBounds(pageNo * pageSize, pageSize);
		return reportMapper.findAll(rowBounds);
	}

	@Override
	public ReportDTO findById(Long reportNo) {
		ReportDTO reportDTO = reportMapper.findById(reportNo);
		
		if(reportDTO == null) {
			throw new NotFindException("해당 글을 찾을 수 없습니다.");
		}
		
		reportMapper.updateCount(reportNo);
		return reportDTO;
	}

	@Override
	@Transactional
	public ReportDTO updateById(ReportDTO reportDTO, List<MultipartFile> files) {
		Long reportNo = reportDTO.getReportNo();
		checkedOwnerByUser(reportNo);
		
		if(files != null && !files.isEmpty() && files.stream().anyMatch(file -> !file.isEmpty())) {
			List<String> fileUrls = reportMapper.findFileByAll(reportNo);
			
			if(fileUrls != null) {
				for(String fileUrl : fileUrls) {
					fileService.deleteFile(fileUrl);
				}
			}
			reportMapper.deleteFileById(reportNo);
			
			for(MultipartFile file : files) {
				
				if( !file.isEmpty()) {
					String filePath = fileService.storage(file);					
					
					ReportVO requestFileData = ReportVO.builder()
													   .reportNo(reportNo)
													   .fileUrl(filePath)
													   .build();
					reportMapper.saveFile(requestFileData);
				}
			}			
		}
		reportMapper.updateById(reportDTO);
		return reportDTO;
	}

	@Override
	@Transactional
	public void softDeleteById(Long reportNo) {
		checkedOwnerByUser(reportNo);
		reportMapper.softDeleteById(reportNo);
	}
	
	/** 사용자 인증 */
	private void checkedOwnerByUser(Long reportNo) {
		Long authUserNo = authService.getUserDetails().getUserNo();
		Long findUserNo = reportMapper.findByUserNo(reportNo);
		
		if(findUserNo == null || !authUserNo.equals(findUserNo)) {
			throw new UnauthorizedException("수정/삭제할 권한이 없습니다.");
		}
	}
}
