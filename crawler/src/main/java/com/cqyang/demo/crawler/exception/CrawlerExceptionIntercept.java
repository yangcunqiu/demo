package com.cqyang.demo.crawler.exception;

import com.cqyang.demo.crawler.model.CrawlerResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class CrawlerExceptionIntercept {


    @ResponseBody
    @ExceptionHandler(CrawlerException.class)
    public CrawlerResult<?> crawlerExceptionIntercept(CrawlerException crawlerException) {
        log.error("Intercept {}", crawlerException.getErrorMessage());
        return CrawlerResult.fail(crawlerException);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public CrawlerResult<?> exceptionIntercept(Exception exception) {
        exception.printStackTrace();
        return CrawlerResult.fail(new CrawlerException(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}
