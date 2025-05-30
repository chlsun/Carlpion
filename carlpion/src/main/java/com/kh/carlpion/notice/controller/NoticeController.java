package com.kh.carlpion.notice.controller;

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
import com.kh.carlpion.notice.model.dto.NoticeDTO;
import com.kh.carlpion.notice.model.service.NoticeService;
import com.kh.carlpion.utill.service.ResponseDataService;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/notice")
public class NoticeController {
	
	private final NoticeService noticeService;
	private final ResponseDataService dataService;
	private final FileService fileService;
	
	@PostMapping
	public ResponseEntity<?> save(NoticeDTO noticeDTO, @RequestParam(name = "file", required = false) List<MultipartFile> files) {
		fileService.filesCheck(files);
		noticeService.save(noticeDTO, files);
		return ResponseEntity.status(HttpStatus.CREATED).body(dataService.responseData("201", "CREATED", null));
	}
	
	@GetMapping
	public ResponseEntity<?> findAll(@RequestParam(name = "page", defaultValue = "0") int pageNo) {		
		return ResponseEntity.status(HttpStatus.OK).body(dataService.responseData("200", "OK", noticeService.findAll(pageNo)));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable(name = "id") @Min(value = 1) Long noticeNo) {		
		NoticeDTO noticeDTO = noticeService.findById(noticeNo);
		return ResponseEntity.status(HttpStatus.OK).body(dataService.responseData("200", "OK", noticeDTO));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateById(@PathVariable(name = "id") Long noticeNo, NoticeDTO noticeDTO, 
																			@RequestParam(name = "file", required = false) List<MultipartFile> files) {
		fileService.filesCheck(files);
		noticeDTO.setNoticeNo(noticeNo);		
		return ResponseEntity.status(HttpStatus.OK).body(dataService.responseData("200", "OK", noticeService.updateById(noticeDTO, files)));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> softDeleteById(@PathVariable(name = "id") Long noticeNo) {
		noticeService.softDeleteById(noticeNo);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(dataService.responseData("204", "NO_CONTENT", null));
	}	
}
