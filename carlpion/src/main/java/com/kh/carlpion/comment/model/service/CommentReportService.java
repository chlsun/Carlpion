package com.kh.carlpion.comment.model.service;

import java.util.List;

import com.kh.carlpion.comment.model.dto.CommentReportDTO;

public interface CommentReportService {
	
	void saveComment(CommentReportDTO commentReportDTO);
	
	List<CommentReportDTO> findAllComment(Long reportNo);
	
	void softDeleteCommentById(Long commentNo);
}
