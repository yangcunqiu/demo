package com.cqyang.demo.crawler.core;

import com.cqyang.demo.crawler.model.CrawlerProcessorConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * 所有爬虫都要走的Process
 */
@Slf4j
@Component
public abstract class CrawlerProcessor<T extends CrawlerProcessorConfig> extends CrawlerModule implements PageProcessor {


    protected abstract List<Request> buildRequest(T t);

    @Override
    public void process(Page page) {
        log.info("CrawlerProcessor process");
        execute(page);

        // 保存一下最原始的数据

    }

    protected abstract void execute(Page page);


}
