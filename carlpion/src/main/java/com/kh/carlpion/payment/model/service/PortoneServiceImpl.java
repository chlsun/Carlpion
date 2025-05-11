package com.kh.carlpion.payment.model.service;


import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.carlpion.rental.model.dto.PortOneTokenRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PortoneServiceImpl implements PortoneService {

	private final RestTemplate restTemplate = new RestTemplate();
	@Value("${portone.secret}")
	private String secretKey;
	
	@Value("${portone.key}")
	private String restAPIKey;
	
	
	public String getAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        PortOneTokenRequest tokenRequest = new PortOneTokenRequest(restAPIKey, secretKey);
        
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
			json = objectMapper.writeValueAsString(tokenRequest);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        
        HttpEntity<?> request = new HttpEntity<>(json, headers);
        
        ResponseEntity<Map> response = restTemplate.postForEntity("https://api.iamport.kr/users/getToken", request, Map.class);
        
        return (String) ((Map) response.getBody().get("response")).get("access_token");
    }

	
	@Override
	public void preparePayment(String merchantUid, int totalPrice) {
		String token = getAccessToken();
		
		

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = Map.of(
            "merchant_uid", merchantUid,
            "amount", totalPrice
        );

        HttpEntity<?> request = new HttpEntity<>(body, headers);
        restTemplate.postForEntity("https://api.iamport.kr/payments/prepare", request, String.class);
	}
	
	public Map<String, Object> verifyPayment(String impUID, String token) {
        HttpHeaders headers = new HttpHeaders();
        
        
        log.info("여기여기여기여기 : {}", impUID);
        
        headers.set("Authorization", token);

        HttpEntity<?> request = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(
            "https://api.iamport.kr/payments/" + impUID,
            HttpMethod.GET,
            request,
            Map.class
        );

        return (Map<String, Object>) response.getBody().get("response");
    }

	
	

}
