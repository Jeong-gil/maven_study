package com.exam.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.exam.mapper.MySqlMapper;

public final class MySqlDao {

	private static MySqlDao instance = new MySqlDao();
	
	public static MySqlDao getInstance() {
		return instance;
	}
	
	/////////////////////////////////////////////////
	
	private SqlSessionFactory sqlSessionFactory;

	private MySqlDao() {
		sqlSessionFactory = MyBatisUtils.getSqlSessionFactory();
	}
	
	
	public int getNextNum(String tableName) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			MySqlMapper mapper = sqlSession.getMapper(MySqlMapper.class);
			int num = mapper.getNextNum(tableName);
			return num;
		}
	}
	
}



