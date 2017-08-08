package cn.htd.tradecenter.dao;


import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.tradecenter.dto.ShopDeliveryTypeDTO;
import cn.htd.tradecenter.dto.ShopFreightTemplateDTO;

/**
 * <p>Description: [运费模板dao层]</p>
 */
public interface ShopFreightTemplateDAO extends BaseDAO<ShopFreightTemplateDTO> {

    public void  addForSeller(ShopFreightTemplateDTO dto);

}