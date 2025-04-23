package com.kh.carlpion.admin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.carlpion.admin.model.dto.CarModelDTO;
import com.kh.carlpion.admin.model.service.CarModelService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("admin")
public class AdminController {
	
	private final CarModelService carModelService;

	@PostMapping("/model")
	public ResponseEntity<?> setCarModel(@RequestBody CarModelDTO carModel){
		
		carModelService.setCarModel(carModel);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
		
	}
}
