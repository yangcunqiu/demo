package com.cqyang.demo.crawler.medical.model;

import lombok.Data;

/**
 * 医保局发起的通用加密请求
 */
@Data
public class MedicalEncryptRequest {
    private MedicalEncryptData data;
}


