package org.doit.ik.service;

import java.util.List;

import org.doit.ik.domain.BoardVO;
import org.doit.ik.domain.Criteria;

public interface BoardService {

	List<BoardVO> getList();	//페이징 처리가 안된 서비스에 있는 메서드
	
	List<BoardVO> getListWithPaging(Criteria criteria);	//페이징 처리가 된 서비스에 있는 메서드
	int getTotal(Criteria criteria); //총레코드수
	
	void register(BoardVO board);
	BoardVO get(Long bno);
	
	boolean modify(BoardVO board);
	
	boolean remove(Long bno);
	
}
