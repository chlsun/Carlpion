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
public class FileReportServiceImpl implements FileReportService {
	
	private final FileMapper fileMapper;
	private final FileService fileService;
	
	private static final String TYPE = "report";

	@Override
	@Transactional
	public void saveFile(FileDTO fileDTO) {
		FileVO fileVO = FileVO.builder()
							  .type(TYPE)
							  .reportNo(fileDTO.getReportNo())
							  .fileUrl(fileDTO.getFileUrl())
							  .build();
		fileMapper.saveFile(fileVO);
	}

	@Override
	@Transactional
	public void deleteFileById(Long reportNo) {
		FileVO fileVO = FileVO.builder()
							  .type(TYPE)
							  .reportNo(reportNo)
							  .build();
		fileMapper.deleteFileById(fileVO);
	}
	
	@Override
	public List<String> findFileByAll(Long reportNo) {
		FileVO fileVO = FileVO.builder()
							  .type(TYPE)
							  .reportNo(reportNo)
							  .build();
		return fileMapper.findFileByAll(fileVO);
	}
	
	
	public void saveFiles(MultipartFile file, Long reportNo) {
		if(file != null && !file.isEmpty()) {
			String filePath = fileService.storage(file);
			
			FileDTO requestFileData = new FileDTO();
			requestFileData.setReviewNo(reportNo);
			requestFileData.setFileUrl(filePath);
			saveFile(requestFileData);
		}
	}	
	
	public void deleteFiles(Long reportNo) {
	    List<String> fileUrls = findFileByAll(reportNo);
	    
	    if (fileUrls != null && !fileUrls.isEmpty()) {
	        for (String fileUrl : fileUrls) {
	            fileService.deleteFile(fileUrl);
	        }
	        deleteFileById(reportNo);
	    }
	}

}
