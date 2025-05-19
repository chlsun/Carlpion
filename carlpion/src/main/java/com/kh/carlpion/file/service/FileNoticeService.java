package com.kh.carlpion.file.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.file.dto.FileDTO;

public interface FileNoticeService {
	
	void saveFile(FileDTO fileDTO);
	
	void deleteFileById(Long noticeNo);
	
	List<String> findFileByAll(Long noticeNo);
	
	void saveFiles(MultipartFile file, Long noticeNo);
	
	void deleteFiles(Long noticeNo);
}
