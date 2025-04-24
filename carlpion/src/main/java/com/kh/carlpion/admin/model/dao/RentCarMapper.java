package com.kh.carlpion.admin.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.carlpion.admin.model.dto.RentCarDTO;

@Mapper
public interface RentCarMapper {
	
	int checkCarId(String carId);
	
	int rentCarCount();
	
	List<RentCarDTO> getRentCarList(RowBounds rowBounds);
	
	void setRentCar(RentCarDTO rentCar);
}
