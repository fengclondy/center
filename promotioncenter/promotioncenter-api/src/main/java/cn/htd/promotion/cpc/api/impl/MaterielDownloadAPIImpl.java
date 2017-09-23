package cn.htd.promotion.cpc.api.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.api.MaterielDownloadAPI;
import cn.htd.promotion.cpc.biz.service.MaterielDownloadService;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.common.util.ValidateResult;
import cn.htd.promotion.cpc.common.util.ValidationUtils;
import cn.htd.promotion.cpc.dto.request.ActivityPictureInfoReqDTO;
import cn.htd.promotion.cpc.dto.request.MemberActivityPictureReqDTO;
import cn.htd.promotion.cpc.dto.response.ActivityPictureInfoResDTO;
import cn.htd.promotion.cpc.dto.response.ActivityPictureMemberDetailResDTO;
import cn.htd.promotion.cpc.dto.response.MemberActivityPictureResDTO;

@Service("materielDownloadAPI")
public class MaterielDownloadAPIImpl implements MaterielDownloadAPI {

	@Resource
	private MaterielDownloadService materielDownloadService;

	@Override
	public String addMaterielDownload(String activityPictureInfoReqDTO) {
		ActivityPictureInfoReqDTO activityPictureInfoReq = JSON.parseObject(activityPictureInfoReqDTO,
				ActivityPictureInfoReqDTO.class);
		ActivityPictureInfoResDTO activityPictureInfoResDTO = new ActivityPictureInfoResDTO();
		// 输入DTO的验证
		ValidateResult validateResult = ValidationUtils.validateEntity(activityPictureInfoReq);
		// 有错误信息时返回错误信息
		if (validateResult.isHasErrors()) {
			throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(),
					validateResult.getErrorMsg());
		}
		activityPictureInfoResDTO = materielDownloadService.addMaterielDownload(activityPictureInfoReq);
		return JSON.toJSONString(activityPictureInfoResDTO);
	}

	@Override
	public String editMaterielDownload(String activityPictureInfoReqDTO) {
		ActivityPictureInfoReqDTO activityPictureInfoReq = JSON.parseObject(activityPictureInfoReqDTO,
				ActivityPictureInfoReqDTO.class);
		ActivityPictureInfoResDTO activityPictureInfoResDTO = new ActivityPictureInfoResDTO();
		// 输入DTO的验证
		ValidateResult validateResult = ValidationUtils.validateEntity(activityPictureInfoReq);
		// 有错误信息时返回错误信息
		if (validateResult.isHasErrors()) {
			throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(),
					validateResult.getErrorMsg());
		}
		activityPictureInfoResDTO = materielDownloadService.editMaterielDownload(activityPictureInfoReq);
		return JSON.toJSONString(activityPictureInfoResDTO);
	}

	@Override
	public String viewMaterielDownload(String activityPictureInfoReqID) {

		ActivityPictureInfoResDTO activityPictureInfoResDTO = materielDownloadService
				.viewMaterielDownload(activityPictureInfoReqID);
		return JSON.toJSONString(activityPictureInfoResDTO);
	}

	@Override
	public String selectMaterielDownload(String activityPictureInfoReqDTO, String pager) {
		ActivityPictureInfoReqDTO activityPictureInfoReq = JSON.parseObject(activityPictureInfoReqDTO,
				ActivityPictureInfoReqDTO.class);
		Pager<ActivityPictureInfoResDTO> page = JSON.parseObject(pager, Pager.class);

		ExecuteResult<DataGrid<ActivityPictureInfoResDTO>> activityPictureInfoResDTO = materielDownloadService
				.selectMaterielDownload(activityPictureInfoReq, page);
		return JSON.toJSONString(activityPictureInfoResDTO);
	}

	@Override
	public String delMaterielDownload(String activityPictureInfoReqID) {
		ActivityPictureInfoResDTO activityPictureInfoResDTO = materielDownloadService
				.delMaterielDownload(activityPictureInfoReqID);
		return JSON.toJSONString(activityPictureInfoResDTO);
	}

	@Override
	public ExecuteResult<DataGrid<MemberActivityPictureResDTO>> selectMemberActivityPicture(
			String memberActivityPictureReqDTO, String messageID) {
		MemberActivityPictureReqDTO memberActivityPictureReq = JSON.parseObject(memberActivityPictureReqDTO, MemberActivityPictureReqDTO.class);
		
//		// 输入DTO的验证
//		ValidateResult validateResult = ValidationUtils.validateEntity(memberActivityPictureReq);
//		// 有错误信息时返回错误信息
//		if (validateResult.isHasErrors()) {
//			throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(),
//					validateResult.getErrorMsg());
//		}
		ExecuteResult<DataGrid<MemberActivityPictureResDTO>> memberActivityPictureResDTO = materielDownloadService.selectMemberActivityPicture(memberActivityPictureReq);
		
		return memberActivityPictureResDTO;
	}

	@Override
	public String delMemberActivityPictureById(String memberActivityPictureReqDTO) {
		MemberActivityPictureReqDTO memberActivityPictureReq = JSON.parseObject(memberActivityPictureReqDTO,
				MemberActivityPictureReqDTO.class);
		// 输入DTO的验证
		ValidateResult validateResult = ValidationUtils.validateEntity(memberActivityPictureReq);
		MemberActivityPictureResDTO memberActivityPictureResDTO = new MemberActivityPictureResDTO();
		// 有错误信息时返回错误信息
		if (validateResult.isHasErrors()) {
			throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(),
					validateResult.getErrorMsg());
		}
		memberActivityPictureResDTO = materielDownloadService
				.delMemberActivityPicture(memberActivityPictureReq.getId());
		return JSON.toJSONString(memberActivityPictureResDTO);
	}

	public String selectMaterielDownloadByMemberCode(String memberCode, String pictureType, String messageid) {
		ExecuteResult<DataGrid<ActivityPictureInfoResDTO>> activityPictureInfoResDTO = materielDownloadService
				.selectMaterielDownloadByMemberCode(memberCode, pictureType, messageid);
		return JSON.toJSONString(activityPictureInfoResDTO);
	}

	@Override
	public String selectMaterielDownloadMember(String pictureID, String pager, String messageid) {
		Pager<ActivityPictureMemberDetailResDTO> page = JSON.parseObject(pager, Pager.class);

		ExecuteResult<DataGrid<ActivityPictureMemberDetailResDTO>> activityPictureInfoResDTO = materielDownloadService
				.selectMaterielDownloadMember(pictureID, page,messageid);
		return JSON.toJSONString(activityPictureInfoResDTO);
	}

}
