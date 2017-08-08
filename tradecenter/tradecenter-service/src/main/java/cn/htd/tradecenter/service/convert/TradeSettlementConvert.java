package cn.htd.tradecenter.service.convert;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import cn.htd.tradecenter.common.constant.SettlementConstants;
import cn.htd.tradecenter.common.enums.SettlementEnum;
import cn.htd.tradecenter.common.enums.SettlementStatusEnum;
import cn.htd.tradecenter.domain.order.TradeOrderSettlement;
import cn.htd.tradecenter.domain.order.TradeOrderSettlementItems;
import cn.htd.tradecenter.dto.TradeOrderSettlementDTO;
import cn.htd.tradecenter.dto.bill.TradeSettlementCompDTO;
import cn.htd.tradecenter.dto.bill.TradeSettlementConstDTO;
import cn.htd.tradecenter.dto.bill.TradeSettlementDTO;
import cn.htd.tradecenter.dto.bill.TradeSettlementDetailDTO;
import cn.htd.tradecenter.dto.bill.TradeSettlementStatusHistoryDTO;
import cn.htd.tradecenter.service.TradeSettlementService;

/**
 * @Purpose 结算转换类
 * @author zf.zhang
 * @since 2017-02-16 12:05
 */
@Service("tradeSettlementConvert")
public class TradeSettlementConvert {
	
	private static final Logger logger = LoggerFactory.getLogger(TradeSettlementConvert.class);
	
	@Resource
	private TradeSettlementService tradeSettlementService;
	
	
	/**
	 * 根据订单信息生成结算历史DTO
	 * @param tradeOrderSettlementDTO
	 * @param settlementNo
	 * @return
	 */
	public TradeSettlementStatusHistoryDTO convert2TradeSetStatusHistoryDTO(TradeOrderSettlementDTO tradeOrderSettlementDTO,String settlementNo){
		
		TradeSettlementStatusHistoryDTO tradeSettlementStatusHistoryDTO = new TradeSettlementStatusHistoryDTO();
		
		tradeSettlementStatusHistoryDTO.setSettlementNo(settlementNo);
		
		// 结算单状态 [10.待财务确认,11.待商家提款,12.商家提款处理中,13.结算已完成,14.结算单失效]
		tradeSettlementStatusHistoryDTO.setStatus(SettlementStatusEnum.SETTLEMENT_STATUS_10.key());
		tradeSettlementStatusHistoryDTO.setStatusText(SettlementStatusEnum.SETTLEMENT_STATUS_10.value());
		
		// 获取服务器当前时间
		Calendar calendar = Calendar.getInstance();
		Date currentTime = calendar.getTime();
		
		//操作类型 1.job调度 2.运营系统管理员
		if(SettlementConstants.OPERATE_TYPE_JOB.equals(tradeOrderSettlementDTO.getOperateType())){//1.job调度
			
			tradeSettlementStatusHistoryDTO.setCreateId(SettlementConstants.SYSTEM_CRON_ID);
			tradeSettlementStatusHistoryDTO.setCreateName(SettlementConstants.SYSTEM_CRON_NAME);
			tradeSettlementStatusHistoryDTO.setCreateTime(currentTime);
			tradeSettlementStatusHistoryDTO.setModifyId(SettlementConstants.SYSTEM_CRON_ID);
			tradeSettlementStatusHistoryDTO.setModifyName(SettlementConstants.SYSTEM_CRON_NAME);
			tradeSettlementStatusHistoryDTO.setModifyTime(currentTime);
			
		}else{//2.运营系统管理员
			
			tradeSettlementStatusHistoryDTO.setCreateId(tradeOrderSettlementDTO.getCreateId());
			tradeSettlementStatusHistoryDTO.setCreateName(tradeOrderSettlementDTO.getCreateName());
			tradeSettlementStatusHistoryDTO.setCreateTime(currentTime);
			tradeSettlementStatusHistoryDTO.setModifyId(tradeOrderSettlementDTO.getModifyId());
			tradeSettlementStatusHistoryDTO.setModifyName(tradeOrderSettlementDTO.getModifyName());
			tradeSettlementStatusHistoryDTO.setModifyTime(currentTime);
		}
		
		return tradeSettlementStatusHistoryDTO;
	}
	
	/**
	 * 根据订单信息生成结算补偿DTO
	 * @param tradeSetSellerInfoDTO 卖家信息
	 * @param settlementNo 结算单号
	 * @return
	 */
	public TradeSettlementCompDTO convert2TradeSettlementCompDTO(TradeOrderSettlementDTO tradeOrderSettlementDTO,String settlementNo, String content){
		
		TradeSettlementCompDTO tradeSettlementCompDTO = new TradeSettlementCompDTO();
		
		tradeSettlementCompDTO.setSettlementNo(settlementNo);
		tradeSettlementCompDTO.setSellerId(tradeOrderSettlementDTO.getSellerId());
		tradeSettlementCompDTO.setSellerCode(tradeOrderSettlementDTO.getSellerCode());
		
		// seller_type 1:是内部，2是外部。
		tradeSettlementCompDTO.setSellerType(tradeOrderSettlementDTO.getSellerType());
		
		tradeSettlementCompDTO.setSellerName(tradeOrderSettlementDTO.getSellerName());
		tradeSettlementCompDTO.setShopId(tradeOrderSettlementDTO.getShopId());
		tradeSettlementCompDTO.setShopName(tradeOrderSettlementDTO.getShopName());
		
		//结算单类型: 10 外部供应商 20 平台公司
		tradeSettlementCompDTO.setProductChannelCode(tradeOrderSettlementDTO.getSettlementTypeCode());
		tradeSettlementCompDTO.setProductChannelName(tradeOrderSettlementDTO.getSettlementTypeName());
		
		//状态:0.失败，1.成功
		tradeSettlementCompDTO.setStatus(SettlementConstants.SETTLEMENTCOMP_STATUS_FAILED);
		tradeSettlementCompDTO.setStatusText(SettlementConstants.SETTLEMENTCOMP_STATUS_TEXT_FAILED);
		
		// 获取服务器当前时间
		Calendar calendar = Calendar.getInstance();
		Date currentTime = calendar.getTime();
		
		//结算单生成时间(job的生成时间)
	    //job生成结算单时间
		tradeSettlementCompDTO.setSettlementTime(currentTime);
		
		//错误日志内容
		tradeSettlementCompDTO.setContent(content);
		
		//操作类型 1.job调度 2.运营系统管理员
		tradeSettlementCompDTO.setCreateId(SettlementConstants.SYSTEM_CRON_ID);
		tradeSettlementCompDTO.setCreateName(SettlementConstants.SYSTEM_CRON_NAME);
		tradeSettlementCompDTO.setCreateTime(currentTime);
		tradeSettlementCompDTO.setModifyId(SettlementConstants.SYSTEM_CRON_ID);
		tradeSettlementCompDTO.setModifyName(SettlementConstants.SYSTEM_CRON_NAME);
		tradeSettlementCompDTO.setModifyTime(currentTime);
			
		return tradeSettlementCompDTO;
	}
	
	
	/**
	 * 根据订单信息生成结算DTO
	 * @param tradeSetSellerInfoDTO 卖家信息
	 * @param totalCommissionAmount 佣金总金额
	 * @param totalSettlementAmount 结算总金额
	 * @param settlementNo 结算单号
	 * @return
	 */
	public TradeSettlementDTO convert2TradeSettlementDTO(TradeOrderSettlementDTO tradeOrderSettlementDTO,BigDecimal totalCommissionAmount,
			BigDecimal totalSettlementAmount,String settlementNo){
		
		TradeSettlementDTO tradeSettlementDTO = new TradeSettlementDTO();
		
		tradeSettlementDTO.setSettlementNo(settlementNo);
		tradeSettlementDTO.setSellerId(tradeOrderSettlementDTO.getSellerId());
		tradeSettlementDTO.setSellerCode(tradeOrderSettlementDTO.getSellerCode());
		
		// seller_type 1:是内部，2是外部。
		tradeSettlementDTO.setSellerType(tradeOrderSettlementDTO.getSellerType());
		
		tradeSettlementDTO.setSellerName(tradeOrderSettlementDTO.getSellerName());
		tradeSettlementDTO.setShopId(tradeOrderSettlementDTO.getShopId());
		tradeSettlementDTO.setShopName(tradeOrderSettlementDTO.getShopName());
		
		//结算单类型: 10 外部供应商 20 平台公司
		tradeSettlementDTO.setProductChannelCode(tradeOrderSettlementDTO.getSettlementTypeCode());
		tradeSettlementDTO.setProductChannelName(tradeOrderSettlementDTO.getSettlementTypeName());
		
		//佣金总金额
		tradeSettlementDTO.setTotalCommissionAmount(totalCommissionAmount);
		//结算总金额
		tradeSettlementDTO.setTotalSettlementAmount(totalSettlementAmount);
		
		 // 结算单状态 [10.待财务确认,11.待商家提款,12.商家提款处理中,13.结算已完成,14.结算单失效]
		tradeSettlementDTO.setStatus(SettlementStatusEnum.SETTLEMENT_STATUS_10.key());
		
		// 获取服务器当前时间
		Calendar calendar = Calendar.getInstance();
		Date currentTime = calendar.getTime();
		
		//结算单生成时间(job的生成时间)
		if(null != tradeOrderSettlementDTO.getSettlementTime()){//重新计算生成结算单时间
			tradeSettlementDTO.setSettlementTime(tradeOrderSettlementDTO.getSettlementTime());
		}else{//job生成结算单时间
			tradeSettlementDTO.setSettlementTime(currentTime);
		}
		
		//操作类型 1.job调度 2.运营系统管理员
		if(SettlementConstants.OPERATE_TYPE_JOB.equals(tradeOrderSettlementDTO.getOperateType())){//1.job调度
			
			tradeSettlementDTO.setCreateId(SettlementConstants.SYSTEM_CRON_ID);
			tradeSettlementDTO.setCreateName(SettlementConstants.SYSTEM_CRON_NAME);
			tradeSettlementDTO.setCreateTime(currentTime);
			tradeSettlementDTO.setModifyId(SettlementConstants.SYSTEM_CRON_ID);
			tradeSettlementDTO.setModifyName(SettlementConstants.SYSTEM_CRON_NAME);
			tradeSettlementDTO.setModifyTime(currentTime);
			
		}else{//2.运营系统管理员
			
			tradeSettlementDTO.setCreateId(tradeOrderSettlementDTO.getCreateId());
			tradeSettlementDTO.setCreateName(tradeOrderSettlementDTO.getCreateName());
			tradeSettlementDTO.setCreateTime(currentTime);
			tradeSettlementDTO.setModifyId(tradeOrderSettlementDTO.getModifyId());
			tradeSettlementDTO.setModifyName(tradeOrderSettlementDTO.getModifyName());
			tradeSettlementDTO.setModifyTime(currentTime);
		}
		
		return tradeSettlementDTO;
	}
	
	
	/**
	 * 根据订单信息生成结算详情DTO
	 * @param tradeOrderSettlementItems
	 * @param tradeOrderSettlement
	 * @param productChannelCode
	 * @param productChannelName
	 * @param settlementNo
	 * @return
	 */
	public TradeSettlementDetailDTO convert2TradeSettlementDetailDTO(TradeOrderSettlementItems tradeOrderSettlementItems,TradeOrderSettlement tradeOrderSettlement,
			TradeOrderSettlementDTO tradeOrderSettlementDTO,String settlementNo){
		
		TradeSettlementDetailDTO tradeSettlementDetailDTO = new TradeSettlementDetailDTO();
		tradeSettlementDetailDTO.setSettlementNo(settlementNo);
		tradeSettlementDetailDTO.setOrderNo(tradeOrderSettlement.getOrderNo());
		tradeSettlementDetailDTO.setOrderItemNo(tradeOrderSettlementItems.getOrderItemNo());
		
		tradeSettlementDetailDTO.setBuyerId(tradeOrderSettlement.getBuyerId());
		tradeSettlementDetailDTO.setBuyerCode(tradeOrderSettlement.getBuyerCode());
		tradeSettlementDetailDTO.setBuyerName(tradeOrderSettlement.getBuyerName());
		tradeSettlementDetailDTO.setSellerId(tradeOrderSettlement.getSellerId());
		tradeSettlementDetailDTO.setSellerCode(tradeOrderSettlement.getSellerCode());
		tradeSettlementDetailDTO.setSellerType(tradeOrderSettlement.getSellerType());
		tradeSettlementDetailDTO.setSellerName(tradeOrderSettlement.getSellerName());
		tradeSettlementDetailDTO.setShopId(tradeOrderSettlement.getShopId());
		tradeSettlementDetailDTO.setShopName(tradeOrderSettlement.getShopName());
		
		//支付方式：1：余额帐支付，2：平台账户支付，3：在线支付
		tradeSettlementDetailDTO.setPayType(tradeOrderSettlement.getPayType());
		
		
		//渠道编码: 10 内部供应商 20 外部供应商 3010 京东商品＋
		String channelCode = tradeOrderSettlementItems.getChannelCode();
		//渠道名称
		String channelName = SettlementEnum.ProductChannel.getValue(channelCode);
		//渠道编码: 10 内部供应商 20 外部供应商 3010 京东商品＋
		tradeSettlementDetailDTO.setChannelCode(channelCode);
		//渠道名称
		tradeSettlementDetailDTO.setChannelName(channelName);
		
		// 商品编码
		tradeSettlementDetailDTO.setItemCode(tradeOrderSettlementItems.getItemCode());
		// 商品名称
		tradeSettlementDetailDTO.setGoodsName(tradeOrderSettlementItems.getGoodsName());
		// 商品SKU编码
		tradeSettlementDetailDTO.setSkuCode(tradeOrderSettlementItems.getSkuCode());
		 // 商品SKUERP编码，内部商品时保存ERP编码，外接商品时保存外接商品SKUID
		tradeSettlementDetailDTO.setSkuErpCode(tradeOrderSettlementItems.getSkuErpCode());
		// 商品SKUEAN码
		tradeSettlementDetailDTO.setSkuEanCode(tradeOrderSettlementItems.getSkuEanCode());
		
		tradeSettlementDetailDTO.setFirstCategoryId(tradeOrderSettlementItems.getFirstCategoryId());
		tradeSettlementDetailDTO.setFirstCategoryName(tradeOrderSettlementItems.getFirstCategoryName());
		tradeSettlementDetailDTO.setSecondCategoryId(tradeOrderSettlementItems.getSecondCategoryId());
		tradeSettlementDetailDTO.setSecondCategoryName(tradeOrderSettlementItems.getSecondCategoryName());
		tradeSettlementDetailDTO.setThirdCategoryId(tradeOrderSettlementItems.getThirdCategoryId());
		tradeSettlementDetailDTO.setThirdCategoryName(tradeOrderSettlementItems.getThirdCategoryName());
		tradeSettlementDetailDTO.setCategoryIdList(tradeOrderSettlementItems.getCategoryIdList());
		tradeSettlementDetailDTO.setCategoryNameList(tradeOrderSettlementItems.getCategoryNameList());
		
		tradeSettlementDetailDTO.setBrandId(tradeOrderSettlementItems.getBrandId());
		tradeSettlementDetailDTO.setBrandName(tradeOrderSettlementItems.getBrandName());
		
		//商品数量
		tradeSettlementDetailDTO.setGoodsCount(tradeOrderSettlementItems.getGoodsCount());
		//订单行总价
		tradeSettlementDetailDTO.setOrderItemTotalAmount(tradeOrderSettlementItems.getOrderItemTotalAmount());
		//订单行实付金额
		tradeSettlementDetailDTO.setOrderItemPayAmount(tradeOrderSettlementItems.getOrderItemPayAmount());
		
		
		// [start]------------------  商品+使用 ------------------------
		// 外部供货价 商品+
		if(null != tradeOrderSettlementItems.getCostPrice()){
			tradeSettlementDetailDTO.setCostPrice(tradeOrderSettlementItems.getCostPrice());
		}
		//是否是VIP会员（0.不是，1.是）
		if(tradeOrderSettlement.getIsVipMember() == 1){//是VIP会员
	        //	VIP价浮动比例  商品+
			tradeSettlementDetailDTO.setVipPriceFloatingRatio(tradeOrderSettlementItems.getPriceFloatingRatio());
		}else{
			// 价格浮动比例 商品+
			tradeSettlementDetailDTO.setPriceFloatingRatio(tradeOrderSettlementItems.getPriceFloatingRatio());
		}
		
//		tradeSettlementDetailDTO.setPriceFloatingRatio(tradeOrderSettlementItems.getPriceFloatingRatio());
		
		// [end]------------------  商品+使用 ------------------------
		
		// 佣金比例
		tradeSettlementDetailDTO.setCommissionRate(tradeOrderSettlementItems.getCommissionRatio());
		// 佣金金额
		tradeSettlementDetailDTO.setCommissionAmount(tradeOrderSettlementItems.getCommissionAmount());
		
        //原始
		//列表取自订单中心的数据
		//1、会员名称：小b的公司名称
		//2、结算金额=订单行金额-佣金金额
		//3、本期结算总额：所有结算金额相加
		//4、本期佣金总额：所有佣金金额相加

//最新		商品+规则
//		1、非VIP情况下分佣规则
//		1）总部拿到的钱=外部供货价+总部佣金金额（非VIP）。即600+6.24=606.24
//		2）平台公司拿到的钱=商城销售价-外部供货价-总部佣金金额（非VIP）。即624-600-6.24=17.76
//		2、VIP情况下分佣规则
//		1）总部拿到的钱=外部供货价+总部佣金金额（VIP）。即600+6.18=606.18
//		2）	平台公司拿到的钱=VIP会员价-外部供货价-总部佣金金额（VIP）。即618-600-6.18=11.82
		
		//外部供应商 
		//结算金额=实付金额-佣金金额
		
		 // 外部供应商
		 if(SettlementEnum.ProductChannel.PRODUCT_CHANNEL_20.key().equals(channelCode)){
			//结算金额=实付金额-佣金金额
			 BigDecimal settlementAmount = tradeOrderSettlementItems.getOrderItemPayAmount().subtract(tradeOrderSettlementItems.getCommissionAmount());
			 tradeSettlementDetailDTO.setSettlementAmount(settlementAmount); 
		 }else{//商品+等等其他外接商品渠道
				//结算金额 = 平台公司拿到的钱
				BigDecimal totalCostPrice = tradeOrderSettlementItems.getCostPrice().multiply(new BigDecimal(tradeOrderSettlementItems.getGoodsCount()));
				BigDecimal settlementAmount = tradeOrderSettlementItems.getOrderItemPayAmount().subtract(totalCostPrice).subtract(tradeOrderSettlementItems.getCommissionAmount());
				tradeSettlementDetailDTO.setSettlementAmount(settlementAmount); 
		 }
		
		// 获取服务器当前时间
		Calendar calendar = Calendar.getInstance();
		Date currentTime = calendar.getTime();
		
		//操作类型 1.job调度 2.运营系统管理员
		if(SettlementConstants.OPERATE_TYPE_JOB.equals(tradeOrderSettlementDTO.getOperateType())){//1.job调度
			
			tradeSettlementDetailDTO.setCreateId(SettlementConstants.SYSTEM_CRON_ID);
			tradeSettlementDetailDTO.setCreateName(SettlementConstants.SYSTEM_CRON_NAME);
			tradeSettlementDetailDTO.setCreateTime(currentTime);
			tradeSettlementDetailDTO.setModifyId(SettlementConstants.SYSTEM_CRON_ID);
			tradeSettlementDetailDTO.setModifyName(SettlementConstants.SYSTEM_CRON_NAME);
			tradeSettlementDetailDTO.setModifyTime(currentTime);
			
		}else{//2.运营系统管理员
			
			tradeSettlementDetailDTO.setCreateId(tradeOrderSettlementDTO.getCreateId());
			tradeSettlementDetailDTO.setCreateName(tradeOrderSettlementDTO.getCreateName());
			tradeSettlementDetailDTO.setCreateTime(currentTime);
			tradeSettlementDetailDTO.setModifyId(tradeOrderSettlementDTO.getModifyId());
			tradeSettlementDetailDTO.setModifyName(tradeOrderSettlementDTO.getModifyName());
			tradeSettlementDetailDTO.setModifyTime(currentTime);
		}
      
		return tradeSettlementDetailDTO;
	}
	
	/**
	 * 生成基础TradeOrderSettlementDTO
	 * @return
	 */
	public TradeOrderSettlementDTO  generateBaseTradeOrderSettlementDTO(){
		
	    Calendar calendar = Calendar.getInstance();
	    Date currentTime = calendar.getTime();
		
		// [start]-------------startTime endTime 的计算------------
		// 当前时间
		
		Calendar calendarLastMonth = Calendar.getInstance();
		calendarLastMonth.setTime(currentTime);
		calendarLastMonth.add(Calendar.MONTH, -1);
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM");
		
		String calendarLastMonthStr = sdf.format(calendarLastMonth.getTime());
		String calendarCurrentMonthStr = sdf.format(currentTime);
		
			// 开始时间
		String startTime = calendarLastMonthStr + "-01 00:00:00";
			// 结束时间
		String endTime = calendarCurrentMonthStr + "-01 00:00:00";
		// [end]-------------startTime endTime 的计算------------
		
		
		TradeOrderSettlementDTO tradeOrderSettlementDTO = new TradeOrderSettlementDTO();
		
		tradeOrderSettlementDTO.setStartTime(startTime);
		tradeOrderSettlementDTO.setEndTime(endTime);
		
	    // 多个订单状态 [61,62] 61:买家收货,62:到期自动收货
	     String[] orderStatusArray = new String[]{SettlementConstants.ORDER_STATUS_61,SettlementConstants.ORDER_STATUS_62};
	     tradeOrderSettlementDTO.setOrderStatusArray(orderStatusArray);
	     
	     //已支付取消订单状态 [40,50]
	     String[] orderStatusCancelArray = new String[]{SettlementConstants.ORDER_STATUS_40,SettlementConstants.ORDER_STATUS_50};
	     tradeOrderSettlementDTO.setOrderStatusCancelArray(orderStatusCancelArray);
	     //设置订单取消
	     tradeOrderSettlementDTO.setIsCancelOrder(SettlementConstants.IS_CANCEL_ORDER_1);
	     
	     
	     //是否已结算 0：未结算，1：已结算，2：结算处理中
	     tradeOrderSettlementDTO.setIsSettled(SettlementConstants.IS_SETTLED_0);
	     
	     // 出结算单时间间隔【默认为7天】
	     tradeOrderSettlementDTO.setTimeInterval(SettlementConstants.TIME_INTERVAL);
	     
	     tradeOrderSettlementDTO.setSortName(SettlementConstants.SORT_NAME_MODIFYTIME);
	     tradeOrderSettlementDTO.setSortDir(SettlementConstants.SORT_DIR_ASC);
	     
		 List<TradeSettlementConstDTO> tradeSettlementConstList = tradeSettlementService.getSetConsByType(SettlementConstants.CONST_TYPE_USE_PRODUCT_CHANNEL);
			if(null == tradeSettlementConstList || tradeSettlementConstList.size() < 1){
				logger.info("提示：请配置要统计的订单类型！！！");
				return null;
			}

			String[] useProductChannelCodeArray = new String[tradeSettlementConstList.size()];
			for(int i=0;i<tradeSettlementConstList.size();i++){
				useProductChannelCodeArray[i] = tradeSettlementConstList.get(i).getConstKey();
			}
			tradeOrderSettlementDTO.setUseProductChannelCodeArray(useProductChannelCodeArray);
	     
	     return tradeOrderSettlementDTO;
	}
	

}
