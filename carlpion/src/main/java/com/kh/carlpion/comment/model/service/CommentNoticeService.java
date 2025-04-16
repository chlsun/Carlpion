package com.kh.carlpion.comment.model.service;

import java.util.List;

import com.kh.carlpion.comment.model.dto.CommentNoticeDTO;

public interface CommentNoticeService {
	void save(CommentNoticeDTO commentNoticeDTO);
	
	List<CommentNoticeDTO> findAll(Long noticeNo);
	
	void deleteById(Long commentNo);
}
