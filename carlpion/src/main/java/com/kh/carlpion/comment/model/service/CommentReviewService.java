package com.kh.carlpion.comment.model.service;

import java.util.List;

import com.kh.carlpion.comment.model.dto.CommentReviewDTO;

public interface CommentReviewService {
	
	void saveComment(CommentReviewDTO commentReviewDTO);
	
	List<CommentReviewDTO> findAllComment(Long reviewNo);
	
	void softDeleteCommentById(Long commentNo);
}
