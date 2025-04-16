package com.kh.carlpion.comment.model.service;

import java.util.List;

import com.kh.carlpion.comment.model.dto.CommentReportDTO;

public interface CommentReportService {
	void save(CommentReportDTO commentReportDTO);
	
	List<CommentReportDTO> findAll(Long reportNo);
	
	void deleteById(Long commentNo);
}
