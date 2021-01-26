package com.exam;

public class Hello {
	
	public static String helloWorld() {
		return "Hello world!";
	}
	
	public static int[] getNumArr() {
		return new int[] { 10, 20, 30 };
	}
	
	public static void receivePositiveNumber(int num) throws Exception {
		if (num < 0) {  // 음수
			throw new Exception("음수는 받을 수 없습니다..");
		}
		// 양수
	}
	
}
