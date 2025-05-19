package com.kh.carlpion.file.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.file.dao.FileMapper;
import com.kh.carlpion.file.dto.FileDTO;
import com.kh.carlpion.file.vo.FileVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileReviewServiceImpl implements FileReviewService {
	
	private final FileMapper fileMapper;
	private final FileService fileService;
	
	private static final String TYPE = "review";

	@Override
	@Transactional
	public void saveFile(FileDTO fileDTO) {
		FileVO fileVO = FileVO.builder()
							  .type(TYPE)
							  .reviewNo(fileDTO.getReviewNo())
							  .fileUrl(fileDTO.getFileUrl())
							  .build();
		fileMapper.saveFile(fileVO);		
	}

	@Override
	@Transactional
	public void deleteFileById(Long reviewNo) {
		FileVO fileVO = FileVO.builder()
							  .type(TYPE)
							  .reportNo(reviewNo)
							  .build();
		fileMapper.deleteFileById(fileVO);
	}
	
	@Override
	public List<String> findFileByAll(Long reviewNo) {
		FileVO fileVO = FileVO.builder()
							  .type(TYPE)
							  .reportNo(reviewNo)
							  .build();
		return fileMapper.findFileByAll(fileVO);
	}
	
	
	public void saveFiles(MultipartFile file, Long reviewNo) {
		if(file != null && !file.isEmpty()) {
			String filePath = fileService.storage(file);
			
			FileDTO requestFileData = new FileDTO();
			requestFileData.setReviewNo(reviewNo);
			requestFileData.setFileUrl(filePath);
			saveFile(requestFileData);
		}
	}	
	
	public void deleteFiles(Long reviewNo) {
	    List<String> fileUrls = findFileByAll(reviewNo);
	    
	    if (fileUrls != null && !fileUrls.isEmpty()) {
	        for (String fileUrl : fileUrls) {
	            fileService.deleteFile(fileUrl);
	        }
	        deleteFileById(reviewNo);
	    }
	}

}
