/**
 * Copyright (C), 2013-2018, 汇通达网络有限公司
 * FileName: 	AbstractConvert.java
 * Author:      zhangzhifeng
 * Date:     	2018年1月9日
 * Description: DMO，DTO转换类
 */
package com.bjucloud.contentcenter.service.convert;

import java.util.ArrayList;
import java.util.List;

/**
 * DMO，DTO转换类
 */
public abstract class AbstractConvert<SOURCE, TARGET> {

    /**
     * 返回多个目标对象
     *
     * @param sources
     * @return List<TARGET>
     */
    public List<TARGET> toTarget(List<SOURCE> sources) {

        if (null == sources) {
            return null;
        }
        if (sources.size() < 1) {
            return null;
        }

        List<TARGET> target = new ArrayList<TARGET>(sources.size());
        for (SOURCE source : sources) {
            target.add(toTarget(source));
        }

        return target;
    }

    /**
     * 返回多个源对象
     *
     * @param targets
     * @return List<SOURCE>
     */
    public List<SOURCE> toSource(List<TARGET> targets) {

        if (null == targets) {
            return null;
        }
        if (targets.size() < 1) {
            return null;
        }

        List<SOURCE> sources = new ArrayList<SOURCE>(targets.size());
        for (TARGET target : targets) {
            sources.add(toSource(target));
        }

        return sources;
    }

    /**
     * 返回单个源对象
     *
     * @param target
     * @return SOURCE
     */
    public SOURCE toSource(TARGET target) {
        if (null == target) {
            return null;
        }

        return populateSource(target);
    }

    /**
     * 返回单个目标对象
     *
     * @param source
     * @return TARGET
     */
    public TARGET toTarget(SOURCE source) {
        if (null == source) {
            return null;
        }

        return populateTarget(source);
    }

    /**
     * 填充目标对象
     *
     * @param source
     * @return TARGET
     */
    protected abstract TARGET populateTarget(SOURCE source);


    /**
     * 填充目标对象
     *
     * @param target
     * @return
     */
    protected abstract SOURCE populateSource(TARGET target);


}
