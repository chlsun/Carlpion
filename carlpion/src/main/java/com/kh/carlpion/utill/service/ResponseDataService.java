package com.kh.carlpion.utill.service;

import org.springframework.stereotype.Service;

import com.kh.carlpion.utill.dto.DataType;

@Service
public class ResponseDataService {
	
	public DataType responseData(String code, String message, Object items) {
		DataType dataType = DataType.builder()
														.code(code)
														.message(message)
														.items(items)
														.build();
		return dataType;
	}
}
