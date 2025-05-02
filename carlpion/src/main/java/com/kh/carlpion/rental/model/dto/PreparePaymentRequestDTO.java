package com.kh.carlpion.rental.model.dto;

import lombok.Data;

@Data
public class PreparePaymentRequestDTO {
	private String merchantUid;
	private int carNo;
	private String rentalDate;
	private String returnDate;
}
