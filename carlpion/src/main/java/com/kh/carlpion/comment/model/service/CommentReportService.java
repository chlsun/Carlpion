package com.kh.carlpion.comment.model.service;

import java.util.List;

import com.kh.carlpion.comment.model.dto.CommentReportDTO;

public interface CommentReportService {
	
	void saveReport(CommentReportDTO commentReportDTO);
	
	List<CommentReportDTO> findAllReport(Long reportNo);
	
	void deleteReportById(Long commentNo);
}
