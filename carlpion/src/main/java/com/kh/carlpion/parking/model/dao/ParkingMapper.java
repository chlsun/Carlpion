package com.kh.carlpion.parking.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.carlpion.parking.model.dto.ParkingDTO;

@Mapper
public interface ParkingMapper {

	void parkingInfoSetting(List<ParkingDTO> parkingList);
	
	List<ParkingDTO> selectParkingId();
	
	List<ParkingDTO> getParkingInfoByTitle(String search);
}
