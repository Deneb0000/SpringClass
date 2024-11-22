package org.doit.ik.di2;

import org.doit.ik.di.RecordImpl;
import org.doit.ik.di.RecordViewImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
// application == 자바 클래스 설정파일
//p 85p 자바코딩을 스프링 설정  == 직관적	
	
	//<bean id="record" class="org.doit.ik.di.RecordImpl"></bean>
	@Bean
	public RecordImpl record() {
		return new RecordImpl();
	}
	
	@Bean (name = "rvi")
	public RecordViewImpl getRecordViewImpl() {
		RecordViewImpl rvi = new RecordViewImpl();
		 rvi.setRecord(record());
	      return rvi;
	}
	/*<bean id="rvi" class="org.doit.ik.di.RecordViewImpl">
	
	
	<property name="record">
	<ref bean="record"/>
	</property>
	</bean>
	*/
	
}//class
