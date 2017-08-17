package cn.htd.promotion.cpc.dto.response;

import java.io.Serializable;

/**
 * 促销活动层级DTO
 */
public class PromotionAccumulatyDTO extends PromotionInfoResDTO implements Serializable {

	private static final long serialVersionUID = -6475736552526494381L;

	/**
	 * ID
	 */
	private Long id;
	/**
	 * 层级序号
	 */
	private int levelNumber;
	/**
	 * 层级编码
	 */
	private String levelCode;
	/**
	 * 累计方式 0：无 1：固定值 2：每 3：满 4：第
	 */
	private String addupType;
	/**
	 * 层级数值
	 */
	private String levelAmount;
	/**
	 * 数值单位类型 1：金额:2：人数3：件数4：概率
	 */
	private String quantifierType;
	/**
	 * 层级描述
	 */
	private String levelDescribe;
	/**
	 * 删除标记
	 */
	private int deleteFlag;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getLevelNumber() {
		return levelNumber;
	}

	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public String getAddupType() {
		return addupType;
	}

	public void setAddupType(String addupType) {
		this.addupType = addupType;
	}

	public String getLevelAmount() {
		return levelAmount;
	}

	public void setLevelAmount(String levelAmount) {
		this.levelAmount = levelAmount;
	}

	public String getQuantifierType() {
		return quantifierType;
	}

	public void setQuantifierType(String quantifierType) {
		this.quantifierType = quantifierType;
	}

	public String getLevelDescribe() {
		return levelDescribe;
	}

	public void setLevelDescribe(String levelDescribe) {
		this.levelDescribe = levelDescribe;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public void setPromotionAccumulaty(PromotionAccumulatyDTO accuDTO) {
		super.setPromoionInfo(accuDTO);
		this.id = accuDTO.getId();
		this.levelNumber = accuDTO.getLevelNumber();
		this.levelCode = accuDTO.getLevelCode();
		this.addupType = accuDTO.getAddupType();
		this.levelAmount = accuDTO.getLevelAmount();
		this.quantifierType = accuDTO.getQuantifierType();
		this.levelDescribe = accuDTO.getLevelDescribe();
		this.deleteFlag = accuDTO.getDeleteFlag();
	}
}
