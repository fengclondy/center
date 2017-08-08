package cn.htd.membercenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.membercenter.dto.SellerCategoryBrandShieldDTO;

public interface SellerCategoryBrandShieldDAO extends BaseDAO<SellerCategoryBrandShieldDTO> {

	public int updateSellerShield(@Param("dto") SellerCategoryBrandShieldDTO dto);
	
	public SellerCategoryBrandShieldDTO selectSellerShieldById(@Param("sellerShieldId") int sellerShieldId);
}
