package com.kh.carlpion.file.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	
	String saveFile(MultipartFile file);
	
	boolean deleteFile(String fileName);
	
	String getSignedUrl(String fileName);
}
