package cn.htd.goodscenter.service.impl.vip;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.htd.common.ExecuteResult;
import cn.htd.goodscenter.common.constants.Constants;
import cn.htd.goodscenter.common.constants.ErrorCodes;
import cn.htd.goodscenter.common.constants.ResultCodeEnum;
import cn.htd.goodscenter.dao.ItemMybatisDAO;
import cn.htd.goodscenter.dao.ItemSkuDAO;
import cn.htd.goodscenter.dao.vip.VipItemEntryInfoMapper;
import cn.htd.goodscenter.domain.Item;
import cn.htd.goodscenter.domain.vip.VipItemEntryInfo;
import cn.htd.goodscenter.dto.ItemSkuDTO;
import cn.htd.goodscenter.dto.enums.HtdItemStatusEnum;
import cn.htd.goodscenter.dto.vip.VipItemAddInDTO;
import cn.htd.goodscenter.dto.vip.VipItemEntryInfoDTO;
import cn.htd.goodscenter.dto.vip.VipItemListInDTO;
import cn.htd.goodscenter.dto.vip.VipItemListOutDTO;
import cn.htd.goodscenter.service.vip.VipItemExportService;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.service.MemberBaseInfoService;

import com.google.common.collect.Lists;

@Service("vipItemExportService")
public class VipItemExportServiceImpl implements VipItemExportService{
	@Resource
	private ItemMybatisDAO itemMybatisDAO;
	@Resource
	private ItemSkuDAO itemSkuDAO;
	@Resource
	private VipItemEntryInfoMapper vipItemEntryInfoMapper;

	@Resource
	private MemberBaseInfoService memberBaseInfoService;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public ExecuteResult<List<VipItemListOutDTO>> queryVipItemList(VipItemListInDTO vipItemListInDTO) {
		logger.info("查询VIP套餐商品信息, 参数 : {}", JSONObject.fromObject(vipItemListInDTO));
		ExecuteResult<List<VipItemListOutDTO>> result = new ExecuteResult<List<VipItemListOutDTO>>();
		try {
			Long sellerId_0801 = null;
			ExecuteResult<List<MemberBaseInfoDTO>> listExecuteResult = this.memberBaseInfoService.getMemberInfoByCompanyCode("0801", "2");
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
			vipItemListInDTO.setSellerId(sellerId_0801);
			List<VipItemListOutDTO> vipItemList = itemMybatisDAO.queryVipItemList(vipItemListInDTO);
			if(CollectionUtils.isEmpty(vipItemList)){
				result.setCode(ErrorCodes.E10000.name());
				result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("vipItemList")));
				return result;
			}
			for(VipItemListOutDTO vipItemListOutDTO:vipItemList) {
				List<VipItemEntryInfoDTO> vipItemEntryInfoList=itemMybatisDAO.queryVipItemEntryDetailList(vipItemListOutDTO.getItemId());
				vipItemListOutDTO.setVipItemEntryInfoList(vipItemEntryInfoList);
			}
			result.setCode(ResultCodeEnum.SUCCESS.getCode());
			result.setResult(vipItemList);
		} catch (Exception e) {
			logger.error("查询VIP套餐商品信息出错, 错误信息", e);
			result.setCode(ResultCodeEnum.ERROR.getCode());
			result.setResultMessage(ResultCodeEnum.ERROR.getMessage());
			result.addErrorMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public ExecuteResult<ItemSkuDTO> queryItemInfoBySkuCode(String skuCode) {
		logger.info("根据商品编码选择VIP套餐子商品, 商品编码 : {}", skuCode);
		ExecuteResult<ItemSkuDTO> executeResult = new ExecuteResult();
		try {
			if (StringUtils.isEmpty(skuCode)) {
				executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
				executeResult.setResultMessage("商品SKU编码为空");
				executeResult.addErrorMessage("商品SKU编码为空");
				return executeResult;
			}
			Long sellerId_0801 = null;
			ExecuteResult<List<MemberBaseInfoDTO>> listExecuteResult = this.memberBaseInfoService.getMemberInfoByCompanyCode("0801", "2");
			if (listExecuteResult.isSuccess()) {
				List<MemberBaseInfoDTO> memberBaseInfoDTOList = listExecuteResult.getResult();
				if (memberBaseInfoDTOList != null && memberBaseInfoDTOList.size() > 0) {
					sellerId_0801 = memberBaseInfoDTOList.get(0).getId();
				}
			} else {
				executeResult.setCode(ResultCodeEnum.ERROR.getCode());
				executeResult.setResultMessage(ResultCodeEnum.ERROR.getMessage());
				executeResult.addErrorMsg("查询会员中心获取总部ID出错, 错误信息 : " + listExecuteResult.getErrorMessages());
				return executeResult;
			}
			ItemSkuDTO itemSkuDTO = this.itemSkuDAO.queryItemSkuDetailBySkuCode(skuCode);
			if (itemSkuDTO != null && sellerId_0801.equals(itemSkuDTO.getSellerId())) {
				executeResult.setResult(itemSkuDTO);
				executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
			} else {
				executeResult.setCode(ResultCodeEnum.OUTPUT_IS_NULL.getCode());
				executeResult.setResultMessage(ResultCodeEnum.OUTPUT_IS_NULL.getMessage());
				executeResult.addErrorMessage("商品不存在或者不是0801的商品");
				return executeResult;
			}
		} catch (Exception e) {
			logger.error("根据商品编码选择VIP套餐子商品出错, 错误信息 : ", e);
			executeResult.setCode(ResultCodeEnum.ERROR.getCode());
			executeResult.setResultMessage(ResultCodeEnum.ERROR.getMessage());
			executeResult.addErrorMessage(e.getMessage());
		}
		return executeResult;
	}

	@Transactional
	@Override
	public ExecuteResult<String> addOrModifyVipItemEntry(Long itemId, Integer vipItemType, Integer vipSyncFlag, List<VipItemAddInDTO> vipItemAddInDTOList) {
		logger.info("添加VIP套餐组成项, itemId : {}, vipItemType : {}, vipSyncFlag : {}, vipItemAddInDTOList : {}", new Object[]{itemId, vipItemType, vipSyncFlag, JSONArray.fromObject(vipItemAddInDTOList)});
		ExecuteResult<String> executeResult = new ExecuteResult();
		try {
			Item item = new Item();
			item.setItemId(itemId);
			item.setVipItemType(vipItemType);
			item.setVipSyncFlag(vipSyncFlag);
			item.setIsVipItem(1);
			int result = this.itemMybatisDAO.updateByPrimaryKeySelective(item);
			if (result == 0) {
				throw new Exception("新增套餐失败, 更新item失败, itemId : " + itemId);
			}
			/** 处理套餐商品上下架逻辑 **/
		    boolean changeFlag = false;
			List<VipItemEntryInfoDTO> nativeVipItemEntryInfoList = itemMybatisDAO.queryVipItemEntryDetailList(itemId);
			if (nativeVipItemEntryInfoList != null && nativeVipItemEntryInfoList.size() > 0) { // 如果原来该套餐商品有子项数据
				// 现在插入的数据和本地数据，组成元素发生变化
				Set<String> nativeSet = this.getVipItemEntryInfoSet(nativeVipItemEntryInfoList);
				Set<String> currentSet = this.getVipItemAddInfoSet(vipItemAddInDTOList);
				// 差集
				Set<String> resultSet = this.getDifferenceSet(nativeSet, currentSet);
				if (!resultSet.isEmpty()) { // 有不同的组成
					logger.info("添加VIP套餐组成项和原来组成元素有差异, 将下架套餐, item_id : {}", itemId);
					changeFlag = true;
				} else { // 组成相同
					// 元素没有变化查看价格是否有变动
					for (VipItemEntryInfoDTO vipItemEntryInfoDTO : nativeVipItemEntryInfoList) {
						for (VipItemAddInDTO vipItemAddInDTO : vipItemAddInDTOList) {
							// 比较元素相同的 基本价格和销售价格，如果有变动
							if (vipItemEntryInfoDTO.getSkuCode().equals(vipItemAddInDTO.getSkuCode())) {
								System.out.println(!vipItemEntryInfoDTO.getBasePrice().equals(vipItemAddInDTO.getBasePrice()));
								System.out.println(!vipItemEntryInfoDTO.getSalePrice().equals(vipItemAddInDTO.getSalePrice()));
								if (!vipItemEntryInfoDTO.getBasePrice().equals(vipItemAddInDTO.getBasePrice())
										|| !vipItemEntryInfoDTO.getSalePrice().equals(vipItemAddInDTO.getSalePrice())) {
									changeFlag = true;
									logger.info("添加VIP套餐组成项和原来组成元素相同, 但价格发生变化, 将下架套餐, item_id : {}, entry_sku_code : {}" , itemId, vipItemEntryInfoDTO.getSkuCode());
									break;
								}
							}
						}
					}
				}
			}
			if (changeFlag) {
				this.updateItemDownShelf(itemId);
			}
			// 删除该套餐的所有组成项，重新插入
			vipItemEntryInfoMapper.deleteAllVipItemEntryInfo(itemId);
			if (vipItemAddInDTOList != null && vipItemAddInDTOList.size() > 0) {
				for (VipItemAddInDTO vipItemAddInDTO : vipItemAddInDTOList) {
					VipItemEntryInfo vipItemEntryInfo = new VipItemEntryInfo();
					vipItemEntryInfo.setVipItemId(itemId);
					vipItemEntryInfo.setSkuCode(vipItemAddInDTO.getSkuCode());
					vipItemEntryInfo.setBasePrice(vipItemAddInDTO.getBasePrice());
					vipItemEntryInfo.setSalePrice(vipItemAddInDTO.getSalePrice());
					vipItemEntryInfo.setSupplierName(vipItemAddInDTO.getSupplierName());
					vipItemEntryInfo.setDeleteFlag(0);
					vipItemEntryInfo.setCreateId(0L);
					vipItemEntryInfo.setCreateName(Constants.SYSTEM_CREATE_NAME);
					// itemId + skuCode 组合唯一索引
					int result_entry = this.vipItemEntryInfoMapper.insert(vipItemEntryInfo);
					if (result_entry == 0) {
						throw new Exception("新增套餐组成项失败, 子商品SKU编码 : " + vipItemAddInDTO.getSkuCode());
					}
				}
			}
		} catch (Exception e) {
			logger.info("添加VIP套餐组成项出错, 错误信息 :", e);
			executeResult.setCode(ResultCodeEnum.ERROR.getCode());
			executeResult.setResultMessage(ResultCodeEnum.ERROR.getMessage());
			executeResult.addErrorMessage(e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return executeResult;
	}

	private Set<String> getVipItemEntryInfoSet(List<VipItemEntryInfoDTO> vipItemEntryInfoDTOList) {
		Set<String> set = new HashSet();
		if (vipItemEntryInfoDTOList != null) {
			for (VipItemEntryInfoDTO vipItemEntryInfoDTO : vipItemEntryInfoDTOList) {
				set.add(vipItemEntryInfoDTO.getSkuCode());
			}
		}
		return set;
	}

	private Set<String> getVipItemAddInfoSet(List<VipItemAddInDTO> vipItemAddInDTOList) {
		Set<String> set = new HashSet();
		if (vipItemAddInDTOList != null) {
			for (VipItemAddInDTO vipItemAddInDTO : vipItemAddInDTOList) {
				set.add(vipItemAddInDTO.getSkuCode());
			}
		}
		return set;
	}

	/**
	 * 算出两个集合的差集
	 * @param nativeSet
	 * @param curSet
	 * @return
	 */
	private Set<String> getDifferenceSet(Set<String> nativeSet, Set<String> curSet) {
		// nativeSet - curSet
		Set<String> result1 = new HashSet<String>();
		result1.clear();
		result1.addAll(nativeSet);
		result1.removeAll(curSet);
		// curSet - nativeSet
		Set<String> result2 = new HashSet<String>();
		result2.clear();
		result2.addAll(curSet);
		result2.removeAll(nativeSet);
		// 合并result1， result2
		Set<String> result = new HashSet<String>();
		result.addAll(result1);
		result.addAll(result2);
		return result;
	}


	/**
	 * 下架商品
	 * @param itemId
	 */
	private void updateItemDownShelf(Long itemId) {
		// 如果是已上架状态，更新为已下架
		Item item = this.itemMybatisDAO.queryItemByPk(itemId);
		if (item.getItemStatus().equals(HtdItemStatusEnum.SHELVED.getCode())) {
			Item itemParam = new Item();
			itemParam.setItemId(itemId);
			itemParam.setItemStatus(HtdItemStatusEnum.NOT_SHELVES.getCode());
			int result = this.itemMybatisDAO.updateByPrimaryKeySelective(itemParam);
			if (result == 0) {
				throw new RuntimeException("新增套餐失败, 更新item失败, itemId : " + itemId);
			}
			logger.info("下架套餐商品成功, 原itemStatus : {}", item.getItemStatus());
		} else {
			logger.info("不下架套餐商品, 因为原来商品状态不是上架状态, 原itemStatus : {}", item.getItemStatus());
		}
	}
}
