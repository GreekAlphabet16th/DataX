package com.cetiti.dataX.dao;

import com.cetiti.dataX.entity.DataProperties;
import java.util.List;

public interface DataPropertiesDao {

    List<DataProperties> DataPropertiesList();

    int insertDataProperties(DataProperties properties);

    int updateDataProperties(DataProperties properties);

    int deleteDataProperties(String dataId);

    DataProperties getDataProperties(String dataId);


}
