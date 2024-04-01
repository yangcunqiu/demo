package com.cqyang.demo.crawler.core;

import com.cqyang.demo.crawler.model.CrawlerProcessorConfig;
import com.cqyang.demo.crawler.model.enums.CrawlerModuleCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;

import java.util.List;

/**
 * 默认processor
 */
@Component
@Slf4j
public class CrawlerDefaultProcessor extends CrawlerProcessor<CrawlerProcessorConfig> {

    @Override
    public String getModuleCode() {
        return CrawlerModuleCodeEnum.DEFAULT_PROCESSOR.getCode();
    }

    @Override
    protected List<Request> buildRequest(CrawlerProcessorConfig config) {
        return null;
    }

    @Override
    protected void execute(Page page) {
        log.info("CrawlerDefaultProcessor execute");
    }
}
