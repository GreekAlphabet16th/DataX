package com.cetiti.dataX.service;

import com.cetiti.core.support.PageModel;
import com.cetiti.dataX.entity.DataProperties;

import java.util.List;
import java.util.Map;

public interface ApiService {

    //rest方式接口访问
    PageModel<Map> RestApiService(String mapper, String method, Map<String,String> parameters);
    //数据发布
    int insertApiService(DataProperties dataProperties, List<String> mappers);
}
