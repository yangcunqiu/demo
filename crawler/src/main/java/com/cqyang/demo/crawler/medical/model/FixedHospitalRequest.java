package com.cqyang.demo.crawler.medical.model;

import lombok.Data;

/**
 * 定点医疗机构查询请求
 * param_data = {
 *     "addr": "",
 *     "regnCode": "110000",
 *     "medinsName": "",
 *     "sprtEcFlag": "",
 *     "medinsLvCode": "",
 *     "medinsTypeCode": "",
 *     "pageNum": 1,
 *     "pageSize": 10
 * }
 */
@Data
public class FixedHospitalRequest {
    private String addr;
    private String regnCode;
    private String medinsName;
    private String sprtEcFlag;
    private String medinsLvCode;
    private String medinsTypeCode;
    private int pageNum = 1;
    private int pageSize = 10;
}
