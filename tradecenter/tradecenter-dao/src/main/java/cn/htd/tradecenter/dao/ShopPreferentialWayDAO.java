package cn.htd.tradecenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.tradecenter.dto.ShopPreferentialWayDTO;


/**
 * <p>Description: [优惠方式dao]</p>
 */
public interface ShopPreferentialWayDAO extends BaseDAO<ShopPreferentialWayDTO> {


    public List<ShopPreferentialWayDTO> selectListByCondition(@Param("entity") ShopPreferentialWayDTO dto);

}
