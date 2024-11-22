package org.doit.ik.aop4;

import org.doit.ik.aop.Calculator;
import org.springframework.context.support.GenericXmlApplicationContext;

public class Ex04 {

	public static void main(String[] args) {
		// p226 @Aspect 에노테이션 기반 AOP 
		
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext
				("classpath:org/doit/ik/aop4/application-context4.xml");

		//Calculator calc = ctx.getBean("calcProxy", Calculator.class);
		Calculator calc = ctx.getBean("calc4", Calculator.class);	//보조기능 장착을 안하고 싶을땐 'Proxy'를 빼면 된다
		
		System.out.println(calc.add(3, 5));
		
		System.out.println("END");

		
	}//main

}//class
