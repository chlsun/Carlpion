package com.kh.carlpion.rental.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Reservation {
	
	private String merchantUid;
	private Long userNo;
	private int carNo;
	private String parkingId;
	private String rentalDate;
	private String returnDate;
	private int totalPrice;
	private String status;
	private String impUID;
	
}
