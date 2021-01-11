package com.exam.controller.comment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.exam.dao.CommentDao;
import com.exam.dao.MySqlDao;
import com.exam.vo.CommentVo;
import com.exam.vo.Criteria;
import com.google.gson.Gson;

import lombok.extern.java.Log;

@Log
@WebServlet(urlPatterns = "/comment/*", loadOnStartup = 1)
public class CommentRestServlet extends HttpServlet {
	
	private Gson gson = new Gson();

	private void sendResponse(String strJson, HttpServletResponse response) throws ServletException, IOException {
		// 클라이언트에게 응답 주기
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(strJson);
//		out.flush();
		out.close();
	}
	
	// SQL SELECT문 요청 처리
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("doGet 호출됨");
		
		CommentDao commentDao = CommentDao.getInstance();
		
		String category = request.getParameter("category");
		String strJson = "";
		if (category.equals("one")) {
			int cno = Integer.parseInt(request.getParameter("cno"));
			
			CommentVo commentVo = commentDao.getCommentByCno(cno);
			
			Map<String, Object> map = new HashMap<>();
			map.put("commentVo", commentVo);
			map.put("isSuccess", true);
			
			strJson = gson.toJson(map);
		} else if (category.equals("list")) {
			int nno = Integer.parseInt(request.getParameter("nno")); // 게시판 글번호
			int pageNum = Integer.parseInt(request.getParameter("pageNum")); // 댓글 페이지번호
			int numOfRows = Integer.parseInt(request.getParameter("numOfRows")); // 한개의 댓글페이지당 댓글갯수
			
//			List<CommentVo> commentList = commentDao.getComments(nno);
			
			Criteria cri = new Criteria(pageNum, numOfRows);
			
			List<CommentVo> commentList = commentDao.getCommentsWithPaging(nno, cri);
			
			int totalCount = commentDao.getTotalCountByNno(nno); // 총 댓글 갯수
			
			Map<String, Object> map = new HashMap<>();
			map.put("commentList", commentList);
			map.put("isSuccess", true);
			map.put("totalCount", totalCount);
			
			strJson = gson.toJson(map);
		}
		
		// 클라이언트에게 응답 주기
		sendResponse(strJson, response);
	} // doGet

	// SQL INSERT문 요청 처리
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("doPost 호출됨");
		
		// 한글처리
		request.setCharacterEncoding("utf-8");
		
		// 로그인 확인 세션점검
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		if (id == null) {
			// 클라이언트에게 응답을 줄 JSON 데이터 준비
			Map<String, Object> map = new HashMap<>();
			map.put("isSuccess", false);
			map.put("description", "not login");
			
			String strJson = gson.toJson(map);
			log.info("strJson : " + strJson);
			
			// 클라이언트에게 응답 주기
			sendResponse(strJson, response);
			return;
		}
		
		
		MySqlDao mySqlDao = MySqlDao.getInstance();
		CommentDao commentDao = CommentDao.getInstance();
		
		String category = request.getParameter("category");
		log.info(category);
		
		BufferedReader reader = request.getReader(); // http메시지 body에서 JSON 문자열 가져오기
		CommentVo commentVo = gson.fromJson(reader, CommentVo.class);
		reader.close();  // 입출력 객체 닫기
		
		if (category.equals("main")) { // 주댓글 등록하기
			int cno = mySqlDao.getNextNum("comment");
			commentVo.setCno(cno);  // 댓글 번호
			commentVo.setReRef(cno); // 주댓글일때는 글번호가 글그룹번호와 동일함
			commentVo.setReLev(0);
			commentVo.setReSeq(0);
			commentVo.setId(id); // 로그인 아이디가 댓글작성자 아이디가 됨
			log.info("commentVo : " + commentVo);
			
			commentDao.addComment(commentVo); // 주댓글 등록하기 메소드 호출
			
		} else if (category.equals("reply")) { // 답댓글 등록하기
			int cno = mySqlDao.getNextNum("comment");
			commentVo.setCno(cno);  // 댓글 번호
			commentVo.setId(id);
			log.info("답댓글 : " + commentVo.toString());
			
			commentDao.addReplyComment(commentVo); // 답댓글 등록하기 메소드 호출
		}
		
		// 클라이언트에게 응답을 줄 JSON 데이터 준비
		Map<String, Object> map = new HashMap<>();
		map.put("isSuccess", true);
		map.put("comment", commentVo);
		
		String strJson = gson.toJson(map);
		log.info("strJson : " + strJson);
		
		// 클라이언트에게 응답 주기
		sendResponse(strJson, response);
	}

	// SQL UPDATE문 요청 처리
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("doPut 호출됨");
		
		// 한글처리
		request.setCharacterEncoding("utf-8");
		
		BufferedReader reader = request.getReader();
		
		
		CommentVo commentVo = gson.fromJson(reader, CommentVo.class);
		log.info("commentVo : " + commentVo);
		
		CommentDao commentDao = CommentDao.getInstance();
		commentDao.update(commentVo); // DB update 처리 완료
		
		// 댓글수정 완료된 댓글내용 가져오기
		CommentVo commentVoGet = commentDao.getCommentByCno(commentVo.getCno());
		
		// 클라이언트에게 응답을 줄 JSON 데이터 준비
		Map<String, Object> map = new HashMap<>();
		map.put("isSuccess", true);
		map.put("comment", commentVoGet);
		
		String strJson = gson.toJson(map);
		log.info("strJson : " + strJson);
		
		// 클라이언트에게 응답 주기
		sendResponse(strJson, response);
	}

	// SQL DELETE문 요청 처리
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("doDelete 호출됨");
		
		int cno = Integer.parseInt(request.getParameter("cno"));
		log.info("삭제 cno = " + cno);
		
		CommentDao commentDao = CommentDao.getInstance();
		commentDao.deleteByCno(cno); // DB delete 처리 완료
		
		Map<String, Object> map = new HashMap<>();
		map.put("isSuccess", true);
		
		String strJson = gson.toJson(map);
		
		// 클라이언트에게 응답 주기
		sendResponse(strJson, response);
	}

}
