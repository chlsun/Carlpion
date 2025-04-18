package com.kh.carlpion.report.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.carlpion.report.model.dto.ReportDTO;
import com.kh.carlpion.report.model.vo.ReportVO;

@Mapper
public interface ReportMapper {
	
	void save(ReportVO reportVO);
	
	List<ReportDTO> findAll(RowBounds rowBounds);
	
	ReportDTO findById(Long reportNo);
	
	void updateById(ReportDTO reportDTO);
			
	void deleteById(Long reportNo);
}
