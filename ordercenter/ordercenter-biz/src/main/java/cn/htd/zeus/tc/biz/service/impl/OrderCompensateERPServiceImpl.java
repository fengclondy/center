package cn.htd.zeus.tc.biz.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSONObject;

import cn.htd.goodscenter.dto.stock.Order4StockChangeDTO;
import cn.htd.goodscenter.dto.stock.Order4StockEntryDTO;
import cn.htd.goodscenter.dto.stock.StockTypeEnum;
import cn.htd.zeus.tc.biz.dao.OrderCompensateERPDAO;
import cn.htd.zeus.tc.biz.dao.PayOrderInfoDAO;
import cn.htd.zeus.tc.biz.dao.TradeOrderErpDistributionDAO;
import cn.htd.zeus.tc.biz.dao.TradeOrderErpDistributionLogDAO;
import cn.htd.zeus.tc.biz.dao.TradeOrderItemsDAO;
import cn.htd.zeus.tc.biz.dao.TradeOrderItemsDiscountDAO;
import cn.htd.zeus.tc.biz.dao.TradeOrdersDAO;
import cn.htd.zeus.tc.biz.dmo.OrderCompensateERPCallBackDMO;
import cn.htd.zeus.tc.biz.dmo.OrderCompensateERPDMO;
import cn.htd.zeus.tc.biz.dmo.OrderQueryParamDMO;
import cn.htd.zeus.tc.biz.dmo.PayOrderInfoDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderErpDistributionDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderErpDistributionLogDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsDiscountDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsStatusHistoryDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsWarehouseDetailDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderStatusHistoryDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrdersDMO;
import cn.htd.zeus.tc.biz.rao.GoodsCenterRAO;
import cn.htd.zeus.tc.biz.service.OrderCompensateERPService;
import cn.htd.zeus.tc.biz.service.OrderStatusChangeCommonService;
import cn.htd.zeus.tc.biz.service.TradeOrderItemStatusHistoryService;
import cn.htd.zeus.tc.biz.service.TradeOrderStatusHistoryService;
import cn.htd.zeus.tc.common.constant.Constant;
import cn.htd.zeus.tc.common.enums.MiddleWareEnum;
import cn.htd.zeus.tc.common.enums.OrderStatusEnum;
import cn.htd.zeus.tc.common.enums.PayStatusEnum;
import cn.htd.zeus.tc.common.enums.PayTypeEnum;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.common.mq.MQQueueFactoryConfig;
import cn.htd.zeus.tc.common.util.DateUtil;
import cn.htd.zeus.tc.common.util.GenerateIdsUtil;
import cn.htd.zeus.tc.common.util.JSONUtil;
import cn.htd.zeus.tc.common.util.StringUtilHelper;
import cn.htd.zeus.tc.dto.OrderCompensateERPDTO;
import cn.htd.zeus.tc.dto.othercenter.response.OtherCenterResDTO;
import cn.htd.zeus.tc.dto.response.EmptyResDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCompensateERPCallBackReqDTO;

@Service
public class OrderCompensateERPServiceImpl implements OrderCompensateERPService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderCompensateERPServiceImpl.class);

	@Autowired
	private OrderCompensateERPDAO tradeOrderErpDistributionDao;

	@Autowired
	private TradeOrderErpDistributionDAO tradeOrderErpDistributionDAO;

	@Autowired
	private TradeOrderErpDistributionLogDAO tradeOrderErpDistributionLogDAO;

	@Autowired
	private TradeOrderItemsDiscountDAO tradeOrderItemsDiscountDAO;

	@Autowired
	private PayOrderInfoDAO payOrderInfoDAO;

	@Autowired
	private TradeOrdersDAO tradeOrdersDAO;

	@Autowired
	private TradeOrderItemsDAO tradeOrderItemsDAO;

	@Autowired
	private GoodsCenterRAO goodsCenterRAO;

	@Autowired
	private TradeOrderItemStatusHistoryService tradeOrderItemStatusHistoryService;

	@Autowired
	private TradeOrderStatusHistoryService tradeOrderStatusHistoryService;

	@Autowired
	private OrderStatusChangeCommonService orderStatusChangeCommonService;

	@Autowired
	private AmqpTemplate item_down_erp_template;

	@Autowired
	MQQueueFactoryConfig mqQueueFactoryConfig;

	private static final int IS_CHANGE_PRICE_YES = 1;// 是否议价 (1:是，0:否)

	private static final int zero = 0;

	DecimalFormat df1 = new DecimalFormat("0.0000");

	@Override
	public TradeOrderItemsDMO selectTradeOrderItemsInfo(TradeOrderItemsDMO tradeOrderItemsDMO) {
		return tradeOrderErpDistributionDao.selectTradeOrderItemsInfo(tradeOrderItemsDMO);
	}

	@Override
	public List<TradeOrderItemsWarehouseDetailDMO> selectTradeOrderItemsWarehouseDetailList(
			TradeOrderItemsWarehouseDetailDMO tradeOrderItemsWarehouseDetailDMO) {
		return tradeOrderErpDistributionDao
				.selectTradeOrderItemsWarehouseDetailList(tradeOrderItemsWarehouseDetailDMO);
	}

	/*
	 * 定时任务不需要返回,直接返回null
	 * 
	 * @param paramMap
	 * 
	 * @return null
	 */
	@Override
	public List<OrderCompensateERPDMO> selectErpDistributionOrdersList(Map paramMap) {
		return tradeOrderErpDistributionDao.selectErpDistributionOrdersList(paramMap);
	}

	/*
	 * mq调用五合一下行中间件订单补偿,
	 * 
	 * @param 订单
	 * 
	 * @return void
	 */
	@Override
	@Transactional
	public void compensateERP(OrderCompensateERPDMO[] tasks) {
		outterLoop: for (OrderCompensateERPDMO requestInfo : tasks) {
			OrderCompensateERPDTO orderCompensateERPDTO = new OrderCompensateERPDTO();
			JSONObject jsonObj = (JSONObject) JSONObject.toJSON(requestInfo);
			orderCompensateERPDTO = JSONObject.toJavaObject(jsonObj, OrderCompensateERPDTO.class);
			orderCompensateERPDTO.setOperateCode(Constant.OPERATE_CODE);
			orderCompensateERPDTO.setOperateName(Constant.OPERATER_NAME);
			orderCompensateERPDTO.setShippingMethod(requestInfo.getDeliveryType());
			// 五合一下行订单号 实际是erp锁定余额编号
			orderCompensateERPDTO.setOrderNo(requestInfo.getErpLockBalanceCode());
			orderCompensateERPDTO.setMasterOrderNo(requestInfo.getOrderNo());
			if (!StringUtilHelper.allIsNull(requestInfo.getBuyerRemarks(),
					requestInfo.getOrderRemarks())) {
				orderCompensateERPDTO.setOrderNote(
						requestInfo.getBuyerRemarks() + "|" + requestInfo.getOrderRemarks());
			}

			if (StringUtilHelper.isNotNull(orderCompensateERPDTO.getInvoiceType())) {
				if (orderCompensateERPDTO.getInvoiceType()
						.equals(MiddleWareEnum.MIDDLE_WARE_JD_INVOICETYPE_COMMON.getCode())) {
					orderCompensateERPDTO
							.setInvoiceType(MiddleWareEnum.ERP_INVOICETYPE_COMMON.getCode());
				} else if (orderCompensateERPDTO.getInvoiceType()
						.equals(MiddleWareEnum.MIDDLE_WARE_JD_INVOICETYPE_VAT.getCode())) {
					orderCompensateERPDTO
							.setInvoiceType(MiddleWareEnum.ERP_INVOICETYPE_VAT.getCode());
				}
			}

			if (requestInfo.getOrderFrom().equals(OrderStatusEnum.ORDER_FROM_VMS.getCode())) {
				orderCompensateERPDTO.setDepartmentCode(requestInfo.getSalesDepartmentCode());
			}

			if (StringUtilHelper.isNotNull(requestInfo.getOrderItemNos(),
					requestInfo.getOrderNo())) {
				if (StringUtilHelper.isNotNull(requestInfo.getOrderFrom()) && !requestInfo
						.getOrderFrom().equals(OrderStatusEnum.ORDER_FROM_VMS.getCode())) {
					// 校验收付款的收付款下行状态是不是为2,如果不为2 continue
					PayOrderInfoDMO record = new PayOrderInfoDMO();
					record.setOrderNo(requestInfo.getOrderNo());
					PayOrderInfoDMO payOrderInfoDMO = payOrderInfoDAO
							.selectPayOrderByOrderNo(record);
					if (null != payOrderInfoDMO
							&& StringUtilHelper.isNotNull(payOrderInfoDMO.getPayType())
							&& payOrderInfoDMO.getPayType().toString()
									.equals(PayStatusEnum.ERP_PAY.getCode())) {
						if (StringUtilHelper.isNull(payOrderInfoDMO.getPayStatus())
								|| !payOrderInfoDMO.getPayStatus()
										.equals(PayTypeEnum.SUCCESS.getCode())) {
							LOGGER.warn("五合一下行-没有从收付款待下行信息表里查到数据或者收付款支付不是SUCCESS,入参orderNo:{}"
									, requestInfo.getOrderNo());
							continue;
						}
					} else if (null == payOrderInfoDMO
							|| StringUtilHelper.isNull(payOrderInfoDMO.getPayResultStatus())
							|| !payOrderInfoDMO.getPayResultStatus().toString()
									.equals(PayTypeEnum.PAY_RESULT_STATUS_SUCC.getCode())) {
						LOGGER.warn("五合一下行-没有从收付款待下行信息表里查到数据或者收付款下行状态不是回调成功,入参orderNo:{}"
								, requestInfo.getOrderNo());
						continue;
					}
				}

				// 二级节点-商品明细 List
				List<Map<String, Object>> orderDetailList = new ArrayList<Map<String, Object>>();
				orderCompensateERPDTO.setOrderDetail(orderDetailList);

				// 二级节点-使用返利 List
				List<Map<String, Object>> rebateDetailList = new ArrayList<Map<String, Object>>();
				orderCompensateERPDTO.setRebateDetail(rebateDetailList);

				Map<String, Object> VMSRebateMap = new HashMap<String, Object>();

				// 销售金额（总）
				BigDecimal payMent = new BigDecimal(0);
				// 返利金额（总）
				BigDecimal rebateAmountTotal = new BigDecimal(0);

				String[] orderItemNoArray = requestInfo.getOrderItemNos().split(",");// 订单行集合
				for (int i = 0; i < orderItemNoArray.length; i++) {
					// 查询订单行信息表
					TradeOrderItemsDMO tradeOrderItemsDMO = new TradeOrderItemsDMO();
					tradeOrderItemsDMO.setOrderNo(requestInfo.getOrderNo());
					tradeOrderItemsDMO.setOrderItemNo(orderItemNoArray[i]);
					TradeOrderItemsDMO tradeOrderItemsInfo = tradeOrderErpDistributionDao
							.selectTradeOrderItemsInfo(tradeOrderItemsDMO);

					// 查询订单行拆单明细信息表
					TradeOrderItemsWarehouseDetailDMO tradeOrderItemsWarehouseDetailDMO = new TradeOrderItemsWarehouseDetailDMO();
					tradeOrderItemsWarehouseDetailDMO.setOrderNo(requestInfo.getOrderNo());
					tradeOrderItemsWarehouseDetailDMO.setOrderItemNo(orderItemNoArray[i]);
					List<TradeOrderItemsWarehouseDetailDMO> tradeOrderItemsWarehouseDetailList = tradeOrderErpDistributionDao
							.selectTradeOrderItemsWarehouseDetailList(
									tradeOrderItemsWarehouseDetailDMO);

					BigDecimal rebateAll = new BigDecimal(0);
					if (null != tradeOrderItemsWarehouseDetailList
							&& tradeOrderItemsWarehouseDetailList.size() > 0) {
						for (int j = 0; j < tradeOrderItemsWarehouseDetailList.size(); j++) {
							// 组装商品明细Map
							HashMap<String, Object> orderDetailMap = new HashMap<String, Object>();

							orderDetailMap.put("orderFrom", requestInfo.getSourceId());
							orderDetailMap.put("warehouseCode",
									tradeOrderItemsWarehouseDetailList.get(j).getWarehouseCode());
							orderDetailMap.put("productCode", tradeOrderItemsInfo.getItemSpuCode());

							orderDetailMap.put("productNum", tradeOrderItemsWarehouseDetailList
									.get(j).getGoodsCount().toString());// 有默认值,无需判空
							int isChangePrice = tradeOrderItemsInfo.getIsChangePrice();// 是否议价
																						// (1:是，0:否)
							if (IS_CHANGE_PRICE_YES == isChangePrice) {
								orderDetailMap.put("price",
										df1.format(tradeOrderItemsInfo.getBargainingGoodsPrice()));// 有默认值,无需判空
							} else {
								orderDetailMap.put("price",
										df1.format(tradeOrderItemsInfo.getGoodsPrice()));// 有默认值,无需判空
							}

							// 使用返利金额（默认0） 求百分比,精确到两位，四舍五入
							if (zero != tradeOrderItemsInfo.getGoodsCount()) {
								BigDecimal warehouseGoodsCount = new BigDecimal(
										tradeOrderItemsWarehouseDetailList.get(j).getGoodsCount());
								BigDecimal itemsGoodsCount = new BigDecimal(
										tradeOrderItemsInfo.getGoodsCount());
								LOGGER.info("warehouseGoodsCount:" + warehouseGoodsCount
										+ " itemsGoodsCount:" + itemsGoodsCount
										+ " tradeOrderItemsInfo.getUsedRebateAmount():"
										+ new BigDecimal(df1.format(
												tradeOrderItemsInfo.getTotalDiscountAmount())));

								if (j == tradeOrderItemsWarehouseDetailList.size() - 1) {
									BigDecimal rebateTemp = new BigDecimal(df1
											.format(tradeOrderItemsInfo.getTotalDiscountAmount()))
													.subtract(rebateAll);
									orderDetailMap.put("rebate", rebateTemp);
								} else {
									BigDecimal rebate = warehouseGoodsCount
											.divide(itemsGoodsCount, 8, BigDecimal.ROUND_HALF_UP)
											.multiply(new BigDecimal(df1.format(
													tradeOrderItemsInfo.getTotalDiscountAmount())))
											.setScale(8, BigDecimal.ROUND_HALF_UP);
									rebateAll = rebateAll.add(rebate);
									orderDetailMap.put("rebate", df1.format(rebate));
								}
							} else {
								orderDetailMap.put("rebate", "0");
							}
							BigDecimal rebate = new BigDecimal(
									orderDetailMap.get("rebate").toString());
							// 销售金额
							BigDecimal amount = new BigDecimal(
									tradeOrderItemsWarehouseDetailList.get(j).getGoodsCount())
											.multiply(new BigDecimal(
													orderDetailMap.get("price").toString()));
							orderDetailMap.put("payment", df1.format(amount.subtract(rebate)));

							payMent = payMent.add(amount).subtract(rebate);// 销售金额（总）
							orderDetailMap.put("productAttribute",
									tradeOrderItemsWarehouseDetailList.get(j)
											.getProductAttribute());// 商品属性
							orderDetailMap.put("supplierCode",
									tradeOrderItemsWarehouseDetailList.get(j).getSupplierCode());// 供货商
							orderDetailMap.put("departmentCode", tradeOrderItemsWarehouseDetailList
									.get(j).getPurchaseDepartmentCode());// 采购部门
							orderDetailMap.put("agreementNum", "");// 采购协议-数据库里没有
							orderDetailList.add(orderDetailMap);
						}
					} else {
						LOGGER.warn("trade_order_items_warehouse_detail表没有查到数据,条件:{}"
								, JSONObject.toJSONString(tradeOrderItemsWarehouseDetailDMO));// TODO
						continue outterLoop;
					}

					// tradeOrderItemsInfo.getHasUsedCoupon() 为1
					// 说明使用了优惠券，tradeOrderItemsInfo.getErpRebateNo()不为空说明使用了返利，不能叠加使用
					if (tradeOrderItemsInfo.getHasUsedCoupon().intValue() == Integer
							.valueOf(OrderStatusEnum.HAS_USED_COUPON.getCode()).intValue()
							|| StringUtilHelper.isNotNull(tradeOrderItemsInfo.getErpRebateNo())) {
						HashMap<String, Object> rebateDetailMap = new HashMap<String, Object>();
						rebateAmountTotal = rebateAmountTotal.add(new BigDecimal(
								df1.format(tradeOrderItemsInfo.getTotalDiscountAmount())));// 返利金额（总）
						if (StringUtilHelper.isNull(tradeOrderItemsInfo.getErpRebateNo())) {// 说明用了优惠券
							rebateDetailMap.put("rebateNo", tradeOrderItemsInfo.getErpRebateNo());// 返利单号为''时生成返利单
							rebateDetailMap.put("rebateAmount",
									df1.format(tradeOrderItemsInfo.getTotalDiscountAmount()));// 返利金额(数据库有默认值,不要判空)
							rebateDetailMap.put("categoryCode",
									tradeOrderItemsInfo.getErpFirstCategoryCode());// 商品分类代码
																					// 返利单号为''时不能为空
							rebateDetailMap.put("brandCode",
									tradeOrderItemsInfo.getBrandId().toString());// 品牌代码
																					// 返利单号为''时不能为空(数据库有默认值,不要判空)
							rebateDetailMap.put("saleType", requestInfo.getSaleType());
							if ("".equals(tradeOrderItemsInfo.getErpRebateNo())) {// 数据库默认''
								rebateDetailMap.put("rebateCode", "0022");
								rebateDetailMap.put("rebateWay", "01");// 返利方式-数据库返利单号为空时候传01
							} else {
								rebateDetailMap.put("rebateWay", "");// 返利方式-数据库返利单号不为空时候传空
								rebateDetailMap.put("rebateCode",
										tradeOrderItemsInfo.getErpRebateCode());
							}

							if (StringUtilHelper.isNotNull(requestInfo.getIsTimelimitedOrder())
									&& OrderStatusEnum.IS_TIMELIMITED_ORDER.getCode().equals(
											requestInfo.getIsTimelimitedOrder().toString())) {
								rebateDetailMap.put("rebateIdentifier",
										requestInfo.getPromotionId());// 红包唯一标识符
							} else {
								String orderNo = requestInfo.getOrderNo();
								List<TradeOrderItemsDiscountDMO> itemDiscount = tradeOrderItemsDiscountDAO
										.selectBuyerCouponCodeByOrderNo(orderNo);
								if (null != itemDiscount && itemDiscount.size() > 0) {
									rebateDetailMap.put("rebateIdentifier",
											itemDiscount.get(0).getPromotionId());// 红包唯一标识符
									if (StringUtilHelper
											.isNull(itemDiscount.get(0).getPromotionId())) {
										LOGGER.warn("中台根据orderNo:{}查优惠信息表,没有查到促销活动编码",orderNo);
										continue outterLoop;
									}
								} else {
									LOGGER.warn("中台根据orderNo:{}查优惠信息表,没有查到数据",orderNo);
									rebateDetailMap.put("rebateIdentifier", "");
									continue outterLoop;
								}
							}

							if (StringUtilHelper.isNotNull(requestInfo.getSourceId())) {
								if ("1".equals(requestInfo.getSourceId())) {
									rebateDetailMap.put("sourceId", "1");// 商城
								} else if ("2".equals(requestInfo.getSourceId())) {
									rebateDetailMap.put("sourceId", "0");// vms
								}
							}
							// 使用优惠券
							rebateDetailList.add(rebateDetailMap);

						} else {// 说明是vms返利-只需要传这两个字段就行了
							if (VMSRebateMap.containsKey("rebateNo")) {
								String rebateAmount = df1.format(
										new BigDecimal(VMSRebateMap.get("rebateAmount").toString())
												.add(tradeOrderItemsInfo.getTotalDiscountAmount()));
								VMSRebateMap.put("rebateAmount", rebateAmount);
								VMSRebateMap.put("rebateNo", tradeOrderItemsInfo.getErpRebateNo());
							} else {
								VMSRebateMap.put("rebateAmount",
										df1.format(tradeOrderItemsInfo.getTotalDiscountAmount()));
								VMSRebateMap.put("rebateNo", tradeOrderItemsInfo.getErpRebateNo());
							}
						}
					}

				}
				// 使用返利
				if (!VMSRebateMap.isEmpty()) {
					rebateDetailList.add(VMSRebateMap);
				}

				orderCompensateERPDTO.setRebateAmountTotal(df1.format(rebateAmountTotal));
				orderCompensateERPDTO.setPaymentTotal(df1.format(payMent));
				String issueLogId = GenerateIdsUtil.generateId(GenerateIdsUtil.getHostIp());
				orderCompensateERPDTO.setIssueLogId(issueLogId);
				if (orderCompensateERPDTO.getSourceId()
						.equals(OrderStatusEnum.ORDER_FROM_VMS.getCode())) {
					orderCompensateERPDTO.setSourceId(OrderStatusEnum.ORDER_FROM_ERP_VMS.getCode());
				} else {
					orderCompensateERPDTO.setSourceId(OrderStatusEnum.ORDER_FROM_MALL.getCode());
				}
				// update 订单分销单信息表
				TradeOrderErpDistributionDMO tradeOrderErpDistributionDMO = new TradeOrderErpDistributionDMO();
				tradeOrderErpDistributionDMO.setId(requestInfo.getId());
				updateTradeOrderErpDistributionByPrimaryKey(tradeOrderErpDistributionDMO);
				LOGGER.info("MessageId:{} 订单中心往五合一接口-发送日志流水issueLogId:{}" , issueLogId,issueLogId);
				LOGGER.info("MessageId:{} 五合一接口-准备往中间件发送MQ信息为:{}" ,issueLogId, JSONUtil.toJSONString(orderCompensateERPDTO));
				item_down_erp_template.convertAndSend(
						mqQueueFactoryConfig.getOrdercenterMiddlewareCompensate(),
						JSONUtil.toJSONString(orderCompensateERPDTO));
				LOGGER.info("MessageId:{} 成功发送mq----五合一队列名:{}",issueLogId
						, mqQueueFactoryConfig.getOrdercenterMiddlewareCompensate());
				// 直接更新订单表
				changeOrderStatus(requestInfo.getOrderNo(), requestInfo.getOrderItemNos(),
						OrderStatusEnum.PAYED_SPLITED_ORDER_PRE_ERP.getCode(),
						OrderStatusEnum.PAYED_SPLITED_ORDER_PRE_ERP.getMsg(), false, "", "");
			} else {
				LOGGER.warn(
						"从表trade_order_erp_distribution查出的订单行号(orderItemNos):{} orderNo:{}",
						requestInfo.getOrderItemNos(), requestInfo.getOrderNo());
			}

		}
	}

	private void changeOrderStatus(final String orderNo, final String orderItems,
			final String status, final String statusText, final boolean directUpdateOrder,
			final String orderErrorStatus, final String orderErrorReason) {
		try {
			new Thread(new Runnable() {
				public void run() {
					orderStatusChangeCommonService.orderStatusChange(orderNo, orderItems, status,
							statusText, directUpdateOrder, orderErrorStatus, orderErrorReason);
					// 如果有其他品牌品类的订单状态更新成40，这时候总订单状态应该更新成33
					Map<String, String> ordersItemMap = new HashMap<String, String>();
					ordersItemMap.put("orderNo", orderNo);
					ordersItemMap.put("isCancelOrderItem", "0");
					long ordersItemCount = tradeOrderItemsDAO
							.selectTradeOrderItemsByOrderNoOrStatus(ordersItemMap);
					if (ordersItemCount != zero) {
						Map<String, String> ordersItemStatusMap = new HashMap<String, String>();
						ordersItemStatusMap.put("orderNo", orderNo);
						ordersItemStatusMap.put("isCancelOrderItem", "0");
						ordersItemStatusMap.put("orderItemStatus", status);
						long ordersItemStatusCount = tradeOrderItemsDAO
								.selectTradeOrderItemsByOrderNoOrStatus(ordersItemStatusMap);

						Map<String, String> ordersItemPreDeliveryStatusMap = new HashMap<String, String>();
						ordersItemPreDeliveryStatusMap.put("orderNo", orderNo);
						ordersItemPreDeliveryStatusMap.put("isCancelOrderItem", "0");
						ordersItemPreDeliveryStatusMap.put("orderItemStatus",
								OrderStatusEnum.PRE_DELIVERY.getCode());
						long ordersItemPreDeliveryStatusCount = tradeOrderItemsDAO
								.selectTradeOrderItemsByOrderNoOrStatus(
										ordersItemPreDeliveryStatusMap);

						if (ordersItemStatusCount > 0 && ordersItemPreDeliveryStatusCount > 0
								&& ordersItemCount == ordersItemStatusCount
										+ ordersItemPreDeliveryStatusCount) {
							orderStatusChangeCommonService.updateOrder(status, orderNo, statusText,
									orderErrorStatus, orderErrorReason, false);
						}
					}
				}
			}).start();
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.warn("五合一下行 -- 修改订单和订单行表装填出现异常-(此异常不需要回滚)");
		}
	}

	/*
	 * 提供给中间件回调的方法
	 * 
	 * @param callBackInDTO
	 * 
	 * @return OrderCompensateERPCallBackOutDTO
	 */
	@Override
	@Transactional
	public OrderCompensateERPCallBackDMO executeOrderCompensateERPCallBack(
			OrderCompensateERPCallBackReqDTO orderCompensateERPCallBackReqDTO) {
		String messageId = GenerateIdsUtil.generateId(GenerateIdsUtil.getHostIp());
		LOGGER.info("MessageId:{} 五合一下行回调开始{}", messageId,
				JSONObject.toJSONString(orderCompensateERPCallBackReqDTO));
		OrderCompensateERPCallBackDMO orderCompensateERPCallBackDMO = new OrderCompensateERPCallBackDMO();
		orderCompensateERPCallBackDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
		orderCompensateERPCallBackDMO.setResultMsg(ResultCodeEnum.SUCCESS.getMsg());
		try {
			TradeOrderErpDistributionDMO tradeOrderErpDistributionDMO = new TradeOrderErpDistributionDMO();
			tradeOrderErpDistributionDMO
					.setErpLockBalanceCode(orderCompensateERPCallBackReqDTO.getOrderCode());
			tradeOrderErpDistributionDMO
					.setErpFirstCategoryCode(orderCompensateERPCallBackReqDTO.getCategroyCode());
			tradeOrderErpDistributionDMO
					.setBrandId(Long.parseLong(orderCompensateERPCallBackReqDTO.getBrandCode()));

			TradeOrderErpDistributionDMO selectTradeOrderErpDistributionDMO = tradeOrderErpDistributionDAO
					.selectOrderItemNosBySelective(tradeOrderErpDistributionDMO);
			LOGGER.info("MessageId:{} 五合一下行回调--查询分销单表结果:{}", messageId,
					JSONObject.toJSONString(selectTradeOrderErpDistributionDMO));
			if (null == selectTradeOrderErpDistributionDMO
					|| "".equals(selectTradeOrderErpDistributionDMO.getOrderNo())) {
				LOGGER.info("MessageId:{} 中间件回调订单中心时候,没有查到订单分销单信息表的数据-入参:{}",messageId
						, JSONObject.toJSONString(tradeOrderErpDistributionDMO));
				orderCompensateERPCallBackDMO.setResultCode(
						ResultCodeEnum.ORDERCALLBACK_QUERY_ERP_DISTRIBUTION_IS_NULL.getCode());
				orderCompensateERPCallBackDMO.setResultMsg(
						ResultCodeEnum.ORDERCALLBACK_QUERY_ERP_DISTRIBUTION_IS_NULL.getMsg());
				return orderCompensateERPCallBackDMO;
			}
			// 回调成功更新订单行表-待发货(订单行表全部是待发货-就更新订单表成待发货),失败时更新订单表和订单行表成异常订单
			TradeOrdersDMO tradeOrdersDMO = new TradeOrdersDMO();
			tradeOrdersDMO.setOrderNo(selectTradeOrderErpDistributionDMO.getOrderNo());
			TradeOrderItemsDMO tradeOrderItemsDMO = new TradeOrderItemsDMO();
			tradeOrderItemsDMO.setModifyTime(DateUtil.getSystemTime());
			tradeOrderItemsDMO.setModifyId(Long.parseLong(Constant.OPERATE_CODE));
			tradeOrderItemsDMO.setModifyName(Constant.OPERATER_NAME);
			tradeOrderItemsDMO.setErpDistributionId(selectTradeOrderErpDistributionDMO.getId());

			if (orderCompensateERPCallBackReqDTO.getResult()
					.equals(MiddleWareEnum.SUCCESS_ONE.getCode())) {// 1：成功
				tradeOrderErpDistributionDMO.setErpSholesalerCode(
						orderCompensateERPCallBackReqDTO.getJL_SholesalerCode());
				tradeOrderErpDistributionDMO.setErpWholesalePaymentCode(
						orderCompensateERPCallBackReqDTO.getJL_WholesalePayment());
				tradeOrderErpDistributionDMO.setErpCreateRecordCode(
						orderCompensateERPCallBackReqDTO.getJL_CreateRecordCode());
				tradeOrderErpDistributionDMO
						.setErpCompanyCode(orderCompensateERPCallBackReqDTO.getJL_ComPanyCode());
				tradeOrderErpDistributionDMO.setModifyTime(DateUtil.getSystemTime());
				tradeOrderErpDistributionDMO.setModifyId(Long.parseLong(Constant.OPERATE_CODE));
				tradeOrderErpDistributionDMO.setModifyName(Constant.OPERATER_NAME);
				tradeOrderErpDistributionDMO.setErpStatus(OrderStatusEnum.ERP_SUCCESS.getCode());
				tradeOrderErpDistributionDAO
						.updateTradeOrderErpDistribution(tradeOrderErpDistributionDMO);

				tradeOrderItemsDMO.setErpSholesalerCode(
						orderCompensateERPCallBackReqDTO.getJL_SholesalerCode());
				tradeOrderItemsDMO.setOrderItemStatus(OrderStatusEnum.PRE_DELIVERY.getCode());
				tradeOrderItemsDMO.setOrderItemErrorReason("");
				tradeOrderItemsDMO.setOrderItemErrorStatus("");

				// 扣减内部供应商商品库存
				String orderNo = selectTradeOrderErpDistributionDMO.getOrderNo();
				String orderItemNos = selectTradeOrderErpDistributionDMO.getOrderItemNos();
				// 查询订单行信息表
				OrderQueryParamDMO orderQueryParamDMO = new OrderQueryParamDMO();
				orderQueryParamDMO.setOrderNo(orderNo);
				orderQueryParamDMO.setOrderItemNoList(Arrays.asList(orderItemNos.split(",")));
				LOGGER.info("MessageId:{} 五合一回调成功,从分销单表里查询出的订单行集合为：{},订单号:{}", messageId,
						orderItemNos, orderNo);
				TradeOrdersDMO tradeOrdersDMOTemp = tradeOrdersDAO
						.selectOrderByOrderNoANDOrderItemNo(orderQueryParamDMO);
				LOGGER.info("MessageId:{} 五合一回调成功，判断是不是要扣减库存查询结果tradeOrdersDMOTemp:{}", messageId,
						JSONObject.toJSONString(tradeOrdersDMOTemp));
				if (null != tradeOrdersDMOTemp && null != tradeOrdersDMOTemp.getIsTimelimitedOrder()
						&& !OrderStatusEnum.IS_TIMELIMITED_ORDER.getCode()
								.equals(tradeOrdersDMOTemp.getIsTimelimitedOrder().toString())) {
					LOGGER.info("MessageId:{} 五合一回调成功，扣减非秒杀商品 isTimelimitedOrder:{}", messageId,
							tradeOrdersDMOTemp.getIsTimelimitedOrder());
					EmptyResDTO goodsResDTO = batchReduceStock(tradeOrdersDMOTemp, messageId);
					if (!goodsResDTO.getResponseCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
						orderCompensateERPCallBackDMO.setResultMsg(goodsResDTO.getReponseMsg());
						orderCompensateERPCallBackDMO.setResultCode(goodsResDTO.getResponseCode());
						return orderCompensateERPCallBackDMO;
					}
				}

			} else {

				tradeOrderErpDistributionDMO.setModifyTime(DateUtil.getSystemTime());
				tradeOrderErpDistributionDMO.setModifyId(Long.parseLong(Constant.OPERATE_CODE));
				tradeOrderErpDistributionDMO.setModifyName(Constant.OPERATER_NAME);
				tradeOrderErpDistributionDMO.setErpStatus(OrderStatusEnum.ERP_FAIL.getCode());
				tradeOrderErpDistributionDAO
						.updateTradeOrderErpDistribution(tradeOrderErpDistributionDMO);

				TradeOrderErpDistributionLogDMO tradeOrderErpDistributionLogDMO = new TradeOrderErpDistributionLogDMO();
				tradeOrderErpDistributionLogDMO
						.setErpDistributionId(selectTradeOrderErpDistributionDMO.getId());
				tradeOrderErpDistributionLogDMO
						.setErpErrorMsg(orderCompensateERPCallBackReqDTO.getErrormessage());
				tradeOrderErpDistributionLogDMO.setModifyId(Long.parseLong(Constant.OPERATE_CODE));
				tradeOrderErpDistributionLogDMO.setModifyName(Constant.OPERATER_NAME);
				tradeOrderErpDistributionLogDMO.setModifyTime(DateUtil.getSystemTime());
				long updateErpDistributionLog = tradeOrderErpDistributionLogDAO
						.updateErpDistributionLogByPrimaryKey(tradeOrderErpDistributionLogDMO);
				if (updateErpDistributionLog == 0) {// 插入
					tradeOrderErpDistributionLogDMO
							.setCreateId(Long.parseLong(Constant.OPERATE_CODE));
					tradeOrderErpDistributionLogDMO.setCreateName(Constant.OPERATER_NAME);
					tradeOrderErpDistributionLogDMO.setCreateTime(DateUtil.getSystemTime());
					tradeOrderErpDistributionLogDAO
							.insertErpDistributionLog(tradeOrderErpDistributionLogDMO);
				}

				handleOrderAndItemParam(tradeOrderItemsDMO, tradeOrdersDMO,
						orderCompensateERPCallBackReqDTO);
			}
			// 更新订单表和订单行表
			handleOrderAndItem(selectTradeOrderErpDistributionDMO, tradeOrderItemsDMO,
					selectTradeOrderErpDistributionDMO.getOrderNo());

		} catch (Exception e) {
			orderCompensateERPCallBackDMO.setResultCode(ResultCodeEnum.ERROR.getCode());
			orderCompensateERPCallBackDMO.setResultMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(w.toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		return orderCompensateERPCallBackDMO;
	}

	/*
	 * 五合一回调-更新订单表和订单行
	 */
	private void handleOrderAndItem(TradeOrderErpDistributionDMO selectTradeOrderErpDistributionDMO,
			TradeOrderItemsDMO tradeOrderItemsDMO, String orderNo) {
		// 更新订单行表-满足订单行表的状态全部是待发货这个条件更新订单表
		String orderItemNos = selectTradeOrderErpDistributionDMO.getOrderItemNos();
		if (StringUtilHelper.isNotNull(orderItemNos)) {
			String[] orderItemNosArray = orderItemNos.split(",");
			for (int i = 0; i < orderItemNosArray.length; i++) {
				tradeOrderItemsDMO.setOrderItemNo(orderItemNosArray[i]);
				tradeOrderItemsDAO.updateTradeOrderItemsByItemNo(tradeOrderItemsDMO);
				if (OrderStatusEnum.PRE_DELIVERY.getCode()
						.equals(tradeOrderItemsDMO.getOrderItemStatus())) {
					// 插入订单行履历表
					TradeOrderItemsStatusHistoryDMO record = new TradeOrderItemsStatusHistoryDMO();
					record.setOrderItemNo(orderItemNosArray[i]);
					record.setOrderItemStatus(OrderStatusEnum.PRE_DELIVERY.getCode());
					record.setOrderItemStatusText(OrderStatusEnum.PRE_DELIVERY.getMsg());
					tradeOrderItemStatusHistoryService.insertTradeOrderItemStatusHistory(record);
				}
			}

			Map<String, String> ordersItemMap = new HashMap<String, String>();
			ordersItemMap.put("orderNo", orderNo);
			ordersItemMap.put("isCancelOrderItem", "0");
			long ordersItemCount = tradeOrderItemsDAO
					.selectTradeOrderItemsByOrderNoOrStatus(ordersItemMap);
			if (ordersItemCount != zero) {
				Map<String, String> ordersItemStatusMap = new HashMap<String, String>();
				ordersItemStatusMap.put("orderNo", orderNo);
				ordersItemStatusMap.put("orderItemStatus", OrderStatusEnum.PRE_DELIVERY.getCode());
				ordersItemStatusMap.put("isCancelOrderItem", "0");
				long ordersItemStatusCount = tradeOrderItemsDAO
						.selectTradeOrderItemsByOrderNoOrStatus(ordersItemStatusMap);
				if (ordersItemCount == ordersItemStatusCount) {
					// TODO 更新订单表为待发货
					TradeOrdersDMO tradeOrdersDMO = new TradeOrdersDMO();
					tradeOrdersDMO.setOrderStatus(OrderStatusEnum.PRE_DELIVERY.getCode());
					tradeOrdersDMO.setOrderNo(orderNo);
					tradeOrdersDMO.setModifyTime(DateUtil.getSystemTime());
					tradeOrdersDMO.setModifyId(Long.parseLong(Constant.OPERATE_CODE));
					tradeOrdersDMO.setModifyName(Constant.OPERATER_NAME);
					tradeOrdersDMO.setOrderErrorStatus("");
					tradeOrdersDMO.setOrderErrorReason("");

					TradeOrdersDMO querTradeOrders = tradeOrdersDAO.selectOrderByOrderNo(orderNo);
					if (null != querTradeOrders
							&& StringUtilHelper.isNotNull(querTradeOrders.getOrderFrom())
							&& OrderStatusEnum.ORDER_FROM_VMS.getCode()
									.equals(querTradeOrders.getOrderFrom())) {
						tradeOrdersDMO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
						tradeOrdersDMO.setPayOrderTime(DateUtil.getSystemTime());
					}

					tradeOrdersDAO.updateTradeOrdersByOrderNo(tradeOrdersDMO);

					// 插入订单行履历表
					TradeOrderStatusHistoryDMO record = new TradeOrderStatusHistoryDMO();
					record.setOrderNo(orderNo);
					record.setOrderStatus(OrderStatusEnum.PRE_DELIVERY.getCode());
					record.setOrderStatusText(OrderStatusEnum.PRE_DELIVERY.getMsg());
					tradeOrderStatusHistoryService.insertTradeOrderStatusHistory(record);
				}
			}
		} else {
			LOGGER.warn("五合一回调的时候没有从订单分销单信息表查到关联订单行号");
		}
	}

	/*
	 * 五合一回调-入参失败的时候组装订单和订单行表参数
	 */
	private void handleOrderAndItemParam(TradeOrderItemsDMO tradeOrderItemsDMO,
			TradeOrdersDMO tradeOrdersDMO,
			OrderCompensateERPCallBackReqDTO orderCompensateERPCallBackReqDTO) {
		// 如果是VMS开单下行失败订单总订单状态改为“待支付”，子订单状态改为“待支付-VMS开单待下行ERP”
		TradeOrdersDMO tradeOrdersRes = tradeOrdersDAO
				.selectOrderByOrderNo(tradeOrdersDMO.getOrderNo());
		if (StringUtilHelper.isNotNull(tradeOrdersRes.getOrderFrom())
				&& tradeOrdersRes.getOrderFrom().equals(OrderStatusEnum.ORDER_FROM_VMS.getCode())) {
			tradeOrderItemsDMO.setOrderItemStatus(OrderStatusEnum.VMS_ORDER_PRE_DOWN_ERP.getCode());
			tradeOrdersDMO.setOrderStatus(OrderStatusEnum.PRE_PAY.getCode());
		}
		tradeOrderItemsDMO.setOrderItemErrorStatus(OrderStatusEnum.ERP_EXECUTE__ERROR.getCode());
		tradeOrderItemsDMO.setOrderItemErrorTime(DateUtil.getSystemTime());
		tradeOrderItemsDMO
				.setOrderItemErrorReason(orderCompensateERPCallBackReqDTO.getErrormessage());

		tradeOrdersDMO.setOrderErrorStatus(OrderStatusEnum.ERP_EXECUTE__ERROR.getCode());
		tradeOrdersDMO.setOrderErrorTime(DateUtil.getSystemTime());
		tradeOrdersDMO.setOrderErrorReason(orderCompensateERPCallBackReqDTO.getErrormessage());
		tradeOrdersDAO.updateTradeOrdersByOrderNo(tradeOrdersDMO);
	}

	@Override
	public int updateTradeOrderErpDistributionByPrimaryKey(
			TradeOrderErpDistributionDMO tradeOrderErpDistributionDMO) {
		tradeOrderErpDistributionDMO.setErpDownTime(DateUtil.getSystemTime());
		tradeOrderErpDistributionDMO.setModifyTime(DateUtil.getSystemTime());
		tradeOrderErpDistributionDMO.setModifyId(Long.parseLong(Constant.OPERATE_CODE));
		tradeOrderErpDistributionDMO.setModifyName(Constant.OPERATER_NAME);
		tradeOrderErpDistributionDMO.setErpStatus(OrderStatusEnum.ERP_DOWN_LINK.getCode());
		int update = tradeOrderErpDistributionDAO
				.updateTradeOrderErpDistributionByPrimaryKey(tradeOrderErpDistributionDMO);
		LOGGER.info("更新trade_order_erp_distribution 数据库结果:{}" , update);
		return update;
	}

	/*
	 * 调商品中心批量扣减库存
	 */
	private EmptyResDTO batchReduceStock(final TradeOrdersDMO record, final String messageId) {
		EmptyResDTO emptyResDTO = new EmptyResDTO();
		try {
			List<Order4StockChangeDTO> order4StockChangeDTOs = new ArrayList<Order4StockChangeDTO>();
			Order4StockChangeDTO order4StockChangeDTO = new Order4StockChangeDTO();
			order4StockChangeDTO.setOrderNo(record.getOrderNo());
			order4StockChangeDTO.setOrderResource(record.getOrderFrom());
			order4StockChangeDTO.setMessageId(messageId);
			List<Order4StockEntryDTO> orderEntries = new ArrayList<Order4StockEntryDTO>();

			List<TradeOrderItemsDMO> itemsList = record.getOrderItemsList();
			for (TradeOrderItemsDMO itemsDMO : itemsList) {
				Order4StockEntryDTO order4StockEntryDTO = new Order4StockEntryDTO();
				order4StockEntryDTO.setSkuCode(itemsDMO.getSkuCode());
				order4StockEntryDTO.setIsBoxFlag(itemsDMO.getIsBoxFlag());
				order4StockEntryDTO.setQuantity(itemsDMO.getGoodsCount());
				Integer isChangePrice = itemsDMO.getIsChangePrice();
				Integer bargainingGoodsCount = itemsDMO.getBargainingGoodsCount();
				if (isChangePrice == 1 && bargainingGoodsCount != 0) {
					order4StockEntryDTO.setQuantity(bargainingGoodsCount);
				}
				order4StockEntryDTO.setStockTypeEnum(StockTypeEnum.REDUCE);
				orderEntries.add(order4StockEntryDTO);
			}
			order4StockChangeDTO.setOrderEntries(orderEntries);
			order4StockChangeDTOs.add(order4StockChangeDTO);

			LOGGER.info("MessageId:{} 【五合一回调】【调商品中心批量扣减库存开始】--组装查询参数开始:{}",messageId,
					JSONObject.toJSONString(order4StockChangeDTOs));
			OtherCenterResDTO<String> result = goodsCenterRAO
					.batchReduceStock(order4StockChangeDTOs, messageId);

			LOGGER.info("MessageId:{} 【五合一回调】【调商品中心批量扣减库存结束】--结果:{}", messageId,JSONObject.toJSONString(result));
			if (!result.getOtherCenterResponseCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
				emptyResDTO.setReponseMsg(result.getOtherCenterResponseMsg());
				emptyResDTO.setResponseCode(result.getOtherCenterResponseCode());
				return emptyResDTO;
			}
			emptyResDTO.setReponseMsg(ResultCodeEnum.SUCCESS.getMsg());
			emptyResDTO.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
		} catch (Exception e) {
			emptyResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			emptyResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.warn("MessageId:{} 调商品中心批量扣减库存失败",messageId);
		}

		return emptyResDTO;
	}
}
