package com.cqyang.demo.crawler.model;

import com.cqyang.demo.crawler.exception.CrawlerException;
import lombok.Data;

/**
 * 通用返回对象
 * @param <T>
 */
@Data
public class CrawlerResult<T> {

    private int code;
    private String message;
    private T data;

    public static <T> CrawlerResult<T> success(T t) {
        CrawlerResult<T> result = new CrawlerResult<>();
        result.setCode(200);
        result.setMessage("success");
        result.setData(t);
        return result;
    }

    public static <T> CrawlerResult<T> success() {
        return success(null);
    }

    public static <T> CrawlerResult<T> fail(CrawlerException crawlerException) {
        CrawlerResult<T> result = new CrawlerResult<>();
        result.setCode(crawlerException.getCode());
        result.setMessage(crawlerException.getErrorMessage());
        return result;
    }
}
