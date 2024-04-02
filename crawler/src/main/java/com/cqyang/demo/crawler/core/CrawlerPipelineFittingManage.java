package com.cqyang.demo.crawler.core;

import com.alibaba.fastjson.JSON;
import com.cqyang.demo.crawler.model.CrawlerFittingConfig;
import com.cqyang.demo.crawler.model.CrawlerPipelineConfig;
import com.cqyang.demo.crawler.model.enums.CrawlerFittingModuleTypeEnum;
import org.apache.commons.lang3.StringUtils;
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
    protected CrawlerFittingConfig getFittingConfig(String fittingConfigJson) {
        if (StringUtils.isBlank(fittingConfigJson)) {
            return new CrawlerFittingConfig();
        }
        return JSON.parseObject(fittingConfigJson, CrawlerFittingConfig.class);
    }

    @Override
    protected Crawler fitting(Crawler crawler, List<Pipeline> pipelines, CrawlerFittingConfig fittingConfig) {
        return crawler.setPipelines(pipelines);
    }
}
