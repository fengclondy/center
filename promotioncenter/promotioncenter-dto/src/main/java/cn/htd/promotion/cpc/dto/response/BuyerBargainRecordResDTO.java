package cn.htd.promotion.cpc.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 砍价明细表
 * @author xuxuejiao
 *
 */
public class BuyerBargainRecordResDTO implements Serializable {

	private static final long serialVersionUID = -2898091593219031058L;

	private Integer id;
	
	private String bargainCode;//砍价发起编号
	
	private String headSculptureUrl;//砍价人头像
	
	private String bargainPersonCode;//砍价人编码
	
	private String bargainPresonName;//砍价人名称
	
	private BigDecimal bargainAmount = BigDecimal.ZERO;//砍价金额
	
	private Date bargainTime;//砍价时间
	
	private Integer createId;//创建人ID
	
	private String createName;//创建人名称
	
	private Date createTime;//创建时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBargainCode() {
		return bargainCode;
	}

	public void setBargainCode(String bargainCode) {
		this.bargainCode = bargainCode;
	}

	public String getHeadSculptureUrl() {
		return headSculptureUrl;
	}

	public void setHeadSculptureUrl(String headSculptureUrl) {
		this.headSculptureUrl = headSculptureUrl;
	}

	public String getBargainPersonCode() {
		return bargainPersonCode;
	}

	public void setBargainPersonCode(String bargainPersonCode) {
		this.bargainPersonCode = bargainPersonCode;
	}

	public String getBargainPresonName() {
		return bargainPresonName;
	}

	public void setBargainPresonName(String bargainPresonName) {
		this.bargainPresonName = bargainPresonName;
	}

	public BigDecimal getBargainAmount() {
		return bargainAmount;
	}

	public void setBargainAmount(BigDecimal bargainAmount) {
		this.bargainAmount = bargainAmount;
	}

	public Date getBargainTime() {
		return bargainTime;
	}

	public void setBargainTime(Date bargainTime) {
		this.bargainTime = bargainTime;
	}

	public Integer getCreateId() {
		return createId;
	}

	public void setCreateId(Integer createId) {
		this.createId = createId;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
