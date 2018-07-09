package com.cetiti.core.support;

import java.io.Serializable;

public class MsgModel implements Serializable {

    private static final long serialVersionUID = 1L;

    //状态值
    private String status;
    //消息
    private String msg;
    //返回对象
    private Object res;

    public MsgModel() {
    }

    public MsgModel(String status, String msg) {
        this(status,msg,null);
    }

    public MsgModel(String msg) {
        this(null,msg,null);
    }

    public MsgModel(String status, String msg, Object res) {
        this.status = status;
        this.msg = msg;
        this.res = res;
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public Object getRes() {
        return res;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setRes(Object res) {
        this.res = res;
    }
}
