package cn.htd.storecenter.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.ExecuteResult;
import cn.htd.storecenter.dao.ShopDomainDAO;
import cn.htd.storecenter.service.ShopDomainService;

@Service("shopDomainService")
public class ShopDomainServiceImpl implements ShopDomainService {
	private final static Logger logger = LoggerFactory.getLogger(ShopDomainServiceImpl.class);

	@Resource
	private ShopDomainDAO shopDomainDAO;

	@Override
	public ExecuteResult<Boolean> existShopUrl(String shopUrl) {

		ExecuteResult<Boolean> result = new ExecuteResult<Boolean>();
		try {

			Long count = shopDomainDAO.existShopUrl(shopUrl);
			if (count > 0) {
				result.setResult(false);
			} else {
				result.setResult(true);
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
