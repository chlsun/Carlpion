package com.kh.carlpion.comment.model.service;

import java.util.List;

import com.kh.carlpion.comment.model.dto.CommentReviewDTO;
import com.kh.carlpion.comment.model.vo.CommentReviewVO;

public interface CommentReviewService {
	
	void saveReview(CommentReviewDTO commentReviewDTO);
	
	List<CommentReviewDTO> findAllReview(Long reviewNo);
	
	void deleteReviewById(Long commentNo);
}
