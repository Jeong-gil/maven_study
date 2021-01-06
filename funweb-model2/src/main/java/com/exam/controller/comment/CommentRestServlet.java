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

import com.exam.dao.CommentDao;
import com.exam.vo.CommentVo;
import com.google.gson.Gson;

import lombok.extern.java.Log;

@Log
@WebServlet(urlPatterns = "/comment/*", loadOnStartup = 1)
public class CommentRestServlet extends HttpServlet {
	
	private CommentDao commentDao = CommentDao.getInstance();
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
			int nno = Integer.parseInt(request.getParameter("nno"));
			
			List<CommentVo> commentList = commentDao.getComments(nno);
			
			Map<String, Object> map = new HashMap<>();
			map.put("commentList", commentList);
			map.put("isSuccess", true);
			
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
		
		BufferedReader reader = request.getReader();
		
		CommentVo commentVo = gson.fromJson(reader, CommentVo.class);
		log.info("commentVo : " + commentVo);
		
		commentDao.addComment(commentVo); // DB insert 처리 완료
		
		// 클라이언트에게 응답을 줄 JSON 데이터 준비
		Map<String, Object> map = new HashMap<>();
		map.put("isSuccess", true);
		
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
		
		commentDao.update(commentVo); // DB update 처리 완료
		
		// 클라이언트에게 응답을 줄 JSON 데이터 준비
		Map<String, Object> map = new HashMap<>();
		map.put("isSuccess", true);
		
		String strJson = gson.toJson(map);
		log.info("strJson : " + strJson);
		
		// 클라이언트에게 응답 주기
		sendResponse(strJson, response);
	}

	// SQL DELETE문 요청 처리
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("doDelete 호출됨");
		
		int cno = Integer.parseInt(request.getParameter("cno"));
		
		commentDao.deleteByCno(cno); // DB delete 처리 완료
		
		Map<String, Object> map = new HashMap<>();
		map.put("isSuccess", true);
		
		String strJson = gson.toJson(map);
		
		// 클라이언트에게 응답 주기
		sendResponse(strJson, response);
	}

}
