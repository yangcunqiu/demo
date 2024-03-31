package com.cqyang.demo.crawler.medical.model;

import lombok.Data;
import java.util.List;

/**
 * 医保局分页返回通用对象
 */
@Data
public class MedicalPageResponse<T> {
    private int startRow;
    private int prePage;
    private boolean hasNextPage;
    private int nextPage;
    private int pageSize;
    private int endRow;
    private List<T> list;
    private int pageNum;
    private int navigatePages;
    private int total;
    private int navigateFirstPage;
    private int pages;
    private int size;
    private boolean isLastPage;
    private boolean hasPreviousPage;
    private int navigateLastPage;
    private boolean isFirstPage;
}
