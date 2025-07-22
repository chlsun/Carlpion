package com.kh.carlpion.util.model.service;

import org.springframework.stereotype.Service;

import com.kh.carlpion.util.model.dto.ResponseData;


@Service
public class ResponseDataServiceImpl implements ResponseDataService {

	@Override
	public ResponseData responseDataBuilder(String message, String code, Object item) {
		
		ResponseData responseData = ResponseData.builder()
												.message(message)
												.code(code)
												.item(item)
												.build();
		
		return responseData;
	}

}
