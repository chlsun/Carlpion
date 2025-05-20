package com.kh.carlpion.review.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.auth.model.service.AuthService;
import com.kh.carlpion.exception.exceptions.NotFindException;
import com.kh.carlpion.exception.exceptions.UnauthorizedException;
import com.kh.carlpion.file.service.FileDynamicService;
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
	private final FileDynamicService fileDynamicService;
	private final PointService pointService;
	
	private static final String BOARD_TYPE = "review";

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
				fileDynamicService.saveFiles(file, reviewNo, BOARD_TYPE);
			}
		}
		pointService.saveHistory(userNo,"리뷰 작성", 10L);
	}

	@Override
	public Map<String, Object> findAll(int pageNo) {
		int pageLimit = 12;
		int btnLimit = 12;	
		int totalCount = reviewMapper.findTotalCount(pageNo);
		int maxPage = (int)Math.ceil((double) totalCount / pageLimit);	
		int startBtn = (pageNo - 1) / btnLimit * btnLimit + 1;
		int endBtn = startBtn + btnLimit - 1;
		
		if(pageNo > maxPage && maxPage > 0) {
			pageNo = maxPage;
		}
		
		if(maxPage == 0) {
			pageNo = 1;
		}

		if(endBtn > maxPage) {
			endBtn = maxPage;
		}
		RowBounds rowBounds = new RowBounds((pageNo - 1) * pageLimit, pageLimit);
		List<ReviewDTO> list = reviewMapper.findAll(rowBounds);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("totalCount", totalCount);
		map.put("pageNo", pageNo);
		map.put("pageLimit", pageLimit);
		map.put("btnLimit", btnLimit);
		map.put("maxPage", maxPage);
		map.put("startBtn", startBtn);
		map.put("endBtn", endBtn);
		return map;
	}

	@Override
	public ReviewDTO findById(Long reviewNo) {
		ReviewDTO reviewDTO = reviewMapper.findById(reviewNo);
		
		if(reviewDTO == null) {
			throw new NotFindException("해당 글을 찾을 수 없습니다.");
		}				
		List<String> fileUrls = fileDynamicService.findFileByAll(reviewNo, BOARD_TYPE);
		
		reviewDTO.setFileUrls(fileUrls);		
		reviewMapper.updateCount(reviewNo);	
		return reviewDTO;
	}

	@Override
	@Transactional
	public ReviewDTO updateById(ReviewDTO reviewDTO, List<MultipartFile> files) {
		Long reviewNo = reviewDTO.getReviewNo();
		Long userNo = authService.getUserDetails().getUserNo();
		checkedOwnerByUser(userNo, reviewNo);
		
		boolean containsFiles = files != null && files.stream().anyMatch(file -> file != null && !file.isEmpty());
		
		if(containsFiles) {				
			fileDynamicService.deleteFiles(reviewNo, BOARD_TYPE);
			
			for(MultipartFile file : files) {			
				fileDynamicService.saveFiles(file, reviewNo, BOARD_TYPE);
			}
		} else { 
			fileDynamicService.deleteFiles(reviewNo, BOARD_TYPE);
		}
		reviewMapper.updateById(reviewDTO);
		return reviewDTO;
	}

	@Override
	@Transactional
	public void softDeleteById(Long reviewNo) {
		Long userNo = authService.getUserDetails().getUserNo();
		checkedOwnerByUser(userNo, reviewNo);
		reviewMapper.softDeleteById(reviewNo);

		pointService.saveHistory(userNo,"리뷰 삭제", -10L);
	}
	
	/** 사용자 인증 */
	private void checkedOwnerByUser(Long userNo, Long reviewNo) {
		Long findUserNo = reviewMapper.findByUserNo(reviewNo);	
		
		boolean isAdmin = SecurityContextHolder.getContext().getAuthentication()
		        .getAuthorities().stream()
		        .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
		
		
		if (!isAdmin && (findUserNo == null || !userNo.equals(findUserNo))) {
	        throw new UnauthorizedException("수정할 권한이 없습니다.");
	    }
	}
}
