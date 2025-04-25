package com.kh.carlpion.comment.model.service;

import java.util.List;

import com.kh.carlpion.comment.model.dto.CommentDTO;
import com.kh.carlpion.comment.model.dto.CommentDynamicDTO;

public interface CommentReportService {
	
	void saveComment(CommentDynamicDTO commentDynamicDTO);
	
	List<CommentDTO> findAllComment(Long reportNo);
	
	void softDeleteCommentById(Long commentNo);
}
