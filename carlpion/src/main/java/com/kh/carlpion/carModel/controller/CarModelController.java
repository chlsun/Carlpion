package com.kh.carlpion.carModel.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.carlpion.admin.model.dto.CarModelDTO;
import com.kh.carlpion.admin.model.service.CarModelService;
import com.kh.carlpion.util.model.dto.ResponseData;
import com.kh.carlpion.util.model.service.ResponseDataService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("carModel")
public class CarModelController {
	
	private final CarModelService carModelService;
	private final ResponseDataService responseDataService;
	
	@GetMapping
	public ResponseEntity<ResponseData> getCarModelRandomList(){
		
		List<CarModelDTO> carModelList = carModelService.getCarModelRandomList();
		
		ResponseData responseData = responseDataService.responseDataBuilder("조회 성공", "200", carModelList);
		
		return ResponseEntity.ok(responseData);
	}
}
