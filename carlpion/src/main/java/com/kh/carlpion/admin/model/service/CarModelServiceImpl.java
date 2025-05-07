package com.kh.carlpion.admin.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.admin.model.dao.CarModelMapper;
import com.kh.carlpion.admin.model.dto.CarModelDTO;
import com.kh.carlpion.admin.model.vo.CarModel;
import com.kh.carlpion.admin.util.PageInfoUtil;
import com.kh.carlpion.exception.exceptions.AlreadyExistsException;
import com.kh.carlpion.exception.exceptions.CarModelNotFoundException;
import com.kh.carlpion.exception.exceptions.ImgFileNotFoundException;
import com.kh.carlpion.exception.exceptions.ModelNotFoundException;
import com.kh.carlpion.file.service.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
@Service
public class CarModelServiceImpl implements CarModelService {
	
	private final PageInfoUtil pageInfoUtil;
	private final CarModelMapper carModelMapper;
	private final FileService fileService;
	
	public Map<String, Object> getCarModelList(int page){
		
		final int pageSize = 10;
		
		int carModelCount = carModelMapper.carModelCount();
		
		int offsetNum = (page - 1) * pageSize;
		
		RowBounds rowBound = new RowBounds(offsetNum, pageSize);
		
		List<CarModelDTO> returnList = carModelMapper.getCarModelList(rowBound);
		
		if(!returnList.isEmpty()) {
			for(CarModelDTO carModel : returnList) {
				carModel.setImgURL("http://localhost/uploads/carModel/" + carModel.getImgURL());
			}
		}
		
		Map<String, Integer> pageInfo = pageInfoUtil.getPageInfo(page, carModelCount, pageSize);
		
		
		Map<String, Object> viewInfo = new HashMap();
		
		viewInfo.put("carModelList", returnList);
		viewInfo.put("pageInfo", pageInfo);
		
		return viewInfo;
	}
	
	

	@Override
	public void setCarModel(CarModelDTO carModel, MultipartFile file) {
		
		if(file == null) {
			throw new ImgFileNotFoundException("이미지 파일을 꼭 추가해주세요");
		}
		
		String check = carModelMapper.checkCarModel(carModel.getModelNo());
		
		if(check != null) {
			throw new AlreadyExistsException("이미 존재하는 차량 모델입니다.");
		}
		
		String filePath = fileService.storage(file, "carModel");
		
		
		log.info("filePath : ", filePath);
		
		CarModel model = CarModel.builder()
								 .carModel(carModel.getCarModel())
								 .rentPrice(carModel.getRentPrice())
								 .hourPrice(carModel.getHourPrice())
								 .chargeType(carModel.getChargeType())
								 .seatCount(carModel.getSeatCount())
								 .carImgURL(filePath)
								 .build();
		
		carModelMapper.setCarModel(model);
	}
	
	@Override
	public void updateCarModel(CarModelDTO carModel, MultipartFile file) {
		
		String prevImg = carModelMapper.checkCarModel(carModel.getModelNo());
		
		if(prevImg == null) {
			throw new ModelNotFoundException("존재하지 않는 차량 모델입니다.");
		}
		
		
		String filePath = prevImg;
		
		if(file != null) {
			filePath = fileService.storage(file, "carModel");
		}
		
		CarModel model = CarModel.builder()
								 .modelNo(carModel.getModelNo())
								 .carModel(carModel.getCarModel())
								 .rentPrice(carModel.getRentPrice())
								 .hourPrice(carModel.getHourPrice())
								 .chargeType(carModel.getChargeType())
								 .seatCount(carModel.getSeatCount())
								 .carImgURL(filePath)
								 .build();
		
		carModelMapper.updateCarModel(model);
		
		if(file != null) {
			fileService.deleteFile("carModel", prevImg);
		}
	}

	@Override
	public void removeCarModel(CarModelDTO carModel) {
		
		String prevImg = carModelMapper.checkCarModel(carModel.getModelNo());
		
		if(prevImg == null) {
			throw new ModelNotFoundException("존재하지 않는 차량 모델입니다.");
		}
		
		int rentCarCount = carModelMapper.getRentCarByModelNo(carModel.getModelNo());
		
		if(rentCarCount > 0) {
			throw new ModelNotFoundException("운영중인 렌트 차량이 있습니다. \n운영중인 치량을 먼저 삭제해주세요.");
		}
		
		carModelMapper.removeCarModel(carModel.getModelNo());
		
		fileService.deleteFile("carModel", prevImg);
	}

	@Override
	public List<CarModelDTO> getCarModelNameList() {
		
		int carModelCount = carModelMapper.carModelCount();
		
		if(carModelCount < 1) {
			throw new CarModelNotFoundException("차량 모델이 존재하지 않습니다.");
		}
		
		List<CarModelDTO> returnList = carModelMapper.getCarModelNameList();
		
		
		return returnList;
	}

	

}
