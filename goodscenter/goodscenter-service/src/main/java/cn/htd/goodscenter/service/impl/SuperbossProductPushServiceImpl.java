package cn.htd.goodscenter.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.common.constants.Constants;
import cn.htd.goodscenter.common.constants.ErrorCodes;
import cn.htd.goodscenter.common.constants.ResultCodeEnum;
import cn.htd.goodscenter.dao.SuperbossProductPushDAO;
import cn.htd.goodscenter.domain.SuperbossProductPush;
import cn.htd.goodscenter.dto.ItemSkuDTO;
import cn.htd.goodscenter.dto.SuperbossProductPushDTO;
import cn.htd.goodscenter.service.ItemSkuExportService;
import cn.htd.goodscenter.service.SuperbossProductPushService;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.service.MemberBaseInfoService;

@Service("superbossProductPushService")
public class SuperbossProductPushServiceImpl implements SuperbossProductPushService {

	private static final Logger logger = LoggerFactory.getLogger(SuperbossProductPushServiceImpl.class);

	@Resource
	private SuperbossProductPushDAO superbossProductPushDAO;

	@Resource
	private MemberBaseInfoService memberBaseInfoService;

	@Resource
	private ItemSkuExportService itemSkuExportService;

	@Override
	public ExecuteResult<DataGrid<SuperbossProductPushDTO>> querySuperbossProductPushList(
			SuperbossProductPushDTO superbossProductPushDTO, Pager<SuperbossProductPushDTO> pager) {
		ExecuteResult<DataGrid<SuperbossProductPushDTO>> result = new ExecuteResult<DataGrid<SuperbossProductPushDTO>>();
		if (superbossProductPushDTO == null) {
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("SkuCode")));
			return result;
		}
		DataGrid<SuperbossProductPushDTO> dtoDataGrid = new DataGrid<SuperbossProductPushDTO>();
		logger.info("查询超级老板APP推荐商品信息开始--------入参为：" + JSON.toJSONString(superbossProductPushDTO));
		long count = superbossProductPushDAO.querySuperbossProductPushListCount(superbossProductPushDTO);
		List<SuperbossProductPush> productList = superbossProductPushDAO
				.querySuperbossProductPushList(superbossProductPushDTO, pager);
		List<SuperbossProductPushDTO> productListDTO = new ArrayList<SuperbossProductPushDTO>();
		if (CollectionUtils.isNotEmpty(productList)) {
			String marketCouponListString = JSON.toJSONString(productList);
			productListDTO = JSON.parseObject(marketCouponListString,
					new TypeReference<ArrayList<SuperbossProductPushDTO>>() {
					});
		}
		dtoDataGrid.setRows(this.resolveCidBrand(productListDTO));
		dtoDataGrid.setTotal(count);
		result.setResult(dtoDataGrid);
		return result;
	}

	@Override
	public ExecuteResult<String> insertSuperbossProductPush(SuperbossProductPushDTO superbossProductPushDTO) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			if (superbossProductPushDTO == null) {
				result.setCode(ErrorCodes.E10000.name());
				result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("SkuCode")));
				return result;
			}
			// 查询汇通达0801公司的sellerId
			Long sellerId_0801 = null;
			ExecuteResult<List<MemberBaseInfoDTO>> listExecuteResult = this.memberBaseInfoService
					.getMemberInfoByCompanyCode("0801", "2");
			if (listExecuteResult.isSuccess()) {
				List<MemberBaseInfoDTO> memberBaseInfoDTOList = listExecuteResult.getResult();
				if (memberBaseInfoDTOList != null && memberBaseInfoDTOList.size() > 0) {
					sellerId_0801 = memberBaseInfoDTOList.get(0).getId();
				}
			} else {
				result.setCode(ResultCodeEnum.ERROR.getCode());
				result.setResultMessage(ResultCodeEnum.ERROR.getMessage());
				result.addErrorMsg("查询会员中心获取总部ID出错, 错误信息 : " + listExecuteResult.getErrorMessages());
				return result;
			}
			// 默认非0801的商品
			superbossProductPushDTO.setHtdFlag("0");
			// 校验新增推荐商品是否是0801的商品
			ExecuteResult<ItemSkuDTO> skuInfo = itemSkuExportService
					.queryItemSkuByCode(superbossProductPushDTO.getSkuCode());
			if (skuInfo.getResult() != null) {
				Long skuSellerId = skuInfo.getResult().getSellerId();
				if (sellerId_0801.equals(skuSellerId)) {
					superbossProductPushDTO.setHtdFlag("1");
				}
			}
			logger.info("插入超级老板APP推荐商品信息开始--------入参为：" + JSON.toJSONString(superbossProductPushDTO));
			superbossProductPushDAO.insertSuperbossProductPush(superbossProductPushDTO);
			result.setResult("success");
		} catch (Exception e) {
			logger.error("插入超级老板APP推荐商品信息异常:：" + e.getMessage());
			result.getErrorMessages().add(e.getMessage());
			result.setResult("fail");
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public ExecuteResult<String> updateSuperbossProductPush(SuperbossProductPushDTO superbossProductPushDTO) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			if (superbossProductPushDTO == null) {
				result.setCode(ErrorCodes.E10000.name());
				result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("SkuCode")));
				return result;
			}
			logger.info("更新超级老板APP推荐商品信息开始--------入参为：" + JSON.toJSONString(superbossProductPushDTO));
			superbossProductPushDAO.updateSuperbossProductPush(superbossProductPushDTO);
			result.setResult("success");
		} catch (Exception e) {
			logger.error("更新超级老板APP推荐商品信息异常:：" + e.getMessage());
			result.getErrorMessages().add(e.getMessage());
			result.setResult("fail");
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public ExecuteResult<String> deleteSuperbossProductPush(List<SuperbossProductPushDTO> superbossProductPushDTO) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			if (CollectionUtils.isEmpty(superbossProductPushDTO)) {
				result.setCode(ErrorCodes.E10000.name());
				result.setResult("fail");
				result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("SkuCode")));
				return result;
			}
			logger.info("删除超级老板APP推荐商品信息开始--------入参为：" + JSON.toJSONString(superbossProductPushDTO));
			for (SuperbossProductPushDTO product : superbossProductPushDTO) {
				product.setDeleteFlag(Constants.DELETE_FLAG);
				superbossProductPushDAO.deleteSuperbossProductPush(product);
			}
			result.setResult("success");
		} catch (Exception e) {
			logger.error("删除超级老板APP推荐商品信息异常:：" + e.getMessage());
			result.getErrorMessages().add(e.getMessage());
			result.setResult("fail");
			throw new RuntimeException(e);
		}
		return result;
	}

	public List<SuperbossProductPushDTO> resolveCidBrand(List<SuperbossProductPushDTO> list) {
		if (CollectionUtils.isNotEmpty(list)) {
			for (SuperbossProductPushDTO product : list) {
				product.setCidBrand(product.getCategoryName() + "、" + product.getBrandName());
			}
		}
		return list;
	}

	@Override
	public ExecuteResult<SuperbossProductPushDTO> querySuperbossProductPushInfo(
			SuperbossProductPushDTO superbossProductPushDTO) {
		ExecuteResult<SuperbossProductPushDTO> result = new ExecuteResult<SuperbossProductPushDTO>();
		SuperbossProductPush superbossProductPush = null;
		try {
			superbossProductPush = superbossProductPushDAO.querySuperbossProductPushInfo(superbossProductPushDTO);
			if (superbossProductPush != null) {
				String marketCouponListString = JSON.toJSONString(superbossProductPush);
				SuperbossProductPushDTO productListDTO = JSON.parseObject(marketCouponListString,
						new TypeReference<SuperbossProductPushDTO>() {
						});
				productListDTO.setCidBrand(productListDTO.getCategoryName() + "、" + productListDTO.getBrandName());
				result.setResult(productListDTO);
			}
		} catch (Exception e) {
			logger.error("查询超级老板APP推荐商品详情信息异常:：" + e.getMessage());
			result.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

}
