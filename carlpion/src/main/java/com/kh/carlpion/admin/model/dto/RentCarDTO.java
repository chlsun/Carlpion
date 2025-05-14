package com.kh.carlpion.admin.model.dto;

import java.sql.Date;

import org.springframework.validation.annotation.Validated;

import com.kh.carlpion.parking.model.dto.ParkingDTO;
import com.kh.carlpion.rental.model.dto.ReservationDateDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Validated
public class RentCarDTO {
	
	private int CarNo;
	
	@Size(min=7, max=8, message="잘못된 형식의 차량번호입니다.")
	@NotBlank(message="차량번호는 공백이 입력될 수 없습니다.")
	@Pattern(regexp = "^\\d{2,3}[가-힣]\\d{4}$", message="차량 번호 형식이 잘못되었습니다.\n00가0000 혹은 000가0000 와 같은 형식으로 입력해주세요.")
	private String carId;
	
	private int modelNo;
	
	@Size(min=0, max=255, message="잘못된 형식의 주차장 아이디 입니다.")
	@NotBlank(message="주차장 아이디는 공백이 입력될 수 없습니다.")
	private String parkingId;
	
	private Date enrollDate;
	
	private CarModelDTO carModel;
	
	private ParkingDTO parking;
	
	private ReservationDateDTO reservationRental;
}
