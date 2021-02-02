<%@page import="org.mindrot.jbcrypt.BCrypt"%>
<%@page import="com.exam.dao.MemberMyBatisDao"%>
<%@page import="com.exam.dao.MemberDao"%>
<%@page import="java.sql.Timestamp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// post 전송 파라미터값 한글처리
request.setCharacterEncoding("utf-8");
%>
<%-- 액션태그로 VO 객체 준비 --%>
<jsp:useBean id="memberVo" class="com.exam.vo.MemberVo" />
<%-- 액션태그로 VO객체에 파라미터값 저장 --%>
<jsp:setProperty property="*" name="memberVo" />
<%
// 사용자 입력 패스워드를 암호화된 문자열로 변경
String passwd = memberVo.getPasswd();
String hashedPwd = BCrypt.hashpw(passwd, BCrypt.gensalt());
memberVo.setPasswd(hashedPwd);

// 가입날짜 생성해서 넣기
memberVo.setRegDate(new Timestamp(System.currentTimeMillis()));
// DAO 객체 준비
//MemberDao memberDao = MemberDao.getInstance();
MemberMyBatisDao memberDao = MemberMyBatisDao.getInstance();
// 회원가입 메서드 호출
memberDao.addMember(memberVo);
// 회원가입 성공 후 로그인 페이지로 리다이렉트
//response.sendRedirect("login.jsp");
%>
<script>
	alert('회원가입 성공');
	location.href = 'login.jsp';
</script>




