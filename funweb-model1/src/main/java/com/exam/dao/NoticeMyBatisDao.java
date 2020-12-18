package com.exam.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.exam.mapper.MemberMapper;
import com.exam.mapper.NoticeMapper;
import com.exam.vo.NoticeVo;

public class NoticeMyBatisDao {

	private static NoticeMyBatisDao instance = new NoticeMyBatisDao();
	
	public static NoticeMyBatisDao getInstance() {
		return instance;
	}
	
	/////////////////////////////////////////////////
	
	private SqlSessionFactory sqlSessionFactory;

	private NoticeMyBatisDao() {
		sqlSessionFactory = MyBatisUtils.getSqlSessionFactory();
	}
	
	
	// 주글쓰기 메서드
	public void addNotice(NoticeVo noticeVo) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			NoticeMapper mapper = sqlSession.getMapper(NoticeMapper.class);
			mapper.addNotice(noticeVo);
		}
	}
	
	
	public NoticeVo getNoticeByNum(int num) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			NoticeMapper mapper = sqlSession.getMapper(NoticeMapper.class);
			NoticeVo noticeVo = mapper.getNoticeByNum(num);
			return noticeVo;
		}
	}
	
	
	
	public void updateReadcount(int num) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			NoticeMapper mapper = sqlSession.getMapper(NoticeMapper.class);
			mapper.updateReadcount(num);
		}
	}
	
	
	
	// 전체글갯수 가져오기
	public int getCountAll() {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			NoticeMapper mapper = sqlSession.getMapper(NoticeMapper.class);
			int count = mapper.getCountAll();
			return count;
		}
	}
	
	
	public List<NoticeVo> getNotices(int startRow, int pageSize) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			NoticeMapper mapper = sqlSession.getMapper(NoticeMapper.class);
			List<NoticeVo> list = mapper.getNotices(startRow, pageSize);
			return list;
		}
	}
	
	
	public void updateBoard(NoticeVo noticeVo) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			NoticeMapper mapper = sqlSession.getMapper(NoticeMapper.class);
			mapper.updateBoard(noticeVo);
		}
	}
	
	public void deleteNoticeByNum(int num) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			NoticeMapper mapper = sqlSession.getMapper(NoticeMapper.class);
			mapper.deleteNoticeByNum(num);
		}
	}
	
	public void deleteAll() {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			NoticeMapper mapper = sqlSession.getMapper(NoticeMapper.class);
			mapper.deleteAll();
		}
	}
	
	// 답글쓰기 메서드
	public boolean updateAndAddReply(NoticeVo noticeVo) {
		SqlSession sqlSession = null;
		try {
			// 트랜잭션 단위로 처리하기 위해서 수동커밋으로 설정함
			sqlSession = sqlSessionFactory.openSession(false); // false면 수동커밋
			NoticeMapper mapper = sqlSession.getMapper(NoticeMapper.class);
			
			// 답글 insert 하기 전에 같은 글그룹 내의 순번 수정하기
			mapper.updateReSeq(noticeVo.getReRef(), noticeVo.getReSeq());
			
			// 답글에 알맞은 값으로 VO를 수정
			noticeVo.setReLev(noticeVo.getReLev() + 1);
			noticeVo.setReSeq(noticeVo.getReSeq() + 1);
			
			// 답글 insert 하기
			mapper.addNotice(noticeVo);
			
			sqlSession.commit(); // 커밋하기
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			sqlSession.rollback(); // 롤백하기
			return false;
		} finally {
			sqlSession.close(); // sqlSession 닫기
		}
	}
	
	
	// 검색어를 적용한 글갯수 가져오기
	public int getCountBySearch(String category, String search) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			NoticeMapper mapper = sqlSession.getMapper(NoticeMapper.class);
			return mapper.getCountBySearch(category, search);
		}
	}
	
	
	public List<NoticeVo> getNoticesBySearch(int startRow, int pageSize, String category, String search) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			NoticeMapper mapper = sqlSession.getMapper(NoticeMapper.class);
			return mapper.getNoticesBySearch(startRow, pageSize, category, search);
		}
	}
	
	
	// notice 테이블과 attach 테이블 왼쪽 외부조인해서 가져오기
	public NoticeVo getNoticeAndAttaches(int num) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			NoticeMapper mapper = sqlSession.getMapper(NoticeMapper.class);
			return mapper.getNoticeAndAttaches(num);
		}
	}
	
	//public List<NoticeVo> getNoticesByNums(List<Integer> numList)
	public List<NoticeVo> getNoticesByNums(Integer... numArr) {
		List<Integer> numList = Arrays.asList(numArr);
		
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			NoticeMapper mapper = sqlSession.getMapper(NoticeMapper.class);
			return mapper.getNoticesByNums(numList);
		}
	}
	
	
	public static void main(String[] args) {
		NoticeMyBatisDao dao = NoticeMyBatisDao.getInstance();
		
		List<Integer> numList = new ArrayList<>();
		numList.add(1014);
		numList.add(1013);
		numList.add(1010);
		
		//List<NoticeVo> noticeList = dao.getNoticesByNums(numList);
		List<NoticeVo> noticeList = dao.getNoticesByNums(1014, 1013, 1010);
		for (NoticeVo noticeVo : noticeList) {
			System.out.println(noticeVo);
		}
		
	}
	
}



