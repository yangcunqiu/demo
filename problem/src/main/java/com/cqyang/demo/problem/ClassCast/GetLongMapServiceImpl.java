package com.cqyang.demo.problem.ClassCast;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GetLongMapServiceImpl implements GetLongMapService {

    @Override
    public Map<Long, String> getLongMap() {
        Map<Long, String> map = new HashMap<>();
        map.put(1L, "GetLongMapService");
        return map;
    }
}
