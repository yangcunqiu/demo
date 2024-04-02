package com.cqyang.demo.crawler.service;

import com.cqyang.demo.crawler.model.enums.CrawlerSceneEnum;
import com.cqyang.demo.crawler.model.request.AddCrawlerRequest;
import com.cqyang.demo.crawler.model.vo.CrawlerFittingModuleVO;
import com.cqyang.demo.crawler.model.vo.CrawlerInfoVO;

import java.util.List;

public interface CrawlerService {

    List<CrawlerFittingModuleVO> listFittingModule(Integer type);

    /**
     * 新增爬虫
     */
    void createCrawler(AddCrawlerRequest addCrawlerRequest);

    /**
     * 查询所有爬虫
     */
    List<CrawlerInfoVO> list();

    /**
     * 查询爬虫详情
     * @param uniqueKey 唯一键
     */
    CrawlerInfoVO findByUniqueKey(long uniqueKey);

    /**
     * 启动爬虫
     * @param uniqueKey 唯一键
     * @see CrawlerSceneEnum
     *
     */
    void runCrawler(long uniqueKey);

    /**
     * 关闭爬虫
     * @param uniqueKey 唯一键
     * @see CrawlerSceneEnum
     */
    void closeCrawler(long uniqueKey);
}
