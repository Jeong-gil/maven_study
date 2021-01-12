package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.MemberVo;
import com.example.mapper.MemberMapper;

import lombok.extern.java.Log;

@Log
@Service
@Transactional  // 이 클래스의 모든 메소드가 각각 한개의 트랜잭션 단위로 수행됨 (서비스 애노테이션이랑만 사용할 수 있다)
public class MemberService {
	
	// 스프링 빈으로 등록된 객체들 중에서 
	// 타입으로 객체의 참조를 가져와서 참조변수에 저장해줌
	@Autowired
	private MemberMapper memberMapper;
	
//	@Transactional
	public MemberVo getMemberById(String id) {
		MemberVo memberVo = memberMapper.getMemberById(id);
		return memberVo;
	}
	
	public void addMember(MemberVo memberVo) {
		memberMapper.addMember(memberVo);
	}
	
}
