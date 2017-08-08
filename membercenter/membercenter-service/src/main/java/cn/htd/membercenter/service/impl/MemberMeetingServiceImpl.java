package cn.htd.membercenter.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.ExecuteResult;
import cn.htd.membercenter.common.constant.GlobalConstant;
import cn.htd.membercenter.dao.MemberBaseOperationDAO;
import cn.htd.membercenter.dao.SellerMeetingEvaluateDAO;
import cn.htd.membercenter.dao.SellerMeetingInfoDAO;
import cn.htd.membercenter.dao.SellerMeetingSignDAO;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.SellerMeetingEvaluateDTO;
import cn.htd.membercenter.dto.SellerMeetingInfoDTO;
import cn.htd.membercenter.dto.SellerMeetingSignDTO;
import cn.htd.membercenter.service.MemberMeetingService;

@Service("memberMeetingService")
public class MemberMeetingServiceImpl implements MemberMeetingService {

	private final static Logger logger = LoggerFactory.getLogger(MemberMeetingServiceImpl.class);
	@Resource
	SellerMeetingInfoDAO sellerMeetingInfoDAO;

	@Resource
	SellerMeetingSignDAO sellerMeetingSignDAO;

	@Resource
	SellerMeetingEvaluateDAO sellerMeetingEvaluateDAO;

	@Resource
	private MemberBaseOperationDAO memberBaseOperationDAO;

	@Override
	public ExecuteResult<Boolean> meetingSign(SellerMeetingSignDTO dto) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			SellerMeetingInfoDTO meetingInfo = sellerMeetingInfoDAO.getMeetingByNo(dto.getMeetingNo());
			if (null != meetingInfo) {// 验证会议是否过期
				int deleteFlag = meetingInfo.getDeleteFlag();
				if (deleteFlag == 0) {
					// 验证会议是否已经签到过
					Long count = sellerMeetingSignDAO.getCountByMemberCodeAndMeetingNo(dto.getMemberCode(),
							dto.getMeetingNo());
					if (count > 0) {
						rs.setResult(false);
						rs.setResultMessage("您已经签到过了");
					} else {// 签到入表
						Long memberId = memberBaseOperationDAO.getMemberIdByCode(dto.getMemberCode());
						MemberBaseInfoDTO memberInfo = memberBaseOperationDAO.getMemberbaseById(memberId,
								GlobalConstant.IS_BUYER);
						MemberBaseInfoDTO sellerInfo = memberBaseOperationDAO
								.getMemberbaseBySellerId(meetingInfo.getSellerId(), GlobalConstant.IS_SELLER);
						if (null != memberId) {
							dto.setMemberId(memberId);
							dto.setMemberName(memberInfo.getCompanyName());
							dto.setArtificialPersonName(memberInfo.getArtificialPersonName());
							dto.setMemberCode(memberInfo.getMemberCode());
							dto.setMeetingAddr(meetingInfo.getMeetingAddr());
							dto.setMeetingTitle(meetingInfo.getMeetingTitle());
							dto.setSellerName(sellerInfo.getCompanyName());
							dto.setSellerCode(sellerInfo.getMemberCode());
							dto.setSellerId(meetingInfo.getSellerId());
							dto.setMeetingStartTime(meetingInfo.getMeetingStartTime());
							dto.setMeetingEndTime(meetingInfo.getMeetingEndTime());
							sellerMeetingSignDAO.insertSelective(dto);
							rs.setResult(true);
							rs.setResultMessage("success");
						} else {
							rs.setResult(false);
							rs.setResultMessage("会员信息不存在");
						}

					}

				} else {
					rs.setResult(false);
					rs.setResultMessage("该二维码已失效");
				}
			} else {
				rs.setResult(false);
				rs.setResultMessage("该二维码已失效");
			}
		} catch (Exception e) {
			rs.setResult(false);
			rs.addErrorMessage("签到异常");
			rs.setResultMessage("签到异常");
			logger.error("MemberMeetingServiceImpl-meetingSign签到异常:" + e);
			e.printStackTrace();
		}
		return rs;
	}

	@Override
	public ExecuteResult<Boolean> meetingCommand(SellerMeetingEvaluateDTO dto) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			Long commandCount = sellerMeetingEvaluateDAO.getCountByMemberCodeAndMeetingNo(dto.getMemberCode(),
					dto.getMeetingNo());
			if (commandCount > 0) {
				rs.setResult(false);
				rs.setResultMessage("您已进行过评价");
			} else {
				SellerMeetingInfoDTO meetingInfo = sellerMeetingInfoDAO.getMeetingByNo(dto.getMeetingNo());
				if (null != meetingInfo) {// 验证会议是否过期
					int deleteFlag = meetingInfo.getDeleteFlag();
					if (deleteFlag == 0) {
						// 验证会议是否已经签到
						Long count = sellerMeetingSignDAO.getCountByMemberCodeAndMeetingNo(dto.getMemberCode(),
								dto.getMeetingNo());
						if (count > 0) {
							Long memberId = memberBaseOperationDAO.getMemberIdByCode(dto.getMemberCode());
							MemberBaseInfoDTO memberInfo = memberBaseOperationDAO.getMemberbaseById(memberId,
									GlobalConstant.IS_BUYER);
							MemberBaseInfoDTO sellerInfo = memberBaseOperationDAO
									.getMemberbaseBySellerId(meetingInfo.getSellerId(), GlobalConstant.IS_SELLER);
							if (null != memberId) {// 评价入表
								dto.setMemberId(memberId);
								dto.setSellerId(meetingInfo.getSellerId());
								dto.setMeetingStartTime(meetingInfo.getMeetingStartTime());
								dto.setSellerCode(sellerInfo.getMemberCode());
								dto.setSellerName(sellerInfo.getCompanyName());
								dto.setMeetingAddr(meetingInfo.getMeetingAddr());
								dto.setArtificialPersonName(memberInfo.getArtificialPersonName());
								dto.setMeetingTitle(meetingInfo.getMeetingTitle());
								dto.setMemberCode(memberInfo.getMemberCode());
								dto.setMemberName(memberInfo.getCompanyName());
								dto.setMeetingEndTime(meetingInfo.getMeetingEndTime());
								sellerMeetingEvaluateDAO.insertSelective(dto);
								rs.setResult(true);
								rs.setResultMessage("success");
							} else {
								rs.setResult(false);
								rs.setResultMessage("会员信息不存在");
							}
						} else {
							rs.setResult(false);
							rs.setResultMessage("您还未签到会议");
						}

					} else {
						rs.setResult(false);
						rs.setResultMessage("该二维码已失效");
					}
				} else {
					rs.setResult(false);
					rs.setResultMessage("该二维码已失效");
				}
			}

		} catch (Exception e) {
			rs.setResult(false);
			rs.addErrorMessage("评价异常");
			rs.setResultMessage("评价异常");
			logger.error("MemberMeetingServiceImpl-meetingCommand评价异常:" + e);
			e.printStackTrace();
		}
		return rs;
	}

}
