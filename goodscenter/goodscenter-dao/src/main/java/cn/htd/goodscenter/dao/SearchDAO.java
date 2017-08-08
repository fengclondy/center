package cn.htd.goodscenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.goodscenter.domain.Item;
import cn.htd.goodscenter.dto.ItemSkuDTO;
import cn.htd.goodscenter.dto.SearchInDTO;

public interface SearchDAO {

	/**
	 * 
	 * <p>
	 * Discription:[查询搜索商品]
	 * </p>
	 */
	List<ItemSkuDTO> queryItemSkus(@Param("entity") SearchInDTO inDTO, @Param("page") Pager pager);

	/**
	 * 
	 * <p>
	 * Discription:[查询搜索商品总条数]
	 * </p>
	 */
	long queryItemSkusCount(@Param("entity") SearchInDTO inDTO, @Param("page") Pager pager);

	/**
	 * 
	 * <p>
	 * Discription:[查询搜索页要返回的搜索条件ID属性串 、品牌 和类目]
	 * </p>
	 */
	List<Item> queryConditionIds(@Param("entity") SearchInDTO inDTO);

	List<ItemSkuDTO> queryByItemSkusList(@Param("entity") SearchInDTO inDTO, @Param("page") Pager pager);
}
