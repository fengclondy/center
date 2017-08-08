package com.bjucloud.contentcenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dto.FloorQueryOutDTO;
import com.bjucloud.contentcenter.dto.HTDFloorNavDTO;

/**
 * Created by thinkpad on 2017/1/20.
 */
public interface HTDFloorNavService {
    /**
     * 根据id查询楼层导航信息
     * @return
     */
    ExecuteResult<HTDFloorNavDTO> queryById(Long id);


    /**
     * 更新楼层导航信息
     * @return
     */
    ExecuteResult<String> modifyFloorNavById(HTDFloorNavDTO dto);

    /**
     * 根据条件查询楼层导航设置
     * @param record
     * @param page
     * @return
     */
    ExecuteResult<DataGrid<HTDFloorNavDTO>> queryListByCondition(HTDFloorNavDTO record, Pager page);

    /**
     * 根据条件查询楼层和楼层导航内容
     * @param dto
     * @param page
     * @return
     */
    ExecuteResult<DataGrid<FloorQueryOutDTO>> queryFloorAndNavListByCondition(FloorQueryOutDTO dto,Pager page);


    /**
     * 添加
     * @param dto
     * @return
     */
    ExecuteResult<String> add(HTDFloorNavDTO dto);

    /**
     * 根据排序号查找
     * @return
     */
    DataGrid<HTDFloorNavDTO> queryBySortNum(Long sortNum);

    ExecuteResult<String> modifyBySortAndFloorId(HTDFloorNavDTO htdFloorNavDTO);

    DataGrid<HTDFloorNavDTO> queryByFloorId(Long floorId);

    public DataGrid<HTDFloorNavDTO> queryNavName();
}
