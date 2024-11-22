package org.doit.ik.aop2;

import org.doit.ik.aop.Calculator;
import org.springframework.context.support.GenericXmlApplicationContext;

public class Ex02 {

	public static void main(String[] args) {
		// p204 스프링 AOP
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext
				("classpath:org/doit/ik/aop2/application-context2.xml");

		Calculator calc = ctx.getBean("calcProxy", Calculator.class);
		//Calculator calc = ctx.getBean("calc", Calculator.class);	//보조기능 장착을 안하고 싶을땐 'Proxy'를 빼면 된다
		
		System.out.println(calc.add(3, 5));
		
		System.out.println("END");

		
	}//main

}//class
