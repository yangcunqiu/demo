package com.cqyang.demo.crawler.model.po;

import com.cqyang.demo.crawler.model.enums.CollectStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 采集列表表
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CollectList extends BasicPO {
    private Long id;
    // 爬虫
    private Integer crawlerInfoId;
    private Integer crawlerScene;;
    private String crawlerSceneDesc;;
    // 任务
    private Integer taskInfoId;
    private String taskTag;
    // 唯一标识, 主要用来规避重复采集
    private String uniqueId;
    // 关键词json, 存这次采集所需要的keyword, 如果要重试这一次采集, 可以只依赖这个字段来重新构建一模一样的Request
    private String keyword;
    // 采集到的数据
    private String data;
    /**
     * 采集状态
     * @see CollectStatusEnum
     */
    private Integer status;
}
