package com.kh.carlpion.notice.controller;

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
	
	private static final int MAX_FILE_COUNT = 5;
	
	@PostMapping
	public ResponseEntity<?> save(NoticeDTO noticeDTO, @RequestParam(name = "file", required = false) List<MultipartFile> files) {
		
		if(files != null && MAX_FILE_COUNT < files.size()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("파일은 최대 " + MAX_FILE_COUNT + "개까지 첨부할 수 있습니다.");
		}
		
		noticeService.save(noticeDTO, files);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping
	public ResponseEntity<Map<String, Object>> findAll(@RequestParam(name = "page", defaultValue = "0") int pageNo) {		
		return ResponseEntity.ok(noticeService.findAll(pageNo));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<NoticeDTO> findById(@PathVariable(name = "id") @Min(value = 1) Long noticeNo) {		
		return ResponseEntity.ok(noticeService.findById(noticeNo));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateById(@PathVariable(name = "id") Long noticeNo, NoticeDTO noticeDTO, @RequestParam(name = "file", required = false) List<MultipartFile> files) {

		if(files != null && MAX_FILE_COUNT < files.size()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("파일은 최대 " + MAX_FILE_COUNT + "개까지 첨부할 수 있습니다.");
		}
		
		noticeDTO.setNoticeNo(noticeNo);		
		return ResponseEntity.ok(noticeService.updateById(noticeDTO, files));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> softDeleteById(@PathVariable(name = "id") Long noticeNo) {
		noticeService.softDeleteById(noticeNo);
		return ResponseEntity.noContent().build();
	}	
}
