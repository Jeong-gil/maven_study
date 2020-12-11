<%@page import="com.exam.dao.NoticeDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
//로그인 체크를 위해 세션값 가져오기
String id = (String) session.getAttribute("id");
//세션값 id 없으면 login.jsp로 리다이렉트 이동
if (id == null) {
	response.sendRedirect("/member/login.jsp"); 
	return;
}
%>

<%-- post전송 파라미터값 한글처리 --%>
<% request.setCharacterEncoding("utf-8"); %>

<%-- 액션태그로 파라미터값 저장위해 VO객체 준비 --%>
<jsp:useBean id="noticeVo" class="com.exam.vo.NoticeVo" />

<%-- 액션태그로 VO객체에 파라미터값 저장하기 --%>
<jsp:setProperty property="*" name="noticeVo"/>

<%-- pageNum 파라미터 가져오기 --%>
<% String pageNum = request.getParameter("pageNum"); %>

<%-- DAO 객체 준비 후 업데이트하기 --%>
<%
NoticeDao noticeDao = NoticeDao.getInstance();
noticeDao.updateBoard(noticeVo);
%>

<%-- 수정된 글의 상세보기 화면으로 리다이렉트 이동 --%>
<% response.sendRedirect("/center/content.jsp?num=" + noticeVo.getNum() + "&pageNum=" + pageNum); %>


