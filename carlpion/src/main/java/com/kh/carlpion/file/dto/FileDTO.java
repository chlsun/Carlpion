package com.kh.carlpion.file.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FileDTO {
	private String type;
	private String fileUrl;
	
	private Long noticeNo;
	private Long reportNo;
	private Long reviewNo;
}
