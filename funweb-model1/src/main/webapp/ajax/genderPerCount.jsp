<%@page import="com.exam.dao.MemberMyBatisDao"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.exam.dao.MemberDao"%>
<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// DAO 객체 준비
//MemberDao memberDao = MemberDao.getInstance();
MemberMyBatisDao memberDao = MemberMyBatisDao.getInstance();

List<Map<String, Object>> list = memberDao.getGenderPerCount();
System.out.println("list = " + list);


JSONArray jsonArray = new JSONArray();

for (Map<String, Object> map : list) {
	String gender = (String) map.get("gender");
	long cnt = (long) map.get("cnt");
	
	JSONArray rowArray = new JSONArray();
	rowArray.add(0, gender);
	rowArray.add(1, cnt);
	
	jsonArray.add(rowArray);
} // for

JSONArray titleArray = new JSONArray();
titleArray.add("성별");
titleArray.add("회원수");

jsonArray.add(0, titleArray);

System.out.println(jsonArray);
out.println(jsonArray);
%>

