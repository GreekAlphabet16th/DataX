package com.cetiti.dataX.dao;

import com.cetiti.dataX.entity.ApiMethodInfo;

import java.util.List;
import java.util.Map;

/**
 * 资源目录
 * */
public interface ApiMethodInfoDao {

    List<Map> apiMethodList();

    int insertApiMethodInfo(ApiMethodInfo apiMethodInfo);

    int updateApiMethodInfo(ApiMethodInfo apiMethodInfo);

    int deleteApiMethodInfo(String apiId);

}
