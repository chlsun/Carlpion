package com.kh.carlpion.file.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.file.dto.FileDTO;

public interface FileReportService {
	
	void saveFile(FileDTO fileDTO);
	
	void deleteFileById(Long reportNo);
	
	List<String> findFileByAll(Long reportNo);
	
	void saveFiles(MultipartFile file, Long reportNo);
	
	void deleteFiles(Long reportNo);
}
