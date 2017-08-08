package cn.htd.zeus.tc.biz.dmo;

import java.util.Date;

public class TradeOrderErpDistributionLogDMO {
	
    private Long erpDistributionId;

    private String erpErrorMsg;

    private Long createId;

    private String createName;

    private Date createTime;

    private Long modifyId;

    private String modifyName;

    private Date modifyTime;

	public Long getErpDistributionId() {
		return erpDistributionId;
	}

	public void setErpDistributionId(Long erpDistributionId) {
		this.erpDistributionId = erpDistributionId;
	}

	public String getErpErrorMsg() {
        return erpErrorMsg;
    }

    public void setErpErrorMsg(String erpErrorMsg) {
        this.erpErrorMsg = erpErrorMsg == null ? null : erpErrorMsg.trim();
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
        this.modifyName = modifyName == null ? null : modifyName.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}