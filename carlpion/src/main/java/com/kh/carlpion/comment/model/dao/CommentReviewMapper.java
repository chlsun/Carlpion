package com.kh.carlpion.comment.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.carlpion.comment.model.dto.CommentReviewDTO;
import com.kh.carlpion.comment.model.vo.CommentReviewVO;

@Mapper
public interface CommentReviewMapper {

	void saveComment(CommentReviewVO commentReviewVO);
	
	List<CommentReviewDTO> findAllComment(Long reviewNo); 
	
	void softDeleteCommentById(Long commentNo);
	
	Long findByUserNo(Long commentNo);
}
