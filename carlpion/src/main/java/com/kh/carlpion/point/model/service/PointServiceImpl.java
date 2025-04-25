package com.kh.carlpion.point.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.carlpion.auth.model.service.AuthService;
import com.kh.carlpion.point.model.dao.PointMapper;
import com.kh.carlpion.point.model.dto.LikeDTO;
import com.kh.carlpion.point.model.dto.PointDTO;
import com.kh.carlpion.point.model.dto.PointHistoryDTO;
import com.kh.carlpion.point.model.vo.LikeVO;
import com.kh.carlpion.point.model.vo.PointHistoryVO;
import com.kh.carlpion.point.model.vo.PointVO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PointServiceImpl implements PointService {
	
	private final PointMapper pointMapper;
	private final AuthService authService;

	@Override
	@Transactional
	public void saveHistoryPoint(PointHistoryDTO pointHistoryDTO) {
		Long userNo = authService.getUserDetails().getUserNo();
		
		PointHistoryVO pointHistoryVO = PointHistoryVO.builder()
													  .userNo(userNo)
													  .reviewNo(pointHistoryDTO.getReviewNo())
													  .pointChange(pointHistoryDTO.getPointChange())
													  .reason(pointHistoryDTO.getReason())
													  .build();
		pointMapper.saveHistoryPoint(pointHistoryVO);
	}

	@Override
	public List<PointHistoryDTO> findAll(Long userNo) {
		return pointMapper.findAll(userNo);
	}

	@Override
	@Transactional
	public void updateUserPoint(PointDTO pointDTO) {
		Long userNo = authService.getUserDetails().getUserNo();
		
		PointVO pointVO = PointVO.builder()
								 .userNo(userNo)
								 .point(pointDTO.getPoint())
								 .build();
		pointMapper.updateUserPoint(pointVO);
	}

	@Override
	@Transactional
	public void updateUserLevel(PointDTO pointDTO) {
		Long userNo = authService.getUserDetails().getUserNo();
		
		PointVO pointVO = PointVO.builder()
								 .userNo(userNo)
								 .userLevel(pointDTO.getUserLevel())
								 .build();
		pointMapper.updateUserLevel(pointVO);
	}

	@Override
	@Transactional
	public void saveReviewLike(LikeDTO likeDTO) {
		pointMapper.saveReviewLike(likeData(likeDTO));		
	}

	@Override
	public List<LikeDTO> findAllLike() {
		return pointMapper.findAllLike();
	}

	@Override
	@Transactional
	public void deleteReviewLike(LikeDTO likeDTO) {
		pointMapper.deleteReviewLike(likeData(likeDTO));		
	}
	
	private LikeVO likeData(LikeDTO likeDTO) {
		Long userNo = authService.getUserDetails().getUserNo();
		
		LikeVO requestData = LikeVO.builder()
								   .userNo(userNo)
								   .reviewNo(likeDTO.getReviewNo())
								   .build();		
		return requestData;
	}
}
