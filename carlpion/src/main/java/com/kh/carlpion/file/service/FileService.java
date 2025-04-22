package com.kh.carlpion.file.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.exception.exceptions.FileSaveException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileService {
	
	private final Path fileLocation;
	
	private static final String UPLOAD_URL = "http://localhost/uploads/";
	private static final List<String> FILE_EXTENSIONS = Arrays.asList(
		".jpg", ".jpeg", ".png", ".gif", ".svg", ".webp", ".pdf", 
        ".doc", ".docx", ".ppt", ".pptx", ".xls", ".xlsx", ".txt", ".hwp"
	);	/* 이미지 + 문서만 */
	
	
	public FileService() {
		this.fileLocation = Paths.get("uploads").toAbsolutePath().normalize();
		
		try {
			Files.createDirectories(this.fileLocation);	/* 저장할 곳이 없으면 생성 */
			
		} catch (IOException e) {
			throw new FileSaveException("디렉토리를 생성할 수 없습니다: " + e.getMessage());
		}
		
	}
	
	public String storage(MultipartFile file) {
		UUID uuid = UUID.randomUUID();	/* 32자 (-)포함 36자 */
		
		String uuidShort = Base64.getUrlEncoder()
								 .withoutPadding()
								 .encodeToString(uuid.toString().getBytes());	/* 22자 */
		
		String originalFileName = file.getOriginalFilename();	/* 기존 파일이름 */		
		String extension = "";
		
		/* 파일 확장자 검증 */
		if(originalFileName != null && originalFileName.contains(".")) {
			extension = originalFileName.substring(originalFileName.lastIndexOf(".")).toLowerCase();	
		}	

		if( !FILE_EXTENSIONS.contains(extension)) {
			throw new FileSaveException("지원하지 않는 파일 확장자 [ " + extension + " ]입니다.");
		}
		
		String newFileName = uuidShort + extension;
		
		Path tagetLocation = this.fileLocation.resolve(newFileName);
//		log.info("저장 경로 보기: {}",tagetLocation);
		
		try {
			Files.copy(file.getInputStream(), tagetLocation);
			return UPLOAD_URL + newFileName;
			
		} catch (IOException e) {
			throw new FileSaveException("파일을 저장할 수 없습니다: " + e.getMessage());
		}				
	}
	
	
}
