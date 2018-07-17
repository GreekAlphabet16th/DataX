package com.cetiti.core.dataSource;

import org.apache.ibatis.builder.*;
import org.apache.ibatis.builder.xml.XMLStatementBuilder;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.io.InputStream;
import java.util.*;

public class DataXMapperBuilder extends BaseBuilder {

    private XPathParser parser;
    private MapperBuilderAssistant builderAssistant;
    private Map<String, XNode> sqlFragments;
    private String resource;


    public DataXMapperBuilder(InputStream inputStream, Configuration configuration, String resource, Map<String, XNode> sqlFragments, String namespace) {
        this(inputStream, configuration, resource, sqlFragments);
        this.builderAssistant.setCurrentNamespace(namespace);
    }

    public DataXMapperBuilder(InputStream inputStream, Configuration configuration, String resource, Map<String, XNode> sqlFragments) {
        this(new XPathParser(inputStream, true, configuration.getVariables(), new XMLMapperEntityResolver()), configuration, resource, sqlFragments);
    }

    private DataXMapperBuilder(XPathParser parser, Configuration configuration, String resource, Map<String, XNode> sqlFragments) {
        super(configuration);
        this.builderAssistant = new MapperBuilderAssistant(configuration, resource);
        this.parser = parser;
        this.sqlFragments = sqlFragments;
        this.resource = resource;
    }

    public void parse() {
        if (!this.configuration.isResourceLoaded(this.resource)) {
            this.configurationElement(this.parser.evalNode("/mapper"));
            this.configuration.addLoadedResource(this.resource);
            this.bindMapperForNamespace();
        }

        this.parsePendingResultMaps();
        this.parsePendingChacheRefs();
        this.parsePendingStatements();
    }
    

    public XNode getSqlFragment(String refid) {
        return (XNode)this.sqlFragments.get(refid);
    }

    private void configurationElement(XNode context) {
        try {
            String namespace = context.getStringAttribute("namespace");
            if (namespace != null && !namespace.equals("")) {
                this.builderAssistant.setCurrentNamespace(namespace);
                this.cacheRefElement(context.evalNode("cache-ref"));
                this.cacheElement(context.evalNode("cache"));
                this.parameterMapElement(context.evalNodes("/mapper/parameterMap"));
                this.resultMapElements(context.evalNodes("/mapper/resultMap"));
                this.sqlElement(context.evalNodes("/mapper/sql"));
                this.buildStatementFromContext(context.evalNodes("select|insert|update|delete"));
            } else {
                throw new BuilderException("Mapper's namespace cannot be empty");
            }
        } catch (Exception var3) {
            throw new BuilderException("Error parsing Mapper XML. Cause: " + var3, var3);
        }
    }

    private void buildStatementFromContext(List<XNode> list) {
        if (this.configuration.getDatabaseId() != null) {
            this.buildStatementFromContext(list, this.configuration.getDatabaseId());
        }

        this.buildStatementFromContext(list, (String)null);
    }

    private void buildStatementFromContext(List<XNode> list, String requiredDatabaseId) {
        Iterator i$ = list.iterator();

        while(i$.hasNext()) {
            XNode context = (XNode)i$.next();
            XMLStatementBuilder statementParser = new XMLStatementBuilder(this.configuration, this.builderAssistant, context, requiredDatabaseId);

            try {
                statementParser.parseStatementNode();
            } catch (IncompleteElementException var7) {
                this.configuration.addIncompleteStatement(statementParser);
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

    private void cacheRefElement(XNode context) {
        if (context != null) {
            this.configuration.addCacheRef(this.builderAssistant.getCurrentNamespace(), context.getStringAttribute("namespace"));
            CacheRefResolver cacheRefResolver = new CacheRefResolver(this.builderAssistant, context.getStringAttribute("namespace"));

            try {
                cacheRefResolver.resolveCacheRef();
            } catch (IncompleteElementException var4) {
                this.configuration.addIncompleteCacheRef(cacheRefResolver);
            }
        }

    }

    private void cacheElement(XNode context) throws Exception {
        if (context != null) {
            String type = context.getStringAttribute("type", "PERPETUAL");
            Class<? extends Cache> typeClass = this.typeAliasRegistry.resolveAlias(type);
            String eviction = context.getStringAttribute("eviction", "LRU");
            Class<? extends Cache> evictionClass = this.typeAliasRegistry.resolveAlias(eviction);
            Long flushInterval = context.getLongAttribute("flushInterval");
            Integer size = context.getIntAttribute("size");
            boolean readWrite = !context.getBooleanAttribute("readOnly", false).booleanValue();
            boolean blocking = context.getBooleanAttribute("blocking", false).booleanValue();
            Properties props = context.getChildrenAsProperties();
            this.builderAssistant.useNewCache(typeClass, evictionClass, flushInterval, size, readWrite, blocking, props);
        }

    }

    private void parameterMapElement(List<XNode> list) throws Exception {
        Iterator i$ = list.iterator();

        while(i$.hasNext()) {
            XNode parameterMapNode = (XNode)i$.next();
            String id = parameterMapNode.getStringAttribute("id");
            String type = parameterMapNode.getStringAttribute("type");
            Class<?> parameterClass = this.resolveClass(type);
            List<XNode> parameterNodes = parameterMapNode.evalNodes("parameter");
            List<ParameterMapping> parameterMappings = new ArrayList();
            Iterator iterator = parameterNodes.iterator();

            while(iterator.hasNext()) {
                XNode parameterNode = (XNode)i$.next();
                String property = parameterNode.getStringAttribute("property");
                String javaType = parameterNode.getStringAttribute("javaType");
                String jdbcType = parameterNode.getStringAttribute("jdbcType");
                String resultMap = parameterNode.getStringAttribute("resultMap");
                String mode = parameterNode.getStringAttribute("mode");
                String typeHandler = parameterNode.getStringAttribute("typeHandler");
                Integer numericScale = parameterNode.getIntAttribute("numericScale");
                ParameterMode modeEnum = this.resolveParameterMode(mode);
                Class<?> javaTypeClass = this.resolveClass(javaType);
                JdbcType jdbcTypeEnum = this.resolveJdbcType(jdbcType);
                Class<? extends TypeHandler<?>> typeHandlerClass = (Class<? extends TypeHandler<?>>) this.resolveClass(typeHandler);
                ParameterMapping parameterMapping = this.builderAssistant.buildParameterMapping(parameterClass, property, javaTypeClass, jdbcTypeEnum, resultMap, modeEnum, typeHandlerClass, numericScale);
                parameterMappings.add(parameterMapping);
            }

            this.builderAssistant.addParameterMap(id, parameterClass, parameterMappings);
        }

    }

    private void resultMapElements(List<XNode> list) throws Exception {
        Iterator i$ = list.iterator();

        while(i$.hasNext()) {
            XNode resultMapNode = (XNode)i$.next();

            try {
                this.resultMapElement(resultMapNode);
            } catch (IncompleteElementException var5) {
                ;
            }
        }

    }

    private ResultMap resultMapElement(XNode resultMapNode) throws Exception {
        return this.resultMapElement(resultMapNode, Collections.<ResultMapping>emptyList());
    }

    private ResultMap resultMapElement(XNode resultMapNode, List<ResultMapping> additionalResultMappings) throws Exception {
        ErrorContext.instance().activity("processing " + resultMapNode.getValueBasedIdentifier());
        String id = resultMapNode.getStringAttribute("id", resultMapNode.getValueBasedIdentifier());
        String type = resultMapNode.getStringAttribute("type", resultMapNode.getStringAttribute("ofType", resultMapNode.getStringAttribute("resultType", resultMapNode.getStringAttribute("javaType"))));
        String extend = resultMapNode.getStringAttribute("extends");
        Boolean autoMapping = resultMapNode.getBooleanAttribute("autoMapping");
        Class<?> typeClass = this.resolveClass(type);
        Discriminator discriminator = null;
        List<ResultMapping> resultMappings = new ArrayList();
        resultMappings.addAll(additionalResultMappings);
        List<XNode> resultChildren = resultMapNode.getChildren();
        Iterator i$ = resultChildren.iterator();

        while(i$.hasNext()) {
            XNode resultChild = (XNode)i$.next();
            if ("constructor".equals(resultChild.getName())) {
                this.processConstructorElement(resultChild, typeClass, resultMappings);
            } else if ("discriminator".equals(resultChild.getName())) {
                discriminator = this.processDiscriminatorElement(resultChild, typeClass, resultMappings);
            } else {
                List<ResultFlag> flags = new ArrayList();
                if ("id".equals(resultChild.getName())) {
                    flags.add(ResultFlag.ID);
                }

                resultMappings.add(this.buildResultMappingFromContext(resultChild, typeClass, flags));
            }
        }

        ResultMapResolver resultMapResolver = new ResultMapResolver(this.builderAssistant, id, typeClass, extend, discriminator, resultMappings, autoMapping);

        try {
            return resultMapResolver.resolve();
        } catch (IncompleteElementException var14) {
            this.configuration.addIncompleteResultMap(resultMapResolver);
            throw var14;
        }
    }

    private void processConstructorElement(XNode resultChild, Class<?> resultType, List<ResultMapping> resultMappings) throws Exception {
        List<XNode> argChildren = resultChild.getChildren();

        XNode argChild;
        ArrayList flags;
        for(Iterator i$ = argChildren.iterator(); i$.hasNext(); resultMappings.add(this.buildResultMappingFromContext(argChild, resultType, flags))) {
            argChild = (XNode)i$.next();
            flags = new ArrayList();
            flags.add(ResultFlag.CONSTRUCTOR);
            if ("idArg".equals(argChild.getName())) {
                flags.add(ResultFlag.ID);
            }
        }

    }

    private Discriminator processDiscriminatorElement(XNode context, Class<?> resultType, List<ResultMapping> resultMappings) throws Exception {
        String column = context.getStringAttribute("column");
        String javaType = context.getStringAttribute("javaType");
        String jdbcType = context.getStringAttribute("jdbcType");
        String typeHandler = context.getStringAttribute("typeHandler");
        Class<?> javaTypeClass = this.resolveClass(javaType);
        Class<? extends TypeHandler<?>> typeHandlerClass = (Class<? extends TypeHandler<?>>) this.resolveClass(typeHandler);
        JdbcType jdbcTypeEnum = this.resolveJdbcType(jdbcType);
        Map<String, String> discriminatorMap = new HashMap();
        Iterator i$ = context.getChildren().iterator();

        while(i$.hasNext()) {
            XNode caseChild = (XNode)i$.next();
            String value = caseChild.getStringAttribute("value");
            String resultMap = caseChild.getStringAttribute("resultMap", this.processNestedResultMappings(caseChild, resultMappings));
            discriminatorMap.put(value, resultMap);
        }

        return this.builderAssistant.buildDiscriminator(resultType, column, javaTypeClass, jdbcTypeEnum, typeHandlerClass, discriminatorMap);
    }

    private void sqlElement(List<XNode> list) throws Exception {
        if (this.configuration.getDatabaseId() != null) {
            this.sqlElement(list, this.configuration.getDatabaseId());
        }

        this.sqlElement(list, (String)null);
    }

    private void sqlElement(List<XNode> list, String requiredDatabaseId) throws Exception {
        Iterator i$ = list.iterator();

        while(i$.hasNext()) {
            XNode context = (XNode)i$.next();
            String databaseId = context.getStringAttribute("databaseId");
            String id = context.getStringAttribute("id");
            id = this.builderAssistant.applyCurrentNamespace(id, false);
            if (this.databaseIdMatchesCurrent(id, databaseId, requiredDatabaseId)) {
                this.sqlFragments.put(id, context);
            }
        }

    }

    private boolean databaseIdMatchesCurrent(String id, String databaseId, String requiredDatabaseId) {
        if (requiredDatabaseId != null) {
            if (!requiredDatabaseId.equals(databaseId)) {
                return false;
            }
        } else {
            if (databaseId != null) {
                return false;
            }

            if (this.sqlFragments.containsKey(id)) {
                XNode context = (XNode)this.sqlFragments.get(id);
                if (context.getStringAttribute("databaseId") != null) {
                    return false;
                }
            }
        }

        return true;
    }

    private ResultMapping buildResultMappingFromContext(XNode context, Class<?> resultType, List<ResultFlag> flags) throws Exception {
        String property = context.getStringAttribute("property");
        String column = context.getStringAttribute("column");
        String javaType = context.getStringAttribute("javaType");
        String jdbcType = context.getStringAttribute("jdbcType");
        String nestedSelect = context.getStringAttribute("select");
        String nestedResultMap = context.getStringAttribute("resultMap", this.processNestedResultMappings(context, Collections.<ResultMapping>emptyList()));
        String notNullColumn = context.getStringAttribute("notNullColumn");
        String columnPrefix = context.getStringAttribute("columnPrefix");
        String typeHandler = context.getStringAttribute("typeHandler");
        String resultSet = context.getStringAttribute("resultSet");
        String foreignColumn = context.getStringAttribute("foreignColumn");
        boolean lazy = "lazy".equals(context.getStringAttribute("fetchType", this.configuration.isLazyLoadingEnabled() ? "lazy" : "eager"));
        Class<?> javaTypeClass = this.resolveClass(javaType);
        Class<? extends TypeHandler<?>> typeHandlerClass = (Class<? extends TypeHandler<?>>) this.resolveClass(typeHandler);
        JdbcType jdbcTypeEnum = this.resolveJdbcType(jdbcType);
        return this.builderAssistant.buildResultMapping(resultType, property, column, javaTypeClass, jdbcTypeEnum, nestedSelect, nestedResultMap, notNullColumn, columnPrefix, typeHandlerClass, flags, resultSet, foreignColumn, lazy);
    }

    private String processNestedResultMappings(XNode context, List<ResultMapping> resultMappings) throws Exception {
        if (("association".equals(context.getName()) || "collection".equals(context.getName()) || "case".equals(context.getName())) && context.getStringAttribute("select") == null) {
            ResultMap resultMap = this.resultMapElement(context, resultMappings);
            return resultMap.getId();
        } else {
            return null;
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
