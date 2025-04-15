package com.kh.carlpion.comment.model.service;

import com.kh.carlpion.comment.model.dto.CommentNtDTO;
import com.kh.carlpion.comment.model.dto.CommentRpDTO;
import com.kh.carlpion.comment.model.dto.CommentRvDTO;

public interface CommentService {
	void saveNotice(CommentNtDTO ntDTO);
	void saveReport(CommentRpDTO rpDTO);
	void saveReview(CommentRvDTO rvDTO);
	
	void deleteNoticeById(Long noticeNo);
	void deleteReportById(Long reportNo);
	void deleteReviewById(Long reviewNo);
}
