package com.kh.carlpion.mypage.model.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ReservationsDTO {
	private String carId;
	private Long modelNo;
	private String carModel;
	private Long rentPrice;
	
	private Long  reservationNo;
	private Date rentalDate; 
	private Date returnDate;
	private Long totalPrice;
	private String status;
	private Date paymentCompletedAt;
	
	private String parkingId;
	private String parkingTitle;
	private String parkingAddr;
	
	
	
		
}
