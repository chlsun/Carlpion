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

import com.kh.carlpion.file.service.FileService;
import com.kh.carlpion.report.model.dto.ReportDTO;
import com.kh.carlpion.report.model.service.ReportService;
import com.kh.carlpion.utill.service.ResponseDataService;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/reports")
public class ReportController {
	
	private final ReportService reportService;
	private final ResponseDataService dataService;
	private final FileService fileService;
	
	@PostMapping
	public ResponseEntity<?> save(ReportDTO reportDTO, @RequestParam(name = "file", required = false) List<MultipartFile> files) {
		fileService.filesCheck(files);
		reportService.save(reportDTO, files);
		return ResponseEntity.status(HttpStatus.CREATED).body(dataService.responseData("201", "CREATED", null));
	}
	
	@GetMapping
	public ResponseEntity<?> findAll(@RequestParam(name = "page", defaultValue = "1") int pageNo) {
		return ResponseEntity.status(HttpStatus.OK).body(dataService.responseData("200", "OK",reportService.findAll(pageNo)));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable(name = "id") @Min(value = 1) Long reportNo) {
		ReportDTO reportDTO = reportService.findById(reportNo);
		return ResponseEntity.status(HttpStatus.OK).body(dataService.responseData("200", "OK", reportDTO));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateById(@PathVariable(name = "id") Long reportNo, ReportDTO reportDTO, 
																			@RequestParam(name = "file", required = false) List<MultipartFile> files) {
		fileService.filesCheck(files);
		reportDTO.setReportNo(reportNo);
		return ResponseEntity.status(HttpStatus.OK).body(dataService.responseData("200", "OK", reportService.updateById(reportDTO, files)));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> softDeleteById(@PathVariable(name = "id") Long reportNo) {
		reportService.softDeleteById(reportNo);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(dataService.responseData("204", "NO_CONTENT", null));
	}
}
