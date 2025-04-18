package com.kh.carlpion.comment.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.carlpion.comment.model.dao.CommentReportMapper;
import com.kh.carlpion.comment.model.dto.CommentReportDTO;
import com.kh.carlpion.comment.model.vo.CommentReportVO;
import com.kh.carlpion.report.model.service.ReportService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentReportServiceImpl implements CommentReportService {

	private final CommentReportMapper commentReportMapper;
	private final ReportService reportService;
	
	@Override
	public void saveReport(CommentReportDTO commentReportDTO) {
		CommentReportVO requestData = CommentReportVO
										.builder()
										.userNo(null)
										.content(commentReportDTO.getContent())
										.reportNo(commentReportDTO.getReportNo())
										.build();
		commentReportMapper.saveReport(requestData);
	}

	@Override
	public List<CommentReportDTO> findAllReport(Long reportNo) {
		reportService.findById(reportNo);
		return commentReportMapper.findAllReport(reportNo);
	}

	@Override
	public void deleteReportById(Long commentNo) {
		commentReportMapper.deleteReportById(commentNo);
	}

}
