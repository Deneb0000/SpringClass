package org.doit.ik.mapper.scott;

import java.util.ArrayList;

import org.doit.ik.domain.EmpDTO;

public interface EmpMapper {

	ArrayList<EmpDTO> selectEmp(int [] deptnos);
}
