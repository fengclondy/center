package cn.htd.searchcenter.service;

import java.math.BigDecimal;

public interface SearchPriceService {


	public BigDecimal queryJDVipPrice(Long itemId);

	public String queryBuyerGradePrice(Long itemId, Integer isBoxFlag);

	public String queryGroupPrice(Long itemId, Integer isBoxFlag);

	public String queryAreaPrice(Long itemId, Integer isBoxFlag);
}
