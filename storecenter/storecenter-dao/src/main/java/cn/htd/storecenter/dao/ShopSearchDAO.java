package cn.htd.storecenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.storecenter.dto.ShopDTO;

public interface ShopSearchDAO {

	/**
	 * 
	 * <p>
	 * Discription:[搜索店铺]
	 * </p>
	 * 
	 * @param inDTO
	 * @param page
	 */
	List<ShopDTO> searchShop(@Param("entity") ShopDTO inDTO, @Param("page") Pager page);

	/**
	 * 
	 * <p>
	 * Discription:[搜索店铺总数]
	 * </p>
	 * 
	 * @param inDTO
	 * @return
	 */
	Long searchShopCount(@Param("entity") ShopDTO inDTO);

}
