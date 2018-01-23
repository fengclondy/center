/**
 * Copyright (C), 2013-2018, 汇通达网络有限公司
 * FileName:    ValetOrderGroup.java
 * Author:      shihb
 * Date:        2018年1月22日
 * Description: 代客下单validate用分组定义
 * History:     
 * shihb     2018年1月22日 1.0         创建
 */
package cn.htd.tradecenter.dto;

/**
 * 代客下单validate用分组定义
 */
public interface ValetOrderGroup {

    /**
     * 超级经理人
     */
    public static interface SuperManager {
    }

    /**
     * 超级供应商
     */
    public static interface Vms {
    }

}
