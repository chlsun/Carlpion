package com.kh.carlpion.review.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.review.model.dto.ReviewDTO;
import com.kh.carlpion.review.model.service.ReviewService;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/reviews")
public class ReviewController {

	private final ReviewService reviewService;
	
	@PostMapping
	public ResponseEntity<?> save(ReviewDTO reviewDTO, @RequestParam(name = "file", required = false) MultipartFile file) {
		reviewService.save(reviewDTO, file);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping
	public ResponseEntity<List<ReviewDTO>> findAll(@RequestParam(name = "page", defaultValue = "0") int pageNo) {
		return ResponseEntity.ok(reviewService.findAll(pageNo));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ReviewDTO> findById(@PathVariable(name = "id") @Min(value = 1) Long reviewNo) {
		return ResponseEntity.ok(reviewService.findById(reviewNo));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ReviewDTO> updateById(@PathVariable(name = "id") Long reviewNo, ReviewDTO reviewDTO, @RequestParam(name = "file", required = false) MultipartFile file) {
		reviewDTO.setReviewNo(reviewNo);
		return ResponseEntity.ok(reviewService.updateById(reviewDTO, file));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable(name = "id") Long reviewNo) {
		reviewService.deleteById(reviewNo);
		return ResponseEntity.noContent().build();
	}
}
