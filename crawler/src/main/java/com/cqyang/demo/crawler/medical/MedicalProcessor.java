package com.cqyang.demo.crawler.medical;

import com.alibaba.fastjson.JSON;
import com.cqyang.demo.crawler.CrawlerProcessor;
import com.cqyang.demo.crawler.medical.builder.MedicalBuilder;
import com.cqyang.demo.crawler.medical.model.MedicalEncryptResponse;
import com.cqyang.demo.crawler.medical.model.MedicalPageResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

import java.util.Map;

@Slf4j
public class MedicalProcessor extends CrawlerProcessor {

//    @Override
//    public void process(Page page) {
//        String rawText = page.getRawText();
//        if (StringUtils.isNotBlank(rawText)) {
//            MedicalEncryptResponse medicalEncryptResponse = JSON.parseObject(rawText, MedicalEncryptResponse.class);
//            MedicalPageResponse medicalPageResponse = null;
//            try {
//                medicalPageResponse = EncryptUtil.decrypt(medicalEncryptResponse);
//            } catch (ScriptException e) {
//                throw new RuntimeException(e);
//            }
//            page.putField("page", medicalPageResponse);
//        }
//
//    }

    @Override
    protected void execute(Page page) {
        log.info("MedicalProcessor execute");
        String rawText = page.getRawText();
        if (StringUtils.isNotBlank(rawText)) {
            try {
                MedicalEncryptResponse medicalEncryptResponse = JSON.parseObject(rawText, MedicalEncryptResponse.class);
                Map<String, Object> extras = page.getRequest().getExtras();
                MedicalBuilder<?> medicalBuilder = (MedicalBuilder<?>) extras.get("medicalBuilder");
                MedicalPageResponse<?> decrypt = EncryptUtil.decrypt(medicalEncryptResponse, medicalBuilder);
                page.putField("page", decrypt);
            } catch (Exception e) {
                log.error("execute fail, ", e);
            }
        }
    }

    @Override
    public Site getSite() {
        return Site.me()
                .setCharset("utf-8")
                .setTimeOut(3000)
                .setRetryTimes(3)
                .setRetrySleepTime(1000);
    }
}
