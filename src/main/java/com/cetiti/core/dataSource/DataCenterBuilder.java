package com.cetiti.core.dataSource;

import com.cetiti.core.support.UUIDGenerator;
import com.cetiti.dataX.entity.*;
import com.cetiti.dataX.enums.Driver;
import com.cetiti.dataX.enums.SqlType;
import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.StaticTextSqlNode;
import org.apache.ibatis.scripting.xmltags.TextSqlNode;
import org.apache.ibatis.scripting.xmltags.XMLScriptBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.*;


public class DataCenterBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataCenterBuilder.class);
    private static final String DEFAULT_MYBATIS_CONFIG = "spring/dataCenter.xml";

    public SqlSessionFactory sqlSessionFactoryBuild(DataCenter dataCenter,List<String> mappers){
        Properties properties = this.dataCenterLoad(dataCenter);
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(DEFAULT_MYBATIS_CONFIG);
        } catch (IOException e) {
            LOGGER.error("DEFAULT_MYBATIS_CONFIG error", e,
                    e.getCause());
        }
        SqlSessionFactory sqlSessionFactory = new DataXSqlSessionFactoryBuilder().build(inputStream,properties,mappers);
        return sqlSessionFactory;
    }

    public XmlResource xmlResourceBuild(XmlResource xmlResource, List<String> parent){
        try {
            XmlResource var = new XmlResource();
            if (!parent.isEmpty()) {
                Iterator i$ = parent.iterator();
                while(true) {
                    while(i$.hasNext()) {
                        String url = (String)i$.next();
                        XPathParser parser;
                        InputStream inputStream;
                        if (url != null) {
                            ErrorContext.instance().resource(url);
                            inputStream = Resources.getUrlAsStream(url);
                            parser = new XPathParser(inputStream, true, null, new XMLMapperEntityResolver());
                            var = this.parseXmlResource(xmlResource, parser.evalNode("/mapper"), url);
                        }
                    }

                    return var;
                }
            }
        } catch (Exception var3) {
            throw new BuilderException("Error parsing SQL Mapper Configuration. Cause: " + var3, var3);
        }
        return null;
    }


    private Properties dataCenterLoad(DataCenter dataCenter){
        Properties properties = new Properties();
        properties.setProperty("sqlType", SqlType.getValue(dataCenter.getSqlType()));
        properties.setProperty("driver", Driver.getValue(dataCenter.getSqlType()));
        properties.setProperty("url", this.sqlUrl(dataCenter));
        properties.setProperty("userName", dataCenter.getUserName());
        properties.setProperty("passWord", dataCenter.getPassWord());
        return properties;
    }


    /**
     *xml 文件解析
     * */
    private XmlResource parseXmlResource(XmlResource xmlResource, XNode context, String url){
        Map<String,Object> map = new HashMap<>();
        List<ApiMethodInfo> apiMethodInfos = new ArrayList<>();
        String namespace = context.getStringAttribute("namespace");
        if(namespace != null && !namespace.equals("")){
            xmlResource.setCategoryId(UUIDGenerator.generate());
            xmlResource.setMapperUrl(url);
            String ns = context.getStringAttribute("ns");
            if (ns != null && !ns.equals("")){
                xmlResource.setNameSpace(ns);
            }
            String icon = context.getStringAttribute("icon");
            if (ns != null && !ns.equals("")){
                xmlResource.setIconName(icon);
            }
            List<XNode> list = context.evalNodes("select");
            Iterator i$ = list.iterator();
            while (i$.hasNext()){
                XNode node = (XNode) i$.next();
                ApiMethodInfo apiMethodInfo = new ApiMethodInfo();
                String selectId = node.getStringAttribute("selectId");
                String apiName = new StringBuffer(namespace+"."+node.getStringAttribute("id")).toString();
                apiMethodInfo.setApiParameters(this.parseDynamicTags(node));
                apiMethodInfo.setCategoryId(xmlResource.getCategoryId());
                apiMethodInfo.setApiId(UUIDGenerator.generate());
                apiMethodInfo.setSelectId(selectId);
                apiMethodInfo.setApiName(apiName);
                apiMethodInfos.add(apiMethodInfo);
            }
            xmlResource.setApiMethodInfos(apiMethodInfos);
        }
        return xmlResource;
    }

    private String parseDynamicTags(XNode node){
        String prefix = "#{";
        String suffix = "}";
        String sql = node.getStringBody().trim();
        if(sql.contains(prefix)){
            String[] str = sql.split("#\\{");
            StringBuilder parameters = new StringBuilder();
            for(int i = 0; i < str.length; i++){
                if(str[i].contains(suffix)){
                    parameters.append(str[i].substring(0,str[i].lastIndexOf(suffix)));
                    if(i != str.length-1) {
                        parameters.append(",");
                    }
                }
            }
            return parameters.toString();
        }
        return null;
    }

    private String sqlUrl(DataCenter dataCenter){
        StringBuilder sqlUrl = new StringBuilder("jdbc:");
        if(dataCenter.getSqlType() == 1){
            sqlUrl.append("mysql://" + dataCenter.getUrl() + "?useUnicode=true&amp;characterEncoding=UTF-8");
        }else {
            sqlUrl.append("oracle:thin:@" + dataCenter.getUrl());
        }
        return sqlUrl.toString();
    }



}
