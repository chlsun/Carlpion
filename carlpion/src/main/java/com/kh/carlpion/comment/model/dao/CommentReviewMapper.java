package com.kh.carlpion.comment.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.carlpion.comment.model.dto.CommentReviewDTO;

@Mapper
public interface CommentReviewMapper {

	void saveReview(CommentReviewDTO commentReviewDTO);
	
	List<CommentReviewDTO> findAllReview(Long reviewNo); 
	
	void deleteReviewById(Long commentNo);
}
