package com.kh.carlpion.admin.model.dto;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Validated
public class CarModelDTO {
	
	@Size(max=50, message="차량 모델명은 50자 이상은 입력 불가능합니다.")
	@NotBlank(message="차량 모델명을 입력해주세요")
	private String carModel;
	
	@NotBlank(message="렌트 비용을 입력해주세요")
	private int rentPrice;
	
	@NotBlank(message="시간당 비용을 입력해주세요")
	private int hourPrice;
	
	@Size(max=30, message="커넥터명은 30자 이상은 입력 불가능합니다.")
	@NotBlank(message="커넥터 종류를 입력해주세요")
	private String chargeType;
	
	@NotBlank(message="승치 인원을 입력해주세요")
	private int seatCount;
	
	@NotBlank(message="차량 이미지을 추가해주세요")
	private String carImgURL;
	
}
