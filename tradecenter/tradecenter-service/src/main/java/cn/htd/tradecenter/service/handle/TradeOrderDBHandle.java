package cn.htd.tradecenter.service.handle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSONObject;

import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.middleware.MiddlewareInterfaceConstant;
import cn.htd.common.middleware.MiddlewareInterfaceUtil;
import cn.htd.common.util.DateUtils;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.tradecenter.common.constant.ReturnCodeConst;
import cn.htd.tradecenter.common.enums.YesNoEnum;
import cn.htd.tradecenter.common.exception.TradeCenterBusinessException;
import cn.htd.tradecenter.common.utils.MiddleWare;
import cn.htd.tradecenter.dao.JDOrderInfoDAO;
import cn.htd.tradecenter.dao.TradeOrderErpDistributionDAO;
import cn.htd.tradecenter.dao.TradeOrderItemsDAO;
import cn.htd.tradecenter.dao.TradeOrderItemsDiscountDAO;
import cn.htd.tradecenter.dao.TradeOrderItemsPriceHistoryDAO;
import cn.htd.tradecenter.dao.TradeOrderItemsStatusHistoryDAO;
import cn.htd.tradecenter.dao.TradeOrderItemsWarehouseDetailDAO;
import cn.htd.tradecenter.dao.TradeOrderPayInfoDAO;
import cn.htd.tradecenter.dao.TradeOrderStatusHistoryDAO;
import cn.htd.tradecenter.dao.TradeOrdersDAO;
import cn.htd.tradecenter.domain.JDOrderInfo;
import cn.htd.tradecenter.dto.RedoErpWorkDTO;
import cn.htd.tradecenter.dto.TradeOrderErpDistributionDTO;
import cn.htd.tradecenter.dto.TradeOrderItemsDTO;
import cn.htd.tradecenter.dto.TradeOrderItemsDiscountDTO;
import cn.htd.tradecenter.dto.TradeOrderItemsPriceHistoryDTO;
import cn.htd.tradecenter.dto.TradeOrderItemsStatusHistoryDTO;
import cn.htd.tradecenter.dto.TradeOrderItemsWarehouseDetailDTO;
import cn.htd.tradecenter.dto.TradeOrderPayInfoDTO;
import cn.htd.tradecenter.dto.TradeOrderStatusHistoryDTO;
import cn.htd.tradecenter.dto.TradeOrdersDTO;

@Service("tradeOrderDBHandle")
public class TradeOrderDBHandle {

    @javax.annotation.Resource
    private DictionaryUtils dictionary;

    @javax.annotation.Resource
    private TradeOrdersDAO orderDAO;

    @javax.annotation.Resource
    private TradeOrderItemsDAO orderItemsDAO;

    @javax.annotation.Resource
    private TradeOrderItemsWarehouseDetailDAO orderItemsWarehouseDAO;

    @javax.annotation.Resource
    private TradeOrderErpDistributionDAO erpDistributionDAO;

    @javax.annotation.Resource
    private TradeOrderStatusHistoryDAO orderStatusHistoryDAO;

    @javax.annotation.Resource
    private TradeOrderItemsStatusHistoryDAO orderItemsStatusHistoryDAO;

    @Resource
    private TradeOrderItemsDiscountDAO orderItemsDiscountDAO;

    @Resource
    private TradeOrderItemsPriceHistoryDAO orderItemsPriceHistoryDAO;

    @Resource
    private TradeOrderPayInfoDAO orderPayInfoDAO;

    @Resource
    private JDOrderInfoDAO jdOrderInfoDAO;

    @Autowired
   	private MiddleWare middleware;

    /**
     * VMS直接新增订单处理
     *
     * @param venusOrderDTO
     * @return
     * @throws Exception
     */
    public TradeOrdersDTO createVenusTradeOrders(TradeOrdersDTO venusOrderDTO) throws Exception {

        List<TradeOrderItemsDTO> orderItemsDTOList = venusOrderDTO.getOrderItemList();
        List<TradeOrderStatusHistoryDTO> orderStatusHistoryList = venusOrderDTO.getOrderStatusHistoryDTOList();
        List<TradeOrderItemsStatusHistoryDTO> orderItemsStatusHistoryDTOList = new ArrayList<TradeOrderItemsStatusHistoryDTO>();
        List<TradeOrderErpDistributionDTO> erpDistributionDTOList = venusOrderDTO.getErpDistributionDTOList();
        String orderItemNos = "";
        String orderItemNo = "";

        try {
            orderDAO.addTradeOrderInfo(venusOrderDTO);
            orderStatusHistoryDAO.addTradeOrderStatusHistoryList(orderStatusHistoryList);
            for (TradeOrderErpDistributionDTO erpDistributionDTO : erpDistributionDTOList) {
                erpDistributionDAO.addErpDistributionInfo(erpDistributionDTO);
            }
            for (TradeOrderItemsDTO orderItemDTO : orderItemsDTOList) {
                orderItemsStatusHistoryDTOList.addAll(orderItemDTO.getItemStatusHistoryDTOList());
                orderItemNo = orderItemDTO.getOrderItemNo();
                for (TradeOrderErpDistributionDTO erpDistributionDTO : erpDistributionDTOList) {
                    orderItemNos = erpDistributionDTO.getOrderItemNos();
                    if (orderItemNos.indexOf(orderItemNo.toString()) >= 0) {
                        orderItemDTO.setErpDistributionId(erpDistributionDTO.getId());
                        break;
                    }
                }
                orderItemsDAO.addTradeOrderItemInfo(orderItemDTO);
                orderItemsWarehouseDAO.addOrderItemsWarehouseDetailList(orderItemDTO.getWarehouseDTOList());
            }
            if (orderItemsStatusHistoryDTOList != null && orderItemsStatusHistoryDTOList.size() > 0) {
                orderItemsStatusHistoryDAO.addOrderItemsStatusHistoryList(orderItemsStatusHistoryDTOList);
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
        return venusOrderDTO;
    }

    /**
     * 根据订单编号查询订单信息
     *
     * @param orderNo
     * @return
     * @throws Exception
     */
    public TradeOrdersDTO queryTradeOrdersByOrderNo(String orderNo, boolean isContainsDeleteOrder) throws Exception {
        TradeOrdersDTO tradeOrdersDTO = null;
        TradeOrderItemsDTO queryOrderItemsCondition = new TradeOrderItemsDTO();
        List<TradeOrderItemsDTO> tradeOrderItemsDTOList = null;
        List<TradeOrderErpDistributionDTO> orderErpDistributionDTOList = null;
        List<TradeOrderItemsWarehouseDetailDTO> itemWarehouseDetailDTOList = null;
        List<TradeOrderItemsDiscountDTO> orderItemDiscountDTOList = null;
        String orderItemNo = "";
        TradeOrderItemsDTO tmpOrderItemDTO = null;
        List<TradeOrderItemsDiscountDTO> tmpOrderItemDiscountDTOList = null;
        try {
            tradeOrdersDTO = orderDAO.queryTradeOrderByOrderNo(orderNo);
            if (tradeOrdersDTO == null) {
                return null;
            }
            queryOrderItemsCondition.setOrderNo(orderNo);
            if (!isContainsDeleteOrder) {
                queryOrderItemsCondition.setIsCancelOrderItem(YesNoEnum.DELETE.getValue());
            }
            tradeOrderItemsDTOList = orderItemsDAO.queryTradeOrderItemsByOrderNo(queryOrderItemsCondition);
            if (tradeOrderItemsDTOList != null && !tradeOrderItemsDTOList.isEmpty()) {
                orderErpDistributionDTOList = erpDistributionDAO.queryOrderErpDistributionByOrderNo(orderNo);
                tradeOrdersDTO.setErpDistributionDTOList(orderErpDistributionDTOList);
                for (TradeOrderItemsDTO orderItemDTO : tradeOrderItemsDTOList) {
                    if (YesNoEnum.DELETE.getValue() == orderItemDTO.getIsCancelOrderItem()) {
                        continue;
                    }
                    itemWarehouseDetailDTOList = orderItemsWarehouseDAO
                            .queryOrderItemWarehouseDetailByOrderItemNo(orderItemDTO);
                    orderItemDTO.setWarehouseDTOList(itemWarehouseDetailDTOList);
                    //取最新的分销现价
                    BigDecimal costPrice =  getSaleLimitPrice(orderItemDTO.getItemSpuCode(),tradeOrdersDTO.getSellerCode());
                    orderItemDTO.setCostPrice(costPrice);
                }
                if (YesNoEnum.YES.getValue() == tradeOrdersDTO.getHasUsedCoupon()) {
                    orderItemDiscountDTOList = orderItemsDiscountDAO.queryItemDiscountByOrderNo(orderNo);
                    if (orderItemDiscountDTOList != null && !orderItemDiscountDTOList.isEmpty()) {
                        for (TradeOrderItemsDiscountDTO discountDTO : orderItemDiscountDTOList) {
                            orderItemNo = discountDTO.getOrderItemNo();
                            tmpOrderItemDTO = null;
                            for (TradeOrderItemsDTO orderItemDTO : tradeOrderItemsDTOList) {
                                if (YesNoEnum.DELETE.getValue() == orderItemDTO.getIsCancelOrderItem()) {
                                    continue;
                                }
                                if (orderItemNo.equals(orderItemDTO.getOrderItemNo())
                                        && YesNoEnum.YES.getValue() == orderItemDTO.getHasUsedCoupon()) {
                                    tmpOrderItemDTO = orderItemDTO;
                                    break;
                                }
                            }
                            if (tmpOrderItemDTO == null) {
                                continue;
                            }
                            tmpOrderItemDiscountDTOList = tmpOrderItemDTO.getDiscountDTOList();
                            if (tmpOrderItemDiscountDTOList == null || tmpOrderItemDiscountDTOList.isEmpty()) {
                                tmpOrderItemDiscountDTOList = new ArrayList<TradeOrderItemsDiscountDTO>();
                            }
                            tmpOrderItemDiscountDTOList.add(discountDTO);
                            tmpOrderItemDTO.setDiscountDTOList(tmpOrderItemDiscountDTOList);
                        }
                    }
                }
                tradeOrdersDTO.setOrderItemList(tradeOrderItemsDTOList);
            }
        } catch (Exception e) {
            throw e;
        }
        return tradeOrdersDTO;
    }

    /**
     * 获取最新的分销现价
     */
    @SuppressWarnings("rawtypes")
	public BigDecimal getSaleLimitPrice(String spuCode, String supplierCode) {
		if(StringUtils.isEmpty(supplierCode)||StringUtils.isEmpty(spuCode)){
			return null;
		}
		String tokenUrl=middleware.getPath()+ "/token/"
				+ MiddlewareInterfaceConstant.MIDDLE_PLATFORM_APP_ID;
		String accessTokenErp = MiddlewareInterfaceUtil.httpGet(tokenUrl, Boolean.TRUE);
		if(StringUtils.isEmpty(accessTokenErp)){
			return null;
		}
		Map accessTokenMap = (Map) JSONObject.parseObject(accessTokenErp, Map.class);
		if(Integer.parseInt(String.valueOf( accessTokenMap.get("code")))!=1){
			return null;
		}
		String accessToken=String.valueOf(accessTokenMap.get("data"));

		String url=middleware.getPath()+"/product/findProductPrice";
		String param = "?supplierCode=" + supplierCode + "&productCode=" + spuCode + "&token=" + accessToken;
		String responseJson = MiddlewareInterfaceUtil
				.httpGet(url + param, Boolean.TRUE);
		if(StringUtils.isEmpty(responseJson)){
			return null;
		}
		Map map = (Map) JSONObject.parseObject(responseJson, Map.class);
		if(Integer.parseInt(String.valueOf( map.get("code")))!=1){
			return null;
		}

		if(map.get("data")==null){
			return null;
		}
		Map mapSaleLimitPrice = (Map) JSONObject.parseObject(String.valueOf(map.get("data")), Map.class);
		if(mapSaleLimitPrice.get("floorPrice")==null){
			return null;
		}
		BigDecimal result=null;
		 if( mapSaleLimitPrice.get("floorPrice")!=null) {
			 result=new BigDecimal(String.valueOf(mapSaleLimitPrice.get("floorPrice")));
		 }
		return result;

	}


    /**
     * 保存订单拆单信息
     *
     * @param confirmOrderDTO
     * @return
     * @throws TradeCenterBusinessException
     * @throws Exception
     */
    public TradeOrdersDTO saveTradeOrderDistributionInfo(TradeOrdersDTO confirmOrderDTO)
            throws TradeCenterBusinessException, Exception {
        int updateNum = 0;
        TradeOrdersDTO tradeOrdersDTO = null;
        List<TradeOrderItemsDTO> orderItemsDTOList = confirmOrderDTO.getOrderItemList();
        List<TradeOrderStatusHistoryDTO> orderStatusHistoryDTOList = confirmOrderDTO.getOrderStatusHistoryDTOList();
        List<TradeOrderErpDistributionDTO> orderErpDistributionDTOList = confirmOrderDTO.getErpDistributionDTOList();
        List<TradeOrderItemsWarehouseDetailDTO> warehouseDTOList = null;
        List<TradeOrderItemsStatusHistoryDTO> itemStatusHistoryDTOList = null;
        String orderItemNo = "";
        Map<String, Long> distrubitionIdMap = new HashMap<String, Long>();
        Long distributionId = 0L;
        String orderItemNos = "";
        TradeOrderStatusHistoryDTO tmpOrderStatus = null;
        TradeOrderItemsStatusHistoryDTO tmpOrderItemStatus = null;

        if (orderErpDistributionDTOList == null) {
            throw new TradeCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR,
                    "订单编号:" + confirmOrderDTO.getOrderNo() + "的分销单信息不存在");
        }
        try {
            updateNum = orderDAO.updateTradeOrdersStatusInfo(confirmOrderDTO);
            if (updateNum == 0) {
                throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_UPDATE_ERROR,
                        "订单编号:" + confirmOrderDTO.getOrderNo() + "的信息更新失败");
            }
            if (orderStatusHistoryDTOList != null && !orderStatusHistoryDTOList.isEmpty()) {
                for (TradeOrderStatusHistoryDTO orderStatusDTO : orderStatusHistoryDTOList) {
                    tmpOrderStatus = orderStatusHistoryDAO.queryStatusHistoryByOrderStatus(orderStatusDTO);
                    if (tmpOrderStatus == null) {
                        orderStatusHistoryDAO.addTradeOrderStatusHistory(orderStatusDTO);
                    }
                }
            }
            for (TradeOrderErpDistributionDTO erpDistributionDTO : orderErpDistributionDTOList) {
                if (erpDistributionDTO.getId() != null) {
                    updateNum = erpDistributionDAO.updateTradeOrdersErpDistributionStatusInfo(erpDistributionDTO);
                    if (updateNum == 0) {
                        throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_DISTRIBUTE_UPDATE_ERROR, "订单编号:"
                                + confirmOrderDTO.getOrderNo() + " 分销单ID：" + erpDistributionDTO.getId() + " 更新失败");
                    }
                } else {
                    erpDistributionDAO.addErpDistributionInfo(erpDistributionDTO);
                    distributionId = erpDistributionDTO.getId();
                    orderItemNos = erpDistributionDTO.getOrderItemNos();
                    if (!StringUtils.isEmpty(orderItemNos)) {
                        for (String itemNo : orderItemNos.split(",")) {
                            distrubitionIdMap.put(itemNo, distributionId);
                        }
                    }
                }
            }
            for (TradeOrderItemsDTO orderItemDTO : orderItemsDTOList) {
                if (YesNoEnum.NO.getValue() != orderItemDTO.getIsCancelOrderItem()) {
                    continue;
                }
                orderItemNo = orderItemDTO.getOrderItemNo();
                if (!distrubitionIdMap.isEmpty() && distrubitionIdMap.containsKey(orderItemNo)) {
                    orderItemDTO.setErpDistributionId(distrubitionIdMap.get(orderItemNo));
                }
                warehouseDTOList = orderItemDTO.getWarehouseDTOList();
                itemStatusHistoryDTOList = orderItemDTO.getItemStatusHistoryDTOList();
                updateNum = orderItemsDAO.updateTradeOrderItemsStatusInfo(orderItemDTO);
                if (updateNum == 0) {
                    throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ITEM_UPDATE_ERROR,
                            "订单编号:" + confirmOrderDTO.getOrderNo() + " 订单行编号：" + orderItemNo + " 更新失败");
                }
                for (TradeOrderItemsWarehouseDetailDTO warehouseDTO : warehouseDTOList) {
                    if (warehouseDTO.getId() != null) {
                        if (YesNoEnum.YES.getValue() == warehouseDTO.getDeleteFlag()) {
                            orderItemsWarehouseDAO.deleteTradeOrderItemsWarehouseInfo(warehouseDTO);
                        }
                    } else {
                        orderItemsWarehouseDAO.addOrderItemsWarehouseDetail(warehouseDTO);
                    }
                }
                if (itemStatusHistoryDTOList != null && !itemStatusHistoryDTOList.isEmpty()) {
                    for (TradeOrderItemsStatusHistoryDTO orderItemStatusDTO : itemStatusHistoryDTOList) {
                        tmpOrderItemStatus = orderItemsStatusHistoryDAO
                                .queryStatusHistoryByOrderItemStatus(orderItemStatusDTO);
                        if (tmpOrderItemStatus == null) {
                            orderItemsStatusHistoryDAO.addOrderItemsStatusHistory(orderItemStatusDTO);
                        }
                    }
                }
            }
            tradeOrdersDTO = queryTradeOrdersByOrderNo(confirmOrderDTO.getOrderNo(), false);
        } catch (TradeCenterBusinessException tcbe) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw tcbe;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
        return tradeOrdersDTO;
    }

    /**
     * 保存审核同意的订单
     *
     * @param orderDTO
     * @return
     * @throws TradeCenterBusinessException
     * @throws Exception
     */
    public TradeOrdersDTO saveApproveTradeOrderInfo(TradeOrdersDTO orderDTO)
            throws TradeCenterBusinessException, Exception {
        int updateNum = 0;
        String orderNo = orderDTO.getOrderNo();
        String orderItemNo = "";
        TradeOrdersDTO tradeOrderDTO = new TradeOrdersDTO();
        List<TradeOrderItemsDTO> orderItemsDTOList = orderDTO.getOrderItemList();
        TradeOrderItemsDTO tmpOrderItemDTO = null;
        TradeOrderStatusHistoryDTO orderStatusHistoryDTO = new TradeOrderStatusHistoryDTO();
        TradeOrderItemsStatusHistoryDTO itemStatusHistoryDTO = null;
        TradeOrderStatusHistoryDTO tmpOrderStatus = null;
        TradeOrderItemsStatusHistoryDTO tmpOrderItemStatus = null;
        boolean needUpdateStatusHistory = false;
        String waitPayStatus = dictionary.getValueByCode(DictionaryConst.TYPE_ORDER_STATUS,
                DictionaryConst.OPT_ORDER_STATUS_WAIT_PAY);
        try {
            tradeOrderDTO.setOrderNo(orderNo);
            tradeOrderDTO.setOrderStatus(dictionary.getValueByCode(DictionaryConst.TYPE_ORDER_STATUS,
                    DictionaryConst.OPT_ORDER_STATUS_VERIFY_PASS_WAIT_PAY));
            tradeOrderDTO.setOrderRemarks(orderDTO.getOrderRemarks());
            tradeOrderDTO.setModifyId(orderDTO.getModifyId());
            tradeOrderDTO.setModifyName(orderDTO.getModifyName());
            tradeOrderDTO.setModifyTime(orderDTO.getModifyTime());
            updateNum = orderDAO.updateTradeOrdersStatusInfo(tradeOrderDTO);
            if (updateNum == 0) {
                throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_UPDATE_ERROR,
                        "订单编号:" + orderNo + "的信息更新失败");
            }
            orderStatusHistoryDTO.setOrderNo(orderNo);
            orderStatusHistoryDTO.setOrderStatus(dictionary.getValueByCode(DictionaryConst.TYPE_ORDER_STATUS,
                    DictionaryConst.OPT_ORDER_STATUS_VERIFY_PASS_WAIT_PAY));
            orderStatusHistoryDTO.setOrderStatusText(dictionary.getNameByCode(DictionaryConst.TYPE_ORDER_STATUS,
                    DictionaryConst.OPT_ORDER_STATUS_VERIFY_PASS_WAIT_PAY));
            orderStatusHistoryDTO.setCreateId(orderDTO.getModifyId());
            orderStatusHistoryDTO.setCreateName(orderDTO.getModifyName());
            tmpOrderStatus = orderStatusHistoryDAO.queryStatusHistoryByOrderStatus(orderStatusHistoryDTO);
            if (tmpOrderStatus == null) {
                orderStatusHistoryDAO.addTradeOrderStatusHistory(orderStatusHistoryDTO);
            }
            for (TradeOrderItemsDTO orderItemDTO : orderItemsDTOList) {
                if (YesNoEnum.NO.getValue() != orderItemDTO.getIsCancelOrderItem()) {
                    continue;
                }
                orderItemNo = orderItemDTO.getOrderItemNo();
                needUpdateStatusHistory = waitPayStatus.compareTo(orderItemDTO.getOrderItemStatus()) == 0 ? false
                                                                                                          : true;
                tmpOrderItemDTO = new TradeOrderItemsDTO();
                tmpOrderItemDTO.setOrderNo(orderNo);
                tmpOrderItemDTO.setOrderItemNo(orderItemNo);
                tmpOrderItemDTO.setOrderItemStatus(dictionary.getValueByCode(DictionaryConst.TYPE_ORDER_STATUS,
                        DictionaryConst.OPT_ORDER_STATUS_VERIFY_PASS_WAIT_PAY));
                tmpOrderItemDTO.setModifyId(orderDTO.getModifyId());
                tmpOrderItemDTO.setModifyName(orderDTO.getModifyName());
                tmpOrderItemDTO.setModifyTime(orderItemDTO.getModifyTime());
                updateNum = orderItemsDAO.updateTradeOrderItemsStatusInfo(tmpOrderItemDTO);
                if (updateNum == 0) {
                    throw new TradeCenterBusinessException(ReturnCodeConst.ORDER_ITEM_UPDATE_ERROR,
                            "订单编号:" + orderNo + " 订单行编号：" + orderItemNo + " 更新失败");
                }
                if (needUpdateStatusHistory) {
                    itemStatusHistoryDTO = new TradeOrderItemsStatusHistoryDTO();
                    itemStatusHistoryDTO.setOrderItemNo(orderItemNo);
                    itemStatusHistoryDTO.setOrderItemStatus(dictionary.getValueByCode(DictionaryConst.TYPE_ORDER_STATUS,
                            DictionaryConst.OPT_ORDER_STATUS_VERIFY_PASS_WAIT_PAY));
                    itemStatusHistoryDTO.setOrderItemStatusText(dictionary.getNameByCode(
                            DictionaryConst.TYPE_ORDER_STATUS, DictionaryConst.OPT_ORDER_STATUS_VERIFY_PASS_WAIT_PAY));
                    itemStatusHistoryDTO.setCreateId(orderDTO.getModifyId());
                    itemStatusHistoryDTO.setCreateName(orderDTO.getModifyName());
                    tmpOrderItemStatus = orderItemsStatusHistoryDAO
                            .queryStatusHistoryByOrderItemStatus(itemStatusHistoryDTO);
                    if (tmpOrderItemStatus == null) {
                        orderItemsStatusHistoryDAO.addOrderItemsStatusHistory(itemStatusHistoryDTO);
                    }
                }
            }
            tradeOrderDTO = queryTradeOrdersByOrderNo(orderDTO.getOrderNo(), false);
        } catch (TradeCenterBusinessException tcbe) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw tcbe;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
        return tradeOrderDTO;
    }

    /**
     * 更新订单议价信息
     *
     * @param orderDTO
     * @return
     * @throws TradeCenterBusinessException
     * @throws Exception
     */
    public TradeOrdersDTO saveNegotiateTradeOrderInfo(TradeOrdersDTO orderDTO)
            throws TradeCenterBusinessException, Exception {
        TradeOrdersDTO tradeOrderDTO = new TradeOrdersDTO();
        List<TradeOrderItemsDTO> orderItemsDTOList = orderDTO.getOrderItemList();
        List<TradeOrderPayInfoDTO> orderPayInfoList = orderDTO.getOrderPayDTOList();
        List<TradeOrderItemsStatusHistoryDTO> itemStatusHistoryDTOList = null;
        TradeOrderItemsPriceHistoryDTO itemPriceHistoryDTO = null;
        boolean hasChangedFlag = false;
        TradeOrderItemsStatusHistoryDTO tmpOrderItemStatus = null;

        try {
            for (TradeOrderItemsDTO orderItemDTO : orderItemsDTOList) {
                if ("delete".equals(orderItemDTO.getDealFlag())) {
                    hasChangedFlag = true;
                    orderItemsDAO.deleteTradeOrderItemsInfo(orderItemDTO);
                } else if ("update".equals(orderItemDTO.getDealFlag())) {
                    hasChangedFlag = true;
                    orderItemsDAO.updateTradeOrderItemsNegotiateInfo(orderItemDTO);
                } else if ("add".equals(orderItemDTO.getDealFlag())) {
                    hasChangedFlag = true;
                    orderItemsDAO.addTradeOrderItemInfo(orderItemDTO);
                } else {
                    continue;
                }
                itemPriceHistoryDTO = orderItemDTO.getItemPriceHistoryDTO();
                if (itemPriceHistoryDTO != null) {
                    orderItemsPriceHistoryDAO.insertItemPriceHistoryInfo(itemPriceHistoryDTO);
                }
                itemStatusHistoryDTOList = orderItemDTO.getItemStatusHistoryDTOList();
                if (itemStatusHistoryDTOList != null && !itemStatusHistoryDTOList.isEmpty()) {
                    for (TradeOrderItemsStatusHistoryDTO orderItemStatusDTO : itemStatusHistoryDTOList) {
                        tmpOrderItemStatus = orderItemsStatusHistoryDAO
                                .queryStatusHistoryByOrderItemStatus(orderItemStatusDTO);
                        if (tmpOrderItemStatus == null) {
                            orderItemsStatusHistoryDAO.addOrderItemsStatusHistory(orderItemStatusDTO);
                        }
                    }
                }
            }
            if (hasChangedFlag) {
                orderDAO.updateTradeOrdersNegotiateInfo(orderDTO);
                if (orderPayInfoList != null && !orderPayInfoList.isEmpty()) {
                    orderPayInfoDAO.deleteOrderPayInfoByOrderNo(orderDTO.getOrderNo());
                    for (TradeOrderPayInfoDTO payInfo : orderPayInfoList) {
                        orderPayInfoDAO.addOrderPayInfo(payInfo);
                    }
                }
            }
            tradeOrderDTO = queryTradeOrdersByOrderNo(orderDTO.getOrderNo(), false);
        } catch (TradeCenterBusinessException tcbe) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw tcbe;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
        return tradeOrderDTO;
    }

    /**
     * 更新ERP下行信息状态
     *
     * @param redoWorkDTO
     * @param orderDTO
     * @param orderItemDTO
     * @throws TradeCenterBusinessException
     * @throws Exception
     */
    public void saveRedoErpDownTradeOrderInfo(RedoErpWorkDTO redoWorkDTO, TradeOrdersDTO orderDTO,
                                              TradeOrderItemsDTO orderItemDTO)
            throws TradeCenterBusinessException, Exception {
        String orderNo = orderItemDTO.getOrderNo();
        List<TradeOrderItemsDTO> orderItemList = null;
        String firstCategoryCode = orderItemDTO.getErpFirstCategoryCode();
        Long brandId = orderItemDTO.getBrandId();
        Long erpDistributionId = orderItemDTO.getErpDistributionId();
        TradeOrderPayInfoDTO payInfoDTO = new TradeOrderPayInfoDTO();
        TradeOrderErpDistributionDTO erpDistributionDTO = new TradeOrderErpDistributionDTO();
        JDOrderInfo jdOrderInfo = new JDOrderInfo();
        int updateCnt = 0;
        String postStatus = dictionary.getValueByCode(DictionaryConst.TYPE_ORDER_STATUS,
                DictionaryConst.OPT_ORDER_STATUS_POST_STRIKEA_PENDING);
        String erpDownStatus = dictionary.getValueByCode(DictionaryConst.TYPE_ORDER_STATUS,
                DictionaryConst.OPT_ORDER_STATUS_ERP_DOWNING);
        String orderChannelCode = orderItemDTO.getChannelCode();
        String errorStatusCode = "";
        String errorReasonStr = "";
        Date errorTime = DateUtils.parse("0000-00-00 00:00:00", DateUtils.YYDDMMHHMMSS);

        try {
            if (postStatus.equals(orderItemDTO.getOrderItemStatus())) {
                payInfoDTO.setOrderNo(orderNo);
                payInfoDTO.setClassCode(firstCategoryCode);
                payInfoDTO.setBrandCode(String.valueOf(brandId));
                payInfoDTO.setPayResultStatus(YesNoEnum.NO.getValue());
                payInfoDTO.setPaySendCount(0);
                updateCnt = orderPayInfoDAO.updateOrderPayTimesByOrderItem(payInfoDTO);
                if (updateCnt == 0) {
                    throw new TradeCenterBusinessException(ReturnCodeConst.SYSTEM_ERROR, "重置收付款下行状态失败");
                }
                orderItemDTO.setOrderItemErrorStatus("");
                orderItemDTO.setOrderItemErrorReason("");
                orderItemDTO.setModifyId(redoWorkDTO.getOperatorId());
                orderItemDTO.setModifyName(redoWorkDTO.getOperatorName());
                orderItemDTO.setIsCancelOrderItem(YesNoEnum.NO.getValue());
                updateCnt = orderItemsDAO.updateTradeOrderItemsPostStrikeaStatus(orderItemDTO);
            } else if (erpDownStatus.equals(orderItemDTO.getOrderItemStatus())) {
                if (dictionary
                        .getValueByCode(DictionaryConst.TYPE_PRODUCT_CHANNEL, DictionaryConst.OPT_PRODUCT_CHANNEL_HTD)
                        .equals(orderChannelCode)) {
                    erpDistributionDTO.setId(erpDistributionId);
                    erpDistributionDTO.setErpStatus(dictionary.getValueByCode(DictionaryConst.TYPE_ERP_STATUS,
                            DictionaryConst.OPT_ERP_STATUS_PENDING));
                    erpDistributionDTO.setErpDownTimes(0);
                    erpDistributionDTO.setModifyId(redoWorkDTO.getOperatorId());
                    erpDistributionDTO.setModifyName(redoWorkDTO.getOperatorName());
                    updateCnt = erpDistributionDAO.updateTradeOrdersErpDistributionTimes(erpDistributionDTO);
                    if (updateCnt == 0) {
                        throw new TradeCenterBusinessException(ReturnCodeConst.SYSTEM_ERROR, "重置五合一／预售下行状态失败");
                    }
                    orderItemDTO.setOrderItemErrorStatus("");
                    orderItemDTO.setOrderItemErrorReason("");
                    orderItemDTO.setModifyId(redoWorkDTO.getOperatorId());
                    orderItemDTO.setModifyName(redoWorkDTO.getOperatorName());
                    orderItemDTO.setIsCancelOrderItem(YesNoEnum.NO.getValue());
                    updateCnt = orderItemsDAO.updateTradeOrderItemsErpDownStatus(orderItemDTO);
                } else if (dictionary
                        .getValueByCode(DictionaryConst.TYPE_PRODUCT_CHANNEL, DictionaryConst.OPT_PRODUCT_CHANNEL_JD)
                        .equals(orderChannelCode)) {
                    jdOrderInfo.setOrderNo(orderNo);
                    jdOrderInfo.setErpBookSendCount(0);
                    jdOrderInfo.setErpResultCode("");
                    jdOrderInfo.setErpResultMsg("");
                    jdOrderInfo.setErpResultStatus(YesNoEnum.NO.getValue());
                    updateCnt = jdOrderInfoDAO.updateJDOrderErpInfoByOrderNo(jdOrderInfo);
                    if (updateCnt == 0) {
                        throw new TradeCenterBusinessException(ReturnCodeConst.SYSTEM_ERROR, "重置五合一／预售下行状态失败");
                    }
                    orderItemDTO.setOrderItemErrorStatus("");
                    orderItemDTO.setOrderItemErrorReason("");
                    orderItemDTO.setModifyId(redoWorkDTO.getOperatorId());
                    orderItemDTO.setModifyName(redoWorkDTO.getOperatorName());
                    orderItemDTO.setIsCancelOrderItem(YesNoEnum.NO.getValue());
                    updateCnt = orderItemsDAO.updateTradeOrderItemsPreSaleDownStatus(orderItemDTO);
                }
            }
            if (updateCnt == 0) {
                throw new TradeCenterBusinessException(ReturnCodeConst.SYSTEM_ERROR, "重置五合一／预售下行状态失败");
            }
            orderItemList = orderItemsDAO.queryTradeOrderItemsByOrderNo(orderItemDTO);
            for (TradeOrderItemsDTO tmpOrderItemsDTO : orderItemList) {
                if (YesNoEnum.NO.getValue() != tmpOrderItemsDTO.getIsCancelOrderItem()) {
                    continue;
                }
                if (postStatus.equals(orderItemDTO.getOrderItemStatus())
                        && postStatus.equals(tmpOrderItemsDTO.getOrderItemStatus())) {
                    errorStatusCode = tmpOrderItemsDTO.getOrderItemErrorStatus();
                    if (!StringUtils.isEmpty(errorStatusCode)) {
                        errorTime = tmpOrderItemsDTO.getOrderItemErrorTime();
                        errorReasonStr = tmpOrderItemsDTO.getOrderItemErrorReason();
                        break;
                    }
                } else if (erpDownStatus.equals(orderItemDTO.getOrderItemStatus())
                        && erpDownStatus.equals(tmpOrderItemsDTO.getOrderItemStatus())) {
                    errorStatusCode = tmpOrderItemsDTO.getOrderItemErrorStatus();
                    if (!StringUtils.isEmpty(errorStatusCode)) {
                        errorTime = tmpOrderItemsDTO.getOrderItemErrorTime();
                        errorReasonStr = tmpOrderItemsDTO.getOrderItemErrorReason();
                        break;
                    }
                }
            }
            orderDTO.setOrderErrorStatus(errorStatusCode);
            orderDTO.setOrderErrorTime(errorTime);
            orderDTO.setOrderErrorReason(errorReasonStr);
            orderDTO.setModifyId(redoWorkDTO.getOperatorId());
            orderDTO.setModifyName(redoWorkDTO.getOperatorName());
            updateCnt = orderDAO.updateTradeOrdersErpDownStatusInfo(orderDTO);
            if (updateCnt == 0) {
                throw new TradeCenterBusinessException(ReturnCodeConst.SYSTEM_ERROR, "重置五合一／预售下行状态失败");
            }
        } catch (TradeCenterBusinessException tcbe) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw tcbe;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }
}
