package com.exam.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.exam.mapper.CommentMapper;
import com.exam.vo.CommentVo;
import com.exam.vo.Criteria;

import lombok.extern.java.Log;

@Log
public class CommentDao {
	
	private static CommentDao instance = new CommentDao();
	
	public static CommentDao getInstance() {
		return instance;
	}

	private SqlSessionFactory sqlSessionFactory;
	
	private CommentDao() {
		sqlSessionFactory = MyBatisUtils.getSqlSessionFactory();
	}
	
	
	public int getTotalCountByNno(int nno) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
			int count = commentMapper.getTotalCountByNno(nno);
			return count;
		}
	}
	
	public int addComment(CommentVo commentVo) {
//		System.out.println(commentVo);
		log.info(commentVo.toString());
		
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
			int count = commentMapper.insert(commentVo);
			return count;
		}
	}
	
	public CommentVo getCommentByCno(int cno) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
			CommentVo commentVo = commentMapper.getCommentByCno(cno);
			return commentVo;
		}
	}
	
	public int deleteByCno(int cno) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
			int count = commentMapper.deleteByCno(cno);
			return count;
		}
	}
	
	public void update(CommentVo commentVo) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
			commentMapper.update(commentVo);
		}
	}
	
	public List<CommentVo> getComments(int nno) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
			List<CommentVo> list = commentMapper.getComments(nno);
			return list;
		}
	}
	
	
	public List<CommentVo> getCommentsWithPaging(int nno, Criteria cri) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
			List<CommentVo> list = commentMapper.getCommentsWithPaging(nno, cri);
			return list;
		}
	}
	
	
	// 답댓글 쓰기 메소드 (트랜잭션 처리 - 수동커밋)
	public void addReplyComment(CommentVo commentVo) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(false)) { // 수동커밋
			CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
			
			// 같은 글그룹 내에서 순번정리 업데이트하기
			commentMapper.updateReSeq(commentVo.getReRef(), commentVo.getReSeq());
			
			// 등록할 답글 정보로 VO의 re 값 수정하기
			commentVo.setReLev(commentVo.getReLev() + 1);
			commentVo.setReSeq(commentVo.getReSeq() + 1);
			
			// 답글 insert하기
			commentMapper.insert(commentVo);

			// 커밋하기
			sqlSession.commit();
		}
	}
	
}




