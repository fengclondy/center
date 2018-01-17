package cn.htd.tradecenter.service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.common.utils.CollectionUtils;

import cn.htd.common.ExecuteResult;
import cn.htd.tradecenter.common.constant.SettlementConstants;
import cn.htd.tradecenter.common.enums.SettlementEnum;
import cn.htd.tradecenter.common.enums.SettlementStatusEnum;
import cn.htd.tradecenter.common.utils.PaySDK;
import cn.htd.tradecenter.common.utils.SettlementUtils;
import cn.htd.tradecenter.dao.TradeOrderSettlementDAO;
import cn.htd.tradecenter.dao.TradeSettlementDAO;
import cn.htd.tradecenter.dao.TradeSettlementDetailDAO;
import cn.htd.tradecenter.dao.TradeSettlementWithdrawDAO;
import cn.htd.tradecenter.domain.UnSettlementCount;
import cn.htd.tradecenter.domain.order.TradeSettlementComp;
import cn.htd.tradecenter.dto.TradeOrderSettlementDTO;
import cn.htd.tradecenter.dto.bill.TradeSetSellerInfoDTO;
import cn.htd.tradecenter.dto.bill.TradeSettlementConstDTO;
import cn.htd.tradecenter.dto.bill.TradeSettlementDTO;
import cn.htd.tradecenter.dto.bill.TradeSettlementDetailDTO;
import cn.htd.tradecenter.dto.bill.TradeSettlementWithdrawDTO;
import cn.htd.tradecenter.service.convert.TradeSettlementConvert;
import cn.htd.tradecenter.service.impl.TradeSettlementServiceImpl;
import cn.htd.usercenter.dto.LoginResDTO;


/**
 * @Purpose 结算功能模块单元测试
 * @author zhangzhifeng
 * @since 2017-02-10 18:30
 *
 */
public class TradeSettlementServiceImplTest {

	private static final Logger logger = LoggerFactory.getLogger(TradeSettlementServiceImplTest.class);

	
	ApplicationContext ctx;
	private TradeSettlementService tradeSettlementService;
//	private MemberBaseInfoService memberBaseInfoService;


	private TradeSettlementDetailDAO tradeSettlementDetailDAO;

	private TradeSettlementDAO tradeSettlementDAO;

	// 订单DAO
	private TradeOrderSettlementDAO tradeOrderSettlementDAO;

	// 结算单工具类
	private SettlementUtils settlementUtils;
	
	private TradeSettlementConvert tradeSettlementConvert;

	private TradeSettlementWithdrawDAO tradeSettlementWithdrawDAO;
	
	private PaySDK paySDK;
	
	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("test.xml");
		tradeSettlementService = (TradeSettlementService) ctx.getBean("tradeSettlementService");
//		memberBaseInfoService = (MemberBaseInfoService) ctx.getBean("memberBaseInfoService");

		tradeSettlementDetailDAO = (TradeSettlementDetailDAO) ctx.getBean("tradeSettlementDetailDAO");
		tradeSettlementDAO = (TradeSettlementDAO) ctx.getBean("tradeSettlementDAO");
		tradeOrderSettlementDAO = (TradeOrderSettlementDAO) ctx.getBean("tradeOrderSettlementDAO");

		settlementUtils = (SettlementUtils) ctx.getBean("settlementUtils");
		
		tradeSettlementConvert = (TradeSettlementConvert) ctx.getBean("tradeSettlementConvert");
		
		tradeSettlementWithdrawDAO = (TradeSettlementWithdrawDAO)ctx.getBean("tradeSettlementWithdrawDAO");
		
		paySDK = (PaySDK)ctx.getBean("paySDK");
	}


    /**
     * 根据商家类型查询供应商单元测试
     */
	@Test
	public void selectSellerTypeListTest() {

		// 通过sellerType(商家类型 1:内部供应商，2.外部供应商)  查询供应商信息
//		String sellerType = "2";
//		ExecuteResult<List<SellerTypeInfoDTO>> result = memberBaseInfoService.selectSellerTypeList(sellerType);
//		List<SellerTypeInfoDTO> sellerTypeInfoList = result.getResult();
//		System.out.println("======>sellerTypeInfoList:" + sellerTypeInfoList);

	}


    /**
     * 结算单详情添加单元测试
     */
	@Test
	public void addTradeSettlementDetail() {

		Calendar calendar = Calendar.getInstance();
		Date currentTime = calendar.getTime();

		TradeSettlementDetailDTO tradeSettlementDetailDTO = new TradeSettlementDetailDTO();
		tradeSettlementDetailDTO.setId(System.currentTimeMillis());
		tradeSettlementDetailDTO.setSettlementNo("0000001");
		tradeSettlementDetailDTO.setOrderNo("10001");
		tradeSettlementDetailDTO.setOrderItemNo("1000101");
		tradeSettlementDetailDTO.setSellerId(10000L);
		tradeSettlementDetailDTO.setShopId(10001L);

		tradeSettlementDetailDTO.setCreateId(10000L);
		tradeSettlementDetailDTO.setCreateName("测试");
		tradeSettlementDetailDTO.setCreateTime(currentTime);
		tradeSettlementDetailDTO.setModifyId(10000L);
		tradeSettlementDetailDTO.setModifyName("测试");
		tradeSettlementDetailDTO.setModifyTime(currentTime);


		tradeSettlementDetailDAO.addTradeSettlementDetail(tradeSettlementDetailDTO);


	}


    /**
     * 结算单添加单元测试
     */
	@Test
	public void addTradeSettlement() {

		Calendar calendar = Calendar.getInstance();
		Date currentTime = calendar.getTime();
		TradeSettlementDTO tradeSettlementDTO = new TradeSettlementDTO();
		tradeSettlementDTO.setSettlementNo("00001");
		tradeSettlementDTO.setSellerId(100000L);
		tradeSettlementDTO.setSellerCode("100000");
		tradeSettlementDTO.setSellerType("1");
		tradeSettlementDTO.setSellerName("xxxxx");
		tradeSettlementDTO.setShopId(2000L);
		tradeSettlementDTO.setShopName("aaa");
		tradeSettlementDTO.setProductChannelCode("");
		tradeSettlementDTO.setProductChannelName("");
		tradeSettlementDTO.setTotalCommissionAmount(new BigDecimal("12.12"));
		tradeSettlementDTO.setTotalSettlementAmount(new BigDecimal("12.12"));
		tradeSettlementDTO.setStatus("1");
		tradeSettlementDTO.setSettlementTime(currentTime);
		tradeSettlementDTO.setCreateId(10000L);
		tradeSettlementDTO.setCreateName("测试");
		tradeSettlementDTO.setCreateTime(currentTime);
		tradeSettlementDTO.setModifyId(10000L);
		tradeSettlementDTO.setModifyName("测试");
		tradeSettlementDTO.setModifyTime(currentTime);

		tradeSettlementDAO.addTradeSettlement(tradeSettlementDTO);

	}


    /**
     * 根据结算单号查询结算单单元测试
     */
	@Test
	public void getTradeSettlementBySettlementNo() {

		String settlementNo = "1702000000000037";

		TradeSettlementDTO tradeSettlement = tradeSettlementDAO.getTradeSettlementBySettlementNo(settlementNo);
		System.out.println(tradeSettlement);

	}

    /**
     * 根据条件更新结算单状态单元测试
     */
	@Test
	public void updateTradeSetStatusByParams() {

		Calendar calendar = Calendar.getInstance();
		Date currentTime = calendar.getTime();
		TradeSettlementDTO tradeSettlementDTO = new TradeSettlementDTO();
		tradeSettlementDTO.setSettlementNo("00001");
		tradeSettlementDTO.setStatus("2");
		tradeSettlementDTO.setModifyId(10000L);
		tradeSettlementDTO.setModifyName("测试");
		tradeSettlementDTO.setModifyTime(currentTime);

		tradeSettlementDAO.updateTradeSetStatusByParams(tradeSettlementDTO);

	}

    /**
     * cronJob单元测试
     */
	@Test
	public void settlementCron() {

//		TradeOrderSettlementDTO tradeOrderSettlementDTO = new TradeOrderSettlementDTO();
//
//		tradeOrderSettlementDTO.setStartTime("2017-02-01 00:00:00");
//		tradeOrderSettlementDTO.setEndTime("2017-03-01 00:00:00");
//
//	    // 多个订单状态 [61,62] 61:买家收货,62:到期自动收货
//	     String[] orderStatusArray = new String[]{SettlementConstants.ORDER_STATUS_61,SettlementConstants.ORDER_STATUS_62};
//	     tradeOrderSettlementDTO.setOrderStatusArray(orderStatusArray);
//	     
//	     //已支付取消订单状态 [40,50]
//	     String[] orderStatusCancelArray = new String[]{SettlementConstants.ORDER_STATUS_40,SettlementConstants.ORDER_STATUS_50};
//	     tradeOrderSettlementDTO.setOrderStatusCancelArray(orderStatusCancelArray);
//	     //设置订单取消
//	     tradeOrderSettlementDTO.setIsCancelOrder(SettlementConstants.IS_CANCEL_ORDER_1);
//
//	     // 出结算单时间间隔【默认为7天】
//	     tradeOrderSettlementDTO.setTimeInterval(SettlementConstants.TIME_INTERVAL);
//
//	     tradeOrderSettlementDTO.setSortName(SettlementConstants.SORT_NAME_MODIFYTIME);
//	     tradeOrderSettlementDTO.setSortDir(SettlementConstants.SORT_DIR_ASC);
//	     
//		List<TradeSettlementConstDTO> tradeSettlementConstList = tradeSettlementService.getSetConsByType(SettlementConstants.CONST_TYPE_USE_PRODUCT_CHANNEL);
//		if(null == tradeSettlementConstList || tradeSettlementConstList.size() < 1){
//			System.out.println("提示：请配置要统计的订单类型！！！");
//			return;
//		}
//
//		String[] useProductChannelCodeArray = new String[tradeSettlementConstList.size()];
//		for(int i=0;i<tradeSettlementConstList.size();i++){
//			useProductChannelCodeArray[i] = tradeSettlementConstList.get(i).getConstKey();
//		}
//		tradeOrderSettlementDTO.setUseProductChannelCodeArray(useProductChannelCodeArray);
		
		
		TradeOrderSettlementDTO tradeOrderSettlementDTO = tradeSettlementConvert.generateBaseTradeOrderSettlementDTO();
		tradeOrderSettlementDTO.setCurrentPage(1);
		tradeOrderSettlementDTO.setPageSize(1000);
		
		
		 List<TradeSetSellerInfoDTO> tradeSetSellerInfoList = tradeOrderSettlementDAO.getTradeSetSellerInfos(tradeOrderSettlementDTO);
		 if(null == tradeSetSellerInfoList || tradeSetSellerInfoList.size() < 1){
			System.out.println("查询符合结算规则的订单里的所有供应商为空======》tradeSetSellerInfoList:" + tradeSetSellerInfoList);
			return;
		 }

		 if(null != tradeSetSellerInfoList && tradeSetSellerInfoList.size() > 0){
			 for(TradeSetSellerInfoDTO tradeSetSellerInfo : tradeSetSellerInfoList){

				 tradeOrderSettlementDTO.setSellerId(tradeSetSellerInfo.getSellerId());
				 tradeOrderSettlementDTO.setSellerCode(tradeSetSellerInfo.getSellerCode());
				 tradeOrderSettlementDTO.setSellerName(tradeSetSellerInfo.getSellerName());
//				 tradeOrderSettlementDTO.setSellerType(tradeSetSellerInfo.getSellerType());
				 
				 
				 TradeSettlementConstDTO tradeSettlementConstDTO = tradeSettlementService.getSetConsByTypeAndKey(SettlementConstants.CONST_TYPE_PRODUCT_CHANNEL,tradeSetSellerInfo.getProductChannelCode());

					//渠道编码: 10 内部供应商 20 外部供应商 3010 京东商品＋
					 String productChannelCode = tradeSettlementConstDTO.getConstKey();
					//渠道名称
					 String productChannelName = tradeSettlementConstDTO.getConstValue();
					//结算单类型: 10 外部供应商 20 平台公司
					 String settlementTypeCode = null;
					//渠道名称
					 String settlementTypeName = null;
					 
					 // 外部供应商
					 if(SettlementEnum.ProductChannel.PRODUCT_CHANNEL_20.key().equals(productChannelCode)){
						 settlementTypeCode = SettlementEnum.SettlementType.SETTLEMENT_TYPE_10.key();
						 settlementTypeName = SettlementEnum.SettlementType.SETTLEMENT_TYPE_10.value();
					 }else{//商品+等等其他外接商品渠道
						 settlementTypeCode = SettlementEnum.SettlementType.SETTLEMENT_TYPE_20.key();
						 settlementTypeName = SettlementEnum.SettlementType.SETTLEMENT_TYPE_20.value();
					 }
					
				//渠道编码: 10 内部供应商 20 外部供应商 3010 京东商品＋
				tradeOrderSettlementDTO.setProductChannelCode(productChannelCode);
				tradeOrderSettlementDTO.setProductChannelName(productChannelName);
				//结算单类型: 10 外部供应商 20 平台公司
				tradeOrderSettlementDTO.setSettlementTypeCode(settlementTypeCode);
				tradeOrderSettlementDTO.setSettlementTypeName(settlementTypeName);

				 tradeOrderSettlementDTO.setOperateType(SettlementConstants.OPERATE_TYPE_JOB);

			     // 查询符合结算规则的订单数量
			     Integer cnt = tradeOrderSettlementDAO.getOrderSettlementCount(tradeOrderSettlementDTO);
			     if(null == cnt || cnt.intValue() < 1){//没有符合条件的订单记录
			    	 System.out.println("供应商[" + tradeOrderSettlementDTO.getSellerName() + "," + tradeOrderSettlementDTO.getSellerCode() + "]没有符合条件的订单记录！！！");
			    	 return;
			     }
			     //设置总记录数
			     tradeOrderSettlementDTO.setTotalRows(cnt);

			     // 生成新的结算单
			     String settlementNo = tradeSettlementService.generateAStatementBySeller(tradeOrderSettlementDTO);
			     System.out.println("===》生成的结算单号：" + settlementNo);

			 }
		 }


	}


    /**
     * 根据结算单号重新生成结算单单元测试
     */
	@Test
	public void rebuildTheBillBySettlementNo() {

		//结算单号
		String settlementNo = "101703290086";
		//操作用户ID
		Long userId = 100001L;
		//操作用户名称
		String userName = "system_admin";

	     // 生成新的结算单
	     Map<String,String> retMap = tradeSettlementService.rebuildTheBillBySettlementNo(settlementNo,userId,userName);
	    // retStatus 1.生成结算单成功  2.生成结算单失败  3.结算单不是待财务确认状态 4.没有符合条件的订单记录 5.结算单不存在
	     String status = retMap.get("status");
	     if("1".equals(status)){
	    	 String settlementNo_new = retMap.get("settlementNo");
	    	 System.out.println("===》重新生成的结算单号：" + settlementNo_new);
	     }


	}




	@Test
	public void redisTest(){

		settlementUtils.setTaskRunFlag(SettlementConstants.SETTLEMENT_SCHEDULE_TASK, SettlementConstants.RUN_FLAG, SettlementConstants.RUNING);

	}
	
	
	/**
	 * 根据条件查询结算单的总金额
	 */
	@Test
	public void getTradeSetTotalAmountByParamsTest(){
		String sellerCode = "htd20012342131";
		String payType = "1";
		String payType2 = "2";
		//payType可以传入>=1个
//		BigDecimal TotalAmount = tradeSettlementService.getTradeSetTotalAmountByParams(sellerCode, payType);
//		BigDecimal TotalAmount = tradeSettlementService.getTradeSetTotalAmountByParams(sellerCode, payType,payType2);
//		System.out.println("===>TotalAmount:" + TotalAmount);
	}

	/**
	 * @author tangjiayong
	 *
	 * 查询未结算的结算单数量
	 */
	@Test
	public void queryUnSettlementCountTest()
	{
		String sellerCode ="1";
		ExecuteResult<List<UnSettlementCount>> result = tradeSettlementService.queryUnSettlementCount();
		logger.info(result.getCode()+","+result.getResult());
	}

	@Test
	public void queryUnSettlementCount1Test()
	{
		Map<String,String> params = new HashMap<String,String>();
		params.put("sellerCode", "100000");
		params.put("status", "11");
		Map<String,String> retMap = tradeSettlementService.queryUnSettlementCount(params);
		System.out.println("status:"+retMap.get("status")+",msg:"+retMap.get("msg"));
	}

    /**
     * 根据type和key查询单条常量
     */
	@Test
	public void getSetConsByTypeAndKeyTest()
	{
		TradeSettlementConstDTO dto = tradeSettlementService.getSetConsByTypeAndKey(SettlementConstants.CONST_TYPE_ACCOUNTS_RECEIVABLE,"htdAccountNo");
		System.out.println(dto);
	}

	/**
	 * 根据type查询多条常量
	 */
	@Test
	public void getSetConsByTypeTest()
	{
		List<TradeSettlementConstDTO> list = tradeSettlementService.getSetConsByType(SettlementConstants.CONST_TYPE_PRODUCT_CHANNEL);
		System.out.println(list);
	}

	/**
	 * 查询需要补偿的结算单
	 */
	@Test
	public void getTradeSettlementCompsTest()
	{
		List<TradeSettlementComp> list = tradeSettlementService.getTradeSettlementComps();
		System.out.println(list);
	}

	@Test
	public void wd(){
//		Map<String,Object> wdcParams = new HashMap<String,Object>();
//		wdcParams.put("settlementNo","101703290092");
//        String bindId = "O00216121917021011100000";
//        String accountNo = "16121616222911100000";
//        String userId = "16121616222911100000";
//        String amount = "400";
//        String userIp = "";
//        wdcParams.put("bindId" , bindId);
//        wdcParams.put("accountNo" , accountNo);
//        wdcParams.put("userId" , userId);
//        wdcParams.put("amount" , amount);
//        wdcParams.put("userIp" , userIp);
//        wdcParams.put("totalCommissionAmount" , "400");
//        TradeSettlementWithdrawDTO dto = new TradeSettlementWithdrawDTO();
//        dto.setOperateType("1");//清分
//		dto.setTradeType("2");//平台公司
//		dto.setSettlementNo("101703290092");
//		tradeSettlementService.payOperation(dto);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("settlementNo", "201704260021");
		params.put("userId", "1");
		params.put("userName", "admin");
		tradeSettlementService.insidetradeSetAffirm(params);
	}
	
	@Test
	public void batchDistribution(){
		TradeSettlementWithdrawDTO dto = new TradeSettlementWithdrawDTO();
		dto.setOperateType("1");//清分
		dto.setTradeType("1");//外部供应商
		dto.setSettlementNo("101704260020");
		List<String> merchantOrderNoList = new ArrayList<String>();
		merchantOrderNoList.add("1017042617282602319");
//		Map<String,Object> mapForNotErp = tradeSettlementDetailDAO.queryCountMoney(dto);
		tradeSettlementService.batchDistribution(dto, merchantOrderNoList, "4074.76", "http://op.htd.cn/athena-web/admin/callback/distributionReturn");
	}

	@Test
	public void insidetradeSetAffirm(){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("settlementNo", "201705250007");
		
		params.put("userId", "1");
		params.put("userName", "系统管理员");
		tradeSettlementService.insidetradeSetAffirm(params);
	}
	
	@Test
	public void payOperation(){
		TradeSettlementWithdrawDTO dto = new TradeSettlementWithdrawDTO();
//		dto.setSettlementNo("201705250006");
		dto.setTradeNo("QF170525000011");
        dto.setOperateType("1");//提款
        dto.setTradeType("2");
		tradeSettlementService.payOperation(dto);
	}
	
	@Test
	public void withdrawalsConfirm(){
		Map<String,String> params = new HashMap<String, String>();
        params.put("settlementNo" , "101705310017");
        params.put("bindId" , "O00217060810221611100000");
        params.put("accountNo" , "17041117051311100048");
        params.put("userId" , "17041117051311100048");
        params.put("amount" , "269.12000000");
        params.put("userIp" , null);

    	ExecuteResult<Map<String,Object>> result = tradeSettlementService.withdrawalsConfirm(params);

	}
	
	@Test
	public void doSyncExternalTradeData(){
		try {
			tradeSettlementService.doSyncExternalTradeData("QF170801000028");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void updateTradeStatus(){
		TradeSettlementWithdrawDTO dto = new TradeSettlementWithdrawDTO();
		dto.setTradeNo("TK171114000115");
		dto.setOperateType("2");//提款
		dto.setTradeType("1");//外部供应商
		dto.setModifyTime(new Date());
		dto.setStatus(SettlementStatusEnum.SETTLEMENT_STATUS_11.key());
		dto.setStatusText(SettlementStatusEnum.SETTLEMENT_STATUS_11.value());
        tradeSettlementService.updateTradeStatus(dto);
	}
}
