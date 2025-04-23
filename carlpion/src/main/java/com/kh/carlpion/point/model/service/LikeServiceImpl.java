package com.kh.carlpion.point.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.carlpion.point.model.dao.LikeMapper;
import com.kh.carlpion.point.model.dto.LikeDTO;
import com.kh.carlpion.point.model.vo.LikeVO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LikeServiceImpl implements LikeService {
	
	private final LikeMapper likeMapper;

	@Override
	@Transactional
	public void saveReviewLike(LikeDTO likeDTO) {	
		likeMapper.saveReviewLike(likeData(likeDTO));
	}

	@Override
	public List<LikeDTO> findAll() {
		return likeMapper.findAll();
	}

	@Override
	@Transactional
	public void deleteReviewLike(LikeDTO likeDTO) {
		likeMapper.deleteReviewLike(likeData(likeDTO));
	}
	
	private LikeVO likeData(LikeDTO likeDTO) {
		LikeVO requestData = LikeVO.builder()
								   .userNo(likeDTO.getUserNo())
								   .reviewNo(likeDTO.getReviewNo())
								   .build();		
		return requestData;
	}
}
