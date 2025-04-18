package com.kh.carlpion.comment.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.carlpion.comment.model.dto.CommentNoticeDTO;
import com.kh.carlpion.comment.model.service.CommentNoticeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/notice/comments")
public class CommentNoticeController {
	
	private final CommentNoticeService commentNoticeService;
	
	@PostMapping
	public ResponseEntity<?> saveNotice(CommentNoticeDTO commentNoticeDTO) {
		commentNoticeService.saveNotice(commentNoticeDTO);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping
	public ResponseEntity<List<CommentNoticeDTO>> findAllNotice(@RequestParam(name = "noticeNo") Long noticeNo) {
		return ResponseEntity.ok(commentNoticeService.findAllNotice(noticeNo));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteNoticeById(@PathVariable(name = "id") Long commentNo) {
		commentNoticeService.deleteNoticeById(commentNo);
		return ResponseEntity.noContent().build();
	}
}
