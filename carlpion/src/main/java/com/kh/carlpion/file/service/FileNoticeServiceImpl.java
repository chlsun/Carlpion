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
public class FileNoticeServiceImpl implements FileNoticeService {
	
	private final FileMapper fileMapper;
	private final FileService fileService;
	
	private static final String TYPE = "notice";

	@Override
	@Transactional
	public void saveFile(FileDTO fileDTO) {
		FileVO fileVO = FileVO.builder()
							  .type(TYPE)
							  .noticeNo(fileDTO.getNoticeNo())
							  .fileUrl(fileDTO.getFileUrl())
							  .build();
		fileMapper.saveFile(fileVO);
	}

	@Override
	@Transactional
	public void deleteFileById(Long noticeNo) {
		FileVO fileVO = FileVO.builder()
							  .type(TYPE)
							  .noticeNo(noticeNo)
							  .build();
		fileMapper.deleteFileById(fileVO);
	}
	
	@Override
	public List<String> findFileByAll(Long noticeNo) {
		FileVO fileVO = FileVO.builder()
							  .type(TYPE)
							  .noticeNo(noticeNo)
							  .build();
		return fileMapper.findFileByAll(fileVO);
	}
	
	
	public void saveFiles(MultipartFile file, Long noticeNo) {
		if(file != null && !file.isEmpty()) {
			String filePath = fileService.storage(file);
			
			FileDTO requestFileData = new FileDTO();
			requestFileData.setReviewNo(noticeNo);
			requestFileData.setFileUrl(filePath);
			saveFile(requestFileData);
		}
	}	
	
	public void deleteFiles(Long noticeNo) {
	    List<String> fileUrls = findFileByAll(noticeNo);
	    
	    if (fileUrls != null && !fileUrls.isEmpty()) {
	        for (String fileUrl : fileUrls) {
	            fileService.deleteFile(fileUrl);
	        }
	        deleteFileById(noticeNo);
	    }
	}

}
