package cn.htd.tradecenter.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.tradecenter.dto.ShopDeliveryTypeDTO;


/**
 * <p>Description: [运费模板-优惠方式dao层]</p>
 */
public interface ShopDeliveryTypeDAO extends BaseDAO<ShopDeliveryTypeDTO> {

    public List<ShopDeliveryTypeDTO> selectListByCondition(@Param("entity") ShopDeliveryTypeDTO dto);
}