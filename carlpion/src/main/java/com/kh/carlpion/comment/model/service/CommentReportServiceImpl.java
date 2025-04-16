package com.kh.carlpion.comment.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.carlpion.comment.model.dto.CommentReportDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentReportServiceImpl implements CommentReportService {

	@Override
	public void saveReport(CommentReportDTO commentReportDTO) {

	}

	@Override
	public List<CommentReportDTO> findAllReport(Long reportNo) {
		return null;
	}

	@Override
	public void deleteReportById(Long commentNo) {

	}

}
