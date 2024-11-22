package org.doit.ik.persistence;

import java.sql.SQLException;

import org.doit.ik.domain.MemberVO;

// 인터페이스로 변경~~
public interface MemberDao {

	
	public MemberVO getMember(String id) throws ClassNotFoundException, SQLException;
	
	
	public int insert(MemberVO member) throws ClassNotFoundException, SQLException;
	
}
