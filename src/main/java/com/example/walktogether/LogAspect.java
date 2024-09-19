package com.example.walktogether;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Aspect
@Component
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Around("execution(* com.example.walktogether.service.*.*(..))")
    public Object logExecutionStartAndEndTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();

        LocalDateTime endTime = LocalDateTime.now();

        logger.info("Method {} finished at {} with result {}", joinPoint.getSignature().getName(), endTime, result);

        return result;
    }
}
