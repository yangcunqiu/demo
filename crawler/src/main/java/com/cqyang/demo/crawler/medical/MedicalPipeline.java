package com.cqyang.demo.crawler.medical;

import com.alibaba.fastjson.JSON;
import com.cqyang.demo.crawler.medical.model.MedicalPageResponse;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Slf4j
public class MedicalPipeline implements Pipeline {

    @Override
    public void process(ResultItems resultItems, Task task) {
        log.info("MedicalPipeline process");
        MedicalPageResponse<?> page = resultItems.get("page");
        log.info("page: {}", JSON.toJSONString(page));
    }
}
