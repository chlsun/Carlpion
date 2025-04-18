package com.kh.carlpion.parking.model.service;

import java.util.List;

import com.kh.carlpion.parking.model.dto.ParkingDTO;

public interface ParkingService {
	
	void parkingInfoSetting();
	
	
	List<ParkingDTO> getParkingInfoByTitle(String search);
	
}
