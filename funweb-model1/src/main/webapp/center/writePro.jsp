<%@page import="com.exam.dao.*"%>
<%@page import="java.sql.Timestamp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%-- post전송 파라미터값 한글처리 --%>
<% request.setCharacterEncoding("utf-8"); %>

<%-- pageNum 파라미터 가져오기 --%>
<% String pageNum = request.getParameter("pageNum"); %>

<jsp:useBean id="noticeVo" class="com.exam.vo.NoticeVo" />
<jsp:setProperty property="*" name="noticeVo"/>

<%
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
// NoticeDao noticeDao = NoticeDao.getInstance();
NoticeMyBatisDao noticeDao = NoticeMyBatisDao.getInstance();

//주글 등록하기
noticeDao.addNotice(noticeVo);

// 글내용 상세보기 화면 content.jsp로 이동
response.sendRedirect("/center/content.jsp?num=" + num + "&pageNum=" + pageNum);
%>


