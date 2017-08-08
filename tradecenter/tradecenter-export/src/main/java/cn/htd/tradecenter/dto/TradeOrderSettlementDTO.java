package cn.htd.tradecenter.dto;

import java.util.Date;

import cn.htd.tradecenter.dto.pagination.Pagination;

/**
 * 
 * @author zhangzhifeng
 *
 */
public class TradeOrderSettlementDTO  extends Pagination{
	
	//卖家ID
	private Long sellerId ;
	//卖家编号
	private String sellerCode ;
	//卖家类型 1:是内部，2是外部。
	private String sellerType ;
	//卖家名称
	private String sellerName ;
	//店铺ID
	private Long shopId ;
	//店铺名称
	private String shopName ;
	//渠道编码: 10 内部供应商 20 外部供应商 3010 京东商品＋
	private String productChannelCode ;
	//渠道名称
	private String productChannelName ;
	
	//结算单生成时间(job的生成时间)
	private Date settlementTime;
	
	//操作类型 1.job调度 2.运营系统管理员
	private String operateType ;
	
	//结算单类型: 10 外部供应商 20 平台公司
	private String settlementTypeCode ;
	//渠道名称
	private String settlementTypeName ;
	//使用渠道编码
	private String useProductChannelCode ;
	//渠道名称
	private String useProductChannelName ;
	
	//使用渠道编码  20 外部供应商 3010 京东商品＋
	private String[] useProductChannelCodeArray ;
	
	// 开始时间
	private String startTime ;
	// 结束时间
	private String endTime ;
	
    //订单状态 10:待审核,20:待支付,21:审核通过待支付,31:已支付待拆单，32：已支付已拆单待下行ERP,40:待发货,50:已发货,61:买家收货,62:到期自动收货
    // 多个订单状态 [61,62]
    private String[] orderStatusArray;
    
    //已支付取消订单状态 [40,50]
    private String[] orderStatusCancelArray;
    //是否是取消订单 0:未取消，1：已取消
    private String isCancelOrder;
    
    //是否已结算 0：未结算，1：已结算，2：结算处理中
    private Integer isSettled;
    
	// 出结算单时间间隔【默认为7天】
	private String timeInterval ;
    
    // 排序字段名称
    private String sortName;
    // 排序方式[ASC(升序)  DESC(降序)]
    private String sortDir;
    
    
	private Long createId;
	private String createName;
	private Date createTime;
	private Long modifyId;
	private String modifyName;
	private Date modifyTime;
	
    
	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	public String getSellerCode() {
		return sellerCode;
	}
	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}
	public String getSellerType() {
		return sellerType;
	}
	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getProductChannelCode() {
		return productChannelCode;
	}
	public void setProductChannelCode(String productChannelCode) {
		this.productChannelCode = productChannelCode;
	}
	public String getProductChannelName() {
		return productChannelName;
	}
	public void setProductChannelName(String productChannelName) {
		this.productChannelName = productChannelName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String[] getOrderStatusArray() {
		return orderStatusArray;
	}
	public void setOrderStatusArray(String[] orderStatusArray) {
		this.orderStatusArray = orderStatusArray;
	}
	public String getSortName() {
		return sortName;
	}
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}
	public String getSortDir() {
		return sortDir;
	}
	public void setSortDir(String sortDir) {
		this.sortDir = sortDir;
	}
	public String getTimeInterval() {
		return timeInterval;
	}
	public void setTimeInterval(String timeInterval) {
		this.timeInterval = timeInterval;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public Date getSettlementTime() {
		return settlementTime;
	}
	public void setSettlementTime(Date settlementTime) {
		this.settlementTime = settlementTime;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
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
	public String getSettlementTypeCode() {
		return settlementTypeCode;
	}
	public void setSettlementTypeCode(String settlementTypeCode) {
		this.settlementTypeCode = settlementTypeCode;
	}
	public String getSettlementTypeName() {
		return settlementTypeName;
	}
	public void setSettlementTypeName(String settlementTypeName) {
		this.settlementTypeName = settlementTypeName;
	}
	public String getUseProductChannelCode() {
		return useProductChannelCode;
	}
	public void setUseProductChannelCode(String useProductChannelCode) {
		this.useProductChannelCode = useProductChannelCode;
	}
	public String getUseProductChannelName() {
		return useProductChannelName;
	}
	public void setUseProductChannelName(String useProductChannelName) {
		this.useProductChannelName = useProductChannelName;
	}
	public String[] getUseProductChannelCodeArray() {
		return useProductChannelCodeArray;
	}
	public void setUseProductChannelCodeArray(String[] useProductChannelCodeArray) {
		this.useProductChannelCodeArray = useProductChannelCodeArray;
	}
	public String[] getOrderStatusCancelArray() {
		return orderStatusCancelArray;
	}
	public void setOrderStatusCancelArray(String[] orderStatusCancelArray) {
		this.orderStatusCancelArray = orderStatusCancelArray;
	}
	public String getIsCancelOrder() {
		return isCancelOrder;
	}
	public void setIsCancelOrder(String isCancelOrder) {
		this.isCancelOrder = isCancelOrder;
	}
	public Integer getIsSettled() {
		return isSettled;
	}
	public void setIsSettled(Integer isSettled) {
		this.isSettled = isSettled;
	}

    

}
