package com.wor.dash.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
/**
 * https://www.baeldung.com/spring-aop-test-aspect
 * AOP 연습용
 */
@Aspect
@Component
public class ExecutionTimeAspect {
    private Logger log = LoggerFactory.getLogger(ExecutionTimeAspect.class);

    @Around(value = "execution(* com.wor.dash.*.model.mapper.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long t = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        log.info("Execution time=" + (System.currentTimeMillis() - t) + "ms");
        return result;
    }
}