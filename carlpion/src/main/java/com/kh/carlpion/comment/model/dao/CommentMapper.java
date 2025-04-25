package com.kh.carlpion.comment.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.carlpion.comment.model.dto.CommentDTO;
import com.kh.carlpion.comment.model.vo.CommentVO;

@Mapper
public interface CommentMapper {

	void saveComment(CommentVO commentVO);
	
	List<CommentDTO> findAllComment(CommentVO commentVO);
	
	void softDeleteCommentById(CommentVO commentVO);
	
	Long findUserNoById(CommentVO commentVO);
}
