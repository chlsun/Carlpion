package com.kh.carlpion.notice.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.carlpion.notice.model.dto.NoticeDTO;
import com.kh.carlpion.notice.model.vo.NoticeVO;

@Mapper
public interface NoticeMapper {	
	
	void save(NoticeVO noticeVO);	
	
	List<NoticeDTO> findAll(RowBounds rowBounds);	
	
	NoticeDTO findById(Long noticeNo);	
	
	void updateById(NoticeDTO noticeDTO);	
	
	void softDeleteById(Long noticeNo);
	
	void saveFile(NoticeVO noticeVO);
	
	List<String> findFileByAll(Long noticeNo);
	
	void deleteFileById(Long noticeNo);
	
	Long findByUserNo(Long noticeNo);
	
	void updateCount(Long noticeNo);
	
	int findTotalCount(int pageNo);
}
