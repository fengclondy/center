package cn.htd.tradecenter.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.dao.util.RedisDB;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberDetailInfo;
import cn.htd.membercenter.dto.MemberImportSuccInfoDTO;
import cn.htd.membercenter.service.MemberBaseInfoService;
import cn.htd.tradecenter.common.constant.SettlementConstants;
import cn.htd.tradecenter.common.enums.SettlementEnum;
import cn.htd.tradecenter.common.enums.SettlementStatusEnum;
import cn.htd.tradecenter.common.utils.ErpPay;
import cn.htd.tradecenter.common.utils.PaySDK;
import cn.htd.tradecenter.common.utils.RedissonClientUtil;
import cn.htd.tradecenter.common.utils.SettlementUtils;
import cn.htd.tradecenter.dao.TraSetComOpeDAO;
import cn.htd.tradecenter.dao.TradeOrderSettlementDAO;
import cn.htd.tradecenter.dao.TradeSettlementCompDAO;
import cn.htd.tradecenter.dao.TradeSettlementConstDAO;
import cn.htd.tradecenter.dao.TradeSettlementDAO;
import cn.htd.tradecenter.dao.TradeSettlementDetailDAO;
import cn.htd.tradecenter.dao.TradeSettlementStatusHistoryDAO;
import cn.htd.tradecenter.dao.TradeSettlementWithdrawDAO;
import cn.htd.tradecenter.domain.UnSettlementCount;
import cn.htd.tradecenter.domain.order.TradeOrderSettlement;
import cn.htd.tradecenter.domain.order.TradeOrderSettlementItems;
import cn.htd.tradecenter.domain.order.TradeSettlementComp;
import cn.htd.tradecenter.dto.TradeOrderSettlementDTO;
import cn.htd.tradecenter.dto.bill.TraSetComOpeDTO;
import cn.htd.tradecenter.dto.bill.TradeOrderItemDTO;
import cn.htd.tradecenter.dto.bill.TradeSettlementCompDTO;
import cn.htd.tradecenter.dto.bill.TradeSettlementConstDTO;
import cn.htd.tradecenter.dto.bill.TradeSettlementDTO;
import cn.htd.tradecenter.dto.bill.TradeSettlementDetailDTO;
import cn.htd.tradecenter.dto.bill.TradeSettlementStatusHistoryDTO;
import cn.htd.tradecenter.dto.bill.TradeSettlementWithdrawDTO;
import cn.htd.tradecenter.service.TradeSettlementService;
import cn.htd.tradecenter.service.convert.TradeSettlementConvert;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yiji.openapi.tool.YijifuGateway;
import com.yiji.openapi.tool.YijipayConstants;
import com.yiji.openapi.tool.util.DigestUtil;
import com.yiji.openapi.tool.util.Ids;

@Service("tradeSettlementService")
public class TradeSettlementServiceImpl implements TradeSettlementService{

	private static final Logger logger = LoggerFactory.getLogger(TradeSettlementServiceImpl.class);
	
	private static final String ROCKTITTLE = "WITHDRAW";
	// 订单DAO
	@Resource
	private TradeOrderSettlementDAO tradeOrderSettlementDAO;
	// 结算信息DAO
	@Resource
	private TradeSettlementDAO tradeSettlementDAO;
	// 结算详情DAO
	@Resource
	private TradeSettlementDetailDAO tradeSettlementDetailDAO;
	// 结算历史DAO
	@Resource
	private TradeSettlementStatusHistoryDAO tradeSettlementStatusHistoryDAO;
	@Resource
	private TradeSettlementConstDAO tradeSettlementConstDAO;
	
	@Resource
	private SettlementUtils settlementUtils;
	@Resource
	private PaySDK paySDK;
	
	@Resource
	private ErpPay erpPay;
	
	@Resource
	private TradeSettlementWithdrawDAO tradeSettlementWithdrawDAO;
	
	@Resource
	private TraSetComOpeDAO traSetComOpeDAO;
	
	@Resource
	private TradeSettlementCompDAO tradeSettlementCompDAO;
	
	@Resource
	private TradeSettlementConvert tradeSettlementConvert;
	
	@Resource
	private RedisDB redisDB;

	@Resource
	private BasicDataSource dataSource;
	
	@Resource
	private MemberBaseInfoService memberBaseInfoService;
	
	@Resource
	private RedissonClientUtil redissonClientUtil;

	@Override
	public ExecuteResult<DataGrid<TradeSettlementDTO>> queryTradeSettlements(HashMap<String, Object> params, Pager<TradeSettlementDTO> pager) {
		params.put("channelCode","10");
		return queryTradeSettlement(params,pager);
	}

	@Override
	public ExecuteResult<TradeSettlementDTO> queryTradeSettlementsByParams(
			Map<String, Object> params) {
		ExecuteResult<TradeSettlementDTO> result = new ExecuteResult<TradeSettlementDTO>();
		try {
			List<TradeSettlementDTO> tradeSettlementDTOList = tradeSettlementDAO.queryTradeSettlementsByParams(params);
			if(CollectionUtils.isEmpty(tradeSettlementDTOList)){
				result.setResult(null);
			}
			result.setResult(tradeSettlementDTOList.get(0));
		} catch (Exception e) {
			logger.error("执行方法【queryTradeSettlementsByParams】报错：{}", e);
			result.addErrorMessage("执行方法【queryTradeSettlementsByParams】报错：" + e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public ExecuteResult<DataGrid<TradeSettlementDetailDTO>> queryTradeSettlementDetails(Map<String, Object> params, Pager<TradeSettlementDetailDTO> pager) {
		ExecuteResult<DataGrid<TradeSettlementDetailDTO>> result = new ExecuteResult<DataGrid<TradeSettlementDetailDTO>>();
		try {
			DataGrid<TradeSettlementDetailDTO> dataGrid = new DataGrid<TradeSettlementDetailDTO>();
			List<TradeSettlementDetailDTO> tradeSettlementDetailDTOList = tradeSettlementDetailDAO.queryTradeSettlementDetailsForPage(params, pager);
			for (TradeSettlementDetailDTO detail : tradeSettlementDetailDTOList) {
				StringBuilder cateList=new StringBuilder();
				if(detail.getFirstCategoryName()!=null && !"".equals(detail.getFirstCategoryName())){
					cateList.append(detail.getFirstCategoryName()).append(">>");
				}else{
					cateList.append("-->>");
				}
				if(detail.getSecondCategoryName()!=null  && !"".equals(detail.getSecondCategoryName())){
					cateList.append(detail.getSecondCategoryName()).append(">>");
				}else{
					cateList.append("-->>");
				}
				if(detail.getThirdCategoryName()!=null  && !"".equals(detail.getThirdCategoryName())){
					cateList.append(detail.getThirdCategoryName());
				}else{
					cateList.append("--");
				}
				detail.setCategoryNameList(cateList.toString());
			}
			long count = tradeSettlementDetailDAO.countTradeSettlementDetails(params);
			dataGrid.setTotal(count);
			dataGrid.setRows(tradeSettlementDetailDTOList);
			result.setResult(dataGrid);
		} catch (Exception e) {
			logger.error("执行方法【queryTradeSettlements】报错：{}", e);
			result.addErrorMessage("执行方法【queryTradeSettlements】报错：" + e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}


	@Override
	public ExecuteResult<DataGrid<TradeSettlementDTO>> tradeSettlementsManageForPlatformCompany(HashMap<String, Object> params, Pager<TradeSettlementDTO> pager) {
		params.put("channelCode","20");
		return queryTradeSettlement(params,pager);
	}

	@Override
	public ExecuteResult<DataGrid<TradeSettlementDTO>> tradeSettlementsDetailForPlatformCompany(HashMap<String, Object> params, Pager<TradeSettlementDTO> pager) {
		return null;
	}

	private ExecuteResult<DataGrid<TradeSettlementDTO>> queryTradeSettlement(Map<String, Object> params, Pager<TradeSettlementDTO> pager){
		ExecuteResult<DataGrid<TradeSettlementDTO>> result = new ExecuteResult<DataGrid<TradeSettlementDTO>>();
		try {
			DataGrid<TradeSettlementDTO> dataGrid = new DataGrid<TradeSettlementDTO>();
			List<TradeSettlementDTO> tradeSettlementDTOList = tradeSettlementDAO.queryTradeSettlementsForPage(params, pager);
			long count = tradeSettlementDAO.countTradeSettlement(params);
			dataGrid.setTotal(count);
			dataGrid.setRows(tradeSettlementDTOList);
			result.setResult(dataGrid);
		} catch (Exception e) {
			logger.error("执行方法【queryTradeSettlement】报错：{}", e);
			result.addErrorMessage("执行方法【queryTradeSettlement】报错：" + e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	
	@Override
	public List<Map<String,Object>> getTradeSetConst(Map<String, Object> params){
		List<Map<String,Object>> statusList = new ArrayList<Map<String,Object>>();
		try {
			statusList = tradeSettlementConstDAO.getTradeSetConst(params);
		} catch (Exception e) {
			logger.error("执行方法【getTradeSetStatus】报错：{}", e);
			throw new RuntimeException(e);
		}
		return statusList;
	}
	
	@Transactional
	@Override
	public Map<String,String> tradeSetInvalid(Map<String, Object> params) {
		
		//操作用户ID
		Long userId = 100001L;
		//操作用户名称
		String userName = "system_admin";
		
		Map<String,String> map = new HashMap<String,String>();
		if(params.get("settlementNo") != null){
			map = rebuildTheBillBySettlementNo(params.get("settlementNo").toString(),userId,userName);
		}
		if(map.get("status") != null && "1".equals(map.get("status"))){
			params.put("status", SettlementStatusEnum.SETTLEMENT_STATUS_14.key());
			params.put("statusText", SettlementStatusEnum.SETTLEMENT_STATUS_14.value());
			int count = updateTradeStatus(params);
			if(count <= 0){
				map.put("status", "0");
			}
		}
		return map;
	}
	
	@Transactional
	public int updateTradeStatus(Map<String,Object> params){
		try {
			TradeSettlementDTO tradeSettlementDTO = new TradeSettlementDTO();
			TradeSettlementStatusHistoryDTO historyDTO = new TradeSettlementStatusHistoryDTO();
			if(params.get("settlementNo") != null){
				tradeSettlementDTO.setSettlementNo(params.get("settlementNo").toString());
				historyDTO.setSettlementNo(params.get("settlementNo").toString());
				historyDTO.setCreateTime(new Date());
				tradeSettlementDTO.setModifyTime(new Date());
			}
			if(params.get("userId") != null){
				tradeSettlementDTO.setModifyId(Long.parseLong(String.valueOf(params.get("userId"))));
				historyDTO.setCreateId(Long.parseLong(String.valueOf(params.get("userId"))));
			}
			if(params.get("userName") != null){
				tradeSettlementDTO.setModifyName(String.valueOf(params.get("userName")));
				historyDTO.setCreateName(String.valueOf(params.get("userName")));
			}
			if(params.get("status") != null){
				tradeSettlementDTO.setStatus(params.get("status").toString());
				historyDTO.setStatus(params.get("status").toString());
			}
			if(params.get("statusText") != null){
				historyDTO.setStatusText(params.get("statusText").toString());
			}
			int count = tradeSettlementDAO.updateTradeSetStatusByParams(tradeSettlementDTO);
			if(count > 0){
				count = tradeSettlementStatusHistoryDAO.addTradeSetStatusHistory(historyDTO);
			}
			return count;
		} catch (Exception e) {
			logger.error("执行方法【tradeSetInvalid】报错：{}", e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 根据结算单号重新生成结算单
	 * @param settlementNo 结算单号
	 * @param userId 操作用户ID
	 * @param userName 操作用户名称
	 * @return
	 */
	@Transactional
	public Map<String,String> rebuildTheBillBySettlementNo(String settlementNo,Long userId,String userName){
		
		// 业务场景描述：只有结算状态为待财务确认的，才有确认结算单按钮和重新生成结算单按钮。其余状态不展示
		// 返回状态解释：1.生成结算单成功  2.生成结算单失败  3.结算单不是待财务确认状态 4.没有符合条件的订单记录 5.结算单不存在
		
		Map<String,String> retMap = new HashMap<String, String>();
		// retStatus 1.生成结算单成功  2.生成结算单失败  3.结算单不是待财务确认状态 4.没有符合条件的订单记录 5.结算单不存在  6.订单类型不存在
		String retStatus = "1";
		String settlementNo_new = null;
		
		try {
			
		TradeSettlementDTO tradeSettlement = tradeSettlementDAO.getTradeSettlementBySettlementNo(settlementNo);
		
		if(null == tradeSettlement){
			logger.info("此结算单[" + settlementNo + "]不存在......");
			retStatus = "5";
			retMap.put("status", retStatus);
			return retMap;
		}
		
		//结算状态:10(待财务确认)、11(待商家提款)、12(商家提款处理中)、13(结算已完成)、14(结算单失效)
		String status = tradeSettlement.getStatus();
		// 只有结算状态为待财务确认的，才有确认结算单按钮和重新生成结算单按钮。其余状态不展示
		if(!SettlementStatusEnum.SETTLEMENT_STATUS_10.key().equals(status)){
			logger.info("结算单[" + settlementNo + "]状态(" + status + ")不是待财务确认");
			retStatus = "3";
			retMap.put("status", retStatus);
			return retMap;
		}

		TradeOrderSettlementDTO tradeOrderSettlementDTO = new TradeOrderSettlementDTO();
		
		tradeOrderSettlementDTO.setSellerId(tradeSettlement.getSellerId());
		tradeOrderSettlementDTO.setSellerCode(tradeSettlement.getSellerCode());
		tradeOrderSettlementDTO.setSellerName(tradeSettlement.getSellerName());
		tradeOrderSettlementDTO.setSellerType(tradeSettlement.getSellerType());
		
//		 tradeOrderSettlementDTO.setProductChannelCode(tradeSettlement.getProductChannelCode());
//		 tradeOrderSettlementDTO.setProductChannelName(tradeSettlement.getProductChannelName());
		 
			List<TradeSettlementConstDTO> tradeSettlementConstList = getSetConsByType(SettlementConstants.CONST_TYPE_USE_PRODUCT_CHANNEL);
			if(null == tradeSettlementConstList || tradeSettlementConstList.size() < 1){
				logger.info("提示：请配置要统计的订单类型！！！");
				retStatus = "6";
				retMap.put("status", retStatus);
				return retMap;
			}

			String[] useProductChannelCodeArray = null;
			// 外部供应商
			if(SettlementEnum.SettlementType.SETTLEMENT_TYPE_10.key().equals(tradeSettlement.getProductChannelCode())){
				useProductChannelCodeArray = new String[1];
				for(int i=0;i<tradeSettlementConstList.size();i++){
					String useProductChannelCode = tradeSettlementConstList.get(i).getConstKey();
					if(SettlementEnum.ProductChannel.PRODUCT_CHANNEL_20.key().equals(useProductChannelCode)){
						useProductChannelCodeArray[0] = useProductChannelCode;
					}
				}
			}else{//商品+
				useProductChannelCodeArray = new String[tradeSettlementConstList.size()];
				for(int i=0;i<tradeSettlementConstList.size();i++){
					String useProductChannelCode = tradeSettlementConstList.get(i).getConstKey();
					if(!SettlementEnum.ProductChannel.PRODUCT_CHANNEL_20.key().equals(useProductChannelCode)){
						useProductChannelCodeArray[i] = useProductChannelCode;
					}
				}
			}

			tradeOrderSettlementDTO.setUseProductChannelCodeArray(useProductChannelCodeArray);
		 
		//结算单类型: 10 外部供应商 20 平台公司
		tradeOrderSettlementDTO.setSettlementTypeCode(tradeSettlement.getProductChannelCode());
		tradeOrderSettlementDTO.setSettlementTypeName(tradeSettlement.getProductChannelName());
		 
		 // 设置操作类型和操作用户信息
		 tradeOrderSettlementDTO.setOperateType(SettlementConstants.OPERATE_TYPE_SYSTEM);
		 tradeOrderSettlementDTO.setCreateId(userId);
		 tradeOrderSettlementDTO.setCreateName(userName);
		 tradeOrderSettlementDTO.setModifyId(userId);
		 tradeOrderSettlementDTO.setModifyName(userName);
		
		// [start]-------------startTime endTime 的计算------------
		//结算单生成时间(job的生成时间)
		Date settlementTime = tradeSettlement.getSettlementTime();
		
		Calendar calendarLastMonth = Calendar.getInstance();
		calendarLastMonth.setTime(settlementTime);
		calendarLastMonth.add(Calendar.MONTH, -1);
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM");
		
		String calendarLastMonthStr = sdf.format(calendarLastMonth.getTime());
		String calendarCurrentMonthStr = sdf.format(settlementTime);
		
			// 开始时间
		String startTime = calendarLastMonthStr + "-01 00:00:00";
			// 结束时间
		String endTime = calendarCurrentMonthStr + "-01 00:00:00";
		tradeOrderSettlementDTO.setStartTime(startTime);
		tradeOrderSettlementDTO.setEndTime(endTime);
		tradeOrderSettlementDTO.setSettlementTime(settlementTime);
		// [end]-------------startTime endTime 的计算------------
		
		
	    // 多个订单状态 [61,62] 61:买家收货,62:到期自动收货
	     String[] orderStatusArray = new String[]{SettlementConstants.ORDER_STATUS_61,SettlementConstants.ORDER_STATUS_62};
	     tradeOrderSettlementDTO.setOrderStatusArray(orderStatusArray);
	     
	     //已支付取消订单状态 [40,50]
	     String[] orderStatusCancelArray = new String[]{SettlementConstants.ORDER_STATUS_40,SettlementConstants.ORDER_STATUS_50};
	     tradeOrderSettlementDTO.setOrderStatusCancelArray(orderStatusCancelArray);
	     //设置订单取消
	     tradeOrderSettlementDTO.setIsCancelOrder(SettlementConstants.IS_CANCEL_ORDER_1);
	     
	     //是否已结算 0：未结算，1：已结算，2：结算处理中
	     tradeOrderSettlementDTO.setIsSettled(SettlementConstants.IS_SETTLED_2);
	     
	     // 出结算单时间间隔【默认为7天】
	     tradeOrderSettlementDTO.setTimeInterval(SettlementConstants.TIME_INTERVAL);
	     
	     tradeOrderSettlementDTO.setSortName(SettlementConstants.SORT_NAME_MODIFYTIME);
	     tradeOrderSettlementDTO.setSortDir(SettlementConstants.SORT_DIR_ASC);
	     
	     // 查询符合结算规则的订单数量
	     Integer cnt = tradeOrderSettlementDAO.getOrderSettlementCount(tradeOrderSettlementDTO);
	     if(null == cnt || cnt.intValue() < 1){//没有符合条件的订单记录
	    	 logger.info("供应商[" + tradeOrderSettlementDTO.getSellerName() + "," + tradeOrderSettlementDTO.getSellerCode() + "]没有符合条件的订单记录！！！");
	    	 retStatus = "4";
	    	 retMap.put("status", retStatus);
			return retMap;
	     }
	     //设置总记录数
	     tradeOrderSettlementDTO.setTotalRows(cnt);
	     
	     // 生成新的结算单
	 	settlementNo_new = generateAStatementBySeller(tradeOrderSettlementDTO);
	 	if(null == settlementNo_new){
	 		logger.info("结算单[" + settlementNo + "]重新生成失败！！！");
	 		 retStatus = "2";
	 		 retMap.put("status", retStatus);
			return retMap;
	 	}
	 	
		
		// 获取服务器当前时间
		Calendar calendar = Calendar.getInstance();
		Date currentTime = calendar.getTime();
		
	 	// 结算单状态 [10.待财务确认,11.待商家提款,12.商家提款处理中,13.结算已完成,14.结算单失效]
	 	// 更新结算单状态为 结算单失效
	 	TradeSettlementDTO tradeSettlementDTO = new TradeSettlementDTO();
	 	tradeSettlementDTO.setSettlementNo(settlementNo);
	 	tradeSettlementDTO.setStatus(SettlementStatusEnum.SETTLEMENT_STATUS_14.key());
	 	tradeSettlementDTO.setModifyId(userId);
	 	tradeSettlementDTO.setModifyName(userName);
	 	tradeSettlementDTO.setModifyTime(currentTime);
		tradeSettlementDAO.updateTradeSetStatusByParams(tradeSettlementDTO);
		
		// 添加结算单历史记录
		TradeSettlementStatusHistoryDTO tradeSettlementStatusHistoryDTO = new TradeSettlementStatusHistoryDTO();
		tradeSettlementStatusHistoryDTO.setSettlementNo(settlementNo);
		
		// 结算单状态 [10.待财务确认,11.待商家提款,12.商家提款处理中,13.结算已完成,14.结算单失效]
		tradeSettlementStatusHistoryDTO.setStatus(SettlementStatusEnum.SETTLEMENT_STATUS_14.key());
		tradeSettlementStatusHistoryDTO.setStatusText(SettlementStatusEnum.SETTLEMENT_STATUS_14.value());
		tradeSettlementStatusHistoryDTO.setCreateId(userId);
		tradeSettlementStatusHistoryDTO.setCreateName(userName);
		tradeSettlementStatusHistoryDTO.setCreateTime(currentTime);
		tradeSettlementStatusHistoryDTO.setModifyId(userId);
		tradeSettlementStatusHistoryDTO.setModifyName(userName);
		tradeSettlementStatusHistoryDTO.setModifyTime(currentTime);
		
		tradeSettlementStatusHistoryDAO.addTradeSetStatusHistory(tradeSettlementStatusHistoryDTO);
		
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "TradeSettlementServiceImpl-rebuildTheBillBySettlementNo", e.toString());
	 		 retStatus = "2";
	 		 retMap.put("status", retStatus);
			return retMap;
		}
		
		retMap.put("settlementNo", settlementNo_new);
		retMap.put("status", retStatus);
		return retMap;
	}
	
	/**
	 * 查询需要补偿的结算单
	 * @return
	 */
	public List<TradeSettlementComp> getTradeSettlementComps(){
		
		TradeSettlementCompDTO tradeSettlementCompDTO = new TradeSettlementCompDTO();
		tradeSettlementCompDTO.setStatus(SettlementConstants.SETTLEMENTCOMP_STATUS_FAILED);
		tradeSettlementCompDTO.setCurrentPage(1);
		
		return tradeSettlementCompDAO.getTradeSettlementCompsByParams(tradeSettlementCompDTO);
	}
	
	
    /**
     * job补偿生成结算单
     * @param tradeSettlementComp
     */
	@Transactional
    public void rebuildTheTradeSettlementComp(TradeSettlementComp tradeSettlementComp){
		
		// 业务场景描述：只有结算状态为待财务确认的，才有确认结算单按钮和重新生成结算单按钮。其余状态不展示
		// 返回状态解释：1.生成结算单成功  2.生成结算单失败  3.结算单不是待财务确认状态 4.没有符合条件的订单记录 5.结算单不存在
		
		// retStatus 1.生成结算单成功  2.生成结算单失败  3.结算单不是待财务确认状态 4.没有符合条件的订单记录 5.结算单不存在  6.订单类型不存在
		
		try {
		
		if(null == tradeSettlementComp){
			logger.info("tradeSettlementComp is null......");
			return;
		}
		
		//状态: 成功(success)/失败(failed)
		String status = tradeSettlementComp.getStatus();
		if(!SettlementConstants.SETTLEMENTCOMP_STATUS_FAILED.equals(status)){
			logger.info("结算单[" + tradeSettlementComp.getSettlementNo() + "]状态(" + status + ")不是失败");
			return;
		}

		TradeOrderSettlementDTO tradeOrderSettlementDTO = new TradeOrderSettlementDTO();
		
		tradeOrderSettlementDTO.setSellerId(tradeSettlementComp.getSellerId());
		tradeOrderSettlementDTO.setSellerCode(tradeSettlementComp.getSellerCode());
		tradeOrderSettlementDTO.setSellerName(tradeSettlementComp.getSellerName());
		tradeOrderSettlementDTO.setSellerType(tradeSettlementComp.getSellerType());
		 
			List<TradeSettlementConstDTO> tradeSettlementConstList = getSetConsByType(SettlementConstants.CONST_TYPE_USE_PRODUCT_CHANNEL);
			if(null == tradeSettlementConstList || tradeSettlementConstList.size() < 1){
				logger.info("提示：请配置要统计的订单类型！！！");
				return;
			}

			String[] useProductChannelCodeArray = null;
			// 外部供应商
			if(SettlementEnum.SettlementType.SETTLEMENT_TYPE_10.key().equals(tradeSettlementComp.getProductChannelCode())){
				useProductChannelCodeArray = new String[1];
				for(int i=0;i<tradeSettlementConstList.size();i++){
					String useProductChannelCode = tradeSettlementConstList.get(i).getConstKey();
					if(SettlementEnum.ProductChannel.PRODUCT_CHANNEL_20.key().equals(useProductChannelCode)){
						useProductChannelCodeArray[0] = useProductChannelCode;
					}
				}
			}else{//商品+
				useProductChannelCodeArray = new String[tradeSettlementConstList.size()];
				for(int i=0;i<tradeSettlementConstList.size();i++){
					String useProductChannelCode = tradeSettlementConstList.get(i).getConstKey();
					if(!SettlementEnum.ProductChannel.PRODUCT_CHANNEL_20.key().equals(useProductChannelCode)){
						useProductChannelCodeArray[i] = useProductChannelCode;
					}
				}
			}

			tradeOrderSettlementDTO.setUseProductChannelCodeArray(useProductChannelCodeArray);
		 
		//结算单类型: 10 外部供应商 20 平台公司
		tradeOrderSettlementDTO.setSettlementTypeCode(tradeSettlementComp.getProductChannelCode());
		tradeOrderSettlementDTO.setSettlementTypeName(tradeSettlementComp.getProductChannelName());
		 
		 // 设置操作类型和操作用户信息
		 tradeOrderSettlementDTO.setOperateType(SettlementConstants.OPERATE_TYPE_JOB);
		 tradeOrderSettlementDTO.setCreateId(SettlementConstants.SYSTEM_CRON_ID);
		 tradeOrderSettlementDTO.setCreateName(SettlementConstants.SYSTEM_CRON_NAME);
		 tradeOrderSettlementDTO.setModifyId(SettlementConstants.SYSTEM_CRON_ID);
		 tradeOrderSettlementDTO.setModifyName(SettlementConstants.SYSTEM_CRON_NAME);
		
		// [start]-------------startTime endTime 的计算------------
		//结算单生成时间(job的生成时间)
		Date settlementTime = tradeSettlementComp.getSettlementTime();
		
		Calendar calendarLastMonth = Calendar.getInstance();
		calendarLastMonth.setTime(settlementTime);
		calendarLastMonth.add(Calendar.MONTH, -1);
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM");
		
		String calendarLastMonthStr = sdf.format(calendarLastMonth.getTime());
		String calendarCurrentMonthStr = sdf.format(settlementTime);
		
			// 开始时间
		String startTime = calendarLastMonthStr + "-01 00:00:00";
			// 结束时间
		String endTime = calendarCurrentMonthStr + "-01 00:00:00";
		tradeOrderSettlementDTO.setStartTime(startTime);
		tradeOrderSettlementDTO.setEndTime(endTime);
		tradeOrderSettlementDTO.setSettlementTime(settlementTime);
		// [end]-------------startTime endTime 的计算------------
		
		
	    // 多个订单状态 [61,62] 61:买家收货,62:到期自动收货
	     String[] orderStatusArray = new String[]{SettlementConstants.ORDER_STATUS_61,SettlementConstants.ORDER_STATUS_62};
	     tradeOrderSettlementDTO.setOrderStatusArray(orderStatusArray);
	     
	     //已支付取消订单状态 [40,50]
	     String[] orderStatusCancelArray = new String[]{SettlementConstants.ORDER_STATUS_40,SettlementConstants.ORDER_STATUS_50};
	     tradeOrderSettlementDTO.setOrderStatusCancelArray(orderStatusCancelArray);
	     //设置订单取消
	     tradeOrderSettlementDTO.setIsCancelOrder(SettlementConstants.IS_CANCEL_ORDER_1);
	     
	     //是否已结算 0：未结算，1：已结算，2：结算处理中
	     tradeOrderSettlementDTO.setIsSettled(SettlementConstants.IS_SETTLED_2);
	     
	     // 出结算单时间间隔【默认为7天】
	     tradeOrderSettlementDTO.setTimeInterval(SettlementConstants.TIME_INTERVAL);
	     
	     tradeOrderSettlementDTO.setSortName(SettlementConstants.SORT_NAME_MODIFYTIME);
	     tradeOrderSettlementDTO.setSortDir(SettlementConstants.SORT_DIR_ASC);
	     
	     // 查询符合结算规则的订单数量
	     Integer cnt = tradeOrderSettlementDAO.getOrderSettlementCount(tradeOrderSettlementDTO);
	     if(null == cnt || cnt.intValue() < 1){//没有符合条件的订单记录
	    	 logger.info("供应商[" + tradeOrderSettlementDTO.getSellerName() + "," + tradeOrderSettlementDTO.getSellerCode() + "]没有符合条件的订单记录！！！");
			return ;
	     }
	     //设置总记录数
	     tradeOrderSettlementDTO.setTotalRows(cnt);
	     
	     // 生成新的结算单
	 	String settlementNo_new = generateAStatementBySeller(tradeOrderSettlementDTO);
	 	if(null == settlementNo_new){
	 		logger.info("结算单[" + tradeSettlementComp.getSettlementNo() + "]重新生成失败！！！");
			return ;
	 	}
		
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "TradeSettlementServiceImpl-rebuildTheJobBillBySettlementNo", e.toString());
		}
		
	}

	
	/**
	 * 生成新的结算单
	 */
	/**
	 * 生成新的结算单,返回结算单号
	 */
    @Transactional
	public String generateAStatementBySeller(TradeOrderSettlementDTO tradeOrderSettlementDTO) {
		
		logger.info("======》当前时间为：" + getCurrentTime());
		logger.info("======》正在生成供应商[" + tradeOrderSettlementDTO.getSellerName() + "," + tradeOrderSettlementDTO.getSellerCode() + "]的结算单......");
		
		String settlementNo = null;
		try {

//			结算单解释
//			结算单规则：T+M的订单符合当次账期时间，就会被纳入当次账期来出结算单
//			T+M：T为确认收货的时间点，M为自行设置的时间点
//			---自动确认收货时间为发货后的15天；M为7天
//			账期：一个自然月
//			出结算单时间：每个自然月的8号0点
			
			int totalPages = tradeOrderSettlementDTO.getTotalPages();// 总页数
			 
			// 计算生成结算单明细
			Map<String,BigDecimal> retMap = null;
			//佣金总金额
			BigDecimal totalCommissionAmount = BigDecimal.ZERO;
			//结算总金额
			BigDecimal totalSettlementAmount = BigDecimal.ZERO;
			
			// 生成结算单号
//			String settlementNo = tradeSettlementDAO.generateTheBillingNumber();
			settlementNo = settlementUtils.generateSettlementNo(tradeOrderSettlementDTO.getSettlementTypeCode());
						
		     // 循环处理每一页
		     for(int i = 1;i <= totalPages; i++){
		    	 
			     tradeOrderSettlementDTO.setCurrentPage(i);
			     
				    // 根据结算单规则查询符合条件的订单
					List<TradeOrderSettlement> tradeOrderSettlementList = tradeOrderSettlementDAO.getOrderSettlementsByParams(tradeOrderSettlementDTO);
					if(null == tradeOrderSettlementList || tradeOrderSettlementList.size() < 1){
						logger.info("供应商[" + tradeOrderSettlementDTO.getSellerName() + "," + tradeOrderSettlementDTO.getSellerCode() + "]没有符合条件的订单");
						continue;
					}
					
					// 计算生成结算单明细
					retMap = calculateTheBuildBillDetail(tradeOrderSettlementList,tradeOrderSettlementDTO,settlementNo);
				     // 没有计算生成结算单明细
//				     if(null == retMap){
//				    	logger.info("供应商[" + tradeOrderSettlementDTO.getSellerName() + "," + tradeOrderSettlementDTO.getSellerCode() + "]没有计算生成结算单明细");
//				    	return null;
//				     }
				     
					//佣金总金额
					BigDecimal totalCommissionAmount_det = retMap.get("totalCommissionAmount_page");
					//结算总金额
					BigDecimal totalSettlementAmount_det = retMap.get("totalSettlementAmount_page");
					// 每个订单行的总和
					totalCommissionAmount = totalCommissionAmount.add(totalCommissionAmount_det);
					totalSettlementAmount = totalSettlementAmount.add(totalSettlementAmount_det);
			     
		     }

				//佣金总金额
				retMap.put("totalCommissionAmount", totalCommissionAmount);
				//结算总金额
				retMap.put("totalSettlementAmount", totalSettlementAmount);

			// 计算生成结算单
		     calculateTheBuildBill(retMap,tradeOrderSettlementDTO,settlementNo);  
		     
		     // 返回结算单号
		     return settlementNo;
			
		} catch (Exception e) {
			logger.error("供应商[" + tradeOrderSettlementDTO.getSellerName() + "," + tradeOrderSettlementDTO.getSellerCode() + "]生成结算单异常！！！",e);
			// 处理异常的结算单
			handleExceptionSettlement(tradeOrderSettlementDTO,settlementNo,e.toString());
		}
		
		return null;
	}
	
    
    /**
     * 处理异常的结算单
     * @param tradeOrderSettlementDTO
     * @param settlementNo
     * @param content
     */
    public void handleExceptionSettlement(TradeOrderSettlementDTO tradeOrderSettlementDTO,String settlementNo,String content){
    	
       //只重跑job调度生成的异常结算单
	   //操作类型 1.job调度 2.运营系统管理员
	   if(SettlementConstants.OPERATE_TYPE_JOB.equals(tradeOrderSettlementDTO.getOperateType())){//1.job调度
		   if(content.length() < 990){
			   content.substring(0, content.length());
		   }else{
			   content.substring(0, 990);
		   }
		// 转换成结算补偿DTO
    	  TradeSettlementCompDTO tradeSettlementCompDTO = tradeSettlementConvert.convert2TradeSettlementCompDTO(tradeOrderSettlementDTO,settlementNo,content);
    	  tradeSettlementCompDAO.addTradeSettlementComp(tradeSettlementCompDTO);
	    }
	 
    }
    
	
	/**
	 * 计算生成结算单
	 * @param params
	 * @param tradeOrderSettlementDTO
	 * @param settlementNo
	 */
	@Transactional
	public void calculateTheBuildBill(Map<String,BigDecimal> params,TradeOrderSettlementDTO tradeOrderSettlementDTO,String settlementNo){
			
			//佣金总金额
			BigDecimal totalCommissionAmount = params.get("totalCommissionAmount");
			//结算总金额
			BigDecimal totalSettlementAmount = params.get("totalSettlementAmount");
			
			
			// 转换成结算DTO
			TradeSettlementDTO tradeSettlementDTO = tradeSettlementConvert.convert2TradeSettlementDTO(tradeOrderSettlementDTO,totalCommissionAmount,totalSettlementAmount,settlementNo);
			// 入结算信息表
			tradeSettlementDAO.addTradeSettlement(tradeSettlementDTO);
			
			// 转换成结算历史DTO
			TradeSettlementStatusHistoryDTO tradeSettlementStatusHistoryDTO = tradeSettlementConvert.convert2TradeSetStatusHistoryDTO(tradeOrderSettlementDTO,settlementNo);
			// 入结算历史表
			tradeSettlementStatusHistoryDAO.addTradeSetStatusHistory(tradeSettlementStatusHistoryDTO);
		
	}
	
	/**
	 * 计算生成结算单明细
	 * @param tradeOrderSettlementList
	 * @param tradeOrderSettlementDTO
	 * @param settlementNo
	 * @return
	 */
	@Transactional
	public Map<String,BigDecimal> calculateTheBuildBillDetail(List<TradeOrderSettlement> tradeOrderSettlementList,TradeOrderSettlementDTO tradeOrderSettlementDTO,String settlementNo){
		
		if(null == tradeOrderSettlementList || tradeOrderSettlementList.size() < 1){
			logger.info("供应商[" + tradeOrderSettlementDTO.getSellerName() + "," + tradeOrderSettlementDTO.getSellerCode() + "]没有结算单明细");
			return null;
		}
		
		Map<String,BigDecimal> retMap = new HashMap<String, BigDecimal>();
			
			//佣金总金额
			BigDecimal totalCommissionAmount = BigDecimal.ZERO;
			//结算总金额
			BigDecimal totalSettlementAmount = BigDecimal.ZERO;
			
			// 循环处理符合条件的订单
			for(TradeOrderSettlement tradeOrderSettlement : tradeOrderSettlementList){

				// 循环处理订单行
				List<TradeOrderSettlementItems> tradeOrderSettlementItemsList = tradeOrderSettlement.getOrderItemList();
				if(null != tradeOrderSettlementItemsList && tradeOrderSettlementItemsList.size() > 0){
					
					for(TradeOrderSettlementItems tradeOrderSettlementItems : tradeOrderSettlementItemsList){
						
						// 佣金金额
						BigDecimal commissionAmount = tradeOrderSettlementItems.getCommissionAmount();
						//结算金额 订单行金额-佣金金额
//						BigDecimal settlementAmount = tradeOrderSettlementItems.getOrderItemPayAmount().subtract(tradeOrderSettlementItems.getCommissionAmount());
						
						//结算金额 = 平台公司拿到的钱
//						BigDecimal totalCostPrice = tradeOrderSettlementItems.getCostPrice().multiply(new BigDecimal(tradeOrderSettlementItems.getGoodsCount()));
//						BigDecimal settlementAmount = tradeOrderSettlementItems.getOrderItemPayAmount().subtract(totalCostPrice).subtract(tradeOrderSettlementItems.getCommissionAmount());
						
						BigDecimal settlementAmount = BigDecimal.ZERO;
						String channelCode = tradeOrderSettlementItems.getChannelCode();
						 // 外部供应商
						 if(SettlementEnum.ProductChannel.PRODUCT_CHANNEL_20.key().equals(channelCode)){
							//结算金额=实付金额-佣金金额
							 settlementAmount = tradeOrderSettlementItems.getOrderItemPayAmount().subtract(tradeOrderSettlementItems.getCommissionAmount());
						 }else{//商品+等等其他外接商品渠道
							//结算金额 = 平台公司拿到的钱
							BigDecimal totalCostPrice = tradeOrderSettlementItems.getCostPrice().multiply(new BigDecimal(tradeOrderSettlementItems.getGoodsCount()));
							settlementAmount = tradeOrderSettlementItems.getOrderItemPayAmount().subtract(totalCostPrice).subtract(tradeOrderSettlementItems.getCommissionAmount());
						 }
						
						// 每个订单行的总和
						totalCommissionAmount = totalCommissionAmount.add(commissionAmount);
						totalSettlementAmount = totalSettlementAmount.add(settlementAmount);

					// 转换成结算详情DTO
					TradeSettlementDetailDTO tradeSettlementDetailDTO = tradeSettlementConvert.convert2TradeSettlementDetailDTO(tradeOrderSettlementItems,tradeOrderSettlement,
							tradeOrderSettlementDTO,settlementNo);
					//入结算详情表
					tradeSettlementDetailDAO.addTradeSettlementDetail(tradeSettlementDetailDTO);
					
					   //是否已结算 0：未结算，1：已结算，2：结算处理中
					TradeOrderItemDTO tradeOrderItemDTO = new TradeOrderItemDTO();
					tradeOrderItemDTO.setIsSettled(SettlementConstants.IS_SETTLED_2);
					tradeOrderItemDTO.setOrderNo(tradeOrderSettlementItems.getOrderNo());
					tradeOrderItemDTO.setOrderItemNo(tradeOrderSettlementItems.getOrderItemNo());
					//跟新订单行的结算状态
					tradeOrderSettlementDAO.updateTradeOrderItem(tradeOrderItemDTO);
						
					}
					
				}
				
			}
			
			//佣金总金额
			retMap.put("totalCommissionAmount_page", totalCommissionAmount);
			//结算总金额
			retMap.put("totalSettlementAmount_page", totalSettlementAmount);
		
		return retMap;
	}
	

    /**
     * 根据type和key查询单条常量
     * @param type
     * @param key
     * @return
     */
	@Override
	public TradeSettlementConstDTO getSetConsByTypeAndKey(String type,String key){
		
		TradeSettlementConstDTO tradeSettlementConstDTO = new TradeSettlementConstDTO();
		tradeSettlementConstDTO.setConstType(type);
		tradeSettlementConstDTO.setConstKey(key);
		tradeSettlementConstDTO.setConstStatus(SettlementConstants.SETTLEMENTCONST_STATUS_ENABLE);
		
		return tradeSettlementConstDAO.getSetConsByTypeAndKey(tradeSettlementConstDTO);
	}
	
	/**
	 * 根据type查询多条常量
	 * @param type
	 * @return
	 */
	@Override
	public List<TradeSettlementConstDTO> getSetConsByType(String type){
		
		TradeSettlementConstDTO tradeSettlementConstDTO = new TradeSettlementConstDTO();
		tradeSettlementConstDTO.setConstType(type);
		tradeSettlementConstDTO.setConstStatus(SettlementConstants.SETTLEMENTCONST_STATUS_ENABLE);
		
		return tradeSettlementConstDAO.getSetConsByType(tradeSettlementConstDTO);
	}
	
	/**
	 * 获取当前时间
	 * @return String
	 */
	public String getCurrentTime() {
		Calendar calendar = Calendar.getInstance();
		String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
		return currentTime;
	}

	@Transactional
	@Override
	public int tradeSetAffirm(Map<String, Object> params) {
		params.put("status", SettlementStatusEnum.SETTLEMENT_STATUS_11.key());
		params.put("statusText", SettlementStatusEnum.SETTLEMENT_STATUS_11.value());
		int count = updateTradeStatus(params);
		//以下为清分处理
		String settlementNo = null;
		TradeSettlementWithdrawDTO dto = new TradeSettlementWithdrawDTO();
		dto.setOperateType("1");//清分
		dto.setTradeType("1");//外部供应商
		if(params.get("settlementNo") != null){
			settlementNo = params.get("settlementNo").toString();
			dto.setSettlementNo(settlementNo);
		}
		if(params.get("userId") != null){
			dto.setCreateId(Long.parseLong(String.valueOf(params.get("userId"))));
			dto.setModifyId(Long.parseLong(String.valueOf(params.get("userId"))));
		}
		if(params.get("userName") != null){
			dto.setCreateName(String.valueOf(params.get("userName")));
			dto.setModifyName(String.valueOf(params.get("userName")));
		}
		updateOrderStatus(dto);
		List<String> merchantOrderNoList = tradeSettlementDAO.getMerchantOrderNoList(dto);
		Map<String,Object> mapForNotErp = tradeSettlementDetailDAO.queryCountMoney(dto);
		batchDistribution(dto , merchantOrderNoList , mapForNotErp.get("orderitempayamounts").toString() , paySDK.getBatchReturn_url());
		return count;
	}
	
	@Transactional
	@Override
	public int insidetradeSetAffirm(Map<String, Object> params) {
		logger.info("TradeSettlementServiceImpl-----insidetradeSetAffirm-----" + params);
		String settlementNo = null;
		int count = 0;
		TradeSettlementWithdrawDTO dto = new TradeSettlementWithdrawDTO();
		dto.setOperateType("1");//清分
		dto.setTradeType("2");//平台公司
		if(params.get("settlementNo")!=null){
			settlementNo = params.get("settlementNo").toString();
			dto.setSettlementNo(settlementNo);
		}
		if(params.get("userId") != null){
			dto.setCreateId(Long.parseLong(String.valueOf(params.get("userId"))));
			dto.setModifyId(Long.parseLong(String.valueOf(params.get("userId"))));
		}
		if(params.get("userName") != null){
			dto.setCreateName(String.valueOf(params.get("userName")));
			dto.setModifyName(String.valueOf(params.get("userName")));
		}
		params.put("status", SettlementStatusEnum.SETTLEMENT_STATUS_15.key());
		params.put("statusText", SettlementStatusEnum.SETTLEMENT_STATUS_15.value());
		count = updateTradeStatus(params);
		updateOrderStatus(dto);
		//erp 下账到小牛成本金额
		List<String> list = new ArrayList<String>();
		list.add("1");
		dto.setPayTypeList(list);//非erp
		List<String> merchantOrderNoList = tradeSettlementDAO.getMerchantOrderNoList(dto);
		if(!CollectionUtils.isEmpty(merchantOrderNoList)){
			TraSetComOpeDTO tscDto = new TraSetComOpeDTO();
			tscDto.setSettlementNo(settlementNo);
			tscDto.setOperateType("1");
			tscDto.setInterfaceType("3");
			for(String merchantOrderNo : merchantOrderNoList){
				dealForERP(tscDto , dto , merchantOrderNo);
			}
		}
		
		//非erp进行清分操作
		list.clear();
		merchantOrderNoList.clear();
		list.add("2");
		list.add("3");
		dto.setPayTypeList(list);//非erp
		merchantOrderNoList = tradeSettlementDAO.getMerchantOrderNoList(dto);
		if(!CollectionUtils.isEmpty(merchantOrderNoList)){
			Map<String,Object> mapForNotErp = tradeSettlementDetailDAO.queryCountMoney(dto);
			batchDistribution(dto , merchantOrderNoList , mapForNotErp.get("orderitempayamounts").toString() , paySDK.getReturn_url());
		}else{
			params.put("status", SettlementStatusEnum.SETTLEMENT_STATUS_13.key());
			params.put("statusText", SettlementStatusEnum.SETTLEMENT_STATUS_13.value());
			updateTradeStatus(params);
		}
		return count;
	}
	
	public void dealForERP(TraSetComOpeDTO tscDto , TradeSettlementWithdrawDTO dto , String merchOrderNo){	
		logger.info("TradeSettlementServiceImpl-----dealForERP-----begin");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("settlementNo", dto.getSettlementNo());
		MemberBaseInfoDTO memberBaseInfoDTO = getMemberBaseInfoDTO(dto.getSettlementNo());
	    logger.info("dealForERP-----memberBaseInfoDTO : " +memberBaseInfoDTO);
	    if(memberBaseInfoDTO == null){
			return ;
		}
//	    String merchOrderNo = settlementUtils.getMerchOrderNoForERP(memberBaseInfoDTO.getCompanyCode());
	    logger.info("dealForERP-----merchOrderNo : " +merchOrderNo);
	    if(dto.getTradeNo() == null){
	    	tscDto.setSettlementNo(dto.getSettlementNo());
			tscDto.setTradeNo(merchOrderNo);
			tscDto.setCreateTime(new Date());
			traSetComOpeDAO.addTraSetComOpe(tscDto);
		}
		List<String> list = new ArrayList<String>();
		list.add("1");
		dto.setPayTypeList(list);
		Map<String,Object> mapForErp = tradeSettlementDetailDAO.queryCountMoney(dto);
		logger.info("dealForERP-----mapForErp : " +mapForErp);
		Map<String,String> erpMap = getYjfCommonMap("withdrawMoneyForERP");
		erpMap.put("pcUserId", memberBaseInfoDTO.getCompanyCode());
		erpMap.put("costAmount", mapForErp.get("costandcomamount").toString());
		erpMap.put("merchOrderNo", merchOrderNo);
		logger.info("dealForERP-----withdrawMoneyForERP-----begin");
		logger.info("dealForERP-----withdrawMoneyForERP-----params:" +erpMap);
		String erpStr = YijifuGateway.getOpenApiClientService().doPost(paySDK.getUrl(), erpMap, paySDK.getKey());
		logger.info("dealForERP-----withdrawMoneyForERP-----result:" +erpStr);
		logger.info("dealForERP-----withdrawMoneyForERP-----end");
		JSONObject erpJson = JSONObject.fromObject(erpStr);
		if("EXECUTE_SUCCESS".equals(erpJson.get("resultCode"))){
			settlementUtils.dropMerchOrderNoForERP(memberBaseInfoDTO.getCompanyCode());
			tscDto.setStatus("1");
			tscDto.setModifyTime(new Date());
			tscDto.setContent(erpJson.get("resultMessage").toString());
		}else{
			tscDto.setStatus("0");
			tscDto.setModifyTime(new Date());
			tscDto.setContent(erpJson.get("resultMessage").toString());
		}
		traSetComOpeDAO.updateTraSetComOpe(tscDto);
	}
	
	
	@Override
	public ExecuteResult<List<UnSettlementCount>> queryUnSettlementCount() {
		ExecuteResult<List<UnSettlementCount>> result = new ExecuteResult<List<UnSettlementCount>> ();
		result.setCode(SettlementConstants.SETTLEMENT_COUNT_SUCCESS);
		result.setResultMessage(SettlementConstants.SETTLEMENT_COUNT_SUCCESS_TEXT);
		try {
			List<UnSettlementCount> list = new ArrayList<UnSettlementCount>();
			UnSettlementCount count1 = new UnSettlementCount();
			int outCount = tradeSettlementDAO.queryUnSettlementCount(SettlementEnum.SettlementType.SETTLEMENT_TYPE_10.key());
			count1.setVendorType(SettlementEnum.SettlementType.SETTLEMENT_TYPE_10.key());
			count1.setCount(outCount);
			list.add(count1);
			UnSettlementCount count2 = new UnSettlementCount();
			int inCount = tradeSettlementDAO.queryUnSettlementCount(SettlementEnum.SettlementType.SETTLEMENT_TYPE_20.key());
			count2.setVendorType(SettlementEnum.SettlementType.SETTLEMENT_TYPE_20.key());
			count2.setCount(inCount);
			list.add(count2);
			result.setResult(list);
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "TradeSettlementServiceImpl-queryUnSettlementCount", e.toString());
			result.setCode(SettlementConstants.SETTLEMENT_COUNT_FAILED);
			result.setResultMessage(SettlementConstants.SETTLEMENT_COUNT_FAILED_TEXT);
			result.addErrorMessage(e.toString());
			result.setResult(null);
		}
		return result;
	}
	
	
	@Override
	public Map<String, String> queryUnSettlementCount(Map<String, String> params) {
	
		Map<String,String> retMap = new HashMap<String, String>();
		try {
			String status = "";
			if(StringUtils.isNotEmpty(params.get("status"))){
				status = params.get("status");
			}else{
				status = "10";
			}
			int total = tradeSettlementDAO.queryUnSettlementCountBySellerCode(params.get("sellerCode"),status);
			retMap.put("status", "1");
			retMap.put("msg", String.valueOf(total));
			
		} catch (Exception e) {
			logger.error("执行方法【queryUnSettlementCount】报错：{}", e);
			retMap.put("status", "0");
			retMap.put("msg", e.getMessage());
		}
		
		return retMap;
	}
	
	@Override
	public ExecuteResult<Map<String, Object>> withdrawalsConfirm(Map<String,String> wdcParams) {
		ExecuteResult<Map<String, Object>> result = new ExecuteResult<Map<String,Object>>();
		result.setCode(SettlementConstants.SETTLEMENT_COUNT_SUCCESS);
		result.setResultMessage(SettlementConstants.SETTLEMENT_COUNT_SUCCESS_TEXT);
		RLock rLock = null;
		try{
			TradeSettlementWithdrawDTO dto = new TradeSettlementWithdrawDTO();
			dto.setOperateType("2");//提款
			dto.setTradeType("1");//外部供应商
			String settlementNo = wdcParams.get("settlementNo");
			RedissonClient redisson = redissonClientUtil.getInstance();
			rLock = redisson.getLock(ROCKTITTLE + settlementNo);
			rLock.lock();
			dto.setSettlementNo(settlementNo);
			String bindId = wdcParams.get("bindId");
			String accountNo = wdcParams.get("accountNo");
			String userId = wdcParams.get("userId");
			String amount = wdcParams.get("amount");
			String userIp = wdcParams.get("userIp");
			String message = null;
			
			List<String> statusList = new ArrayList<String>();
			statusList.add("EXECUTE_PROCESSING");
			statusList.add("SUCCESS");
			
			dto.setStatusList(statusList);
			List<TradeSettlementWithdrawDTO> list = tradeSettlementWithdrawDAO.queryTraSetWithdraw(dto);
			if(!CollectionUtils.isEmpty(list)){
				result.setCode(SettlementConstants.SETTLEMENT_COUNT_FAILED);
				result.setResultMessage(settlementNo + "已经提款处理中或已经提款完成，请刷新页面！");
				return result;
			}
			String merchOrderNo = settlementUtils.generateWithdrawalsNo("TK");
			dto.setTradeNo(merchOrderNo);
			dto.setCreateTime(new Date());
			tradeSettlementWithdrawDAO.addTraSetWithdraw(dto);
			
			Map<String,Object> map = new HashMap<String,Object>();
			Map<String,String> secYjfMap = getYjfCommonMap("withdraw");
			secYjfMap.put(YijipayConstants.NOTIFY_URL, paySDK.getNotify_url());
			secYjfMap.put("merchOrderNo" , merchOrderNo);
			secYjfMap.put("bindId",bindId);
			secYjfMap.put("userId",userId);
			secYjfMap.put("accountNo",accountNo);
			secYjfMap.put("amount",amount);
			secYjfMap.put("delay","T1");
			secYjfMap.put("userIp",userIp);
			logger.info("TradeSettlementServiceImpl-----withdraw-----params:"+secYjfMap);
			String witRetStr = YijifuGateway.getOpenApiClientService().doPost(paySDK.getUrl(), secYjfMap, paySDK.getKey());
			logger.info("TradeSettlementServiceImpl-----withdraw-----results:"+witRetStr);
	        JSONObject witJson = JSONObject.fromObject(witRetStr);
	        if("EXECUTE_PROCESSING".equals(witJson.get("resultCode"))){
	        	message = "success";
	        	Map<String,Object> params = new HashMap<String,Object>();
	    		params.put("settlementNo", settlementNo);
	    		params.put("status", SettlementStatusEnum.SETTLEMENT_STATUS_12.key());
	    		params.put("statusText", SettlementStatusEnum.SETTLEMENT_STATUS_12.value());
	    		updateTradeStatus(params);
	        }else {
	        	result.setCode(SettlementConstants.SETTLEMENT_COUNT_FAILED);
				result.setResultMessage(SettlementConstants.SETTLEMENT_COUNT_FAILED_TEXT);
	        	message = "提款失败";
	        }
	        dto.setModifyTime(new Date());
			dto.setStatus(witJson.get("resultCode").toString());
			dto.setContent(witJson.get("resultMessage").toString());
			tradeSettlementWithdrawDAO.updateTraSetWithdraw(dto);
	        map.put("message", message);
	        result.setResult(map);
		}catch(Exception e){
			logger.error("\n 方法[{}]，异常：[{}]", "TradeSettlementServiceImpl-withdrawalsConfirm", e.toString());
			result.setCode(SettlementConstants.SETTLEMENT_COUNT_FAILED);
			result.setResultMessage(SettlementConstants.SETTLEMENT_COUNT_FAILED_TEXT);
			result.addErrorMessage(e.toString());
			result.setResult(null);
		}finally{
			/** 释放锁资源 **/
			if (rLock != null) {
				rLock.unlock();
			}
		}
		return result;
	}

	public Map<String,String> getYjfCommonMap(String service){
		Map<String,String> map = new HashMap<String,String>();
		map.put(YijipayConstants.REQUEST_NO, Ids.oid());
		map.put(YijipayConstants.PARTNER_ID, paySDK.getPartenerId());
		map.put(YijipayConstants.PROTOCOL, "HTTP_FORM_JSON");
		map.put(YijipayConstants.SERVICE, service);
		map.put(YijipayConstants.SIGN_TYPE, "MD5");
		map.put(YijipayConstants.VERSION,"1.0");
		return map;
	}

	public static String getRandom() {
		String[] verifyString = new String[] { "0", "1", "2", "3", "4", "5",
				"6", "7", "8", "9" };
		Random random = new Random(System.currentTimeMillis());
		StringBuilder verifyBuilder = new StringBuilder();
		for (int i = 0; i < 20; i++) {
			int rd = random.nextInt(10);
			verifyBuilder.append(verifyString[rd]);
		}
		String randomCode = verifyBuilder.toString();
		logger.info("randomCode:" , randomCode);
		return randomCode;
	}

	@Override
	public TradeSettlementWithdrawDTO queryTraSetWithdraw(TradeSettlementWithdrawDTO dto) {
		List<TradeSettlementWithdrawDTO> list = tradeSettlementWithdrawDAO.queryTraSetWithdraw(dto);
		if(CollectionUtils.isEmpty(list)){
			return null; 
		}
		return list.get(0);
	}

	@Override
	public int updateTraSetWithdraw(TradeSettlementWithdrawDTO dto) {
		return tradeSettlementWithdrawDAO.updateTraSetWithdraw(dto);
	}
	
	/**
	 * 批量清分
	 * @return
	 */
	@Override
	public int batchDistribution(TradeSettlementWithdrawDTO dto , List<String> merchantOrderNoList , String totalCommissionAmount , String batchReturnUrl){
		logger.info("TradeSettlementServiceImpl-----batchDistribution-----begin");
		logger.info("TradeSettlementServiceImpl-----batchDistribution-----params : {dto:" + dto + ",merchantOrderNoList:"
				+merchantOrderNoList+",totalCommissionAmount:"+totalCommissionAmount+",batchReturnUrl"+batchReturnUrl+"}");
		String merchOrderNo = null;
		if(dto.getTradeNo() == null){
			merchOrderNo = settlementUtils.generateWithdrawalsNo("QF");
		}else{
			merchOrderNo = dto.getTradeNo();
		}
		String batchNo = getRandom();
		int result = 0;//0:OK -1:create failed -2:bind failed -3:finish failed

		if(dto.getTradeNo() == null){
			dto.setTradeNo(merchOrderNo);
			tradeSettlementWithdrawDAO.addTraSetWithdraw(dto);
		}
		Map<String,String> createMap = getYjfCommonMap("profitBatchCreate");
		createMap.put("batchNo", batchNo);
		createMap.put("allAmount", totalCommissionAmount);
		createMap.put("allNum", String.valueOf(merchantOrderNoList.size()));
		createMap.put("tradeMemo", "这是一个备注");
		createMap.put("merchOrderNo" , merchOrderNo);
		createMap.put(YijipayConstants.NOTIFY_URL , batchReturnUrl);
		logger.info("TradeSettlementServiceImpl-----profitBatchCreate-----params:"+createMap);
		String createStr = YijifuGateway.getOpenApiClientService().doPost(paySDK.getUrl(), createMap, paySDK.getKey());
		logger.info("TradeSettlementServiceImpl-----profitBatchCreate-----results:"+createStr);
		JSONObject creatJson = JSONObject.fromObject(createStr);
		if("EXECUTE_SUCCESS".equals(creatJson.get("resultCode"))){
			dto.setCreateTime(new Date());
			dto.setStatus(creatJson.get("batchStatus").toString());
			dto.setContent(creatJson.get("resultMessage").toString());
			tradeSettlementWithdrawDAO.updateTraSetWithdraw(dto);
			Map<String,String> bindMap = getYjfCommonMap("profitBatchBind");
			bindMap.put("batchNo" , batchNo);
			bindMap.put("merchOrderNo" , merchOrderNo);
			bindMap.put("merchantOrderNoList" , merchantOrderNoList.toString());
			logger.info("TradeSettlementServiceImpl-----profitBatchBind-----params:"+bindMap);
			String bindStr = YijifuGateway.getOpenApiClientService().doPost(paySDK.getUrl(), bindMap, paySDK.getKey());
			logger.info("TradeSettlementServiceImpl-----profitBatchBind-----results:"+bindStr);
			JSONObject bindJson = JSONObject.fromObject(bindStr);
			if("EXECUTE_SUCCESS".equals(bindJson.get("resultCode"))){
				dto.setModifyTime(new Date());
				dto.setStatus(bindJson.get("batchStatus").toString());
				dto.setContent(creatJson.get("resultMessage").toString());
				tradeSettlementWithdrawDAO.updateTraSetWithdraw(dto);
				Map<String,String> finishMap = getYjfCommonMap("profitBatchFinish");
				finishMap.put("batchNo" , batchNo);
				finishMap.put("merchOrderNo" , merchOrderNo);
				logger.info("TradeSettlementServiceImpl-----profitBatchFinish-----params:"+finishMap);
				String finishStr = YijifuGateway.getOpenApiClientService().doPost(paySDK.getUrl(), finishMap, paySDK.getKey());
				logger.info("TradeSettlementServiceImpl-----profitBatchFinish-----results:"+finishStr);
				JSONObject finishJson = JSONObject.fromObject(finishStr);
				if("EXECUTE_SUCCESS".equals(finishJson.get("resultCode"))){
					dto.setModifyTime(new Date());
//					dto.setStatus(finishJson.get("resultCode").toString());
					dto.setContent(creatJson.get("resultMessage").toString());
					tradeSettlementWithdrawDAO.updateTraSetWithdraw(dto);
				}else{
					result = -3;
				}
			}else{
				result = -2;
			}
		}else{
			result = -1;
		}
		logger.info("TradeSettlementServiceImpl-----batchDistribution-----end");
		return result;
	}

	@Override
	public int updateSetSuccess(TradeSettlementWithdrawDTO dto) {
		List<TradeSettlementWithdrawDTO> traSetWitDtos = tradeSettlementWithdrawDAO.queryTraSetWithdraw(dto);
		if(CollectionUtils.isEmpty(traSetWitDtos)){
			return 0;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		String settlementNo = traSetWitDtos.get(0).getSettlementNo();
		params.put("settlementNo", settlementNo);
		params.put("status", SettlementStatusEnum.SETTLEMENT_STATUS_13.key());
		params.put("statusText", SettlementStatusEnum.SETTLEMENT_STATUS_13.value());
		return updateTradeStatus(params);
	}
	
	@Override
	public int updateTradeStatus(TradeSettlementWithdrawDTO dto){
		List<TradeSettlementWithdrawDTO> traSetWitDtos = tradeSettlementWithdrawDAO.queryTraSetWithdraw(dto);
		if(CollectionUtils.isEmpty(traSetWitDtos)){
			return 0;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		String settlementNo = traSetWitDtos.get(0).getSettlementNo();
		params.put("settlementNo", settlementNo);
		params.put("status", dto.getStatus());
		params.put("statusText", dto.getStatusText());
		return updateTradeStatus(params);
	}

	@Override
	public void payOperation(TradeSettlementWithdrawDTO dto) {
		List<TradeSettlementWithdrawDTO> traSetWitDtos = tradeSettlementWithdrawDAO.queryTraSetWithdraw(dto);
		if(CollectionUtils.isEmpty(traSetWitDtos)){
			return;
		}
		TradeSettlementWithdrawDTO traSetWitDto = traSetWitDtos.get(0);
		List<String> list = new ArrayList<String>();
		//非erp
		list.add("2");
		list.add("3");
		traSetWitDto.setPayTypeList(list);
		if(traSetWitDto != null){
			balancePay(traSetWitDto);
			withdrawMoney(traSetWitDto);
		}
	}
	
	public MemberBaseInfoDTO getMemberBaseInfoDTO(String settlementNo){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("settlementNo", settlementNo);
		MemberBaseInfoDTO dto = null;
        Pager<TradeSettlementDetailDTO> pager = new Pager<TradeSettlementDetailDTO>();
		List<TradeSettlementDetailDTO> tradeSettlementDetailDTOList = tradeSettlementDetailDAO.queryTradeSettlementDetailsForPage(params, pager);
        if(!CollectionUtils.isEmpty(tradeSettlementDetailDTOList) && tradeSettlementDetailDTOList.get(0) != null) {
            ExecuteResult<MemberImportSuccInfoDTO> result = memberBaseInfoService.querySellerIdByCode(tradeSettlementDetailDTOList.get(0).getSellerCode());
            if(result != null && result.getResult() != null){
            	ExecuteResult<MemberDetailInfo> memberDetailInfoResult = memberBaseInfoService.getMemberDetailBySellerId(Long.parseLong(result.getResult().getMemberId()));
            	if(memberDetailInfoResult != null && memberDetailInfoResult.getResult() != null && memberDetailInfoResult.getResult().getMemberBaseInfoDTO() != null){
            		dto = memberDetailInfoResult.getResult().getMemberBaseInfoDTO();
            	}
            }
        }
        return dto;
	}
	
	public void balancePay(TradeSettlementWithdrawDTO dto){
		MemberBaseInfoDTO memberBaseInfoDTO = getMemberBaseInfoDTO(dto.getSettlementNo());
		if(memberBaseInfoDTO == null){
			return;
		}
		TradeSettlementConstDTO constDto = getSetConsByTypeAndKey(SettlementConstants.CONST_TYPE_ACCOUNTS_RECEIVABLE,"htdAccountNo");
		Map<String,Object> map = tradeSettlementDetailDAO.queryCountMoney(dto);
		Map<String,String> payMap = getYjfCommonMap("balancePay");
		String merchOrderNo = null;
		TraSetComOpeDTO tscDto = new TraSetComOpeDTO();
		tscDto.setOperateType("2");
		tscDto.setInterfaceType("2");
		tscDto.setSettlementNo(dto.getSettlementNo());
		List<TraSetComOpeDTO> list = traSetComOpeDAO.queryTraSetComOpeByParams(tscDto);
		if(CollectionUtils.isEmpty(list)){
			merchOrderNo = settlementUtils.generateWithdrawalsNo("BP");
			tscDto.setTradeNo(merchOrderNo);
			tscDto.setCreateTime(new Date());
			traSetComOpeDAO.addTraSetComOpe(tscDto);
		}else{
			merchOrderNo = list.get(0).getTradeNo();
		}
		payMap.put("tradeName", "平台公司支付佣金");
		payMap.put("payerUserId", memberBaseInfoDTO.getAccountNo());
		payMap.put("payerAccountNo", memberBaseInfoDTO.getAccountNo());
		payMap.put("amount", map.get("costandcomamount").toString());
//		payMap.put("payerUserId", "17030914401111100000");
//		payMap.put("payerAccountNo", "17030914401111100000");
//		payMap.put("amount", "10");
		payMap.put("sellerUserId", constDto.getConstValue());
		payMap.put("sellerAccountNo", constDto.getConstValue());
		payMap.put("userIp", "");
		payMap.put("tradeMemo", "这是一个备注");
		payMap.put("merchOrderNo", merchOrderNo);
		logger.info("TradeSettlementServiceImpl-----balancePay-----params:"+payMap);
		String payStr = YijifuGateway.getOpenApiClientService().doPost(paySDK.getUrl(), payMap, paySDK.getKey());
		logger.info("TradeSettlementServiceImpl-----balancePay-----params:"+payStr);
		JSONObject payJson = JSONObject.fromObject(payStr);
		if("EXECUTE_SUCCESS".equals(payJson.get("resultCode"))){
			tscDto.setStatus("1");
			tscDto.setModifyTime(new Date());
			tscDto.setContent(payJson.get("resultMessage").toString());
		}else{
			tscDto.setStatus("0");
			tscDto.setModifyTime(new Date());
			tscDto.setContent(payJson.get("resultMessage").toString());
		}
		tscDto.setModifyTime(new Date());
		traSetComOpeDAO.updateTraSetComOpe(tscDto);
	}

	public void withdrawMoney(TradeSettlementWithdrawDTO dto){
		MemberBaseInfoDTO memberBaseInfoDTO = getMemberBaseInfoDTO(dto.getSettlementNo());
		if(memberBaseInfoDTO == null){
			return;
		}
		Map<String,Object> map = tradeSettlementDetailDAO.queryCountMoney(dto);
		String merchOrderNo = null;
		TraSetComOpeDTO tscDto = new TraSetComOpeDTO();
		tscDto.setOperateType("2");
		tscDto.setInterfaceType("3");
		tscDto.setSettlementNo(dto.getSettlementNo());
		List<TraSetComOpeDTO> list = traSetComOpeDAO.queryTraSetComOpeByParams(tscDto);
		if(CollectionUtils.isEmpty(list)){
			merchOrderNo = settlementUtils.generateWithdrawalsNo("WM");
			tscDto.setCreateTime(new Date());
			tscDto.setTradeNo(merchOrderNo);
			traSetComOpeDAO.addTraSetComOpe(tscDto);
		}else{
			merchOrderNo = list.get(0).getTradeNo();
		}
		Map<String,String> notErpMap = getYjfCommonMap("withdrawMoney");
		notErpMap.put("pcUserId", memberBaseInfoDTO.getCompanyCode());
		notErpMap.put("costAmount", map.get("withdrawamount").toString());
//		notErpMap.put("costAmount", "10");
//		notErpMap.put("pcUserId", "0810");
		notErpMap.put("merchOrderNo" , merchOrderNo);
		logger.info("TradeSettlementServiceImpl-----withdrawMoney-----params:"+notErpMap);
		String notErpStr = YijifuGateway.getOpenApiClientService().doPost(paySDK.getUrl(), notErpMap, paySDK.getKey());
		logger.info("TradeSettlementServiceImpl-----withdrawMoney-----params:"+notErpStr);
		JSONObject notErpJson = JSONObject.fromObject(notErpStr);
		
		if("EXECUTE_SUCCESS".equals(notErpJson.get("resultCode"))){
			tscDto.setStatus("1");
			tscDto.setModifyTime(new Date());
			tscDto.setContent(notErpJson.get("resultMessage").toString());
		}else{
			tscDto.setStatus("0");
			tscDto.setModifyTime(new Date());
			tscDto.setContent(notErpJson.get("resultMessage").toString());
		}
		tscDto.setModifyTime(new Date());
		traSetComOpeDAO.updateTraSetComOpe(tscDto);
	}
	
	public void updateOrderStatus(TradeSettlementWithdrawDTO dto){
		Pager<TradeSettlementDetailDTO> pager = new Pager<TradeSettlementDetailDTO>();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("settlementNo", dto.getSettlementNo());
		List<TradeSettlementDetailDTO> tradeSettlementDetailDTOList = tradeSettlementDetailDAO.queryTradeSettlementDetailsForPage(params, pager);
		if(!CollectionUtils.isEmpty(tradeSettlementDetailDTOList)){
			for(TradeSettlementDetailDTO traSetDetDto : tradeSettlementDetailDTOList){
				TradeOrderItemDTO tradeOrderItemDTO = new TradeOrderItemDTO();
				tradeOrderItemDTO.setIsSettled(SettlementConstants.IS_SETTLED_1);
				tradeOrderItemDTO.setOrderItemNo(traSetDetDto.getOrderItemNo());
				tradeOrderItemDTO.setOrderNo(traSetDetDto.getOrderNo());
				tradeOrderItemDTO.setModifyId(dto.getModifyId());
				tradeOrderItemDTO.setModifyName(dto.getModifyName());
				tradeOrderItemDTO.setModifyTime(new Date());
				tradeOrderSettlementDAO.updateTradeOrderItem(tradeOrderItemDTO);
				tradeOrderItemDTO.setStatus("30");
				tradeOrderItemDTO.setStatusText("已结算");
				tradeOrderItemDTO.setCreateId(dto.getCreateId());
				tradeOrderItemDTO.setCreateName(dto.getCreateName());
				tradeOrderItemDTO.setCreateTime(new Date());
				tradeOrderSettlementDAO.addTradeOrderItemStatusHis(tradeOrderItemDTO);
			}
		}
	}

	@Override
	public void doSyncExternalTradeData(String tradeNo) throws Exception {
		if(StringUtils.isEmpty(tradeNo)){
    		return ;
		}
		TradeSettlementWithdrawDTO dto = new TradeSettlementWithdrawDTO();
		dto.setTradeNo(tradeNo);
		List<TradeSettlementWithdrawDTO> tsdList = tradeSettlementWithdrawDAO.queryTraSetWithdraw(dto);
		if(CollectionUtils.isEmpty(tsdList) || null == tsdList.get(0)){
			return ;
		}
		String settlementNo = tsdList.get(0).getSettlementNo();
		if(StringUtils.isEmpty(settlementNo)){
			return ;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("returnUrl", erpPay.getReturn_url());
		map.put("protocol", "HTTP_FORM_JSON");
		map.put("signType", "MD5");
		map.put("partnerId", erpPay.getPartenerId());
		map.put("requestNo", Ids.oid());
		map.put("service", "syncExternalTradeData");
		map.put("version", "1.0");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String,Object> mapData = getSyncParams(settlementNo);
		if(null != mapData){
			list.add(mapData);
			map.put("data", JSON.toJSONString(list));
			String signedStr = DigestUtil.digest(map, erpPay.getSignKey(), DigestUtil.DigestALGEnum.MD5);
			map.put("sign", signedStr);
			PostMethod method = null;
			HttpClient http = new HttpClient();
			method = new PostMethod(erpPay.getUrl());
			method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			logger.info("MessageId{}:结算信息传送到ERP入参{}", settlementNo ,JSON.toJSONString(map));
			NameValuePair[] data = mapToParameters(map);
			method.setRequestBody(data);
			int status = http.executeMethod(method);
			if(status == 200){//成功
				logger.info("结算信息传送erp成功");
			}else{
				logger.info("结算信息传送erp失败");
			}
			ObjectMapper mapper = new ObjectMapper();
			final Map<?, ?> m = mapper.readValue(method.getResponseBody(), Map.class);
			logger.info("MessageId{}:结算信息传送到ERP出参{}",settlementNo , JSON.toJSONString(m));
	        if(null != m){
	            if(Boolean.valueOf(m.get("success").toString())){
	            	logger.info("erp处理成功");
	            }else{
	            	logger.info("erp处理失败   "+m.get("resultMessage").toString()+","+ m.get("resultDetail").toString());
	            }
	        }
		}
	}

	public Map<String,Object> getSyncParams(String settlementNo){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("settlementNo", settlementNo);
		List<TradeSettlementDTO> dtos = tradeSettlementDAO.queryTradeSettlementsByParams(params);
		if(!CollectionUtils.isEmpty(dtos) && null != dtos.get(0)){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("tradeType", "0014");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			map.put("tradeDate", sdf.format(dtos.get(0).getModifyTime()));
			map.put("reservedField01", dtos.get(0).getSettlementNo());
			map.put("reservedField04", String.valueOf(dtos.get(0).getTotalSettlementAmount()));
			List<TradeSettlementDetailDTO> tdtos = tradeSettlementDetailDAO.queryTradeSettlementDetailsForPage(params, new Pager<TradeSettlementDetailDTO>());
			if(CollectionUtils.isEmpty(tdtos) || null == tdtos.get(0)){
				return null;
			}
			map.put("reservedField02", tdtos.get(0).getBuyerCode());
			
			ExecuteResult<String> result = memberBaseInfoService.IsHasInnerComapanyCert(tdtos.get(0).getSellerCode());
			if(!"success".equals(result.getResultMessage())){
				return null;
			}
			if("1".equals(result.getResult())){
				ExecuteResult<MemberBaseInfoDTO> result1 = memberBaseInfoService.getInnerInfoByOuterHTDCode(tdtos.get(0).getSellerCode());
				if(null == result1 ||null == result1.getResult()){
					return null;
				}
				map.put("reservedField03", result1.getResult().getMemberCode());
				map.put("reservedField05", "1");
			}else{
				map.put("reservedField03", tdtos.get(0).getSellerCode());
				map.put("reservedField05", "2");
			}
			return map;
		}
		return null;
	}
	
	public static NameValuePair[] mapToParameters(Map<String, Object> map)
	{
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (map != null) {
            for (Entry<String, ?> entry : map.entrySet()) {
                if (entry.getValue() == null) {
                    nvps.add(new NameValuePair(entry.getKey(), null));
                } else {
                    nvps.add(new NameValuePair(entry.getKey(), entry.getValue().toString()));
                }
            }
            
            NameValuePair[] result = new NameValuePair[nvps.size()];
            return nvps.toArray(result);
        }
		return null;
	}

}
