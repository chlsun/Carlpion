package com.kh.carlpion.admin.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.carlpion.admin.model.dto.RentCarDTO;
import com.kh.carlpion.rental.model.dto.ReservationDTO;

@Mapper
public interface RentCarMapper {
	
	String checkCarNo(int carNo);
	
	int checkCarId(String carId);
	
	int rentCarCount();
	
	List<RentCarDTO> getRentCarList(RowBounds rowBounds);
	
	List<RentCarDTO> getRentCarList();
	
	List<RentCarDTO> getRentCarListByAddr(ReservationDTO reservation);
	
	List<RentCarDTO> getRentalListByParkingId(ReservationDTO reservation);
	
	List<RentCarDTO> getRentalListByCarNo(int carNo);
	
	void setRentCar(RentCarDTO rentCar);
	
	void updateRentCar(RentCarDTO rentCar);
	
	void deleteRentCarByCarNo(int carNo);
}
