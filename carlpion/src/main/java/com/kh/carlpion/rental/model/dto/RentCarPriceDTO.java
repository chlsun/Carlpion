package com.kh.carlpion.rental.model.dto;

import lombok.Data;

@Data
public class RentCarPriceDTO {

	private int carNo;
	private int rentPrice;
	private int hourPrice;
	private String parkingId;

}
