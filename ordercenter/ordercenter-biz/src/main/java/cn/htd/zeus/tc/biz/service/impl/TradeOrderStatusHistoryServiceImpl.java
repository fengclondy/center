package cn.htd.zeus.tc.biz.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.htd.common.ExecuteResult;
import cn.htd.membercenter.dto.MemberCompanyInfoDTO;
import cn.htd.membercenter.service.MemberBaseInfoService;
import cn.htd.zeus.tc.biz.dao.OrderPaymentResultDAO;
import cn.htd.zeus.tc.biz.dao.TradeOrderItemsDAO;
import cn.htd.zeus.tc.biz.dao.TradeOrderItemsStatusHistoryDAO;
import cn.htd.zeus.tc.biz.dao.TradeOrderStatusHistoryDAO;
import cn.htd.zeus.tc.biz.dmo.OrderPaymentResultDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsStatusHistoryDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsStatusHistoryListDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderStatusHistoryDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderStatusHistoryListDMO;
import cn.htd.zeus.tc.biz.service.TradeOrderItemStatusHistoryService;
import cn.htd.zeus.tc.biz.service.TradeOrderStatusHistoryService;
import cn.htd.zeus.tc.common.enums.MemberCenterEnum;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.common.util.DateUtil;
import cn.htd.zeus.tc.dto.response.UpdateOrderStatusResDTO;
import cn.htd.zeus.tc.dto.resquest.TradeOrderStatusHistoryReqDTO;
import cn.htd.zeus.tc.dto.resquest.UpdateOrderStatusReqDTO;

/**
 * 订单状态履历Service实现类 Copyright (C), 2013-2016, 汇通达网络有限公司 FileName:
 * TradeOrderStatusHistoryServiceImpl.java Author: jiaop Date: 2016-8-25
 * 下午4:47:39 Description: //模块目的、功能描述 History: //修改记录
 * <author> <time> <version> <desc> 修改人姓名 修改时间 版本号 描述
 */
@Service
public class TradeOrderStatusHistoryServiceImpl implements TradeOrderStatusHistoryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TradeOrderStatusHistoryServiceImpl.class);

	@Autowired
	private TradeOrderStatusHistoryDAO tradeOrderStatusHistoryDAO;

	@Autowired
	private TradeOrderItemsStatusHistoryDAO orderItemsStatusHistoryDAO;

	@Autowired
	private OrderPaymentResultDAO orderPaymentResultDAO;

	@Autowired
	private MemberBaseInfoService memberBaseInfoService;

	@Autowired
	private TradeOrderItemsDAO tradeOrderItemsDAO;

	@Autowired
	private TradeOrderItemStatusHistoryService tradeOrderItemStatusHistoryService;

	private static final int ONE = 1;// 常量1

	private static final int ZREO = 0;// 常量0

	@Override
	public TradeOrderStatusHistoryListDMO selectHistoryByOrderNo(TradeOrderStatusHistoryReqDTO record) {

		TradeOrderStatusHistoryListDMO tradeOrderStatusHistoryListDMO = new TradeOrderStatusHistoryListDMO();
		try {
			TradeOrderStatusHistoryDMO tradeOrderStatusHistory = new TradeOrderStatusHistoryDMO();
			tradeOrderStatusHistory.setOrderNo(record.getOrderNo());
			List<TradeOrderStatusHistoryDMO> orderStatusDMOList = tradeOrderStatusHistoryDAO
					.selectHistoryByOrderNo(tradeOrderStatusHistory);
			if (orderStatusDMOList != null) {
				tradeOrderStatusHistoryListDMO.setHistoryList(orderStatusDMOList);
			}
			tradeOrderStatusHistoryListDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
			tradeOrderStatusHistoryListDMO.setResultMsg(ResultCodeEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			tradeOrderStatusHistoryListDMO.setResultCode(ResultCodeEnum.ERROR.getCode());
			tradeOrderStatusHistoryListDMO.setResultMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法TradeOrderStatusHistoryServiceImpl.selectByOrderNoAndStatus出现异常{}",
					record.getMessageId(), w.toString());
		}

		return tradeOrderStatusHistoryListDMO;
	}

	@Override
	public TradeOrderItemsStatusHistoryListDMO selectOrderItemsHistoryByOrderNo(TradeOrderStatusHistoryReqDTO record) {

		TradeOrderItemsStatusHistoryListDMO tradeOrderItemsStatusHistoryListDMO = new TradeOrderItemsStatusHistoryListDMO();
		try {
			List<TradeOrderItemsStatusHistoryDMO> orderItemsHistoryList = orderItemsStatusHistoryDAO
					.selectItemsHistoryByOrderNo(record.getOrderNo());
			if (orderItemsHistoryList != null) {
				tradeOrderItemsStatusHistoryListDMO.setOrderItemsHistoryList(orderItemsHistoryList);
			}
			tradeOrderItemsStatusHistoryListDMO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
			tradeOrderItemsStatusHistoryListDMO.setResultMsg(ResultCodeEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			tradeOrderItemsStatusHistoryListDMO.setResultCode(ResultCodeEnum.ERROR.getCode());
			tradeOrderItemsStatusHistoryListDMO.setResultMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法TradeOrderStatusHistoryServiceImpl.selectByOrderNoAndStatus出现异常{}",
					record.getMessageId(), w.toString());
		}

		return tradeOrderItemsStatusHistoryListDMO;
	}

	@Override
	public void insertTradeOrderStatusHistory(TradeOrderStatusHistoryDMO record) {
		int orderStatusCount = tradeOrderStatusHistoryDAO.selectCountByOrderNoAndOrderStatus(record);
		if (orderStatusCount < ONE) {
			record.setCreateTime(DateUtil.getSystemTime());
			int count = tradeOrderStatusHistoryDAO.insertSelective(record);
			if (count < ONE) {
				LOGGER.info("插入订单状态履历表失败");
			} else {
				LOGGER.info("插入订单状态履历表成功");
			}
			LOGGER.info("【插入订单状态履历结束】--结果:{}",count);
		}
	}

	@Override
	public UpdateOrderStatusResDTO updateOrderStatus(UpdateOrderStatusReqDTO record) {

		UpdateOrderStatusResDTO updateOrderStatusResDTO = new UpdateOrderStatusResDTO();
		try {
			OrderPaymentResultDMO orderPaymentResultDMO = new OrderPaymentResultDMO();
			orderPaymentResultDMO.setOrderStatus(record.getOrderStatus());
			orderPaymentResultDMO.setOrderNo(record.getOrderNo());
			orderPaymentResultDMO.setOrderStatusText(record.getOrderStatusText());
			orderPaymentResultDMO.setOrderReceiptTime(DateUtil.getSystemTime());
			orderPaymentResultDMO.setModifyTime(DateUtil.getSystemTime());
			// 会员信息
			MemberCompanyInfoDTO memberDTO = new MemberCompanyInfoDTO();
			if (record.getBuyerCode() != null) {
				MemberCompanyInfoDTO memberCompanyInfoDTO = new MemberCompanyInfoDTO();
				memberCompanyInfoDTO.setBuyerSellerType(Integer.parseInt(MemberCenterEnum.MEMBER_TYPE_BUYER.getCode()));
				memberCompanyInfoDTO.setMemberCode(record.getBuyerCode());
				ExecuteResult<MemberCompanyInfoDTO> result = memberBaseInfoService
						.selectMobilePhoneMemberId(memberCompanyInfoDTO);
				if (result.getResult() != null) {
					memberDTO = result.getResult();
				} else {
					updateOrderStatusResDTO.setReponseMsg(ResultCodeEnum.ORDERSETTLEMENT_MEMBER_INFO_NULL.getMsg());
					updateOrderStatusResDTO.setResponseCode(ResultCodeEnum.ORDERSETTLEMENT_MEMBER_INFO_NULL.getCode());
					return updateOrderStatusResDTO;
				}
			} else {
				memberDTO.setMemberId(new Long(1001));
				memberDTO.setCompanyName("system");
			}
			int resultCount = orderPaymentResultDAO.updateOrderStatusByTradeNo(orderPaymentResultDMO);
			if (resultCount > ZREO) {

				// 新增订单状态履历表
				insertTradeOrderStatusHistory(orderPaymentResultDMO, memberDTO);
				// 更新订单行状态
				tradeOrderItemsDAO.updateOrderItemStatus4OrderExpire(orderPaymentResultDMO);
				// 根据订单号查询订单行号
				List<TradeOrderItemsDMO> itemsList = tradeOrderItemsDAO
						.selectOrderItemsByOrderNo(orderPaymentResultDMO.getOrderNo());
				// 新增订单行状态履历表
				if (itemsList.size() > ZREO) {
					for (TradeOrderItemsDMO tradeOrderItemsDMO : itemsList) {
						tradeOrderItemsDMO.setOrderItemStatus(record.getOrderStatus());
						tradeOrderItemsDMO.setOrderItemStatusText(record.getOrderStatusText());
						insertTradeOrderItemsStatusHistory(tradeOrderItemsDMO, memberDTO);
					}
				}
			} else {
				updateOrderStatusResDTO.setReponseMsg(ResultCodeEnum.ORDERSTATUS_ORDER_STATUS_UPDATE_FAIL.getMsg());
				updateOrderStatusResDTO
						.setResponseCode(ResultCodeEnum.ORDERSTATUS_ORDER_STATUS_UPDATE_FAIL.getCode());
				return updateOrderStatusResDTO;
			}

			updateOrderStatusResDTO.setReponseMsg(ResultCodeEnum.SUCCESS.getMsg());
			updateOrderStatusResDTO.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
		} catch (Exception e) {
			updateOrderStatusResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			updateOrderStatusResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法TradeOrderStatusHistoryServiceImpl.updateOrderStatus出现异常{}",
					record.getMessageId(), w.toString());
			return updateOrderStatusResDTO;
		}
		return updateOrderStatusResDTO;
	}

	/*
	 * 插入订单状态履历
	 */
	private void insertTradeOrderStatusHistory(final OrderPaymentResultDMO orderPaymentResultDMO,
			final MemberCompanyInfoDTO memberDTO) {
		try {
			new Thread(new Runnable() {
				public void run() {
					TradeOrderStatusHistoryDMO record = new TradeOrderStatusHistoryDMO();
					record.setOrderNo(orderPaymentResultDMO.getOrderNo());
					record.setOrderStatus(orderPaymentResultDMO.getOrderStatus());
					record.setOrderStatusText(orderPaymentResultDMO.getOrderStatusText());
					record.setCreateId(memberDTO.getMemberId());
					record.setCreateName(memberDTO.getCompanyName());
					insertTradeOrderStatusHistory(record);
				}
			}).start();
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.warn("插入订单状态履历表出现异常-(此异常不需要回滚)");
		}
	}

	/*
	 * 插入订单行状态履历
	 */
	private void insertTradeOrderItemsStatusHistory(final TradeOrderItemsDMO tradeOrderItemsDMO,
			final MemberCompanyInfoDTO memberDTO) {
		try {
			new Thread(new Runnable() {
				public void run() {
					TradeOrderItemsStatusHistoryDMO record = new TradeOrderItemsStatusHistoryDMO();
					record.setOrderItemNo(tradeOrderItemsDMO.getOrderItemNo());
					record.setOrderItemStatus(tradeOrderItemsDMO.getOrderItemStatus());
					record.setOrderItemStatusText(tradeOrderItemsDMO.getOrderItemStatusText());
					record.setCreateId(memberDTO.getMemberId());
					record.setCreateName(memberDTO.getCompanyName());
					tradeOrderItemStatusHistoryService.insertTradeOrderItemStatusHistory(record);
				}
			}).start();
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.warn("插入订单行状态履历表出现异常-(此异常不需要回滚)");
		}
	}

}
