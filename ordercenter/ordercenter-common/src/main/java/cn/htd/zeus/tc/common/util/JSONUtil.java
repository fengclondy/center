/*
 * Copyright (C), 2002-2014, 汇通达网络股份有限公司
 * FileName: JSONUtil.java
 * Author:   史辉
 * Date:     2014-1-20 下午04:33:29
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package cn.htd.zeus.tc.common.util;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 
 * 〈一句话功能简述〉<br>
 * JSON常用工具类
 * 
 * @author shihui
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class JSONUtil {

    /**
     * json转换成javaBean
     * 
     * @param <T>
     * @param json
     * @param clazz
     * @return
     */
    public static final <T> T parseObject(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    /**
     * json转换成javaBean
     * 
     * @param <T>
     * @param json
     * @param clazz
     * @return
     */
    public static final <T> List<T> parseArray(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

    /**
     * javaBean转换成json字符串
     * 
     * @param object
     * @return
     */
    public static final String toJSONString(Object object) {
        return JSON.toJSONString(object);
    }

    /**
     * 
     * 功能描述: json字符串中包含类信息<br>
     * 〈功能详细描述〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static final String toJSONStringWithClassName(Object object) {
        return JSON.toJSONString(object, SerializerFeature.WriteClassName);
    }
}
