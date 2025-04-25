package com.kh.carlpion.comment.model.service;

import java.util.List;

import com.kh.carlpion.comment.model.dto.CommentDTO;
import com.kh.carlpion.comment.model.dto.CommentDynamicDTO;

public interface CommentNoticeService {
	
	void saveComment(CommentDynamicDTO commentDynamicDTO);
	
	List<CommentDTO> findAllComment(Long noticeNo);
	
	void softDeleteCommentById(Long commentNo);
}
