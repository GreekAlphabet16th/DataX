package com.cetiti.dataX.entity;

import java.io.Serializable;

/**
 * 数据源javaBean
 * @author zhouliyu
 * @version dataX v1.0.0
 * */
public class DataCenter implements Serializable {

    private static final long serialVersionUID = -7250299194476904869L;
    private Long sqlId;
    private Integer sqlType;
    private String sqlName;
    private String url;
    private String userName;
    private String passWord;

    public Long getSqlId() {
        return sqlId;
    }

    public void setSqlId(Long sqlId) {
        this.sqlId = sqlId;
    }

    public Integer getSqlType() {
        return sqlType;
    }

    public void setSqlType(Integer sqlType) {
        this.sqlType = sqlType;
    }

    public String getSqlName() {
        return sqlName;
    }

    public void setSqlName(String sqlName) {
        this.sqlName = sqlName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return "DataCenter{" +
                "sqlId=" + sqlId +
                ", sqlType=" + sqlType +
                ", sqlName='" + sqlName + '\'' +
                ", url='" + url + '\'' +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }
}
