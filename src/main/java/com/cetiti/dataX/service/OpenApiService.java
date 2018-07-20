package com.cetiti.dataX.service;

import com.cetiti.core.support.PageModel;

import java.util.List;
import java.util.Map;

/**
 * 开放接口管理
 * @author zhouliyu
 * @version dataX v1.0.0
 * */
public interface OpenApiService {

    /**
     * 资源发布
     * */
    int insertOpenApiService(List<String> mappers);
    /**
     * rest接口形式
     * */
    PageModel<Map> RestOpenApiService(String mapper, String method, Map<String,String> parameters);

    /**
     * 删除资源目录
     * */

    /**
     * 删除方法
     * */
}
