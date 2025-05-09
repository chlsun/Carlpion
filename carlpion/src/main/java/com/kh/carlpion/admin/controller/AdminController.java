package com.kh.carlpion.admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.admin.model.dto.CarModelDTO;
import com.kh.carlpion.admin.model.dto.RentCarDTO;
import com.kh.carlpion.admin.model.service.CarModelService;
import com.kh.carlpion.admin.model.service.RentCarService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("admin")
public class AdminController {
	
	private final CarModelService carModelService;
	private final RentCarService rentCarService;
	
	@GetMapping("/model/{page}")
	public ResponseEntity<?> getCarModelList(@PathVariable(name="page") int page){

		Map<String, Object> viewInfo = carModelService.getCarModelList(page);
		
		log.info("viewInfo : ", viewInfo);
		
		return ResponseEntity.status(HttpStatus.OK).body(viewInfo);
	}
	
	@GetMapping("/model")
	public ResponseEntity<?> getCarModelNameList(){

		List<CarModelDTO> modelNameList = carModelService.getCarModelNameList();
		
		return ResponseEntity.status(HttpStatus.OK).body(modelNameList);
	}

	@PostMapping("/model")
	public ResponseEntity<?> setCarModel(@Valid @ModelAttribute CarModelDTO carModel, 
										 @RequestParam(name="file", required=false) MultipartFile file){
		
		log.info("여기는 오나");
		
		carModelService.setCarModel(carModel, file);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
		
	}
	
	@PutMapping("/model")
	public ResponseEntity<?> updateCarModel(@Valid @ModelAttribute CarModelDTO carModel,
											@RequestParam(name="file", required=false) MultipartFile file){
		
		log.info("file: {} ", file);
		
		carModelService.updateCarModel(carModel, file);
		
		return ResponseEntity.status(HttpStatus.OK).build();
		
	}
	
	@DeleteMapping("/model")
	public ResponseEntity<?> removeCarModel(@RequestBody CarModelDTO carModel){
		
		carModelService.removeCarModel(carModel);
		
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	
	@PostMapping("/car")
	public ResponseEntity<?> setRentCar(@Valid @RequestBody RentCarDTO rentCar){
		
		rentCarService.setRentCar(rentCar);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	
	@GetMapping("/car/{page}")
	public ResponseEntity<?> getRentCarList(@PathVariable(name="page") int page){
		
		Map<String, Object> viewInfo = rentCarService.getRentCarList(page);
		
		return ResponseEntity.status(HttpStatus.OK).body(viewInfo);
	}
	
	@PutMapping("/car")
	public ResponseEntity<?> updateRentCar(@Valid @RequestBody RentCarDTO rentCar){
		
		rentCarService.updateRentCar(rentCar);
		
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@DeleteMapping("/car")
	public ResponseEntity<?> deleteRentCarByCarNo(@RequestParam(name = "carNo") int carNo){
		
		rentCarService.deleteRentCarByCarNo(carNo);
		
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	
}
