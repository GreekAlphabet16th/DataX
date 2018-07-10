package com.cetiti.dataX.dao;

import com.cetiti.dataX.entity.ApiInfo;

import java.math.BigDecimal;
import java.util.List;

public interface ApiInfoDao {
    
    List<ApiInfo> apiInfoList();

    int insertApiInfo(ApiInfo apiInfo);

    int updateApiInfo(BigDecimal apiId);

    int deleteApiInfo(BigDecimal apiId);
    
}
