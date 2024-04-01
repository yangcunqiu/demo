package com.cqyang.demo.crawler.core;

import com.cqyang.demo.crawler.model.CrawlerFittingConfig;
import com.cqyang.demo.crawler.model.CrawlerPipelineConfig;
import com.cqyang.demo.crawler.model.enums.CrawlerFittingModuleTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

@Component
public class CrawlerPipelineFittingManage extends CrawlerFittingManage<CrawlerFittingConfig, Pipeline> {

    @Autowired
    private List<CrawlerPipeline<? extends CrawlerPipelineConfig>> pipelines;

    @Override
    public Integer fittingType() {
        return CrawlerFittingModuleTypeEnum.PIPELINE.getType();
    }

    @Override
    public Integer sort() {
        return CrawlerFittingModuleTypeEnum.PIPELINE.getSort();
    }

    @Override
    protected Integer fittingModuleCount() {
        return null;
    }

    @Override
    public List<? extends Pipeline> listCrawlerModule() {
        return pipelines;
    }

    @Override
    protected Crawler fitting(Crawler crawler, List<Pipeline> pipelines) {
        return crawler.setPipelines(pipelines);
    }
}
