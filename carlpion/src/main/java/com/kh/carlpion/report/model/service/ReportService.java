package com.kh.carlpion.report.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.report.model.dto.ReportDTO;

public interface ReportService {
	
	void save(ReportDTO reportDTO, List<MultipartFile> files);
	
	List<ReportDTO> findAll(int pageNo);
	
	ReportDTO findById(Long reportNo);
	
	ReportDTO updateById(ReportDTO reportDTO, List<MultipartFile> files);
	
	void deleteById(Long reportNo);
}
