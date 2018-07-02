package com.cetiti.core.builder;

import org.apache.ibatis.builder.BaseBuilder;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.session.Configuration;

import java.util.Properties;

public class XMLServiceBuilder extends BaseBuilder {
    private boolean parsed;
    private XPathParser parser;
    private String environment;
    private ReflectorFactory localReflectorFactory;


    public XMLServiceBuilder(XPathParser parser, String environment, Properties props) {
        super(new Configuration());

    }
}
