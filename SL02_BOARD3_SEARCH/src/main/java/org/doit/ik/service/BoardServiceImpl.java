package org.doit.ik.service;

import java.util.List;

import org.doit.ik.domain.BoardVO;
import org.doit.ik.domain.Criteria;
import org.doit.ik.mapper.BoardMapper;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
@AllArgsConstructor
public class BoardServiceImpl implements BoardService{

	//@Autowired
	//@Setter
	private BoardMapper boardMapper;
	
	@Override
	public List<BoardVO> getList() {

		log.info("> BoardServiceImol.getList()...");
		return this.boardMapper.getList();
	}

	@Override
	public void register(BoardVO board) {
		log.info("> BoardServiceImol.register()...");
		//this.boardMapper.insert(board);
		this.boardMapper.insertSelectKey(board);
	}

	@Override
	public BoardVO get(Long bno) {
		log.info("> BoardServiceImol.get()...");
		return this.boardMapper.read(bno);
	}

	@Override
	public boolean modify(BoardVO board) {
		log.info("> BoardServiceImol.modify()...");
		return this.boardMapper.update(board) == 1;
	}

	@Override
	public boolean remove(Long bno) {
		log.info("> BoardServiceImol.remove()...");
		return this.boardMapper.delete(bno) == 1;
	}

	@Override
	public List<BoardVO> getListWithPaging(Criteria criteria) {
		log.info("> BoardServiceImol.getListWithPaging()...");
		return this.boardMapper.getListWithPaging(criteria);
	}

	@Override
	public int getTotal(Criteria criteria) {
		log.info("> BoardServiceImol.getListWithPaging()...");
		return this.boardMapper.getTotalCount(criteria);
	}


}
