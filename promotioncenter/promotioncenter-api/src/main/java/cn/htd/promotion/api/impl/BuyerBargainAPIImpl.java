package cn.htd.promotion.api.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.htd.promotion.api.BuyerBargainAPI;
import cn.htd.promotion.cpc.biz.dao.BuyerLaunchBargainInfoDAO;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.response.BuyerLaunchBargainInfoResDTO;

@Service("buyerBargainAPI")
public class BuyerBargainAPIImpl implements BuyerBargainAPI{
	
	@Resource
	private BuyerLaunchBargainInfoDAO buyerBargainDAO;

	@Override
	public ExecuteResult<List<BuyerLaunchBargainInfoResDTO>> getBuyerLaunchBargainInfoByBuyerCode(String buyerCode) {
		ExecuteResult<List<BuyerLaunchBargainInfoResDTO>> result = new ExecuteResult<List<BuyerLaunchBargainInfoResDTO>>();
		List<BuyerLaunchBargainInfoResDTO> buyerBargainInfoList = buyerBargainDAO.getBuyerLaunchBargainInfoByBuyerCode(buyerCode);
		result.setResult(buyerBargainInfoList);
		result.setCode(ResultCodeEnum.SUCCESS.getCode());
		if(buyerBargainInfoList == null){
			result.setResultMessage(ResultCodeEnum.NORESULT.getMsg());
		}else{
			result.setResultMessage(ResultCodeEnum.SUCCESS.getMsg());
		}
		return result;
	}

}
