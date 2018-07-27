package com.cetiti.dataX.entity;

import java.io.Serializable;
import java.util.List;

public class XmlResource implements Serializable {
    private static final long serialVersionUID = 5791665416405403464L;
    private String categoryId;
    private String mapperUrl;
    private String nameSpace;
    private String iconName;
    private Long sqlId;
    private String provider;
    private String description;
    private List<ApiMethodInfo> apiMethodInfos;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getMapperUrl() {
        return mapperUrl;
    }

    public void setMapperUrl(String mapperUrl) {
        this.mapperUrl = mapperUrl;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public Long getSqlId() {
        return sqlId;
    }

    public void setSqlId(Long sqlId) {
        this.sqlId = sqlId;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ApiMethodInfo> getApiMethodInfos() {
        return apiMethodInfos;
    }

    public void setApiMethodInfos(List<ApiMethodInfo> apiMethodInfos) {
        this.apiMethodInfos = apiMethodInfos;
    }

    @Override
    public String toString() {
        return "XmlResource{" +
                "categoryId='" + categoryId + '\'' +
                ", mapperUrl='" + mapperUrl + '\'' +
                ", nameSpace='" + nameSpace + '\'' +
                ", iconName='" + iconName + '\'' +
                ", sqlId='" + sqlId + '\'' +
                ", provider='" + provider + '\'' +
                ", description='" + description + '\'' +
                ", apiMethodInfos=" + apiMethodInfos +
                '}';
    }
}
