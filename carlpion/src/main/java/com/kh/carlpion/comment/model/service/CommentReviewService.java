package com.kh.carlpion.comment.model.service;

import java.util.List;

import com.kh.carlpion.comment.model.dto.CommentDTO;
import com.kh.carlpion.comment.model.dto.CommentDynamicDTO;

public interface CommentReviewService {
	
	void saveComment(CommentDynamicDTO commentDynamicDTO);
	
	List<CommentDTO> findAllComment(Long reviewNo);
	
	void softDeleteCommentById(Long commentNo);
}
