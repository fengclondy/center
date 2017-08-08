package cn.htd.goodscenter.domain.spu;

import java.io.Serializable;
import java.util.Date;

public class ItemSpuDescribe implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5014432580684119862L;

	private Long desId;

    private Long spuId;

    private Byte deleteFlag;

    private Long createId;

    private String createName;

    private Date createTime;

    private String spuDesc;

    public Long getDesId() {
        return desId;
    }

    public void setDesId(Long desId) {
        this.desId = desId;
    }

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public Byte getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Byte deleteFlag) {
        this.deleteFlag = deleteFlag;
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

    public String getSpuDesc() {
        return spuDesc;
    }

    public void setSpuDesc(String spuDesc) {
        this.spuDesc = spuDesc == null ? null : spuDesc.trim();
    }
}