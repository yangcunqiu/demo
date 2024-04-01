package com.cqyang.demo.crawler.model.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 爬虫的配置, 主要保存这个爬虫要用哪些processor pipeline等
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CrawlerConfig extends BasicPO{
    private Integer id;
    // 爬虫id
    private Integer crawlerInfoId;
    // processor的全限定名
    private String processorName;
    // pipeline的全限定名, pipeline可以有多个, 所以这个是一个序列化之后集合 ["xxx","xxx"]
    private String pipeline;
}
