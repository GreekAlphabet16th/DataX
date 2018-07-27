package com.cetiti.dataX.service.impl;

import com.cetiti.core.support.ApiResult;
import com.cetiti.core.support.BaseSupport;
import com.cetiti.core.support.PageModel;
import com.cetiti.dataX.dao.DataCenterDao;
import com.cetiti.dataX.entity.DataCenter;
import com.cetiti.dataX.enums.DriverEnum;
import com.cetiti.dataX.service.DataCenterService;
import com.cetiti.dataX.web.DataCenterController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@Service
public class DataCenterServiceImpl extends BaseSupport implements DataCenterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataCenterController.class);
    @Autowired
    private DataCenterDao dataCenterDao;

    @Override
    public PageModel<DataCenter> dataCenterList(int pageNum, int pageSize, String keyWord) {
        ApiResult.offsetPage(pageNum,pageSize);
        List<DataCenter> list = dataCenterDao.dataCenterList(keyWord);
        PageModel<DataCenter> result = ApiResult.resultPage(list);
        return result;
    }

    @Override
    public int insertDataCenter(DataCenter dataCenter) {
        if(isNull(dataCenter)){
           return 0;
        }
        return dataCenterDao.insertDataCenter(dataCenter);
    }

    @Override
    public int updateDataCenter(DataCenter dataCenter) {
        if(isNull(dataCenter)){
            return 0;
        }
        return dataCenterDao.updateDataCenter(dataCenter);
    }

    @Override
    public int deleteDataCenter(long sqlId) {
        return dataCenterDao.deleteDataCenter(sqlId);
    }

    @Override
    public DataCenter getDataCenter(long sqlId) {
        if(isNull(sqlId)){
            return null;
        }
        return dataCenterDao.getDataCenter(sqlId);
    }

    /**
     * 数据库连接测试
     * */
    public boolean isConnect(DataCenter dataCenter){
        StringBuilder url = new StringBuilder("jdbc:");
        Connection connection = null;
        int num =0;
        if(dataCenter.getSqlType() == 1){
            url.append("mysql://" + dataCenter.getUrl());
        }else {
            url.append("oracle:thin:@" + dataCenter.getUrl());
        }
        try {
            Class.forName(DriverEnum.getValue(dataCenter.getSqlType()));
            connection = DriverManager.getConnection(url.toString(),dataCenter.getUserName(),dataCenter.getPassWord());
            if(!isNull(connection)){
                return true;
            }
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }finally {
            try {
                if(!isNull(connection)){
                    connection.close();
                }
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }
        return false;
    }
}
