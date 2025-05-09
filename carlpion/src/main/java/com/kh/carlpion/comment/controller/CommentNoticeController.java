package com.kh.carlpion.comment.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.carlpion.comment.model.dto.CommentDTO;
import com.kh.carlpion.comment.model.dto.CommentDynamicDTO;
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
	public ResponseEntity<?> saveComment(@RequestBody CommentDynamicDTO commentDynamicDTO) {
		log.info("{}",commentDynamicDTO);
		commentNoticeService.saveComment(commentDynamicDTO);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping
	public ResponseEntity<List<CommentDTO>> findAllComment(@RequestParam(name = "noticeNo") Long noticeNo) {
		log.info("{}",noticeNo);
		return ResponseEntity.ok(commentNoticeService.findAllComment(noticeNo));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> softDeleteCommentById(@PathVariable(name = "id") Long commentNo) {
		commentNoticeService.softDeleteCommentById(commentNo);
		return ResponseEntity.noContent().build();
	}
}
