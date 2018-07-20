package com.cetiti.dataX.dao;

import com.cetiti.dataX.entity.XmlResource;

/**
 * 资源目录
 * */
public interface XmlResourceDao {

    int insertXmlResource(XmlResource xmlResource);

    int updateXmlResource(XmlResource xmlResource);

    int deleteXmlResource(String categoryId);

}
