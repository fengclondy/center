package cn.htd.membercenter.dto;

public class QueryBatchAddItemAuthInDTO {
    // 平台公司名称
    private String companyName;
    // 平台公司编码
    private String companyCode;
    // 是否开通 0：未开通 1：已开通
    private Integer isOpen;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }
}
