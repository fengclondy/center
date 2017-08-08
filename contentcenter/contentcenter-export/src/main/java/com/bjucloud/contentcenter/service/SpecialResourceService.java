package com.bjucloud.contentcenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dto.SpecialResourceDTO;

/**
 * Created by thinkpad on 2017/2/13.
 */
public interface SpecialResourceService {
    public DataGrid<SpecialResourceDTO> queryListByCondition(SpecialResourceDTO record, Pager page) ;

    /**
     * <p>Discription:静态资源删除</p>
     */
    public ExecuteResult<String> delById(Long id);

    /**
     * <p>
     * Discription:静态资源详情查询
     * </p>
     */
    public SpecialResourceDTO queryById(Long id) ;

    /**
     * 静态资源上下架
     * @return
     */
    public ExecuteResult<String> modify(SpecialResourceDTO specialResourceDTO) ;


    /**
     * <p>Discription:静态资源新增</p>
     */
    public ExecuteResult<String> save(SpecialResourceDTO specialResourceDTO);

	public ExecuteResult<SpecialResourceDTO> queryByLink(String topicCode);
}
