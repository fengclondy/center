package cn.htd.goodscenter.domain;
import java.io.Serializable;
import java.util.Date;

/**商品描述(商品详情)实体类
 * Created by GZG on 2016/11/16.
 */

public class ItemDescribe implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1397918453287159038L;

	private Long desId;

    private Long itemId;

    private Long createId;

    private Long modifyId;//更新人ID
    private String modifyName;//更新人名称
    private Date modifyTime;//更新时间

    private String createName;

    private Date createTime;

    private String describeContent;// 商品描述

    public Long getDesId() {
        return desId;
    }

    public void setDesId(Long desId) {
        this.desId = desId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
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

    public String getDescribeContent() {
        return describeContent;
    }

    public void setDescribeContent(String describeContent) {
        this.describeContent = describeContent == null ? null : describeContent.trim();
    }

}
