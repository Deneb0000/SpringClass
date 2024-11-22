package org.doit.ik.aop2.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
@Component
public class LogPrintAroundAdvice implements MethodInterceptor{

	@Override
	public Object invoke(MethodInvocation method) throws Throwable {
	      StopWatch sw = new StopWatch();
	      sw.start();
	      // long start = System.nanoTime();
	      Log log = LogFactory.getLog(this.getClass());
	      String methodName = method.getMethod().getName();
	      log.info("> " + methodName +"() start.");   
	      
	      Object result = method.proceed();
	      
	      log.info("> " + methodName +"() end.");
	      
	      sw.stop();
	      log.info("> " + methodName +"() 처리 시간 :  " + sw.getTotalTimeMillis() +"ms");
	      
	      return result;
	   }

}
