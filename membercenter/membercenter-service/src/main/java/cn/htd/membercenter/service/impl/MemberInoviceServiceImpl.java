package cn.htd.membercenter.service.impl;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.ExecuteResult;
import cn.htd.common.encrypt.KeygenGenerator;
import cn.htd.membercenter.common.constant.ErpStatusEnum;
import cn.htd.membercenter.common.constant.GlobalConstant;
import cn.htd.membercenter.dao.MemberBaseOperationDAO;
import cn.htd.membercenter.dao.MemberInvoiceDAO;
import cn.htd.membercenter.domain.MemberStatusInfo;
import cn.htd.membercenter.dto.MemberBuyerVerifyDetailInfoDTO;
import cn.htd.membercenter.dto.MemberBuyerVerifyInfoDTO;
import cn.htd.membercenter.dto.MemberInvoiceDTO;
import cn.htd.membercenter.dto.VerifyResultDTO;
import cn.htd.membercenter.service.MemberInvoiceService;

@Service("memberInvoiceService")
public class MemberInoviceServiceImpl implements MemberInvoiceService {

	private final static Logger logger = LoggerFactory.getLogger(MemberInoviceServiceImpl.class);

	@Resource
	private MemberInvoiceDAO memberInvoiceDAO;

	@Resource
	MemberBaseOperationDAO memberBaseOperationDAO;

	@Override
	public ExecuteResult<MemberInvoiceDTO> queryMemberInvoiceInfo(MemberInvoiceDTO memberInvoiceDTO) {
		ExecuteResult<MemberInvoiceDTO> rs = new ExecuteResult<MemberInvoiceDTO>();
		String memberId = memberInvoiceDTO.getMemberId();
		MemberInvoiceDTO member = null;
		if (StringUtils.isNotBlank(memberId)) {
			member = memberInvoiceDAO.queryMemberInvoiceInfo(memberInvoiceDTO);
		}
		try {
			if (member != null) {
				rs.setResult(member);
				rs.setResultMessage("success");
			} else {
				rs.setResultMessage("要查询的数据不存在");
			}
		} catch (Exception e) {
			rs.setResultMessage("error");
			logger.error("MemberInoviceServiceImpl----->queryMemberInvoiceInfo=" + e);
		}
		return rs;
	}

	@Override
	public ExecuteResult<Boolean> modifyMemberInoviceInfo(MemberInvoiceDTO memberInvoiceDTO) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		String memberId = memberInvoiceDTO.getMemberId();
		if (StringUtils.isNotBlank(memberId)) {
			try {
				MemberInvoiceDTO member = memberInvoiceDAO.queryMemberInvoiceInfo(memberInvoiceDTO);
				memberInvoiceDAO.modifyMemberInoviceInfo(memberInvoiceDTO);
				if (member != null) {
					MemberBuyerVerifyInfoDTO info = new MemberBuyerVerifyInfoDTO();
					info.setRecordId(Long.valueOf(memberInvoiceDTO.getMemberId()));
					info.setCreateId(Long.valueOf(memberInvoiceDTO.getOperateId()));
					info.setCreateName(memberInvoiceDTO.getOperateName());
					int verifyId = 0;
					// 运营平台操作的类型为0
					if (StringUtils.isNotBlank(memberInvoiceDTO.getRecordType())) {
						info.setRecordType("0");
						memberInvoiceDAO.addVerifyInfo(info);
						verifyId = info.getId().intValue();
					} else {
						info.setRecordType("27");
					}

					if (memberInvoiceDTO.getInvoiceNotify() != null
							&& !memberInvoiceDTO.getInvoiceNotify().equalsIgnoreCase(member.getInvoiceNotify())) {
						this.addVerifyMethod(verifyId, info.getRecordId(),
								Long.valueOf(memberInvoiceDTO.getOperateId()), memberInvoiceDTO.getOperateName(),
								"普通发票抬头", "member_invoice_info", "invoice_notify", member.getInvoiceNotify(),
								memberInvoiceDTO.getInvoiceNotify(), member.getInvoiceNotify(),
								memberInvoiceDTO.getInvoiceNotify(), info.getRecordType());
					}
					if (memberInvoiceDTO.getTaxManId() != null
							&& !memberInvoiceDTO.getTaxManId().equalsIgnoreCase(member.getTaxManId())) {
						this.addVerifyMethod(verifyId, info.getRecordId(),
								Long.valueOf(memberInvoiceDTO.getOperateId()), memberInvoiceDTO.getOperateName(),
								"纳税人识别号", "member_invoice_info", "tax_man_id", member.getTaxManId(),
								memberInvoiceDTO.getTaxManId(), member.getTaxManId(), memberInvoiceDTO.getTaxManId(),
								info.getRecordType());
					}
					if (memberInvoiceDTO.getContactPhone() != null
							&& !memberInvoiceDTO.getContactPhone().equalsIgnoreCase(member.getContactPhone())) {
						this.addVerifyMethod(verifyId, info.getRecordId(),
								Long.valueOf(memberInvoiceDTO.getOperateId()), memberInvoiceDTO.getOperateName(),
								"联系电话", "member_invoice_info", "contact_phone", member.getContactPhone(),
								memberInvoiceDTO.getContactPhone(), member.getContactPhone(),
								memberInvoiceDTO.getContactPhone(), info.getRecordType());
					}
					if (memberInvoiceDTO.getInvoiceAddress() != null
							&& !memberInvoiceDTO.getInvoiceAddress().equalsIgnoreCase(member.getInvoiceAddress())) {
						this.addVerifyMethod(verifyId, info.getRecordId(),
								Long.valueOf(memberInvoiceDTO.getOperateId()), memberInvoiceDTO.getOperateName(),
								"发票地址", "member_invoice_info", "invoice_address", member.getInvoiceAddress(),
								memberInvoiceDTO.getInvoiceAddress(), member.getInvoiceAddress(),
								memberInvoiceDTO.getInvoiceAddress(), info.getRecordType());
					}
					if (memberInvoiceDTO.getBankName() != null
							&& !memberInvoiceDTO.getBankName().equalsIgnoreCase(member.getBankName())) {
						this.addVerifyMethod(verifyId, info.getRecordId(),
								Long.valueOf(memberInvoiceDTO.getOperateId()), memberInvoiceDTO.getOperateName(),
								"开户行名称", "member_invoice_info", "bank_name", member.getBankName(),
								memberInvoiceDTO.getBankName(), member.getBankName(), memberInvoiceDTO.getBankName(),
								info.getRecordType());
					}
					if (memberInvoiceDTO.getBankAccount() != null
							&& !memberInvoiceDTO.getBankAccount().equalsIgnoreCase(member.getBankAccount())) {
						this.addVerifyMethod(verifyId, info.getRecordId(),
								Long.valueOf(memberInvoiceDTO.getOperateId()), memberInvoiceDTO.getOperateName(),
								"开户行账号", "member_invoice_info", "bank_account", member.getBankAccount(),
								memberInvoiceDTO.getBankAccount(), member.getBankAccount(),
								memberInvoiceDTO.getBankAccount(), info.getRecordType());
					}
				}

				VerifyResultDTO verify = memberBaseOperationDAO.selectVerifyByIdAndInfoType(Long.valueOf(memberId),
						GlobalConstant.INFO_TYPE_ERP_ADD);
				if (null != verify) {
					saveErpModify(memberInvoiceDTO);// 修改发票信息下行ERP
				}

				rs.setResultMessage("success");
				rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			} catch (Exception e) {
				logger.error("MemberInoviceServiceImpl----->modifyMemberInoviceInfo=" + e);
				rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
				rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
			}
		}
		return rs;
	}

	/**
	 * 添加审批信息
	 * 
	 * @param dto
	 * @param cont
	 * @param table
	 * @param field
	 * @param before
	 * @param after
	 * @return
	 */
	public void addVerifyMethod(int verifyId, Long recordId, Long modifyId, String modifyName, String cont,
			String table, String field, String before, String after, String beforeDesc, String afterDesc,
			String recordType) {
		MemberBuyerVerifyDetailInfoDTO detail = new MemberBuyerVerifyDetailInfoDTO();
		detail.setVerifyId(new Long(verifyId));
		detail.setContentName(cont);
		detail.setChangeTableId(table);
		detail.setChangeFieldId(field);
		detail.setBeforeChange(before);
		detail.setAfterChange(after);
		detail.setBeforeChangeDesc(beforeDesc);
		detail.setAfterChangeDesc(afterDesc);
		detail.setModifyType(recordType);
		detail.setRecordId(recordId);
		detail.setRecordType("1");
		detail.setOperatorId(modifyId);
		detail.setOperatorName(modifyName);
		memberInvoiceDAO.addVerifyDetailInfo(detail);
	}

	/**
	 * 发票信息下行ERP
	 * 
	 * @param dto
	 * @return
	 * @throws ParseException
	 */
	private boolean saveErpModify(MemberInvoiceDTO dto) throws ParseException {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date trancDate = new Date(format.parse(format.format(date)).getTime());
		MemberStatusInfo status = new MemberStatusInfo();// 会员审核状态信息
		status.setModifyId(Long.valueOf(dto.getOperateId()));
		status.setModifyTime(trancDate);
		status.setVerifyTime(trancDate);
		status.setMemberId(Long.valueOf(dto.getMemberId()));
		status.setModifyName(dto.getOperateName());
		status.setSyncErrorMsg("");
		status.setSyncKey(KeygenGenerator.getUidKey());
		status.setVerifyStatus(ErpStatusEnum.PENDING.getValue());
		status.setInfoType(GlobalConstant.INFO_TYPE_ERP_MODIFY);
		status.setVerifyId(0L);
		status.setCreateId(Long.valueOf(dto.getOperateId()));
		status.setCreateName(dto.getOperateName());
		status.setCreateTime(trancDate);
		memberBaseOperationDAO.deleteMemberStatus(status);
		memberBaseOperationDAO.insertMemberStatus(status);
		return true;
	}
}
