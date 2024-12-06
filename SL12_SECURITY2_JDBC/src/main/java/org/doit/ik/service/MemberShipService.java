package org.doit.ik.service;

import java.sql.SQLException;

import org.doit.ik.domain.NoticeVO;

// 트랜잭션 처리 위한 서비스 인터페이스!!
public interface MemberShipService {
	
	// 트랜잭션 처리할 메서드 서비스에 옮기기
	public void insertAndPointUpOfMember(NoticeVO notice, String id) throws ClassNotFoundException, SQLException;  
}
