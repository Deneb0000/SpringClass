package org.doit.ik.di.test;

import org.doit.ik.di.RecordViewImpl;
import org.springframework.context.support.GenericXmlApplicationContext;

public class Ex02 {

	public static void main(String[] args) {
		// application-context.xml : 스프링 빈 객체를 생성 + DI  설정파일
		// ApplicationContext = 공장 = 스프링 컨테이너
		// XmlWeb[ApplicationContext]   p59
		String resourceLocations = "classpath:org/doit/ik/di/application-context.xml";
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext(resourceLocations);

		RecordViewImpl rvi = ctx.getBean("rvi", RecordViewImpl.class);

		rvi.input();
		rvi.output();

		System.out.println("END.");

	}//main

}//class
