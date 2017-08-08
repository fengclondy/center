package cn.htd.goodscenter.dto.mall;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 商品收藏入参
 */
public class ItemFavouriteInDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	@NotNull(message = "userId不能为null")
	private Long userId; //用户ID

	@NotNull(message = "sellerId不能为null")
	private Long sellerId; //卖家ID

	@NotNull(message = "shopId不能为null")
	private Long shopId; //店铺ID

	@NotNull(message = "itemId不能为null")
	private Long itemId; //商品ID

	@NotNull(message = "skuId不能为null")
	private Long skuId; //SKU_ID

	@NotNull(message = "createId不能为null")
    private Long createId; //创建人ID

	@NotNull(message = "createName不能为null")
	private String createName;//创建人名称

	@NotNull(message = "channelCode不能为null")
	private String channelCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	@Override
	public String toString() {
		return "ItemFavouriteInDTO{" +
				"id=" + id +
				", userId=" + userId +
				", sellerId=" + sellerId +
				", shopId=" + shopId +
				", itemId=" + itemId +
				", skuId=" + skuId +
				", createId=" + createId +
				", createName='" + createName + '\'' +
				", channelCode='" + channelCode + '\'' +
				'}';
	}
}
