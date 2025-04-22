package com.kh.carlpion.report.model.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.exception.exceptions.NotFindException;
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
	private final FileService fileService;

	@Override
	@Transactional
	public void save(ReportDTO reportDTO, List<MultipartFile> files) {		
		/*사용자 인증 구간*/
		
		ReportVO requestData = ReportVO.builder()
									   .title(reportDTO.getTitle())
									   .content(reportDTO.getContent())
									   .userNo(reportDTO.getUserNo())
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
//					log.info("saveFile: {}", requestFileData);
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
		
		if(reportNo == null) {
			throw new NotFindException("Not Find Report");
		}
		return reportDTO;
	}

	@Override
	@Transactional
	public ReportDTO updateById(ReportDTO reportDTO, List<MultipartFile> files) {
		
		if(files != null && !files.isEmpty() && files.stream().anyMatch(file -> !file.isEmpty())) {
			List<String> fileUrls = reportMapper.findFileByAll(reportDTO.getReportNo());
			
			if(fileUrls != null) {
				for(String fileUrl : fileUrls) {
					fileService.deleteFile(fileUrl);
				}
			}
			reportMapper.deleteFileById(reportDTO.getReportNo());
			
			for(MultipartFile file : files) {
				
				if( !file.isEmpty()) {
					String filePath = fileService.storage(file);					
					reportDTO.setFileUrl(filePath);
					
					ReportVO requestFileData = ReportVO.builder()
													   .reportNo(reportDTO.getReportNo())
													   .fileUrl(filePath)
													   .build();
					reportMapper.saveFile(requestFileData);
//					log.info("saveFile: {}", requestFileData);
				}
			}			
		}
		reportMapper.updateById(reportDTO);
		return reportDTO;
	}

	@Override
	@Transactional
	public void deleteById(Long reportNo) {
		reportMapper.deleteById(reportNo);
	}
}
