package cn.htd.promotion.cpc.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.StringUtil;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.biz.dao.ActivityPictureInfoDAO;
import cn.htd.promotion.cpc.biz.dao.ActivityPictureMemberDetailDAO;
import cn.htd.promotion.cpc.biz.service.MaterielDownloadService;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.ExceptionUtils;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.common.util.GeneratorUtils;
import cn.htd.promotion.cpc.dto.request.ActivityPictureInfoReqDTO;
import cn.htd.promotion.cpc.dto.request.ActivityPictureMemberDetailReqDTO;
import cn.htd.promotion.cpc.dto.response.ActivityPictureInfoResDTO;
import cn.htd.promotion.cpc.dto.response.ActivityPictureMemberDetailResDTO;

@Service("materielDownloadService")
public class MaterielDownloadServiceimpl implements MaterielDownloadService {
	private static final Logger logger = LoggerFactory.getLogger(MaterielDownloadServiceimpl.class);

	@Resource
	private GeneratorUtils noGenerator;

	@Resource
	private ActivityPictureInfoDAO activityPictureInfoDAO;

	@Resource
	private ActivityPictureMemberDetailDAO activityPictureMemberDetailDAO;

	private String ptype = "30";

	@Override
	public ActivityPictureInfoResDTO addMaterielDownload(ActivityPictureInfoReqDTO activityPictureInfoReqDTO) {
		ActivityPictureInfoResDTO rt = new ActivityPictureInfoResDTO();
		try {
			if (activityPictureInfoReqDTO == null) {
				throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "物料下载参数不能为空");
			}
			String aid = noGenerator.generatePromotionId(ptype);
			rt.setPictureId(aid);
			activityPictureInfoReqDTO.setPictureId(aid);
			activityPictureInfoDAO.add(activityPictureInfoReqDTO);

			if (activityPictureInfoReqDTO.getIsVip() == 0) {
				List<ActivityPictureMemberDetailReqDTO> pdlist = activityPictureInfoReqDTO
						.getActivityPictureMemberDetailList();
				for (ActivityPictureMemberDetailReqDTO activityPictureMemberDetailReqDTO : pdlist) {
					activityPictureMemberDetailReqDTO.setPictureId(aid);
					activityPictureMemberDetailDAO.add(activityPictureMemberDetailReqDTO);
				}
			}

			rt.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
			rt.setResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
		} catch (PromotionCenterBusinessException e) {
			rt.setResponseCode(e.getCode());
			rt.setResponseMsg(e.getMessage());
		} catch (Exception e) {
			rt.setResponseCode(ResultCodeEnum.ERROR.getCode());
			rt.setResponseMsg(ExceptionUtils.getStackTraceAsString(e));
		}
		return rt;
	}

	@Override
	public ActivityPictureInfoResDTO editMaterielDownload(ActivityPictureInfoReqDTO activityPictureInfoReqDTO) {
		ActivityPictureInfoResDTO rt = new ActivityPictureInfoResDTO();
		try {
			if (activityPictureInfoReqDTO == null) {
				throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "物料下载参数不能为空");
			}
			String aid = activityPictureInfoReqDTO.getPictureId();
			if (StringUtil.isEmpty(aid)) {
				throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "物料下载参数不能为空");
			}
			rt.setPictureId(aid);
			ActivityPictureInfoResDTO old = activityPictureInfoDAO.selectByPictureId(aid);
			if (old != null) {
				activityPictureInfoReqDTO.setId(old.getId());
				activityPictureInfoDAO.update(activityPictureInfoReqDTO);

				if (activityPictureInfoReqDTO.getIsVip() == 0) {
					List<ActivityPictureMemberDetailReqDTO> pdlist = activityPictureInfoReqDTO
							.getActivityPictureMemberDetailList();
					activityPictureMemberDetailDAO.deleteByPictureId(aid);
					for (ActivityPictureMemberDetailReqDTO activityPictureMemberDetailReqDTO : pdlist) {
						activityPictureMemberDetailReqDTO.setPictureId(aid);
						activityPictureMemberDetailDAO.add(activityPictureMemberDetailReqDTO);
					}
				}
			} else {
				throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "物料下载查询为空");
			}

			rt.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
			rt.setResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
		} catch (PromotionCenterBusinessException e) {
			rt.setResponseCode(e.getCode());
			rt.setResponseMsg(e.getMessage());
		} catch (Exception e) {
			rt.setResponseCode(ResultCodeEnum.ERROR.getCode());
			rt.setResponseMsg(ExceptionUtils.getStackTraceAsString(e));
		}
		return rt;
	}

	@Override
	public ActivityPictureInfoResDTO viewMaterielDownload(String activityPictureInfoId) {
		ActivityPictureInfoResDTO rt = new ActivityPictureInfoResDTO();
		try {
			if (StringUtil.isEmpty(activityPictureInfoId)) {
				throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "物料下载参数不能为空");
			}
			rt = activityPictureInfoDAO.selectByPictureId(activityPictureInfoId);
			if (rt != null) {

				if (rt.getIsVip() == 0) {
					List<ActivityPictureMemberDetailResDTO> pdlist = activityPictureMemberDetailDAO
							.selectByPictureId(rt.getPictureId());
					rt.setActivityPictureMemberDetailList(pdlist);
				}
			} else {
				throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "物料下载查询为空");
			}

			rt.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
			rt.setResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
		} catch (PromotionCenterBusinessException e) {
			rt.setResponseCode(e.getCode());
			rt.setResponseMsg(e.getMessage());
		} catch (Exception e) {
			rt.setResponseCode(ResultCodeEnum.ERROR.getCode());
			rt.setResponseMsg(ExceptionUtils.getStackTraceAsString(e));
		}
		return rt;
	}

	@Override
	public ExecuteResult<DataGrid<ActivityPictureInfoResDTO>> selectMaterielDownload(ActivityPictureInfoReqDTO activityPictureInfoReqDTO,
			Pager<ActivityPictureInfoResDTO> pager) {
		if(pager==null) {
			pager = new Pager<ActivityPictureInfoResDTO>();
		}
		if (pager.getPage() < 1) {
			pager.setPage(1);
		}
		if (pager.getRows() < 1) {
			pager.setRows(10);
		}
		DataGrid<ActivityPictureInfoResDTO> dataGrid = new DataGrid<ActivityPictureInfoResDTO>();
		List<ActivityPictureInfoResDTO> resList = new ArrayList<ActivityPictureInfoResDTO>();
		ExecuteResult<DataGrid<ActivityPictureInfoResDTO>> result = new ExecuteResult<DataGrid<ActivityPictureInfoResDTO>>();
		try {
			resList = activityPictureInfoDAO
					.selectMaterielDownloadList(activityPictureInfoReqDTO, pager);
			Long pCount = activityPictureInfoDAO
					.selectMaterielDownloadListCount(activityPictureInfoReqDTO);
			dataGrid.setRows(resList);
			dataGrid.setTotal(pCount);
			result.setResult(dataGrid);
		} catch (Exception e) {
			result.setCode(ResultCodeEnum.ERROR.getCode());
			result.setErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public ActivityPictureInfoResDTO delMaterielDownload(String activityPictureInfoId) {
		ActivityPictureInfoResDTO rt = new ActivityPictureInfoResDTO();
		try {
			if (StringUtil.isEmpty(activityPictureInfoId)) {
				throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "物料下载参数不能为空");
			}
			activityPictureInfoDAO.deleteByPictureId(activityPictureInfoId);
			activityPictureMemberDetailDAO.deleteByPictureId(activityPictureInfoId);

			rt.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
			rt.setResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
		} catch (PromotionCenterBusinessException e) {
			rt.setResponseCode(e.getCode());
			rt.setResponseMsg(e.getMessage());
		} catch (Exception e) {
			rt.setResponseCode(ResultCodeEnum.ERROR.getCode());
			rt.setResponseMsg(ExceptionUtils.getStackTraceAsString(e));
		}
		return rt;
	}

}
