package cn.htd.promotion.cpc.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 砍价活动信息表
 * @author admin
 *
 */
public class PromotionBargainInfoResDTO implements Serializable{

	private static final long serialVersionUID = 8605548926689874138L;

	private Integer bargainId;//砍价ID
	
	private String promotionId;//促销活动编码
	
	private String levelCode;//层级编码
	
	private String goods_picture;//商品图片
	
	private String goods_name;//商品名称
	
	private BigDecimal goods_cost_price;//商品原价
	
	private BigDecimal goods_floor_price;//商品底价
	
	private Integer partake_times;//参与砍价的人数
	
	private Integer goods_num;//参砍商品数量
	
private Integer createId;//创建人ID
	
	private String createName;//创建人名称
	
	private Date createTime;//创建时间
	
	private Integer modifyId;//更新人ID
	
	private String modifyName;//更新人名称
	
	private Date modifyTime;//更新时间

	public Integer getBargainId() {
		return bargainId;
	}

	public void setBargainId(Integer bargainId) {
		this.bargainId = bargainId;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public String getGoods_picture() {
		return goods_picture;
	}

	public void setGoods_picture(String goods_picture) {
		this.goods_picture = goods_picture;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public BigDecimal getGoods_cost_price() {
		return goods_cost_price;
	}

	public void setGoods_cost_price(BigDecimal goods_cost_price) {
		this.goods_cost_price = goods_cost_price;
	}

	public BigDecimal getGoods_floor_price() {
		return goods_floor_price;
	}

	public void setGoods_floor_price(BigDecimal goods_floor_price) {
		this.goods_floor_price = goods_floor_price;
	}

	public Integer getPartake_times() {
		return partake_times;
	}

	public void setPartake_times(Integer partake_times) {
		this.partake_times = partake_times;
	}

	public Integer getGoods_num() {
		return goods_num;
	}

	public void setGoods_num(Integer goods_num) {
		this.goods_num = goods_num;
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

	public Integer getModifyId() {
		return modifyId;
	}

	public void setModifyId(Integer modifyId) {
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
