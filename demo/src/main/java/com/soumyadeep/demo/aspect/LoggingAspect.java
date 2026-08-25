package com.soumyadeep.demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    
    @Around("execution(* com.soumyadeep.demo.*(..))"+
            "&& !within(com.soumyadeep.demo.aspect.LoggingAspect..*) ")
    public Object addMethodNameToMdc(ProceedingJoinPoint joinPoint) throws Throwable {
        // Get the method name
        String methodName = joinPoint.getSignature().getName();
        
        // Put into MDC (matches your XML key "Activity")
        MDC.put("Activity", methodName);
        
        try {
            return joinPoint.proceed();
        } finally {
            MDC.remove("Activity");
        }
    }
}
