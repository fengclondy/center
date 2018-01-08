package cn.htd.membercenter.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.domain.ItemBrand;
import cn.htd.goodscenter.dto.ItemBrandDTO;
import cn.htd.goodscenter.dto.ItemCategoryDTO;
import cn.htd.goodscenter.service.ItemBrandExportService;
import cn.htd.goodscenter.service.ItemCategoryService;
import cn.htd.membercenter.common.constant.ErpStatusEnum;
import cn.htd.membercenter.common.constant.GlobalConstant;
import cn.htd.membercenter.common.constant.MemberCenterCodeEnum;
import cn.htd.membercenter.dao.MemberBaseDAO;
import cn.htd.membercenter.dao.MemberBusinessRelationDAO;
import cn.htd.membercenter.dto.MemberBaseDTO;
import cn.htd.membercenter.dto.MemberBusinessRelationDTO;
import cn.htd.membercenter.dto.MemberRelationSearchDTO;
import cn.htd.membercenter.dto.MyMemberDTO;
import cn.htd.membercenter.enums.AuditStatusEnum;
import cn.htd.membercenter.service.MemberBaseService;
import cn.htd.membercenter.service.MemberBusinessRelationService;
import cn.htd.storecenter.dto.ShopBrandDTO;
import cn.htd.storecenter.service.ShopBrandExportService;

@Service("memberBusinessRelationService")
public class MemberBusinessRelationServiceImpl implements MemberBusinessRelationService {

	private final static Logger logger = LoggerFactory.getLogger(MemberBusinessRelationServiceImpl.class);

	@Resource
	private MemberBaseDAO memberBaseDao;

	@Resource
	private MemberBusinessRelationDAO memberBusinessRelationDAO;

	@Autowired
	private ShopBrandExportService shopBrandExportService;

	@Autowired
	private ItemCategoryService itemCategoryService;

	@Autowired
	private ItemBrandExportService itemBrandExportService;

	@Resource
	private MemberBaseService memberBaseService;

	@Override
	public ExecuteResult<DataGrid<MemberBusinessRelationDTO>> queryMemberBusinessRelationListInfo(
			MemberBusinessRelationDTO memberBusinessRelationDTO, Pager<MemberBusinessRelationDTO> pager) {
		ExecuteResult<DataGrid<MemberBusinessRelationDTO>> rs = new ExecuteResult<DataGrid<MemberBusinessRelationDTO>>();
		try {
			DataGrid<MemberBusinessRelationDTO> dg = new DataGrid<MemberBusinessRelationDTO>();
			String buyerId = memberBusinessRelationDTO.getBuyerId();
			String sellerId = memberBusinessRelationDTO.getSellerId();
			if (StringUtils.isNotBlank(buyerId) && StringUtils.isNotBlank(sellerId)) {
				memberBusinessRelationDTO.setAuditStatus(AuditStatusEnum.PASSING_AUDIT.getCode());
				memberBusinessRelationDTO.setDeleteFlag(GlobalConstant.DELETED_FLAG_NO);
				List<MemberBusinessRelationDTO> businessList = memberBusinessRelationDAO
						.queryMemberBusinessRelationListInfo(memberBusinessRelationDTO, pager);
				long count = memberBusinessRelationDAO
						.queryMemberBusinessRelationListInfoCount(memberBusinessRelationDTO);
				try {
					if (businessList != null) {
						//dg.setRows(setValue4Relation(businessList));
						dg.setRows(setValue4RelationBatch(businessList));
						dg.setTotal(count);
						rs.setResult(dg);
					} else {
						rs.setResultMessage("要查询的数据不存在");
					}

					rs.setResultMessage("success");
				} catch (Exception e) {
					rs.addErrorMessage("查询品牌品类名称出错" + e);
					rs.setResultMessage("error");
				}
			} else {
				rs.setResultMessage("参数不全");
				rs.setResultMessage("error");
			}
		} catch (Exception e) {
			logger.error("MemberBusinessRelationServiceImpl----->queryMemberBusinessRelationListInfo=" + e);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	private List<MemberBusinessRelationDTO> setValueRelation(List<ShopBrandDTO> shopBrandDTOs) {
		List<Long> categoryList = new ArrayList<Long>();
		List<Long> brandList = new ArrayList<Long>();
		List<MemberBusinessRelationDTO> resultList = new ArrayList<MemberBusinessRelationDTO>();
		for (ShopBrandDTO mbr : shopBrandDTOs) {
			categoryList.add(mbr.getCategoryId());
			brandList.add(mbr.getBrandId());
		}
		ExecuteResult<List<ItemCategoryDTO>> categorysList = itemCategoryService.getCategoryListByCids(categoryList);
		ExecuteResult<List<ItemBrandDTO>> brandsList  =itemBrandExportService.queryItemBrandByIds(brandList.toArray(new Long[0]));
		if(categorysList.isSuccess() && brandsList.isSuccess()){
			for(ShopBrandDTO dto: shopBrandDTOs){
				MemberBusinessRelationDTO mbrDto = new MemberBusinessRelationDTO();
				mbrDto.setBrandId(dto.getBrandId());
				mbrDto.setCategoryId(dto.getCategoryId());
				for(ItemCategoryDTO itemCategory:categorysList.getResult()){
					if(dto.getCategoryId() == itemCategory.getCategoryCid()){
						mbrDto.setCategoryName(itemCategory.getCategoryCName());
					}
				}
				for(ItemBrandDTO itemBrand:brandsList.getResult()){
					if(dto.getBrandId() == itemBrand.getBrandId()){
						mbrDto.setBrandName(itemBrand.getBrandName());
					}
				}
				resultList.add(mbrDto);
			}
		}
		return resultList;
	}
	
	private List<MemberBusinessRelationDTO> setValue4Relation(List<MemberBusinessRelationDTO> businessList) {
		for (MemberBusinessRelationDTO mbr : businessList) {
			ExecuteResult<ItemCategoryDTO> category = itemCategoryService.getCategoryByCid(mbr.getCategoryId());
			if (category.getResult() != null) {
				mbr.setCategoryName(category.getResult().getCategoryCName());
			}
			ExecuteResult<ItemBrand> brand = itemBrandExportService.queryItemBrandById(mbr.getBrandId());
			if (brand.getResult() != null) {
				mbr.setBrandName(brand.getResult().getBrandName());
			}
		}
		return businessList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ExecuteResult<DataGrid<MemberBusinessRelationDTO>> queryMemberNoneBusinessRelationListInfo(
			MemberBusinessRelationDTO memberBusinessRelationDTO, Pager<MemberBusinessRelationDTO> pager) {
		ExecuteResult<DataGrid<MemberBusinessRelationDTO>> rs = new ExecuteResult<DataGrid<MemberBusinessRelationDTO>>();
		try {
			DataGrid<MemberBusinessRelationDTO> dg = new DataGrid<MemberBusinessRelationDTO>();
			String buyerId = memberBusinessRelationDTO.getBuyerId();
			String sellerId = memberBusinessRelationDTO.getSellerId();
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(buyerId) && StringUtils.isNotBlank(sellerId)) {
				Pager pg = new Pager();
				pg.setRows(99999);
				ShopBrandDTO shopBrandDTO = new ShopBrandDTO();
				shopBrandDTO.setSellerId(Long.valueOf(memberBusinessRelationDTO.getSellerId()));
				shopBrandDTO.setBrandId(memberBusinessRelationDTO.getBrandId());
				shopBrandDTO.setBrandIdList(memberBusinessRelationDTO.getBrandIdList());
				shopBrandDTO.setCategoryIdList(memberBusinessRelationDTO.getCategoryIdList());
				ExecuteResult<DataGrid<ShopBrandDTO>> result = shopBrandExportService.queryShopBrandAll(shopBrandDTO,
						null);
				List<ShopBrandDTO> shopList = result.getResult().getRows();
				if (CollectionUtils.isNotEmpty(shopList)) {
					memberBusinessRelationDTO.setAuditStatus(AuditStatusEnum.PASSING_AUDIT.getCode());
					memberBusinessRelationDTO.setDeleteFlag(GlobalConstant.DELETED_FLAG_NO);
					// 查询所有的已存在经营关系信息
					List<MemberBusinessRelationDTO> allBusinessList = memberBusinessRelationDAO
							.queryMemberBusinessRelationListInfo(memberBusinessRelationDTO, pg);
					// 查询已经删除的经营关系信息
					memberBusinessRelationDTO.setDeleteFlag(GlobalConstant.DELETED_FLAG_YES);
					List<MemberBusinessRelationDTO> deleteBusinessList = memberBusinessRelationDAO
							.queryMemberBusinessRelationListInfo(memberBusinessRelationDTO, pg);
					// 先从总的经营关系中去除掉所有的存在的经营关系再加上已删除的经营关系信息，保证刚被删除的经营关系显示靠前
					map = this.caculateRelationList(shopList, allBusinessList, deleteBusinessList, pager);
				}

				try {
					if (MapUtils.isNotEmpty(map)) {
						//dg.setRows(setValue4Relation((List<MemberBusinessRelationDTO>) map.get("relationList")));
						dg.setRows(setValue4RelationBatch((List<MemberBusinessRelationDTO>) map.get("relationList")));
						dg.setTotal(Long.valueOf(map.get("total").toString()));
						rs.setResult(dg);

					} else {
						rs.setResultMessage("要查询的数据不存在");
					}

					rs.setResultMessage("success");
				} catch (Exception e) {
					rs.setResultMessage("error");
					throw new RuntimeException(e);
				}
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			logger.error("MemberBusinessRelationServiceImpl----->queryMemberBusinessRelationListInfo=" + w.toString());
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	private Map<String, Object> caculateRelationList(List<ShopBrandDTO> shopList,
			List<MemberBusinessRelationDTO> allBusinessList, List<MemberBusinessRelationDTO> deleteBusinessList,
			Pager<MemberBusinessRelationDTO> pager) {
		List<MemberBusinessRelationDTO> result = new ArrayList<MemberBusinessRelationDTO>();
		List<MemberBusinessRelationDTO> resultList = new ArrayList<MemberBusinessRelationDTO>();
		if (CollectionUtils.isNotEmpty(shopList)) {
			for (ShopBrandDTO shop : shopList) {
				for (MemberBusinessRelationDTO relation : allBusinessList) {
					if (shop.getCategoryId() == relation.getCategoryId()
							&& shop.getBrandId() == relation.getBrandId()) {
						shop.setStatus("0");
					}
				}
				for (MemberBusinessRelationDTO relation : deleteBusinessList) {
					if (shop.getCategoryId() == relation.getCategoryId()
							&& shop.getBrandId() == relation.getBrandId()) {
						shop.setStatus("0");
					}
				}
			}
		}
		result.addAll(deleteBusinessList);
		for (ShopBrandDTO shop : shopList) {
			if (!"0".equals(shop.getStatus())) {
				MemberBusinessRelationDTO mbr = new MemberBusinessRelationDTO();
				mbr.setCategoryId(shop.getCategoryId());
				mbr.setBrandId(shop.getBrandId());
				result.add(mbr);
			}
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("total", result.size());
		int page = pager.getPage();
		int rows = pager.getRows();
		for (int i = 0; i < result.size(); i++) {
			if (i >= (page - 1) * rows && i < page * rows) {
				resultList.add(result.get(i));
			}
		}
		resultMap.put("relationList", resultList);
		return resultMap;
	}

	@Override
	public ExecuteResult<Boolean> deleteMemberBusinessRelationInfo(List<MemberBusinessRelationDTO> mbrList) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		if (CollectionUtils.isNotEmpty(mbrList)) {
			for (MemberBusinessRelationDTO mbrDTO : mbrList) {
				String memberId = mbrDTO.getBuyerId();
				String sellerId = mbrDTO.getSellerId();
				long brandId = mbrDTO.getBrandId();
				long categoryId = mbrDTO.getCategoryId();
				if (StringUtils.isNotBlank(memberId) && StringUtils.isNotBlank(sellerId) && brandId != 0
						&& categoryId != 0) {
					try {
						mbrDTO.setDeleteFlag(GlobalConstant.DELETED_FLAG_YES);
						memberBusinessRelationDAO.deleteMemberBusinessRelationInfo(mbrDTO);
						rs.setResultMessage("success");
						rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
					} catch (Exception e) {
						logger.error("MemberBusinessRelationServiceImpl----->deleteMemberBusinessRelationInfo=" + e);
						rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
						rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
					}
				} else {
					rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
					rs.addErrorMessage("参数不全！");
				}
			}
		}
		return rs;
	}

	@Override
	public ExecuteResult<Boolean> insertMemberBusinessRelationInfo(List<MemberBusinessRelationDTO> mbrList) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		if (CollectionUtils.isNotEmpty(mbrList)) {
			try {
				ExecuteResult<Boolean> res = this.queryMemberBoxRelationInfo(mbrList.get(0));
				if (!res.getResult()) {
					this.insertMeberBoxRelationInfo(mbrList.get(0));
				}
				for (MemberBusinessRelationDTO mbrDTO : mbrList) {
					String memberId = mbrDTO.getBuyerId();
					String sellerId = mbrDTO.getSellerId();
					long brandId = mbrDTO.getBrandId();
					long categoryId = mbrDTO.getCategoryId();
					if (StringUtils.isNotBlank(memberId) && StringUtils.isNotBlank(sellerId) && brandId != 0
							&& categoryId != 0) {

						MemberBusinessRelationDTO mbr = memberBusinessRelationDAO
								.queryMemberBusinessRelationDetail(mbrDTO);
						if (mbr != null) {
							mbrDTO.setDeleteFlag(GlobalConstant.DELETED_FLAG_NO);
							// 设置审核状态为审核通过
							mbrDTO.setAuditStatus(AuditStatusEnum.PASSING_AUDIT.getCode());
							memberBusinessRelationDAO.updateMemberBusinessRelationInfo(mbrDTO);
						} else {
							// 设置审核状态为审核通过
							mbrDTO.setAuditStatus(AuditStatusEnum.PASSING_AUDIT.getCode());
							memberBusinessRelationDAO.insertMemberBusinessRelationInfo(mbrDTO);
						}
						rs.setResultMessage("success");
						rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
					} else {
						rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
						rs.addErrorMessage("参数不全！");
					}
				}
			} catch (Exception e) {
				logger.error("MemberBusinessRelationServiceImpl----->insertMemberBusinessRelationInfo=" + e);
				rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
				rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
			}
		}
		return rs;
	}
	
	@Override
	public ExecuteResult<Boolean> updateCustomManager(List<MemberBusinessRelationDTO> mbrList){
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			for (MemberBusinessRelationDTO mbrDTO : mbrList) {
				String memberId = mbrDTO.getBuyerId();
				String sellerId = mbrDTO.getSellerId();
				long brandId = mbrDTO.getBrandId();
				long categoryId = mbrDTO.getCategoryId();
				if (StringUtils.isNotBlank(memberId) && StringUtils.isNotBlank(sellerId) && brandId != 0
						&& categoryId != 0) {
					mbrDTO.setDeleteFlag(GlobalConstant.DELETED_FLAG_NO);
					// 设置审核状态为审核通过
					mbrDTO.setAuditStatus(AuditStatusEnum.PASSING_AUDIT.getCode());
					memberBusinessRelationDAO.updateMemberBusinessRelationInfo(mbrDTO);
					rs.setResultMessage("success");
					rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
				} else {
					rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
					rs.addErrorMessage("参数不全！");
				}
			}
		} catch (Exception e) {
			logger.error("MemberBusinessRelationServiceImpl----->updateCustomManager=" + e);
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	@Override
	public ExecuteResult<MemberBaseDTO> queryMemberBusinessRelationPendingAudit(
			MemberBusinessRelationDTO memberBusinessRelationDTO) {
		ExecuteResult<MemberBaseDTO> rs = new ExecuteResult<MemberBaseDTO>();
		try {
			MemberBaseDTO mbd = new MemberBaseDTO();
			String buyerId = memberBusinessRelationDTO.getBuyerId();
			String sellerId = memberBusinessRelationDTO.getSellerId();
			if (StringUtils.isNotBlank(buyerId) && StringUtils.isNotBlank(sellerId)) {
				mbd.setMemberId(memberBusinessRelationDTO.getBuyerId());
				MemberBaseDTO memberBaseDTO = memberBaseDao.queryMemberBaseInfoById(mbd);
				if (StringUtils.isNotBlank(memberBaseDTO.getLocationTown())
						&& !memberBaseDTO.getLocationTown().equals("0")) {
					memberBaseDTO.setCompanyWorkAddress(
							memberBaseService.getAddressBaseByCode(memberBaseDTO.getLocationTown())
									+ memberBaseDTO.getLocationDetail());
				} else {
					memberBaseDTO.setCompanyWorkAddress(
							memberBaseService.getAddressBaseByCode(memberBaseDTO.getLocationCounty())
									+ memberBaseDTO.getLocationDetail());
				}

				memberBusinessRelationDTO.setAuditStatus(AuditStatusEnum.PENDING_AUDIT.getCode());
				List<MemberBusinessRelationDTO> relationList = memberBusinessRelationDAO
						.queryMemberBusinessRelationPendingAudit(memberBusinessRelationDTO);
				if (CollectionUtils.isNotEmpty(relationList)) {
					memberBaseDTO.setRelationList(relationList);
					rs.setResult(memberBaseDTO);
					rs.setResultMessage("success");
				} else {
					rs.setResultMessage("要查询的数据不存在");
				}
			} else {
				rs.setResultMessage("error");
				rs.setResultMessage("参数不正确");
			}
		} catch (Exception e) {
			logger.error("MemberBusinessRelationServiceImpl----->queryMemberBusinessRelationPendingAudit=" + e);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	@Override
	public ExecuteResult<Boolean> queryMemberBoxRelationInfo(MemberBusinessRelationDTO memberBusinessRelationDTO) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		String buyerId = memberBusinessRelationDTO.getBuyerId();
		String sellerId = memberBusinessRelationDTO.getSellerId();
		try {
			if (StringUtils.isNotBlank(sellerId) && StringUtils.isNotBlank(buyerId)) {
				long count = memberBusinessRelationDAO.queryMemberBoxRelationInfo(memberBusinessRelationDTO);
				if (count > 0) {
					rs.setResult(true);
					rs.setResultMessage("success");
				} else {
					rs.setResult(false);
					rs.setResultMessage("查询不到该记录");
				}
			} else {
				rs.setResult(false);
				rs.setResultMessage("参数不全");
			}
		} catch (Exception e) {
			logger.error("MemberBusinessRelationServiceImpl----->queryMemberBoxRelationInfo=" + e);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	@Override
	public ExecuteResult<Boolean> insertMeberBoxRelationInfo(MemberBusinessRelationDTO memberBusinessRelationDTO) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		String buyerId = memberBusinessRelationDTO.getBuyerId();
		String sellerId = memberBusinessRelationDTO.getSellerId();
		try {
			if (StringUtils.isNotBlank(sellerId) && StringUtils.isNotBlank(buyerId)) {
				memberBusinessRelationDTO.setErpStatus(ErpStatusEnum.PENDING.getValue());
				memberBusinessRelationDAO.insertMeberBoxRelationInfo(memberBusinessRelationDTO);
				rs.setResult(true);
				rs.setResultMessage("success");
			} else {
				rs.setResult(false);
				rs.setResultMessage("参数不全");
			}
		} catch (Exception e) {
			logger.error("MemberBusinessRelationServiceImpl----->insertMeberBoxRelationInfo=" + e);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}

		return rs;
	}

	@Override
	public ExecuteResult<String> getQueryMemberBusinessRelationCode(String messageId, Long buyerId, Long sellerId,
			Long categoryId, Long brandId) {
		// TODO Auto-generated method stub
		ExecuteResult<String> rs = new ExecuteResult<String>();
		try {
			List<MemberBusinessRelationDTO> list = memberBusinessRelationDAO.queryMemberBusinessRelationList(buyerId,
					sellerId, categoryId, brandId);
			if (list != null) {
				if (list.size() > 0) {
					rs.setCode(MemberCenterCodeEnum.BUSINESS_RELATIONSHIP.getCode());
					rs.setResult("success");
					rs.setResultMessage("买家和卖家有经营关系");
				} else {
					rs.setCode(MemberCenterCodeEnum.NO_BUSINESS_RELATIONSHIP.getCode());
					rs.setResult("fail");
					rs.setResultMessage("买家和卖家没有经营关系");
				}
			}
			logger.info(messageId);
		} catch (Exception e) {
			rs.setCode(MemberCenterCodeEnum.ERROR.getCode());
			rs.setResult("error");
			rs.setResultMessage("未知异常！");
			logger.error("MemberBusinessRelationServiceImpl----->getQueryMemberBusinessRelationList" + e);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	@Override
	public ExecuteResult<List<MemberBusinessRelationDTO>> selectMemberBussinsessRelationShip(Long memberId) {
		ExecuteResult<List<MemberBusinessRelationDTO>> rs = new ExecuteResult<List<MemberBusinessRelationDTO>>();
		try {
			List<MemberBusinessRelationDTO> list = memberBusinessRelationDAO
					.selectMemberBussinsessRelationShip(memberId);
			if (list != null) {
				if (list.size() > 0) {
					rs.setCode(MemberCenterCodeEnum.BUSINESS_RELATIONSHIP.getCode());
					rs.setResult(list);
					rs.setResultMessage("success");
				} else {
					rs.setCode(MemberCenterCodeEnum.NO_BUSINESS_RELATIONSHIP.getCode());
					rs.setResult(null);
					rs.setResultMessage("没查到数据");
					rs.addErrorMessage("fail");

				}
			} else {
				rs.setResultMessage("查询列表为null");
				rs.addErrorMessage("fail");
			}
		} catch (Exception e) {
			rs.setCode(MemberCenterCodeEnum.ERROR.getCode());
			rs.addErrorMessage("error");
			rs.setResultMessage("未知异常！");
			logger.error("MemberBusinessRelationServiceImpl----->selectMemberBussinsessRelationShip" + e);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	@Override
	public ExecuteResult<List<MemberBusinessRelationDTO>> queryCategoryIdAndBrandIdBySellerId(
			MemberBusinessRelationDTO memberBusinessRelationDTO) {
		ExecuteResult<List<MemberBusinessRelationDTO>> rs = new ExecuteResult<List<MemberBusinessRelationDTO>>();
		try {
			String sellerId = memberBusinessRelationDTO.getSellerId();
			if (StringUtils.isNotBlank(sellerId)) {
				//memberBusinessRelationDTO.setAuditStatus(AuditStatusEnum.PASSING_AUDIT.getCode());
//				memberBusinessRelationDTO.setDeleteFlag(GlobalConstant.DELETED_FLAG_NO);
//				List<MemberBusinessRelationDTO> businessList = memberBusinessRelationDAO
//						.queryCategoryIdAndBrandIdBySellerId(memberBusinessRelationDTO);
				ShopBrandDTO shopBrandDTO = new ShopBrandDTO();
				shopBrandDTO.setOrderByType(1);
				shopBrandDTO.setBrandIdList(memberBusinessRelationDTO.getBrandIdList());
				shopBrandDTO.setCategoryIdList(memberBusinessRelationDTO.getCategoryIdList());
				shopBrandDTO.setSellerId(Long.valueOf(memberBusinessRelationDTO.getSellerId()));
				ExecuteResult<DataGrid<ShopBrandDTO>> result = shopBrandExportService.queryShopBrandAll(shopBrandDTO,
						null);
				List<ShopBrandDTO> shopBrandDTOs = result.getResult().getRows();
				try {
					if (CollectionUtils.isNotEmpty(shopBrandDTOs)) {
						rs.setResult(setValueRelation(shopBrandDTOs)); 
					}else{
						rs.setResultMessage("要查询的数据不存在");
					}
					rs.setResultMessage("success");
				} catch (Exception e) {
					rs.setResultMessage("error");
					throw new RuntimeException(e);
				}
			} else {
				rs.setResultMessage("参数不全");
				rs.setResultMessage("error");
			}
		} catch (Exception e) {
			logger.error("MemberBusinessRelationServiceImpl----->queryCategoryIdAndBrandIdBySellerId=" + e);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	@Override
	public ExecuteResult<DataGrid<MyMemberDTO>> queryMemberBussinessByCategoryId(
			MemberRelationSearchDTO dto , Pager<MemberBusinessRelationDTO> pager) {
		ExecuteResult<DataGrid<MyMemberDTO>> rs = new ExecuteResult<DataGrid<MyMemberDTO>>();
		try {
			DataGrid<MyMemberDTO> dg = new DataGrid<MyMemberDTO>();
			String sellerId = dto.getSellerId();
			String brandId = dto.getBrandId();
			String categoryId = dto.getCategoryId();
			if (StringUtils.isNotBlank(sellerId) && StringUtils.isNotBlank(brandId) 
					&& StringUtils.isNotBlank(categoryId)) {
				List<String> buyerIdList = memberBusinessRelationDAO
						.queryMemberBussinessByCategoryId(dto);
				if(CollectionUtils.isNotEmpty(buyerIdList)){
					dto.setBuyerIdList(buyerIdList);
				}
				
				List<MyMemberDTO> memberInfoList = memberBusinessRelationDAO.queryMemberInfo(dto , pager);
				long count = memberBusinessRelationDAO.queryMemberInfoCount(dto);
				if(0 == dto.getShowType()){
					dto.setShowType(1);
				}else{
					dto.setShowType(0);
				}
				long reast = memberBusinessRelationDAO.queryMemberInfoCount(dto);
				if (CollectionUtils.isNotEmpty(memberInfoList)) {
					dg.setRows(memberInfoList);
					dg.setTotal(count);
					dg.setPageNum((int)reast);
					rs.setResult(dg);
				} else {
					rs.setResultMessage("要查询的数据不存在");
				}

				rs.setResultMessage("success");
			} else {
				rs.setResultMessage("参数不全");
				rs.setResultMessage("error");
			}
		} catch (Exception e) {
			logger.error("MemberBusinessRelationServiceImpl----->queryMemberBussinessByCategoryId=" + e);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}
	

	/**
	 * 优化经营关系查询方法（根据品牌品类id查询name）
	 * 
	 * @author li.jun
	 * @time 2017-12-28
	 * @param businessList
	 * @return
	 */
	private List<MemberBusinessRelationDTO> setValue4RelationBatch(List<MemberBusinessRelationDTO> businessList) {

		// 品类id列表
		List<Long> cidList = new ArrayList<Long>();
		// 品牌id列表
		List<Long> ids = new ArrayList<Long>();
		for (MemberBusinessRelationDTO mbr : businessList) {
			ids.add(mbr.getBrandId());
			cidList.add(mbr.getCategoryId());
		}
		ExecuteResult<List<ItemCategoryDTO>> categoryResult = itemCategoryService.getCategoryListByCids(cidList);
		ExecuteResult<List<ItemBrandDTO>> itemResult = itemBrandExportService
				.queryItemBrandByIds(ids.toArray(new Long[0]));
		List<ItemCategoryDTO> categoryList = categoryResult.getResult();
		List<ItemBrandDTO> itemList = itemResult.getResult();
		// 循环取品类名称
		if (categoryList != null && categoryList.size() > 0) {
			for (MemberBusinessRelationDTO mbr : businessList) {
				for (ItemCategoryDTO category : categoryList) {
					if (mbr.getCategoryId() == category.getCategoryCid()) {
						mbr.setCategoryName(category.getCategoryCName());
					}
				}
			}
		}
		// 循环取品牌名称
		if (itemList != null && itemList.size() > 0) {
			for (MemberBusinessRelationDTO mbr : businessList) {
				for (ItemBrandDTO item : itemList) {
					if (mbr.getBrandId() == item.getBrandId()) {
						mbr.setBrandName(item.getBrandName());
					}
				}
			}
		}

		return businessList;
	}
	
}
