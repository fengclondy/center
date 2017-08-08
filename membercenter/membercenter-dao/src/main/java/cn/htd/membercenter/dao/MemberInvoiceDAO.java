package cn.htd.membercenter.dao;

import org.apache.ibatis.annotations.Param;

import cn.htd.membercenter.dto.MemberBuyerVerifyDetailInfoDTO;
import cn.htd.membercenter.dto.MemberBuyerVerifyInfoDTO;
import cn.htd.membercenter.dto.MemberInvoiceDTO;

public interface MemberInvoiceDAO {

	public MemberInvoiceDTO queryMemberInvoiceInfo(@Param("memberInvoiceDTO") MemberInvoiceDTO memberInvoiceDTO);

	public void modifyMemberInoviceInfo(@Param("memberInvoiceDTO") MemberInvoiceDTO memberInvoiceDTO);

	/**
	 * 插入审批信息表
	 * 
	 * @param dto
	 * @return
	 */
	public int addVerifyInfo(MemberBuyerVerifyInfoDTO dto);

	/**
	 * 插入审批详细表
	 * 
	 * @param dto
	 * @return
	 */
	public int addVerifyDetailInfo(MemberBuyerVerifyDetailInfoDTO dto);

}
