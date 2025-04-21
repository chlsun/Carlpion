package com.kh.carlpion.comment.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.carlpion.comment.model.dao.CommentNoticeMapper;
import com.kh.carlpion.comment.model.dto.CommentNoticeDTO;
import com.kh.carlpion.comment.model.vo.CommentNoticeVO;
import com.kh.carlpion.notice.model.service.NoticeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentNoticeServiceImpl implements CommentNoticeService {
	
	private final CommentNoticeMapper commentNoticeMapper;
	private final NoticeService noticeService;

	@Override
	public void saveNotice(CommentNoticeDTO commentNoticeDTO) {
		
		CommentNoticeVO requestData = CommentNoticeVO.builder()
													 .userNo(null)
													 .content(commentNoticeDTO.getContent())
													 .noticeNo(commentNoticeDTO.getNoticeNo())
													 .build();
		commentNoticeMapper.saveNotice(requestData);
	}

	@Override
	public List<CommentNoticeDTO> findAllNotice(Long noticeNo) {
		noticeService.findById(noticeNo);
		return commentNoticeMapper.findAllNotice(noticeNo);
	}

	@Override
	public void deleteNoticeById(Long commentNo) {
		commentNoticeMapper.deleteNoticeById(commentNo);
	}

}
