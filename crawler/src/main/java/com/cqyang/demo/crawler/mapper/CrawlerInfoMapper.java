package com.cqyang.demo.crawler.mapper;

import com.cqyang.demo.crawler.model.po.CrawlerInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CrawlerInfoMapper {

    void addCrawler(CrawlerInfo crawlerInfo);

}
