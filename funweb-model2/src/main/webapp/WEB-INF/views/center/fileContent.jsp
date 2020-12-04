<%@page import="com.exam.vo.AttachVo"%>
<%@page import="java.util.List"%>
<%@page import="com.exam.dao.AttachDao"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.exam.vo.NoticeVo"%>
<%@page import="com.exam.dao.NoticeDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
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
}

// 작성일자 출력포맷 "2020-11-09 12:27:10"
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
%>
<!DOCTYPE html>
<html>
<head>
<%-- head 컨텐트 영역 --%>
<jsp:include page="/WEB-INF/views/include/headContent.jsp" />

<link href="/css/subpage.css" rel="stylesheet" type="text/css"  media="all">
</head>
<body>
<div id="wrap">
	<%-- header 영역 --%>
	<jsp:include page="/WEB-INF/views/include/topHeader.jsp" />

	<div class="clear"></div>
	<div id="sub_img_center"></div>
	
	<div class="clear"></div>
	<%-- nav 영역 --%>
	<jsp:include page="/WEB-INF/views/include/submenuBoard.jsp" />
	
	<article>
		
	<h1>자료실 게시판 상세보기</h1>
		
	<table id="notice">
		<tr>
			<th scope="col" class="tno">글번호</th>
			<td class="left" width="500"><%=noticeVo.getNum() %></td>
		</tr>
		<tr>
			<th scope="col" class="tread">조회수</th>
			<td class="left"><%=noticeVo.getReadcount() %></td>
		</tr>
		<tr>
			<th scope="col" class="twrite">작성자</th>
			<td class="left"><%=noticeVo.getId() %></td>
		</tr>
		<tr>
			<th scope="col" class="tdate">작성일자</th>
			<td class="left"><%=sdf.format(noticeVo.getRegDate()) %></td>
		</tr>
		<tr>
			<th scope="col" class="ttitle">글제목</th>
			<td class="left"><%=noticeVo.getSubject() %></td>
		</tr>
		<tr>
			<th scope="col" class="ttitle">글내용</th>
			<td class="left"><%=content %></td>
		</tr>
		<tr>
			<th scope="col" class="ttitle">첨부파일</th>
			<td class="left">
			<%
			if (attachList != null && attachList.size() > 0) {
				for (AttachVo attachVo : attachList) {
					if (attachVo.getImage().equals("I")) {
						%>
						<p>
							<a href="/upload/<%=attachVo.getUploadpath() %>/<%=attachVo.getFilename() %>">
								<img src="/upload/<%=attachVo.getUploadpath() %>/<%=attachVo.getFilename() %>" width="100" height="100">
							</a>
						</p>
						<%
					} else {
						%>
						<p>
							<a href="/upload/<%=attachVo.getUploadpath() %>/<%=attachVo.getFilename() %>">
								<%=attachVo.getFilename() %>
							</a>
						</p>					
						<%
					}
				} // for
			}
			%>
			</td>
		</tr>
	</table>

	<div id="table_search">
		<%
		String id = (String) session.getAttribute("id");
		if (id != null) { // 로그인 했을때
			if (id.equals(noticeVo.getId())) { // 로그인 아이디와 글작성자 아이디가 같을때
				%>
				<input type="button" value="글수정" class="btn">
				<input type="button" value="글삭제" class="btn" onclick="remove()">
				<%
			}
			%>
			<input type="button" value="답글쓰기" class="btn">
			<%
		}
		%>
		<input type="button" value="목록보기" class="btn" onclick="location.href = 'fileNotice.jsp?pageNum=<%=pageNum %>'">
	</div>
	
	<div class="clear"></div>
	<div id="page_control">
	</div>
		
	</article>
    
	<div class="clear"></div>
	<%-- footer 영역 --%>
	<jsp:include page="/WEB-INF/views/include/bottomFooter.jsp" />
</div>

<script>
	function remove() {
		let isDelete = confirm('<%=noticeVo.getNum() %>번 글을 정말 삭제하시겠습니까?');
		if (isDelete) {
			location.href = 'fileDelete.jsp?num=<%=noticeVo.getNum() %>&pageNum=<%=pageNum %>';
		}
	}
</script>
</body>
</html>   



    