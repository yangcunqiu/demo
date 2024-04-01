package com.cqyang.demo.crawler.model;

import lombok.Data;

import java.util.List;

/**
 * 通用返回分页对象
 */
@Data
public class CrawlerPage<T> {
    private List<T> list;
    private int pageNum;
    private int pageSize;
    private int pages;
    private long total;
    private boolean hasNextPage;
}
