package cn.htd.membercenter.service;

import cn.htd.common.ExecuteResult;
import cn.htd.membercenter.dto.BindingBankCardCallbackDTO;
import cn.htd.membercenter.dto.BindingBankCardDTO;
import cn.htd.membercenter.dto.YijifuCorporateCallBackDTO;
import cn.htd.membercenter.dto.YijifuCorporateDTO;
import cn.htd.membercenter.dto.YijifuCorporateModifyDTO;

public interface PayInfoService {
	public ExecuteResult<YijifuCorporateCallBackDTO> realNameSaveVerify(YijifuCorporateDTO dto);

	public ExecuteResult<YijifuCorporateCallBackDTO> realNameModifyVerify(YijifuCorporateModifyDTO dto);

	public ExecuteResult<BindingBankCardCallbackDTO> bindingBankCard(BindingBankCardDTO dto);

	public Boolean yijiRealNameModify(String accountNo, String notifyTypeEnum);

	public Boolean cardUnsign(BindingBankCardDTO dto);

	public ExecuteResult<Boolean> bindCardBack(BindingBankCardCallbackDTO dto);
}
