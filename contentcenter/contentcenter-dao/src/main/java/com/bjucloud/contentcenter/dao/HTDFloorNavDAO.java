package com.bjucloud.contentcenter.dao;

import cn.htd.common.dao.orm.BaseDAO;
import com.bjucloud.contentcenter.dto.HTDFloorNavDTO;

import java.util.List;

/**
 * Created by thinkpad on 2017/1/20.
 */
public interface HTDFloorNavDAO extends BaseDAO<HTDFloorNavDTO> {

    public List<HTDFloorNavDTO> selectBySortNum(Long sortNum);

    public Integer updateBySortAndFloorId(HTDFloorNavDTO htdFloorNavDTO);

    public List<HTDFloorNavDTO> selectByFloorId(Long floorId);

    public List<HTDFloorNavDTO> selectRecName();


}
