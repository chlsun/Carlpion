package com.kh.carlpion.admin.model.dto;

import java.sql.Date;

import com.kh.carlpion.parking.model.dto.ParkingDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@NotBlank
@Size
public class RentCarDTO {
	
	@Size(min=7, max=8, message="잘못된 형식의 차량번호입니다.")
	@NotBlank(message="차량번호는 공백이 입력될 수 없습니다.")
	private String carId;
	
	@NotBlank(message="차량 모델 번호는 공백이 입력될 수 없습니다.")
	private int modelNo;
	
	@Size(min=0, max=255, message="잘못된 형식의 주차장 아이디 입니다.")
	@NotBlank(message="주차장 아이디는 공백이 입력될 수 없습니다.")
	private String parkingId;
	
	private Date enrollDate;
	
	private CarModelDTO carModel;
	
	private ParkingDTO parking;
}
