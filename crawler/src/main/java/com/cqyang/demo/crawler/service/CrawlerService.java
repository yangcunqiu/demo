package com.cqyang.demo.crawler.service;

import com.cqyang.demo.crawler.model.request.AddCrawlerRequest;
import com.cqyang.demo.crawler.model.vo.CrawlerFittingModuleVO;

import java.util.List;

public interface CrawlerService {

    List<CrawlerFittingModuleVO> listFittingModule(Integer type);

    /**
     * 新增爬虫
     */
    void createCrawler(AddCrawlerRequest addCrawlerRequest);
}
