package com.kh.carlpion.file.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.file.dto.FileDTO;

public interface FileReviewService {
	
	void saveFile(FileDTO fileDTO);
	
	void deleteFileById(Long reviewNo);
	
	List<String> findFileByAll(Long reviewNo);

	void saveFiles(MultipartFile file, Long reviewNo);
	
	void deleteFiles(Long reviewNo);
}
