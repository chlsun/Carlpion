package com.kh.carlpion.parking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.carlpion.parking.model.service.ParkingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("parking")
@RequiredArgsConstructor
public class ParkingController {
	
	private final ParkingService parkingService;

	@PostMapping("/setting")
	public ResponseEntity<?> parkingInfoSetting(){
		
		parkingService.parkingInfoSetting();
		
		return ResponseEntity.ok(201);
	}
}
