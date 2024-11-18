package org.doit.ik.mapper;

import java.util.List;

import org.doit.ik.domain.BoardVO;
import org.doit.ik.domain.Criteria;

public interface BoardMapper {
	
	List<BoardVO> getList(); // 페이징 처리가 안된 getlist 함수
	List<BoardVO> getListWithPaging(Criteria criteria); // 페이징 처리가 된 getlist 함수
	int getTotalCount(Criteria criteria); //총 레코드 수를 반환
	
	void insert(BoardVO board);// 새글쓰기
	void insertSelectKey(BoardVO board);// 새글쓰기+글번호(pk) 반환
	
	BoardVO read(Long bno);
	
	int update(BoardVO board); //게시글수정

	int delete(Long bno);
}
