package com.kh.carlpion.review.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.review.model.dto.ReviewDTO;
import com.kh.carlpion.review.model.vo.ReviewVO;

public interface ReviewService {
	
	void save(ReviewDTO reviewDTO, MultipartFile file);
	
	List<ReviewDTO> findAll(int pageNo);
	
	ReviewDTO findById(Long reviewNo);
	
	ReviewDTO updateById(ReviewDTO reviewDTO, MultipartFile file);
	
	void deleteById(Long reviewNo);
}
