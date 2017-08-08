package cn.htd.searchcenter.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.htd.searchcenter.dao.PriceInfoDAO;
import cn.htd.searchcenter.domain.PriceDTO;
import cn.htd.searchcenter.service.SearchPriceService;

@Service("searchPriceService")
public class SearchPriceServiceImpl implements SearchPriceService{

	@Resource
	private PriceInfoDAO priceInfoDao;

	@Override
	public BigDecimal queryJDVipPrice(Long itemId) {
		BigDecimal price = priceInfoDao.queryJDVipPrice(itemId);
		if(null != price && price.compareTo(BigDecimal.ZERO) == 1)
		{
			return price;
		}
		else
		{
			return null;
		}
	}

	@Override
	public String queryBuyerGradePrice(Long itemId, Integer isBoxFlag) {
		String sellerIdAndpriceAndgradeAssembling = "";
		List<PriceDTO> priceList = priceInfoDao.queryBuyerGradePrice(itemId,isBoxFlag);
		if(null != priceList)
		{
			for (PriceDTO price : priceList) 
			{
				sellerIdAndpriceAndgradeAssembling += price.getSellerId() + ":" + price.getBuyerGrade() + ":" + price.getPrice() + ",";
			}
		}
		if(!"".equals(sellerIdAndpriceAndgradeAssembling))
		{
			sellerIdAndpriceAndgradeAssembling = sellerIdAndpriceAndgradeAssembling.substring(0, sellerIdAndpriceAndgradeAssembling.length() - 1);
		}
		return sellerIdAndpriceAndgradeAssembling;
	}

	@Override
	public String queryGroupPrice(Long itemId, Integer isBoxFlag) {
		String sellerIdAndpriceAssembling = "";
		List<PriceDTO> priceList = priceInfoDao.queryGroupPrice(itemId,isBoxFlag);
		if(null != priceList)
		{
			for (PriceDTO price : priceList) 
			{
				sellerIdAndpriceAssembling += price.getSellerId() + ":" + price.getPrice() + ",";
			}
		}
		if(!"".equals(sellerIdAndpriceAssembling))
		{
			sellerIdAndpriceAssembling = sellerIdAndpriceAssembling.substring(0, sellerIdAndpriceAssembling.length() - 1);
		}
		return sellerIdAndpriceAssembling;
	}

	@Override
	public String queryAreaPrice(Long itemId, Integer isBoxFlag) {
		String sellerIdAndareaCodeAndpriceAssembling = "";
		List<PriceDTO> priceList = priceInfoDao.queryAreaPrice(itemId,isBoxFlag);
		if(null != priceList)
		{
			for (PriceDTO price : priceList) 
			{
				sellerIdAndareaCodeAndpriceAssembling += price.getSellerId() + ":" + price.getPrice() + ",";
			}
		}
		if(!"".equals(sellerIdAndareaCodeAndpriceAssembling))
		{
			sellerIdAndareaCodeAndpriceAssembling = sellerIdAndareaCodeAndpriceAssembling.substring(0, sellerIdAndareaCodeAndpriceAssembling.length() - 1);
		}
		return sellerIdAndareaCodeAndpriceAssembling;
	}
}
