package com.kh.carlpion.notice.model.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.auth.model.service.AuthService;
import com.kh.carlpion.exception.exceptions.NotFindException;
import com.kh.carlpion.exception.exceptions.UnauthorizedException;
import com.kh.carlpion.file.service.FileService;
import com.kh.carlpion.notice.model.dao.NoticeMapper;
import com.kh.carlpion.notice.model.dto.NoticeDTO;
import com.kh.carlpion.notice.model.vo.NoticeVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService {
	
	private final NoticeMapper noticeMapper;
	private final AuthService authService;
	private final FileService fileService;

	@Override
	@Transactional
	public void save(NoticeDTO noticeDTO, List<MultipartFile> files) {
		/*사용자 인증 구간*/
		Long userNo = authService.getUserDetails().getUserNo();
		
		NoticeVO requestData = NoticeVO.builder()
									   .userNo(userNo)
									   .title(noticeDTO.getTitle())
									   .content(noticeDTO.getContent())
									   .build();
		noticeMapper.save(requestData);
		
		if(files != null && !files.isEmpty()) {					
			for(MultipartFile file : files) {	
				
				if( !file.isEmpty()) {
					String filePath = fileService.storage(file);
					
					NoticeVO requestFileData = NoticeVO.builder()
													   .noticeNo(requestData.getNoticeNo())
													   .fileUrl(filePath)
													   .build();
					noticeMapper.saveFile(requestFileData);
				}
			}
		}	
//		log.info("save: {}", requestData);
	}

	@Override
	public List<NoticeDTO> findAll(int pageNo) {
		int pageSize = 10;
		RowBounds rowBounds = new RowBounds(pageNo * pageSize, pageSize);
		return noticeMapper.findAll(rowBounds);
	}

	@Override
	public NoticeDTO findById(Long noticeNo) {
		NoticeDTO noticeDTO = noticeMapper.findById(noticeNo);
		
		if(noticeDTO == null) {
			throw new NotFindException("해당 글을 찾을 수 없습니다.");
		}
		
		noticeMapper.updateCount(noticeNo);
		return noticeDTO;
	}

	@Override
	@Transactional
	public NoticeDTO updateById(NoticeDTO noticeDTO, List<MultipartFile> files) {
		Long noticeNo = noticeDTO.getNoticeNo();
		checkedOwnerByUser(noticeNo);
		
		if(files != null && !files.isEmpty() && files.stream().anyMatch(file -> !file.isEmpty())) {
			List<String> fileUrls = noticeMapper.findFileByAll(noticeNo);
			
			if(fileUrls != null) {
				for(String fileUrl : fileUrls) {
					fileService.deleteFile(fileUrl);
				}
			}
			noticeMapper.deleteFileById(noticeNo);
			
			for(MultipartFile file : files) {
				
				if( !file.isEmpty()) {
					String filePath = fileService.storage(file);
					
					NoticeVO requestFileData = NoticeVO.builder()
													   .noticeNo(noticeNo)
													   .fileUrl(filePath)
													   .build();
					noticeMapper.saveFile(requestFileData);
				}
			}
		}
		noticeMapper.updateById(noticeDTO);
		return noticeDTO;
	}

	@Override
	@Transactional
	public void softDeleteById(Long noticeNo) {
		checkedOwnerByUser(noticeNo);
		noticeMapper.softDeleteById(noticeNo);
	}
	
	/** 사용자 인증 */
	private void checkedOwnerByUser(Long noticeNo) {
		Long authUserNo = authService.getUserDetails().getUserNo();
		Long findUserNo = noticeMapper.findByUserNo(noticeNo);
		
		if(findUserNo == null || !authUserNo.equals(findUserNo)) {
			throw new UnauthorizedException("수정/삭제할 권한이 없습니다.");
		}
	}
}
