package cn.htd.promotion.cpc.biz.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cn.htd.promotion.cpc.biz.dao.PromotionSloganDAO;
import cn.htd.promotion.cpc.biz.dmo.PromotionSloganDMO;
import cn.htd.promotion.cpc.biz.service.PromotionSloganService;
import cn.htd.promotion.cpc.dto.response.PromotionSloganDTO;

/**
 * 活动宣传语实现类
 * @author xmz
 *
 */
@Service("promotionSloganService")
public class PromotionSloganServiceImpl implements PromotionSloganService{

	private static final Logger LOGGER = LoggerFactory.getLogger(PromotionSloganServiceImpl.class);

	@Resource
	private PromotionSloganDAO promotionSloganDAO;
	
	@Override
	public List<PromotionSloganDTO> queryBargainSloganBySellerCode(String providerSellerCode, String messageId)
			throws Exception {
		LOGGER.info("MessageId{}:调用promotionSloganDAO.queryBargainSloganBySellerCode（）方法开始,入参{}",messageId,providerSellerCode+":"+messageId);
		List<PromotionSloganDMO> promotionSloganDMOList = promotionSloganDAO.queryBargainSloganBySellerCode(providerSellerCode);;
		LOGGER.info("MessageId{}:调用promotionSloganDAO.queryBargainSloganBySellerCode（）方法开始,出参{}",JSON.toJSONString(promotionSloganDMOList));
		List<PromotionSloganDTO> promotionSloganDTOList = null;
		if(null != promotionSloganDMOList){
			promotionSloganDTOList = new ArrayList<PromotionSloganDTO>();
			String str = JSONObject.toJSONString(promotionSloganDMOList);
			promotionSloganDTOList = JSONObject.parseArray(str,PromotionSloganDTO.class);
		}
		return promotionSloganDTOList;
	}

}
