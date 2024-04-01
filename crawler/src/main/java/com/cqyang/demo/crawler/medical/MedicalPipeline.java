package com.cqyang.demo.crawler.medical;

import com.cqyang.demo.crawler.core.CrawlerPipeline;
import com.cqyang.demo.crawler.model.CrawlerPipelineConfig;
import com.cqyang.demo.crawler.model.enums.CrawlerModuleCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

@Slf4j
@Component
public class MedicalPipeline extends CrawlerPipeline<CrawlerPipelineConfig> {
    @Override
    protected String getModuleCode() {
        return CrawlerModuleCodeEnum.MEDICAL_PIPELINE.getCode();
    }

    @Override
    protected void execute(ResultItems resultItems, Task task) {

    }

//    @Override
//    public void process(ResultItems resultItems, Task task) {
//        log.info("MedicalPipeline process");
//        MedicalPageResponse<?> page = resultItems.get("page");
//        log.info("page: {}", JSON.toJSONString(page));
//    }
}
