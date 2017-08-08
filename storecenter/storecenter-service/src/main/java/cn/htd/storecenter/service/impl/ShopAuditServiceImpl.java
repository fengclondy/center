package cn.htd.storecenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;

import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.storecenter.dao.ShopBrandDAO;
import cn.htd.storecenter.dao.ShopCategorySellerDAO;
import cn.htd.storecenter.dao.ShopInfoDAO;
import cn.htd.storecenter.dto.ShopAuditDTO;
import cn.htd.storecenter.dto.ShopBrandDTO;
import cn.htd.storecenter.dto.ShopCategorySellerDTO;
import cn.htd.storecenter.dto.ShopDTO;
import cn.htd.storecenter.service.ShopAuditService;

@Service("shopAuditService")
public class ShopAuditServiceImpl implements ShopAuditService {
	private final static Logger logger = LoggerFactory.getLogger(ShopAuditServiceImpl.class);

	@Resource
	private ShopInfoDAO shopInfoDAO;
	@Resource
	private ShopBrandDAO shopBrandDAO;
	@Resource
	private ShopCategorySellerDAO shopCategorySellerDAO;

	@SuppressWarnings("rawtypes")
	@Override
	public ExecuteResult<ShopAuditDTO> queryShopAuditInfo(Long shopId) {
		ExecuteResult<ShopAuditDTO> result = new ExecuteResult<ShopAuditDTO>();
		ShopAuditDTO shopAudiDTO = new ShopAuditDTO();
		try {
			ShopDTO shopInfo = shopInfoDAO.selectById(shopId);
			shopAudiDTO.setShopInfo(shopInfo);

			ShopBrandDTO sbDTO = new ShopBrandDTO();
			sbDTO.setShopId(shopId);
			Pager page = new Pager();
			page.setRows(Integer.MAX_VALUE);
			List<ShopBrandDTO> sbList = shopBrandDAO.selectListByCondition(sbDTO, page);
			shopAudiDTO.setSbList(sbList);

			ShopCategorySellerDTO scsDTO = new ShopCategorySellerDTO();
			scsDTO.setShopId(shopId);
			List<ShopCategorySellerDTO> scsList = shopCategorySellerDAO.selectListByCondition(scsDTO, page);
			shopAudiDTO.setScsList(scsList);
			result.setResult(shopAudiDTO);
			result.setResultMessage("success");
		} catch (Exception e) {
			result.setResultMessage("error");
			result.getErrorMessages().add(e.getMessage());
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}

		return result;
	}

}
