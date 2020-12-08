package com.exam.controller.notice;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.exam.controller.Controller;
import com.exam.dao.JdbcUtils;
import com.exam.dao.NoticeDao;
import com.exam.vo.NoticeVo;

public class WriteProController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("WriteProController......");
		
		// session 객체 참조 가져오기
		HttpSession session = request.getSession();
		
		// 로그인 여부 확인
		String id = (String) session.getAttribute("id");
		if (id == null) {
			return "redirect:/notice.do";
		}
		
		// pageNum 파라미터 가져오기
		String pageNum = request.getParameter("pageNum");
		
		// NoticeVo 객체 준비
		NoticeVo noticeVo = new NoticeVo();
		
		// 파라미터값을 가져와서 NoticeVo 객체에 저장
		noticeVo.setId(request.getParameter("id"));
		noticeVo.setSubject(request.getParameter("subject"));
		noticeVo.setContent(request.getParameter("content"));
		
		//ip  regDate  readcount  값 저장
		noticeVo.setIp(request.getRemoteAddr());
		noticeVo.setRegDate(new Timestamp(System.currentTimeMillis()));
		noticeVo.setReadcount(0);  // 조회수

		//re_ref  re_lev  re_seq
		int num = JdbcUtils.getNextNum("notice"); // insert될 글번호 가져오기
		noticeVo.setReRef(num); // 주글일때는 글번호가 그룹번호가 됨
		noticeVo.setReLev(0); // 주글일때는 들여쓰기 레벨이 0 (들여쓰기 없음)
		noticeVo.setReSeq(0); // 주글일때는 글그룹 내에서 순번이 0 (첫번째)

		//DAO 객체 준비
		NoticeDao noticeDao = NoticeDao.getInstance();

		//주글 등록하기
		noticeDao.addNotice(noticeVo);

		// 글내용 상세보기 화면 content.jsp로 이동
		return "redirect:/content.do?num=" + num + "&pageNum=" + pageNum;
	}

}
