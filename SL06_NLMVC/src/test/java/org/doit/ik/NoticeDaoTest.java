package org.doit.ik;

import java.sql.SQLException;

import org.doit.ik.domain.NoticeVO;
import org.doit.ik.persistence.NoticeDAO;
import org.junit.jupiter.api.Test;

class NoticeDaoTest {

	@Test
	void testNoticeInsert() {
		//System.out.println("hello World");
		NoticeDAO noticeDAO = new NoticeDAO();
		NoticeVO notice = new NoticeVO();
		notice.setTitle("첫번째 게시글");
		notice.setContent("첫 번째 게시글");

		int rowConut = 0;
		try {
			rowConut = noticeDAO.insert(notice);
			System.out.println(rowConut);
		} catch (ClassNotFoundException|SQLException e) {
			e.printStackTrace();
		}
		System.out.println("공지사항추가");
		
	}

}
