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
import com.kh.carlpion.point.model.service.PointService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentDynamicServiceImpl implements CommentDynamicService {

	private final CommentMapper commentMapper;
	private final AuthService authService;
	private final PointService pointService;

	@Override
	@Transactional
	public void saveComment(CommentDynamicDTO commentDynamicDTO) {
		Long userNo = authService.getUserDetails().getUserNo();
		CommentVO requestData = CommentVO.builder()
										 .commentType(commentDynamicDTO.getCommentType())
										 .userNo(userNo)
										 .content(commentDynamicDTO.getContent())
										 .boardNo(commentDynamicDTO.getBoardNo())
										 .build();
		commentMapper.saveComment(requestData);
		
		if("review".equals(commentDynamicDTO.getCommentType())) {
			pointService.saveHistory(userNo, "리뷰 댓글 작성", 5L);
		}
	}

	@Override
	public List<CommentDTO> findAllComment(Long boardNo, String type) {
		CommentVO requestData = CommentVO.builder()
										 .commentType(type)
										 .boardNo(boardNo)
										 .build();										
		return commentMapper.findAllComment(requestData);
	}

	@Override
	@Transactional
	public void softDeleteCommentById(Long commentNo, String type) {
		Long userNo = authService.getUserDetails().getUserNo();
		CommentVO requestData = checkedOwnerByUser(userNo, commentNo, type);
		commentMapper.softDeleteCommentById(requestData);
		
		if("review".equals(type)) {
			pointService.saveHistory(userNo, "리뷰 댓글 삭제", -5L);
		}
	}

	/** 사용자 인증 */
	private CommentVO checkedOwnerByUser(Long userNo, Long commentNo, String type) {
		CommentVO requestData = CommentVO.builder()
										 .commentType(type)
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
