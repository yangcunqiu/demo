package com.cqyang.demo.crawler.core;

import com.cqyang.demo.crawler.exception.CrawlerException;
import com.cqyang.demo.crawler.model.CrawlerFittingConfig;
import com.cqyang.demo.crawler.model.enums.CrawlerFittingModuleTypeEnum;
import com.cqyang.demo.crawler.model.enums.CrawlerModuleCodeEnum;
import com.cqyang.demo.crawler.model.vo.CrawlerFittingModuleVO;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 创建并组装Crawler
 * T 组装需要的配置
 * R 组装模块实例
 */
public abstract class CrawlerFittingManage<T extends CrawlerFittingConfig, R> {

    /**
     * 获取组装类型
     * @see CrawlerFittingModuleTypeEnum
     */
    public abstract Integer fittingType();

    /**
     * 获取组装的排序
     */
    public abstract Integer sort();

    /**
     * 获取模块在组装过程中限制的数量, 返回null代表不限制数量
     */
    protected abstract Integer fittingModuleCount();

    /**
     * 校验组装类型
     */
    public void verifyType(List<Integer> typeList) throws CrawlerException {
        Integer limitCount = fittingModuleCount();
        if (Objects.isNull(limitCount)) {
            return;
        }
        long count = typeList.stream().filter(type -> Objects.equals(type, fittingType())).count();
        if (count != limitCount) {
            throw new CrawlerException(String.format("组装类型: %s 传入数量校验失败, 需要%d, 传入%d", fittingType(), limitCount, count));
        }
    }

    /**
     * 获取所有的模块用作展示
     */
    public List<CrawlerFittingModuleVO> list() {
        return listCrawlerModule().stream()
                .map(module -> (CrawlerModule)module)
                .filter(module -> Objects.nonNull(module.getModuleCode()))
                .map(module -> {
                    String moduleCode = module.getModuleCode();
                    return CrawlerModuleCodeEnum.getByCode(moduleCode);
                })
                .filter(Objects::nonNull)
                .map(moduleCodeEnum -> {
                    CrawlerFittingModuleVO vo = new CrawlerFittingModuleVO();
                    vo.setType(moduleCodeEnum.getModuleTypeEnum().getType());
                    vo.setCode(moduleCodeEnum.getCode());
                    vo.setClassName(moduleCodeEnum.getClassName());
                    vo.setDesc(moduleCodeEnum.getDesc());
                    return vo;
                }).collect(Collectors.toList());
    }

    /**
     * 获取所有模块
     */
    protected abstract List<? extends R> listCrawlerModule();

    /**
     * 组装Crawler
     */
    public Crawler fittingCrawler(Crawler crawler, List<String> codeList) {
        return fitting(crawler, getByModuleCode(codeList));
    }

    /**
     * 根据模块的codeList获取实例list
     */
    private List<R> getByModuleCode(List<String> codeList) {
        return listCrawlerModule().stream()
                .filter(module -> codeList.contains(((CrawlerModule)module).getModuleCode())).collect(Collectors.toList());
    }

    /**
     * 组装
     */
    protected abstract Crawler fitting(Crawler crawler, List<R> rList);


}
