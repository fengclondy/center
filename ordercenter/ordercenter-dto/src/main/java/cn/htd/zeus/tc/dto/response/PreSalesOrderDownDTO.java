package cn.htd.zeus.tc.dto.response;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class PreSalesOrderDownDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3749216321246208781L;
	  /**
     *中台供应商编号
     */
    private String supplierCode;

    /**
     *商城订单号
     */
    private String orderNo;

    /**
     *厂家名称
     */
   // private String manufacturer ;

    /**
     *客户名称
     */
    private String customerName;

    /**
     *支付方式
     */
    private String paymentMethod;

    /**
     *销售方式代码
     */
    private String saleType;

    /**
     *服务区域
     */
    private String serviceArea;

    /**
     *送货地址
     */
    private String recieverAddress;

    /**
     *送货电话
     */
    private String recieverPhone;

    /**
     *送货联系人
     */
    private String recieverName;

    /**
     *送货备注
     */
    private String deliveryNote;
    
    /*
     * 主订单号
     */
    private String masterOrderNo;

    /*
     * 订单类型  0-商品+订单；1-金牌套餐订单
     */
    private Integer sourceId;
    
    /**
     * 一级节点()[{"productName":"商品名称","price":"商品单价","num":"商品数量","payment":"商品金额","costPrice":"进货单价"}]
     */
    private List<Map<String,Object>> productDetail;

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

 /*   public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }*/

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getSaleType() {
        return saleType;
    }

    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }

    public String getServiceArea() {
        return serviceArea;
    }

    public void setServiceArea(String serviceArea) {
        this.serviceArea = serviceArea;
    }

    public String getRecieverAddress() {
        return recieverAddress;
    }

    public void setRecieverAddress(String recieverAddress) {
        this.recieverAddress = recieverAddress;
    }

    public String getRecieverPhone() {
        return recieverPhone;
    }

    public void setRecieverPhone(String recieverPhone) {
        this.recieverPhone = recieverPhone;
    }

    public String getRecieverName() {
        return recieverName;
    }

    public void setRecieverName(String recieverName) {
        this.recieverName = recieverName;
    }

    public String getDeliveryNote() {
        return deliveryNote;
    }

    public void setDeliveryNote(String deliveryNote) {
        this.deliveryNote = deliveryNote;
    }

    public List<Map<String, Object>> getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(List<Map<String, Object>> productDetail) {
        this.productDetail = productDetail;
    }

	public String getMasterOrderNo() {
		return masterOrderNo;
	}

	public void setMasterOrderNo(String masterOrderNo) {
		this.masterOrderNo = masterOrderNo;
	}

	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
    
}
