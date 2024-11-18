package org.doit.ik.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.doit.ik.domain.BoardVO;

public interface BoardMapper {

	List<BoardVO> getList();
	
}
