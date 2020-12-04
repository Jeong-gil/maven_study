package com.exam.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.exam.vo.MemberVo;

public class MemberDao {
	
	// 싱글톤 패턴 설계
	private static MemberDao instance = new MemberDao();
	
	public static MemberDao getInstance() {
		return instance;
	}
	
	///////////////////////////////////////////////////////////

	private MemberDao() {
	}

	// 회원정보 1명 insert하기
	public void addMember(MemberVo memberVo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = JdbcUtils.getConnection();
			
			String sql = "";
			sql += "INSERT INTO member (id, passwd, name, age, gender, email, reg_date, address, tel) ";
			sql += "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberVo.getId());
			pstmt.setString(2, memberVo.getPasswd());
			pstmt.setString(3, memberVo.getName());
			pstmt.setInt(4, memberVo.getAge());
			pstmt.setString(5, memberVo.getGender());
			pstmt.setString(6, memberVo.getEmail());
			pstmt.setTimestamp(7, memberVo.getRegDate());
			pstmt.setString(8, memberVo.getAddress());
			pstmt.setString(9, memberVo.getTel());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 예외 발생여부에 관계없이 무조건 정리작업 수행함.
			// try블록에서 만든 객체를 정리하는 작업을 주로 함
			JdbcUtils.close(con, pstmt);
		}
	} // addMember()
	
	
	// 로그인 확인.
	// check -1  없는 아이디
	// check  0  패스워드 틀림
	// check  1  아이디, 패스워드 모두 일치
	public int userCheck(String id, String passwd) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		
		int check = -1; // 없는 아이디 상태값으로 초기화
		
		try {
			con = JdbcUtils.getConnection();
			// id에 해당하는 passwd 가져오기
			sql = "SELECT passwd FROM member WHERE id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			// rs에 저장
			rs = pstmt.executeQuery();
			// rs에 데이터(행) 있으면
			//             패스워드 비교  맞으면  check = 1  틀리면  check = 0
			// rs에 데이터(행) 없으면   check = -1
			if (rs.next()) {
				if (passwd.equals(rs.getString("passwd"))) {
					check = 1;
				} else {
					check = 0;
				}
			} else {
				check = -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.close(con, pstmt, rs);
		}
		return check;
	} // userCheck()
	
	
	
	
	// 전체 회원목록 가져오기
	public List<MemberVo> getAllMembers() {
		List<MemberVo> list = new ArrayList<>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "";
		
		try {
			con = JdbcUtils.getConnection();
			
			sql = "SELECT * FROM member ORDER BY id";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				MemberVo memberVo = new MemberVo();
				memberVo.setId(rs.getString("id"));
				memberVo.setPasswd(rs.getString("passwd"));
				memberVo.setName(rs.getString("name"));
				memberVo.setAge(rs.getInt("age"));
				memberVo.setGender(rs.getString("gender"));
				memberVo.setEmail(rs.getString("email"));
				memberVo.setRegDate(rs.getTimestamp("reg_date"));
				
				list.add(memberVo);
			} // while
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.close(con, pstmt, rs);
		}
		return list;
	} // getAllMembers()
	
	
	// 특정id에 해당하는 회원 1명 가져오기
	public MemberVo getMemberById(String id) {
		MemberVo memberVo = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "";
		
		try {
			con = JdbcUtils.getConnection();
			
			sql = "SELECT * FROM member WHERE id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				memberVo = new MemberVo();
				memberVo.setId(rs.getString("id"));
				memberVo.setPasswd(rs.getString("passwd"));
				memberVo.setName(rs.getString("name"));
				memberVo.setAge(rs.getInt("age"));
				memberVo.setGender(rs.getString("gender"));
				memberVo.setEmail(rs.getString("email"));
				memberVo.setRegDate(rs.getTimestamp("reg_date"));
			} // if
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.close(con, pstmt, rs);
		}
		return memberVo;
	} // getMemberById()
	
	
	public int getCountById(String id) {
		int count = 0;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "";
		
		try {
			con = JdbcUtils.getConnection();
			
			sql = "SELECT COUNT(*) FROM member WHERE id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				count = rs.getInt(1);
			} // if
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.close(con, pstmt, rs);
		}
		return count;
	} // getCountById()
	
	
	// 특정id에 해당하는 회원 정보 수정하기
	public void update(MemberVo memberVo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = JdbcUtils.getConnection();
			
			String sql = "";
			sql += "UPDATE member ";
			sql += "SET name = ?, age = ?, gender = ?, email = ?, address = ?, tel = ? ";
			sql += "WHERE id = ? ";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberVo.getName());
			pstmt.setInt(2, memberVo.getAge());
			pstmt.setString(3, memberVo.getGender());
			pstmt.setString(4, memberVo.getEmail());
			pstmt.setString(5, memberVo.getAddress());
			pstmt.setString(6, memberVo.getTel());
			pstmt.setString(7, memberVo.getId());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 예외 발생여부에 관계없이 무조건 정리작업 수행함.
			// try블록에서 만든 객체를 정리하는 작업을 주로 함
			JdbcUtils.close(con, pstmt);
		}
	} // addMember()
	
	// 특정id에 해당하는 회원 1명 삭제하기
	public void deleteById(String id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = JdbcUtils.getConnection();
			
			String sql = "";
			sql += "DELETE FROM member WHERE id = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.close(con, pstmt);
		}
	} // deleteById()
	
	
	// 모든 회원 삭제하기
	public void deleteAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = JdbcUtils.getConnection();
			
			String sql = "";
			sql += "DELETE FROM member ";
			pstmt = con.prepareStatement(sql);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.close(con, pstmt);
		}
	} // deleteAll()
	
	
	public static void main(String[] args) {
		
		// MemberDao 객체 준비
		MemberDao memberDao = new MemberDao();
		
		Random random = new Random();
		
		memberDao.deleteAll(); // 전체삭제
		
		System.out.println("======== insert 테스트 =========");
		
		for (int i=0; i<1000; i++) {
			MemberVo memberVo = new MemberVo();
			memberVo.setId("user"+i);
			memberVo.setPasswd("1234");
			memberVo.setName("유저"+i);
			
			// 나이값의 범위  8세이상 ~ 100세이하
			int age = random.nextInt(93) + 8; // (0~92)+8 -> (8~100)
			memberVo.setAge(age);
			
			boolean isMale = random.nextBoolean(); // 남성 true 여성 false
			if (isMale) {
				memberVo.setGender("남");
			} else {
				memberVo.setGender("여");
			}
			
			memberVo.setEmail("user" + i + "@user.com");
			memberVo.setRegDate(new Timestamp(System.currentTimeMillis()));
			memberVo.setAddress("부산시");
			memberVo.setTel("010-1234-5678");
			
			memberDao.addMember(memberVo);
			System.out.println("insert 성공!");
		}
		
		List<MemberVo> list = memberDao.getAllMembers();
		for (MemberVo memberVo : list) {
			System.out.println(memberVo);
		}
		
		System.out.println("======== getMemberById 테스트 =========");
		
		String testId = "user0";
		
		MemberVo memberVo = memberDao.getMemberById(testId);
		System.out.println(memberVo);
		
		System.out.println("======== update 테스트 =========");
		
		memberVo.setName("이순신"); // 수정될 이름값 설정
		memberDao.update(memberVo);
		
		MemberVo getMemberVo = memberDao.getMemberById(testId);
		System.out.println(getMemberVo);
		
		System.out.println("======== deleteById 테스트 =========");
		
		memberDao.deleteById(testId);
		
		MemberVo getMemberVo2 = memberDao.getMemberById(testId);
		System.out.println(getMemberVo2);
		
	} // main
	
}






