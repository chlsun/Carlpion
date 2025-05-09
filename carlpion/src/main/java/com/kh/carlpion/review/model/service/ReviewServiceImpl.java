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
import com.kh.carlpion.file.service.FileService;
import com.kh.carlpion.point.model.dto.LikeDTO;
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
		PointHistoryDTO pointHistoryDTO = new PointHistoryDTO();
		pointHistoryDTO.setUserNo(userNo);
		pointHistoryDTO.setReason("리뷰 작성");
		pointHistoryDTO.setPointChange(10L);
		pointService.saveHistoryPoint(pointHistoryDTO);
//		log.info("save: {}", requestData);		
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
		Long userNo = authService.getUserDetails().getUserNo();
		
		if(reviewDTO == null) {
			throw new NotFindException("해당 글을 찾을 수 없습니다.");
		}

		List<String> fileUrls = reviewMapper.findFileByAll(reviewNo);
		
		reviewDTO.setFileUrls(fileUrls);		
		reviewMapper.updateCount(reviewNo);	
		
		LikeDTO likeDTO = new LikeDTO();
		likeDTO.setUserNo(userNo);
		likeDTO.setReviewNo(reviewNo);
		boolean like = pointService.findByLike(likeDTO);
		
		reviewDTO.setLike(like);
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
			deleteFiles(reviewNo);
			
			for(MultipartFile file : files) {			
				if(file != null && !file.isEmpty()) {
					String filePath = fileService.storage(file);
					
					ReviewVO requestFileData = ReviewVO.builder()
													   .reviewNo(reviewNo)
													   .fileUrl(filePath)
													   .build();
					reviewMapper.saveFile(requestFileData);
				}
			}
		} else { 
			deleteFiles(reviewNo);
		}
		reviewMapper.updateById(reviewDTO);
		return reviewDTO;
	}
	
	private void deleteFiles(Long reviewNo) {
	    List<String> fileUrls = reviewMapper.findFileByAll(reviewNo);
	    
	    if (fileUrls != null && !fileUrls.isEmpty()) {
	        for (String fileUrl : fileUrls) {
	            fileService.deleteFile(fileUrl);
	        }
	        reviewMapper.deleteFileById(reviewNo);
	    }
	}

	@Override
	@Transactional
	public void softDeleteById(Long reviewNo) {
		Long userNo = authService.getUserDetails().getUserNo();
		checkedOwnerByUser(userNo, reviewNo);
		reviewMapper.softDeleteById(reviewNo);
		
		PointHistoryDTO pointHistoryDTO = new PointHistoryDTO();	
		pointHistoryDTO.setUserNo(userNo);
		pointHistoryDTO.setReason("리뷰 삭제");
		pointHistoryDTO.setPointChange(-10L);	
		pointService.saveHistoryPoint(pointHistoryDTO);
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
