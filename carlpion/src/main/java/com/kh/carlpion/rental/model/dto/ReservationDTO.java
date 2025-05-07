package com.kh.carlpion.rental.model.dto;

import lombok.Data;

@Data
public class ReservationDTO {
	
	private String rentalDate;
	
	private String returnDate;
	
	private String parkingAddr;
	
	private String parkingId;
}
