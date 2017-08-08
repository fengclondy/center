package cn.htd.zeus.tc.biz.rao;

import java.util.List;

import cn.htd.storecenter.dto.QQCustomerDTO;

public interface StoreCenterRAO {

	public List<QQCustomerDTO> searchQQCustomerByCondition(Long shopId, String messageId,
			String customerType);
}
