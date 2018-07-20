package com.cetiti.dataX.service;

import com.cetiti.core.support.PageModel;
import com.cetiti.dataX.entity.DataCenter;

import java.util.List;

/**
 * 数据源管理
 * @author zhouliyu
 * @version dataX v1.0.0
 * */
public interface DataCenterService {

    /**
     * 数据源列表
     * */
    PageModel<DataCenter> dataCenterList(int pageNum, int pageSize, String keyWord);

    /**
     * 添加数据源
     * */
    int insertDataCenter(DataCenter dataCenter);

    /**
     * 更新数据源
     * */
    int updateDataCenter(DataCenter dataCenter);

    /**
     * 删除数据源
     * */
    int deleteDataCenter(long sqlId);

    /**
     * 根据sqlId获得指定数据源
     * @param sqlId
     * @return
     * */
    DataCenter getDataCenter(long sqlId);

    boolean isConnect(DataCenter dataCenter);
}
