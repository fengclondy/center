package cn.htd.membercenter.dto;

import java.io.Serializable;

/**
 * @version 创建时间：2016年12月6日 下午2:49:48 类说明:归属关系管理
 */
public class SellerBelongRelationDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long memberId;// 会员ID
	private String memberCode;
	private Long curBelongSellerId;// 当前归属商家ID
	private String curBelongSellerName;// 当前归属商家名称
	private String curBelongSellerCode;// 公司编码

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public Long getCurBelongSellerId() {
		return curBelongSellerId;
	}

	public void setCurBelongSellerId(Long curBelongSellerId) {
		this.curBelongSellerId = curBelongSellerId;
	}

	public String getCurBelongSellerName() {
		return curBelongSellerName;
	}

	public void setCurBelongSellerName(String curBelongSellerName) {
		this.curBelongSellerName = curBelongSellerName;
	}

	public String getCurBelongSellerCode() {
		return curBelongSellerCode;
	}

	public void setCurBelongSellerCode(String curBelongSellerCode) {
		this.curBelongSellerCode = curBelongSellerCode;
	}

}
