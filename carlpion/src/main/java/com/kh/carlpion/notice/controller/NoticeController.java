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

import com.kh.carlpion.notice.model.dto.NoticeDTO;
import com.kh.carlpion.notice.model.service.NoticeService;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/notice")
public class NoticeController {
	
	private final NoticeService noticeService;
	
	@PostMapping
	public ResponseEntity<?> save(NoticeDTO noticeDTO, @RequestParam(name = "file", required = false) MultipartFile file) {
		noticeService.save(noticeDTO, file);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping
	public ResponseEntity<List<NoticeDTO>> findAll(@RequestParam(name = "page", defaultValue = "0") int pageNo) {		
		return ResponseEntity.ok(noticeService.findAll(pageNo));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<NoticeDTO> findById(@PathVariable(name = "id") @Min(value = 1) Long noticeNo) {		
		return ResponseEntity.ok(noticeService.findById(noticeNo));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<NoticeDTO> updateById(@PathVariable(name = "id") Long noticeNo, NoticeDTO noticeDTO, @RequestParam(name = "file", required = false) MultipartFile file) {
		noticeDTO.setNoticeNo(noticeNo);		
		return ResponseEntity.ok(noticeService.updateById(noticeDTO, file));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable(name = "id") Long noticeNo) {
		noticeService.deleteById(noticeNo);
		return ResponseEntity.noContent().build();
	}	
}
