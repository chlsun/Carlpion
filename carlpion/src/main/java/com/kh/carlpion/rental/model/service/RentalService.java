package com.kh.carlpion.rental.model.service;

import java.util.List;
import java.util.Map;

import com.kh.carlpion.admin.model.dto.RentCarDTO;
import com.kh.carlpion.rental.model.dto.PreparePaymentRequestDTO;
import com.kh.carlpion.rental.model.dto.ReservationDTO;
import com.kh.carlpion.rental.model.dto.ReservationDetailDTO;
import com.kh.carlpion.rental.model.dto.ReservationHistoryDTO;

public interface RentalService {
	List<RentCarDTO> searchRentCarList(ReservationDTO reservation);
	
	List<RentCarDTO> getRentalListByParkingId(ReservationDTO reservation);
	
	List<RentCarDTO> getRentalListByCarNo(int carNo);
	
	Map<String, Integer> preparePaymdent(PreparePaymentRequestDTO PreparePaymentRequest);
	
	void completePayment(ReservationDetailDTO reservationDetail);
	
	ReservationHistoryDTO getPaymentHistory(String impUID);
	
	ReservationHistoryDTO getReservation();
	
	List<ReservationHistoryDTO> getReservationList();
	
	Map<String, Object> getRentHistory(int limit);
}
