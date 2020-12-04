<%@page import="com.exam.dao.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 파라미터값 id 가져오기
String id = request.getParameter("id");

// DAO 객체 준비
MemberDao memberDao = MemberDao.getInstance();

// 중복아이디 여부 확인
int count = memberDao.getCountById(id);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>ID 중복확인</h2>
<hr>
<%
//count == 1  아이디있음  "사용중인 ID 입니다."
//count == 0  아이디없음  "사용가능한 ID 입니다."
if (count == 1) {
	%>
	<p>아이디 중복, 이미 사용중인 ID 입니다.</p>
	<%
} else {
	%>
	<p>사용가능한 ID 입니다.</p>
	<input type="button" value="ID 사용" id="btnUseId">
	<%
}
%>

<form action="joinIdDupCheck.jsp" method="get" name="frm">
	<input type="text" name="id" value="<%=id %>">
	<input type="submit" value="ID 중복확인">
</form>

<script src="/script/jquery-3.5.1.js"></script>
<script>
	$('#btnUseId').click(function () {
		// 검색한 ID 값 -> 창을 열게해준 부모페이지인 join.jsp의 id 입력상자에 넣기
		window.opener.document.frm.id.value = frm.id.value;
		window.close(); // 현재 창 닫기
	});
</script>
</body>
</html>



