package com.kh.carlpion.rental.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.carlpion.admin.model.dto.RentCarDTO;
import com.kh.carlpion.rental.model.dto.PreparePaymentRequestDTO;
import com.kh.carlpion.rental.model.dto.ReservationDTO;
import com.kh.carlpion.rental.model.dto.ReservationDetailDTO;
import com.kh.carlpion.rental.model.service.RentalService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("rents")
@RestController
public class RentalController {
	
	private final RentalService rentalService;

	@GetMapping
	public ResponseEntity<?> getRentalList(ReservationDTO reservation){
			
			List<RentCarDTO> rentCarList = rentalService.searchRentCarList(reservation);
		
		return ResponseEntity.status(HttpStatus.OK).body(rentCarList);
	}
	
	@GetMapping("/parking")
	public ResponseEntity<?> getRentalListByParkingId(ReservationDTO reservation){
		
			List<RentCarDTO> rentCarList = rentalService.getRentalListByParkingId(reservation);
		
		return ResponseEntity.status(HttpStatus.OK).body(rentCarList);
	}
	
	@GetMapping("/details/{carNo}")
	public ResponseEntity<?> getRentalListByCarNo(@PathVariable(name="carNo") int carNo){
			
			log.info("carNo : {}", carNo);
		
			List<RentCarDTO> rentCarList = rentalService.getRentalListByCarNo(carNo);
		
		return ResponseEntity.status(HttpStatus.OK).body(rentCarList);
	}
	
	@PostMapping("/payment/prepare")
	public ResponseEntity<?> preparePaymdent(@RequestBody PreparePaymentRequestDTO PreparePaymentRequest){
		
		Map<String, Integer> response = rentalService.preparePaymdent(PreparePaymentRequest);
	
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping("/payment/complate")
	public ResponseEntity<?> completePayment(@RequestBody ReservationDetailDTO reservationDetail){
		
		rentalService.completePayment(reservationDetail);
		
		return ResponseEntity.status(HttpStatus.CREATED).body("성공");
	}
}
