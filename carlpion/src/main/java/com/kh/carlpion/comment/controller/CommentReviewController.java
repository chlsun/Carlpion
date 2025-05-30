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
import com.kh.carlpion.utill.service.ResponseDataService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/reviews/comments")
public class CommentReviewController {

	private final CommentDynamicService commentDynamicService;
	private final ResponseDataService dataService;
	
	private static final String COMMENT_TYPE = "review";
	
	@PostMapping
	public ResponseEntity<?> saveComment(@RequestBody CommentDynamicDTO commentDynamicDTO) {
		commentDynamicDTO.setCommentType(COMMENT_TYPE);
		commentDynamicDTO.setBoardNo(commentDynamicDTO.getReviewNo());
		commentDynamicService.saveComment(commentDynamicDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(dataService.responseData("201", "CREATED", null));
	}
	
	@GetMapping
	public ResponseEntity<?> findAllComment(@RequestParam(name = "reviewNo") Long reviewNo) {
		List<CommentDTO> list = commentDynamicService.findAllComment(reviewNo, COMMENT_TYPE);
		return ResponseEntity.status(HttpStatus.OK).body(dataService.responseData("200", "OK", list));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> softDeleteCommentById(@PathVariable(name = "id") Long commentNo) {
		commentDynamicService.softDeleteCommentById(commentNo, COMMENT_TYPE);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(dataService.responseData("204", "NO_CONTENT", null));
	}
}
