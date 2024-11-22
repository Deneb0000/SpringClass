package org.doit.ik.di4.test;

import org.doit.ik.di4.RecordViewImpl4;
import org.springframework.context.support.GenericXmlApplicationContext;

public class Ex05 {

	public static void main(String[] args) {

		//[자동 의존성 주입]	p.103
		String resourceLocation ="classpath:org/doit/ik/di4/application-context4.xml";
		GenericXmlApplicationContext ctx= new GenericXmlApplicationContext(resourceLocation);



		RecordViewImpl4 rvi = ctx.getBean("recordViewImpl4", RecordViewImpl4.class);
		//RecordViewImpl rvi = ctx.getBean("getRecordViewImpl", RecordViewImpl.class);

		rvi.input();
		rvi.output();

		System.out.println("END.");
	}//main

}//class
