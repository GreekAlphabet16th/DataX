package com.cetiti.core.dataSource;

import org.apache.ibatis.builder.*;
import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.apache.ibatis.builder.xml.XMLStatementBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.session.Configuration;

import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class XMLServiceBuilder extends BaseBuilder {
    private XPathParser parser;
    private MapperBuilderAssistant builderAssistant;
    private Map<String, XNode> sqlFragments;
    private String resource;

    public XMLServiceBuilder(InputStream inputStream, Configuration configuration, String resource, Map<String, XNode> sqlFragments) {
        this(new XPathParser(inputStream, true, configuration.getVariables(), new XMLMapperEntityResolver()), configuration, resource, sqlFragments);
    }

    private XMLServiceBuilder(XPathParser parser, Configuration configuration, String resource, Map<String, XNode> sqlFragments) {
        super(configuration);
        this.builderAssistant = new MapperBuilderAssistant(configuration, resource);
        this.parser = parser;
        this.sqlFragments = sqlFragments;
        this.resource = resource;
    }

    public void parse(){
        if (!this.configuration.isResourceLoaded(this.resource)) {
            //this.configurationElement(this.parser.evalNode("/mapper"));
            this.configuration.addLoadedResource(this.resource);
            this.bindMapperForNamespace();
        }

        this.parsePendingResultMaps();
        this.parsePendingChacheRefs();
        this.parsePendingStatements();
    }

/*    private Configuration dataCenterElement(XNode parent){
        if(){

        }
    }*/




    private void parsePendingStatements() {
        Collection<XMLStatementBuilder> incompleteStatements = this.configuration.getIncompleteStatements();
        synchronized(incompleteStatements) {
            Iterator iter = incompleteStatements.iterator();

            while(iter.hasNext()) {
                try {
                    ((XMLStatementBuilder)iter.next()).parseStatementNode();
                    iter.remove();
                } catch (IncompleteElementException var6) {
                    ;
                }
            }

        }
    }
    private void parsePendingChacheRefs() {
        Collection<CacheRefResolver> incompleteCacheRefs = this.configuration.getIncompleteCacheRefs();
        synchronized(incompleteCacheRefs) {
            Iterator iter = incompleteCacheRefs.iterator();

            while(iter.hasNext()) {
                try {
                    ((CacheRefResolver)iter.next()).resolveCacheRef();
                    iter.remove();
                } catch (IncompleteElementException var6) {
                    ;
                }
            }

        }
    }

    private void parsePendingResultMaps() {
        Collection<ResultMapResolver> incompleteResultMaps = this.configuration.getIncompleteResultMaps();
        synchronized(incompleteResultMaps) {
            Iterator iter = incompleteResultMaps.iterator();

            while(iter.hasNext()) {
                try {
                    ((ResultMapResolver)iter.next()).resolve();
                    iter.remove();
                } catch (IncompleteElementException var6) {
                    ;
                }
            }

        }
    }

    private void bindMapperForNamespace() {
        String namespace = this.builderAssistant.getCurrentNamespace();
        if (namespace != null) {
            Class boundType = null;

            try {
                boundType = Resources.classForName(namespace);
            } catch (ClassNotFoundException var4) {
                ;
            }

            if (boundType != null && !this.configuration.hasMapper(boundType)) {
                this.configuration.addLoadedResource("namespace:" + namespace);
                this.configuration.addMapper(boundType);
            }
        }

    }

}
