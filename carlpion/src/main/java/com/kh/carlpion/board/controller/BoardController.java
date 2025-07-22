package com.kh.carlpion.board.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.carlpion.board.dto.BoardDTO;
import com.kh.carlpion.board.service.BoardService;
import com.kh.carlpion.util.model.dto.ResponseData;
import com.kh.carlpion.util.model.service.ResponseDataService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("board")
public class BoardController {
	
	private final BoardService boardService;
	private final ResponseDataService responseDataService;

	@GetMapping("/review")
	public ResponseEntity<ResponseData> getReviewBoard(){
		
		List<BoardDTO> boardList = boardService.getReviewBoard();
		
		ResponseData responseData = responseDataService.responseDataBuilder("조회 성공", "200", boardList);
	
		return ResponseEntity.ok(responseData);
	}
	
	@GetMapping("/report")
	public ResponseEntity<ResponseData> getReportBoard(){
		
		List<BoardDTO> boardList = boardService.getReportBoard();
	
		ResponseData responseData = responseDataService.responseDataBuilder("조회 성공", "200", boardList);
		
		return ResponseEntity.ok(responseData);
	}
	
	@GetMapping("/notice")
	public ResponseEntity<ResponseData> getNoticeBoard(){
		
		List<BoardDTO> boardList = boardService.getNoticeBoard();
	
		ResponseData responseData = responseDataService.responseDataBuilder("조회 성공", "200", boardList);
		
		return ResponseEntity.ok(responseData);
	}
}