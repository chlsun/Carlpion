package com.kh.carlpion.report.model.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.file.service.FileService;
import com.kh.carlpion.report.model.dao.ReportMapper;
import com.kh.carlpion.report.model.dto.ReportDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {
	
	private final ReportMapper reportMapper;
	private final FileService fileService;

	@Override
	public void save(ReportDTO reportDTO, MultipartFile file) {

	}

	@Override
	public List<ReportDTO> findAll(int pageNo) {
		int pageSize = 10;
		RowBounds rowBounds = new RowBounds(pageNo * pageSize, pageSize);
		return reportMapper.findAll(rowBounds);
	}

	@Override
	public ReportDTO findById(Long reportNo) {
		return null;
	}

	@Override
	public ReportDTO updateById(ReportDTO reportDTO, MultipartFile file) {
		return null;
	}

	@Override
	public void deleteById(Long reportNo) {

	}

}
