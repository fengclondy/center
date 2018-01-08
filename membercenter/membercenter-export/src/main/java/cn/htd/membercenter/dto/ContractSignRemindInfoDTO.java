package cn.htd.membercenter.dto;

import java.util.Date;

/**
 * @ClassName ContractSignRemindInfoDTO
 * @Description 合同提醒实体类
 * @author: zhoutong
 * @date: Created in 2017/12/18
 */
public class ContractSignRemindInfoDTO {

    /**
    *  是否需要提醒标识
    **/
    private int isNeedRemind;

    /**
    *  会员店编码
    **/
    private String memberCode;

    /**
    *  创建人id
    **/
    private Long createId;

    /**
    *  创建人名称
    **/
    private String createName;

    /**
    *  创建时间
    **/
    private Date createTime;

    /**
    *  修改人id
    **/
    private Long modifyId;

    /**
    *  修改人名称
    **/
    private String modifyName;

    /**
    *  修改时间
    **/
    private Date modifyTime;

    public int getIsNeedRemind() {
        return isNeedRemind;
    }

    public void setIsNeedRemind(int isNeedRemind) {
        this.isNeedRemind = isNeedRemind;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
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
        this.createName = createName;
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
        this.modifyName = modifyName;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
