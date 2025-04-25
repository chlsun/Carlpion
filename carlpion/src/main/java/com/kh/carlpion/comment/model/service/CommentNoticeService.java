package com.kh.carlpion.comment.model.service;

import java.util.List;

import com.kh.carlpion.comment.model.dto.CommentNoticeDTO;

public interface CommentNoticeService {
	
	void saveComment(CommentNoticeDTO commentNoticeDTO);
	
	List<CommentNoticeDTO> findAllComment(Long noticeNo);
	
	void softDeleteCommentById(Long commentNo);
}
