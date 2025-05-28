package com.kh.carlpion.notice.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.carlpion.auth.model.service.AuthService;
import com.kh.carlpion.exception.exceptions.NotFindException;
import com.kh.carlpion.exception.exceptions.UnauthorizedException;
import com.kh.carlpion.file.service.FileDynamicService;
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
	private final FileDynamicService fileDynamicService;
	
	private static final String BOARD_TYPE = "notice";

	@Override
	@Transactional
	public void save(NoticeDTO noticeDTO, List<MultipartFile> files) {
		/*사용자 인증 구간*/
		Long userNo = authService.getUserDetails().getUserNo();
		
		NoticeVO requestData = NoticeVO.builder()
									   .userNo(userNo)
									   .modifierNo(userNo)
									   .title(noticeDTO.getTitle())
									   .content(noticeDTO.getContent())
									   .build();
		noticeMapper.save(requestData);
		Long noticeNo = requestData.getNoticeNo();
		
		if(files != null && !files.isEmpty()) {					
			for(MultipartFile file : files) {	
				fileDynamicService.saveFiles(file, noticeNo, BOARD_TYPE);
			}
		}	
	}

	@Override
	public Map<String, Object> findAll(int pageNo) {
		int pageLimit = 10;
		int btnLimit = 10;	
		int totalCount = noticeMapper.findTotalCount(pageNo);
		int maxPage = (int)Math.ceil((double) totalCount / pageLimit);	
		int startBtn = (pageNo - 1) / btnLimit * btnLimit + 1;
		int endBtn = startBtn + btnLimit - 1;
		
		if(pageNo > maxPage && maxPage > 0) {
			pageNo = maxPage;
		}
		
		if(maxPage == 0) {
			pageNo = 1;
		}

		if(endBtn > maxPage) {
			endBtn = maxPage;
		}

		RowBounds rowBounds = new RowBounds((pageNo - 1) * pageLimit, pageLimit);		
		List<NoticeDTO> list = noticeMapper.findAll(rowBounds);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("totalCount", totalCount);
		map.put("pageNo", pageNo);
		map.put("pageLimit", pageLimit);
		map.put("btnLimit", btnLimit);
		map.put("maxPage", maxPage);
		map.put("startBtn", startBtn);
		map.put("endBtn", endBtn);
		return map;		
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
		Long userNo = authService.getUserDetails().getUserNo();
		checkedOwnerByUser(noticeNo);
		
		boolean containsFiles = files != null && files.stream().anyMatch(file -> file != null && !file.isEmpty());
		
		if(containsFiles) {
			fileDynamicService.deleteFiles(noticeNo, BOARD_TYPE);
			
			for(MultipartFile file : files) {
				fileDynamicService.saveFiles(file, noticeNo, BOARD_TYPE);
			}
		} else { 
			fileDynamicService.deleteFiles(noticeNo, BOARD_TYPE);
		}		
		noticeDTO.setModifierNo(userNo);
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

		boolean isAdmin = SecurityContextHolder.getContext().getAuthentication()
		        .getAuthorities().stream()
		        .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
		
		
		if (!isAdmin && (findUserNo == null || !authUserNo.equals(findUserNo))) {
	        throw new UnauthorizedException("수정/삭제할 권한이 없습니다.");
	    }
	}
}
