package com.kh.carlpion.report.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.report.model.dto.ReportDTO;
import com.kh.carlpion.report.model.vo.ReportVO;

public interface ReportService {
	
	void save(ReportDTO reportDTO, MultipartFile file);
	
	List<ReportDTO> findAll(int pageNo);
	
	ReportDTO findById(Long reportNo);
	
	ReportDTO updateById(ReportDTO reportDTO, MultipartFile file);
	
	void deleteById(Long reportNo);
}
