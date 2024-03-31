package com.cqyang.demo.crawler.medical.model;

import lombok.Data;

/**
 * 医保局返回的通用加密响应
 */
@Data
public class MedicalEncryptResponse {
    private int code;
    private MedicalEncryptData data;
    private String message;
    private String timestamp;
    private String type;
}

