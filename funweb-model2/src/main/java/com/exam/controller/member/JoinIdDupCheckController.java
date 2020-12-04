package com.exam.controller.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.exam.controller.Controller;
import com.exam.dao.MemberDao;

public class JoinIdDupCheckController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("JoinIdDupCheckController......");
		
		// 파라미터값 id 가져오기
		String id = request.getParameter("id");

		// DAO 객체 준비
		MemberDao memberDao = MemberDao.getInstance();

		// 중복아이디 여부 확인
		int count = memberDao.getCountById(id);
		
		// * 영역객체 4가지 (톰캣이 관리하는 Map 컬렉션)
		//  application : 웹프로그램 1개당 유지되는 영역객체
		//  session : 사용자 1명당 유지되는 영역객체
		//  request : 요청 한번마다 유지되는 영역객체
		//  page : jsp 페이지 한번 실행때만 유지되는 영역객체
		
		// request 영역객체에 View 화면에서 필요한 데이터를 저장
		request.setAttribute("id", id);
		request.setAttribute("count", count);
		
		// 디스패치 방식으로 jsp를 바로실행하면
		// 앞에서 사용한 request객체가 jsp까지 전달되므로
		// jsp화면에서 데이터를 사용할 수 있음
		return "member/joinIdDupCheck";
	}

}
