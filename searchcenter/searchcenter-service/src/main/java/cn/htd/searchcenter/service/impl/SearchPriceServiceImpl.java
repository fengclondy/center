package cn.htd.searchcenter.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.htd.searchcenter.dao.PriceInfoDAO;
import cn.htd.searchcenter.datasource.DataSource;
import cn.htd.searchcenter.domain.PriceDTO;
import cn.htd.searchcenter.service.SearchPriceService;

@Service("searchPriceService")
@DataSource("dataSource_priceCenter")
public class SearchPriceServiceImpl implements SearchPriceService{

	@Resource
	private PriceInfoDAO priceInfoDao;

	@Override
	public PriceDTO queryItemPriceByItemId(Long itemId, int isBox) {
		return priceInfoDao.queryItemPriceByItemId(itemId, isBox);
	}
	
	@Override
	public BigDecimal queryExternalItemPrice(Long itemId) throws Exception {
		return priceInfoDao.queryExternalItemPrice(itemId);
	}
}
