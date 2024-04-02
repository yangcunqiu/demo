package com.cqyang.demo.crawler.mapper;

import com.cqyang.demo.crawler.model.po.CrawlerInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CrawlerInfoMapper {

    void insert(CrawlerInfo crawlerInfo);

    void updateStatusByUniqueKey(@Param("uniqueKey") long uniqueKey, @Param("status") int status);

    CrawlerInfo findBySceneAndNotStatus(@Param("scene") int scene, @Param("status") int status);

    List<CrawlerInfo> findAll();

    CrawlerInfo findByUniqueKey(@Param("uniqueKey") long uniqueKey);
}
