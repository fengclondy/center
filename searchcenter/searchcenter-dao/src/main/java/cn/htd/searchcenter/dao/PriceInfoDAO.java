package cn.htd.searchcenter.dao;


import java.math.BigDecimal;

import org.apache.ibatis.annotations.Param;

import cn.htd.searchcenter.domain.PriceDTO;

public interface PriceInfoDAO {

	public PriceDTO queryItemPriceByItemId(@Param("itemId") Long itemId, @Param("isBox") int isBox);

	public BigDecimal queryExternalItemPrice(@Param("itemId") Long itemId);
}
