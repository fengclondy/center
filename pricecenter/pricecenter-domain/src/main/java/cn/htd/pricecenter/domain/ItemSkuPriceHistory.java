package cn.htd.pricecenter.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ItemSkuPriceHistory implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5176580937282989443L;

	private Long id;

    private Long skuId;

    private String channelCode;

    private BigDecimal areaSalePrice;

    private BigDecimal boxSalePrice;

    private Long createId;

    private String createName;

    private Date createTime;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode == null ? null : channelCode.trim();
    }

    public BigDecimal getAreaSalePrice() {
        return areaSalePrice;
    }

    public void setAreaSalePrice(BigDecimal areaSalePrice) {
        this.areaSalePrice = areaSalePrice;
    }

    public BigDecimal getBoxSalePrice() {
        return boxSalePrice;
    }

    public void setBoxSalePrice(BigDecimal boxSalePrice) {
        this.boxSalePrice = boxSalePrice;
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
        this.createName = createName == null ? null : createName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}