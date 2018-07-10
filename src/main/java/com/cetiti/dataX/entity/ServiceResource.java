package com.cetiti.dataX.entity;

import java.math.BigDecimal;

public class ServiceResource {
    private BigDecimal categoryId;
    private String resourceUrl;
    private String nameSpace;
    private String iconName;
    private BigDecimal dataId;

    public BigDecimal getCategoryId() {
        return categoryId;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public String getIconName() {
        return iconName;
    }

    public BigDecimal getDataId() {
        return dataId;
    }

    public void setCategoryId(BigDecimal categoryId) {
        this.categoryId = categoryId;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public void setDataId(BigDecimal dataId) {
        this.dataId = dataId;
    }

    @Override
    public String toString() {
        return "ServiceResource{" +
                "categoryId=" + categoryId +
                ", resourceUrl='" + resourceUrl + '\'' +
                ", nameSpace='" + nameSpace + '\'' +
                ", iconName='" + iconName + '\'' +
                ", dataId=" + dataId +
                '}';
    }
}
