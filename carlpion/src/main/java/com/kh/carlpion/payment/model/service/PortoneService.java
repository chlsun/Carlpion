package com.kh.carlpion.payment.model.service;

import java.util.Map;

public interface PortoneService {

	String getAccessToken();
	
	void preparePayment(String merchantUid, int totalPrice);
	
	Map<String, Object> verifyPayment(String impUID, String token);
}
