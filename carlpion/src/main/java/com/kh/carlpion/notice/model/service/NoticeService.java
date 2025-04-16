package com.kh.carlpion.notice.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.notice.model.dto.NoticeDTO;


public interface NoticeService {
	void save(NoticeDTO noticeDTO, MultipartFile file);
	
	List<NoticeDTO> findAll(int pageNo);
	
	NoticeDTO findById(Long noticeNo);
	
	NoticeDTO updateById(NoticeDTO noticeDTO, MultipartFile file);
	
	void deleteById(Long noticeNo);
}
