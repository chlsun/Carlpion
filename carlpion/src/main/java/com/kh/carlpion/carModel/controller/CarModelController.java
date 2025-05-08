package com.kh.carlpion.carModel.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.carlpion.admin.model.dto.CarModelDTO;
import com.kh.carlpion.admin.model.service.CarModelService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("carModel")
public class CarModelController {
	
	private final CarModelService carModelService;
	
	@GetMapping
	public ResponseEntity<?> getCarModelRandomList(){
		
		List<CarModelDTO> carModelList = carModelService.getCarModelRandomList();
		
		return ResponseEntity.status(HttpStatus.OK).body(carModelList);
	}
}
