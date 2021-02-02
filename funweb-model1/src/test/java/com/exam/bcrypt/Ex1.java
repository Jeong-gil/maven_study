package com.exam.bcrypt;

import org.mindrot.jbcrypt.BCrypt;

public class Ex1 {

	public static void main(String[] args) {
		// 암호화 할 패스워드
		String inputPwd = "abcd1234";
		// 솔트 문자열 생성
		String salt = BCrypt.gensalt();
		System.out.println("salt : " + salt);
		// 솔트 문자열을 추가하여 패스워드 암호화
		String hashedPwd = BCrypt.hashpw(inputPwd, salt);
		System.out.println("hashedPwd : " + hashedPwd);
		System.out.println("hashedPwd length : " + hashedPwd.length()); // 문자길이 60
		// 암호화된 문자열과 같은내용인지 비교. 복호화는 불가능함.
		boolean isValidPwd = BCrypt.checkpw(inputPwd, hashedPwd);
		System.out.println("isValidPwd : " + isValidPwd);
	} // main
	
}