package com.cetiti.dataX.service.impl;

import com.cetiti.core.support.PageModel;
import com.cetiti.dataX.service.OpenApiService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class OpenApiServiceImpl implements OpenApiService {
    @Autowired


    @Override
    public int insertOpenApiService(List<String> mappers) {
        //mapper_resource->category_id->api_method_info

        return 0;
    }

    @Override
    public PageModel<Map> RestOpenApiService(String mapper, String method, Map<String, String> parameters) {
        return null;
    }


}
