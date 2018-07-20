package com.cetiti.dataX.entity;

public class ApiMethodInfo {
    private String apiId;
    private String categoryId;
    private String apiName;
    private String selectId;
    private String apiParameters;

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getSelectId() {
        return selectId;
    }

    public void setSelectId(String selectId) {
        this.selectId = selectId;
    }

    public String getApiParameters() {
        return apiParameters;
    }

    public void setApiParameters(String apiParameters) {
        this.apiParameters = apiParameters;
    }

    @Override
    public String toString() {
        return "ApiMethodInfo{" +
                "apiId='" + apiId + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", apiName='" + apiName + '\'' +
                ", selectId='" + selectId + '\'' +
                ", apiParameters='" + apiParameters + '\'' +
                '}';
    }
}
