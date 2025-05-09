package com.kh.carlpion.point.model.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.carlpion.auth.model.service.AuthService;
import com.kh.carlpion.exception.exceptions.NotFindException;
import com.kh.carlpion.exception.exceptions.PointException;
import com.kh.carlpion.exception.exceptions.UnauthorizedException;
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
	
	/** 
	 * Long userNo,
	 * Long pointChange,
	 * String reason 
	 */
	@Override
	@Transactional
	public void saveHistoryPoint(PointHistoryDTO pointHistoryDTO) {	
		checkedOwnerByUser(pointHistoryDTO.getUserNo());
		PointHistoryVO pointHistoryVO = PointHistoryVO.builder()
													  .userNo(pointHistoryDTO.getUserNo())
													  .pointChange(pointHistoryDTO.getPointChange())
													  .reason(pointHistoryDTO.getReason())
													  .build();
		pointMapper.saveHistoryPoint(pointHistoryVO);
		
		PointDTO pointDTO = new PointDTO();
		pointDTO.setUserNo(pointHistoryDTO.getUserNo());
		pointDTO.setPoint(pointHistoryDTO.getPointChange());			
		updateUserPoint(pointDTO);
	}

	@Override
	@Transactional
	public void updateUserPoint(PointDTO pointDTO) {
		Long userNo = pointDTO.getUserNo();
		Long changePoint = pointDTO.getPoint();
		
		if(changePoint != null && changePoint < 0) {
			PointDTO currentPoint = pointMapper.findByPoint(userNo);
			
			if(currentPoint == null) {
				throw new PointException("포인트 조회의 오류가 발생했습니다.");
			}
			Long totalPoint = currentPoint.getPoint();
			
			if(totalPoint + changePoint < 0) {
				throw new PointException("포인트가 부족합니다. 보유한 포인트: ["+ totalPoint +"]");
			}
		}
		PointVO pointVO = PointVO.builder()
								 .userNo(userNo)
								 .point(changePoint)
								 .build();
		pointMapper.updateUserPoint(pointVO);
	}
	
	@Override
	public List<PointHistoryDTO> findAll(Long userNo) {
		return pointMapper.findAllHistory(userNo);
	}

	/** String userLevel */
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
	
	/** 사용자 인증 */
	private void checkedOwnerByUser(Long targetUserNo) {
		Long userNo = authService.getUserDetails().getUserNo();
		boolean isAdmin = SecurityContextHolder.getContext()
											   .getAuthentication()
									           .getAuthorities()
									           .stream()
									           .anyMatch(authority -> 
									           		authority.getAuthority().equals("ROLE_ADMIN")
						         			   );
		if( !isAdmin && !userNo.equals(targetUserNo)) {
			throw new UnauthorizedException("해당 요청을 처리할 수 없습니다.");
		}
		PointDTO userInfo = pointMapper.findByPoint(targetUserNo);
		
		if(userInfo == null) {
			throw new NotFindException("해당 사용자를 찾을 수 없습니다. ["+ targetUserNo +"]");
		}
	}

	
	@Override
	@Transactional
	public void saveReviewLike(LikeDTO likeDTO) {
		pointMapper.saveReviewLike(likeData(likeDTO));		
	}

	@Override
	public List<LikeDTO> findAllLike(LikeDTO likeDTO) {
		return null;	/* 구현 필요 */
	}
	
	@Override
	public LikeDTO findByLike(LikeDTO likeDTO) {
		Long userNo = authService.getUserDetails().getUserNo();
		likeDTO.setUserNo(userNo);
		return pointMapper.findByLike(likeDTO);	
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
