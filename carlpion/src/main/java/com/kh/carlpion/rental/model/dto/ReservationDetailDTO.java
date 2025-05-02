package com.kh.carlpion.rental.model.dto;

import lombok.Data;

@Data
public class ReservationDetailDTO {
	private int carNo;
	private String rentalDate;
	private String returnDate;
	private int totalPrice;
	private String merchantUid;
	private String impUID;
}
