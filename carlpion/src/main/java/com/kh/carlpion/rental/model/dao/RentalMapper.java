package com.kh.carlpion.rental.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.kh.carlpion.rental.model.dto.PreparePaymentRequestDTO;
import com.kh.carlpion.rental.model.dto.RentCarPriceDTO;
import com.kh.carlpion.rental.model.dto.ReservationHistoryDTO;
import com.kh.carlpion.rental.model.entity.Reservation;

@Mapper
public interface RentalMapper {
	
	RentCarPriceDTO getRentCarPriceByCarNo(int carNo);
	
	int checkReservation(String merchantUid);
	
	void saveReservation(Reservation reservation);
	
	int checkDuplicationReservationByUserNo(@Param("preparePaymentRequest") PreparePaymentRequestDTO preparePaymentRequest, 
		    								@Param("userNo") long userNo);
	
	int checkDuplicationReservation(PreparePaymentRequestDTO preparePaymentRequest);
	
	int checkReservationByImpUID(String impUID);
	
	ReservationHistoryDTO getPaymentHistory(String impUID);
	
	ReservationHistoryDTO getReservation(long userNo);
	
	List<ReservationHistoryDTO> getReservationList(@Param("rowBounds") RowBounds rowBounds,
			   									   @Param("userNo") long userNo);
	
	List<ReservationHistoryDTO> getReservationAllList(long userNo);
	
	List<ReservationHistoryDTO> getRentHistory(@Param("rowBounds") RowBounds rowBounds,
											   @Param("userNo") long userNo);
	
	int getRentHistoryCount(long userNo);
	
	int getReservationCount(long userNo);
	
	Long getUserNoByImpUID(String impUID);
	
	int checkDeleteTime(String impUID);
	
	void deleteReservationByImpUID(String impUID);
	
}
