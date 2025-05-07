package com.kh.carlpion.admin.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.carlpion.admin.model.dto.CarModelDTO;
import com.kh.carlpion.admin.model.vo.CarModel;

@Mapper
public interface CarModelMapper {

	String checkCarModel(int modelNo);
	
	int carModelCount();
	
	List<CarModelDTO> getCarModelList(RowBounds rowBounds);
	
	CarModel getCarModelByCarModel(String carModel);
	
	void setCarModel(CarModel carModel);
	
	void updateCarModel(CarModel carModel);
	
	void removeCarModel(int modelNo);
	
	int getRentCarByModelNo(int modelNo);
	
	List<CarModelDTO> getCarModelNameList();
}
