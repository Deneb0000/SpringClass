package org.doit.ik.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.doit.ik.domain.NoticeVO;

// @Transactional 인터페이스에도 사용가능
public interface NoticeMapper {
	
	// NoticeMapper.xml만들지 않으려면? @Select() 주면 됨. 예시로 이것만 바꿔봄~~
	//@Select("SELECT COUNT(*) CNT FROM NOTICES  WHERE ${param1} LIKE '%${param2}%'")
	public int getCount(@Param("field") String field, @Param("query") String query) throws ClassNotFoundException, SQLException;
	
	// public int getCount(String field, String query) throws ClassNotFoundException, SQLException;

	
	//공지사항 목록을 가져오는 ....  페이지번호   검색조건       검색어 
	public List<NoticeVO> getNotices(int page, String field, String query) 
			throws ClassNotFoundException, SQLException;
	
	
	public int delete(String seq) throws ClassNotFoundException, SQLException;
	
	
	public int update(NoticeVO notice) throws ClassNotFoundException, SQLException;
		
	
	
	// 공지사항 보기
	public NoticeVO getNotice(String seq) throws ClassNotFoundException, SQLException;

	public int insert(NoticeVO notice) throws ClassNotFoundException, SQLException;
	
	// 트랜잭션 메서드 추가.
	// 새 글 작성시 포인트 + 1 증가.
	// 서비스 인터페이스로 이동!!
	// public void insertAndPointUpOfMember(NoticeVO notice, String id) throws ClassNotFoundException, SQLException;  
	
	/* 트랜잭션 */
	
	// 조회수 증가
	public void hitUp(String seq) throws ClassNotFoundException, SQLException ;   
	
	// 조회수 가져오기
	public int getHit(String seq) throws ClassNotFoundException, SQLException ;
	
}


