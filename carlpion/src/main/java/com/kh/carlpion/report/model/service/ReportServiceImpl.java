package com.kh.carlpion.report.model.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
	public void save(ReportDTO reportDTO, MultipartFile file) {
		
		ReportVO requestData = null;
		
		if(file != null && !file.isEmpty()) {
			String filePath = fileService.storage(file);
			requestData = ReportVO
							.builder()
							.title(reportDTO.getTitle())
							.content(reportDTO.getContent())
							.userNo(reportDTO.getUserNo())
							.fileUrl(filePath)
							.build();
		} else {
			requestData = ReportVO
							.builder()
							.title(reportDTO.getTitle())
							.content(reportDTO.getContent())
							.userNo(reportDTO.getUserNo())
							.build();
		}
//		log.info("save: {}", requestData);
		reportMapper.save(requestData);
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
			throw new RuntimeException("Not Find Report");
		}
		return reportDTO;
	}

	@Override
	public ReportDTO updateById(ReportDTO reportDTO, MultipartFile file) {
		
		if(file != null && !file.isEmpty()) {
			String filePath = fileService.storage(file);
			reportDTO.setFileUrl(filePath);
		}
		reportMapper.updateById(reportDTO);
		return reportDTO;
	}

	@Override
	public void deleteById(Long reportNo) {
		reportMapper.deleteById(reportNo);
	}
}
