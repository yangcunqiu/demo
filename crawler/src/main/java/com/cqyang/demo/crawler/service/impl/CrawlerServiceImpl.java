package com.cqyang.demo.crawler.service.impl;

import com.alibaba.fastjson.JSON;
import com.cqyang.demo.crawler.core.Crawler;
import com.cqyang.demo.crawler.core.CrawlerDefaultPipeline;
import com.cqyang.demo.crawler.core.CrawlerDefaultProcessor;
import com.cqyang.demo.crawler.core.CrawlerFittingManage;
import com.cqyang.demo.crawler.exception.CrawlerException;
import com.cqyang.demo.crawler.mapper.CrawlerConfigMapper;
import com.cqyang.demo.crawler.mapper.CrawlerInfoMapper;
import com.cqyang.demo.crawler.model.CrawlerFittingConfig;
import com.cqyang.demo.crawler.model.enums.CrawlerFittingModuleTypeEnum;
import com.cqyang.demo.crawler.model.enums.CrawlerModuleCodeEnum;
import com.cqyang.demo.crawler.model.enums.CrawlerSceneEnum;
import com.cqyang.demo.crawler.model.enums.CrawlerStatusEnum;
import com.cqyang.demo.crawler.model.po.CrawlerConfig;
import com.cqyang.demo.crawler.model.po.CrawlerInfo;
import com.cqyang.demo.crawler.model.request.AddCrawlerRequest;
import com.cqyang.demo.crawler.model.request.CrawlerFittingModule;
import com.cqyang.demo.crawler.model.vo.CrawlerFittingModuleVO;
import com.cqyang.demo.crawler.model.vo.CrawlerInfoVO;
import com.cqyang.demo.crawler.model.vo.CrawlerModuleVO;
import com.cqyang.demo.crawler.service.CrawlerService;
import com.cqyang.demo.crawler.util.SnowflakeIdUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // 保存非停止状态的爬虫, key: 爬虫唯一键
    private final Map<Long, Crawler> crawlerMap = new HashMap<>();

    @Autowired
    private List<CrawlerFittingManage<? extends CrawlerFittingConfig, ?>> fittingManages;
    private Map<Integer, CrawlerFittingManage<? extends CrawlerFittingConfig, ?>> fittingManageMap;

    @Autowired
    private CrawlerInfoMapper crawlerInfoMapper;
    @Autowired
    private CrawlerConfigMapper crawlerConfigMapper;

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
    @Transactional
    public void createCrawler(AddCrawlerRequest addCrawlerRequest) {
        Integer scene = addCrawlerRequest.getScene();
        CrawlerSceneEnum sceneEnum = CrawlerSceneEnum.getCrawlerSceneByScene(scene);
        if (Objects.isNull(sceneEnum)) {
            throw new CrawlerException("scene有误");
        }

        // 先校验一下
        CrawlerInfo bySceneAndNotStatus = crawlerInfoMapper.findBySceneAndNotStatus(scene, CrawlerStatusEnum.STOPPED.getStatus());
        if (Objects.nonNull(bySceneAndNotStatus)) {
            throw new CrawlerException("当前场景已存在爬虫, 请先停止后再创建");
        }

        // 组装爬虫
        Crawler crawler = fitting(addCrawlerRequest.getCrawlerFittingModuleList());

        // 分配唯一键
        long uniqueKey = SnowflakeIdUtil.nextId();

        // 持久化
        CrawlerInfo crawlerInfo = new CrawlerInfo();
        crawlerInfo.setUniqueKey(uniqueKey);
        crawlerInfo.setScene(scene);
        crawlerInfo.setSceneDesc(sceneEnum.getDesc());
        crawlerInfo.setStatus(CrawlerStatusEnum.INIT.getStatus());
        crawlerInfoMapper.insert(crawlerInfo);
        // 持久化配置
        List<CrawlerConfig> crawlerConfigList = addCrawlerRequest.getCrawlerFittingModuleList().stream().map(module -> {
            CrawlerConfig crawlerConfig = new CrawlerConfig();
            crawlerConfig.setCrawlerUniqueKey(uniqueKey);
            crawlerConfig.setFittingModuleType(module.getType());
            crawlerConfig.setModuleCodeList(JSON.toJSONString(module.getCodeList()));
            crawlerConfig.setFittingConfigJson(module.getFittingConfig());
            return crawlerConfig;
        }).collect(Collectors.toList());
        crawlerConfigMapper.batchInsert(crawlerConfigList);

        // 放到map
        crawlerMap.put(uniqueKey, crawler);

        // 启动爬虫
        if (addCrawlerRequest.isRunAsync()) {
            crawler.runAsync();
        }
    }

    @Override
    public List<CrawlerInfoVO> list() {
        List<CrawlerInfo> crawlerInfoList = crawlerInfoMapper.findAll();
        return crawlerInfoList.stream().map(info -> {
                CrawlerInfoVO vo = new CrawlerInfoVO();
                vo.setUniqueKey(info.getUniqueKey());
                vo.setScene(info.getScene());
                vo.setSceneDesc(info.getSceneDesc());
                vo.setStatus(info.getStatus());
                return vo;
            }).collect(Collectors.toList());
    }

    @Override
    public CrawlerInfoVO findByUniqueKey(long uniqueKey) {
        CrawlerInfo info = crawlerInfoMapper.findByUniqueKey(uniqueKey);
        // 基础信息
        CrawlerInfoVO vo = new CrawlerInfoVO();
        vo.setUniqueKey(info.getUniqueKey());
        vo.setScene(info.getScene());
        vo.setSceneDesc(info.getSceneDesc());
        vo.setStatus(info.getStatus());

        // 组装配置模块信息
        List<CrawlerConfig> configList = crawlerConfigMapper.findByCrawlerUniqueKey(info.getUniqueKey());
        List<CrawlerFittingModuleVO> fittingModuleVOList = configList.stream()
                .map(config -> {
                    CrawlerFittingModuleVO fittingModuleVO = new CrawlerFittingModuleVO();
                    fittingModuleVO.setType(config.getFittingModuleType());
                    fittingModuleVO.setFittingConfigJson(config.getFittingConfigJson());

                    List<CrawlerModuleVO> moduleVOList = JSON.parseArray(config.getModuleCodeList(), String.class).stream()
                            .map(CrawlerModuleCodeEnum::getByCode)
                            .filter(Objects::nonNull)
                            .map(moduleCodeEnum -> {
                                CrawlerModuleVO moduleVO = new CrawlerModuleVO();
                                moduleVO.setCode(moduleCodeEnum.getCode());
                                moduleVO.setClassName(moduleCodeEnum.getClassName());
                                moduleVO.setDesc(moduleCodeEnum.getDesc());
                                return moduleVO;
                            })
                            .collect(Collectors.toList());

                    fittingModuleVO.setModuleVOList(moduleVOList);
                    return fittingModuleVO;
                })
                .collect(Collectors.toList());

        vo.setFittingModuleVOList(fittingModuleVOList);
        return vo;
    }

    @Override
    public void runCrawler(long uniqueKey) {
        Crawler crawler = crawlerMap.get(uniqueKey);
        if (Objects.nonNull(crawler)) {
            if (Objects.equals(Crawler.Status.Running, crawler.getStatus())) {
                throw new CrawlerException("Crawler is already running!");
            }

            crawler.runAsync();

            // 改数据库状态
            crawlerInfoMapper.updateStatusByUniqueKey(uniqueKey, CrawlerStatusEnum.RUNNING.getStatus());
        }
    }

    @Override
    public void closeCrawler(long uniqueKey) {
        Crawler crawler = crawlerMap.get(uniqueKey);
        if (Objects.nonNull(crawler)) {
            // TODO 检查是否还有任务

            // close
            crawler.close();

            // 改数据库状态
            crawlerInfoMapper.updateStatusByUniqueKey(uniqueKey, CrawlerStatusEnum.STOPPED.getStatus());

            // 移出map
            crawlerMap.remove(uniqueKey);
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
        Map<Integer, CrawlerFittingModule> fittingTypeToModuleMap = fittingModules.stream().collect(Collectors.toMap(CrawlerFittingModule::getType, Function.identity()));
        Crawler crawler = null;
        for (CrawlerFittingManage<? extends CrawlerFittingConfig, ?> manage : manages) {
            CrawlerFittingModule fittingModule = fittingTypeToModuleMap.get(manage.fittingType());
            crawler = manage.fittingCrawler(crawler, fittingModule.getCodeList(), fittingModule.getFittingConfig());
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
