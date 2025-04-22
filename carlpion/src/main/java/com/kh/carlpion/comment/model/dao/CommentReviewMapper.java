package com.kh.carlpion.comment.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.carlpion.comment.model.dto.CommentReviewDTO;
import com.kh.carlpion.comment.model.vo.CommentReviewVO;

@Mapper
public interface CommentReviewMapper {

	void saveReview(CommentReviewVO commentReviewVO);
	
	List<CommentReviewDTO> findAllReview(Long reviewNo); 
	
	void deleteReviewById(Long commentNo);
}
