package org.doit.ik.persistence;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.doit.ik.domain.NoticeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Repository
//@Transactional
public class NoticeDaoImpl implements NoticeDao{

	@Autowired
	private NamedParameterJdbcTemplate npJdbcTemplate;

//	@Autowired
//	private DataSourceTransactionManager transactionManager;

//	@Autowired
//	private TransactionTemplate transactionTemplate;

	// 공지사항의 갯수 반환하는 메서드
	@Override
	public int getCount(String field, String query) throws ClassNotFoundException, SQLException
	{
		String sql = " SELECT COUNT(*) CNT "
				+ " FROM NOTICES "
				+ " WHERE "+field+" LIKE :query";

		MapSqlParameterSource paramSource= new MapSqlParameterSource();
		paramSource.addValue("query", query);

		return this.npJdbcTemplate.queryForObject(
				sql, paramSource, Integer.class);

	}

	// 공지사항의 목록을 List컬렉션으로 반환하는 메서드
	@Override
	public List<NoticeVO> getNotices(
			int page          // 현재 페이지 번호
			, String field    // 검색조건
			, String query    // 검색어
			) throws ClassNotFoundException, SQLException
	{               

		int srow = 1 + (page-1)*15; 
		int erow = 15 + (page-1)*15;        
		String sql = " SELECT * "
				+ "  FROM ( "
				+ "                 SELECT ROWNUM NUM, N.* "
				+ "                 FROM ("
				+ "                          SELECT * "
				+ "                          FROM NOTICES "
				+ "                          WHERE "+field+" LIKE :query "
				+ "                   ORDER BY REGDATE DESC"
				+ "                ) N"
				+ "  ) "
				+  " WHERE NUM BETWEEN :srow AND :erow ";

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("query","%"+query+"%");
		paramMap.put("srow", srow);
		paramMap.put("erow", erow);
		return this.npJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<NoticeVO>(NoticeVO.class));
	}

	// 공지사항 삭제하는 메서드  11:05 ~
	@Override
	public int delete(String seq) throws ClassNotFoundException, SQLException
	{

		String sql = " DELETE FROM notices "
				+ " WHERE seq = :seq";     

		MapSqlParameterSource paramSource= new MapSqlParameterSource();
		paramSource.addValue("seq", seq);      
		return this.npJdbcTemplate.update(sql, paramSource);
	}

	// 공지사항 수정하는 메서드
	@Override
	public int update(NoticeVO notice) throws ClassNotFoundException, SQLException{      
		String sql = "UPDATE NOTICES "
				+ " SET TITLE=:title, CONTENT=:content, FILESRC=:filesrc "
				+ " WHERE SEQ=:seq";
		/* 
      MapSqlParameterSource parameterSource = new MapSqlParameterSource();
      parameterSource.addValue("title", notice.getTitle());
      parameterSource.addValue("content", notice.getContent());
      parameterSource.addValue("filesrc", notice.getFilesrc());
      parameterSource.addValue("seq", notice.getSeq());      

      return this.npJdbcTemplate.update(sql,  parameterSource );
		 */

		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(notice);
		return this.npJdbcTemplate.update(sql,paramSource);
	}

	// 공지사항 보기
	@Override
	public NoticeVO getNotice(String seq) throws ClassNotFoundException, SQLException
	{
		String sql = "SELECT * "
				+ " FROM NOTICES "
				+ " WHERE SEQ= :seq ";   

		MapSqlParameterSource paramSource= new MapSqlParameterSource();
		paramSource.addValue("seq", seq);      

		return this.npJdbcTemplate.queryForObject(sql
				, paramSource
				, new BeanPropertyRowMapper<NoticeVO>(NoticeVO.class));
	}

	// 공지사항 추가하는 메서드
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int insert(NoticeVO notice) throws ClassNotFoundException, SQLException {
		String sql = "INSERT INTO NOTICES"
				+ " (SEQ, TITLE, CONTENT, WRITER, REGDATE, HIT, FILESRC)"
				+ " VALUES( "
				+ "(SELECT NVL(MAX(TO_NUMBER(SEQ)),0)+1 FROM NOTICES), :title, :content, :writer, SYSDATE, 0, :filesrc)";

		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(notice);      
		return this.npJdbcTemplate.update(sql,  paramSource);
	}

	
	//4. xml파일의 <tx:advice> 태그로 설정
	@Override
	public void insertAndPointUpOfMember(NoticeVO notice, String id) throws ClassNotFoundException, SQLException {
		//1. 공지사항 작성하는 코딩
		String sql = "INSERT INTO NOTICES"
				+ " (SEQ, TITLE, CONTENT, WRITER, REGDATE, HIT, FILESRC)"
				+ " VALUES( "
				+ "(SELECT NVL(MAX(TO_NUMBER(SEQ)),0)+1 FROM NOTICES), :title, :content, :writer, SYSDATE, 0, :filesrc)";

		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(notice);      
		this.npJdbcTemplate.update(sql, paramSource);

		//2. 포인트가 증가
		sql = "UPDATE MEMBER "
				+ " SET point = point + 1 "
				+ " WHERE ID = :id ";

		MapSqlParameterSource mParamSource = new MapSqlParameterSource();
		mParamSource.addValue("id", id);
		this.npJdbcTemplate.update(sql, mParamSource);
	}
	
	
	/*
	//3. 트랜젝션템플을 사용한 처리 
	@Override
	public void insertAndPointUpOfMember(NoticeVO notice, String id) throws ClassNotFoundException, SQLException {
		//1. 공지사항 작성하는 코딩
		String sql = "INSERT INTO NOTICES"
				+ " (SEQ, TITLE, CONTENT, WRITER, REGDATE, HIT, FILESRC)"
				+ " VALUES( "
				+ "(SELECT NVL(MAX(TO_NUMBER(SEQ)),0)+1 FROM NOTICES), :title, :content, :writer, SYSDATE, 0, :filesrc)";


		// 2. 포인트 증가
		String sql2 = " UPDATE MEMBER "
				+ " SET point = point + 1 "
				+ " WHERE ID = :id ";

		// p515 예제
		this.transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				// 1
				SqlParameterSource paramSource = new BeanPropertySqlParameterSource(notice);
				npJdbcTemplate.update(sql, paramSource);
				// 2
				MapSqlParameterSource mParamSource = new MapSqlParameterSource();
				mParamSource.addValue("id", id);
				npJdbcTemplate.update(sql2, mParamSource);
			}
		});

	}
	*/




	/*
	//2. 트랜젝션매니저를 사용한 처리 
	@Override
	public void insertAndPointUpOfMember(NoticeVO notice, String id) throws ClassNotFoundException, SQLException {
		//1. 공지사항 작성하는 코딩
		String sql = "INSERT INTO NOTICES"
				+ " (SEQ, TITLE, CONTENT, WRITER, REGDATE, HIT, FILESRC)"
				+ " VALUES( "
				+ "(SELECT NVL(MAX(TO_NUMBER(SEQ)),0)+1 FROM NOTICES), :title, :content, :writer, SYSDATE, 0, :filesrc)";


		//2. 포인트가 증가
		String sql2 = "UPDATE MEMBER "
				+ " SET point = point + 1 "
				+ " WHERE ID = :id ";

		TransactionDefinition definition = new DefaultTransactionDefinition();
		TransactionStatus status = this.transactionManager.getTransaction(definition );
		 try {
	         // 1
	         SqlParameterSource paramSource = new BeanPropertySqlParameterSource(notice);
	         this.npJdbcTemplate.update(sql, paramSource);
	         // 2
	         MapSqlParameterSource mParamSource = new MapSqlParameterSource();
	         mParamSource.addValue("id", id);
	         this.npJdbcTemplate.update(sql2, mParamSource);

	         this.transactionManager.commit(status);

	      } catch (Exception e) {

	         this.transactionManager.rollback(status);

	      }

	   }
	 */



	/*
	//1. 트랜젝션 처리되지 않은 메서드
	@Override
	public void insertAndPointUpOfMember(NoticeVO notice, String id) throws ClassNotFoundException, SQLException {
		//1. 공지사항 작성하는 코딩
		String sql = "INSERT INTO NOTICES"
				+ " (SEQ, TITLE, CONTENT, WRITER, REGDATE, HIT, FILESRC)"
				+ " VALUES( "
				+ "(SELECT NVL(MAX(TO_NUMBER(SEQ)),0)+1 FROM NOTICES), :title, :content, :writer, SYSDATE, 0, :filesrc)";

		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(notice);      
		this.npJdbcTemplate.update(sql, paramSource);

		//2. 포인트가 증가
		sql = "UPDATE MEMBER "
				+ " SET point = point + 1 "
				+ " WHERE ID = :id ";

		MapSqlParameterSource mParamSource = new MapSqlParameterSource();
		mParamSource.addValue("id", id);
		this.npJdbcTemplate.update(sql, mParamSource);
	}
	 */

}