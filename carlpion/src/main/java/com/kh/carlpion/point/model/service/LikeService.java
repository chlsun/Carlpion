package com.kh.carlpion.point.model.service;

import java.util.List;

import com.kh.carlpion.point.model.dto.LikeDTO;

public interface LikeService {

	void saveReviewLike(LikeDTO likeDTO);
	
	List<LikeDTO> findAll();
	
	void deleteReviewLike(LikeDTO likeDTO);
}
