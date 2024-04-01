package com.cqyang.demo.crawler.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@AllArgsConstructor
@Getter
public enum CrawlerSceneEnum {

    MEDICAL(0, "医保局"),

    ;

    private final int scene;
    private final String desc;

    public static CrawlerSceneEnum getCrawlerSceneByScene(int scene) {
        for (CrawlerSceneEnum sceneEnum : CrawlerSceneEnum.values()) {
            if (scene == sceneEnum.getScene()) {
                return sceneEnum;
            }
        }
        return null;
    }

    public static boolean isContain(int scene) {
        return Arrays.stream(CrawlerSceneEnum.values()).anyMatch(value -> Objects.equals(value.getScene(), scene));
    }
}
