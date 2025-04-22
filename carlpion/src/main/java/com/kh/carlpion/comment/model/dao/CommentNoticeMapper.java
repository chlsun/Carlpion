package com.kh.carlpion.comment.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.carlpion.comment.model.dto.CommentNoticeDTO;
import com.kh.carlpion.comment.model.vo.CommentNoticeVO;

@Mapper
public interface CommentNoticeMapper {

	void saveNotice(CommentNoticeVO commentNoticeVO);
	
	List<CommentNoticeDTO> findAllNotice(Long noticeNo);
	
	void deleteNoticeById(Long commentNo);
}
