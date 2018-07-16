package com.cetiti.dataX.dao;


import com.cetiti.dataX.entity.ServiceResource;
import java.util.List;

public interface ServiceResourceDao {

    List<ServiceResource> serviceResourceList();

    int insertServiceResource(ServiceResource serviceResource);

    int updateServiceResource(ServiceResource serviceResource);

    int deleteServiceResource(String categoryId);

    ServiceResource getServiceResource(String categoryId);
}
