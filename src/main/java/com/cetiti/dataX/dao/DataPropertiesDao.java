package com.cetiti.dataX.dao;

import com.cetiti.dataX.entity.DataProperties;

import java.math.BigDecimal;
import java.util.List;

public interface DataPropertiesDao {

    List<DataProperties> DataPropertiesList();

    int insertDataProperties(DataProperties properties);

    int updateDataProperties(DataProperties properties);

    int deleteDataProperties(BigDecimal dataId);

    DataProperties getDataProperties(BigDecimal dataId);



}
