package com.kh.carlpion.comment.model.service;

import java.util.List;

import com.kh.carlpion.comment.model.dto.CommentReviewDTO;

public interface CommentReviewService {
	void save(CommentReviewDTO commentReviewDTO);
	
	List<CommentReviewDTO> findAll(Long reviewNo);
	
	void deleteById(Long commentNo);
}
