package com.cqyang.demo.crawler.core;

import com.cqyang.demo.crawler.model.CrawlerPipelineConfig;
import com.cqyang.demo.crawler.model.enums.CrawlerModuleCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

/**
 * 默认pipeline
 */
@Component
@Slf4j
public class CrawlerDefaultPipeline extends CrawlerPipeline<CrawlerPipelineConfig> {


    @Override
    protected String getModuleCode() {
        return CrawlerModuleCodeEnum.DEFAULT_PIPELINE.getCode();
    }

    @Override
    protected void execute(ResultItems resultItems, Task task) {
        log.info("CrawlerDefaultPipeline execute");
    }
}
