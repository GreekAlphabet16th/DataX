package com.cetiti.dataX.dao;

import com.cetiti.dataX.entity.XmlResource;

import java.util.List;

/**
 * 资源目录管理
 * */
public interface XmlResourceDao {

    List<XmlResource> xmlResourceList();

    int insertXmlResource(XmlResource xmlResource);

    int updateXmlResource(XmlResource xmlResource);

    int deleteXmlResource(String categoryId);

}
