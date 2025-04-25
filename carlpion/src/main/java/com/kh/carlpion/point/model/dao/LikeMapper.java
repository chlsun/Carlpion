package com.kh.carlpion.point.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.carlpion.point.model.dto.LikeDTO;
import com.kh.carlpion.point.model.vo.LikeVO;

@Mapper
public interface LikeMapper {
	
	void saveReviewLike(LikeVO likeVO);
	
	List<LikeDTO> findAll();
	
	void deleteReviewLike(LikeVO likeVO);
}
