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

import com.kh.carlpion.file.service.FileService;
import com.kh.carlpion.point.model.dto.LikeDTO;
import com.kh.carlpion.point.model.service.PointService;
import com.kh.carlpion.review.model.dto.ReviewDTO;
import com.kh.carlpion.review.model.service.ReviewService;
import com.kh.carlpion.utill.service.ResponseDataService;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/reviews")
public class ReviewController {

	private final ReviewService reviewService;
	private final ResponseDataService dataService;
	private final FileService fileService;
	private final PointService pointService;
	
	@PostMapping
	public ResponseEntity<?> save(ReviewDTO reviewDTO, @RequestParam(name = "file", required = false) List<MultipartFile> files) {
		fileService.filesCheck(files);
		reviewService.save(reviewDTO, files);
		return ResponseEntity.status(HttpStatus.CREATED).body(dataService.responseData("201", "CREATED", null));
	}
	
	@PostMapping("/{id}")
	public ResponseEntity<?> like(@PathVariable(name = "id") Long reviewNo) {
		LikeDTO likeDTO = new LikeDTO();
		likeDTO.setReviewNo(reviewNo);
		pointService.saveReviewLike(likeDTO);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping
	public ResponseEntity<?> findAll(@RequestParam(name = "page", defaultValue = "1") int pageNo) {
		return ResponseEntity.status(HttpStatus.OK).body(dataService.responseData("200", "OK", reviewService.findAll(pageNo)));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable(name = "id") @Min(value = 1) Long reviewNo) {		
		ReviewDTO reviewDTO = reviewService.findById(reviewNo);
		return ResponseEntity.status(HttpStatus.OK).body(dataService.responseData("200", "OK", reviewDTO));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateById(@PathVariable(name = "id") Long reviewNo, ReviewDTO reviewDTO, 
																			@RequestParam(name = "file", required = false) List<MultipartFile> files) {
		fileService.filesCheck(files);
		reviewDTO.setReviewNo(reviewNo);
		return ResponseEntity.status(HttpStatus.OK).body(dataService.responseData("200", "OK", reviewService.updateById(reviewDTO, files)));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> softDeleteById(@PathVariable(name = "id") Long reviewNo) {
		reviewService.softDeleteById(reviewNo);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(dataService.responseData("204", "NO_CONTENT", null));
	}
}
