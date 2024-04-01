package com.cqyang.demo.crawler.service.impl;

import com.cqyang.demo.crawler.core.CrawlerDefaultPipeline;
import com.cqyang.demo.crawler.core.CrawlerDefaultProcessor;
import com.cqyang.demo.crawler.core.Crawler;
import com.cqyang.demo.crawler.core.CrawlerFittingManage;
import com.cqyang.demo.crawler.exception.CrawlerException;
import com.cqyang.demo.crawler.mapper.CrawlerInfoMapper;
import com.cqyang.demo.crawler.model.CrawlerFittingConfig;
import com.cqyang.demo.crawler.model.enums.CrawlerFittingModuleTypeEnum;
import com.cqyang.demo.crawler.model.enums.CrawlerSceneEnum;
import com.cqyang.demo.crawler.model.enums.CrawlerStatusEnum;
import com.cqyang.demo.crawler.model.po.CrawlerInfo;
import com.cqyang.demo.crawler.model.request.AddCrawlerRequest;
import com.cqyang.demo.crawler.model.request.CrawlerFittingModule;
import com.cqyang.demo.crawler.model.vo.CrawlerFittingModuleVO;
import com.cqyang.demo.crawler.service.CrawlerService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CrawlerServiceImpl implements CrawlerService {

    private final Map<Integer, Crawler> crawlerMap = new HashMap<>();

    @Autowired
    private List<CrawlerFittingManage<? extends CrawlerFittingConfig, ?>> fittingManages;
    private Map<Integer, CrawlerFittingManage<? extends CrawlerFittingConfig, ?>> fittingManageMap;

    @Autowired
    private CrawlerInfoMapper crawlerInfoMapper;

    @Override
    public List<CrawlerFittingModuleVO> listFittingModule(Integer type) {
        // 查全部
        if (Objects.isNull(type)) {
            return fittingManages.stream().sorted(Comparator.comparingInt(CrawlerFittingManage::sort)).flatMap(manage -> manage.list().stream()).collect(Collectors.toList());
        }
        // 按type查
        if (!CrawlerFittingModuleTypeEnum.isContain(type)) {
            throw new CrawlerException("type有误");
        }

        if (MapUtils.isEmpty(fittingManageMap)) {
            // 第一次被调用
            fittingManageMap = fittingManages.stream().collect(Collectors.toMap(CrawlerFittingManage::fittingType, Function.identity()));
        }
        CrawlerFittingManage<? extends CrawlerFittingConfig, ?> fittingManage = fittingManageMap.get(type);
        if (Objects.nonNull(fittingManage)) {
            return fittingManage.list();
        }
        return Collections.emptyList();
    }

    @Override
    public void createCrawler(AddCrawlerRequest addCrawlerRequest) {
        Integer scene = addCrawlerRequest.getScene();
        CrawlerSceneEnum sceneEnum = CrawlerSceneEnum.getCrawlerSceneByScene(scene);
        if (Objects.isNull(sceneEnum)) {
            throw new CrawlerException("scene有误");
        }

        if (Objects.nonNull(crawlerMap.get(scene))) {
            throw new CrawlerException("当前场景已存在爬虫, 请先关闭后再创建");
        }

        // 组装爬虫
        Crawler crawler = fitting(addCrawlerRequest.getFittingModules());

        // 持久化
        CrawlerInfo crawlerInfo = new CrawlerInfo();
        crawlerInfo.setScene(scene);
        crawlerInfo.setSceneDesc(sceneEnum.getDesc());
        crawlerInfo.setStatus(addCrawlerRequest.isRunAsync() ? CrawlerStatusEnum.RUN.getStatus() : CrawlerStatusEnum.AWAIT.getStatus());
        crawlerInfoMapper.addCrawler(crawlerInfo);

        // 记录一下
        crawlerMap.put(scene, crawler);

        // 启动爬虫
        if (addCrawlerRequest.isRunAsync()) {
            crawler.runAsync();
        }
    }

    private Crawler fitting(List<CrawlerFittingModule> fittingModules) {
        if (CollectionUtils.isEmpty(fittingModules)) {
            return getDefaultCrawler();
        }
        List<Integer> fittingTypeList = fittingModules.stream().map(CrawlerFittingModule::getType).collect(Collectors.toList());
        if (MapUtils.isEmpty(fittingManageMap)) {
            // 第一次被调用
            fittingManageMap = fittingManages.stream().collect(Collectors.toMap(CrawlerFittingManage::fittingType, Function.identity()));
        }
        List<? extends CrawlerFittingManage<? extends CrawlerFittingConfig, ?>> manages = fittingModules.stream()
                .map(module -> fittingManageMap.get(module.getType())).filter(Objects::nonNull)
                .peek(manage -> manage.verifyType(fittingTypeList))
                .sorted(Comparator.comparingInt(CrawlerFittingManage::sort)).collect(Collectors.toList());
        // 组装
        Map<Integer, List<String>> fittingTypeToCodeList = fittingModules.stream()
                .collect(Collectors.groupingBy(CrawlerFittingModule::getType, Collectors.mapping(CrawlerFittingModule::getCode, Collectors.toList())));
        Crawler crawler = null;
        for (CrawlerFittingManage<? extends CrawlerFittingConfig, ?> manage : manages) {
            crawler = manage.fittingCrawler(crawler, fittingTypeToCodeList.get(manage.fittingType()));
        }
        return crawler;
    }

    private void add() {
        // 拿到爬虫
        // 构建请求
        // 添加请求
    }


    private Crawler getDefaultCrawler() {
        return Crawler.create(new CrawlerDefaultProcessor())
                .addPipeline(new CrawlerDefaultPipeline())
                .setExitWhenComplete(false);
    }
}
