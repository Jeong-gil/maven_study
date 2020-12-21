package com.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.exam.vo.AttachVo;

public interface AttachMapper {
	// INSERT, DELETE 수행하는 메소드의 리턴값은 변경된 행의 갯수가 되므로
	// 값을 알고싶을때는 int형으로 하면 됨! (UPDATE는 리턴값이 없으므로 void로 해야함) 
	
	int insertAttach(AttachVo attachVo);
	
	@Select("SELECT * FROM attach WHERE num = #{num}")
	AttachVo getAttachByNum(int num);
	
	@Select("SELECT * FROM attach WHERE no_num = #{noNum}")
	List<AttachVo> getAttachesByNoNum(int noNum);
	
	@Delete("DELETE FROM attach where num = #{num}")
	int deleteAttachByNum(int num);
	
	@Delete("DELETE FROM attach where no_num = #{noNum}")
	int deleteAttachesByNoNum(int noNum);
	
	// 메소드의 매개변수 타입이 리스트 컬렉션일 때는 @Param을 꼭 적용해야 함! 
	void deleteAttachesByNums(@Param("numList") List<Integer> numList);
}




