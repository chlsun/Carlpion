package com.kh.carlpion.admin.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.kh.carlpion.admin.model.dto.CarModelDTO;

@Mapper
public interface CarModelMapper {

	int checkCarModel(String carModel);
	
	void setCarModel(CarModelDTO carModel);
}
