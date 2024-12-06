package org.doit.ik.mapper;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Param;
import org.doit.ik.domain.MemberVO;

// 인터페이스로 변경~~
public interface MemberMapper {

	//회원 정보
	public MemberVO getMember(String id) throws ClassNotFoundException, SQLException;
	
	
	public int insert(MemberVO member) throws ClassNotFoundException, SQLException;
	
	//회원 정보 O + 권한 정보 O를 모두 돌려주는 함수
	public MemberVO read(@Param("userid") String userid) throws ClassNotFoundException, SQLException;
	
}
