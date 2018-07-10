package com.cetiti.core.dataSource;

import org.apache.ibatis.builder.BaseBuilder;
import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.datasource.DataSourceFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.loader.ProxyFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaClass;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.type.JdbcType;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class DataXConfigBuilder extends BaseBuilder {
    private boolean parsed;
    private XPathParser parser;
    private List<String> mappers;
    private String environment;
    private ReflectorFactory localReflectorFactory;


    public DataXConfigBuilder(InputStream inputStream, String environment, Properties props, List<String> mappers) {
        this(new XPathParser(inputStream, true, props, new XMLMapperEntityResolver()), environment, props, mappers);
    }

    private DataXConfigBuilder(XPathParser parser, String environment, Properties props, List<String> mappers) {
        super(new Configuration());
        this.localReflectorFactory = new DefaultReflectorFactory();
        ErrorContext.instance().resource("SQL Mapper Configuration");
        this.configuration.setVariables(props);
        this.parsed = false;
        this.environment = environment;
        this.parser = parser;
        this.mappers = mappers;
    }

    public Configuration parse() {
        if (this.parsed) {
            throw new BuilderException("Each DataXConfigBuilder can only be used once.");
        } else {
            this.parsed = true;
            this.parseConfiguration(this.parser.evalNode("/configuration"), this.mappers);
            return this.configuration;
        }
    }

    private void parseConfiguration(XNode root,List<String> mappers) {
        try {
            Properties settings = this.settingsAsPropertiess(root.evalNode("settings"));
            this.propertiesElement(root.evalNode("properties"));
            this.loadCustomVfs(settings);
            this.typeAliasesElement(root.evalNode("typeAliases"));
            this.pluginElement(root.evalNode("plugins"));
            this.objectFactoryElement(root.evalNode("objectFactory"));
            this.objectWrapperFactoryElement(root.evalNode("objectWrapperFactory"));
            this.reflectionFactoryElement(root.evalNode("reflectionFactory"));
            this.settingsElement(settings);
            this.environmentsElement(root.evalNode("environments"));
            this.databaseIdProviderElement(root.evalNode("databaseIdProvider"));
            this.typeHandlerElement(root.evalNode("typeHandlers"));
            this.mapperElement(mappers);
        } catch (Exception var3) {
            throw new BuilderException("Error parsing SQL Mapper Configuration. Cause: " + var3, var3);
        }
    }

    private Properties settingsAsPropertiess(XNode context) {
        if (context == null) {
            return new Properties();
        } else {
            Properties props = context.getChildrenAsProperties();
            MetaClass metaConfig = MetaClass.forClass(Configuration.class, this.localReflectorFactory);
            Iterator i$ = props.keySet().iterator();

            Object key;
            do {
                if (!i$.hasNext()) {
                    return props;
                }

                key = i$.next();
            } while(metaConfig.hasSetter(String.valueOf(key)));

            throw new BuilderException("The setting " + key + " is not known.  Make sure you spelled it correctly (case sensitive).");
        }
    }

    private void loadCustomVfs(Properties props) throws ClassNotFoundException {
        String value = props.getProperty("vfsImpl");
        if (value != null) {
            String[] clazzes = value.split(",");
            String[] arr$ = clazzes;
            int len$ = clazzes.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String clazz = arr$[i$];
                if (!clazz.isEmpty()) {
                    this.configuration.setVfsImpl(Resources.classForName(clazz));
                }
            }
        }

    }

    private void typeAliasesElement(XNode parent) {
        if (parent != null) {
            Iterator i$ = parent.getChildren().iterator();

            while(i$.hasNext()) {
                XNode child = (XNode)i$.next();
                String alias;
                if ("package".equals(child.getName())) {
                    alias = child.getStringAttribute("name");
                    this.configuration.getTypeAliasRegistry().registerAliases(alias);
                } else {
                    alias = child.getStringAttribute("alias");
                    String type = child.getStringAttribute("type");

                    try {
                        Class<?> clazz = Resources.classForName(type);
                        if (alias == null) {
                            this.typeAliasRegistry.registerAlias(clazz);
                        } else {
                            this.typeAliasRegistry.registerAlias(alias, clazz);
                        }
                    } catch (ClassNotFoundException var7) {
                        throw new BuilderException("Error registering typeAlias for '" + alias + "'. Cause: " + var7, var7);
                    }
                }
            }
        }

    }

    private void pluginElement(XNode parent) throws Exception {
        if (parent != null) {
            Iterator i$ = parent.getChildren().iterator();

            while(i$.hasNext()) {
                XNode child = (XNode)i$.next();
                String interceptor = child.getStringAttribute("interceptor");
                Properties properties = child.getChildrenAsProperties();
                Interceptor interceptorInstance = (Interceptor)this.resolveClass(interceptor).newInstance();
                interceptorInstance.setProperties(properties);
                this.configuration.addInterceptor(interceptorInstance);
            }
        }

    }

    private void objectFactoryElement(XNode context) throws Exception {
        if (context != null) {
            String type = context.getStringAttribute("type");
            Properties properties = context.getChildrenAsProperties();
            ObjectFactory factory = (ObjectFactory)this.resolveClass(type).newInstance();
            factory.setProperties(properties);
            this.configuration.setObjectFactory(factory);
        }

    }

    private void objectWrapperFactoryElement(XNode context) throws Exception {
        if (context != null) {
            String type = context.getStringAttribute("type");
            ObjectWrapperFactory factory = (ObjectWrapperFactory)this.resolveClass(type).newInstance();
            this.configuration.setObjectWrapperFactory(factory);
        }

    }

    private void reflectionFactoryElement(XNode context) throws Exception {
        if (context != null) {
            String type = context.getStringAttribute("type");
            ReflectorFactory factory = (ReflectorFactory)this.resolveClass(type).newInstance();
            this.configuration.setReflectorFactory(factory);
        }

    }

    private void propertiesElement(XNode context) throws Exception {
        if (context != null) {
            Properties defaults = context.getChildrenAsProperties();
            String resource = context.getStringAttribute("resource");
            String url = context.getStringAttribute("url");
            if (resource != null && url != null) {
                throw new BuilderException("The properties element cannot specify both a URL and a resource based property file reference.  Please specify one or the other.");
            }

            if (resource != null) {
                defaults.putAll(Resources.getResourceAsProperties(resource));
            } else if (url != null) {
                defaults.putAll(Resources.getUrlAsProperties(url));
            }

            Properties vars = this.configuration.getVariables();
            if (vars != null) {
                defaults.putAll(vars);
            }

            this.parser.setVariables(defaults);
            this.configuration.setVariables(defaults);
        }

    }

    private void settingsElement(Properties props) throws Exception {
        this.configuration.setAutoMappingBehavior(AutoMappingBehavior.valueOf(props.getProperty("autoMappingBehavior", "PARTIAL")));
        this.configuration.setAutoMappingUnknownColumnBehavior(AutoMappingUnknownColumnBehavior.valueOf(props.getProperty("autoMappingUnknownColumnBehavior", "NONE")));
        this.configuration.setCacheEnabled(this.booleanValueOf(props.getProperty("cacheEnabled"), true).booleanValue());
        this.configuration.setProxyFactory((ProxyFactory)this.createInstance(props.getProperty("proxyFactory")));
        this.configuration.setLazyLoadingEnabled(this.booleanValueOf(props.getProperty("lazyLoadingEnabled"), false).booleanValue());
        this.configuration.setAggressiveLazyLoading(this.booleanValueOf(props.getProperty("aggressiveLazyLoading"), true).booleanValue());
        this.configuration.setMultipleResultSetsEnabled(this.booleanValueOf(props.getProperty("multipleResultSetsEnabled"), true).booleanValue());
        this.configuration.setUseColumnLabel(this.booleanValueOf(props.getProperty("useColumnLabel"), true).booleanValue());
        this.configuration.setUseGeneratedKeys(this.booleanValueOf(props.getProperty("useGeneratedKeys"), false).booleanValue());
        this.configuration.setDefaultExecutorType(ExecutorType.valueOf(props.getProperty("defaultExecutorType", "SIMPLE")));
        this.configuration.setDefaultStatementTimeout(this.integerValueOf(props.getProperty("defaultStatementTimeout"), (Integer)null));
        this.configuration.setDefaultFetchSize(this.integerValueOf(props.getProperty("defaultFetchSize"), (Integer)null));
        this.configuration.setMapUnderscoreToCamelCase(this.booleanValueOf(props.getProperty("mapUnderscoreToCamelCase"), false).booleanValue());
        this.configuration.setSafeRowBoundsEnabled(this.booleanValueOf(props.getProperty("safeRowBoundsEnabled"), false).booleanValue());
        this.configuration.setLocalCacheScope(LocalCacheScope.valueOf(props.getProperty("localCacheScope", "SESSION")));
        this.configuration.setJdbcTypeForNull(JdbcType.valueOf(props.getProperty("jdbcTypeForNull", "OTHER")));
        this.configuration.setLazyLoadTriggerMethods(this.stringSetValueOf(props.getProperty("lazyLoadTriggerMethods"), "equals,clone,hashCode,toString"));
        this.configuration.setSafeResultHandlerEnabled(this.booleanValueOf(props.getProperty("safeResultHandlerEnabled"), true).booleanValue());
        this.configuration.setDefaultScriptingLanguage(this.resolveClass(props.getProperty("defaultScriptingLanguage")));
        this.configuration.setCallSettersOnNulls(this.booleanValueOf(props.getProperty("callSettersOnNulls"), false).booleanValue());
        this.configuration.setLogPrefix(props.getProperty("logPrefix"));
        this.configuration.setLogImpl(this.resolveClass(props.getProperty("logImpl")));
        this.configuration.setConfigurationFactory(this.resolveClass(props.getProperty("configurationFactory")));
    }

    private void environmentsElement(XNode context) throws Exception {
        if (context != null) {
            if (this.environment == null) {
                this.environment = context.getStringAttribute("default");
            }

            Iterator i$ = context.getChildren().iterator();

            while(i$.hasNext()) {
                XNode child = (XNode)i$.next();
                String id = child.getStringAttribute("id");
                if (this.isSpecifiedEnvironment(id)) {
                    TransactionFactory txFactory = this.transactionManagerElement(child.evalNode("transactionManager"));
                    DataSourceFactory dsFactory = this.dataSourceElement(child.evalNode("dataSource"));
                    DataSource dataSource = dsFactory.getDataSource();
                    Environment.Builder environmentBuilder = (new Environment.Builder(id)).transactionFactory(txFactory).dataSource(dataSource);
                    this.configuration.setEnvironment(environmentBuilder.build());
                }
            }
        }

    }

    private void databaseIdProviderElement(XNode context) throws Exception {
        DatabaseIdProvider databaseIdProvider = null;
        if (context != null) {
            String type = context.getStringAttribute("type");
            if ("VENDOR".equals(type)) {
                type = "DB_VENDOR";
            }

            Properties properties = context.getChildrenAsProperties();
            databaseIdProvider = (DatabaseIdProvider)this.resolveClass(type).newInstance();
            databaseIdProvider.setProperties(properties);
        }

        Environment environment = this.configuration.getEnvironment();
        if (environment != null && databaseIdProvider != null) {
            String databaseId = databaseIdProvider.getDatabaseId(environment.getDataSource());
            this.configuration.setDatabaseId(databaseId);
        }

    }

    private TransactionFactory transactionManagerElement(XNode context) throws Exception {
        if (context != null) {
            String type = context.getStringAttribute("type");
            Properties props = context.getChildrenAsProperties();
            TransactionFactory factory = (TransactionFactory)this.resolveClass(type).newInstance();
            factory.setProperties(props);
            return factory;
        } else {
            throw new BuilderException("Environment declaration requires a TransactionFactory.");
        }
    }

    private DataSourceFactory dataSourceElement(XNode context) throws Exception {
        if (context != null) {
            String type = context.getStringAttribute("type");
            Properties props = context.getChildrenAsProperties();
            DataSourceFactory factory = (DataSourceFactory)this.resolveClass(type).newInstance();
            factory.setProperties(props);
            return factory;
        } else {
            throw new BuilderException("Environment declaration requires a DataSourceFactory.");
        }
    }

    private void typeHandlerElement(XNode parent) throws Exception {
        if (parent != null) {
            Iterator i$ = parent.getChildren().iterator();

            while(i$.hasNext()) {
                XNode child = (XNode)i$.next();
                String typeHandlerPackage;
                if ("package".equals(child.getName())) {
                    typeHandlerPackage = child.getStringAttribute("name");
                    this.typeHandlerRegistry.register(typeHandlerPackage);
                } else {
                    typeHandlerPackage = child.getStringAttribute("javaType");
                    String jdbcTypeName = child.getStringAttribute("jdbcType");
                    String handlerTypeName = child.getStringAttribute("handler");
                    Class<?> javaTypeClass = this.resolveClass(typeHandlerPackage);
                    JdbcType jdbcType = this.resolveJdbcType(jdbcTypeName);
                    Class<?> typeHandlerClass = this.resolveClass(handlerTypeName);
                    if (javaTypeClass != null) {
                        if (jdbcType == null) {
                            this.typeHandlerRegistry.register(javaTypeClass, typeHandlerClass);
                        } else {
                            this.typeHandlerRegistry.register(javaTypeClass, jdbcType, typeHandlerClass);
                        }
                    } else {
                        this.typeHandlerRegistry.register(typeHandlerClass);
                    }
                }
            }
        }

    }

    private void mapperElement(List<String> parent) throws Exception {
        if (!parent.isEmpty()) {
            Iterator i$ = parent.iterator();
            while(true) {
                while(i$.hasNext()) {
                    String url = (String)i$.next();
                    DataXMapperBuilder mapperParser;
                    InputStream inputStream;
                    if (url != null) {
                            ErrorContext.instance().resource(url);
                            inputStream = Resources.getUrlAsStream(url);
                            mapperParser = new DataXMapperBuilder(inputStream, this.configuration, url, this.configuration.getSqlFragments());
                            mapperParser.parse();
                    }
                }

                return;
            }
        }
    }

    private boolean isSpecifiedEnvironment(String id) {
        if (this.environment == null) {
            throw new BuilderException("No environment specified.");
        } else if (id == null) {
            throw new BuilderException("Environment requires an id attribute.");
        } else {
            return this.environment.equals(id);
        }
    }

    
}
