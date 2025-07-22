package com.kh.carlpion.rental.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.kh.carlpion.rental.model.dto.ReservationHistoryDTO;
import com.kh.carlpion.rental.model.service.RentalService;
import com.kh.carlpion.util.model.dto.ResponseData;
import com.kh.carlpion.util.model.service.ResponseDataService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("rents")
@RestController
public class RentalController {
	
	private final RentalService rentalService;
	private final ResponseDataService responseDataService;

	@GetMapping
	public ResponseEntity<ResponseData> getRentalList(ReservationDTO reservation){
			
			List<RentCarDTO> rentCarList = rentalService.searchRentCarList(reservation);
			
			ResponseData responseData = responseDataService.responseDataBuilder("조회 성공", "200", rentCarList);
		
		return ResponseEntity.ok(responseData);
	}
	
	@GetMapping("/parking")
	public ResponseEntity<ResponseData> getRentalListByParkingId(ReservationDTO reservation){
		
			List<RentCarDTO> rentCarList = rentalService.getRentalListByParkingId(reservation);
			
			ResponseData responseData = responseDataService.responseDataBuilder("조회 성공", "200", rentCarList);
		
			return ResponseEntity.ok(responseData);
	}
	
	@GetMapping("/details/{carNo}")
	public ResponseEntity<ResponseData> getRentalListByCarNo(@PathVariable(name="carNo") int carNo){
		
			List<RentCarDTO> rentCarList = rentalService.getRentalListByCarNo(carNo);
			
			ResponseData responseData = responseDataService.responseDataBuilder("조회 성공", "200", rentCarList);
		
			return ResponseEntity.ok(responseData);
	}
	
	@PostMapping("/payment/prepare")
	public ResponseEntity<ResponseData> preparePaymdent(@RequestBody PreparePaymentRequestDTO PreparePaymentRequest){
		
		Map<String, Integer> response = rentalService.preparePaymdent(PreparePaymentRequest);
		
		ResponseData responseData = responseDataService.responseDataBuilder("인증 성공", "200", response);
	
		return ResponseEntity.ok(responseData);
	}
	
	@PostMapping("/payment/complate")
	public ResponseEntity<ResponseData> completePayment(@RequestBody ReservationDetailDTO reservationDetail){
		
		rentalService.completePayment(reservationDetail);
		
		ResponseData responseData = responseDataService.responseDataBuilder("추가 성공", "201", null);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
	}
	
	@GetMapping("/payment/{impUID}")
	public ResponseEntity<ResponseData> getPaymentHistory(@PathVariable(name = "impUID") String impUID){
		
		ReservationHistoryDTO reservation = rentalService.getPaymentHistory(impUID);
		
		ResponseData responseData = responseDataService.responseDataBuilder("조회 성공", "200", reservation);
		
		return ResponseEntity.ok(responseData);
	}
	
	@GetMapping("/reservation")
	public ResponseEntity<ResponseData> getReservation(){
		
		ReservationHistoryDTO reservation = rentalService.getReservation();
		
		ResponseData responseData = responseDataService.responseDataBuilder("조회 성공", "200", reservation);
		
		return ResponseEntity.ok(responseData);
	}
	
	@GetMapping("/reservations")
	public ResponseEntity<ResponseData> getReservationList(){
		
		List<ReservationHistoryDTO> reservation = rentalService.getReservationList();
		
		ResponseData responseData = responseDataService.responseDataBuilder("조회 성공", "200", reservation);
		
		return ResponseEntity.ok(responseData);
	}
	
	@DeleteMapping("/reservation/{impUID}")
	public ResponseEntity<ResponseData> deleteReservationByImpUID(@PathVariable(name = "impUID") String impUID){
		
		rentalService.deleteReservationByImpUID(impUID);
		
		ResponseData responseData = responseDataService.responseDataBuilder("삭제 성공", "200", null);
		
		return ResponseEntity.ok(responseData);
	}
	
	
	@GetMapping("/history/{limit}")
	public ResponseEntity<ResponseData> getRentHistory(@PathVariable(name = "limit") int limit){
		
		Map<String, Object> history = rentalService.getRentHistory(limit);
		
		ResponseData responseData = responseDataService.responseDataBuilder("조회 성공", "200", history);
		
		return ResponseEntity.ok(responseData);
	}
	
}
