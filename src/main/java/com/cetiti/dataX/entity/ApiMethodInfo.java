package com.cetiti.dataX.entity;

import java.io.Serializable;

public class ApiMethodInfo implements Serializable {
    private static final long serialVersionUID = -7125465369906143441L;
    private String apiId;
    private String apiName;
    private String selectId;
    private String apiParameters;
    private String categoryId;
    //父级：资源目录
    private XmlResource xmlResource;

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public XmlResource getXmlResource() {
        return xmlResource;
    }

    public void setXmlResource(XmlResource xmlResource) {
        this.xmlResource = xmlResource;
    }

    @Override
    public String toString() {
        return "ApiMethodInfo{" +
                "apiId='" + apiId + '\'' +
                ", apiName='" + apiName + '\'' +
                ", selectId='" + selectId + '\'' +
                ", apiParameters='" + apiParameters + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", xmlResource=" + xmlResource +
                '}';
    }
}
