package com.kh.carlpion.file.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface FileDynamicService {
	
	List<String> findFileByAll(Long boardNo, String type);
	
	void saveFiles(MultipartFile file, Long boardNo, String type);
	
	void deleteFiles(Long boardNo, String type);
}
