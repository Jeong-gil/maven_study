package com.exam.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = "*.do", loadOnStartup = 1)
public class FrontController extends HttpServlet {

	public void init(ServletConfig config) throws ServletException {
		System.out.println("init() 호출됨");
		
		// application 객체 가져와서 필요한 데이터 저장
		ServletContext application = config.getServletContext();
		application.setAttribute("aa", "안녕");
		
		String hello = (String) application.getAttribute("aa");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet() 호출됨");
		
		// 요청 주소
		// http://localhost:80/funweb-model2/index.do
		// http://localhost:80/index.do
		
		// 1단계) 명령어 command 구하기
		
		// URI 주소 가져오기
		String requestURI = request.getRequestURI();
		System.out.println("URI 주소: " + requestURI);
		// URI 주소: /funweb-model2/index.do
		
		// 프로젝트 이름 경로 가져오기
		String contextPath = request.getContextPath();
		System.out.println("contextPath: " + contextPath);
		// contextPath: /funweb-model2
		
		// 요청 명령어 구하기
		String command = requestURI.substring(contextPath.length());
		// command: /index.do
		command = command.substring(0, command.indexOf(".do"));
		System.out.println("command: " + command);
		// command: /index
		
		
		// 2단계) 명령어 실행하기
		Controller controller = null;
		ControllerFactory factory = ControllerFactory.getInstance();
		String strView = null;
		
		// 명령어에 해당하는 컨트롤러 객체 구하기
		controller = factory.getController(command);
		if (controller == null) {
			System.out.println(command + "를 처리하는 컨트롤러가 없습니다.");
			return;
		}
		
		try {
			// 컨트롤러 객체 실행하기
			strView = controller.execute(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		// 3단계) 화면응답(jsp실행) 또는 리다이렉트(.do) 이동
		if (strView == null) {
			System.out.println("이동할 화면 View 정보가 없습니다.");
			return;
		}
		
		if (strView.startsWith("redirect:")) { // .do로 끝나는 경로
			String redirectPath = strView.substring("redirect:".length());
			response.sendRedirect(redirectPath);
		} else {
			String jspPath = "/WEB-INF/views/" + strView + ".jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(jspPath);
			dispatcher.forward(request, response); // 해당 jsp 바로 실행하기
		}
		
	} // doGet
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost() 호출됨");
		
		request.setCharacterEncoding("utf-8"); // post요청 파라미터값 한글처리
		doGet(request, response);
	} // doPost
	
	public void destroy() {
		System.out.println("destroy() 호출됨");
	}

}
