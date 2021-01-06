package com.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.exam.vo.CommentVo;

public interface CommentMapper {

	@Insert("INSERT INTO comment (nno, id, content, re_ref, re_lev, re_seq) "
			+ "VALUES (#{nno}, #{id}, #{content}, #{reRef}, #{reLev}, #{reSeq})")
	int insert(CommentVo commentVo);
	
	@Select("SELECT * FROM comment WHERE cno = #{cno}")
	CommentVo getCommentByCno(int cno);
	
	@Delete("DELETE FROM comment WHERE cno = #{cno}")
	int deleteByCno(int con);
	
	@Update("UPDATE comment "
			+ "SET content = #{content}, update_date = CURRENT_TIMESTAMP() "
			+ "WHERE cno = #{cno}")
	void update(CommentVo commentVo);
	
	@Select("SELECT * "
			+ "FROM comment "
			+ "WHERE nno = #{nno} "
			+ "ORDER BY re_ref DESC, re_seq ASC;")
	List<CommentVo> getComments(int nno);
	
}
