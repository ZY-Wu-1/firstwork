package com.example.demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//记录Controller和Service方法的执行时间，用于性能监控和日志追踪
//AOP切面 - 日志记录
@Aspect // 标记为切面类
@Component // 交给Spring管理
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    //切入点: 所有Controller方法
    @Pointcut("execution(* com.example.demo.controller..*.*(..))")
    public void controllerPointcut() {}

    //切入点: 所有Service方法
    @Pointcut("execution(* com.example.demo.service..*.*(..))")
    public void servicePointcut() {}

    //环绕通知: 环绕Controller方法执行
    @Around("controllerPointcut()")

    public Object logController(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        log.info("[AOP] Controller开始: {}", methodName);
        long start = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();
            long cost = System.currentTimeMillis() - start;
            log.info("[AOP] Controller结束: {}, 耗时: {}ms", methodName, cost);
            return result;
        } catch (Throwable e) {
            log.error("[AOP] Controller异常: {}, 原因: {}", methodName, e.getMessage());
            throw e;
        }
    }

    //环绕通知: 环绕Service方法执行
    @Around("servicePointcut()")
    public Object logService(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        log.info("[AOP] Service开始: {}", methodName);
        long start = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();
            long cost = System.currentTimeMillis() - start;
            log.info("[AOP] Service结束: {}, 耗时: {}ms", methodName, cost);
            return result;
        } catch (Throwable e) {
            log.error("[AOP] Service异常: {}, 原因: {}", methodName, e.getMessage());
            throw e;
        }
    }
}