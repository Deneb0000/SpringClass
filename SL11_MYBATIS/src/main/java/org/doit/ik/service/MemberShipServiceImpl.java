package org.doit.ik.service;

import java.sql.SQLException;

import org.doit.ik.domain.NoticeVO;
import org.doit.ik.mapper.NoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component // 필수. 빈 인식을 위해
public class MemberShipServiceImpl implements MemberShipService{

	@Autowired
	private NoticeMapper noticeDao;
	
	@Override
	//@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT) // 이 메서드만 트랜잭션 추가(aop추가)
	public void insertAndPointUpOfMember(NoticeVO notice, String id) throws ClassNotFoundException, SQLException {
		
		// 트랜잭션 중첩 테스트.
		this.noticeDao.insert(notice);// 얘는 들어가고
		
		notice.setTitle(notice.getTitle() + " : two"); 
		this.noticeDao.insert(notice);// 얘는 안들어갔음
	}

}
