package cn.htd.membercenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.common.constant.GlobalConstant;
import cn.htd.membercenter.dao.MemberBaseOperationDAO;
import cn.htd.membercenter.dao.SellerMeetingInfoDAO;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.SellerMeetingInfoDTO;
import cn.htd.membercenter.service.SellerMeetingVMSService;

@Service("sellerMeetingVMSService")
public class SellerMeetingVMSServiceImpl implements SellerMeetingVMSService {

	private final static Logger logger = LoggerFactory.getLogger(SellerMeetingVMSServiceImpl.class);

	@Resource
	private SellerMeetingInfoDAO sellerMeetingInfoDAO;

	@Resource
	private MemberBaseOperationDAO memberBaseOperationDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.htd.membercenter.service.SellerMeetingVMSService#
	 * selectSellerMeetingInfo(java.lang.String)
	 */
	@Override
	public ExecuteResult<SellerMeetingInfoDTO> selectSellerMeetingInfo(String meetingNo) {
		ExecuteResult<SellerMeetingInfoDTO> rs = new ExecuteResult<SellerMeetingInfoDTO>();
		try {
			SellerMeetingInfoDTO sellerMeetingInfoDTO = sellerMeetingInfoDAO.selectSellerMeetingInfo(meetingNo);
			rs.setResult(sellerMeetingInfoDTO);
			rs.setResultMessage("success");

		} catch (Exception e) {
			logger.error("SellerMeetingVMSServiceImpl----->selectSellerMeetingInfo=" + e);
			rs.addErrorMessage("error");
		}

		return rs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.htd.membercenter.service.SellerMeetingVMSService#
	 * selectSellerMeetingInfoList(cn.htd.common.Pager, java.lang.String)
	 */
	@Override
	public ExecuteResult<DataGrid<SellerMeetingInfoDTO>> selectSellerMeetingInfoList(Pager page, Long sellerId) {
		ExecuteResult<DataGrid<SellerMeetingInfoDTO>> rs = new ExecuteResult<DataGrid<SellerMeetingInfoDTO>>();
		DataGrid<SellerMeetingInfoDTO> dg = new DataGrid<SellerMeetingInfoDTO>();
		try {
			List<SellerMeetingInfoDTO> myMemberDtoList = null;
			Long count = null;
			count = sellerMeetingInfoDAO.selectSellerMeetingInfoListCount(sellerId);
			if (count != null && count.compareTo(0l) > 0) {
				myMemberDtoList = sellerMeetingInfoDAO.selectSellerMeetingInfoList(page, sellerId);
			}
			dg.setRows(myMemberDtoList);
			dg.setTotal(count);
			rs.setResult(dg);
			rs.setResultMessage("success");

		} catch (Exception e) {
			logger.error("SellerMeetingVMSServiceImpl----->selectSellerMeetingInfoList=" + e);
			rs.addErrorMessage("error");
		}

		return rs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.htd.membercenter.service.SellerMeetingVMSService#
	 * insertSellerMeetingInfoDTO(cn.htd.membercenter.dto.SellerMeetingInfoDTO)
	 */
	@Override
	public ExecuteResult<SellerMeetingInfoDTO> insertSellerMeetingInfoDTO(SellerMeetingInfoDTO sellerMeetingInfoDTO) {
		ExecuteResult<SellerMeetingInfoDTO> rs = new ExecuteResult<SellerMeetingInfoDTO>();
		try {
			MemberBaseInfoDTO sellInfo = memberBaseOperationDAO
					.getMemberbaseBySellerId(sellerMeetingInfoDTO.getSellerId(), GlobalConstant.IS_SELLER);
			sellerMeetingInfoDTO.setSellerName(sellInfo.getCompanyName());
			sellerMeetingInfoDTO.setDeleteFlag(GlobalConstant.FLAG_NO);
			String meetingNo = sellInfo.getMemberCode();
			String tmpCODE = "00000000" + sellerMeetingInfoDAO.getMeetingCode();
			meetingNo = meetingNo.concat(tmpCODE.substring(tmpCODE.length() - 6, tmpCODE.length()));
			sellerMeetingInfoDTO.setMeetingNo(meetingNo);
			sellerMeetingInfoDAO.insertSelective(sellerMeetingInfoDTO);
			SellerMeetingInfoDTO dto = new SellerMeetingInfoDTO();
			dto.setMeetingNo(meetingNo);
			dto.setMeetingId(sellerMeetingInfoDTO.getMeetingId());
			rs.setResult(dto);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("SellerMeetingVMSServiceImpl----->insertSellerMeetingInfoDTO=" + e);
			rs.addErrorMessage("error");
			rs.setResultMessage("error");
		}
		return rs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.htd.membercenter.service.SellerMeetingVMSService#
	 * updateSellerMeetingInfoDTO(cn.htd.membercenter.dto.SellerMeetingInfoDTO)
	 */
	@Override
	public ExecuteResult<String> updateSellerMeetingInfoDTO(SellerMeetingInfoDTO sellerMeetingInfoDTO) {
		ExecuteResult<String> rs = new ExecuteResult<String>();
		try {
			MemberBaseInfoDTO sellInfo = memberBaseOperationDAO
					.getMemberbaseBySellerId(sellerMeetingInfoDTO.getSellerId(), GlobalConstant.IS_SELLER);
			sellerMeetingInfoDTO.setSellerName(sellInfo.getCompanyName());
			// sellerMeetingInfoDTO.setDeleteFlag(GlobalConstant.FLAG_NO);
			String meetingNo = sellInfo.getMemberCode();
			String tmpCODE = "00000000" + sellerMeetingInfoDAO.getMeetingCode();
			meetingNo = meetingNo.concat(tmpCODE.substring(tmpCODE.length() - 6, tmpCODE.length()));
			sellerMeetingInfoDTO.setMeetingNo(meetingNo);
			sellerMeetingInfoDAO.updateByPrimaryKeySelective(sellerMeetingInfoDTO);
			rs.setResult(sellerMeetingInfoDTO.getMeetingNo());
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("SellerMeetingVMSServiceImpl----->updateSellerMeetingInfoDTO=" + e);
			rs.addErrorMessage("error");
			rs.setResultMessage("error");
		}
		return rs;
	}

}
