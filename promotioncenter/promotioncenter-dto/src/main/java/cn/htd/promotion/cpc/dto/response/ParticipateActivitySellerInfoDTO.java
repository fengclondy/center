package cn.htd.promotion.cpc.dto.response;

import java.util.Set;

public class ParticipateActivitySellerInfoDTO extends GenricResDTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4614150648768322409L;
	/**
	 * 指定卖家code
	 */
	private Set<String> sellerDetailSet;

	public Set<String> getSellerDetailSet() {
		return sellerDetailSet;
	}

	public void setSellerDetailSet(Set<String> sellerDetailSet) {
		this.sellerDetailSet = sellerDetailSet;
	}
}
