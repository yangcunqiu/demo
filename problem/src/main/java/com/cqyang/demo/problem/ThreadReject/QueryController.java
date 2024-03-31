package com.cqyang.demo.problem.ThreadReject;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@RestController
@RequestMapping("/thread")
@Slf4j
public class QueryController {

    private static final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000), new ThreadPoolExecutor.CallerRunsPolicy());

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @GetMapping("/thread/info")
    public Map<String, Object> getThreadPoolInfo() {
        Map<String, Object> map = new HashMap<>();
        map.put("poolSize", threadPool.getPoolSize());
        map.put("corePoolSize", threadPool.getCorePoolSize());
        map.put("activeCount", threadPool.getActiveCount());
        map.put("taskCount", threadPool.getTaskCount());
        map.put("queueSize", threadPool.getQueue().size());
        return map;
    }

    @GetMapping("/query")
    public void query(int count) {
        List<Integer> idList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            idList.add(i);
        }
        CountDownLatch countDownLatch = new CountDownLatch(1);
        log.info("query, thread: {}", Thread.currentThread().getName());
        threadPool.execute(() -> {
            log.info("query-execute, thread: {}, count: {}, queueSize: {}", Thread.currentThread().getName(), threadPool.getPoolSize(), threadPool.getQueue().size());
            queryByIds(idList, countDownLatch);



        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("query-countDownLatch done");
    }

    private void queryByIds(List<Integer> idList, CountDownLatch cdl) {
        CountDownLatch countDownLatch = new CountDownLatch(idList.size());
        log.info("queryByIds, thread: {}", Thread.currentThread().getName());
        threadPool.execute(() -> {
            log.info("queryByIds-execute, thread: {}, count: {}, queueSize: {}", Thread.currentThread().getName(), threadPool.getPoolSize(), threadPool.getQueue().size());
            invoke0(idList, countDownLatch);

            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("queryByIds-countDownLatch done");
            cdl.countDown();

        });

    }

    private void invoke0(List<Integer> idList, CountDownLatch countDownLatch) {
        log.info("invoke0, thread: {}", Thread.currentThread().getName());
        idList.forEach(id -> threadPool.execute(() -> {
            try {
                log.info("invoke0-execute, thread: {}, id: {}, count: {}, queueSize: {}", Thread.currentThread().getName(), id, threadPool.getPoolSize(), threadPool.getQueue().size());
                // 模拟IO操作
                Thread.sleep(1000);
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }));
    }


}
