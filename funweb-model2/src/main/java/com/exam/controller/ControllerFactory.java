package com.exam.controller;

import java.util.HashMap;
import java.util.Map;

import com.exam.controller.company.*;
import com.exam.controller.filenotice.*;
import com.exam.controller.member.*;
import com.exam.controller.notice.*;

public class ControllerFactory {
	
	private static ControllerFactory instance = new ControllerFactory();
	
	public static ControllerFactory getInstance() {
		return instance;
	}

	///////////////////////////////////////////////////////////

	private Map<String, Controller> map = new HashMap<>();
	
	private ControllerFactory() {
		// 키값은 .do로 끝나는 요청 URL주소의 일부 - 명령어 개념으로 사용
		
		// 명령어와 명령어를 처리하는 컨트롤러 객체를 쌍으로 등록함
		map.put("/index", new IndexController());
		
		// company
		map.put("/welcome", new WelcomeController());
		map.put("/history", new HistoryController());
		
		// member
		map.put("/memberJoin", new MemberJoinController());
		map.put("/joinIdDupCheck", new JoinIdDupCheckController());
		map.put("/memberJoinPro", new MemberJoinProController());
		map.put("/memberLogin", new MemberLoginController());
		map.put("/memberLoginPro", new MemberLoginProController());
		map.put("/memberLogout", new MemberLogoutController());
		
		// notice 텍스트 게시판
		map.put("/notice", new NoticeController());
		map.put("/writeForm", new WriteFormController());
		map.put("/writePro", new WriteProController());
		map.put("/content", new ContentController());
		
		// fileNotice 자료실 게시판
		map.put("/fileNotice", new FileNoticeController());
		map.put("/fileWriteForm", new FileWriteFormController());
		map.put("/fileWritePro", new FileWriteProController());
		map.put("/fileContent", new FileContentController());
		
	} // 생성자

	
	public Controller getController(String command) {
		// Map 컬렉션은 키값이 존재하지않는 값을 꺼내면 null을 리턴함
		Controller controller = map.get(command);
		return controller;
	}
	
}
