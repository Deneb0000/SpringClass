package org.doit.ik.persistence;

import java.sql.SQLException;
import java.util.List;

import org.doit.ik.domain.NoticeVO;


public interface NoticeDao {
	
	public int getCount(String field, String query) throws ClassNotFoundException, SQLException;

	
	//공지사항 목록을 가져오는 ....  페이지번호   검색조건       검색어 
	public List<NoticeVO> getNotices(int page, String field, String query) 
			throws ClassNotFoundException, SQLException;
	
	
	public int delete(String seq) throws ClassNotFoundException, SQLException;
	
	
	public int update(NoticeVO notice) throws ClassNotFoundException, SQLException;
		
	
	
	// 공지사항 보기
	public NoticeVO getNotice(String seq) throws ClassNotFoundException, SQLException;

	public int insert(NoticeVO notice) throws ClassNotFoundException, SQLException;
	
	  
}
