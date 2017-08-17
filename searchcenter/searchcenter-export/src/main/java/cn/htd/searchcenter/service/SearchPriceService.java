package cn.htd.searchcenter.service;

import java.math.BigDecimal;

import cn.htd.searchcenter.domain.PriceDTO;

public interface SearchPriceService {

	public PriceDTO queryItemPriceByItemId(Long itemId, int isBox);
	
	public BigDecimal queryExternalItemPrice(Long itemId) throws Exception;
}
