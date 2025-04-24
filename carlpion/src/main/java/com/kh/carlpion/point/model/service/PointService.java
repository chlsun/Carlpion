package com.kh.carlpion.point.model.service;

import java.util.List;

import com.kh.carlpion.point.model.dto.LikeDTO;
import com.kh.carlpion.point.model.dto.PointDTO;
import com.kh.carlpion.point.model.dto.PointHistoryDTO;

public interface PointService {
	
	void saveHistoryPoint(PointHistoryDTO pointHistoryDTO);
	
	List<PointHistoryDTO> findAll(Long userNo);
	
	void updateUserPoint(PointDTO pointDTO);
			
	void updateUserLevel(PointDTO pointDTO);
	
	void saveReviewLike(LikeDTO likeDTO);
	
	List<LikeDTO> findAll();
	
	void deleteReviewLike(LikeDTO likeDTO);
}
