package org.doit.ik.aop2.advice;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.stereotype.Component;

@Component
public class LogPrintBeforeAdvice implements MethodBeforeAdvice{

	@Override
	public void before(
			Method method, //add()
			Object[] args, // 3,5
			Object target 
			) throws Throwable {
		String methodName =method.getName();
		Log log =LogFactory.getLog(this.getClass());
		//인증 체크 부분 코딩
		log.info(">>" + methodName + "(): LogPrintBeforeAdvice 호출됨");
		
	}

}
