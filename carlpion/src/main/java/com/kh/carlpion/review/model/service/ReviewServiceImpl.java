package com.kh.carlpion.review.model.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.exception.exceptions.NotFindException;
import com.kh.carlpion.file.service.FileService;
import com.kh.carlpion.review.model.dao.ReviewMapper;
import com.kh.carlpion.review.model.dto.ReviewDTO;
import com.kh.carlpion.review.model.vo.ReviewVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {
	
	private final ReviewMapper reviewMapper;
	private final FileService fileService;

	@Override
	public void save(ReviewDTO reviewDTO, List<MultipartFile> files) {
		/*사용자 인증 구간*/
		
		ReviewVO requestData = ReviewVO.builder()
									   .title(reviewDTO.getTitle())
									   .content(reviewDTO.getContent())
									   .userNo(reviewDTO.getUserNo())
									   .build();
		reviewMapper.save(requestData);
		
		if(files != null && !files.isEmpty()) {
			for(MultipartFile file : files) {
				
				if( !file.isEmpty()) {
					String filePath = fileService.storage(file);
					
					ReviewVO requestFileData = ReviewVO.builder()
													   .reviewNo(requestData.getReviewNo())
													   .fileUrl(filePath)
													   .build();
					reviewMapper.saveFile(requestFileData);
//					log.info("saveFile: {}", requestFileData);
				}
			}
		}
//		log.info("save: {}", requestData);
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
			throw new NotFindException("Not Find Notice");
		}
		return reviewDTO;
	}

	@Override
	public ReviewDTO updateById(ReviewDTO reviewDTO, List<MultipartFile> files) {
		
		if(files != null && !files.isEmpty()) {
			for(MultipartFile file : files) {
				
				if( !file.isEmpty()) {
					String filePath = fileService.storage(file);
					reviewDTO.setFileUrl(filePath);
					
					ReviewVO requestFileData = ReviewVO.builder()
													   .reviewNo(reviewDTO.getReviewNo())
													   .fileUrl(filePath)
													   .build();
					reviewMapper.deleteFileById(reviewDTO.getReviewNo());
					reviewMapper.saveFile(requestFileData);
//					log.info("saveFile: {}", requestFileData);
				}
			}			
		}
		reviewMapper.updateById(reviewDTO);
		return reviewDTO;
	}

	@Override
	public void deleteById(Long reviewNo) {
		reviewMapper.deleteById(reviewNo);
	}
}
