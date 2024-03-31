package com.cqyang.demo.crawler.medical.model;

import lombok.Data;

/**
 * 医保局通用加密对象
 */
@Data
public class MedicalEncryptData {

    private MedicalEncData data;
    private String appCode;
    private String version;
    private String encType;
    private String timestamp;
    private String signData;
    private String signType;

}
