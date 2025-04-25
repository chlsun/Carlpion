package com.kh.carlpion.comment.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.carlpion.auth.model.service.AuthService;
import com.kh.carlpion.comment.model.dao.CommentReportMapper;
import com.kh.carlpion.comment.model.dto.CommentReportDTO;
import com.kh.carlpion.comment.model.vo.CommentReportVO;
import com.kh.carlpion.exception.exceptions.UnauthorizedException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentReportServiceImpl implements CommentReportService {

	private final CommentReportMapper commentReportMapper;
	private final AuthService authService;
	
	@Override
	@Transactional
	public void saveComment(CommentReportDTO commentReportDTO) {
		Long userNo = authService.getUserDetails().getUserNo();
		
		CommentReportVO requestData = CommentReportVO.builder()
													 .userNo(userNo)
													 .content(commentReportDTO.getContent())
													 .reportNo(commentReportDTO.getReportNo())
													 .build();
		commentReportMapper.saveComment(requestData);
	}

	@Override
	public List<CommentReportDTO> findAllComment(Long reportNo) {
		return commentReportMapper.findAllComment(reportNo);
	}

	@Override
	@Transactional
	public void softDeleteCommentById(Long commentNo) {
		checkedOwnerByUser(commentNo);
		commentReportMapper.softDeleteCommentById(commentNo);
	}

	/** 사용자 인증 */
	private void checkedOwnerByUser(Long commentNo) {
		Long authUserNo = authService.getUserDetails().getUserNo();
		Long findUserNo = commentReportMapper.findByUserNo(commentNo);
				
		if(findUserNo == null || !authUserNo.equals(findUserNo)) {
			throw new UnauthorizedException("삭제할 권한이 없습니다.");
		}
	}
}
