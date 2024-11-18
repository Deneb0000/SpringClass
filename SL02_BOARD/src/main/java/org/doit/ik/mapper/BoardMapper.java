package org.doit.ik.mapper;

import java.util.List;

import org.doit.ik.domain.BoardVO;

public interface BoardMapper {
	
	List<BoardVO> getList();
	void insert(BoardVO board);// 새글쓰기
	void insertSelectKey(BoardVO board);// 새글쓰기+글번호(pk) 반환
	
	BoardVO read(Long bno);
	
	int update(BoardVO board); //게시글수정

	int delete(Long bno);
}
