package com.cqyang.demo.crawler.medical;

import com.alibaba.fastjson.JSON;
import com.cqyang.demo.crawler.core.CrawlerProcessor;
import com.cqyang.demo.crawler.medical.builder.MedicalBuilder;
import com.cqyang.demo.crawler.medical.model.MedicalEncryptResponse;
import com.cqyang.demo.crawler.medical.model.MedicalPageResponse;
import com.cqyang.demo.crawler.medical.model.MedicalProcessConfig;
import com.cqyang.demo.crawler.model.enums.CrawlerModuleCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class MedicalProcessor extends CrawlerProcessor<MedicalProcessConfig> {

    @Override
    public String getModuleCode() {
        return CrawlerModuleCodeEnum.MEDICAL_PROCESSOR.getCode();
    }

    @Override
    protected List<Request> buildRequest(MedicalProcessConfig config) {
        return null;
    }

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
