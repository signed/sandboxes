package com.github.signed.sanboxes.spring.advices;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class ExecutionTimeLogger {

    private static final Logger logger = LoggerFactory.getLogger(ExecutionTimeLogger.class);

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestMapping() {
    }

    @Around("requestMapping()")
    public Object profile(ProceedingJoinPoint pjp) throws Throwable {
        StopWatch sw = new StopWatch();
        String name = pjp.getSignature().getName();
        try {
            sw.start();
            return pjp.proceed();
        } catch (Exception ex) {
            logger.error("an error occurred", ex);
            throw ex;
        } finally {
            sw.stop();
            logger.info("STOPWATCH: " + sw.getLastTaskTimeMillis() + " - " + name);
        }
    }
}