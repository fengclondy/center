package cn.htd.zeus.tc.biz.rao;

import java.util.List;

import cn.htd.storecenter.dto.QQCustomerDTO;
import cn.htd.storecenter.dto.ShopAuditInDTO;
import cn.htd.storecenter.dto.ShopDTO;

public interface StoreCenterRAO {

	public List<QQCustomerDTO> searchQQCustomerByCondition(Long shopId, String messageId,
			String customerType);

	public List<ShopDTO> queryShopByids(String messageId, ShopAuditInDTO shopAudiinDTO);
}
