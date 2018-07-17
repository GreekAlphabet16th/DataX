package com.cetiti.dataX.entity;


/**
 * 描述：数据源实体类
 * @author zhouliyu
 * @version dataX v1.0.0
 * */
public class DataProperties {

    private String dataId;
    private String sqlType;
    private String dataCenter;
    private String driver;
    private String url;
    private String userName;
    private String passWord;

    public String getDataId() {
        return dataId;
    }

    public String getSqlType() {
        return sqlType;
    }

    public String getDataCenter() {
        return dataCenter;
    }

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType;
    }

    public void setDataCenter(String dataCenter) {
        this.dataCenter = dataCenter;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return "DataProperties{" +
                "dataId=" + dataId +
                ", sqlType='" + sqlType + '\'' +
                ", dataCenter='" + dataCenter + '\'' +
                ", driver='" + driver + '\'' +
                ", url='" + url + '\'' +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }
}
