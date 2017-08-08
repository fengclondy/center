package cn.htd.usercenter.dto;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import cn.htd.usercenter.enums.PermissionEnum;

public class MenuDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 名称
    private String name;
    // 类型
    private PermissionEnum type;
    // 页面URL
    private String pageUrl;
    // 子菜单列表
    private List<MenuDTO> subMenus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PermissionEnum getType() {
        return type;
    }

    public void setType(PermissionEnum type) {
        this.type = type;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public List<MenuDTO> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(List<MenuDTO> subMenus) {
        this.subMenus = subMenus;
    }

    public String toString() {
        return new ReflectionToStringBuilder(this).toString();
    }
}
