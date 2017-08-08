package cn.htd.zeus.tc.biz.util;

import java.math.BigDecimal;
import java.util.List;

import cn.htd.pricecenter.domain.ItemSkuLadderPrice;

public class ExternalSupplierCostCaculateUtil {

	/**
	 * 计算外部供应商商品阶梯价价格
	 * 
	 * @param mallSku
	 * @param skuPrice
	 * @return
	 */
	public BigDecimal caculateLadderPrice4outer(int productCount,
			List<ItemSkuLadderPrice> ladderPriceList) {
		BigDecimal price = new BigDecimal(0);
		// List<ItemSkuLadderPrice> ladderPriceList =
		// skuPrice.getLadderPriceList();
		if (null != ladderPriceList) {
			for (ItemSkuLadderPrice ladder : ladderPriceList) {
				if (productCount >= ladder.getMinNum() && productCount <= ladder.getMaxNum()) {
					price = ladder.getPrice();
					break;
				}
			}
		}
		return price;
	}

}
