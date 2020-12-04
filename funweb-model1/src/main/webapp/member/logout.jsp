<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%-- 세션값 초기화. 로그아웃 작업 --%>
<% session.invalidate(); %>

<%
// 로그인 상태유지용 쿠키가 존재하면 삭제하기
Cookie[] cookies = request.getCookies();

if (cookies != null) {
	for (Cookie cookie : cookies) {
		if (cookie.getName().equals("id")) {
			cookie.setMaxAge(0); // 유효기간 0으로 설정 -> 쿠키 삭제처리
			cookie.setPath("/"); // 경로 설정
			
			response.addCookie(cookie); // 쿠키정보 보내기
		}
	}
}
%>

<%-- index.jsp로 리다이렉트 --%>
<% response.sendRedirect("/index.jsp"); %>
