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
		// Ű���� .do�� ������ ��û URL�ּ��� �Ϻ� - ��ɾ� �������� ���
		
		// ��ɾ�� ��ɾ ó���ϴ� ��Ʈ�ѷ� ��ü�� ������ �����
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
		
		// notice �ؽ�Ʈ �Խ���
		map.put("/notice", new NoticeController());
		map.put("/writeForm", new WriteFormController());
		map.put("/writePro", new WriteProController());
		map.put("/content", new ContentController());
		
		// fileNotice �ڷ�� �Խ���
		map.put("/fileNotice", new FileNoticeController());
		map.put("/fileWriteForm", new FileWriteFormController());
		map.put("/fileWritePro", new FileWriteProController());
		map.put("/fileContent", new FileContentController());
		map.put("/download", new DownloadController());
	} // ������

	
	public Controller getController(String command) {
		// Map �÷����� Ű���� ���������ʴ� ���� ������ null�� ������
		Controller controller = map.get(command);
		return controller;
	}
	
}
