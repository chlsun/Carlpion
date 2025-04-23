package com.kh.carlpion.notice.model.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.exception.exceptions.NotFindException;
import com.kh.carlpion.file.service.FileService;
import com.kh.carlpion.notice.model.dao.NoticeMapper;
import com.kh.carlpion.notice.model.dto.NoticeDTO;
import com.kh.carlpion.notice.model.vo.NoticeVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService {
	
	private final NoticeMapper noticeMapper;
	private final FileService fileService;

	@Override
	@Transactional
	public void save(NoticeDTO noticeDTO, List<MultipartFile> files) {
		/*사용자 인증 구간*/
		
		NoticeVO requestData = NoticeVO.builder()
									   .title(noticeDTO.getTitle())
									   .content(noticeDTO.getContent())
									   .userNo(noticeDTO.getUserNo())
									   .build();
		noticeMapper.save(requestData);
		
		if(files != null && !files.isEmpty()) {					
			for(MultipartFile file : files) {	
				
				if( !file.isEmpty()) {
					String filePath = fileService.storage(file);
					
					NoticeVO requestFileData = NoticeVO.builder()
													   .noticeNo(requestData.getNoticeNo())
													   .fileUrl(filePath)
													   .build();
					noticeMapper.saveFile(requestFileData);
//					log.info("saveFile: {}", requestFileData);
				}
			}
		}	
//		log.info("save: {}", requestData);
	}

	@Override
	public List<NoticeDTO> findAll(int pageNo) {
		int pageSize = 10;
		RowBounds rowBounds = new RowBounds(pageNo * pageSize, pageSize);
		return noticeMapper.findAll(rowBounds);
	}

	@Override
	public NoticeDTO findById(Long noticeNo) {
		NoticeDTO noticeDTO = noticeMapper.findById(noticeNo);
		
		if(noticeNo == null) {
			throw new NotFindException("Not Find Notice");
		}
		return noticeDTO;
	}

	@Override
	@Transactional
	public NoticeDTO updateById(NoticeDTO noticeDTO, List<MultipartFile> files) {
			
		if(files != null && !files.isEmpty() && files.stream().anyMatch(file -> !file.isEmpty())) {
			List<String> fileUrls = noticeMapper.findFileByAll(noticeDTO.getNoticeNo());
			
			if(fileUrls != null) {
				for(String fileUrl : fileUrls) {
					fileService.deleteFile(fileUrl);
				}
			}
			noticeMapper.deleteFileById(noticeDTO.getNoticeNo());
			
			for(MultipartFile file : files) {
				
				if( !file.isEmpty()) {
					String filePath = fileService.storage(file);
					noticeDTO.setFileUrl(filePath);		
					
					NoticeVO requestFileData = NoticeVO.builder()
													   .noticeNo(noticeDTO.getNoticeNo())
													   .fileUrl(filePath)
													   .build();
					noticeMapper.saveFile(requestFileData);
//					log.info("saveFile: {}", requestFileData);
				}
			}
		}
		noticeMapper.updateById(noticeDTO);
		return noticeDTO;
	}

	@Override
	@Transactional
	public void deleteById(Long noticeNo) {
		noticeMapper.deleteById(noticeNo);
	}
}
