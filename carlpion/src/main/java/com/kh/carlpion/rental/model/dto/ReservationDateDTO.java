package com.kh.carlpion.rental.model.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ReservationDateDTO {
	
	private String reservationId;

	private String rentalDate;
	
	private String returnDate;
}
