<%@page import="java.io.BufferedInputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>
<%@page import="com.exam.vo.AttachVo"%>
<%@page import="com.exam.dao.AttachDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 로그인 여부 확인
String id = (String) session.getAttribute("id");
if (id == null) {
	response.sendRedirect("/member/login.jsp");
	return;
}
%>
<%
// 첨부파일 번호 num 파라미터 가져오기
int num = Integer.parseInt(request.getParameter("num"));

AttachDao attachDao = AttachDao.getInstance();

// 첨부파일 번호에 해당하는 레코드 한개 가져오기
AttachVo attachVo = attachDao.getAttachByNum(num);

String realPath = application.getRealPath("/"); // webapp

// 다운로드할 파일정보를 File 객체로 준비
File file = new File(realPath + "/upload/" + attachVo.getUploadpath(), attachVo.getFilename());

// 입력스트림 준비
BufferedInputStream is = null;
is = new BufferedInputStream(new FileInputStream(file));

// 마임(Mime)타입 가져와서 컨텐트 타입으로 설정하기 (image/png, audio/mp3, video/mp4)
String mimeType = application.getMimeType(file.getPath());
if (mimeType == null) { // 마임타입 정보가 없으면 application/octet-stream 로 설정하기
	mimeType = "application/octet-stream";
}
response.setContentType(mimeType);

String filename = attachVo.getFilename();
System.out.println("utf-8 파일명: " + filename);

// 다운로드 파일명의 문자셋을 utf-8에서 iso-8859-1로 변환
filename = new String(filename.getBytes("utf-8"), "iso-8859-1");
System.out.println("iso-8859-1 파일명: " + filename);

// 다운로드 될 실제 파일명을 헤더에 설정
response.setHeader("Content-Disposition", "attachment; filename=" + filename);

// 출력스트림 준비
ServletOutputStream os = response.getOutputStream();

// 다운로드 수행 : 입력스트림으로 파일을 읽어서
//      클라이언트 응답 출력스트림으로 출력하기
int data;
while ((data = is.read()) != -1) {
	os.write(data);
}

// 입출력 객체 자원닫기
is.close();
os.close();
%>




