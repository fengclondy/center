package cn.htd.zeus.tc.dto.response;

import java.io.Serializable;
import java.util.List;

public class JDCreateOrderResDTO implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -110221269469601090L;

	//第三方订单单号
	private String thirdOrder;

	//商品集合
	private List<JDCreateOrderSkuResDTO> sku;

	//收货人
	private String name;

	//省
	private Integer province;

	//市
	private Integer city;

	//区
	private Integer county;

	//镇
	private Integer town;

	//详细地址
	private String address;

	//邮编
	private String zip;

	//座机号
	private String phone;

	//手机号
	private String mobile;

	//邮箱
	private String email;

	//备注（少于100字）
	private String remark;

	//开票方式(1为随货开票，0为订单预借，2为集中开票 )
	private Integer invoiceState;

	//1普通发票2增值税发票
	private Integer invoiceType;

	//4个人，5单位
	private Integer selectedInvoiceTitle;

	//发票抬头
	private String companyName;

	//1:明细，3：电脑配件，19:耗材，22：办公用品
	private Integer invoiceContent;//若增值发票则只能选1 明细

	//1：货到付款，2：邮局付款，4：在线支付（余额支付），5：公司转账，6：银行转账，7：网银钱包， 101：金采支付
	private Integer paymentType;

	//预存款【即在线支付（余额支付）】下单固定1 使用余额
	//非预存款下单固定0 不使用余额
	private Integer isUseBalance;

	//是否预占库存，0是预占库存（需要调用确认订单接口），1是不预占库存
	private Integer submitState;

	//增值票收票人姓名
	private String invoiceName;

	//增值票收票人电话
	private String invoicePhone;

	//增值票收票人所在省(京东地址编码)
	private Integer invoiceProvice;

	//增值票收票人所在市(京东地址编码)
	private Integer invoiceCity;

	//增值票收票人所在区/县(京东地址编码
	private Integer invoiceCounty;

	//增值票收票人所在地址
	private String invoiceAddress;

	//下单价格模式
	//0: 客户端订单价格快照不做验证对比，还是以京东端价格正常下单;
	//1:必需验证客户端订单价格快照，如果快照与京东端价格不一致返回下单失败，需要更新商品价格后，重新下单;
	private Integer doOrderPriceMode;

	//客户端订单价格快照
	private String orderPriceSnap;

	//扩展节点字段（extContent）说明
	private String extContent;

	public String getThirdOrder() {
		return thirdOrder;
	}

	public void setThirdOrder(String thirdOrder) {
		this.thirdOrder = thirdOrder;
	}

	public List<JDCreateOrderSkuResDTO> getSku() {
		return sku;
	}

	public void setSku(List<JDCreateOrderSkuResDTO> sku) {
		this.sku = sku;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public Integer getCounty() {
		return county;
	}

	public void setCounty(Integer county) {
		this.county = county;
	}

	public Integer getTown() {
		return town;
	}

	public void setTown(Integer town) {
		this.town = town;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getInvoiceState() {
		return invoiceState;
	}

	public void setInvoiceState(Integer invoiceState) {
		this.invoiceState = invoiceState;
	}

	public Integer getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(Integer invoiceType) {
		this.invoiceType = invoiceType;
	}

	public Integer getSelectedInvoiceTitle() {
		return selectedInvoiceTitle;
	}

	public void setSelectedInvoiceTitle(Integer selectedInvoiceTitle) {
		this.selectedInvoiceTitle = selectedInvoiceTitle;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getInvoiceContent() {
		return invoiceContent;
	}

	public void setInvoiceContent(Integer invoiceContent) {
		this.invoiceContent = invoiceContent;
	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	public Integer getIsUseBalance() {
		return isUseBalance;
	}

	public void setIsUseBalance(Integer isUseBalance) {
		this.isUseBalance = isUseBalance;
	}

	public Integer getSubmitState() {
		return submitState;
	}

	public void setSubmitState(Integer submitState) {
		this.submitState = submitState;
	}

	public String getInvoiceName() {
		return invoiceName;
	}

	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}

	public String getInvoicePhone() {
		return invoicePhone;
	}

	public void setInvoicePhone(String invoicePhone) {
		this.invoicePhone = invoicePhone;
	}

	public Integer getInvoiceProvice() {
		return invoiceProvice;
	}

	public void setInvoiceProvice(Integer invoiceProvice) {
		this.invoiceProvice = invoiceProvice;
	}

	public Integer getInvoiceCity() {
		return invoiceCity;
	}

	public void setInvoiceCity(Integer invoiceCity) {
		this.invoiceCity = invoiceCity;
	}

	public Integer getInvoiceCounty() {
		return invoiceCounty;
	}

	public void setInvoiceCounty(Integer invoiceCounty) {
		this.invoiceCounty = invoiceCounty;
	}

	public String getInvoiceAddress() {
		return invoiceAddress;
	}

	public void setInvoiceAddress(String invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
	}

	public Integer getDoOrderPriceMode() {
		return doOrderPriceMode;
	}

	public void setDoOrderPriceMode(Integer doOrderPriceMode) {
		this.doOrderPriceMode = doOrderPriceMode;
	}

	public String getOrderPriceSnap() {
		return orderPriceSnap;
	}

	public void setOrderPriceSnap(String orderPriceSnap) {
		this.orderPriceSnap = orderPriceSnap;
	}

	public String getExtContent() {
		return extContent;
	}

	public void setExtContent(String extContent) {
		this.extContent = extContent;
	}

}
