package cn.htd.promotion.cpc.dto.request;

import org.hibernate.validator.constraints.NotEmpty;

public class ValidateLuckDrawReqDTO extends GenricReqDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2993408983774879818L;

	/**
	 * 会员店id
	 * 小b的id
	 */
	@NotEmpty(message = "orgId不能为空")
	private String orgId;

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
}
