package com.kh.carlpion.util.model.service;

import com.kh.carlpion.util.model.dto.ResponseData;

public interface ResponseDataService {
	
	ResponseData responseDataBuilder(String message, String code, Object data);

}
