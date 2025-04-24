package com.kh.carlpion.comment.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.carlpion.comment.model.dto.CommentReportDTO;
import com.kh.carlpion.comment.model.vo.CommentReportVO;

@Mapper
public interface CommentReportMapper {

	void saveReport(CommentReportVO commentReportVO);
	
	List<CommentReportDTO> findAllReport(Long reportNo);
	
	void deleteReportById(Long commentNo);
}
