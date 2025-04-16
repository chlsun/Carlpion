package com.kh.carlpion.notice.model.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.file.service.FileService;
import com.kh.carlpion.notice.model.dao.NoticeMapper;
import com.kh.carlpion.notice.model.dto.NoticeDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService {
	
	private final NoticeMapper noticeMapper;
	private final FileService fileService;

	@Override
	public void save(NoticeDTO noticeDTO, MultipartFile file) {
		
	}

	@Override
	public List<NoticeDTO> findAll(int pageNo) {
		int pageSize = 10;
		RowBounds rowBounds = new RowBounds(pageNo * pageSize, pageSize);
		return noticeMapper.findAll(rowBounds);
	}

	@Override
	public NoticeDTO findById(Long noticeNo) {
		return null;
	}

	@Override
	public NoticeDTO updateById(NoticeDTO noticeDTO, MultipartFile file) {
		return null;
	}

	@Override
	public void deleteById(Long noticeNo) {
		
	}
}
