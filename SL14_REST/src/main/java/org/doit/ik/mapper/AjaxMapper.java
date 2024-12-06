package org.doit.ik.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.doit.ik.domain.EmpVO;

public interface AjaxMapper {
    
   List<EmpVO> selectByDeptEmps(@Param("deptno") int deptno); // 해당 사원 번호 정보 반환
    
}