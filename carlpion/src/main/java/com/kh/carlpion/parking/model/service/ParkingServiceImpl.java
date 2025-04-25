package com.kh.carlpion.parking.model.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.carlpion.exception.exceptions.EmptyInputException;
import com.kh.carlpion.parking.model.dao.ParkingMapper;
import com.kh.carlpion.parking.model.dto.ParkingDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ParkingServiceImpl implements ParkingService {
	
	private final ParkingMapper parkingMapper;
	
	@Value("${parking.api.key}")
	private String apiKey;
	
	
	@Override
	public List<ParkingDTO> getParkingInfoByTitle(String search) {
		
		if("".equals(search)) {
			throw new EmptyInputException("빈 문자열로 조회 할 수 없습니다.");
		}
		
		
		return parkingMapper.getParkingInfoByTitle(search);
	}
	
	
	
	@Override
	public void parkingInfoSetting() {
		List<ParkingDTO> parkingIdList = parkingMapper.selectParkingId();
		
		for(int i = 0; i < 9; i++) {
			setParkingByAPI(i * 1000 + 1);
		}
	}
	
	private void setParkingByAPI(int boundsrow) {

		HttpURLConnection connection = null;

		try {
			StringBuilder sb = new StringBuilder();
			sb.append("http://openapi.seoul.go.kr:8088/")
			  .append(apiKey)
			  .append("/json/GetParkInfo/")
			  .append(boundsrow + "/")
			  .append(boundsrow + 999 + "/");

			URL url = new URL(sb.toString());
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			
			try (
				InputStreamReader isr = new InputStreamReader(connection.getInputStream(), "UTF-8");
				BufferedReader br = new BufferedReader(isr)
			) {
				String line = br.readLine(); 

				ObjectMapper objectMapper = new ObjectMapper();
				JsonNode root = objectMapper.readTree(line);
				JsonNode rows = root.path("GetParkInfo").path("row");

				List<ParkingDTO> parkingList = new ArrayList<>();
				Set<String> existingParkingIds = new HashSet<>();
				
				List<ParkingDTO> parkingIdList = parkingMapper.selectParkingId();
				
				if (parkingIdList != null) {
					for(ParkingDTO parking : parkingIdList) {
						existingParkingIds.add(parking.getParkingId());
					}
				}
				

				for (JsonNode node : rows) {
					if (node.path("PKLT_NM").asText().isEmpty() ||
						node.path("ADDR").asText().isEmpty() ||
						node.path("LAT").asText().equals("0.0") ||
						node.path("LOT").asText().equals("0.0")) {
						continue;
					}
					
					String parkingId = node.path("PKLT_CD").asText();
					
					if (existingParkingIds.contains(parkingId)) {
	                    continue;
	                }

					ParkingDTO parking = new ParkingDTO();
					parking.setParkingId(node.path("PKLT_CD").asText());
					parking.setParkingTitle(node.path("PKLT_NM").asText());
					parking.setParkingAddr(node.path("ADDR").asText());
					parking.setLat(node.path("LAT").asText());
					parking.setLot(node.path("LOT").asText());
					
					existingParkingIds.add(parkingId);
					
					log.info("{}", parking);

					parkingList.add(parking);
				}

				parkingMapper.parkingInfoSetting(parkingList);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// HttpURLConnection 연결 해제
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	


	
}
