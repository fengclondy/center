package com.bjucloud.contentcenter.service;

/**
 * Created by peanut on 2017/2/13.
 */

import cn.htd.common.ExecuteResult;
import com.bjucloud.contentcenter.dto.LoginPageDTO;

/**
 *
 * <p>
 * Description: [登录页]
 * </p>
 */
public interface LoginPageService {

    /**
     *
     * <p>Discription:[登录页查询，查询出来只有一条数据，表中只会有一条数据，]</p>
     */
    public ExecuteResult<LoginPageDTO> getLogo();

    /**
     * 新增网址logo,[表中只会有一条数据]
     * @param record
     * @return
     */
    public ExecuteResult<String> save(LoginPageDTO record);
}
