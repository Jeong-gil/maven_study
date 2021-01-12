package com.example.controller;

import java.sql.Timestamp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.domain.MemberVo;
import com.example.service.MemberService;

import lombok.extern.java.Log;

@Log
@Controller
@RequestMapping("/member/*")
public class MemberController {
	
	private MemberService memberService;
	
//	@RequestMapping(value = "/join", method = RequestMethod.GET)
	@GetMapping("/join")
	public void join() {
		log.info("GET - join() 호출됨");
//		return "member/join";  // 메소드 리턴타입이 String일 경우
	}
	
	@PostMapping("/join")
	public String join(MemberVo memberVo) {
		log.info("POST - join() 호출됨");
		
		// 회원가입 날짜 설정
		memberVo.setRegDate(new Timestamp(System.currentTimeMillis()));
		log.info("memberVo : " + memberVo);
		
		// 회원가입처리
		memberService.addMember(memberVo);
		
		return "redirect:/member/login";
	}
	
	@GetMapping("/login")
	public void login() {
//		return "member/login";
	}
	
}
