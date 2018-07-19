package com.cetiti.core.dataSource;

import com.cetiti.core.support.UUIDGenerator;
import com.cetiti.dataX.entity.ApiInfo;
import com.cetiti.dataX.entity.DataProperties;
import com.cetiti.dataX.entity.ServiceResource;
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
import java.util.*;


public class DataCenterBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataCenterBuilder.class);
    private static final String DEFAULT_MYBATIS_CONFIG = "spring/dataCenter.xml";

    public SqlSessionFactory sqlSessionFactoryBuild(DataProperties DataProperties,List<String> mappers){
        Properties properties = this.propertiesLoad(DataProperties);
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

    public Map<String,Object> xmlServiceBulid(List<String> parent,String dataId){
        try {
            Map<String, Object> map = new HashMap<>();
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
                            map = this.getApiInfo(parser.evalNode("/mapper"), url, dataId);
                        }
                    }

                    return map;
                }
            }
        } catch (Exception var3) {
            throw new BuilderException("Error parsing SQL Mapper Configuration. Cause: " + var3, var3);
        }
        return null;
    }


    private Properties propertiesLoad(DataProperties DataProperties){
        Properties properties = new Properties();
        properties.setProperty("sqlType", DataProperties.getSqlType());
        properties.setProperty("driver", DataProperties.getDriver());
        properties.setProperty("url", DataProperties.getUrl());
        properties.setProperty("userName", DataProperties.getUserName());
        properties.setProperty("passWord", DataProperties.getPassWord());
        return properties;
    }

    /**
     * XML文件解析
     *
     * */
    private Map<String,Object> getApiInfo(XNode context,String url, String dataId){
        Map<String,Object> map = new HashMap<>();
        List<ApiInfo> apiInfos = new ArrayList<>();
        ServiceResource serviceResource = new ServiceResource();
        String namespace = context.getStringAttribute("namespace");
        if(namespace != null && !namespace.equals("")){
            serviceResource.setCategoryId(UUIDGenerator.generate());
            serviceResource.setResourceUrl(url);
            serviceResource.setDataId(dataId);
            String ns = context.getStringAttribute("ns");
            if (ns != null && !ns.equals("")){
                serviceResource.setNameSpace(ns);
            }
            String icon = context.getStringAttribute("icon");
            if (ns != null && !ns.equals("")){
                serviceResource.setIconName(icon);
            }
            List<XNode> list = context.evalNodes("select");
            Iterator i$ = list.iterator();
            while (i$.hasNext()){
                XNode node = (XNode) i$.next();
                ApiInfo apiInfo = new ApiInfo();
                String selectId = node.getStringAttribute("selectId");
                String apiName = new StringBuffer(namespace+"."+node.getStringAttribute("id")).toString();
                apiInfo.setParameters(this.parseDynamicTags(node));
                apiInfo.setApiId(UUIDGenerator.generate());
                apiInfo.setSelectId(selectId);
                apiInfo.setApiName(apiName);
                apiInfo.setCategoryId(serviceResource.getCategoryId());
                apiInfos.add(apiInfo);
            }
            map.put("serviceResource",serviceResource);
            map.put("apiInfos",apiInfos);
        }
        return map;
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



}
