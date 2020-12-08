package com.exam.controller.filenotice;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.exam.controller.Controller;
import com.exam.dao.AttachDao;
import com.exam.dao.NoticeDao;
import com.exam.vo.AttachVo;
import com.exam.vo.NoticeVo;

public class FileContentController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("FileContentController......");
		
		// 파라미터값  num  pageNum  가져오기
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		
		// DAO 객체 준비
		NoticeDao noticeDao = NoticeDao.getInstance();
		AttachDao attachDao = AttachDao.getInstance();

		// 조회수 1 증가
		noticeDao.updateReadcount(num);

		// 글 한개 가져오기
		NoticeVo noticeVo = noticeDao.getNoticeByNum(num);
		// 첨부파일 리스트 정보 가져오기
		List<AttachVo> attachList = attachDao.getAttachesByNoNum(num);


		// 글 내용에서 "\n" 줄바꿈 문자열을 "<br>"로 교체하기
		String content = "";
		if (noticeVo.getContent() != null) {
			content = noticeVo.getContent().replace("\n", "<br>");
			noticeVo.setContent(content);
		}
		
		// 뷰(jsp)에서 필요한 데이터를 request 영역객체에 저장
		request.setAttribute("noticeVo", noticeVo);
		request.setAttribute("attachList", attachList);
		request.setAttribute("pageNum", pageNum);
		
		return "center/fileContent";
	}

}
