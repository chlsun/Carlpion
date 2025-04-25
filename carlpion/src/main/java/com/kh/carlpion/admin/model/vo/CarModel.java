package com.kh.carlpion.admin.model.vo;

import java.sql.Date;

import com.kh.carlpion.review.model.vo.ReviewVO;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Builder
@Getter
public class CarModel {
	
	private int modelNo;
	private String carModel;
	private int rentPrice;
	private int hourPrice;
	private String chargeType;
	private int seatCount;
	private String carImgURL;

}
