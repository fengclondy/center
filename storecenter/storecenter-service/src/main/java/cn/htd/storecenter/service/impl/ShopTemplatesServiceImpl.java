package cn.htd.storecenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.ExecuteResult;
import cn.htd.storecenter.dao.ShopTemplatesDAO;
import cn.htd.storecenter.dto.ShopTemplatesDTO;
import cn.htd.storecenter.service.ShopTemplatesService;

@Service("shopTemplatesService")
public class ShopTemplatesServiceImpl implements ShopTemplatesService {
	private final static Logger logger = LoggerFactory.getLogger(ShopTemplatesServiceImpl.class);

	@Resource
	private ShopTemplatesDAO shopTemplatesDAO;

	@Override
	public ExecuteResult<List<ShopTemplatesDTO>> createShopTemplatesList(ShopTemplatesDTO shopTemplatesDTO) {

		ExecuteResult<List<ShopTemplatesDTO>> result = new ExecuteResult<List<ShopTemplatesDTO>>();

		try {
			List<ShopTemplatesDTO> list = shopTemplatesDAO.selectListByCondition(shopTemplatesDTO, null);
			if (list.size() <= 0) {
				ShopTemplatesDTO d1 = new ShopTemplatesDTO();
				ShopTemplatesDTO d2 = new ShopTemplatesDTO();
				d1.setTemplatesName("shopTemplate1");
				d1.setTemplatesInfo("店铺装修模板1");
				d1.setColor("#ff0000");
				d1.setShopId(shopTemplatesDTO.getShopId());
				d1.setCreateName(shopTemplatesDTO.getCreateName());
				d1.setCreateId(shopTemplatesDTO.getCreateId());
				d1.setModifyId(shopTemplatesDTO.getModifyId());
				d1.setModifyName(shopTemplatesDTO.getModifyName());
				d2.setTemplatesName("shopTemplate2");
				d2.setTemplatesInfo("店铺装修模板2");
				d2.setColor("#ff0000");
				d2.setShopId(shopTemplatesDTO.getShopId());
				d2.setCreateName(shopTemplatesDTO.getCreateName());
				d2.setCreateId(shopTemplatesDTO.getCreateId());
				d2.setModifyId(shopTemplatesDTO.getModifyId());
				d2.setModifyName(shopTemplatesDTO.getModifyName());
				shopTemplatesDAO.insert(d1);
				shopTemplatesDAO.insert(d2);
				list = shopTemplatesDAO.selectListByCondition(shopTemplatesDTO, null);
			}
			result.setResult(list);
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
	public ExecuteResult<String> modifyShopTemplatesStatus(ShopTemplatesDTO dto) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			ShopTemplatesDTO shopTemplatesDTO = new ShopTemplatesDTO();
			shopTemplatesDTO.setShopId(dto.getShopId());

			List<ShopTemplatesDTO> list = shopTemplatesDAO.selectListByCondition(shopTemplatesDTO, null);
			for (ShopTemplatesDTO dt2 : list) {
				if (dt2.getStatus().equals("1")) {
					dt2.setModifyName(dto.getModifyName());
					dt2.setModifyId(dto.getModifyId());
					dt2.setStatus("2");
					shopTemplatesDAO.updateStatus(dt2);
				}
			}
			shopTemplatesDTO.setModifyId(dto.getModifyId());
			shopTemplatesDTO.setModifyName(dto.getModifyName());
			shopTemplatesDTO.setId(dto.getId());
			shopTemplatesDTO.setStatus("1");
			shopTemplatesDAO.updateStatus(shopTemplatesDTO);
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
	public ExecuteResult<String> modifyShopTemplatesColor(ShopTemplatesDTO dto) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			shopTemplatesDAO.updateColor(dto);
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
