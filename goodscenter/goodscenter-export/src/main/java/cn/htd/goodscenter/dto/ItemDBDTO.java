package cn.htd.goodscenter.dto;

import java.io.Serializable;

public class ItemDBDTO implements Serializable {
	private static final long serialVersionUID = -5326479214927874317L;
	private Long storeid;// 店铺ID
	private Long userid; // 用户id
	private Long type; // 类型：1 平台 2卖家 3 买家
	private Long mmid; // 统计表统计类型ID 1:待付款，2：待配送，3：退款中 4：待评价 5售后中 6投诉中 7 即将结束店铺活动
						// 8已结束店铺活动 9 待审核宝贝 10 待上架宝贝 11在售宝贝数
	private Long counter; // 数量
	private Long dbtype;

	public Long getStoreid() {
		return storeid;
	}

	public void setStoreid(Long storeid) {
		this.storeid = storeid;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getMmid() {
		return mmid;
	}

	public void setMmid(Long mmid) {
		this.mmid = mmid;
	}

	public Long getCounter() {
		return counter;
	}

	public void setCounter(Long counter) {
		this.counter = counter;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getDbtype() {
		return dbtype;
	}

	public void setDbtype(Long dbtype) {
		this.dbtype = dbtype;
	}
}
