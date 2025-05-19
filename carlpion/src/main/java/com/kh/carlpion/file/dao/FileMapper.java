package com.kh.carlpion.file.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.carlpion.file.vo.FileVO;

@Mapper
public interface FileMapper {
	
	void saveFile(FileVO fileVO);
	
	void deleteFileById(FileVO fileVO);
	
	List<String> findFileByAll(FileVO fileVO);
}
