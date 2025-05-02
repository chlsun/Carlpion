package com.kh.carlpion.rental.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.kh.carlpion.rental.model.dto.PreparePaymentRequestDTO;
import com.kh.carlpion.rental.model.dto.RentCarPriceDTO;
import com.kh.carlpion.rental.model.entity.Reservation;

@Mapper
public interface RentalMapper {
	
	RentCarPriceDTO getRentCarPriceByCarNo(int carNo);
	
	int checkReservation(String merchantUid);
	
	void saveReservation(Reservation reservation);
	
	int checkDuplicationReservation(PreparePaymentRequestDTO preparePaymentRequest);
}
