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

import com.kh.carlpion.comment.model.dto.CommentReviewDTO;
import com.kh.carlpion.comment.model.service.CommentReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/reviews/comments")
public class CommentReviewController {

	private final CommentReviewService commentReviewService;
	
	@PostMapping
	public ResponseEntity<?> saveComment(CommentReviewDTO commentReviewDTO) {
		commentReviewService.saveComment(commentReviewDTO);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping
	public ResponseEntity<List<CommentReviewDTO>> findAllComment(@RequestParam(name = "reviewNo") Long reviewNo) {
		return ResponseEntity.ok(commentReviewService.findAllComment(reviewNo));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> softDeleteCommentById(@PathVariable(name = "id") Long commentNo) {
		commentReviewService.softDeleteCommentById(commentNo);
		return ResponseEntity.noContent().build();
	}
}
