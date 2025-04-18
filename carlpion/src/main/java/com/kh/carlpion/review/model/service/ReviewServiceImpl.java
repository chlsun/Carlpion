package com.kh.carlpion.review.model.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.file.service.FileService;
import com.kh.carlpion.review.model.dao.ReviewMapper;
import com.kh.carlpion.review.model.dto.ReviewDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {
	
	private final ReviewMapper reviewMapper;
	private final FileService fileService;

	@Override
	public void save(ReviewDTO reviewDTO, MultipartFile file) {

	}

	@Override
	public List<ReviewDTO> findAll(int pageNo) {
		int pageSize = 10;
		RowBounds rowBounds = new RowBounds(pageNo * pageSize, pageSize);
		return reviewMapper.findAll(rowBounds);
	}

	@Override
	public ReviewDTO findById(Long reviewNo) {
		ReviewDTO reviewDTO = reviewMapper.findById(reviewNo);
		
		if(reviewNo == null) {
			throw new RuntimeException("Not Find Notice");
		}
		return reviewDTO;
	}

	@Override
	public ReviewDTO updateById(ReviewDTO reviewDTO, MultipartFile file) {
		
		if(file != null && !file.isEmpty()) {
			String filePath = fileService.storage(file);
			reviewDTO.setFileUrl(filePath);
		}
		reviewMapper.updateById(reviewDTO);
		return reviewDTO;
	}

	@Override
	public void deleteById(Long reviewNo) {
		reviewMapper.deleteById(reviewNo);
	}

}
