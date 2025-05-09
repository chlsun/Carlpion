package com.kh.carlpion.board.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.kh.carlpion.board.dao.BoardMapper;
import com.kh.carlpion.board.dto.BoardDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
	
	private final BoardMapper boardMapper;

	@Override
	public List<BoardDTO> getReviewBoard() {
		
		RowBounds rowBounds = new RowBounds(0, 8);
		
		List<BoardDTO> boardList = boardMapper.getReviewBoard(rowBounds);
		
		for(BoardDTO board : boardList) {
			board.setCategory("리뷰");
		}
		
		return boardList;
	}

	@Override
	public List<BoardDTO> getReportBoard() {
		RowBounds rowBounds = new RowBounds(0, 8);
		
		List<BoardDTO> boardList = boardMapper.getReportBoard(rowBounds);
		
		for(BoardDTO board : boardList) {
			board.setCategory("신고/문의");
		}
		
		return boardList;
	}

	@Override
	public List<BoardDTO> getNoticeBoard() {
		RowBounds rowBounds = new RowBounds(0, 8);
		
		List<BoardDTO> boardList = boardMapper.getNoticeBoard(rowBounds);
		
		for(BoardDTO board : boardList) {
			board.setCategory("공지");
		}
		
		return boardList;
	}
	
	

}
