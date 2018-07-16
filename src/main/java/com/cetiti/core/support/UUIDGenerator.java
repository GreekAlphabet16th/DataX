/*
 * File Name: UUIDGenerator.java
 * Copyright: Copyright 2014-2014 CETC52 CETITI All Rights Reserved.
 * Description: 
 * Author: Wuwuhao
 * Create Date: 2015-11-20

 * Modifier: Wuwuhao
 * Modify Date: 2015-11-20
 * Bugzilla Id: 
 * Modify Content: 
 */
package com.cetiti.core.support;

import java.util.UUID;

/**
 * ID生成器,替代数据库自增长列或者序列的方式，在应用程序中产生数据记录的ID 优点：提高数据库可移植性、应用灵活性，可在分布式环境使用
 * 缺点：32位的ID对某些表显得过大，增大存储空间、索引时间
 *
 * @author zhouliyu
 * @version dataX V1.0.0
 */

public class UUIDGenerator
{
    /**
     * 生成一个32位的唯一ID
     */
    public static String generate()
    {
        String id = UUID.randomUUID().toString();
        return id.replaceAll("-", "");
    }

}
