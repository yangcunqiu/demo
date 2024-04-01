package com.cqyang.demo.crawler.core;

import com.cqyang.demo.crawler.model.CrawlerPipelineConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Component
@Slf4j
public abstract class CrawlerPipeline<T extends CrawlerPipelineConfig> extends CrawlerModule implements Pipeline {

    @Override
    public void process(ResultItems resultItems, Task task) {
        log.info("CrawlerPipeline process");
        execute(resultItems, task);
    }

    protected abstract void execute(ResultItems resultItems, Task task);
}
