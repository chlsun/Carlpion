package com.kh.carlpion.file.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Builder
@Getter
public class FileVO {
	private String type;
	private String fileUrl;	
	private Long boardNo;
}
