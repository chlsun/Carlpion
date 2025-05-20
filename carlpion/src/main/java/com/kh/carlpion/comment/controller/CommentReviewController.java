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
import com.kh.carlpion.comment.model.service.CommentDynamicService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/reviews/comments")
public class CommentReviewController {

	private final CommentDynamicService commentDynamicService;
	
	private static final String COMMENT_TYPE = "review";
	
	@PostMapping
	public ResponseEntity<?> saveComment(@RequestBody CommentDynamicDTO commentDynamicDTO) {
		commentDynamicDTO.setCommentType(COMMENT_TYPE);
		commentDynamicService.saveComment(commentDynamicDTO);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping
	public ResponseEntity<List<CommentDTO>> findAllComment(@RequestParam(name = "reviewNo") Long reviewNo) {
		return ResponseEntity.ok(commentDynamicService.findAllComment(reviewNo, COMMENT_TYPE));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> softDeleteCommentById(@PathVariable(name = "id") Long commentNo) {
		commentDynamicService.softDeleteCommentById(commentNo, COMMENT_TYPE);
		return ResponseEntity.noContent().build();
	}
}
