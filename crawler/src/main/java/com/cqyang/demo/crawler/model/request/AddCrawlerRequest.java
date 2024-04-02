package com.cqyang.demo.crawler.model.request;

import com.cqyang.demo.crawler.model.enums.CrawlerSceneEnum;
import com.sun.istack.internal.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class AddCrawlerRequest {

    /**
     * 爬虫场景
     * @see CrawlerSceneEnum
     */
    @NotNull
    private Integer scene;

    /**
     * 创建完是否立即启动
     */
    private boolean isRunAsync;

    /**
     * 创建组装爬虫的配置
     */
    @NotNull
    private List<CrawlerFittingModule> crawlerFittingModuleList;


}
