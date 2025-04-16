package com.kh.carlpion.comment.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.carlpion.comment.model.dto.CommentReviewDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentReviewServiceImpl implements CommentReviewService {

	@Override
	public void saveReview(CommentReviewDTO commentReviewDTO) {

	}

	@Override
	public List<CommentReviewDTO> findAllReview(Long reviewNo) {
		return null;
	}

	@Override
	public void deleteReviewById(Long commentNo) {

	}

}
