package com.cqyang.demo.problem.ClassCast;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
@Slf4j
public class GetMapAspect {

    @Around("execution(* com.cqyang.demo.problem.ClassCast.GetLongMapService.getLongMap())")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) {
        log.info("before method exec...");
        Map<Integer, String> map = new HashMap<>();
        map.put(2, "IntegerMapAspect");
        return map;
    }
}
