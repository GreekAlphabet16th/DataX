package com.cetiti.core.controller;


import com.cetiti.core.support.BaseSupport;
import com.cetiti.core.support.MsgModel;
import com.cetiti.core.support.PageModel;
import com.github.pagehelper.PageHelper;

import java.util.List;

/**
 * 描述：控制器支持类，继承于BaseSupport
 * 该类提供分页、返回Page、Msg对象、文件上传下载等方法
 * @author zhouliyu
 * @version dataX v1.0.0
 * */
public class BaseController extends BaseSupport {

    /**
     * 分页查询范围，参数前端传入<br>
     *
     * @param offset 起始数量
     * @param limit 限制条数
     */
    protected void offsetPage(int offset, int limit) {
        PageHelper.offsetPage(offset, limit);
    }

    /**
     * 分页结果集对象<br>
     *
     * @param list 查询到的分页结果，为 Page 对象
     * @return PageModel<T> 自定义的分页模型，T 为查询的对象
     */
    protected <T> PageModel<T> resultPage(List<T> list){
        return new PageModel<>(list);
    }

    /**
     * 消息返回对象<br>
     *
     * @param status 状态值（可根据需求任意设置，无强制标准）
     * @param msg 消息内容
     * @param res 返回的对象
     * @return MsgModel 自定义消息模型
     */
    protected MsgModel resultMsg(String status, String msg, Object res) {
        return new MsgModel(status, msg, res);
    }

    /**
     * 消息返回对象<br>
     *
     * @param status 状态值（可根据需求任意设置，无强制标准）
     * @param msg 详细内容
     * @return MsgModel 自定义消息模型
     */
    protected MsgModel resultMsg(String status, String msg) {
        return new MsgModel(status, msg);
    }

    /**
     * 消息返回对象<br>
     *
     * @param msg 详细内容
     * @return MsgModel 自定义消息模型
     */
    protected MsgModel resultMsg(String msg) {
        return new MsgModel(msg);
    }





}
