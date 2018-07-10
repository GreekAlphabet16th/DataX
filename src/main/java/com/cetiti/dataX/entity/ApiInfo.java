package com.cetiti.dataX.entity;

import java.math.BigDecimal;
import java.util.List;

/**
 *描述：API接口信息
 *@author zhouliyu
 *@version dataX v1.0.0
 */
public class ApiInfo {

    private BigDecimal apiId;
    private BigDecimal categoryId;
    private String apiName;
    private String selectId;
    private List<String> inputParams;

    public BigDecimal getApiId() {
        return apiId;
    }

    public BigDecimal getCategoryId() {
        return categoryId;
    }

    public String getApiName() {
        return apiName;
    }

    public String getSelectId() {
        return selectId;
    }

    public List<String> getInputParams() {
        return inputParams;
    }

    public void setApiId(BigDecimal apiId) {
        this.apiId = apiId;
    }

    public void setCategoryId(BigDecimal categoryId) {
        this.categoryId = categoryId;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public void setSelectId(String selectId) {
        this.selectId = selectId;
    }

    public void setInputParams(List<String> inputParams) {
        this.inputParams = inputParams;
    }

    @Override
    public String toString() {
        return "ApiInfo{" +
                "apiId=" + apiId +
                ", categoryId=" + categoryId +
                ", apiName='" + apiName + '\'' +
                ", selectId='" + selectId + '\'' +
                ", inputParams=" + inputParams +
                '}';
    }
}
