package com.exam.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.exam.vo.MemberVo;

// 마이바티스에서 Mapper 인터페이스가 실행의 기준이 됨!
public interface MemberMapper {

	// 매퍼의 메소드가 매개변수를 2개 이상 가질때는
	// @Param 애노테이션 값으로 이름을 지정해야 함
	
	//@Select("SELECT * FROM member WHERE id = #{id}")
	MemberVo getMemberById(String id);
	
	//@Select("SELECT * FROM member ORDER BY id")
	List<MemberVo> getAllMembers();
	
	
	void addMember(MemberVo memberVo);
	
	//@Select("SELECT passwd FROM member WHERE id = #{id}")
	String userCheck(String id);
	
	//@Select("SELECT COUNT(*) FROM member WHERE id = #{id}")
	int getCountById(String id);
	
	
	void update(MemberVo memberVo);
	
	//@Delete("DELETE FROM member WHERE id = #{id} ")
	void deleteById(String id);
	
	//@Delete("DELETE FROM member")
	void deleteAll();
	
	
//	@Select("SELECT gender, count(*) as cnt "
//			+ "FROM member "
//			+ "GROUP BY gender ")
	List<Map<String, Object>> getGenderPerCount();
	
	
	
	List<Map<String, Object>> getAgeRangePerCount();
	
	
}






