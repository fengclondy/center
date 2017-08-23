package cn.htd.promotion.api.impl;


import java.io.PrintWriter;
import java.io.StringWriter;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.promotion.api.PromotionGashaponAPI;
import cn.htd.promotion.cpc.biz.service.PromotionInfoService;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.request.PromotionInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.PromotionAwardDTO;
import cn.htd.promotion.cpc.dto.response.PromotionInfoDTO;



@Service("promotionGashaponAPI")
public class PromotionGashaponAPIImpl implements PromotionGashaponAPI{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BuyerBargainAPIImpl.class);
	
	@Resource
	private PromotionInfoService  promotionInfoService;

	@Override
	public ExecuteResult<DataGrid<PromotionInfoDTO>> getPromotionGashaponByInfo(
			PromotionInfoReqDTO promotionInfoReqDTO,
			Pager<PromotionInfoReqDTO> pager) {
		ExecuteResult<DataGrid<PromotionInfoDTO>> result = new ExecuteResult<DataGrid<PromotionInfoDTO>>();
        try{
                DataGrid<PromotionInfoDTO>promotionInfoList = promotionInfoService.getPromotionGashaponByInfo(promotionInfoReqDTO, pager);
                result.setResult(promotionInfoList);
                result.setCode(ResultCodeEnum.SUCCESS.getCode());
                if(promotionInfoList.getSize() == 0|| promotionInfoList ==null){
                    result.setResultMessage(ResultCodeEnum.NORESULT.getMsg());
                }else{
                    result.setResultMessage(ResultCodeEnum.SUCCESS.getMsg());
                }
        }catch (Exception e) {
            result.setCode(ResultCodeEnum.ERROR.getMsg());
            StringWriter w = new StringWriter();
            e.printStackTrace(new PrintWriter(w));
        }
        return result;
	}





	
}
