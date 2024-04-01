package com.cqyang.demo.crawler.model.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 任务信息表
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TaskInfo extends BasicPO {

    private Integer id;
    // 爬虫
    private Integer CrawlerInfoId;
    private Integer crawlerScene;;
    private String crawlerSceneDesc;;
    // 任务标识
    private String tag;
}
