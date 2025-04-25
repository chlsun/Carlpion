package com.kh.carlpion.comment.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.carlpion.comment.model.dto.CommentReportDTO;
import com.kh.carlpion.comment.model.vo.CommentReportVO;

@Mapper
public interface CommentReportMapper {

	void saveComment(CommentReportVO commentReportVO);
	
	List<CommentReportDTO> findAllComment(Long reportNo);
	
	void softDeleteCommentById(Long commentNo);
	
	Long findByUserNo(Long commentNo);
}
