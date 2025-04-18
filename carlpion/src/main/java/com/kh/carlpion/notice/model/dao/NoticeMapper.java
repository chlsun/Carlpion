package com.kh.carlpion.notice.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.carlpion.notice.model.dto.NoticeDTO;

@Mapper
public interface NoticeMapper {	
	
	void save(NoticeDTO noticeDTO);	
	
	List<NoticeDTO> findAll(RowBounds rowBounds);	
	
	NoticeDTO findById(Long noticeNo);	
	
	void updateById(NoticeDTO noticeDTO);	
	
	void deleteById(Long noticeNo);
}
