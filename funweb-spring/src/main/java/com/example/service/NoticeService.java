package com.example.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.NoticeVo;
import com.example.mapper.NoticeMapper;

@Service
@Transactional
public class NoticeService {

	@Autowired
	private NoticeMapper noticeMapper;

	// 주글 쓰기
	public void addNotice(NoticeVo noticeVo) {
		noticeMapper.addNotice(noticeVo);
	}

	public NoticeVo getNoticeByNum(int num) {
		NoticeVo noticeVo = noticeMapper.getNoticeByNum(num);
		return noticeVo;
	}

	public void updateReadcount(int num) {
		noticeMapper.updateReadcount(num);
	}
}
