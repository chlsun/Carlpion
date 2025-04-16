package com.kh.carlpion.comment.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.carlpion.comment.model.dto.CommentNoticeDTO;

@Mapper
public interface CommentNoticeMapper {

	void saveNotice(CommentNoticeDTO commentNoticeDTO);
	
	List<CommentNoticeDTO> findAllNotice(Long noticeNo);
	
	void deleteNoticeById(Long commentNo);
}
