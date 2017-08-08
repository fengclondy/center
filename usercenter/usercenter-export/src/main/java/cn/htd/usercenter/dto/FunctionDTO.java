package cn.htd.usercenter.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class FunctionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 权限项目名称
    private String name;
    // 机能标示
    private String symbol;
    // 展示顺序
    private int displayOrder;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String toString() {
        return new ReflectionToStringBuilder(this).toString();
    }
}
