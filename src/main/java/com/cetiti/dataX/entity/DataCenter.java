package com.cetiti.dataX.entity;

/**
 * 数据源javaBean
 * @author zhouliyu
 * @version dataX v1.0.0
 * */
public class DataCenter {
    private long sqlId;
    private int sqlType;
    private String sqlName;
    private String url;
    private String userName;
    private String passWord;

    public long getSqlId() {
        return sqlId;
    }

    public void setSqlId(long sqlId) {
        this.sqlId = sqlId;
    }

    public int getSqlType() {
        return sqlType;
    }

    public void setSqlType(int sqlType) {
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
