package com.cetiti.dataX.dao;


import com.cetiti.dataX.entity.ServiceResource;

import java.math.BigDecimal;
import java.util.List;

public interface ServiceResourceDao {

    List<ServiceResource> serviceResourceList();

    int insertServiceResource(ServiceResource serviceResource);

    int updateServiceResource(BigDecimal categoryId);

    int deleteServiceResource(BigDecimal categoryId);
}
