package com.kh.carlpion.report.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.auth.model.service.AuthService;
import com.kh.carlpion.exception.exceptions.NotFindException;
import com.kh.carlpion.exception.exceptions.UnauthorizedException;
import com.kh.carlpion.file.service.FileReportService;
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
	private final FileReportService fileReportService;

	@Override
	@Transactional
	public void save(ReportDTO reportDTO, List<MultipartFile> files) {		
		/*사용자 인증 구간*/
		Long userNo = authService.getUserDetails().getUserNo();
		
		ReportVO requestData = ReportVO.builder()
									   .userNo(userNo)
									   .title(reportDTO.getTitle())
									   .content(reportDTO.getContent())
									   .build();
		reportMapper.save(requestData);	
		Long reportNo = requestData.getReportNo();
		
		if(files != null && !files.isEmpty()) {
			for(MultipartFile file : files) {
				fileReportService.saveFiles(file, reportNo);
			}
		}
	}
	
	@Override
	public Map<String, Object> findAll(int pageNo) {	
		int pageLimit = 10;
		int btnLimit = 10;	
		int totalCount = reportMapper.findTotalCount(pageNo);
		int maxPage = (int)Math.ceil((double) totalCount / pageLimit);	
		int startBtn = (pageNo - 1) / btnLimit * btnLimit + 1;
		int endBtn = startBtn + btnLimit - 1;
		
		if(pageNo > maxPage && maxPage > 0) {
			pageNo = maxPage;
		}
		
		if(maxPage == 0) {
			pageNo = 1;
		}

		if(endBtn > maxPage) {
			endBtn = maxPage;
		}

		RowBounds rowBounds = new RowBounds((pageNo - 1) * pageLimit, pageLimit);		
		List<ReportDTO> list = reportMapper.findAll(rowBounds);	
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("totalCount", totalCount);
		map.put("pageNo", pageNo);
		map.put("pageLimit", pageLimit);
		map.put("btnLimit", btnLimit);
		map.put("maxPage", maxPage);
		map.put("startBtn", startBtn);
		map.put("endBtn", endBtn);
		return map;
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
		
		boolean containsFiles = files != null && files.stream().anyMatch(file -> file != null && !file.isEmpty());
		
		if(containsFiles) {				
			fileReportService.deleteFiles(reportNo);	
			
			for(MultipartFile file : files) {				
				fileReportService.saveFiles(file, reportNo);
			}			
		} else { 
			fileReportService.deleteFiles(reportNo);
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
		
		boolean isAdmin = SecurityContextHolder.getContext().getAuthentication()
		        .getAuthorities().stream()
		        .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
		
		
		if (!isAdmin && (findUserNo == null || !authUserNo.equals(findUserNo))) {
	        throw new UnauthorizedException("수정/삭제할 권한이 없습니다.");
	    }
	}	

}
