package org.doit.ik.mapper;

import org.apache.ibatis.annotations.Select;

public interface TimeMapper {

	String getTime(); //실제 DB서버에서 시간을 읽어 오겠다
	
	@Select("SELECT SYSDATE+1 FROM dual")
	String getNextTime(); //현재 날짜 + 하루를 더 함
	
}
