package com.kh.carlpion.rental.model.dto;

import lombok.Data;

@Data
public class ReservationHistoryDTO {
	
	private String impUID;
	
	private String carModel;
	
	private String carId;
	
	private String parkingAddr;
	
	private String parkingTitle;
	
	private String rentalDate;
	
	private String returnDate;
	
	private int totalPrice;
}
