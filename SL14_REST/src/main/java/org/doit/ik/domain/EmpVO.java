package org.doit.ik.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpVO {
	private int empno;
	   private String ename;
	   private String job;
	   @JsonIgnore  // JSON결과에 제외
	   private int mgr;
	   @JsonFormat(pattern = "yyyyMMddHHmmss" ) /// skfWk gudtlr qusghks
	   private Date hiredate;
	   private int sal;
	   private double comm;
	   private int deptno;
}
