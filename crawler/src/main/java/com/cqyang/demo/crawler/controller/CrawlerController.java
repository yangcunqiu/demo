package com.cqyang.demo.crawler.controller;

import com.cqyang.demo.crawler.model.CrawlerResult;
import com.cqyang.demo.crawler.model.enums.CrawlerFittingModuleTypeEnum;
import com.cqyang.demo.crawler.model.request.AddCrawlerRequest;
import com.cqyang.demo.crawler.model.vo.CrawlerFittingModuleVO;
import com.cqyang.demo.crawler.model.vo.CrawlerInfoVO;
import com.cqyang.demo.crawler.service.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/crawlerInfo")
public class CrawlerController {

    @Autowired
    private CrawlerService crawlerService;

    /**
     * 按类型获取所有的自定义配置类
     * @see CrawlerFittingModuleTypeEnum
     */
    @GetMapping("/list/fitting/module")
    public CrawlerResult<List<CrawlerFittingModuleVO>> listFittingModule(@RequestParam(value = "type", required = false) Integer type) {
        return CrawlerResult.success(crawlerService.listFittingModule(type));
    }

    /**
     * 创建爬虫
     */
    @PostMapping("/create")
    public CrawlerResult<?> createCrawler(@RequestBody @Validated AddCrawlerRequest addCrawlerRequest) {
        crawlerService.createCrawler(addCrawlerRequest);
        return CrawlerResult.success();
    }

    /**
     * 查询所有爬虫
     */
    @GetMapping("/list")
    public CrawlerResult<List<CrawlerInfoVO>> list() {
        return CrawlerResult.success(crawlerService.list());
    }

    /**
     * 查询爬虫详情
     */
    @GetMapping("/find/uniqueKey")
    public CrawlerResult<CrawlerInfoVO> findByUniqueKey(@RequestParam("uniqueKey") Long uniqueKey) {
        return CrawlerResult.success(crawlerService.findByUniqueKey(uniqueKey));
    }
}
