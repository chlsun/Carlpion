package com.kh.carlpion.notice.model.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
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
	public void save(NoticeDTO noticeDTO, MultipartFile file) {
		
		
		NoticeVO requestData = NoticeVO.builder()
									   .title(noticeDTO.getTitle())
									   .content(noticeDTO.getContent())
									   .userNo(noticeDTO.getUserNo())
									   .build();
		noticeMapper.save(requestData);
		
		if(file != null && !file.isEmpty()) {			
			String filePath = fileService.storage(file);
			
			NoticeVO requestFileData = NoticeVO.builder()
											   .noticeNo(requestData.getNoticeNo())
											   .fileUrl(filePath)
											   .build();
			noticeMapper.saveFile(requestFileData);
			log.info("saveFile: {}", requestFileData);
		}	
		log.info("save: {}", requestData);
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
	public NoticeDTO updateById(NoticeDTO noticeDTO, MultipartFile file) {
			
		if(file != null && !file.isEmpty()) {
			String filePath = fileService.storage(file);
			noticeDTO.setFileUrl(filePath);			
		}
		
		noticeMapper.updateById(noticeDTO);
		return noticeDTO;
	}

	@Override
	public void deleteById(Long noticeNo) {
		noticeMapper.deleteById(noticeNo);
	}
}
