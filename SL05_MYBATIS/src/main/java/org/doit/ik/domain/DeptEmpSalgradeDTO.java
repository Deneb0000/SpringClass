package org.doit.ik.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("DeptEmpSalgradeDTO")
public class DeptEmpSalgradeDTO {

	   private int deptno;
	   private String dname;
	   private int empno;
	   private String ename;
	   private String job;
	   private Date hiredate;
	   private double sal;
	   private int grade;
	   
	   
}
