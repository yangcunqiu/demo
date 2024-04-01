package com.cqyang.demo.crawler.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 通用异常
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CrawlerException extends RuntimeException{
    private int code;
    private String errorMessage;

    public CrawlerException(int code) {
        this.code = code;
    }

    public CrawlerException(String errorMessage) {
        this.code = 1000;
        this.errorMessage = errorMessage;
    }

    public CrawlerException(int code, String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }
}
