package com.kh.carlpion.notice.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.notice.model.dto.NoticeDTO;


public interface NoticeService {
	
	void save(NoticeDTO noticeDTO, List<MultipartFile> files);
	
	Map<String, Object> findAll(int pageNo);
	
	NoticeDTO findById(Long noticeNo);
	
	NoticeDTO updateById(NoticeDTO noticeDTO, List<MultipartFile> files);
	
	void softDeleteById(Long noticeNo);
}
