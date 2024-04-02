package com.cqyang.demo.crawler.core;

import com.alibaba.fastjson.JSON;
import com.cqyang.demo.crawler.model.CrawlerFittingConfig;
import com.cqyang.demo.crawler.model.CrawlerProcessorConfig;
import com.cqyang.demo.crawler.model.enums.CrawlerFittingModuleTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CrawlerProcessorFittingManage extends CrawlerFittingManage<CrawlerFittingConfig, CrawlerProcessor<? extends CrawlerProcessorConfig>> {

    @Autowired
    private List<CrawlerProcessor<? extends CrawlerProcessorConfig>> processors;

    @Override
    public Integer fittingType() {
        return CrawlerFittingModuleTypeEnum.PROCESSOR.getType();
    }

    @Override
    public Integer sort() {
        return CrawlerFittingModuleTypeEnum.PROCESSOR.getSort();
    }

    @Override
    protected Integer fittingModuleCount() {
        return 1;
    }

    @Override
    public List<CrawlerProcessor<? extends CrawlerProcessorConfig>> listCrawlerModule() {
        return processors;
    }

    @Override
    protected CrawlerFittingConfig getFittingConfig(String fittingConfigJson) {
        if (StringUtils.isBlank(fittingConfigJson)) {
            return new CrawlerFittingConfig();
        }
        return JSON.parseObject(fittingConfigJson, CrawlerFittingConfig.class);
    }

    @Override
    protected Crawler fitting(Crawler crawler, List<CrawlerProcessor<? extends CrawlerProcessorConfig>> crawlerProcessors, CrawlerFittingConfig fittingConfig) {
        CrawlerProcessor<? extends CrawlerProcessorConfig> crawlerProcessor = crawlerProcessors.get(0);
        crawler = Crawler.create(crawlerProcessor);
        return crawler;
    }
}
