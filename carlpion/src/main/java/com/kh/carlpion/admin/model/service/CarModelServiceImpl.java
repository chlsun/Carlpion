package com.kh.carlpion.admin.model.service;

import org.springframework.stereotype.Service;

import com.kh.carlpion.admin.model.dao.CarModelMapper;
import com.kh.carlpion.admin.model.dto.CarModelDTO;
import com.kh.carlpion.exception.exceptions.AlreadyExistsException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
@Service
public class CarModelServiceImpl implements CarModelService {
	
	private final CarModelMapper carModelMapper;

	@Override
	public void setCarModel(CarModelDTO carModel) {
		
		log.info("carModel: {}", carModel);
		
		int checkNum = carModelMapper.checkCarModel(carModel.getCarModel());
		
		if(checkNum > 0) {
			throw new AlreadyExistsException("이미 존재하는 차량 모델입니다.");
		}
		
		carModelMapper.setCarModel(carModel);
	}

}
