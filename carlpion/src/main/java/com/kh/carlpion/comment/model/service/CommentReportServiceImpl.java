package com.kh.carlpion.comment.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.carlpion.auth.model.service.AuthService;
import com.kh.carlpion.comment.model.dao.CommentMapper;
import com.kh.carlpion.comment.model.dto.CommentDTO;
import com.kh.carlpion.comment.model.dto.CommentDynamicDTO;
import com.kh.carlpion.comment.model.vo.CommentVO;
import com.kh.carlpion.exception.exceptions.UnauthorizedException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentReportServiceImpl implements CommentReportService {

	private final CommentMapper commentMapper;
	private final AuthService authService;
	
	private static final String COMMENT_TYPE = "report";
	
	@Override
	@Transactional
	public void saveComment(CommentDynamicDTO commentDynamicDTO) {
		Long userNo = authService.getUserDetails().getUserNo();
		
		CommentVO requestData = CommentVO.builder()
	 									 .commentType(COMMENT_TYPE)
										 .userNo(userNo)
										 .content(commentDynamicDTO.getContent())
										 .reportNo(commentDynamicDTO.getReportNo())
										 .build();
		commentMapper.saveComment(requestData);
	}

	@Override
	public List<CommentDTO> findAllComment(Long reportNo) {
		CommentVO requestData = CommentVO.builder()
										 .commentType(COMMENT_TYPE)
										 .reviewNo(reportNo)
										 .build();	
		return commentMapper.findAllComment(requestData);
	}

	@Override
	@Transactional
	public void softDeleteCommentById(Long commentNo) {
		CommentVO requestData = checkedOwnerByUser(commentNo);
		commentMapper.softDeleteCommentById(requestData);
	}

	/** 사용자 인증 */
	private CommentVO checkedOwnerByUser(Long commentNo) {
		Long authUserNo = authService.getUserDetails().getUserNo();
		
		CommentVO requestData = CommentVO.builder()
										 .commentType(COMMENT_TYPE)
										 .commentNo(commentNo)
										 .build();
		Long findUserNo = commentMapper.findByUserNo(requestData);
				
		if(findUserNo == null || !authUserNo.equals(findUserNo)) {
			throw new UnauthorizedException("삭제할 권한이 없습니다.");
		}
		
		return requestData;
	}
}
