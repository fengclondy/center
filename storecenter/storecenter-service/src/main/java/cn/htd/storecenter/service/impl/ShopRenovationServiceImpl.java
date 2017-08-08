package cn.htd.storecenter.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.ExecuteResult;
import cn.htd.storecenter.dao.ShopRenovationDAO;
import cn.htd.storecenter.dao.ShopTemplatesDAO;
import cn.htd.storecenter.dto.ShopRenovationDTO;
import cn.htd.storecenter.dto.ShopTemplatesCombinDTO;
import cn.htd.storecenter.dto.ShopTemplatesDTO;
import cn.htd.storecenter.service.ShopRenovationService;

@Service("shopRenovationService")
public class ShopRenovationServiceImpl implements ShopRenovationService {
	private final static Logger logger = LoggerFactory.getLogger(ShopRenovationServiceImpl.class);

	@Resource
	private ShopRenovationDAO shopRenovationDAO;
	@Resource
	private ShopTemplatesDAO shopTemplatesDAO;

	@Override
	public ExecuteResult<ShopTemplatesCombinDTO> queryShopRenovation(ShopRenovationDTO shopRenovationDTO) {
		ExecuteResult<ShopTemplatesCombinDTO> result = new ExecuteResult<ShopTemplatesCombinDTO>();
		try {
			ShopTemplatesCombinDTO shopTemplatesCombinDTO = new ShopTemplatesCombinDTO();
			Map<String, ShopRenovationDTO> map = new HashMap<String, ShopRenovationDTO>();

			// 将模板信息查询 放入返回值中
			ShopTemplatesDTO stdto = shopTemplatesDAO.selectById(shopRenovationDTO.getTemplatesId());
			shopTemplatesCombinDTO.setShopTemplatesDTO(stdto);

			// 将模板下个位置数据装入map中
			List<ShopRenovationDTO> list = shopRenovationDAO.selectListByCondition(shopRenovationDTO, null);
			for (ShopRenovationDTO shopRenovation : list) {
				map.put(shopRenovation.getModultType() + shopRenovation.getPosition(), shopRenovation);
			}
			shopTemplatesCombinDTO.setShopRenovationmap(map);

			result.setResult(shopTemplatesCombinDTO);
			result.setResultMessage("success");
		} catch (Exception e) {
			result.getErrorMessages().add(e.getMessage());
			result.setResultMessage("error");
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public ExecuteResult<String> addShopRenovation(ShopRenovationDTO shopRenovationDTO) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			shopRenovationDAO.insert(shopRenovationDTO);
			result.setResultMessage("success");
		} catch (Exception e) {
			result.getErrorMessages().add(e.getMessage());
			result.setResultMessage("error");
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public ExecuteResult<String> modifyShopRenovation(ShopRenovationDTO shopRenovationDTO) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			shopRenovationDAO.deleteTid(shopRenovationDTO);
			shopRenovationDAO.insert(shopRenovationDTO);
			result.setResultMessage("success");
		} catch (Exception e) {
			result.getErrorMessages().add(e.getMessage());
			result.setResultMessage("error");
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public ExecuteResult<ShopTemplatesCombinDTO> queryShopRenovationByShopId(Long shopId) {
		ExecuteResult<ShopTemplatesCombinDTO> result = new ExecuteResult<ShopTemplatesCombinDTO>();
		try {

			ShopTemplatesCombinDTO shopTemplatesCombinDTO = new ShopTemplatesCombinDTO();
			Map<String, ShopRenovationDTO> map = new HashMap<String, ShopRenovationDTO>();

			// 根据店铺ID查询出用户使用的模板
			ShopTemplatesDTO shopTemplatesDTO = new ShopTemplatesDTO();
			shopTemplatesDTO.setStatus("1");
			shopTemplatesDTO.setShopId(shopId);
			ShopTemplatesDTO stdto = shopTemplatesDAO.selectByShopId(shopId);
			if (stdto == null) {
				result.setResult(null);
			} else {
				// 将模板信息添加到返回信息中
				shopTemplatesCombinDTO.setShopTemplatesDTO(stdto);

				// 将模板具体信息封装到map中
				ShopRenovationDTO shopRenovationDTO = new ShopRenovationDTO();
				shopRenovationDTO.setTemplatesId(stdto.getId());
				List<ShopRenovationDTO> list1 = shopRenovationDAO.selectListByCondition(shopRenovationDTO, null);
				for (ShopRenovationDTO shopRenovation : list1) {
					map.put(shopRenovation.getModultType() + shopRenovation.getPosition(), shopRenovation);
				}
				shopTemplatesCombinDTO.setShopRenovationmap(map);

				result.setResult(shopTemplatesCombinDTO);
			}
			result.setResultMessage("success");
		} catch (Exception e) {
			result.getErrorMessages().add(e.getMessage());
			result.setResultMessage("error");
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

}
