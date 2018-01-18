package cn.htd.tradecenter.service.impl;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.dto.DictionaryInfo;
import cn.htd.tradecenter.common.constant.ReturnCodeConst;
import cn.htd.tradecenter.common.constant.VMSOrderConstants;
import cn.htd.tradecenter.common.exception.TradeCenterBusinessException;
import cn.htd.tradecenter.common.utils.ValidateResult;
import cn.htd.tradecenter.common.utils.ValidationUtils;
import cn.htd.tradecenter.dao.VMSOrderDAO;
import cn.htd.tradecenter.dto.TradeOrdersShowDTO;
import cn.htd.tradecenter.dto.VenusTradeOrdersQueryInDTO;
import cn.htd.tradecenter.enums.OrderStatusEnum;
import cn.htd.tradecenter.service.VMSOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("vmsOrderService")
public class VMSOrderServiceImpl implements VMSOrderService{

    private static final Logger logger = LoggerFactory.getLogger(TradeOrderServiceImpl.class);

    @Resource
    private TradeOrderBaseService baseService;

    @Resource
    private VMSOrderDAO vmsOrderDAO;


    @Override
    public ExecuteResult<DataGrid<TradeOrdersShowDTO>> queryVMSpendingOrderByCondition(VenusTradeOrdersQueryInDTO conditionDTO, Pager<VenusTradeOrdersQueryInDTO> pager) {
        ExecuteResult<DataGrid<TradeOrdersShowDTO>> result = new ExecuteResult<DataGrid<TradeOrdersShowDTO>>();
        DataGrid<TradeOrdersShowDTO> dataGrid = new DataGrid<TradeOrdersShowDTO>();
        if (conditionDTO == null) {
            throw new TradeCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "卖家编码不能为空");
        }
        // 输入DTO的验证
        ValidateResult validateResult = ValidationUtils.validateEntity(conditionDTO);
        // 有错误信息时返回错误信息
        if (validateResult.isHasErrors()) {
            throw new TradeCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, validateResult.getErrorMsg());
        }
        // 根据查询tab设置查询入参
        setVMSpendingQueryCondition(conditionDTO);
        long count = vmsOrderDAO.queryVMSpendingOrderCountByCondition(conditionDTO);
        if(count > 0){
            List<TradeOrdersShowDTO> orderlist = vmsOrderDAO.queryVMSpendingOrderByCondition(conditionDTO, pager);
            dataGrid.setRows(orderlist);
        }
        dataGrid.setTotal(count);
        result.setResult(dataGrid);
        return result;
    }

    private void setVMSpendingQueryCondition(VenusTradeOrdersQueryInDTO conditionDTO){
        List<String> orderStatusList = new ArrayList<String>();
        if(VMSOrderConstants.SEARCH_CONDITION_OUT_DISTRIBTION.equals(conditionDTO.getSearchFlag())){
            orderStatusList.add(OrderStatusEnum.VERIFY_PENDING.getValue());
            conditionDTO.setOrderStatusList(orderStatusList);
            conditionDTO.setIsOutDistribtion(VMSOrderConstants.IS_OUT_DISTRIBTION);
            conditionDTO.setIsCancelFlag(VMSOrderConstants.IS_NOT_CANCEL_ORDER);
        }else if(VMSOrderConstants.SEARCH_CONDITION_PENDING_PRICE.equals(conditionDTO.getSearchFlag())){
            orderStatusList.add(OrderStatusEnum.WAIT_PAY.getValue());
            orderStatusList.add(OrderStatusEnum.VERIFY_WAIT_PAY.getValue());
            conditionDTO.setOrderStatusList(orderStatusList);
            conditionDTO.setIsTimelimitedOrder(VMSOrderConstants.IS_NOT_TIMELIMITED_ORDER);
            conditionDTO.setHasUsedCoupon(VMSOrderConstants.HAS_NOT_USED_COUPON);
            conditionDTO.setIsErrorFlag(VMSOrderConstants.IS_NOT_ERROR_ORDER);
            conditionDTO.setIsCancelFlag(VMSOrderConstants.IS_NOT_CANCEL_ORDER);
        }else if(VMSOrderConstants.SEARCH_CONDITION_WAITING_CONFIRM.equals(conditionDTO.getSearchFlag())){
            orderStatusList.add(OrderStatusEnum.WAIT_CONFIRM.getValue());
            conditionDTO.setOrderStatusList(orderStatusList);
            conditionDTO.setIsErrorFlag(VMSOrderConstants.IS_NOT_ERROR_ORDER);
            conditionDTO.setIsCancelFlag(VMSOrderConstants.IS_NOT_CANCEL_ORDER);
        }else if(VMSOrderConstants.SEARCH_CONDITION_PENDING_SPLIT.equals(conditionDTO.getSearchFlag())){
            orderStatusList.add(OrderStatusEnum.PAID.getValue());
            orderStatusList.add(OrderStatusEnum.PAYED_POST_STRIKEA_SUCCESS_PRE_OPEN_LIST.getValue());
            conditionDTO.setOrderStatusList(orderStatusList);
            conditionDTO.setIsCancelFlag(VMSOrderConstants.IS_NOT_CANCEL_ORDER);
        }else if(VMSOrderConstants.SEARCH_CONDITION_ORDER_ERROR.equals(conditionDTO.getSearchFlag())){
            conditionDTO.setIsErrorFlag(VMSOrderConstants.IS_ERROR_ORDER);
            conditionDTO.setIsCancelFlag(VMSOrderConstants.IS_NOT_CANCEL_ORDER);
        }
    }

}
