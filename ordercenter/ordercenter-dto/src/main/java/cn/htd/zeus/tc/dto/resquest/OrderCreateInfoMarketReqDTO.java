package cn.htd.zeus.tc.dto.resquest;

import java.io.Serializable;
import java.util.List;

/*
 * 请求促销中心专用参数
 */
public class OrderCreateInfoMarketReqDTO extends GenricReqDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3823923065500317404L;

	//优惠券类型
	private String couponType;
	
	//会员类型
	private String buyerType;
	
	//会员名称
	private String buyerName;
	
	//买家等级不能为空
	private String buyerGrade;
	
	/**
	 * 使用优惠券时设定优惠券编码列表
	 */
	private List<String> couponCodeList;
	
	//是否有限时购商品  0:没有，1：有
	private int isHasLimitedTimePurchase;

	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	public String getBuyerType() {
		return buyerType;
	}

	public void setBuyerType(String buyerType) {
		this.buyerType = buyerType;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getBuyerGrade() {
		return buyerGrade;
	}

	public void setBuyerGrade(String buyerGrade) {
		this.buyerGrade = buyerGrade;
	}

	public List<String> getCouponCodeList() {
		return couponCodeList;
	}

	public void setCouponCodeList(List<String> couponCodeList) {
		this.couponCodeList = couponCodeList;
	}
	
	public int getIsHasLimitedTimePurchase() {
		return isHasLimitedTimePurchase;
	}
	
	public void setIsHasLimitedTimePurchase(int isHasLimitedTimePurchase) {
		this.isHasLimitedTimePurchase = isHasLimitedTimePurchase;
	}	
}
