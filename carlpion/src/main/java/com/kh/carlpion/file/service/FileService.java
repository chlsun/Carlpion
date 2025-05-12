package com.kh.carlpion.file.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.exception.exceptions.CreateDirectoriesException;
import com.kh.carlpion.exception.exceptions.FileDeleteException;
import com.kh.carlpion.exception.exceptions.FileSaveException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileService {
	
	private static final Path BASE_LOCATION = Paths.get("uploads").toAbsolutePath().normalize();
	
	private static final List<String> FILE_EXTENSIONS = Arrays.asList(
		".jpg", ".jpeg", ".png", ".gif", ".svg", ".webp", ".pdf", 
        ".doc", ".docx", ".ppt", ".pptx", ".xls", ".xlsx", ".txt", ".hwp"
	);	/* 이미지 + 문서만 */
	
	public FileService() {
		
		try {
			Files.createDirectories(BASE_LOCATION);	/* 없으면 생성 */
			
		} catch (IOException e) {
			throw new CreateDirectoriesException("디렉토리를 생성할 수 없습니다. :" + e.getMessage());
		}
	}
	
	/** 파일 저장 */
	public String storage(MultipartFile file) {
		return storage(file, "");
	}

	public String storage(MultipartFile file, String subDirectory) {
		String originalFileName = file.getOriginalFilename();
		String newFileName = createdFileName(originalFileName);

		Path targetDirectory = BASE_LOCATION.resolve(subDirectory).normalize();

		try {
			Files.createDirectories(targetDirectory); 
			Path targetLocation = targetDirectory.resolve(newFileName);
			Files.copy(file.getInputStream(), targetLocation);
			return newFileName;

		} catch (IOException e) {
			throw new FileSaveException("파일 저장 실패: " + e.getMessage());
		}
	}
	
	/** UUID, Base64를 사용하고. 랜덤 파일명 생성 + 확장자 return */
	private String createdFileName(String originalFileName) {
		UUID uuid = UUID.randomUUID();	/* 32자 (-)포함 36자 */
		
		String uuidShort = Base64.getUrlEncoder()
								 .withoutPadding()
								 .encodeToString(uuid.toString().getBytes());	/* 22자 */	
		String extension = "";
		
		/* 파일 확장자 검증 */
		if(originalFileName != null && originalFileName.contains(".")) {
			extension = originalFileName.substring(originalFileName.lastIndexOf(".")).toLowerCase();	
		}	

		if( !FILE_EXTENSIONS.contains(extension)) {
			throw new FileSaveException("지원하지 않는 파일 확장자 [ " + extension + " ]입니다.");
		}
		
		return uuidShort + extension;
	}
	
	/** 파일 삭제 */
	public boolean deleteFile(String fileName) {
	    // 기본적으로 uploads 폴더에서 파일 삭제
	    return deleteFile("", fileName);
	}

	public boolean deleteFile(String subDirectory, String fileName) {
	    if (fileName == null || fileName.trim().isEmpty()) {
	        throw new FileDeleteException("삭제하려는 파일명이 유효하지 않습니다.");
	    }

	    Path targetDirectory = BASE_LOCATION.resolve(subDirectory).normalize();
	    Path filePath = targetDirectory.resolve(fileName).normalize();

	    // uploads 하위 경로 체크 (보안)
	    if (!filePath.startsWith(BASE_LOCATION)) {
	        throw new FileDeleteException("파일경로가 유효하지 않습니다.");
	    }

	    try {
	        return Files.deleteIfExists(filePath);
	    } catch (IOException e) {
	        throw new FileDeleteException(fileName + " 파일을 삭제할 수 없습니다: " + e.getMessage());
	    }
	}
	
	/** 원본 파일명만 return */
	public String memory(MultipartFile file) {
		return file.getOriginalFilename();
	}
}
