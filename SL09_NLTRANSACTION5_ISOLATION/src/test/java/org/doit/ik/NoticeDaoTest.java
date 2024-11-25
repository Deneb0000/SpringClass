package org.doit.ik;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.doit.ik.domain.NoticeVO;
import org.doit.ik.persistence.NoticeDaoImpl;
import org.junit.jupiter.api.Test;

class NoticeDaoTest {

	@Test
	void testNoticeInsert() {
		// System.out.println("hello!");
		NoticeDaoImpl noticeDao = new NoticeDaoImpl();
		NoticeVO notice = new NoticeVO();
		notice.setTitle("첫 번째 게시글");
		notice.setContent("내 첫 글이다앙");
		
		int rowCount = 0;
		try {
			rowCount =  noticeDao.insert(notice);
			System.out.println(rowCount);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("공지사항 추가!!");
	}

}
