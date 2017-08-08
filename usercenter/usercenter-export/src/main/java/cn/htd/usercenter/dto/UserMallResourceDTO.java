package cn.htd.usercenter.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by thinkpad on 2017/2/6.
 */
public class UserMallResourceDTO implements Serializable, Comparable<UserMallResourceDTO>{

    private static final long serialVersionUID = 1l;
    private Long id;// id
    private Integer type;// 用户类型：1-买家/2-卖家/3-平台
    private String url;// 资源对应的url
    private String resourceName;// 资源名称
    private Long parentId;// 父菜单为-1
    private Integer modularType;// 模块类型 ：1买家中心 2卖家中心
    private List<UserMallResourceDTO> userMallResourceList; // 二级资源List
    private Integer orderid; // 排序id

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getModularType() {
        return modularType;
    }

    public void setModularType(Integer modularType) {
        this.modularType = modularType;
    }

    public List<UserMallResourceDTO> getUserMallResourceList() {
        return userMallResourceList;
    }

    public void setUserMallResourceList(List<UserMallResourceDTO> userMallResourceList) {
        this.userMallResourceList = userMallResourceList;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    @Override
    public int compareTo(UserMallResourceDTO o) {
        // 如果为空往后排
        if (o.getOrderid() == null) {
            return -1;
        }
        return this.orderid.compareTo(o.getOrderid());
    }
}
