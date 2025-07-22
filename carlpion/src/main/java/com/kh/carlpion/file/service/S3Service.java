package com.kh.carlpion.file.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
	
	String upLoad(MultipartFile file);
	
	void deleteFile(String fileUrl);
}
