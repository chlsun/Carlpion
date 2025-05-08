package com.kh.carlpion.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.carlpion.board.dto.BoardDTO;

@Mapper
public interface BoardMapper {
	
	List<BoardDTO> getReviewBoard(RowBounds rowBounds);
	
	List<BoardDTO> getReportBoard(RowBounds rowBounds);
	
	List<BoardDTO> getNoticeBoard(RowBounds rowBounds);

}
