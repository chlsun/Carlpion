package com.kh.carlpion.notice.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Builder
@Getter
@AllArgsConstructor
public class NoticeVO {
	private Long noticeNo;
	private Long userNo;
	private String title;
	private String content;
	private String nickName;
	private String fileUrl;
}
