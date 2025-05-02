package com.kh.carlpion.rental.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortOneTokenRequest {
	@JsonProperty("imp_key")
    private String imp_key;
	
	@JsonProperty("imp_secret")
    private String imp_secret;
}