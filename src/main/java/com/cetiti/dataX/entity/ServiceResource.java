package com.cetiti.dataX.entity;


public class ServiceResource {
    private String categoryId;
    private String resourceUrl;
    private String nameSpace;
    private String iconName;
    private long dataId;

    public String getCategoryId() {
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

    public long getDataId() {
        return dataId;
    }

    public void setCategoryId(String categoryId) {
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

    public void setDataId(long dataId) {
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
