package com.kh.carlpion.admin.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class PageInfoUtil {
	
	public Map<String, Integer> getPageInfo(int page, int carModelCount, int pageSize){
		Map<String, Integer> pageInfo = new HashMap<>();
		
		int totalPages = (int) Math.ceil((double) carModelCount / pageSize);


	    int pageRange = 10;
	    
	    int startPage = ((page - 1) / pageRange) * pageRange + 1;
	    
	    int endPage = Math.min(startPage + pageRange - 1, totalPages);

	    int prevPage = page > 1 ? page - 1 : 1;
	    int nextPage = page < totalPages ? page + 1 : totalPages;
	    
	    pageInfo.put("totalPages", totalPages);   
	    pageInfo.put("currentPage", page);         
	    pageInfo.put("startPage", startPage);      
	    pageInfo.put("endPage", endPage);          
	    pageInfo.put("prevPage", prevPage);        
	    pageInfo.put("nextPage", nextPage);        
	    
		return pageInfo;
	}
}
