package cn.htd.goodscenter.domain;
import java.io.Serializable;
import java.util.Date;

/**商品到货通知实体类
 * Created by GZG on 2016/11/17.
 */
public class ItemArrivalNotify implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -604574261542749293L;

	private Long id;

    private String ctoken;

    private Long buyerId;

    private Long sellerId;

    private Long shopId;

    private Long itemId;

    private Long skuId;

    private Long notifyMobile;

    private String notifyStatus;

    private Date nofityTime;

    private String isBoxFlag;
    
    private Long createId;
    private String createName;
    private Date createTime;
    private Long modifyId;
    private String modifyName;
    private Date modifyTime;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCtoken() {
        return ctoken;
    }

    public void setCtoken(String ctoken) {
        this.ctoken = ctoken == null ? null : ctoken.trim();
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
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

    public Long getNotifyMobile() {
        return notifyMobile;
    }

    public void setNotifyMobile(Long notifyMobile) {
        this.notifyMobile = notifyMobile;
    }

    public String getNotifyStatus() {
        return notifyStatus;
    }

    public void setNotifyStatus(String notifyStatus) {
        this.notifyStatus = notifyStatus == null ? null : notifyStatus.trim();
    }

    public Date getNofityTime() {
        return nofityTime;
    }

    public void setNofityTime(Date nofityTime) {
        this.nofityTime = nofityTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getIsBoxFlag() {
		return isBoxFlag;
	}

	public void setIsBoxFlag(String isBoxFlag) {
		this.isBoxFlag = isBoxFlag;
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

	public Long getModifyId() {
		return modifyId;
	}

	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
    
}
