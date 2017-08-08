package cn.htd.goodscenter.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.goodscenter.dto.ItemSalesVolumeDTO;

public interface ItemSalesVolumeDAO extends BaseDAO<ItemSalesVolumeDTO> {

	/**
	 * 
	 * <p>
	 * Discription:[更新销量]
	 * </p>
	 */
	void addList(@Param("inList") List<ItemSalesVolumeDTO> inList);

	/**
	 * 
	 * <p>
	 * Discription:[删除所有]
	 * </p>
	 */
	void deleteAll();

	List<ItemSalesVolumeDTO> querySaleVolumeBySyncTime(@Param("syncTime") Date syncTime,@Param("page") Pager pager);
}
