package com.kh.carlpion.review.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.carlpion.review.model.dto.ReviewDTO;
import com.kh.carlpion.review.model.vo.ReviewVO;

@Mapper
public interface ReviewMapper {
	
	void save(ReviewVO reviewVO);
	
	List<ReviewDTO> findAll(RowBounds rowBounds);
	
	ReviewDTO findById(Long reviewNo);
	
	void updateById(ReviewDTO reviewDTO);
			
	void softDeleteById(Long reviewNo);
	
	void saveFile(ReviewVO reviewVO);
	
	List<String> findFileByAll(Long reviewNo);
	
	void deleteFileById(Long reviewNo);
	
	Long findByUserNo(Long reviewNo);
	
	void updateCount(Long reviewNo); 
	
	int findTotalCount(int pageNo);
}
