package com.kh.carlpion.point.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.carlpion.point.model.dto.PointHistoryDTO;
import com.kh.carlpion.point.model.vo.PointHistoryVO;
import com.kh.carlpion.point.model.vo.PointVO;

@Mapper
public interface PointMapper {

	void saveHistoryPoint(PointHistoryVO pointHistoryVO);
	
	List<PointHistoryDTO> findAll(Long userNo);
		
	void updateUserPoint(PointVO pointVO);
	
	void updateUserLevel(PointVO pointVO);
}
