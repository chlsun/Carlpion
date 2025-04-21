package com.kh.carlpion.file.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.exception.exceptions.FileSaveException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileService {
	
	private final Path fileLocation;
	
	public FileService() {
		this.fileLocation = Paths.get("uploads").toAbsolutePath().normalize();
	}
	
	public String storage(MultipartFile file) {
		UUID uuid = UUID.randomUUID();	/* 32글자 */
		
		Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();		
		String uuidShort = encoder.encodeToString(uuid.toString().getBytes());	/* 22글자 */
		
		String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));	/* 파일 확장자*/
		String newFileName =  uuidShort + extension;
		
		Path tagetLocation = this.fileLocation.resolve(newFileName);
//		log.info("저장 경로 보기: {}",tagetLocation);
		
		try {
			Files.copy(file.getInputStream(), tagetLocation, StandardCopyOption.REPLACE_EXISTING);
			return "http://localhost/uploads/" + newFileName;
			
		} catch (IOException e) {
			throw new FileSaveException("파일을 저장할 수 없습니다.");
		}				
	}
}
