package com.kh.carlpion.admin.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.admin.model.dto.CarModelDTO;

public interface CarModelService {
	
	Map<String, Object> getCarModelList(int page);
	
	List<CarModelDTO> getCarModelNameList();
	
	void setCarModel(CarModelDTO carModel, MultipartFile file);
	
	void updateCarModel(CarModelDTO carModel, MultipartFile file);
	
	void removeCarModel(CarModelDTO carModel);
	
	List<CarModelDTO> getCarModelRandomList();
	
}
