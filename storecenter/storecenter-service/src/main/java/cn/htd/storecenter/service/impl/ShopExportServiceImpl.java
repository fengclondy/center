package cn.htd.storecenter.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import cn.htd.common.util.SysProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.dao.util.RedisDB;
import cn.htd.storecenter.dao.ShopBrandDAO;
import cn.htd.storecenter.dao.ShopCategoryDAO;
import cn.htd.storecenter.dao.ShopInfoDAO;
import cn.htd.storecenter.dao.ShopModifyDetailDAO;
import cn.htd.storecenter.dto.ShopAuditInDTO;
import cn.htd.storecenter.dto.ShopBrandDTO;
import cn.htd.storecenter.dto.ShopCategoryDTO;
import cn.htd.storecenter.dto.ShopDTO;
import cn.htd.storecenter.dto.ShopInfoModifyInDTO;
import cn.htd.storecenter.dto.ShopModifyDetailDTO;
import cn.htd.storecenter.emums.ShopModifyColumnEnum;
import cn.htd.storecenter.service.ShopExportService;

@Service("shopExportService")
public class ShopExportServiceImpl implements ShopExportService {
	private final static Logger logger = LoggerFactory.getLogger(ShopExportServiceImpl.class);

	@Resource
	private ShopInfoDAO shopInfoDAO;
	@Resource
	private ShopBrandDAO shopBrandDAO;
	@Resource
	private ShopCategoryDAO shopCategoryDAO;
	@Resource
	private ShopModifyDetailDAO shopModifyDetailDAO;
	@Resource
	private RedisDB redisDB;

	@Override
	public ExecuteResult<String> saveShopInfo(ShopDTO shopDTO) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			shopDTO.setStatus("2");
			shopInfoDAO.insert(shopDTO);
			ShopDTO dto = new ShopDTO();
			dto.setShopId(shopDTO.getShopId());
			String shopUrl = SysProperties.getProperty("shop.url")+"/shop/"+shopDTO.getShopId();
			dto.setShopUrl(shopUrl);
			this.shopInfoDAO.update(dto);
			result.setResult(shopDTO.getShopId().toString());
			result.setResultMessage("success");
		} catch (Exception e) {
			result.setResultMessage("error");
			result.getErrorMessages().add(e.getMessage());
			logger.error(e.getMessage());
		}
		return result;
	}

	@Override
	public ExecuteResult<ShopDTO> findShopInfoById(long id) {
		ExecuteResult<ShopDTO> result = new ExecuteResult<ShopDTO>();
		try {
			ShopDTO shopInfo = shopInfoDAO.selectById(id);
			result.setResult(shopInfo);
			result.setResultMessage("success");
		} catch (Exception e) {
			result.setResultMessage("error");
			result.getErrorMessages().add(e.getMessage());
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public ExecuteResult<DataGrid<ShopDTO>> findShopInfoByCondition(ShopDTO shopDTO, Pager<ShopDTO> pager) {
		ExecuteResult<DataGrid<ShopDTO>> result = new ExecuteResult<DataGrid<ShopDTO>>();
		try {
			List<ShopDTO> listShopInfo = shopInfoDAO.selectListByCondition(shopDTO, pager);
			Long size = shopInfoDAO.selectCountByCondition(shopDTO);
			DataGrid<ShopDTO> dataGrid = new DataGrid<ShopDTO>();
			dataGrid.setRows(listShopInfo);
			dataGrid.setTotal(size);
			result.setResult(dataGrid);
			result.setResultMessage("success");
		} catch (Exception e) {
			result.setResultMessage("error");
			result.getErrorMessages().add(e.getMessage());
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}

		return result;
	}

	@Override
	public ExecuteResult<String> modifyShopInfostatus(ShopDTO dto) {
		ExecuteResult<String> result = new ExecuteResult<String>();

		try {
			this.shopInfoDAO.modifyShopInfostatus(dto);
			result.setResultMessage("success");
		} catch (Exception e) {
			result.setResultMessage("error");
			result.getErrorMessages().add(e.getMessage());
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}

		return result;
	}

	/**
	 * <p>
	 * Discription:[批量查询操作]
	 * </p>
	 */
	public ExecuteResult<List<ShopDTO>> queryShopInfoByids(ShopAuditInDTO shopAudiinDTO) {
		ExecuteResult<List<ShopDTO>> result = new ExecuteResult<List<ShopDTO>>();
		try {
			result.setResult(shopInfoDAO.queryShopInfoByIds(shopAudiinDTO));
			result.setResultMessage("success");
		} catch (Exception e) {
			result.setResultMessage("error");
			result.getErrorMessages().add(e.getMessage());
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	public ExecuteResult<List<ShopDTO>> queryShopByids(ShopAuditInDTO shopAudiinDTO) {
		ExecuteResult<List<ShopDTO>> result = new ExecuteResult<List<ShopDTO>>();
		try {
			result.setResult(shopInfoDAO.queryShopByIds(shopAudiinDTO));
			result.setResultMessage("success");
		} catch (Exception e) {
			result.setResultMessage("error");
			result.getErrorMessages().add(e.getMessage());
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public ExecuteResult<String> modifyShopInfoUpdate(ShopInfoModifyInDTO shopInfoModifyInDTO) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			// 修改类目
			if (shopInfoModifyInDTO.getShopCategoryList() != null
					&& shopInfoModifyInDTO.getShopCategoryList().size() > 0) {
				ShopCategoryDTO dto = new ShopCategoryDTO();
				dto.setModifyId(shopInfoModifyInDTO.getShopDTO().getModifyId());
				dto.setModifyName(shopInfoModifyInDTO.getShopDTO().getModifyName());
				dto.setShopId(shopInfoModifyInDTO.getShopDTO().getShopId());
				shopCategoryDAO.deleteByShopId(dto);
				for (ShopCategoryDTO shopCategoryDTO : shopInfoModifyInDTO.getShopCategoryList()) {
					shopCategoryDTO.setShopId(shopInfoModifyInDTO.getShopDTO().getShopId());
					shopCategoryDTO.setSellerId(shopInfoModifyInDTO.getShopDTO().getSellerId());
					shopCategoryDAO.insert(shopCategoryDTO);
				}
			}
			// 修改品牌
			if (shopInfoModifyInDTO.getShopBrandList() != null && shopInfoModifyInDTO.getShopBrandList().size() > 0) {
				shopBrandDAO.deleteByShopId(shopInfoModifyInDTO.getShopDTO().getShopId());
				for (ShopBrandDTO shopBrandDTO : shopInfoModifyInDTO.getShopBrandList()) {
					shopBrandDTO.setShopId(shopInfoModifyInDTO.getShopDTO().getShopId());
					shopBrandDTO.setSellerId(shopInfoModifyInDTO.getShopDTO().getSellerId());
					shopBrandDAO.insert(shopBrandDTO);
				}
			}
			shopInfoDAO.update(shopInfoModifyInDTO.getShopDTO());
			result.setResultMessage("success");
		} catch (Exception e) {
			result.setResultMessage("error");
			result.getErrorMessages().add(e.getMessage());
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}

		return result;
	}

	@Override
	public ExecuteResult<String> modifyShopInfo(ShopDTO dto) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {

			ShopDTO shopBefor = shopInfoDAO.selectById(dto.getShopId());
			shopInfoDAO.update(dto);
			this.isChange(dto, shopBefor);
			result.setResultMessage("success");
		} catch (Exception e) {
			result.setResultMessage("error");
			result.getErrorMessages().add(e.getMessage());
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}

		return result;
	}

	/**
	 *
	 * <p>
	 * Discription:[判断店铺信息是否变更 变更插入申请表]
	 * </p>
	 * 
	 * @param shopInfo
	 * @param shopBefor
	 */
	private void isChange(ShopDTO shopInfo, ShopDTO shopBefor) {
		ShopModifyDetailDTO smdDTO = new ShopModifyDetailDTO();
		smdDTO.setShopId(shopInfo.getShopId());
		smdDTO.setApplicantUserId(shopInfo.getSellerId());
		smdDTO.setApplicantUserName(shopInfo.getCreateName());
		smdDTO.setChangeTableId("shop_info");
		smdDTO.setCreateId(shopInfo.getCreateId());
		smdDTO.setCreateName(shopInfo.getCreateName());
		if (this.validaIsNotSameBoforeAndAfter(shopBefor.getShopName(), shopInfo.getShopName())) {
			// 店铺名称
			smdDTO.setContentName(ShopModifyColumnEnum.SHOP_NAME.getLabel());
			smdDTO.setChangeFieldId("shop_name");
			smdDTO.setBeforeChange(shopBefor.getShopName());
			smdDTO.setAfterChange(shopInfo.getShopName());
			shopModifyDetailDAO.insert(smdDTO);
		}
		if (this.validaIsNotSameBoforeAndAfter(shopBefor.getStatus(), shopInfo.getStatus())) {
			// 店铺状态
			smdDTO.setContentName(ShopModifyColumnEnum.SHOP_STATUS.getLabel());
			smdDTO.setChangeFieldId("status");
			smdDTO.setBeforeChange(shopBefor.getStatus());
			smdDTO.setAfterChange(shopInfo.getStatus());
			shopModifyDetailDAO.insert(smdDTO);
		}
		if (this.validaIsNotSameBoforeAndAfter(shopBefor.getShopType(), shopInfo.getShopType())) {
			// 店铺类型
			smdDTO.setContentName(ShopModifyColumnEnum.SHOP_TYPE.getLabel());
			smdDTO.setChangeFieldId("shop_type");
			smdDTO.setBeforeChange(shopBefor.getShopType());
			smdDTO.setAfterChange(shopInfo.getShopType());
			shopModifyDetailDAO.insert(smdDTO);
		}

		if (this.validaIsNotSameBoforeAndAfter(shopBefor.getBusinessType(), shopInfo.getBusinessType())) {
			// 经营类型
			smdDTO.setContentName(ShopModifyColumnEnum.BUSINESS_TYPE.getLabel());
			smdDTO.setChangeFieldId("business_type");
			smdDTO.setBeforeChange(shopBefor.getBusinessType());
			smdDTO.setAfterChange(shopInfo.getBusinessType());
			shopModifyDetailDAO.insert(smdDTO);
		}

		if (this.validaIsNotSameBoforeAndAfter(shopBefor.getDisclaimer(), shopInfo.getDisclaimer())) {
			// 免责声明
			smdDTO.setContentName(ShopModifyColumnEnum.DISCLAIMER.getLabel());
			smdDTO.setChangeFieldId("disclaimer");
			smdDTO.setBeforeChange(shopBefor.getDisclaimer());
			smdDTO.setAfterChange(shopInfo.getDisclaimer());
			shopModifyDetailDAO.insert(smdDTO);
		}

	}

	private boolean validaIsNotSameBoforeAndAfter(Object before, Object after) {

		if (before == null && after != null) {
			return true;
		} else if (before != null && after != null && !before.equals(after)) {
			return true;
		} else {
			return false;
		}
	}


	@Override
	public ExecuteResult<String> modifyShopInfoAndcbstatus(ShopDTO shopDTO) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {

			shopInfoDAO.modifyShopInfostatus(shopDTO);
			ShopBrandDTO sbDTO = new ShopBrandDTO();
			// 注释掉不需要的状态（这会引起驳回再审核通过状态不更新）
			// sbDTO.setStatus(1);
			sbDTO.setShopId(shopDTO.getShopId());
			List<ShopBrandDTO> brandList = shopBrandDAO.selectListByConditionAll(sbDTO, null);
			for (ShopBrandDTO sbDto1 : brandList) {
				ShopBrandDTO shopBrandDTO = new ShopBrandDTO();
				shopBrandDTO.setId(sbDto1.getId());
				shopBrandDTO.setStatus(shopDTO.getStatus());
				shopBrandDAO.modifyShopCategoryStatus(shopBrandDTO);
			}
			ShopCategoryDTO scDTO = new ShopCategoryDTO();
			// 注释掉不需要的状态（这会引起驳回再审核通过状态不更新）
			// scDTO.setStatus(1);
			scDTO.setShopId(shopDTO.getShopId());
			List<ShopCategoryDTO> sclist = shopCategoryDAO.selectListByConditionAll(scDTO, null);
			for (ShopCategoryDTO scDto1 : sclist) {
				ShopCategoryDTO shopCategoryDTO = new ShopCategoryDTO();
				shopCategoryDTO.setId(scDto1.getId());
				shopCategoryDTO.setStatus(shopDTO.getStatus());
				shopCategoryDAO.modifyShopCategoryStatus(shopCategoryDTO);
			}
			result.setResultMessage("success");
		} catch (Exception e) {
			result.setResultMessage("error");
			result.getErrorMessages().add(e.getMessage());
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public ExecuteResult<String> modifyShopStatus(ShopDTO dto) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			shopInfoDAO.modifyShopInfostatus(dto);
			result.setResultMessage("success");
		} catch (Exception e) {
			result.setResultMessage("error");
			result.getErrorMessages().add(e.getMessage());
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public ExecuteResult<String> modifyShopStatusBack(ShopDTO dto) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			dto.setStatus("2");
			shopInfoDAO.modifyShopInfostatus(dto);
			result.setResultMessage("success");
		} catch (Exception e) {
			result.setResultMessage("error");
			result.getErrorMessages().add(e.getMessage());
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public ExecuteResult<DataGrid<ShopDTO>> queryShopInfoByBrandId(Long brandId, Pager<ShopDTO> page) {
		ExecuteResult<DataGrid<ShopDTO>> result = new ExecuteResult<DataGrid<ShopDTO>>();
		try {
			DataGrid<ShopDTO> dataGrid = new DataGrid<ShopDTO>();
			List<ShopDTO> list = shopInfoDAO.queryShopInfoByBrandId(brandId, page);
			Long count = shopInfoDAO.queryCountShopInfoByBrandId(brandId);
			dataGrid.setRows(list);
			dataGrid.setTotal(count);
			result.setResult(dataGrid);
			result.setResultMessage("success");
		} catch (Exception e) {
			result.setResultMessage("error");
			result.getErrorMessages().add(e.getMessage());
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public ExecuteResult<String> addSecondDomainToRedis(String shopUrl, Long shopId) {
		ExecuteResult<String> exeResult = new ExecuteResult<String>();
		exeResult.setResultMessage("success");
		if (!redisDB.exists(REDIS_SHOP_SECOND_DOMAIN)) {
			addSecondDomainToRedis();
			return exeResult;
		}
		if (StringUtils.isNotBlank(shopUrl) && null != shopId) {
			redisDB.setHash(REDIS_SHOP_SECOND_DOMAIN, shopId.toString(), shopUrl);
		}
		return exeResult;
	}

	@Override
	public ExecuteResult<String> addSecondDomainToRedis() {
		ExecuteResult<String> exeResult = new ExecuteResult<String>();
		exeResult.setResultMessage("success");
		ExecuteResult<DataGrid<ShopDTO>> result = this.findShopInfoByCondition(null, null);
		if (result == null) {
			return exeResult;
		}
		DataGrid<ShopDTO> dategrid = result.getResult();
		if (dategrid == null) {
			return exeResult;
		}
		List<ShopDTO> shopList = dategrid.getRows();
		if (null == shopList) {
			return exeResult;
		}
		int size = shopList.size();
		if (size == 0) {
			return exeResult;
		}
		for (int i = 0; i < size; i++) {
			ShopDTO shopDTO = shopList.get(i);
			if (StringUtils.isNotBlank(shopDTO.getShopUrl()) && null != shopDTO.getShopId()) {
				redisDB.setHash(REDIS_SHOP_SECOND_DOMAIN, shopDTO.getShopId().toString(), shopDTO.getShopUrl());
			}
		}
		return exeResult;
	}

	@Override
	public Long queryStayCheckCount(ShopDTO shopDTO) {
		return shopInfoDAO.selectCountByCondition(shopDTO);
	}


	@Override
	public List<ShopDTO> queryShopInfoBySyncTime(Date syncTime, Pager<ShopDTO> page) {
		return shopInfoDAO.queryShopInfoBySyncTime(syncTime, page);
	}

	@Override
	public ExecuteResult<ShopDTO> queryBySellerId(Long sellerId) {
		ExecuteResult<ShopDTO> result = new ExecuteResult<ShopDTO>();
		try{
			ShopDTO dto = shopInfoDAO.selectBySellerId(sellerId);
			if (dto == null) {
				result.addErrorMessage("根据商家ID:" + sellerId + " 没有已开通的店铺信息");
			} else {
				result.setResult(dto);
			}
		}catch (Exception e){
			result.addErrorMessage(e.getMessage());
			logger.error(e.getMessage());
		}
		return result;
	}


}
