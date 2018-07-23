package com.cetiti.dataX.dao;

import com.cetiti.dataX.entity.ApiMethodInfo;

import java.util.List;

/**
 * 发布接口管理
 * */
public interface ApiMethodInfoDao {

    List<ApiMethodInfo> apiMethodInfoList(); //一对一展示接口详情

    int insertApiMethodInfo(ApiMethodInfo apiMethodInfo);

    int updateApiMethodInfo(ApiMethodInfo apiMethodInfo);

    int deleteApiMethodInfo(String apiId);

}
