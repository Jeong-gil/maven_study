package com.exam.controller.member;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.exam.controller.Controller;
import com.exam.dao.MemberDao;
import com.exam.vo.MemberVo;

public class MemberJoinProController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("MemberJoinProController......");
		
		// VO 객체 준비
		MemberVo vo = new MemberVo();
		
		// VO객체에 파라미터값 저장
		vo.setId(request.getParameter("id"));
		vo.setPasswd(request.getParameter("passwd"));
		vo.setName(request.getParameter("name"));
		vo.setEmail(request.getParameter("email"));
		vo.setAddress(request.getParameter("address"));
		vo.setTel(request.getParameter("tel"));
		vo.setAge(Integer.parseInt(request.getParameter("age")));
		vo.setGender(request.getParameter("gender"));
		
		// 가입날짜 생성해서 넣기
		vo.setRegDate(new Timestamp(System.currentTimeMillis()));

		// DAO 객체 준비
		MemberDao memberDao = MemberDao.getInstance();
		// 회원가입 메서드 호출
		memberDao.addMember(vo);
		
		// 로그인 화면 요청경로로 리다이렉트시키기 위해서
		// 리다이렉트 정보를 리턴함
		return "redirect:/memberLogin.do";
	}

}
