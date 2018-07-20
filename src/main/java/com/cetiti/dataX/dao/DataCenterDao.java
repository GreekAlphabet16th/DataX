package com.cetiti.dataX.dao;

import com.cetiti.dataX.entity.DataCenter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DataCenterDao {
    List<DataCenter> dataCenterList(@Param("keyWord") String keyWord);

    int insertDataCenter(DataCenter dataCenter);

    int updateDataCenter(DataCenter dataCenter);

    int deleteDataCenter(long sqlId);

    DataCenter getDataCenter(long sqlId);


}
