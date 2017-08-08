package com.bjucloud.contentcenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dto.FloorInfoDTO;
import com.bjucloud.contentcenter.dto.HTDFloorNavDTO;

/**
 * Created by thinkpad on 2017/1/22.
 */
public interface FloorInfoService {
    /**
     * 根据条件查询楼层基本信息
     * @param record
     * @param page
     * @return
     */
    DataGrid<FloorInfoDTO> queryListByCondition(FloorInfoDTO record, Pager page);

    /**
     * 根据条件查询楼层基本信息
     * @return
     */
    ExecuteResult<String> addFloorInfo(FloorInfoDTO floorInfoDTO);


    /**
     * <p>
     * Discription:[楼层导航根据id删除]
     * </p>
     */
    public ExecuteResult<String> delete(Integer id);

    /**
     * <p>
     * Discription:[根据ID查询]
     * </p>
     */
    public ExecuteResult<FloorInfoDTO> queryById(Long id);

    /**
     * <p>
     * Discription:[根据ID删除]
     * </p>
     */
    public ExecuteResult<String> delete(FloorInfoDTO floorInfoDTO);

    /**
     * <p>
     * Discription:[根据ID修改状态]
     * </p>
     */
    public ExecuteResult<String> modifyByCondition(FloorInfoDTO dto);

    /**
     * <p>
     * Discription:[根据ID修改状态]
     * </p>
     */
    public ExecuteResult<String> modifyStatus(FloorInfoDTO floorInfoDTO);


    public ExecuteResult<String> modifyStatusBySortNum(String status, Long sortNum);

    DataGrid<FloorInfoDTO> queryListAll(Pager page);
}
