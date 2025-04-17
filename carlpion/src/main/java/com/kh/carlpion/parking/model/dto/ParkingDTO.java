package com.kh.carlpion.parking.model.dto;

import lombok.Data;

@Data
public class ParkingDTO {
	
	private String parkingId;
	private String parkingTitle;
	private String parkingAddr;
	private String lat;
	private String lot;
}
