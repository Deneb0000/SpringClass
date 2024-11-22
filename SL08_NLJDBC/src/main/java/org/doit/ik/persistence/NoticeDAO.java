package org.doit.ik.persistence;

import java.sql.SQLException;
import java.util.List;

import org.doit.ik.domain.NoticeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class NoticeDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;


	public int getCount(String field, String query) throws ClassNotFoundException, SQLException
	{
		String sql = " SELECT COUNT(*) CNT "
				+ " FROM NOTICES WHERE "+field+" LIKE ? ";

		return this.jdbcTemplate.queryForObject(sql, Integer.class, "%"+query+"%");
	}


	public List<NoticeVO> getNotices(
			int page, 
			String field, 
			String query
			) throws ClassNotFoundException, SQLException
	{					
		int srow = 1 + (page-1)*15; // 1, 16, 31, 46, 61, ... an = a1 + (n-1)*d
		int erow = 15 + (page-1)*15; //15, 30, 45, 60, 75, ...

		String sql = " SELECT *"
				+ " FROM "
				+ " (SELECT ROWNUM NUM, N.* "
				+ " FROM (SELECT * "
				+ " FROM NOTICES WHERE "+field+" LIKE ? "
				+ " ORDER BY REGDATE DESC) N) "
				+ " WHERE NUM BETWEEN ? AND ?" ;

		return this.jdbcTemplate.query(sql , new Object[] {"%"+query+"%", srow, erow}
		,new BeanPropertyRowMapper<NoticeVO>(NoticeVO.class));
	}


	public int delete(String seq) throws ClassNotFoundException, SQLException
	{
		String sql = " DELETE FROM notices "
				+ " WHERE SEQ= ? ";

		return this.jdbcTemplate.update(sql, seq);
	}

	//공지사항을 수정하는 메서드
	public int update(NoticeVO notice) throws ClassNotFoundException, SQLException{

		String sql = " UPDATE NOTICES "
				+ " SET TITLE= ?, CONTENT= ?, FILESRC= ? WHERE SEQ= ? ";

		return this.jdbcTemplate.update(sql, notice.getTitle(), notice.getContent(), notice.getFilesrc(), notice.getSeq());
	}

	//공지사항 보기
	public NoticeVO getNotice(String seq) throws ClassNotFoundException, SQLException
	{
		String sql = " SELECT * "
				+ " FROM NOTICES WHERE SEQ= ?";

		return this.jdbcTemplate.queryForObject(sql, new Object[] {seq}
		, new BeanPropertyRowMapper<NoticeVO>(NoticeVO.class));
	}

	//공지사항 추가하는 메서드
	public int insert(NoticeVO notic) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String sql = " INSERT INTO NOTICES "
				+ " (SEQ, TITLE, CONTENT, WRITER, REGDATE, HIT, FILESRC) "
				+ " VALUES( "
				+ " (SELECT NVL(MAX(TO_NUMBER(SEQ)),0)+1 FROM NOTICES), ?, ?, ?, SYSDATE, 0, ?) ";

		return this.jdbcTemplate.update(sql, notic.getTitle(), notic.getContent(), notic.getWriter(), notic.getFilesrc());
	}
}
