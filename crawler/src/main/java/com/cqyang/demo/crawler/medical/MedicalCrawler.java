package com.cqyang.demo.crawler.medical;

import com.cqyang.demo.crawler.medical.builder.FixedHospitalBuilder;
import com.cqyang.demo.crawler.medical.builder.MedicalBuilder;
import com.cqyang.demo.crawler.medical.model.enums.MedicalEnum;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MedicalCrawler {

    Map<Integer, MedicalBuilder<?>> medicalBuilderMap = new HashMap<>();

    private static final Spider spider;
    static {
        spider = Spider.create(new MedicalProcessor())
                .addPipeline(new MedicalPipeline())
                .setExitWhenComplete(false);
        spider.runAsync();
    }


    public void listTask(int medicalType) {
        try {
            medicalBuilderMap.put(MedicalEnum.FIXED_HOSPITAL.getType(), new FixedHospitalBuilder());
            MedicalBuilder<?> medicalBuilder = medicalBuilderMap.get(medicalType);
            Request request = medicalBuilder.buildRequest();
            Map<String, Object> map = new HashMap<>();
            map.put("medicalBuilder", medicalBuilder);
            request.setExtras(map);
            spider.addRequest(request);
        } catch (Exception e) {
            log.error("task fail, ", e);
        }
    }


}
