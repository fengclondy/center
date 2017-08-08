package cn.htd.membercenter.dto;

import java.util.Date;

public class MemberInvoiceInfo {
    private Long invoiceId;

    private Long memberId;

    private Long channelId;

    private String invoiceNotify;

    private String invoiceCompanyName;

    private String taxManId;

    private String bankName;

    private String bankAccount;

    private String contactPhone;

    private Long invoiceAddressProvince;

    private Long invoiceAddressCity;

    private Long invoiceAddressCounty;

    private Long invoiceAddressTown;

    private String invoiceAddressDetail;

    private Byte deleteFlag;

    private String erpStatus;

    private Date erpDownTime;

    private String erpErrorMsg;

    private Long createId;

    private String createName;

    private Date createTime;

    private Long modifyId;

    private String modifyName;

    private Date modifyTime;

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getInvoiceNotify() {
        return invoiceNotify;
    }

    public void setInvoiceNotify(String invoiceNotify) {
        this.invoiceNotify = invoiceNotify == null ? null : invoiceNotify.trim();
    }

    public String getInvoiceCompanyName() {
        return invoiceCompanyName;
    }

    public void setInvoiceCompanyName(String invoiceCompanyName) {
        this.invoiceCompanyName = invoiceCompanyName == null ? null : invoiceCompanyName.trim();
    }

    public String getTaxManId() {
        return taxManId;
    }

    public void setTaxManId(String taxManId) {
        this.taxManId = taxManId == null ? null : taxManId.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount == null ? null : bankAccount.trim();
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    public Long getInvoiceAddressProvince() {
        return invoiceAddressProvince;
    }

    public void setInvoiceAddressProvince(Long invoiceAddressProvince) {
        this.invoiceAddressProvince = invoiceAddressProvince;
    }

    public Long getInvoiceAddressCity() {
        return invoiceAddressCity;
    }

    public void setInvoiceAddressCity(Long invoiceAddressCity) {
        this.invoiceAddressCity = invoiceAddressCity;
    }

    public Long getInvoiceAddressCounty() {
        return invoiceAddressCounty;
    }

    public void setInvoiceAddressCounty(Long invoiceAddressCounty) {
        this.invoiceAddressCounty = invoiceAddressCounty;
    }

    public Long getInvoiceAddressTown() {
        return invoiceAddressTown;
    }

    public void setInvoiceAddressTown(Long invoiceAddressTown) {
        this.invoiceAddressTown = invoiceAddressTown;
    }

    public String getInvoiceAddressDetail() {
        return invoiceAddressDetail;
    }

    public void setInvoiceAddressDetail(String invoiceAddressDetail) {
        this.invoiceAddressDetail = invoiceAddressDetail == null ? null : invoiceAddressDetail.trim();
    }

    public Byte getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Byte deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getErpStatus() {
        return erpStatus;
    }

    public void setErpStatus(String erpStatus) {
        this.erpStatus = erpStatus == null ? null : erpStatus.trim();
    }

    public Date getErpDownTime() {
        return erpDownTime;
    }

    public void setErpDownTime(Date erpDownTime) {
        this.erpDownTime = erpDownTime;
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