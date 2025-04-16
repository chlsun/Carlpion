package com.kh.carlpion.comment.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.carlpion.comment.model.dto.CommentNoticeDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentNoticeServiceImpl implements CommentNoticeService {

	@Override
	public void saveNotice(CommentNoticeDTO commentNoticeDTO) {

	}

	@Override
	public List<CommentNoticeDTO> findAllNotice(Long noticeNo) {
		return null;
	}

	@Override
	public void deleteNoticeById(Long commentNo) {

	}

}
