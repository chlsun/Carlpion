package com.kh.carlpion.comment.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.carlpion.comment.model.dao.CommentReviewMapper;
import com.kh.carlpion.comment.model.dto.CommentReviewDTO;
import com.kh.carlpion.comment.model.vo.CommentReviewVO;
import com.kh.carlpion.review.model.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentReviewServiceImpl implements CommentReviewService {
	
	private final CommentReviewMapper commentReviewMapper;
	private final ReviewService reviewService;

	@Override
	public void saveReview(CommentReviewDTO commentReviewDTO) {
		
		CommentReviewVO requestData = CommentReviewVO.builder()
													 .userNo(commentReviewDTO.getUserNo())
													 .content(commentReviewDTO.getContent())
													 .reviewNo(commentReviewDTO.getReviewNo())
													 .build();
		commentReviewMapper.saveReview(requestData);
	}

	@Override
	public List<CommentReviewDTO> findAllReview(Long reviewNo) {
		reviewService.findById(reviewNo);
		return commentReviewMapper.findAllReview(reviewNo);
	}

	@Override
	public void deleteReviewById(Long commentNo) {
		commentReviewMapper.deleteReviewById(commentNo);
	}

}
