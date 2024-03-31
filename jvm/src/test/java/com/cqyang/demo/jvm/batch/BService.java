//package com.cqyang.demo.batch;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.CountDownLatch;
//
//public class BService {
//    AService aService = new AService();
//
//    public List<Integer> get(List<Integer> ids) throws InterruptedException {
//        if (ids.size() > 100) {
//            throw new RuntimeException("最多支持100");
//        }
//
//        List<Integer> results = new ArrayList<>();
//        // 分批
//        CountDownLatch c = new CountDownLatch(ids.size() / 10);
////        for (int start = 0; start < ids.size(); start += 10) {
////            int end = Math.min(ids.size(), start + 10);
////            List<Integer> batch = ids.subList(start, end);
////            List<Integer> list = ;
////            new Thread(() -> {
////                list = aService.get(batch);
////                c.countDown();
////            }).start();
////
////
////
////            results.addAll(list);
//        }
//        c.await();
//        return results;
//    }
//}
//
//class AService {
//    public List<Integer> get(List<Integer> ids){
//        // 获取数据逻辑
//        System.out.println("分批后: " + ids);
//        return ids;
//    }
//}
