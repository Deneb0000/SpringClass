package org.doit.ik.mapper;

import org.apache.ibatis.annotations.Select;

public interface TimeMapper {
	
	String getTime();
	//이제 impl 구현 안해도 돼 resources에 xml 만듬
	
	@Select("SELECT SYSDATE+1 FROM dual")
	String getNextTime(); // 하루 더한 날짜

}
