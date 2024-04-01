package com.cqyang.demo.crawler.model.po;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 定义一些公用的字段
 */
@Data
public class BasicPO {
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private boolean isDeleted;
}
