package org.doit.ik.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpDTO {

	private int empno;
	private String ename;
	private String job;
	private String mgr;
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date hiredate;
	private double comm;
	private double sal;
	
	//empDTO + salgradeDTO	1:1관계
	private SalgradeDTO salgradeDTO;
	
}
