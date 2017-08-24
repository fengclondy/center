package cn.htd.promotion.cpc.api.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.annotation.Resource;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.api.BuyerBargainAPI;
import cn.htd.promotion.cpc.biz.service.BuyerLaunchBargainInfoService;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.common.util.ValidateResult;
import cn.htd.promotion.cpc.common.util.ValidationUtils;
import cn.htd.promotion.cpc.dto.request.BuyerBargainLaunchReqDTO;
import cn.htd.promotion.cpc.dto.response.BuyerLaunchBargainInfoResDTO;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("buyerBargainAPI")
public class BuyerBargainAPIImpl implements BuyerBargainAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(BuyerBargainAPIImpl.class);

    @Resource
    private BuyerLaunchBargainInfoService buyerLaunchBargainInfoService;

    @Override
    public ExecuteResult<List<BuyerLaunchBargainInfoResDTO>> getBuyerLaunchBargainInfoByBuyerCode(String buyerCode,
            String messageId) {
        ExecuteResult<List<BuyerLaunchBargainInfoResDTO>> result =
                new ExecuteResult<List<BuyerLaunchBargainInfoResDTO>>();
        try {
            if (!StringUtils.isEmpty(buyerCode) && !StringUtils.isEmpty(messageId)) {
                List<BuyerLaunchBargainInfoResDTO> buyerBargainInfoList =
                        buyerLaunchBargainInfoService.getBuyerLaunchBargainInfoByBuyerCode(buyerCode, messageId);
                result.setResult(buyerBargainInfoList);

                if (buyerBargainInfoList.size() == 0 || buyerBargainInfoList == null) {
                    result.setCode(ResultCodeEnum.NORESULT.getCode());
                    result.setResultMessage(ResultCodeEnum.NORESULT.getMsg());
                } else {
                    result.setCode(ResultCodeEnum.SUCCESS.getCode());
                    result.setResultMessage(ResultCodeEnum.SUCCESS.getMsg());
                }
            } else {
                result.setCode(ResultCodeEnum.PROMOTION_PARAM_IS_NULL.getCode());
                result.setErrorMessage(ResultCodeEnum.PROMOTION_PARAM_IS_NULL.getMsg());
            }

            LOGGER.info("MessageId{}:getBuyerLaunchBargainInfoByBuyerCode（）方法,出参{}", messageId,
                    JSON.toJSONString(result));
        } catch (Exception e) {
            result.setCode(ResultCodeEnum.ERROR.getMsg());
            StringWriter w = new StringWriter();
            e.printStackTrace(new PrintWriter(w));
            LOGGER.error("MessageId:{} 调用方法buyerLaunchBargainInfoService.getBuyerLaunchBargainInfoByBuyerCode出现异常{}",
                    messageId, buyerCode + ":" + messageId, w.toString());
        }
        return result;
    }

    @Override
    public ExecuteResult<Boolean> updateBuyerLaunchBargainInfo(BuyerBargainLaunchReqDTO buyerBargainLaunch) {
        ExecuteResult<Boolean> result = new ExecuteResult<Boolean>();
        try {
            //验证参数是否为空
            ValidateResult validateResult = ValidationUtils
                    .validateEntity(buyerBargainLaunch);
            if (validateResult.isHasErrors()) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(),
                        validateResult.getErrorMsg());
            }
            Integer falg = buyerLaunchBargainInfoService.updateBuyerLaunchBargainInfo(buyerBargainLaunch);
            if (falg == 1) {
                result.setCode(ResultCodeEnum.SUCCESS.getCode());
                result.setResult(true);
            } else {
                result.setCode(ResultCodeEnum.ERROR.getCode());
                result.setErrorMessage(ResultCodeEnum.ERROR.getMsg());
                result.setResult(false);
            }
        } catch (PromotionCenterBusinessException pbs) {
            result.setCode(pbs.getCode());
            result.setErrorMessage(pbs.getMessage());
        } catch (Exception e) {
            result.setCode(ResultCodeEnum.ERROR.getMsg());
            StringWriter w = new StringWriter();
            e.printStackTrace(new PrintWriter(w));
            LOGGER.error("MessageId:{} 调用方法buyerLaunchBargainInfoService.updateBuyerLaunchBargainInfo出现异常{}",
                    buyerBargainLaunch.getMessageId(), JSON.toJSONString(buyerBargainLaunch), w.toString());
        }
        return result;
    }

    public ExecuteResult<BuyerLaunchBargainInfoResDTO> addBuyerBargainLaunch(
            BuyerLaunchBargainInfoResDTO bargainInfoDTO, String messageId) {
        ExecuteResult<BuyerLaunchBargainInfoResDTO> result = new ExecuteResult<BuyerLaunchBargainInfoResDTO>();
        if (!StringUtils.isEmpty(messageId) && null != bargainInfoDTO) {
            return buyerLaunchBargainInfoService.addBuyerBargainLaunch(bargainInfoDTO, messageId);
        } else {
            result.setCode(ResultCodeEnum.PROMOTION_PARAM_IS_NULL.getCode());
            result.setErrorMessage(ResultCodeEnum.PROMOTION_PARAM_IS_NULL.getMsg());
        }
        return result;
    }

    @Override
    public ExecuteResult<BuyerLaunchBargainInfoResDTO> getBuyerBargainLaunchInfoByBargainCode(String bargainCode,
            String messageId) {
        ExecuteResult<BuyerLaunchBargainInfoResDTO> result = new ExecuteResult<BuyerLaunchBargainInfoResDTO>();
        try {
            if (!StringUtils.isEmpty(bargainCode) && !StringUtils.isEmpty(messageId)) {
                BuyerLaunchBargainInfoResDTO buyerLaunchBargainInfo = buyerLaunchBargainInfoService.
                        getBuyerBargainLaunchInfoByBargainCode(bargainCode, messageId);
                if (buyerLaunchBargainInfo != null) {
                    result.setCode(ResultCodeEnum.SUCCESS.getCode());
                    result.setResult(buyerLaunchBargainInfo);
                } else {
                    result.setCode(ResultCodeEnum.NORESULT.getCode());
                    result.setCode(ResultCodeEnum.NORESULT.getMsg());
                }
            } else {
                result.setCode(ResultCodeEnum.PROMOTION_PARAM_IS_NULL.getCode());
                result.setErrorMessage(ResultCodeEnum.PROMOTION_PARAM_IS_NULL.getMsg());
                return result;
            }
        } catch (Exception e) {
            result.setCode(ResultCodeEnum.ERROR.getMsg());
            StringWriter w = new StringWriter();
            e.printStackTrace(new PrintWriter(w));
            LOGGER.error("MessageId:{} 调用方法buyerLaunchBargainInfoService.getBuyerBargainLaunchInfoByBargainCode出现异常{}",
                    messageId, bargainCode + ":" + messageId, w.toString());
        }
        return result;
    }

    @Override
    public ExecuteResult<Integer> getBuyerLaunchBargainInfoNum(String promotionId,
            String levelCode, String messageId) {
        ExecuteResult<Integer> result = new ExecuteResult<Integer>();
        try {
            if (!StringUtils.isEmpty(promotionId) && !StringUtils.isEmpty(levelCode) && !StringUtils
                    .isEmpty(messageId)) {
                Integer i =
                        buyerLaunchBargainInfoService.getBuyerLaunchBargainInfoNum(promotionId, levelCode, messageId);
                result.setResult(i);
                result.setCode(ResultCodeEnum.SUCCESS.getCode());
            } else {
                result.setCode(ResultCodeEnum.PROMOTION_PARAM_IS_NULL.getCode());
                result.setErrorMessage(ResultCodeEnum.PROMOTION_PARAM_IS_NULL.getMsg());
            }
            LOGGER.info("MessageId{}:getBuyerLaunchBargainInfoNum（）方法,出参{}", messageId, JSON.toJSONString(result));
        } catch (Exception e) {
            result.setCode(ResultCodeEnum.ERROR.getMsg());
            StringWriter w = new StringWriter();
            e.printStackTrace(new PrintWriter(w));
            LOGGER.error("MessageId:{} 调用方法buyerLaunchBargainInfoService.getBuyerLaunchBargainInfoNum出现异常{}",
                    messageId, promotionId + ":" + levelCode + ":" + messageId, w.toString());
        }
        return result;
    }

    @Override
    public ExecuteResult<DataGrid<BuyerLaunchBargainInfoResDTO>> getBuyerLaunchBargainInfoByCondition(
            Pager<BuyerBargainLaunchReqDTO> pager, BuyerBargainLaunchReqDTO BuyerBargainLaunch) {
        ExecuteResult<DataGrid<BuyerLaunchBargainInfoResDTO>> result =
                new ExecuteResult<DataGrid<BuyerLaunchBargainInfoResDTO>>();
        DataGrid<BuyerLaunchBargainInfoResDTO> datagrid = new DataGrid<BuyerLaunchBargainInfoResDTO>();
        result.setResult(datagrid);
        return result;
    }
}
