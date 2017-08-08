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

import cn.htd.zeus.tc.biz.dao.ShopOrderStatisticsDayReportDAO;
import cn.htd.zeus.tc.biz.dmo.ShopOrderStatisticsDayReportDMO;
import cn.htd.zeus.tc.biz.service.ShopOrderStatisticsReportService;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.dto.response.ShopOrderStatisticsDayReportListResDTO;
import cn.htd.zeus.tc.dto.response.ShopOrderStatisticsDayReportResDTO;
import cn.htd.zeus.tc.dto.resquest.ShopOrderStatisticsDayReportReqDTO;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * @author ly
 *
 */
@Service
public class ShopOrderStatisticsReportServiceImpl implements ShopOrderStatisticsReportService {

	@Autowired
	private ShopOrderStatisticsDayReportDAO shopOrderStatisticsDayReportDAO;

	private static final Logger LOGGER = LoggerFactory.getLogger(ShopOrderStatisticsReportServiceImpl.class);
	
	@Override
	public ShopOrderStatisticsDayReportResDTO queryShopOrderStatisticsDayReportList(
			ShopOrderStatisticsDayReportReqDTO shopOrderStatisticsDayReportReqDTO) {
		
		
		ShopOrderStatisticsDayReportResDTO shopOrderStatisticsDayReportResDTO = new ShopOrderStatisticsDayReportResDTO();
		
		try {
			ShopOrderStatisticsDayReportDMO shopOrderStatisticsDayReportDMO = new ShopOrderStatisticsDayReportDMO();
			int startTime = shopOrderStatisticsDayReportReqDTO.getStartTime();
			shopOrderStatisticsDayReportDMO.setStartTime(startTime);
			int endTime = shopOrderStatisticsDayReportReqDTO.getEndTime();
			shopOrderStatisticsDayReportDMO.setEndTime(endTime);
			long shopId = shopOrderStatisticsDayReportReqDTO.getShopId();
			shopOrderStatisticsDayReportDMO.setShopId(shopId);
			Integer page = shopOrderStatisticsDayReportReqDTO.getPage();
			Integer limit = shopOrderStatisticsDayReportReqDTO.getLimit();
			if(page!=null && limit!=null)
			{
				shopOrderStatisticsDayReportDMO.setStart(page*limit);
				shopOrderStatisticsDayReportDMO.setRows(limit);
			}
			List<ShopOrderStatisticsDayReportDMO> shopOrderStatisticsDayReportDMOList = shopOrderStatisticsDayReportDAO
					.selectByConditions(shopOrderStatisticsDayReportDMO);
			String shopOrderJsonListString = JSON.toJSONString(shopOrderStatisticsDayReportDMOList);
			List<ShopOrderStatisticsDayReportListResDTO> shopOrderJsonList = JSON.parseObject(
					shopOrderJsonListString,
					new TypeReference<ArrayList<ShopOrderStatisticsDayReportListResDTO>>() {
					});
			shopOrderStatisticsDayReportResDTO
					.setShopOrderStatisticsDayReportListResDTO(shopOrderJsonList);
			shopOrderStatisticsDayReportResDTO.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
			shopOrderStatisticsDayReportResDTO.setReponseMsg(ResultCodeEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			shopOrderStatisticsDayReportResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			shopOrderStatisticsDayReportResDTO.setReponseMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法ShopOrderStatisticsReportServiceImpl.queryShopOrderStatisticsDayReportList出现异常{}",
					shopOrderStatisticsDayReportResDTO.getMessageId(), w.toString());
		}
		return shopOrderStatisticsDayReportResDTO;
	}
}
