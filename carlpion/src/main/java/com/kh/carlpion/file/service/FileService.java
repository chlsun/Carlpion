package com.kh.carlpion.file.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileService {
	
	private final Path fileLocation;
	
	public FileService() {
		this.fileLocation = Paths.get("").toAbsolutePath().normalize();
	}
	
	public String storage(MultipartFile file) {
		StringBuilder sb = new StringBuilder();
//		sb.append("_");
		
		String currentTime = new SimpleDateFormat("yyyyMMdd_HHmmss_").format(new Date());
		sb.append(currentTime);
		
		int randomNo = (int)(Math.random() * 900) + 100;	/*000*/
		sb.append(randomNo);
		
		String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		sb.append(extension);
		
		Path tagetLocation = this.fileLocation.resolve(sb.toString());
//		log.info("저장경로: {}",tagetLocation);
		
		try {
			Files.copy(file.getInputStream(), tagetLocation, StandardCopyOption.REPLACE_EXISTING);
			return "http://localhost/" + sb.toString();
			
		} catch (IOException e) {
			throw new RuntimeException("파일을 저장할 수 없습니다.");
		}				
	}
}
