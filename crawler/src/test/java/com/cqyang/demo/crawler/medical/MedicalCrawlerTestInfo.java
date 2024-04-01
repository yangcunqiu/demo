package com.cqyang.demo.crawler.medical;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class MedicalCrawlerTestInfo {

    @Test
    public void testCrawler() {
        MedicalCrawler crawler = new MedicalCrawler();
        crawler.listTask(0);
        while (true){}
    }
}
