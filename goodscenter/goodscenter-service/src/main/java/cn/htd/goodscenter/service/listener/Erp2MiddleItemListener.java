package cn.htd.goodscenter.service.listener;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.dto.DictionaryInfo;
import cn.htd.common.middleware.MiddlewareInterfaceConstant;
import cn.htd.common.middleware.MiddlewareInterfaceUtil;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.goodscenter.dao.spu.ItemSpuMapper;
import cn.htd.goodscenter.domain.spu.ItemSpu;
import cn.htd.goodscenter.dto.enums.HtdItemStatusEnum;
import cn.htd.goodscenter.dto.listener.Erp2MiddleItemInDTO;
import cn.htd.goodscenter.dto.listener.InsertItemSpuCallbackDTO;
import cn.htd.goodscenter.service.utils.ItemCodeGenerator;

import com.alibaba.fastjson.JSON;

public class Erp2MiddleItemListener implements MessageListener{
	private Logger logger = LoggerFactory.getLogger(Erp2MiddleItemListener.class);
	
	@Resource
	private ItemSpuMapper itemSpuMapper;
	@Resource
	private DictionaryUtils dictionaryUtils;
	
	@Transactional
	@Override
	public void onMessage(Message message) {
		logger.info("Erp2MiddleItemListener enter ...");
		InsertItemSpuCallbackDTO insertItemSpuCallbackDTO=new InsertItemSpuCallbackDTO();
		Erp2MiddleItemInDTO erp2MiddleItem=parseMessage(new String(message.getBody()));
		try{
			if(message==null||message.getBody()==null){
				logger.info("Erp2MiddleItemListener message is null");
				return;
			}
			logger.info("Erp2MiddleItemListener enter:{}",JSON.toJSON(erp2MiddleItem));
			if(erp2MiddleItem == null||StringUtils.isEmpty(erp2MiddleItem.getProductCode())){
				return;
			}
			ItemSpu dbItemSpu=itemSpuMapper.queryItemSpuByErpCode(erp2MiddleItem.getProductCode());
			if(dbItemSpu!=null){
				buildItemSpu(erp2MiddleItem, dbItemSpu);
				dbItemSpu.setModifyId(0L);
				dbItemSpu.setModifyName("system");
				dbItemSpu.setModifyTime(new Date());
				itemSpuMapper.updateBySpuIdSelective(dbItemSpu);
				//发送回调请求
				String param="?productCode="+dbItemSpu.getSpuCode()+"&erpProductCode="+erp2MiddleItem.getProductCode()
						+"&flag=true&token="+MiddlewareInterfaceUtil.getAccessToken();
				String resultJson=MiddlewareInterfaceUtil.httpGet(MiddlewareInterfaceConstant.MIDDLEWARE_INSERT_PROD_CALLBACK_URL+param, Boolean.TRUE);
				logger.error(erp2MiddleItem.getProductCode()+"商品已经在中台存在，"+resultJson);
				return;
			}
			//组装spu
			ItemSpu itemSpu = new ItemSpu();
	        itemSpu.setSpuCode(ItemCodeGenerator.generateSpuCode());
	        buildItemSpu(erp2MiddleItem, itemSpu);
	        itemSpu.setCreateId(0L);
	        itemSpu.setCreateName("system");
	        itemSpu.setCreateTime(new Date());
	        itemSpu.setModifyId(0L);
	        itemSpu.setModifyName("system");
	        itemSpu.setModifyTime(new Date());
	        itemSpu.setStatus(HtdItemStatusEnum.PASS.getCode() + "");
	        itemSpu.setTaxRate(new BigDecimal("0.1700"));
	        itemSpuMapper.insertSelective(itemSpu);
	        insertItemSpuCallbackDTO.setFlag(true);
	        insertItemSpuCallbackDTO.setProductCode(itemSpu.getSpuCode());
			insertItemSpuCallbackDTO.setToken(MiddlewareInterfaceUtil.getAccessToken());
			insertItemSpuCallbackDTO.setErpProductCode(erp2MiddleItem.getProductCode());
			String resultJson = sendCallbackReq(insertItemSpuCallbackDTO, erp2MiddleItem);
			logger.info("Erp2MiddleItemListener enter insert prd callbacl resp:{}",resultJson);
			if(StringUtils.isEmpty(resultJson)){
				return;
			}
			Map result=(Map)JSONObject.toBean(JSONObject.fromObject(resultJson),Map.class);
			if(MapUtils.isEmpty(result)||result.get("code")==null||
					 !"1".equals(result.get("code").toString())){
				 //try again
				 logger.error("上行商品回调失败，错误码："+result.get("code"));
			 }
		} catch(Exception e){
			logger.error("Erp2MiddleItemListener::onMessage:", e);
			insertItemSpuCallbackDTO.setFlag(false);
			//发送回调请求
			this.sendCallbackReq(insertItemSpuCallbackDTO, erp2MiddleItem);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	}

	private String sendCallbackReq(InsertItemSpuCallbackDTO insertItemSpuCallbackDTO, Erp2MiddleItemInDTO erp2MiddleItem) {
		String result = null;
		try {
			//发送回调请求
			String param="?productCode="+insertItemSpuCallbackDTO.getProductCode()+"&erpProductCode="+erp2MiddleItem.getProductCode()
					+"&flag=" + insertItemSpuCallbackDTO.isFlag() + "&token="+ MiddlewareInterfaceUtil.getAccessToken();
			result = MiddlewareInterfaceUtil.httpGet(MiddlewareInterfaceConstant.MIDDLEWARE_INSERT_PROD_CALLBACK_URL+param, Boolean.TRUE);
		} catch (Exception e) {
			logger.error("sendCallbackReq error:", e);
		}
		return result;
	}


	private void buildItemSpu(Erp2MiddleItemInDTO erp2MiddleItem,
			ItemSpu itemSpu) {
		itemSpu.setSpuName(erp2MiddleItem.getProductName());// 模板名称
		itemSpu.setErpCode(erp2MiddleItem.getProductCode());//ERP_CODE
		//型号
		if(StringUtils.isNotEmpty(erp2MiddleItem.getProductModel())){
			itemSpu.setModelType(erp2MiddleItem.getProductModel());
		}
		//ERP一级编码
		if(StringUtils.isNotEmpty(erp2MiddleItem.getFirstCategreyCode())){
			itemSpu.setErpFirstCategoryCode(erp2MiddleItem.getFirstCategreyCode());
		}
		//ERP五级编码
		if(StringUtils.isNotEmpty(erp2MiddleItem.getFiveCategreyCode())){
			itemSpu.setErpFiveCategoryCode(erp2MiddleItem.getFiveCategreyCode());
		}
		
		//单位转换成字典对应
		if(StringUtils.isNotEmpty(erp2MiddleItem.getProductUnit())){
			DictionaryInfo unitInfo=dictionaryUtils.getDictionaryByName(DictionaryConst.TYPE_ITEM_UNIT,erp2MiddleItem.getProductUnit());
			if(unitInfo!=null){
				itemSpu.setUnit(unitInfo.getValue());// 单位
			}
		}
		
		if(StringUtils.isNumeric(erp2MiddleItem.getMiddleGroundThirdCategoryCode())){
			itemSpu.setCategoryId(Long.valueOf(erp2MiddleItem.getMiddleGroundThirdCategoryCode()));// 品类ID
		}
		if(StringUtils.isNumeric(erp2MiddleItem.getBrandCode())){
			itemSpu.setBrandId(Long.valueOf(erp2MiddleItem.getBrandCode()));// 品牌ID
		}
	}
	

  /**
   * 解析文本
   * @param message 文本
   * @return 结果集
   */
  private Erp2MiddleItemInDTO parseMessage(String message) {
	  Erp2MiddleItemInDTO erp2MiddleItemInDTO = null;
      try {
          final ObjectMapper mapper = new ObjectMapper();
          mapper.configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
          erp2MiddleItemInDTO = mapper.readValue(message, Erp2MiddleItemInDTO.class);
      } catch (Exception e) {
          logger.error("parseMessage error:", e);
      }
      return erp2MiddleItemInDTO;
  }

}
