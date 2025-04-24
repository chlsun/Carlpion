package com.kh.carlpion.comment.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.carlpion.auth.model.service.AuthService;
import com.kh.carlpion.comment.model.dao.CommentNoticeMapper;
import com.kh.carlpion.comment.model.dto.CommentNoticeDTO;
import com.kh.carlpion.comment.model.vo.CommentNoticeVO;
import com.kh.carlpion.exception.exceptions.UnauthorizedException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentNoticeServiceImpl implements CommentNoticeService {
	
	private final CommentNoticeMapper commentNoticeMapper;
	private final AuthService authService;

	@Override
	@Transactional
	public void saveComment(CommentNoticeDTO commentNoticeDTO) {
		Long userNo = authService.getUserDetails().getUserNo();
		
		CommentNoticeVO requestData = CommentNoticeVO.builder()
													 .userNo(userNo)
													 .content(commentNoticeDTO.getContent())
													 .noticeNo(commentNoticeDTO.getNoticeNo())
													 .build();
		commentNoticeMapper.saveComment(requestData);
	}

	@Override
	public List<CommentNoticeDTO> findAllComment(Long noticeNo) {
		return commentNoticeMapper.findAllComment(noticeNo);
	}

	@Override
	@Transactional
	public void softDeleteCommentById(Long commentNo) {
		checkedOwnerByUser(commentNo);
		commentNoticeMapper.softDeleteCommentById(commentNo);
	}

	/** 사용자 인증 */
	private void checkedOwnerByUser(Long commentNo) {
		Long authUserNo = authService.getUserDetails().getUserNo();
		Long findUserNo = commentNoticeMapper.findByUserNo(commentNo);

		if(findUserNo == null || !authUserNo.equals(findUserNo)) {
			throw new UnauthorizedException("삭제할 권한이 없습니다.");
		}
	}
}
