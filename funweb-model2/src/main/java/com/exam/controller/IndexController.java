package com.exam.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("IndexController......");
		
		// DB처리를 위해 Model역할인 Dao를 사용할수 있음
		
		//return "redirect:/joinForm.do"; // 리다이렉트 정보
		
		//return "/WEB-INF/views/index.jsp";
		return "index";  // 실행할 jsp페이지 경로이름 정보 (디스패치 방식)
		
		
	}

}
