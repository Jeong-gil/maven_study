package com.exam.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.exam.mapper.MemberMapper;
import com.exam.vo.MemberVo;

public class MemberMyBatisDao {

	private static MemberMyBatisDao instance = new MemberMyBatisDao();
	
	public static MemberMyBatisDao getInstance() {
		return instance;
	}
	///////////////////////////////////////////////////////////////
	
	private SqlSessionFactory sqlSessionFactory;

	private MemberMyBatisDao() {
		sqlSessionFactory = MyBatisUtils.getSqlSessionFactory();
	}
	
	
	public MemberVo getMemberById(String id) {
		MemberVo memberVo = null;
		
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			MemberMapper mapper = sqlSession.getMapper(MemberMapper.class);
			memberVo = mapper.getMemberById(id);
		}
		return memberVo;
	}
	
	
	public List<MemberVo> getAllMembers() {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			MemberMapper mapper = sqlSession.getMapper(MemberMapper.class);
			List<MemberVo> list = mapper.getAllMembers();
			return list;
		}
	}
	
	
	
	// 회원정보 1명 insert하기
	public void addMember(MemberVo memberVo) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			MemberMapper mapper = sqlSession.getMapper(MemberMapper.class);
			mapper.addMember(memberVo);
			
//			sqlSession.commit();
//			sqlSession.rollback();
		}
	}
	
	
	
	// 로그인 확인.
	// check -1  없는 아이디
	// check  0  패스워드 틀림
	// check  1  아이디, 패스워드 모두 일치
	public int userCheck(String id, String passwd) {
		int check = -1; // 없는 아이디 상태값으로 초기화
		
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			MemberMapper mapper = sqlSession.getMapper(MemberMapper.class);
			String dbPasswd = mapper.userCheck(id);
			
			if (dbPasswd != null) {
				if (passwd.equals(dbPasswd)) {
					check = 1;
				} else {
					check = 0;
				}
			} else { // dbPasswd == null
				check = -1;
			}
		}
		return check;
	}
	
	
	public int getCountById(String id) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			MemberMapper mapper = sqlSession.getMapper(MemberMapper.class);
			int count = mapper.getCountById(id);
			return count;
		}
	}
	
	
	public void update(MemberVo memberVo) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			MemberMapper mapper = sqlSession.getMapper(MemberMapper.class);
			mapper.update(memberVo);
		}
	}
	
	
	public void deleteById(String id) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			MemberMapper mapper = sqlSession.getMapper(MemberMapper.class);
			mapper.deleteById(id);
		}
	}
	
	
	public void deleteAll() {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			MemberMapper mapper = sqlSession.getMapper(MemberMapper.class);
			mapper.deleteAll();
		}
	}
	
	
	public List<Map<String, Object>> getGenderPerCount() {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			MemberMapper mapper = sqlSession.getMapper(MemberMapper.class);
			List<Map<String, Object>> list = mapper.getGenderPerCount();
			return list;
		}
	}
	
	
	
	public List<Map<String, Object>> getAgeRangePerCount() {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			MemberMapper mapper = sqlSession.getMapper(MemberMapper.class);
			List<Map<String, Object>> list = mapper.getAgeRangePerCount();
			return list;
		}
	}
	
	
	
	public static void main(String[] args) {
		MemberMyBatisDao dao = MemberMyBatisDao.getInstance();
		
		MemberVo memberVo = dao.getMemberById("aaa");
//		System.out.println(memberVo);
		
		System.out.println("============================");
		
		List<MemberVo> list = dao.getAllMembers();
		for (MemberVo vo : list) {
			System.out.println(vo);
		}
	}
	
}




