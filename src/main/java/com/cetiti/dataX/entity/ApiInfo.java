package com.cetiti.dataX.entity;


/**
 *描述：API接口信息
 *@author zhouliyu
 *@version dataX v1.0.0
 */
public class ApiInfo {

    private String apiId;
    private String categoryId;
    private String apiName;
    private String selectId;
    private String parameters;

    public String getApiId() {
        return apiId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getApiName() {
        return apiName;
    }

    public String getSelectId() {
        return selectId;
    }

    public String getParameters() {
        return parameters;
    }
    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public void setSelectId(String selectId) {
        this.selectId = selectId;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "ApiInfo{" +
                "apiId='" + apiId + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", apiName='" + apiName + '\'' +
                ", selectId='" + selectId + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}
