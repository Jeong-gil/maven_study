<%@page import="com.exam.dao.NoticeDao"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="com.exam.dao.JdbcUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
//로그인 여부 확인
String id = (String) session.getAttribute("id");
if (id == null) {
	response.sendRedirect("/center/notice.jsp");
	return;
}

// post 파라미터값 한글처리
request.setCharacterEncoding("utf-8");

//파라미터값 pageNum 가져오기
String pageNum = request.getParameter("pageNum");
%>

<%-- VO 객체 준비 --%>
<jsp:useBean id="noticeVo" class="com.exam.vo.NoticeVo" />

<%-- 파라미터값 가져와서 VO에 저장하기 --%>
<%--
   reRef, reLev, reSeq 는 동일한 VO객체에 저장되지만
   답글 자체의 정보가 아니고 답글을 다는 대상글에 대한 정보임에 주의!!
 --%>
<jsp:setProperty property="*" name="noticeVo"/>

<%
//insert될 글번호 가져오기
int num = JdbcUtils.getNextNum("notice");
noticeVo.setNum(num);

//ip  regDate  readcount  값 저장
noticeVo.setIp(request.getRemoteAddr());
noticeVo.setRegDate(new Timestamp(System.currentTimeMillis()));
noticeVo.setReadcount(0);  // 조회수

// DAO 객체 준비
NoticeDao noticeDao = NoticeDao.getInstance();

// 답글 insert하기
noticeDao.updateAndAddReply(noticeVo);

// 글내용 상세보기 화면 content.jsp로 이동
response.sendRedirect("content.jsp?num=" + noticeVo.getNum() + "&pageNum=" + pageNum);
%>





