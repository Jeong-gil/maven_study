package com.exam.controller.notice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.exam.controller.Controller;

public class WriteFormController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("WriteFormController......");
		
		// session 객체 참조 가져오기
		HttpSession session = request.getSession();
		
		// 로그인 여부 확인
		String id = (String) session.getAttribute("id");
		if (id == null) {
			return "redirect:/notice.do";
		}
		
		// 파라미터값  pageNum  가져오기
		String pageNum = request.getParameter("pageNum");
		
		// 뷰(jsp)에서 필요한 데이터를 request 영역객체에 저장
		request.setAttribute("pageNum", pageNum);
		
		return "center/writeForm";
	}

}
