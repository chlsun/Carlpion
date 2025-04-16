package com.kh.carlpion.comment.model.service;

import java.util.List;

import com.kh.carlpion.comment.model.dto.CommentNoticeDTO;

public interface CommentNoticeService {
	
	void saveNotice(CommentNoticeDTO commentNoticeDTO);
	
	List<CommentNoticeDTO> findAllNotice(Long noticeNo);
	
	void deleteNoticeById(Long commentNo);
}
