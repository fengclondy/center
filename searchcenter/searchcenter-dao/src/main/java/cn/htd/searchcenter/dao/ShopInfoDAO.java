package cn.htd.searchcenter.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.searchcenter.domain.MemberCompanyInfoDTO;
import cn.htd.searchcenter.domain.ShopDTO;

public interface ShopInfoDAO {

	public List<ShopDTO> queryShopInfoBySyncTime(
			@Param("syncTime") Date syncTime, @Param("start") int start,
			@Param("end") int end);
	
	public int queryShopInfoCountBySyncTime(@Param("syncTime") Date syncTime);

	public String getAreaName(@Param("code") String code);

	public String getCidNames(@Param("cids") String cids);

	public int queryShopStatusByShopId(@Param("shopId") Long shopId);
	
	public String queryCidNameAndCidByShopId(@Param("shopId") Long shopId);

	public String queryShopQQByShopId(@Param("shopId") Long shopId);
}
