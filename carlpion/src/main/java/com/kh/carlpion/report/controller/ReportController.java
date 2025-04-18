package com.kh.carlpion.report.controller;

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

import com.kh.carlpion.report.model.dto.ReportDTO;
import com.kh.carlpion.report.model.service.ReportService;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/reports")
public class ReportController {
	
	private final ReportService reportService;
	
	@PostMapping
	public ResponseEntity<?> save(ReportDTO reportDTO, @RequestParam(name = "file", required = false) MultipartFile file) {
		reportService.save(reportDTO, file);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping
	public ResponseEntity<List<ReportDTO>> findAll(@RequestParam(name = "page", defaultValue = "0") int pageNo) {
		return ResponseEntity.ok(reportService.findAll(pageNo));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ReportDTO> findById(@PathVariable(name = "id") @Min(value = 1) Long reportNo) {
		return ResponseEntity.ok(reportService.findById(reportNo));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ReportDTO> updateById(@PathVariable(name = "id") Long reportNo, ReportDTO reportDTO, @RequestParam(name = "file", required = false) MultipartFile file) {
		reportDTO.setReportNo(reportNo);
		return ResponseEntity.ok(reportService.updateById(reportDTO, file));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable(name = "id") Long reportNo) {
		reportService.deleteById(reportNo);
		return ResponseEntity.noContent().build();
	}
}
