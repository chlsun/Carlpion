package com.kh.carlpion.comment.model.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.carlpion.auth.model.service.AuthService;
import com.kh.carlpion.comment.model.dao.CommentMapper;
import com.kh.carlpion.comment.model.dto.CommentDTO;
import com.kh.carlpion.comment.model.dto.CommentDynamicDTO;
import com.kh.carlpion.comment.model.vo.CommentVO;
import com.kh.carlpion.exception.exceptions.UnauthorizedException;
import com.kh.carlpion.point.model.dto.PointHistoryDTO;
import com.kh.carlpion.point.model.service.PointService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentReviewServiceImpl implements CommentReviewService {

	private final CommentMapper commentMapper;
	private final AuthService authService;
	private final PointService pointService;
	
	private static final String COMMENT_TYPE = "review";

	@Override
	@Transactional
	public void saveComment(CommentDynamicDTO commentDynamicDTO) {
		Long userNo = authService.getUserDetails().getUserNo();
		
		CommentVO requestData = CommentVO.builder()
										 .commentType(COMMENT_TYPE)
										 .userNo(userNo)
										 .content(commentDynamicDTO.getContent())
										 .reviewNo(commentDynamicDTO.getReviewNo())
										 .build();
		commentMapper.saveComment(requestData);
		
		PointHistoryDTO pointHistoryDTO = new PointHistoryDTO();	
		pointHistoryDTO.setUserNo(userNo);
		pointHistoryDTO.setReason("리뷰 댓글 작성");
		pointHistoryDTO.setPointChange(5L);
		pointService.saveHistoryPoint(pointHistoryDTO);
	}

	@Override
	public List<CommentDTO> findAllComment(Long reviewNo) {
		CommentVO requestData = CommentVO.builder()
										 .commentType(COMMENT_TYPE)
										 .reviewNo(reviewNo)
										 .build();										
		return commentMapper.findAllComment(requestData);
	}

	@Override
	@Transactional
	public void softDeleteCommentById(Long commentNo) {
		Long userNo = authService.getUserDetails().getUserNo();
		CommentVO requestData = checkedOwnerByUser(userNo, commentNo);
		commentMapper.softDeleteCommentById(requestData);
		
		PointHistoryDTO pointHistoryDTO = new PointHistoryDTO();	
		pointHistoryDTO.setUserNo(userNo);
		pointHistoryDTO.setReason("리뷰 댓글 삭제");
		pointHistoryDTO.setPointChange(-5L);
		pointService.saveHistoryPoint(pointHistoryDTO);
	}

	/** 사용자 인증 */
	private CommentVO checkedOwnerByUser(Long userNo, Long commentNo) {
		CommentVO requestData = CommentVO.builder()
										 .commentType(COMMENT_TYPE)
										 .commentNo(commentNo)
										 .build();
		
		Long findUserNo = commentMapper.findUserNoById(requestData);
		boolean isAdmin = SecurityContextHolder.getContext().getAuthentication()
		        .getAuthorities().stream()
		        .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
		
		
		if (!isAdmin && (findUserNo == null || !userNo.equals(findUserNo))) {
			throw new UnauthorizedException("삭제할 권한이 없습니다.");
		}
		
		return requestData;
	}
}
