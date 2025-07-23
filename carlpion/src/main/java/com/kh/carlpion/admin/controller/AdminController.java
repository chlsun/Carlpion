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
import com.kh.carlpion.util.model.dto.ResponseData;
import com.kh.carlpion.util.model.service.ResponseDataService;

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
	private final ResponseDataService responseDataService;
	
	@GetMapping("/model/{page}")
	public ResponseEntity<ResponseData> getCarModelList(@PathVariable(name="page") int page){

		Map<String, Object> viewInfo = carModelService.getCarModelList(page);
		
		ResponseData responseData = responseDataService.responseDataBuilder("조회 성공", "200", viewInfo);
		
		return ResponseEntity.ok(responseData);
	}
	
	@GetMapping("/model")
	public ResponseEntity<ResponseData> getCarModelNameList(){

		List<CarModelDTO> modelNameList = carModelService.getCarModelNameList();
		
		ResponseData responseData = responseDataService.responseDataBuilder("조회 성공", "200", modelNameList);
		
		return ResponseEntity.ok(responseData);
	}

	@PostMapping("/model")
	public ResponseEntity<ResponseData> setCarModel(@Valid @ModelAttribute CarModelDTO carModel, 
										 @RequestParam(name="file", required=false) MultipartFile file){
		
		carModelService.setCarModel(carModel, file);
		
		ResponseData responseData = responseDataService.responseDataBuilder("추가 성공", "201", null);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
	}
	
	@PutMapping("/model")
	public ResponseEntity<ResponseData> updateCarModel(@Valid @ModelAttribute CarModelDTO carModel,
											@RequestParam(name="file", required=false) MultipartFile file){
		
		carModelService.updateCarModel(carModel, file);
		
		ResponseData responseData = responseDataService.responseDataBuilder("수정 성공", "200", null);
		
		return ResponseEntity.ok(responseData);
		
	}
	
	@DeleteMapping("/model")
	public ResponseEntity<ResponseData> removeCarModel(@RequestBody CarModelDTO carModel){
		
		carModelService.removeCarModel(carModel);
		
		ResponseData responseData = responseDataService.responseDataBuilder("삭제 성공", "200", null);

		
		return ResponseEntity.ok(responseData);
	}
	
	
	@PostMapping("/car")
	public ResponseEntity<ResponseData> setRentCar(@Valid @RequestBody RentCarDTO rentCar){
		
		rentCarService.setRentCar(rentCar);
		
		ResponseData responseData = responseDataService.responseDataBuilder("추가 성공", "201", null);

		return ResponseEntity.ok(responseData);
	}
	
	
	@GetMapping("/car/{page}")
	public ResponseEntity<ResponseData> getRentCarList(@PathVariable(name="page") int page){
		
		Map<String, Object> viewInfo = rentCarService.getRentCarList(page);
		
		ResponseData responseData = responseDataService.responseDataBuilder("조회 성공", "200", viewInfo);

		return ResponseEntity.ok(responseData);
	}
	
	@PutMapping("/car")
	public ResponseEntity<ResponseData> updateRentCar(@Valid @RequestBody RentCarDTO rentCar){
		
		rentCarService.updateRentCar(rentCar);
		
		ResponseData responseData = responseDataService.responseDataBuilder("수정 성공", "200", null);

		return ResponseEntity.ok(responseData);
	}
	
	@DeleteMapping("/car")
	public ResponseEntity<ResponseData> deleteRentCarByCarNo(@RequestParam(name = "carNo") int carNo){
		
		rentCarService.deleteRentCarByCarNo(carNo);
		
		ResponseData responseData = responseDataService.responseDataBuilder("삭제 성공", "200", null);

		return ResponseEntity.ok(responseData);
	}
	
	
}
