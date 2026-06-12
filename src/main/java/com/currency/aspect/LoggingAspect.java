package com.currency.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.currency.service..*(..)) || execution(* com.currency.controller..*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        log.debug("Entering method: {}", methodName);

        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - start;
            log.info("Method {} executed in {} ms", methodName, executionTime);
            return result;
        } catch (Throwable throwable) {
            log.error("Exception in method {}: {}", methodName, throwable.getMessage());
            throw throwable;
        }
    }
}