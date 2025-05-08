package com.kh.carlpion.board.service;

import java.util.List;

import com.kh.carlpion.board.dto.BoardDTO;

public interface BoardService {
	
	List<BoardDTO> getReviewBoard();
	
	List<BoardDTO> getReportBoard();
	
	List<BoardDTO> getNoticeBoard();

}
