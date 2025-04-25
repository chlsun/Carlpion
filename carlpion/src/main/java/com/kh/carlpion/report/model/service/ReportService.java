package com.kh.carlpion.report.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.report.model.dto.ReportDTO;

public interface ReportService {
	
	void save(ReportDTO reportDTO, List<MultipartFile> files);
	
	Map<String, Object> findAll(int pageNo);
	
	ReportDTO findById(Long reportNo);
	
	ReportDTO updateById(ReportDTO reportDTO, List<MultipartFile> files);
	
	void softDeleteById(Long reportNo);
}
