package com.cqyang.demo.crawler;

import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 所有爬虫都要走的Process
 */
@Slf4j
public abstract class CrawlerProcessor implements PageProcessor {

    @Override
    public void process(Page page) {
        log.info("CrawlerProcessor process");
        // 保存一下最原始的数据
        execute(page);
    }

    protected abstract void execute(Page page);
}
