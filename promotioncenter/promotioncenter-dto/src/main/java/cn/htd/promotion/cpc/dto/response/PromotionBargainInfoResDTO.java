package cn.htd.promotion.cpc.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 砍价活动信息表
 * @author xxj
 *
 */
public class PromotionBargainInfoResDTO extends PromotionAccumulatyDTO implements Serializable{

	private static final long serialVersionUID = 8605548926689874138L;

	private String bargainId;//砍价ID
	
	@NotBlank(message = "商品图片不能为空")
	private String goodsPicture;//商品图片
	
	@NotBlank(message = "商品名称不能为空")
	private String goodsName;//商品名称
	
	@NotNull(message = "商品原价不能为空")
	@Min(value = 0, message = "商品原价必须大于0")
	private BigDecimal goodsCostPrice;//商品原价
	
	@NotNull(message = "商品底价不能为空")
	@Min(value = 0, message = "商品底价必须大于0")
	private BigDecimal goodsFloorPrice;//商品底价
	
	@NotNull(message = "参与砍价的人数不能为空")
	@Min(value = 1, message = "参与砍价的人数必须大于0")
	private Integer partakeTimes;//参与砍价的人数
	
	@NotNull(message = "参砍商品数量不能为空")
	@Min(value = 1, message = "参砍商品数量必须大于0")
	private Integer goodsNum;//参砍商品数量
	
	private Integer virtualQuantity;//汇掌柜端虚拟显示人数增加

	private String promotionSlogan;//宣传语
	
	public Integer getVirtualQuantity() {
		return virtualQuantity;
	}

	public void setVirtualQuantity(Integer virtualQuantity) {
		this.virtualQuantity = virtualQuantity;
	}

	public String getPromotionSlogan() {
		return promotionSlogan;
	}

	public void setPromotionSlogan(String promotionSlogan) {
		this.promotionSlogan = promotionSlogan;
	}

	public String getBargainId() {
		return bargainId;
	}

	public void setBargainId(String bargainId) {
		this.bargainId = bargainId;
	}

	public String getGoodsPicture() {
		return goodsPicture;
	}

	public void setGoodsPicture(String goodsPicture) {
		this.goodsPicture = goodsPicture;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public BigDecimal getGoodsCostPrice() {
		return goodsCostPrice;
	}

	public void setGoodsCostPrice(BigDecimal goodsCostPrice) {
		this.goodsCostPrice = goodsCostPrice;
	}

	public BigDecimal getGoodsFloorPrice() {
		return goodsFloorPrice;
	}

	public void setGoodsFloorPrice(BigDecimal goodsFloorPrice) {
		this.goodsFloorPrice = goodsFloorPrice;
	}

	public Integer getPartakeTimes() {
		return partakeTimes;
	}

	public void setPartakeTimes(Integer partakeTimes) {
		this.partakeTimes = partakeTimes;
	}

	public Integer getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}

	public void setPromotionBargainInfoResDTO(PromotionBargainInfoResDTO promotionBargainInfoResDTO) {
		super.setPromotionAccumulaty(promotionBargainInfoResDTO);
		this.bargainId = promotionBargainInfoResDTO.getBargainId();
		this.goodsPicture = promotionBargainInfoResDTO.getGoodsPicture();
		this.goodsName = promotionBargainInfoResDTO.getGoodsName();
		this.goodsCostPrice = promotionBargainInfoResDTO.getGoodsCostPrice();
		this.goodsFloorPrice = promotionBargainInfoResDTO.getGoodsFloorPrice();
		this.partakeTimes = promotionBargainInfoResDTO.getPartakeTimes();
		this.goodsNum = promotionBargainInfoResDTO.getGoodsNum();
		this.promotionSlogan = promotionBargainInfoResDTO.getPromotionSlogan();
		this.virtualQuantity = promotionBargainInfoResDTO.getVirtualQuantity();
	}
	
}
