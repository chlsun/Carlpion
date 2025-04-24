package com.kh.carlpion.point.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.carlpion.point.model.dao.PointMapper;
import com.kh.carlpion.point.model.dto.PointDTO;
import com.kh.carlpion.point.model.dto.PointHistoryDTO;
import com.kh.carlpion.point.model.vo.PointHistoryVO;
import com.kh.carlpion.point.model.vo.PointVO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PointServiceImpl implements PointService {
	
	private final PointMapper pointMapper;

	@Override
	@Transactional
	public void saveHistoryPoint(PointHistoryDTO pointHistoryDTO) {
		PointHistoryVO pointHistoryVO = PointHistoryVO.builder()
													  .userNo(pointHistoryDTO.getUserNo())
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
		PointVO pointVO = PointVO.builder()
								 .userNo(pointDTO.getUserNo())
								 .point(pointDTO.getPoint())
								 .build();
		pointMapper.updateUserPoint(pointVO);
	}

	@Override
	@Transactional
	public void updateUserLevel(PointDTO pointDTO) {
		PointVO pointVO = PointVO.builder()
								 .userNo(pointDTO.getUserNo())
								 .userLevel(pointDTO.getUserLevel())
								 .build();
		pointMapper.updateUserLevel(pointVO);
	}

}
