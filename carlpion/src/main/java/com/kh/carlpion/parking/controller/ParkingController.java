package com.kh.carlpion.parking.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.carlpion.parking.model.dto.ParkingDTO;
import com.kh.carlpion.parking.model.service.ParkingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("parking")
@RequiredArgsConstructor
public class ParkingController {
	
	private final ParkingService parkingService;

	// 공영주차장API data DB에 저장
	@PostMapping("/setting")
	public ResponseEntity<?> parkingInfoSetting(){
		
		parkingService.parkingInfoSetting();
		
		return ResponseEntity.ok(201);
	}
	
	@GetMapping("/{search}")
	public ResponseEntity<?> getParkingInfoByTitle(@PathVariable(name="search") String search){
		
		log.info("{}", search);
		
		List<ParkingDTO> parkingList =  parkingService.getParkingInfoByTitle(search);
		
		return ResponseEntity.status(201).body(parkingList);
	}
	
	
	
}
