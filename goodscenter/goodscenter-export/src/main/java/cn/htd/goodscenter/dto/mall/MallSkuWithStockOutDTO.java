package cn.htd.goodscenter.dto.mall;

import cn.htd.goodscenter.domain.ItemSkuPicture;
import cn.htd.goodscenter.domain.ItemSkuPublishInfo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 商品详情出参+
 * @author chenkang
 */
public class MallSkuWithStockOutDTO implements Serializable {

	private MallSkuOutDTO mallSkuOutDTO;

	private MallSkuStockOutDTO mallSkuStockOutDTO;

	public MallSkuOutDTO getMallSkuOutDTO() {
		return mallSkuOutDTO;
	}

	public void setMallSkuOutDTO(MallSkuOutDTO mallSkuOutDTO) {
		this.mallSkuOutDTO = mallSkuOutDTO;
	}

	public MallSkuStockOutDTO getMallSkuStockOutDTO() {
		return mallSkuStockOutDTO;
	}

	public void setMallSkuStockOutDTO(MallSkuStockOutDTO mallSkuStockOutDTO) {
		this.mallSkuStockOutDTO = mallSkuStockOutDTO;
	}

	@Override
	public String toString() {
		return "MallSkuWithStockOutDTO{" +
				"mallSkuOutDTO=" + mallSkuOutDTO +
				", mallSkuStockOutDTO=" + mallSkuStockOutDTO +
				'}';
	}
}
