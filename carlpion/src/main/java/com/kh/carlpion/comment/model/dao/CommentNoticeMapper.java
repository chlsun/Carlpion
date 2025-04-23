package com.kh.carlpion.comment.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.carlpion.comment.model.dto.CommentNoticeDTO;
import com.kh.carlpion.comment.model.vo.CommentNoticeVO;

@Mapper
public interface CommentNoticeMapper {

	void saveComment(CommentNoticeVO commentNoticeVO);
	
	List<CommentNoticeDTO> findAllComment(Long noticeNo);
	
	void softDeleteCommentById(Long commentNo);
	
	Long findByUserNo(Long commentNo);
}
