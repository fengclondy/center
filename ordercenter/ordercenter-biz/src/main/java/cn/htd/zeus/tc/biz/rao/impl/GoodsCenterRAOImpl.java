package cn.htd.zeus.tc.biz.rao.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.htd.common.ExecuteResult;
import cn.htd.goodscenter.dto.indto.JudgeRecevieAddressInDTO;
import cn.htd.goodscenter.dto.mall.MallSkuInDTO;
import cn.htd.goodscenter.dto.mall.MallSkuOutDTO;
import cn.htd.goodscenter.dto.mall.MallSkuWithStockInDTO;
import cn.htd.goodscenter.dto.mall.MallSkuWithStockOutDTO;
import cn.htd.goodscenter.dto.stock.Order4StockChangeDTO;
import cn.htd.goodscenter.dto.vip.VipItemEntryInfoDTO;
import cn.htd.goodscenter.dto.vip.VipItemListInDTO;
import cn.htd.goodscenter.dto.vip.VipItemListOutDTO;
import cn.htd.goodscenter.service.ItemSkuExportService;
import cn.htd.goodscenter.service.mall.MallItemExportService;
import cn.htd.goodscenter.service.productplus.ProductPlusExportService;
import cn.htd.goodscenter.service.stock.SkuStockChangeExportService;
import cn.htd.goodscenter.service.vip.VipItemExportService;
import cn.htd.zeus.tc.biz.rao.GoodsCenterRAO;
import cn.htd.zeus.tc.common.enums.OrderStatusEnum;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.dto.othercenter.response.OtherCenterResDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCreateInfoReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCreateItemListInfoReqDTO;

@Service
public class GoodsCenterRAOImpl implements GoodsCenterRAO {

	@Autowired
	private MallItemExportService mallItemExportService;

	@Autowired
	private SkuStockChangeExportService skuStockChangeExportService;

	@Autowired
	private ItemSkuExportService itemSkuExportService;

	@Autowired
	private ProductPlusExportService productPlusExportService;

	@Autowired
	private VipItemExportService vipItemExportService;

	private static final Logger LOGGER = LoggerFactory.getLogger(GoodsCenterRAOImpl.class);

	/*
	 * 从商品中心查询商品基本信息
	 * 
	 * @param isHasDevRelation
	 * 
	 * @param orderItemTemp
	 * 
	 * @return ExecuteResult<List<SkuInfo4CartOutDTO>>
	 */
	@Override
	public OtherCenterResDTO<MallSkuWithStockOutDTO> queryMallItemDetailWithStock(
			OrderCreateItemListInfoReqDTO orderItemTemp, String site, String messageId) {
		OtherCenterResDTO<MallSkuWithStockOutDTO> otherCenterResDTO = new OtherCenterResDTO<MallSkuWithStockOutDTO>();
		try {
			MallSkuWithStockOutDTO mallSkuWithStockOutDTO = new MallSkuWithStockOutDTO();
			MallSkuWithStockInDTO mallSkuWithStockInDTO = new MallSkuWithStockInDTO();
			mallSkuWithStockInDTO.setSkuCode(orderItemTemp.getSkuCode());
			mallSkuWithStockInDTO.setIsBoxFlag(orderItemTemp.getIsBoxFlag());
			mallSkuWithStockInDTO.setCityCode(site);
			// MallSkuInDTO mallSkuInDTO = new MallSkuInDTO();
			// mallSkuInDTO.setSkuCode(skuCode);
			Long startTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{}查询商品中心(queryMallItemDetailWithStock查询商品基本信息)--组装查询参数开始:{}",
					messageId, JSONObject.toJSONString(mallSkuWithStockInDTO));
			ExecuteResult<MallSkuWithStockOutDTO> mallSkuWithStockOutResDTO = mallItemExportService
					.queryMallItemDetailWithStock(mallSkuWithStockInDTO);
			Long endTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{}查询商品中心(queryMallItemDetailWithStock查询商品基本信息)--商品中心返回结果:{}",
					messageId, JSONObject.toJSONString(mallSkuWithStockOutResDTO) + " 耗时:"
							+ (endTime - startTime));
			if (null != mallSkuWithStockOutResDTO.getResult()
					&& mallSkuWithStockOutResDTO.isSuccess() == true) {
				mallSkuWithStockOutDTO = mallSkuWithStockOutResDTO.getResult();
				otherCenterResDTO.setOtherCenterResult(mallSkuWithStockOutDTO);
				otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
			} else {
				// 没有查到数据
				LOGGER.warn(
						"MessageId:{}查询商品基本信息(queryMallItemDetailWithStock查询商品基本信息)-没有查到数据 参数:{}",
						messageId, JSONObject.toJSONString(mallSkuWithStockInDTO));
				otherCenterResDTO.setOtherCenterResponseCode(
						ResultCodeEnum.GOODSCENTER_QUERY_SKUINFO_NOT_RESULT.getCode());
				otherCenterResDTO.setOtherCenterResponseMsg(
						ResultCodeEnum.GOODSCENTER_QUERY_SKUINFO_NOT_RESULT.getMsg());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法GoodsCenterRAOImpl.queryMallItemDetailWithStock出现异常{}",
					messageId, w.toString());
			otherCenterResDTO.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return otherCenterResDTO;
	}

	/*
	 * 调用商品中心判断是否超出配送范围
	 */
	@Override
	public OtherCenterResDTO<String> isRecevieAddressInSaleRange(
			OrderCreateInfoReqDTO orderCreateInfoReqDTO,
			OrderCreateItemListInfoReqDTO orderItemTemp, String messageId) {
		OtherCenterResDTO<String> otherCenterResDTO = new OtherCenterResDTO<String>();
		JudgeRecevieAddressInDTO judgeRecevieAddressInDTO = new JudgeRecevieAddressInDTO();
		try {
			judgeRecevieAddressInDTO
					.setProvinceCode(orderCreateInfoReqDTO.getConsigneeAddressProvince());
			judgeRecevieAddressInDTO.setCityCode(orderCreateInfoReqDTO.getConsigneeAddressCity());
			judgeRecevieAddressInDTO
					.setDistrictCode(orderCreateInfoReqDTO.getConsigneeAddressDistrict());
			judgeRecevieAddressInDTO.setMessageId(messageId);
			judgeRecevieAddressInDTO.setSkuCode(orderItemTemp.getSkuCode());
			judgeRecevieAddressInDTO.setIsBoxFlag(orderItemTemp.getIsBoxFlag().toString());
			Long startTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{}查询商品中心(isRecevieAddressInSaleRange判断是否超出配送范围)--组装查询参数开始:{}",
					messageId, JSONObject.toJSONString(judgeRecevieAddressInDTO));
			ExecuteResult<String> isOutRangeRes = itemSkuExportService
					.isRecevieAddressInSaleRange(judgeRecevieAddressInDTO);
			Long endTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{}查询商品中心(isRecevieAddressInSaleRange判断是否超出配送范围)--返回结果:{}",
					messageId,
					JSONObject.toJSONString(isOutRangeRes) + " 耗时:" + (endTime - startTime));
			if (isOutRangeRes.isSuccess() == true) {// 未超出配送范围
				orderItemTemp.setIsOutDistribtion(
						Integer.parseInt(OrderStatusEnum.NOT_OUT_DISTRIBTION.getCode()));
			} else {
				orderItemTemp.setIsOutDistribtion(
						Integer.parseInt(OrderStatusEnum.OUT_DISTRIBTION.getCode()));
			}
			otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法GoodsCenterRAOImpl.isRecevieAddressInSaleRange出现异常{}",
					messageId, w.toString());
			otherCenterResDTO.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return otherCenterResDTO;
	}

	/*
	 * 调用商品中心批量锁定库存接口
	 */
	@Override
	public OtherCenterResDTO<String> batchReserveStock(
			List<Order4StockChangeDTO> order4StockChangeDTOs, String messageId) {
		OtherCenterResDTO<String> otherCenterResDTO = new OtherCenterResDTO<String>();
		try {
			Long startTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{}调用商品中心(batchReserveStock批量锁定库存)--组装查询参数开始:{}", messageId,
					JSONObject.toJSONString(order4StockChangeDTOs));
			ExecuteResult<String> batchReserveStockRes = skuStockChangeExportService
					.batchReserveStock(order4StockChangeDTOs);
			Long endTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{}调用商品中心(batchReserveStock批量锁定库存)--返回结果:{}", messageId,
					JSONObject.toJSONString(batchReserveStockRes) + " 耗时:" + (endTime - startTime));
			if (batchReserveStockRes.isSuccess() == true) {
				otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
			} else {
				LOGGER.warn("MessageId:{} 调用商品中心(batchReserveStock批量锁定库存)-返回错误码和错误信息:{}", messageId,
						ResultCodeEnum.GOODSCENTER_BATCH_RESERVE_STOCK_FAILED.getCode()
								+ ResultCodeEnum.GOODSCENTER_BATCH_RESERVE_STOCK_FAILED.getMsg());
				otherCenterResDTO.setOtherCenterResponseCode(
						ResultCodeEnum.GOODSCENTER_BATCH_RESERVE_STOCK_FAILED.getCode());
				otherCenterResDTO.setOtherCenterResponseMsg(
						ResultCodeEnum.GOODSCENTER_BATCH_RESERVE_STOCK_FAILED.getMsg());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法GoodsCenterRAOImpl.batchReserveStock出现异常{}", messageId,
					w.toString());
			otherCenterResDTO.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return otherCenterResDTO;
	}

	/*
	 * 调商品中心批量释放库存
	 */
	@Override
	public OtherCenterResDTO<String> batchReleaseStock(
			List<Order4StockChangeDTO> order4StockChangeDTOs, String messageId) {
		OtherCenterResDTO<String> otherCenterResDTO = new OtherCenterResDTO<String>();
		try {
			Long startTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{}调用商品中心(batchReleaseStock批量释放库存)--组装查询参数开始:{}", messageId,
					JSONObject.toJSONString(order4StockChangeDTOs));
			ExecuteResult<String> batchReleaseStockRes = skuStockChangeExportService
					.batchReleaseStock(order4StockChangeDTOs);
			Long endTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{}调用商品中心(batchReleaseStock批量释放库存)--返回结果:{}", messageId,
					JSONObject.toJSONString(batchReleaseStockRes) + " 耗时:" + (endTime - startTime));
			if (batchReleaseStockRes.isSuccess() == true) {
				otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
			} else {
				LOGGER.warn("MessageId:{} 调用商品中心(batchReleaseStock批量释放库存)-返回错误码和错误信息:{}", messageId,
						ResultCodeEnum.GOODSCENTER_BATCH_RELEASE_STOCK_FAILED.getCode()
								+ ResultCodeEnum.GOODSCENTER_BATCH_RELEASE_STOCK_FAILED.getMsg());
				otherCenterResDTO.setOtherCenterResponseCode(
						ResultCodeEnum.GOODSCENTER_BATCH_RELEASE_STOCK_FAILED.getCode());
				otherCenterResDTO.setOtherCenterResponseMsg(
						ResultCodeEnum.GOODSCENTER_BATCH_RELEASE_STOCK_FAILED.getMsg());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法GoodsCenterRAOImpl.batchReleaseStock出现异常{}", messageId,
					w.toString());
			otherCenterResDTO.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return otherCenterResDTO;
	}

	/*
	 * 调商品中心批量释放库存-只是用messageId幂等性
	 */
	// @Override
	// public OtherCenterResDTO<String> comboChangeStock(
	// List<Order4StockChangeDTO> order4StockChangeDTOs, String messageId) {
	// OtherCenterResDTO<String> otherCenterResDTO = new
	// OtherCenterResDTO<String>();
	// try {
	// Long startTime = System.currentTimeMillis();
	// LOGGER.info("MessageId:{}调用商品中心(comboChangeStock批量释放库存)--组装查询参数开始:{}",
	// messageId,
	// JSONObject.toJSONString(order4StockChangeDTOs));
	// for (Order4StockChangeDTO order4StockChangeDTO : order4StockChangeDTOs) {
	// ExecuteResult<String> comboChangeStock = skuStockChangeExportService
	// .changePriceStock(order4StockChangeDTO);
	// Long endTime = System.currentTimeMillis();
	// LOGGER.info("MessageId:{}调用商品中心(comboChangeStock批量释放库存)--返回结果:{}",
	// messageId,
	// JSONObject.toJSONString(comboChangeStock) + " 耗时:" + (endTime -
	// startTime));
	// if (comboChangeStock.isSuccess() == true) {
	// otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
	// } else {
	// LOGGER.warn("MessageId:{} 调用商品中心(comboChangeStock批量释放库存)-返回错误码和错误信息:{}",
	// messageId,
	// ResultCodeEnum.GOODSCENTER_RELEASE_RECHARGE_STOCK_FAILED.getCode()
	// + ResultCodeEnum.GOODSCENTER_RELEASE_RECHARGE_STOCK_FAILED
	// .getMsg());
	// otherCenterResDTO.setOtherCenterResponseCode(
	// ResultCodeEnum.GOODSCENTER_RELEASE_RECHARGE_STOCK_FAILED.getCode());
	// otherCenterResDTO.setOtherCenterResponseMsg(
	// ResultCodeEnum.GOODSCENTER_RELEASE_RECHARGE_STOCK_FAILED.getMsg());
	// }
	// }
	// } catch (Exception e) {
	// StringWriter w = new StringWriter();
	// e.printStackTrace(new PrintWriter(w));
	// LOGGER.error("MessageId:{}
	// 调用方法GoodsCenterRAOImpl.comboChangeStock出现异常{}", messageId,
	// w.toString());
	// otherCenterResDTO.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
	// otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
	// }
	// return otherCenterResDTO;
	// }

	/*
	 * 调商品中心批量扣减库存
	 */
	@Override
	public OtherCenterResDTO<String> batchReduceStock(
			List<Order4StockChangeDTO> order4StockChangeDTOs, String messageId) {
		OtherCenterResDTO<String> otherCenterResDTO = new OtherCenterResDTO<String>();
		try {
			Long startTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{}调用商品中心(batchReduceStock批量扣减库存)--组装查询参数开始:{}", messageId,
					JSONObject.toJSONString(order4StockChangeDTOs));
			ExecuteResult<String> batchReduceStockRes = skuStockChangeExportService
					.batchReduceStock(order4StockChangeDTOs);
			Long endTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{}调用商品中心(batchReduceStock批量扣减库存)--返回结果:{}", messageId,
					JSONObject.toJSONString(batchReduceStockRes) + " 耗时:" + (endTime - startTime));
			if (batchReduceStockRes.isSuccess() == true) {
				otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
			} else {
				LOGGER.warn("MessageId:{} 调用商品中心(batchReduceStock批量扣减库存)-返回错误码和错误信息:{}", messageId,
						ResultCodeEnum.GOODSCENTER_BATCH_REDUCE_STOCK_FAILED.getCode()
								+ ResultCodeEnum.GOODSCENTER_BATCH_REDUCE_STOCK_FAILED.getMsg());
				otherCenterResDTO.setOtherCenterResponseCode(
						ResultCodeEnum.GOODSCENTER_BATCH_REDUCE_STOCK_FAILED.getCode());
				otherCenterResDTO.setOtherCenterResponseMsg(
						ResultCodeEnum.GOODSCENTER_BATCH_REDUCE_STOCK_FAILED.getMsg());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法GoodsCenterRAOImpl.batchReduceStock出现异常{}", messageId,
					w.toString());
			otherCenterResDTO.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return otherCenterResDTO;
	}

	/*
	 * 调商品中心批量回滚库存
	 */
	@Override
	public OtherCenterResDTO<String> batchRollbackStock(
			List<Order4StockChangeDTO> order4StockChangeDTOs, String messageId) {
		OtherCenterResDTO<String> otherCenterResDTO = new OtherCenterResDTO<String>();
		try {
			Long startTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{}调用商品中心(batchRollbackStock批量回滚库存)--组装查询参数开始:{}", messageId,
					JSONObject.toJSONString(order4StockChangeDTOs));
			ExecuteResult<String> batchRollbackStockRes = skuStockChangeExportService
					.batchRollbackStock(order4StockChangeDTOs);
			Long endTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{}调用商品中心(batchRollbackStock批量回滚库存)--返回结果:{}", messageId,
					JSONObject.toJSONString(batchRollbackStockRes) + " 耗时:"
							+ (endTime - startTime));
			if (batchRollbackStockRes.isSuccess() == true) {
				otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
			} else {
				LOGGER.warn("MessageId:{} 调用商品中心(batchRollbackStock批量回滚库存)-返回错误码和错误信息:{}",
						messageId, ResultCodeEnum.GOODSCENTER_BATCH_ROLLBACK_STOCK_FAILED.getCode()
								+ ResultCodeEnum.GOODSCENTER_BATCH_ROLLBACK_STOCK_FAILED.getMsg());
				otherCenterResDTO.setOtherCenterResponseCode(
						ResultCodeEnum.GOODSCENTER_BATCH_ROLLBACK_STOCK_FAILED.getCode());
				otherCenterResDTO.setOtherCenterResponseMsg(
						ResultCodeEnum.GOODSCENTER_BATCH_ROLLBACK_STOCK_FAILED.getMsg());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法GoodsCenterRAOImpl.batchRollbackStock出现异常{}", messageId,
					w.toString());
			otherCenterResDTO.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return otherCenterResDTO;
	}

	/**
	 * 查询商品详细信息列表
	 * 
	 * @param mallSkuWithStockInDTOList
	 * @param messageId
	 * @return
	 */
	public OtherCenterResDTO<List<MallSkuWithStockOutDTO>> getMallItemInfoList(
			List<MallSkuWithStockInDTO> mallSkuWithStockInDTOList, String messageId) {
		OtherCenterResDTO<List<MallSkuWithStockOutDTO>> other = new OtherCenterResDTO<List<MallSkuWithStockOutDTO>>();
		List<MallSkuWithStockOutDTO> mallSkuList = new ArrayList<MallSkuWithStockOutDTO>();
		try {
			Long startTime = System.currentTimeMillis();
			LOGGER.info("商品中心--查询商品sku详细信息--组装查询参数开始:" + "MessageId:" + messageId);
			ExecuteResult<List<MallSkuWithStockOutDTO>> mallSku = mallItemExportService
					.queryMallItemDetailWithStockList(mallSkuWithStockInDTOList);
			LOGGER.info("MessageId:{} 商品中心--查询商品sku详细信息--返回结果:{}", messageId,
					JSONObject.toJSONString(mallSku));
			Long endTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{} 商品中心--查询商品sku详细信息 耗时:{}", messageId, endTime - startTime);
			mallSkuList = mallSku.getResult();
			if (mallSkuList != null && mallSku.isSuccess() == true) {
				other.setOtherCenterResult(mallSkuList);
				other.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
				other.setOtherCenterResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
			} else {
				LOGGER.warn(
						"MessageId:{} 商品中心--查询商品sku详细信息(queryMallItemDetailWithStock查询商品基本信息)-没有查到数据 参数:{}",
						messageId, JSONObject.toJSONString(mallSku));
				other.setOtherCenterResponseCode(
						ResultCodeEnum.ORDERSETTLEMENT_PRODUCT_IS_NULL.getCode());
				other.setOtherCenterResponseMsg(
						ResultCodeEnum.ORDERSETTLEMENT_PRODUCT_IS_NULL.getMsg());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法GoodsCenterRAOImpl.getMallItemInfoList出现异常{}", messageId,
					w.toString());
			other.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			other.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return other;
	}

	@Override
	public OtherCenterResDTO<Boolean> canProductPlusSaleBySeller(Long sellerId,
			String productChannel, Long categoryId, Long brandId, String messageId) {
		OtherCenterResDTO<Boolean> other = new OtherCenterResDTO<Boolean>();
		try {
			Long startTime = System.currentTimeMillis();
			LOGGER.info("商品中心--查询商品sku详细信息--组装查询参数开始:" + "MessageId:" + messageId + " sellerId:"
					+ sellerId + " productChannel:" + productChannel + " categoryId:" + categoryId
					+ " brandId:" + brandId);
			ExecuteResult<Boolean> flag = productPlusExportService
					.canProductPlusSaleBySeller(sellerId, productChannel, categoryId, brandId);
			LOGGER.info("MessageId:{} 商品中心--查询商品sku详细信息--返回结果:{}", messageId, flag);
			Long endTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{} 商品中心--查询商品sku详细信息 耗时:{}", messageId, endTime - startTime);
			other.setOtherCenterResult(flag.getResult());
			other.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
			other.setOtherCenterResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法GoodsCenterRAOImpl.canProductPlusSaleBySeller出现异常{}",
					messageId, w.toString());
			other.setOtherCenterResult(false);
			other.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			other.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return other;
	}

	@Override
	public OtherCenterResDTO<List<VipItemEntryInfoDTO>> queryVipItemList(String vipSkuCode,
			String messageId) {
		OtherCenterResDTO<List<VipItemEntryInfoDTO>> other = new OtherCenterResDTO<List<VipItemEntryInfoDTO>>();
		try {
			Long startTime = System.currentTimeMillis();
			LOGGER.info("商品中心--查询VIP套餐--组装查询参数开始:" + "MessageId:" + messageId + " vipSkuCode:"
					+ vipSkuCode);
			VipItemListInDTO vipItemListInDTO = new VipItemListInDTO();
			ExecuteResult<List<VipItemListOutDTO>> vipItemRes = vipItemExportService
					.queryVipItemList(vipItemListInDTO);
			LOGGER.info("MessageId:{} 商品中心--查询VIP套餐--返回结果:{}", messageId,
					JSONObject.toJSONString(vipItemRes));
			Long endTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{} 商品中心--查询VIP套餐 耗时:{}", messageId, endTime - startTime);

			if (vipItemRes != null && null != vipItemRes.getResult()
					&& null != vipItemRes.getResult().get(0) && vipItemRes.isSuccess() == true) {
				List<VipItemEntryInfoDTO> vipItemEntryInfoDTOList = vipItemRes.getResult().get(0)
						.getVipItemEntryInfoList();
				other.setOtherCenterResult(vipItemEntryInfoDTOList);
				other.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
				other.setOtherCenterResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
			} else {
				LOGGER.warn("MessageId:{} 商品中心--查询VIP套餐 (queryVipItemList)-没有查到数据 参数:{}", messageId,
						JSONObject.toJSONString(vipSkuCode));
				other.setOtherCenterResponseCode(
						ResultCodeEnum.ORDERSETTLEMENT_PRODUCT_IS_NULL.getCode());
				other.setOtherCenterResponseMsg(
						"查询VIP套餐:" + ResultCodeEnum.ORDERSETTLEMENT_PRODUCT_IS_NULL.getMsg());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法GoodsCenterRAOImpl.queryVipItemList出现异常{}", messageId,
					w.toString());
			other.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			other.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return other;
	}

	@Override
	public OtherCenterResDTO<List<MallSkuOutDTO>> queryCartItemList(
			List<MallSkuInDTO> mallSkuInDTOList, String messageId) {
		OtherCenterResDTO<List<MallSkuOutDTO>> other = new OtherCenterResDTO<List<MallSkuOutDTO>>();
		try {
			Long startTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{}调用商品中心--根据skucode查询商品集合信息--组装查询参数开始:{}", messageId,
					JSONObject.toJSONString(mallSkuInDTOList));
			ExecuteResult<List<MallSkuOutDTO>> mallSkuOutExuList = mallItemExportService
					.queryCartItemList(mallSkuInDTOList);
			LOGGER.info("MessageId:{} 商品中心--根据skucode查询商品集合信息--返回结果:{}", messageId,
					JSONObject.toJSONString(mallSkuOutExuList));
			Long endTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{} 商品中心--根据skucode查询商品集合信息 耗时:{}", messageId,
					endTime - startTime);
			other.setOtherCenterResult(mallSkuOutExuList.getResult());
			other.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
			other.setOtherCenterResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法GoodsCenterRAOImpl.queryCartItemList出现异常{}", messageId,
					w.toString());
			other.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			other.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return other;
	}

}
