package com.kh.carlpion.parking.model.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ParkingServiceImpl implements ParkingService {
	
	@Value("${parking.api.key}")
	private String apiKey;
	
	@Override
	public void parkingInfoSetting() {
		
		 
		
	}

}
