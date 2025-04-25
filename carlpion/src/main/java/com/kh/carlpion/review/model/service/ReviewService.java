package com.kh.carlpion.review.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.review.model.dto.ReviewDTO;

public interface ReviewService {
	
	void save(ReviewDTO reviewDTO, List<MultipartFile> files);
	
	List<ReviewDTO> findAll(int pageNo);
	
	ReviewDTO findById(Long reviewNo);
	
	ReviewDTO updateById(ReviewDTO reviewDTO, List<MultipartFile> files);
	
	void softDeleteById(Long reviewNo);
}
