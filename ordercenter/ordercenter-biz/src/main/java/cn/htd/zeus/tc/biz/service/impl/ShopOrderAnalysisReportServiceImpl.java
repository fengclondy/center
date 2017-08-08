/**
 * 
 */
package cn.htd.zeus.tc.biz.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.htd.zeus.tc.biz.dao.ShopSalesAnalysisDayReportDAO;
import cn.htd.zeus.tc.biz.dmo.ShopSalesAnalysisDayReportDMO;
import cn.htd.zeus.tc.biz.service.ShopOrderAnalysisReportService;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.dto.response.ShopOrderAnalsisDayReportResDTO;
import cn.htd.zeus.tc.dto.response.ShopOrderAnalysisDayReportListResDTO;
import cn.htd.zeus.tc.dto.resquest.ShopOrderAnalysisDayReportReqDTO;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * @author ly
 *
 */
@Service
public class ShopOrderAnalysisReportServiceImpl implements ShopOrderAnalysisReportService {

	@Autowired
	private ShopSalesAnalysisDayReportDAO shopSalesAnalysisDayReportDAO = null;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ShopOrderAnalysisReportServiceImpl.class);
	
	@Override
	public ShopOrderAnalsisDayReportResDTO queryShopOrderAnalsisDayReportList(
			ShopOrderAnalysisDayReportReqDTO shopOrderAnalysisDayReportReqDTO) {
		
		ShopOrderAnalsisDayReportResDTO shopOrderAnalsisDayReportResDTO = new ShopOrderAnalsisDayReportResDTO();
		try {
			ShopSalesAnalysisDayReportDMO shopSalesAnalysisDayReportDMO = new ShopSalesAnalysisDayReportDMO();
			int shopId = shopOrderAnalysisDayReportReqDTO.getShopId();
			shopSalesAnalysisDayReportDMO.setShopId(shopId);
			int startTime = shopOrderAnalysisDayReportReqDTO.getStartTime();
			shopSalesAnalysisDayReportDMO.setStartTime(startTime);
			int endTime  = shopOrderAnalysisDayReportReqDTO.getEndTime();
			shopSalesAnalysisDayReportDMO.setEndTime(endTime);
			String goodsName = shopOrderAnalysisDayReportReqDTO.getGoodsName();
			shopSalesAnalysisDayReportDMO.setGoodsName(goodsName);
			String itemCode = shopOrderAnalysisDayReportReqDTO.getItemCode();
			shopSalesAnalysisDayReportDMO.setItemCode(itemCode);
			String skuCode = shopOrderAnalysisDayReportReqDTO.getSkuCode();
			shopSalesAnalysisDayReportDMO.setSkuCode(skuCode);
			Integer page = shopOrderAnalysisDayReportReqDTO.getPage();
			Integer limit = shopOrderAnalysisDayReportReqDTO.getLimit();
			if(page!=null && limit!=null)
			{
				shopSalesAnalysisDayReportDMO.setStart(page*limit);
				shopSalesAnalysisDayReportDMO.setRows(limit);
			}
			List<ShopSalesAnalysisDayReportDMO> shopSalesAnalysisDayReportDMOList = shopSalesAnalysisDayReportDAO.selectByConditions(shopSalesAnalysisDayReportDMO);
			String shopOrderJsonListString = JSON.toJSONString(shopSalesAnalysisDayReportDMOList);
			List<ShopOrderAnalysisDayReportListResDTO> shopOrderJsonList = JSON.parseObject(shopOrderJsonListString,new TypeReference<ArrayList<ShopOrderAnalysisDayReportListResDTO>>(){});
			shopOrderAnalsisDayReportResDTO.setShopOrderAnalysisDayReportListResDTOList(shopOrderJsonList);
			shopOrderAnalsisDayReportResDTO.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
			shopOrderAnalsisDayReportResDTO.setReponseMsg(ResultCodeEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			shopOrderAnalsisDayReportResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			shopOrderAnalsisDayReportResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法ShopOrderAnalysisReportServiceImpl.queryShopOrderAnalsisDayReportList出现异常{}",
					shopOrderAnalysisDayReportReqDTO.getMessageId(), w.toString());
		}
		return shopOrderAnalsisDayReportResDTO;
	}
}
