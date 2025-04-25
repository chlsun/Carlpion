package com.kh.carlpion.comment.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.carlpion.auth.model.service.AuthService;
import com.kh.carlpion.comment.model.dao.CommentReviewMapper;
import com.kh.carlpion.comment.model.dto.CommentReviewDTO;
import com.kh.carlpion.comment.model.vo.CommentReviewVO;
import com.kh.carlpion.exception.exceptions.NotFindException;
import com.kh.carlpion.exception.exceptions.UnauthorizedException;
import com.kh.carlpion.review.model.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentReviewServiceImpl implements CommentReviewService {

	private final CommentReviewMapper commentReviewMapper;
	private final AuthService authService;
	private final ReviewService reviewService;

	@Override
	@Transactional
	public void saveComment(CommentReviewDTO commentReviewDTO) {
		Long userNo = authService.getUserDetails().getUserNo();
		
		if( !userNo.equals(commentReviewDTO.getUserNo())) {
			throw new UnauthorizedException("사용자 정보가 일치하지 않습니다.");
		}
		
		CommentReviewVO requestData = CommentReviewVO.builder()
													 .userNo(userNo)
													 .content(commentReviewDTO.getContent())
													 .reviewNo(commentReviewDTO.getReviewNo())
													 .build();
		commentReviewMapper.saveComment(requestData);
	}

	@Override
	public List<CommentReviewDTO> findAllComment(Long reviewNo) {
		reviewService.findById(reviewNo);
		return commentReviewMapper.findAllComment(reviewNo);
	}

	@Override
	@Transactional
	public void softDeleteCommentById(Long commentNo) {
		checkedOwnerByUser(commentNo);
		commentReviewMapper.softDeleteCommentById(commentNo);
	}

	/** 사용자 인증 */
	private void checkedOwnerByUser(Long commentNo) {
		Long authUserNo = authService.getUserDetails().getUserNo();
		Long findUserNo = commentReviewMapper.findByUserNo(commentNo);
		
		if(findUserNo == null || !authUserNo.equals(findUserNo)) {
			throw new UnauthorizedException("삭제할 권한이 없습니다.");
		}
	}
}
