package cn.htd.searchcenter.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.searchcenter.domain.ItemDTO;

public interface ItemInfoDAO {

	public List<ItemDTO> queryItemInfoBySyncTime(
			@Param("syncTime") Date syncTime, @Param("start") int start,
			@Param("end") int end, @Param("nowTime") Date nowTime);

	public int queryItemInfoBySyncTimCount(@Param("syncTime") Date syncTime,
			@Param("nowTime") Date nowTime);

	public Long queryItemInfoNotShield(@Param("shopId") Long shopId,
			@Param("brandId") Long brandId, @Param("cid") Long cid);

	public List<ItemDTO> queryItemAttrInfoBySyncTime(
			@Param("syncTime") Date syncTime, @Param("itemId") Long itemId);

	/**
	 * 京东商品
	 * @param syncTime
	 * @param start
	 * @param end
	 * @return
	 */
	public List<ItemDTO> queryJDItemInfoBySyncTime(@Param("syncTime") Date syncTime, @Param("start") int start,
			@Param("end") int end);
	public int queryJDItemInfoCountBySyncTime(@Param("syncTime") Date syncTime);

	/**
	 * 外部商品
	 * @param syncTime
	 * @param start
	 * @param end
	 * @return
	 */
	public List<ItemDTO> queryExternalItemInfoBySyncTime(@Param("syncTime") Date syncTime, @Param("start") int start,
			@Param("end") int end);
	public int queryExternalItemInfoCountBySyncTime(@Param("syncTime") Date syncTime);

	/**
	 * 秒杀商品
	 * @param syncTime
	 * @param start
	 * @param end
	 * @return
	 */
	public List<ItemDTO> querySeckillItemInfoBySyncTime(@Param("syncTime") Date syncTime, @Param("start") int start,
			@Param("end") int end);
	public int querySeckillItemInfoCountBySyncTime(@Param("syncTime") Date syncTime);

	/**
	 * 汇通达内部大厅商品
	 * @param syncTime
	 * @param start
	 * @param end
	 * @return
	 */
	public List<ItemDTO> queryHTDPublicItemInfoBySyncTime(@Param("syncTime") Date syncTime, @Param("start") int start,
			@Param("end") int end);
	public int queryHTDPublicItemInfoCountBySyncTime(@Param("syncTime") Date syncTime);

	/**
	 * 汇通达内部包厢商品 
	 * @param syncTime
	 * @param start
	 * @param end
	 * @return
	 */
	public List<ItemDTO> queryHTDBoxItemInfoBySyncTime(@Param("syncTime") Date syncTime, @Param("start") int start,
			@Param("end") int end);
	public int queryHTDBoxItemInfoCountBySyncTime(@Param("syncTime") Date syncTime);
}
