package org.doit.ik.di3.test;

import org.doit.ik.di3.RecordViewImpl3;
import org.springframework.context.support.GenericXmlApplicationContext;

public class Ex04 {

	public static void main(String[] args) {

		//[자동 의존성 주입]	p.103
		String resourceLocation ="classpath:org/doit/ik/di3/application-context3.xml";
		GenericXmlApplicationContext ctx= new GenericXmlApplicationContext(resourceLocation);



		RecordViewImpl3 rvi = ctx.getBean("rvi", RecordViewImpl3.class);
		//RecordViewImpl rvi = ctx.getBean("getRecordViewImpl", RecordViewImpl.class);

		rvi.input();
		rvi.output();

		System.out.println("END.");
	}//main

}//class
