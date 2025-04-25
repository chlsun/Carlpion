package com.kh.carlpion.admin.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.kh.carlpion.admin.model.dao.RentCarMapper;
import com.kh.carlpion.admin.model.dto.RentCarDTO;
import com.kh.carlpion.admin.util.PageInfoUtil;
import com.kh.carlpion.exception.exceptions.AlreadyExistsException;
import com.kh.carlpion.exception.exceptions.RentCarNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class RentCarServiceImpl implements RentCarService {
	
	private final RentCarMapper rentCarMapper;
	private final PageInfoUtil pageInfoUtil;

	@Override
	public Map<String, Object> getRentCarList(int page) {
		
		final int pageSize = 10;
		
		int rentCarCount = rentCarMapper.rentCarCount();
		
		if(rentCarCount < 1) {
			throw new RentCarNotFoundException("차량 모델이 존재하지 않습니다.");
		}
		
		int offsetNum = (page - 1) * pageSize;
		
		RowBounds rowBound = new RowBounds(offsetNum, pageSize);
		
		List<RentCarDTO> returnList = rentCarMapper.getRentCarList(rowBound);
		
		log.info("returnList : {}" ,returnList);
		
		Map<String, Integer> pageInfo = pageInfoUtil.getPageInfo(page, rentCarCount, pageSize);
		
		Map<String, Object> viewInfo = new HashMap();
		
		viewInfo.put("carModelList", returnList);
		
		viewInfo.put("pageInfo", pageInfo);
		
		return viewInfo;
		
	}

	@Override
	public void setRentCar(RentCarDTO rentCar) {
		
		int checkNum = rentCarMapper.checkCarId(rentCar.getCarId());
		
		if(checkNum > 0) {
			throw new AlreadyExistsException("이미 존재하는 렌트 차량입니다.");
		}
		
		rentCarMapper.setRentCar(rentCar);
		
	}



}
