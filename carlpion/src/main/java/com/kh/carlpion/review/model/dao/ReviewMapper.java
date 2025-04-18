package com.kh.carlpion.review.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.carlpion.review.model.dto.ReviewDTO;

@Mapper
public interface ReviewMapper {
	
	void save(ReviewDTO reviewDTO);
	
	List<ReviewDTO> findAll(RowBounds rowBounds);
	
	ReviewDTO findById(Long reviewNo);
	
	void updateById(ReviewDTO reviewDTO);
			
	void deleteById(Long reviewNo);
}
