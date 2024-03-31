//package com.cqyang.demo.problem.ClassCast;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//import java.util.Set;
//
//@Slf4j
//@Component
//public class Runner implements CommandLineRunner {
//
//    // map 定义是 Map<Long, String>, getLongMapService.getLongMap() 返回的也是 Map<Long, String>,
//    // forEach循环的时候 Long longKey = key; 报类型转换异常, java.lang.Integer cannot be cast to java.lang.Long
//    // Set<Long> keySet = map.keySet(); 可以正常执行, 而且编译过程中不报错, 运行才报错
//
//    // 1. 用idea进行debug的时候看到 getLongMapService 类型名称前面有CGLIB之类的, 那么应该想到 getLongMapService 被代理了
//    // 2. 为什么编译不报错, 是java中没有真正的泛型, 会被擦除的
//
//    @Autowired
//    private GetLongMapService getLongMapService;
//
//    @Override
//    public void run(String... args) throws Exception {
//        log.info("Runner running ==========================");
//
//        Map<Long, String> map = getLongMapService.getLongMap(); // 被GetMapAspect代理
//        log.info("getLongMapService: {}", getLongMapService);
//
//        Set<Long> keySet = map.keySet();
//        log.info("keySet: {}", keySet);
//
//        keySet.forEach(key -> {
//            Long longKey = key; // java.lang.ClassCastException: java.lang.Integer cannot be cast to java.lang.Long
//        });
//    }
//}
