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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository // 용도에 맞게.. 이걸 넣자.
// @Transactional // 트랜잭션 어노테이션. 이 안의 메서드 전부 트랜잭션 걸림
public class NoticeDaoImpl implements NoticeDao {

	@Autowired
	private NamedParameterJdbcTemplate npJdbcTemplate; // bean 생성한것 주입


	//@Autowired
	//private DataSourceTransactionManager transactionManager;

	//@Autowired
	//private TransactionTemplate transactionTemplate; // 주입.



	@Override
	public int getCount(String field, String query) throws ClassNotFoundException, SQLException
	{	
		// 쿼리만 살려두고, return jdbcTemplate주면 됨.
		String sql = "SELECT COUNT(*) CNT "
				+ " FROM NOTICES "
				+ " WHERE "+field+" LIKE :query"; // ?에 String query 이름 주기

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("query", query); // 매개변수값 넣어주기

		return this.npJdbcTemplate.queryForObject(sql, paramSource, Integer.class);
	}

	@Override
	//공지사항 목록을 가져오는 ....  페이지번호   검색조건       검색어 
	public List<NoticeVO> getNotices(int page, String field, String query) 
			throws ClassNotFoundException, SQLException
	{					

		// 페이징 위해서 넣은 변수
		int srow = 1 + (page-1)*15; // 1, 16, 31, 46, 61, ... an = a1 + (n-1)*d
		int erow = 15 + (page-1)*15; //15, 30, 45, 60, 75, ...

		String sql = "SELECT * FROM";
		sql += "(SELECT ROWNUM NUM, N.* FROM (SELECT * FROM NOTICES WHERE "+field+" LIKE :query ORDER BY REGDATE DESC) N)";
		sql += "WHERE NUM BETWEEN :srow AND :erow";


		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("query", "%"+query+"%");
		paramMap.put("srow", srow);
		paramMap.put("erow", erow);
		return this.npJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<NoticeVO>(NoticeVO.class));
	}

	@Override
	public int delete(String seq) throws ClassNotFoundException, SQLException
	{
		String sql = "DELETE FROM NOTICES WHERE SEQ=:seq";

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("seq", seq); // 매개변수값 넣어주기

		return this.npJdbcTemplate.update(sql, paramSource);
	}

	@Override
	public int update(NoticeVO notice) throws ClassNotFoundException, SQLException{


		String sql = "UPDATE NOTICES SET TITLE=:title, CONTENT=:content, FILESRC=:filesrc WHERE SEQ=:seq";

		/* 
	      MapSqlParameterSource parameterSource = new MapSqlParameterSource();
	      parameterSource.addValue("title", notice.getTitle());
	      parameterSource.addValue("content", notice.getContent());
	      parameterSource.addValue("filesrc", notice.getFilesrc());
	      parameterSource.addValue("seq", notice.getSeq());      
	      return this.npJdbcTemplate.update(sql,  parameterSource );
		 */

		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(notice);
		return this.npJdbcTemplate.update(sql, paramSource);

	}

	@Override
	// 공지사항 보기
	public NoticeVO getNotice(String seq) throws ClassNotFoundException, SQLException
	{
		String sql = "SELECT * FROM NOTICES WHERE SEQ=:seq ";
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("seq", seq); // 매개변수값 넣어주기

		return this.npJdbcTemplate.queryForObject(sql, paramSource, new BeanPropertyRowMapper<NoticeVO>(NoticeVO.class));

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int insert(NoticeVO notice) throws ClassNotFoundException, SQLException {

		// 1. 공지사항 작성
		String sql = "INSERT INTO NOTICES"
				+ " (SEQ, TITLE, CONTENT, WRITER, REGDATE, HIT, FILESRC)"
				+ " VALUES( "
				+ "(SELECT NVL(MAX(TO_NUMBER(SEQ)),0)+1 FROM NOTICES), :title, :content, :writer, SYSDATE, 0, :filesrc)";

		SqlParameterSource paramSourse = new BeanPropertySqlParameterSource(notice);
		this.npJdbcTemplate.update(sql, paramSourse);

		// 2. 포인트 증가
		sql =    "UPDATE MEMBER "
				+ " SET point = point + 1 "
				+ " WHERE id = :id";

		MapSqlParameterSource mParamSourse = new MapSqlParameterSource();
		mParamSourse.addValue("id", "golovech");
		int rowCount = this.npJdbcTemplate.update(sql, mParamSourse);
		return rowCount;

	}
	/*
	// 5. xml 파일 : <tx:advice> 태그로 설정
		@Override
		//@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT) // 이 메서드만 트랜잭션 추가(aop추가)
		public void insertAndPointUpOfMember(NoticeVO notice, String id) throws ClassNotFoundException, SQLException {

			// 트랜잭션 중에 또 트랜잭션 하겠다~

			insert(notice);

			notice.setTitle(notice.getTitle() + " : two");
			insert(notice);
		}
	 */
	/*
	// 트랜잭션 템플릿 사용해보자.
	@Override
	public void insertAndPointUpOfMember(NoticeVO notice, String id) throws ClassNotFoundException, SQLException {

		// 1. 공지사항 작성
		String sql = "INSERT INTO NOTICES"
				+ " (SEQ, TITLE, CONTENT, WRITER, REGDATE, HIT, FILESRC)"
				+ " VALUES( "
				+ "(SELECT NVL(MAX(TO_NUMBER(SEQ)),0)+1 FROM NOTICES), :title, :content, :writer, SYSDATE, 0, :filesrc)";



		// 2. 포인트 증가
		String sql2 =    "UPDATE MEMBER "
				+ " SET point = point + 1 "
				+ " WHERE id = :id";

		// p515
		this.transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				// 1
				SqlParameterSource paramSourse = new BeanPropertySqlParameterSource(notice);
				npJdbcTemplate.update(sql, paramSourse);

				// 2
				MapSqlParameterSource mParamSourse = new MapSqlParameterSource();
				mParamSourse.addValue("id", id);
				npJdbcTemplate.update(sql2, mParamSourse); // mParamSourse 이거 잘못주면 id 안넘어감!!

			}
		});




	}
	 */
	/*
	// 2. 트랜잭션 매니저 사용해보기~
	@Override
	public void insertAndPointUpOfMember(NoticeVO notice, String id) throws ClassNotFoundException, SQLException {

		// 1. 공지사항 작성
		String sql = "INSERT INTO NOTICES"
				+ " (SEQ, TITLE, CONTENT, WRITER, REGDATE, HIT, FILESRC)"
				+ " VALUES( "
				+ "(SELECT NVL(MAX(TO_NUMBER(SEQ)),0)+1 FROM NOTICES), :title, :content, :writer, SYSDATE, 0, :filesrc)";



		// 2. 포인트 증가
		String sql2 =    "UPDATE MEMBER "
				+ " SET point = point + 1 "
				+ " WHERE id = :id";

		TransactionDefinition definition = new DefaultTransactionDefinition();
		TransactionStatus status = this.transactionManager.getTransaction(definition );
		try {
			SqlParameterSource paramSourse = new BeanPropertySqlParameterSource(notice);
			this.npJdbcTemplate.update(sql, paramSourse);

			MapSqlParameterSource mParamSourse = new MapSqlParameterSource();
			mParamSourse.addValue("id", id);
			this.npJdbcTemplate.update(sql2, paramSourse);
			this.transactionManager.commit(status);
		} catch (Exception e) {
			this.transactionManager.rollback(status);
		}



	}
	 */
	/*
	// 1. 트랜잭션 처리 되지않은 메서드
	@Override
	public void insertAndPointUpOfMember(NoticeVO notice, String id) throws ClassNotFoundException, SQLException {

		// 1. 공지사항 작성
		String sql = "INSERT INTO NOTICES"
	              + " (SEQ, TITLE, CONTENT, WRITER, REGDATE, HIT, FILESRC)"
	              + " VALUES( "
	              + "(SELECT NVL(MAX(TO_NUMBER(SEQ)),0)+1 FROM NOTICES), :title, :content, :writer, SYSDATE, 0, :filesrc)";

		  SqlParameterSource paramSourse = new BeanPropertySqlParameterSource(notice);
	      this.npJdbcTemplate.update(sql, paramSourse);

	      // 2. 포인트 증가
	      sql =    "UPDATE MEMBER "
	      		+ " SET point = point + 1 "
	      		+ " WHERE id = :id";

	      MapSqlParameterSource mParamSourse = new MapSqlParameterSource();
	      mParamSourse.addValue("id", id);
	      this.npJdbcTemplate.update(sql, paramSourse);



	}
	 */

	// 격리레벨 트랜잭션 처리 (조회수 증가)
	@Override		// 격리레벨 설정하는 법.
	@Transactional(isolation = Isolation.DEFAULT) // DEFAULT : DBMS(오라클)에서 지원하는 [기본 격리수준]을 따름.
	public void hitUp(String seq) throws ClassNotFoundException, SQLException {
		String sql = "UPDATE notices "
				+ " SET hit = hit + 1 "
				+ " WHERE seq = :seq ";
		MapSqlParameterSource paramSource =new MapSqlParameterSource();
		paramSource.addValue("seq", seq);       
		this.npJdbcTemplate.update(sql, paramSource);
	}

	// 격리레벨 트랜잭션 처리 (조회수 가져오기)
	@Override
	@Transactional
	public int getHit(String seq) throws ClassNotFoundException, SQLException {
		String sql = "SELECT hit  "
				+ " FROM notices "
				+ " WHERE seq = :seq";

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("seq", seq);
		return this.npJdbcTemplate.queryForObject(sql, paramMap, Integer.class);      
	}

}
