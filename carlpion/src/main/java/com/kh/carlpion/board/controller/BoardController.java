package com.kh.carlpion.board.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.carlpion.board.dto.BoardDTO;
import com.kh.carlpion.board.service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("board")
public class BoardController {
	
	private final BoardService boardService;

	@GetMapping("/review")
	public ResponseEntity<?> getReviewBoard(){
		
		List<BoardDTO> boardList = boardService.getReviewBoard();
	
		return ResponseEntity.status(HttpStatus.OK).body(boardList);
	}
	
	@GetMapping("/report")
	public ResponseEntity<?> getReportBoard(){
		
		List<BoardDTO> boardList = boardService.getReportBoard();
	
		return ResponseEntity.status(HttpStatus.OK).body(boardList);
	}
	
	@GetMapping("/notice")
	public ResponseEntity<?> getNoticeBoard(){
		
		List<BoardDTO> boardList = boardService.getNoticeBoard();
	
		return ResponseEntity.status(HttpStatus.OK).body(boardList);
	}
}
