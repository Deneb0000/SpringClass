package org.doit.ik.aop3;

import org.doit.ik.aop.Calculator;
import org.springframework.context.support.GenericXmlApplicationContext;

public class Ex03 {

	public static void main(String[] args) {
		// p209 XML 스키마 기반의 AOP 처리 방법
		
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext
				("classpath:org/doit/ik/aop3/application-context3.xml");

		//Calculator calc = ctx.getBean("calcProxy", Calculator.class);
		Calculator calc = ctx.getBean("calc3", Calculator.class);	//보조기능 장착을 안하고 싶을땐 'Proxy'를 빼면 된다
		
		System.out.println(calc.add(3, 5));
		
		System.out.println("END");

		
	}//main

}//class
