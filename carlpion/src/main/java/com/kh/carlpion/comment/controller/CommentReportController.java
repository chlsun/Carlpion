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

import com.kh.carlpion.comment.model.dto.CommentReportDTO;
import com.kh.carlpion.comment.model.service.CommentReportService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/reports/comments")
public class CommentReportController {
	
	private final CommentReportService commentReportService;
	
	@PostMapping
	public ResponseEntity<?> save(CommentReportDTO commentReportDTO) {
		commentReportService.save(commentReportDTO);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping
	public ResponseEntity<List<CommentReportDTO>> findAll(@RequestParam(name = "noticeNo") Long reportNo) {
		return ResponseEntity.ok(commentReportService.findAll(reportNo));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable(name = "id") Long commentNo) {
		commentReportService.deleteById(commentNo);
		return ResponseEntity.noContent().build();
	}
}
