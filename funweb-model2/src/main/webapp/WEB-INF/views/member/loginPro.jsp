<%@page import="com.exam.dao.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// post 전송 파라미터 한글처리
request.setCharacterEncoding("utf-8");

// 파라미터 id  passwd   keepLogin  가져오기
String id = request.getParameter("id");
String passwd = request.getParameter("passwd");
String strKeepLogin = request.getParameter("keepLogin");

// DAO 객체 준비
MemberDao memberDao = MemberDao.getInstance();

// 로그인 확인.
// check -1  없는 아이디
// check  0  패스워드 틀림
// check  1  아이디, 패스워드 모두 일치
int check = memberDao.userCheck(id, passwd);

// 로그인이 실패했을 경우
if (check != 1) {
	%>
	<script>
		alert('아이디 또는 패스워드가 일치하지 않습니다.');
		history.back();
	</script>	
	<%
	return;
}

//로그인 상태유지 정보 확인하기
boolean keepLogin = false;
if (strKeepLogin != null) { // "true"
	keepLogin = Boolean.parseBoolean(strKeepLogin); // "true" -> true
}

// 로그인이 성공했을 경우
// 세션에 로그인 아이디를 저장 (로그인 처리)
session.setAttribute("id", id);

// 로그인 상태유지를 원하면 쿠키 생성 후 응답주기
if (keepLogin) { // keepLogin == true
	Cookie cookie = new Cookie("id", id);
	cookie.setMaxAge(60 * 10);  // 초단위 설정  10분
	cookie.setPath("/");

	response.addCookie(cookie);
}

// index.jsp로 리다이렉트
response.sendRedirect("/index.jsp");
%>




