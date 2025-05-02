package com.kh.carlpion.report.controller;

import java.util.List;
import java.util.Map;

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
	
	private static final int MAX_FILE_COUNT = 5;
	
	@PostMapping
	public ResponseEntity<?> save(ReportDTO reportDTO, @RequestParam(name = "file", required = false) List<MultipartFile> files) {

		if(files != null && MAX_FILE_COUNT < files.size()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("파일은 최대 " + MAX_FILE_COUNT + "개까지 첨부할 수 있습니다.");
		}
		
		reportService.save(reportDTO, files);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping
	public ResponseEntity<Map<String, Object>> findAll(@RequestParam(name = "page", defaultValue = "1") int pageNo) {
		return ResponseEntity.ok(reportService.findAll(pageNo));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ReportDTO> findById(@PathVariable(name = "id") @Min(value = 1) Long reportNo) {
		return ResponseEntity.ok(reportService.findById(reportNo));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateById(@PathVariable(name = "id") Long reportNo, ReportDTO reportDTO, @RequestParam(name = "file", required = false) List<MultipartFile> files) {

		if(files != null && MAX_FILE_COUNT < files.size()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("파일은 최대 " + MAX_FILE_COUNT + "개까지 첨부할 수 있습니다.");
		}
		
		reportDTO.setReportNo(reportNo);
		return ResponseEntity.ok(reportService.updateById(reportDTO, files));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> softDeleteById(@PathVariable(name = "id") Long reportNo) {
		reportService.softDeleteById(reportNo);
		return ResponseEntity.noContent().build();
	}
}
