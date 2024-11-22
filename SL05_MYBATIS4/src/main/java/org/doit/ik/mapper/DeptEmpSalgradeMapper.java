package org.doit.ik.mapper;

import java.util.List;

import org.doit.ik.domain.DeptEmpSalgradeDTO;
import org.doit.ik.domain.EmpDTO;

public interface DeptEmpSalgradeMapper {

	//List<DeptEmpSalgradeDTO> getDeptEmpInfo();
	
	//1. 모든 부서 정보를 SELECT 하는 인터페이스 부서정보
	List<DeptEmpSalgradeDTO> getDept();
	
	//2. 해당되는 부서의 사원 정보를  SELECT하는 메서드
	List<EmpDTO> getEmpOfDept(int deptno);
}
