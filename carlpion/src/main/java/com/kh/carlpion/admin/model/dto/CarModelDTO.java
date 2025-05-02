package com.kh.carlpion.admin.model.dto;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Validated
public class CarModelDTO {
	
	private int modelNo;
	
	@Size(max=50, message="차량 모델명은 50자 이상은 입력 불가능합니다.")
	@NotBlank(message="차량 모델명을 입력해주세요")
	private String carModel;
	
	@NotNull(message="렌트 비용을 입력해주세요")
	@Positive(message="렌트 비용은 0보다 커야 합니다.")
	private Integer rentPrice;

	@NotNull(message="시간당 비용을 입력해주세요")
	@Positive(message="시간당 비용은 0보다 커야 합니다.")
	private Integer hourPrice;
	
	@Size(max=30, message="커넥터명은 30자 이상은 입력 불가능합니다.")
	@NotBlank(message="커넥터 종류를 입력해주세요")
	private String chargeType;
	
	@NotNull(message="승차 인원을 입력해주세요")
	@Positive(message="승차 인원은 0보다 커야 합니다.")
	private Integer seatCount;
	
	private String imgURL;
}
