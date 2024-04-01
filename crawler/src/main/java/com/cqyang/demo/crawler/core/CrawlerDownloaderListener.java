package com.cqyang.demo.crawler.core;

import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.SpiderListener;

/**
 * 保存downloader的log
 */
@Component
public class CrawlerDownloaderListener implements SpiderListener {

    @Override
    public void onSuccess(Request request) {

    }

    @Override
    public void onError(Request request, Exception e) {

    }
}
