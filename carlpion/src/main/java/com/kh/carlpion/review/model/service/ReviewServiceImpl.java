package com.kh.carlpion.review.model.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.auth.model.service.AuthService;
import com.kh.carlpion.exception.exceptions.NotFindException;
import com.kh.carlpion.exception.exceptions.UnauthorizedException;
import com.kh.carlpion.file.service.FileService;
import com.kh.carlpion.point.model.dto.PointHistoryDTO;
import com.kh.carlpion.point.model.service.PointService;
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
	private final AuthService authService;
	private final FileService fileService;
	private final PointService pointService;

	@Override
	@Transactional
	public void save(ReviewDTO reviewDTO, List<MultipartFile> files) {
		/*사용자 인증 구간*/
		Long userNo = authService.getUserDetails().getUserNo();		
		
		ReviewVO requestData = ReviewVO.builder()
									   .userNo(userNo)
									   .title(reviewDTO.getTitle())
									   .content(reviewDTO.getContent())
									   .build();
		reviewMapper.save(requestData);
		Long reviewNo = requestData.getReviewNo();
		
		if(files != null && !files.isEmpty()) {
			for(MultipartFile file : files) {
				
				if( !file.isEmpty()) {
					String filePath = fileService.storage(file);
					
					ReviewVO requestFileData = ReviewVO.builder()
													   .reviewNo(reviewNo)
													   .fileUrl(filePath)
													   .build();
					reviewMapper.saveFile(requestFileData);
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
		
		if(reviewDTO == null) {
			throw new NotFindException("해당 글을 찾을 수 없습니다.");
		}
		
		reviewMapper.updateCount(reviewNo);
		return reviewDTO;
	}

	@Override
	@Transactional
	public ReviewDTO updateById(ReviewDTO reviewDTO, List<MultipartFile> files) {
		Long reviewNo = reviewDTO.getReviewNo();
		checkedOwnerByUser(reviewNo);
		
		if(files != null && !files.isEmpty() && files.stream().anyMatch(file -> !file.isEmpty())) {
			List<String> fileUrls = reviewMapper.findFileByAll(reviewNo);
			
			if(fileUrls != null) {
				for(String fileUrl : fileUrls) {
					fileService.deleteFile(fileUrl);
				}
			}
			reviewMapper.deleteFileById(reviewNo);
			
			for(MultipartFile file : files) {
				
				if( !file.isEmpty()) {
					String filePath = fileService.storage(file);
					
					ReviewVO requestFileData = ReviewVO.builder()
													   .reviewNo(reviewNo)
													   .fileUrl(filePath)
													   .build();
					reviewMapper.saveFile(requestFileData);
				}
			}			
		}
		reviewMapper.updateById(reviewDTO);
		return reviewDTO;
	}

	@Override
	@Transactional
	public void softDeleteById(Long reviewNo) {
		checkedOwnerByUser(reviewNo);
		reviewMapper.softDeleteById(reviewNo);
	}
	
	/** 사용자 인증 */
	private void checkedOwnerByUser(Long reviewNo) {
		Long authUserNo = authService.getUserDetails().getUserNo();
		Long findUserNo = reviewMapper.findByUserNo(reviewNo);
		
		if(findUserNo == null || !authUserNo.equals(findUserNo)) {
			throw new UnauthorizedException("수정/삭제할 권한이 없습니다.");
		}
	}
}
