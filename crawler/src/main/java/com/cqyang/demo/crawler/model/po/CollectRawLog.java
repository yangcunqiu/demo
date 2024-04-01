package com.cqyang.demo.crawler.model.po;

import com.cqyang.demo.crawler.model.enums.CollectTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Request维度log
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CollectRawLog extends BasicPO {

    private Long id;
    // 爬虫
    private Integer crawlerInfoId;
    private Integer crawlerScene;;
    private String crawlerSceneDesc;;
    // 任务
    private Integer taskInfoId;
    private String taskTag;
    /**
     * 采集类型
     * @see CollectTypeEnum
     */
    private int collectType;
    // 采集id, 有可能是列表的, 也有可能是详情的
    private Long collectId;
    // 原始请求
    private String rawRequest;
    // 原始响应
    private String rawResponse;
}
