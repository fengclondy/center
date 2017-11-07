package cn.htd.zeus.tc.biz.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.htd.zeus.tc.biz.dao.PayOrderInfoDAO;
import cn.htd.zeus.tc.biz.dao.TradeOrderItemsDAO;
import cn.htd.zeus.tc.biz.dmo.PayOrderInfoDMO;
import cn.htd.zeus.tc.biz.dmo.RechargeOrderDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsDMO;
import cn.htd.zeus.tc.biz.service.OrderStatusChangeCommonService;
import cn.htd.zeus.tc.biz.service.PostStrikeaBalanceService;
import cn.htd.zeus.tc.biz.service.RechargeOrderService;
import cn.htd.zeus.tc.common.enums.MiddleWareEnum;
import cn.htd.zeus.tc.common.enums.OrderStatusEnum;
import cn.htd.zeus.tc.common.mq.MQQueueFactoryConfig;
import cn.htd.zeus.tc.common.util.GenerateIdsUtil;
import cn.htd.zeus.tc.common.util.JSONUtil;
import cn.htd.zeus.tc.common.util.StringUtilHelper;
import cn.htd.zeus.tc.dto.resquest.CollectPaymentInfoReqDTO;
import cn.htd.zeus.tc.dto.resquest.PostStrikeaBalanceReqDTO;
import cn.htd.zeus.tc.dto.resquest.RechargeOrderQueryReqDTO;

import com.alibaba.fastjson.JSONObject;

/**
 * 收付款中间件
 * @author jiaop
 *
 */
@Service
public class PostStrikeaBalanceServiceImpl implements PostStrikeaBalanceService{

	private static final Logger LOGGER = LoggerFactory.getLogger(PostStrikeaBalanceServiceImpl.class);

	@Autowired
	private AmqpTemplate postStrikeaBalancer_template;

	@Autowired
	MQQueueFactoryConfig mqQueueFactoryConfig;

	@Autowired
	private PayOrderInfoDAO payOrderInfoDAO;
	
	@Autowired
	private TradeOrderItemsDAO tradeOrderItemsDAO;

	@Autowired
	private OrderStatusChangeCommonService orderStatusChangeCommonService;

	@Autowired
	private RechargeOrderService rechargeOrderService;
	
	DecimalFormat df1 = new DecimalFormat("0.0000");

	@Override
	public void postStrikeaBalance(PostStrikeaBalanceReqDTO postStrikeaBalanceReqDTO) {
		// TODO Auto-generated method stub
		LOGGER.info("收付款-准备往中间件发送MQ信息为:{}",JSONUtil.toJSONString(postStrikeaBalanceReqDTO));
		postStrikeaBalancer_template.convertAndSend(mqQueueFactoryConfig.getMiddlewarePostStrikeaBalance(),JSONUtil.toJSONString(postStrikeaBalanceReqDTO));
        LOGGER.info("成功发送mq");
	}

	@Override
	public List<PayOrderInfoDMO> selectPayOrderFromPayOrderInfo(Map paramMap) {

		return payOrderInfoDAO.selectPayOrderFromPayOrderInfo(paramMap);
	}

	/*
	 * 收付款下行方法
	 * @see cn.htd.zeus.tc.biz.service.PostStrikeaBalanceService#executeDownPostStrikeaBalance(cn.htd.zeus.tc.biz.dmo.PayOrderInfoDMO[])
	 */
	@Override
	public void executeDownPostStrikeaBalance(PayOrderInfoDMO[] tasks) {
		for(PayOrderInfoDMO requestInfo : tasks){
			PostStrikeaBalanceReqDTO postStrikeaBalanceReqDTO = new PostStrikeaBalanceReqDTO();
			List<Map<String,Object>> productDetail = new ArrayList<Map<String,Object>>();
			if(null != requestInfo){
				/*String messageId =  GenerateIdsUtil.generateId(GenerateIdsUtil.getHostIp());
				LOGGER.info("收付款下行生成日志流水issueLogId:"+messageId);*/
				JSONObject jsonObj = (JSONObject)JSONObject.toJSON(requestInfo);
				postStrikeaBalanceReqDTO = JSONObject.toJavaObject(jsonObj, PostStrikeaBalanceReqDTO.class);
				List<CollectPaymentInfoReqDTO> detail = new ArrayList<CollectPaymentInfoReqDTO>();
				postStrikeaBalanceReqDTO.setDetail(detail);

				CollectPaymentInfoReqDTO mapPlus = new CollectPaymentInfoReqDTO();
				mapPlus.setPayCode(MiddleWareEnum.PAY_CODE_PLUS.getCode());
				mapPlus.setDepartmentCode(requestInfo.getDepartmentCode());
				mapPlus.setAmount(requestInfo.getAmount());
				mapPlus.setBrandCode(requestInfo.getBrandCode());
				mapPlus.setClassCode(requestInfo.getClassCode());

				CollectPaymentInfoReqDTO mapReduce = new CollectPaymentInfoReqDTO();
				mapReduce.setPayCode(MiddleWareEnum.PAY_CODE_REDUCE.getCode());
				mapReduce.setDepartmentCode(requestInfo.getDepartmentCode());
				mapReduce.setAmount(new BigDecimal(df1.format(requestInfo.getAmount().multiply(new BigDecimal(-1)))));
				mapReduce.setBrandCode(requestInfo.getBrandCode());
				mapReduce.setClassCode(requestInfo.getClassCode());
				detail.add(mapPlus);
				detail.add(mapReduce);

				if((StringUtilHelper.isNotNull(requestInfo.getOrderFrom()) && requestInfo.getOrderFrom().equals(OrderStatusEnum.ORDER_FROM_VMS.getCode()))
						|| (StringUtilHelper.isNotNull(requestInfo.getProductCode()) && requestInfo.getProductCode().equals(MiddleWareEnum.PRODUCTCODE_ERP_EXTERNAL_SUPPLIER.getCode()))
						|| (requestInfo.getOrderType().toString().equals(MiddleWareEnum.RECHARGE_ORDER_TYPE.getCode()))){
					postStrikeaBalanceReqDTO.setIsLockBalanceFlag(Integer.valueOf(MiddleWareEnum.NOT_LOCK_BALANCE_FLAG.getCode()));
				}else{
					postStrikeaBalanceReqDTO.setIsLockBalanceFlag(Integer.valueOf(MiddleWareEnum.LOCK_BALANCE_FLAG.getCode()));
				}
				if(requestInfo.getOrderType().toString().equals(MiddleWareEnum.RECHARGE_ORDER_TYPE.getCode())){
					// 查询充值订单记录
					RechargeOrderQueryReqDTO rechargeOrderQueryReqDTO = new RechargeOrderQueryReqDTO();
					rechargeOrderQueryReqDTO.setOrderNo(requestInfo.getOrderNo());
					RechargeOrderDMO result = rechargeOrderService.selectRechargeOrderByOrderNo(rechargeOrderQueryReqDTO);
					LOGGER.info("充值订单号为:{}",requestInfo.getOrderNo());
					// 如果充值记录存在并且充值渠道是2：超级老板则记录9002
					if(result != null && "2".equals(result.getRechargeChannelCode())){
						postStrikeaBalanceReqDTO.setProductCode(MiddleWareEnum.SUPERBOSS_RECHARGE.getCode());
					}else if(result != null && "3".equals(result.getRechargeChannelCode())){
						postStrikeaBalanceReqDTO.setProductCode(MiddleWareEnum.POS_RECHARGE.getCode());
					}else{
						postStrikeaBalanceReqDTO.setProductCode(MiddleWareEnum.PRODUCTCODE_RECHARGE.getCode());
					}
				}else{
					postStrikeaBalanceReqDTO.setProductCode(requestInfo.getProductCode());
				}
				LOGGER.info("充值通道为:{}",postStrikeaBalanceReqDTO.getProductCode());
				postStrikeaBalanceReqDTO.setOperaterCode(MiddleWareEnum.OPERATE_CODE.getCode());
				postStrikeaBalanceReqDTO.setOperaterName(MiddleWareEnum.OPERATER_NAME.getCode());
				postStrikeaBalanceReqDTO.setSaleman(MiddleWareEnum.SALES_MAN.getCode());
				postStrikeaBalanceReqDTO.setSalemanCode(MiddleWareEnum.SALE_ASSISTANT_CODE.getCode());
				postStrikeaBalanceReqDTO.setRechargeOrderNo(requestInfo.getDownOrderNo());
				postStrikeaBalance(postStrikeaBalanceReqDTO);
				updateByRechargeOrderNoLockNo(requestInfo);
				//不直接更新订单表
		        changeOrderStatus(requestInfo, OrderStatusEnum.PAYED_POST_STRIKEA_DOWNING.getCode(), 
		        		OrderStatusEnum.PAYED_POST_STRIKEA_DOWNING.getMsg(),false,"","");

			}
		}
	}
	
	/*
	 * 收付款下行按照品牌品类更新
	 */
	private void changeOrderStatus(final PayOrderInfoDMO requestInfo,final String status,final String statusText,final boolean directUpdateOrder,
			final String orderErrorStatus,final String orderErrorReason){
		try{
			new Thread(new Runnable(){
				public void run(){
					TradeOrderItemsDMO tradeOrderItemsDMO = new TradeOrderItemsDMO();
					
					String orderNo = requestInfo.getOrderNo();
					String brandCode = "";
					String classCode = "";
					//如果不是京东商品,就是内部供应商商品
					if(!brandCode.equals(MiddleWareEnum.JD_BRAND_ID_ERP.getCode())){
						brandCode = requestInfo.getBrandCode();
						classCode = requestInfo.getClassCode();
						
						tradeOrderItemsDMO.setBrandId(Long.parseLong(brandCode));
						tradeOrderItemsDMO.setErpFirstCategoryCode(classCode);
					}
					tradeOrderItemsDMO.setOrderNo(orderNo);
					TradeOrderItemsDMO tradeOrderItemsDMORes = tradeOrderItemsDAO.selectOrderItemNoByBrandCodeClassCode(tradeOrderItemsDMO);
					String orderItems = tradeOrderItemsDMORes.getOrderItemNos();
					
					orderStatusChangeCommonService.orderStatusChange(orderNo, orderItems, status, statusText,directUpdateOrder,orderErrorStatus,
							orderErrorReason);
				}
			}).start();
		}catch(Exception e){
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.warn("收付款下行 -- 修改订单和订单行表装填出现异常-(此异常不需要回滚)");
		}
	}

	private void updateByRechargeOrderNoLockNo(final PayOrderInfoDMO payOrderInfoDMO){
		try{
			new Thread(new Runnable(){
				public void run(){
					PayOrderInfoDMO payOrderInfoDMO4Update = new PayOrderInfoDMO();
					payOrderInfoDMO4Update.setOrderNo(payOrderInfoDMO.getOrderNo());
					payOrderInfoDMO4Update.setDownOrderNo(payOrderInfoDMO.getDownOrderNo());
					LOGGER.info("收付款下行---更新TB_B_PAYORDERINFO入参:{}",JSONObject.toJSONString(payOrderInfoDMO4Update));
					int update = payOrderInfoDAO.updateByRechargeOrderNoLockNo(payOrderInfoDMO4Update);
					LOGGER.info("收付款下行---更新TB_B_PAYORDERINFO结果:{}",update);
						}
		}).start();
		}catch(Exception e){
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 收付款下行---更新TB_B_PAYORDERINFO出现异常","",w.toString());
		}
	}

}
