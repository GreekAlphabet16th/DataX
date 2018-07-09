package com.cetiti.dataX.entity;

/**
 * 描述：数据源实体类
 * @author zhouliyu
 * @version dataX v1.0.0
 * */
public class DataProperties {

    private int dataId;
    private String driver;
    private String url;
    private String userName;
    private String passWord;

    public int getDataId() {
        return dataId;
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

    public void setDataId(int dataId) {
        this.dataId = dataId;
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
                ", driver='" + driver + '\'' +
                ", url='" + url + '\'' +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }
}
