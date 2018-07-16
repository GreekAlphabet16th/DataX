package com.cetiti.dataX.dao;

import com.cetiti.dataX.entity.ApiInfo;
import java.util.List;

public interface ApiInfoDao {
    
    List<ApiInfo> apiInfoList();

    int insertApiInfo(ApiInfo apiInfo);

    int updateApiInfo(ApiInfo apiInfo);

    int deleteApiInfo(String apiId);


}
