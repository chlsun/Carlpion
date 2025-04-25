package com.kh.carlpion.admin.model.service;

import java.util.Map;

import com.kh.carlpion.admin.model.dto.RentCarDTO;

public interface RentCarService {

	Map<String, Object> getRentCarList(int page);
	
	void setRentCar(RentCarDTO rentCar);
	
	void updateRentCar(RentCarDTO rentCar);
}
