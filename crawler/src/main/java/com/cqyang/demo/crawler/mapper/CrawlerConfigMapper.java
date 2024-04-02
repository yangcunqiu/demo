package com.cqyang.demo.crawler.mapper;

import com.cqyang.demo.crawler.model.po.CrawlerConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CrawlerConfigMapper {

    void batchInsert(@Param("crawlerConfigList") List<CrawlerConfig> crawlerConfigList);

    List<CrawlerConfig> findByCrawlerUniqueKey(@Param("uniqueKey") long uniqueKey);
}
