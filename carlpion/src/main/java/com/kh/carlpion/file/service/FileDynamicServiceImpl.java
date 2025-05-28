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
public class FileDynamicServiceImpl implements FileDynamicService {
	
	private final FileMapper fileMapper;
	private final FileService fileService;

	@Override
	public List<String> findFileByAll(Long boardNo, String type) {
		FileVO fileVO = FileVO.builder()
							  .type(type)
							  .boardNo(boardNo)
							  .build();
		return fileMapper.findFileByAll(fileVO);
	}	
	
	public void saveFiles(MultipartFile file, Long boardNo, String type) {
		if(file != null && !file.isEmpty()) {
			String filePath = fileService.storage(file);
			
			FileDTO fileDTO = new FileDTO();
			fileDTO.setType(type);
			fileDTO.setBoardNo(boardNo);
			fileDTO.setFileUrl(filePath);
			saveFile(fileDTO);
		}
	}	
	
	public void deleteFiles(Long boardNo, String type) {
	    List<String> fileUrls = findFileByAll(boardNo, type);
	    
	    if (fileUrls != null && !fileUrls.isEmpty()) {
	        for (String fileUrl : fileUrls) {
	            fileService.deleteFile(fileUrl);
	        }
	        deleteFileById(boardNo, type);
	    }
	}
	
	@Transactional
	private void saveFile(FileDTO fileDTO) {
		FileVO fileVO = FileVO.builder()
							  .type(fileDTO.getType())
							  .boardNo(fileDTO.getBoardNo())
							  .fileUrl(fileDTO.getFileUrl())
							  .build();
		fileMapper.saveFile(fileVO);
	}

	@Transactional
	public void deleteFileById(Long boardNo, String type) {
		FileVO fileVO = FileVO.builder()
							  .type(type)
							  .boardNo(boardNo)
							  .build();
		fileMapper.deleteFileById(fileVO);
	}

}
