package cn.htd.promotion.cpc.biz.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.StringUtil;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.biz.dao.ActivityPictureInfoDAO;
import cn.htd.promotion.cpc.biz.dao.ActivityPictureMemberDetailDAO;
import cn.htd.promotion.cpc.biz.dao.MemberActivityPictureDAO;
import cn.htd.promotion.cpc.biz.service.BaseImageMagickService;
import cn.htd.promotion.cpc.biz.service.MaterielDownloadService;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.ExceptionUtils;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.common.util.GeneratorUtils;
import cn.htd.promotion.cpc.dto.request.ActivityPictureInfoReqDTO;
import cn.htd.promotion.cpc.dto.request.ActivityPictureMemberDetailReqDTO;
import cn.htd.promotion.cpc.dto.request.BaseImageDTO;
import cn.htd.promotion.cpc.dto.request.MemberActivityPictureReqDTO;
import cn.htd.promotion.cpc.dto.response.ActivityPictureInfoResDTO;
import cn.htd.promotion.cpc.dto.response.ActivityPictureMemberDetailResDTO;
import cn.htd.promotion.cpc.dto.response.MemberActivityPictureResDTO;

@Service("materielDownloadService")
public class MaterielDownloadServiceimpl implements MaterielDownloadService {
	private static final Logger logger = LoggerFactory.getLogger(MaterielDownloadServiceimpl.class);

	@Resource
	private GeneratorUtils noGenerator;

	@Resource
	private ActivityPictureInfoDAO activityPictureInfoDAO;

	@Resource
	private ActivityPictureMemberDetailDAO activityPictureMemberDetailDAO;

	@Resource
	private MemberActivityPictureDAO memberActivityPictureDAO;

	@Resource
	private BaseImageMagickService baseImageMagickService;

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
			Date itime = activityPictureInfoReqDTO.getInvalidTime();
			if (itime != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(itime);
				cal.set(Calendar.HOUR_OF_DAY, 23);
				cal.set(Calendar.MINUTE, 59);
				cal.set(Calendar.SECOND, 59);
				activityPictureInfoReqDTO.setInvalidTime(cal.getTime());
			}
			
			Integer isUpPromotionFlag = activityPictureInfoDAO.checkActivityName(
					null, activityPictureInfoReqDTO.getPictureName(),
					activityPictureInfoReqDTO.getPictureType());
			if (null != isUpPromotionFlag && isUpPromotionFlag.intValue() > 0) {
				throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_TIME_NOT_UP.getCode(),
						"该活动名称和其他活动一样，请重新设置");
			}
			if(activityPictureInfoReqDTO.getPictureType().equals("3")) {
				isUpPromotionFlag = activityPictureInfoDAO.checkActivityTime(null,
						activityPictureInfoReqDTO.getEffectiveTime(),activityPictureInfoReqDTO.getInvalidTime(), activityPictureInfoReqDTO.getPictureType());
				if (null != isUpPromotionFlag && isUpPromotionFlag.intValue() > 0) {
					throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_TIME_NOT_UP.getCode(),
							"该活动有效期和其他活动重叠，请重新设置");
				}
			}

			
			activityPictureInfoDAO.add(activityPictureInfoReqDTO);

			if (activityPictureInfoReqDTO.getIsVip() == 2) {
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
				Date itime = activityPictureInfoReqDTO.getInvalidTime();
				if (itime != null) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(itime);
					cal.set(Calendar.HOUR_OF_DAY, 23);
					cal.set(Calendar.MINUTE, 59);
					cal.set(Calendar.SECOND, 59);
					activityPictureInfoReqDTO.setInvalidTime(cal.getTime());
				}
				
				Integer isUpPromotionFlag = activityPictureInfoDAO.checkActivityName(
						activityPictureInfoReqDTO.getPictureId(), activityPictureInfoReqDTO.getPictureName(),
						activityPictureInfoReqDTO.getPictureType());
				if (null != isUpPromotionFlag && isUpPromotionFlag.intValue() > 0) {
					throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_TIME_NOT_UP.getCode(),
							"该活动名称和其他活动一样，请重新设置");
				}
				if(activityPictureInfoReqDTO.getPictureType().equals("3")) {
					isUpPromotionFlag = activityPictureInfoDAO.checkActivityTime(activityPictureInfoReqDTO.getPictureId(),
							activityPictureInfoReqDTO.getEffectiveTime(),activityPictureInfoReqDTO.getInvalidTime(), activityPictureInfoReqDTO.getPictureType());
					if (null != isUpPromotionFlag && isUpPromotionFlag.intValue() > 0) {
						throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_TIME_NOT_UP.getCode(),
								"该活动有效期和其他活动重叠，请重新设置");
					}
				}

				
				activityPictureInfoDAO.update(activityPictureInfoReqDTO);

				if (activityPictureInfoReqDTO.getIsVip() == 2) {
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
				if (rt.getIsVip() == 2) {
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
	public ExecuteResult<DataGrid<ActivityPictureInfoResDTO>> selectMaterielDownload(
			ActivityPictureInfoReqDTO activityPictureInfoReqDTO, Pager<ActivityPictureInfoResDTO> pager) {
		if (pager == null) {
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
			resList = activityPictureInfoDAO.selectMaterielDownloadList(activityPictureInfoReqDTO, pager);
			Long pCount = activityPictureInfoDAO.selectMaterielDownloadListCount(activityPictureInfoReqDTO);
			dataGrid.setRows(resList);
			dataGrid.setTotal(pCount);
			result.setResult(dataGrid);
			result.setCode(ResultCodeEnum.SUCCESS.getCode());
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

	@Override
	public ExecuteResult<DataGrid<MemberActivityPictureResDTO>> selectMemberActivityPicture(
			MemberActivityPictureReqDTO memberActivityPictureReqDTO) {
		// 分页
		Pager<MemberActivityPictureReqDTO> pager = new Pager<MemberActivityPictureReqDTO>();
		pager.setPage(memberActivityPictureReqDTO.getPage());
		pager.setRows(memberActivityPictureReqDTO.getPageSize());
		DataGrid<MemberActivityPictureResDTO> dataGrid = new DataGrid<MemberActivityPictureResDTO>();
		List<MemberActivityPictureResDTO> resList = new ArrayList<MemberActivityPictureResDTO>();
		ExecuteResult<DataGrid<MemberActivityPictureResDTO>> result = new ExecuteResult<DataGrid<MemberActivityPictureResDTO>>();
		try {
			resList = memberActivityPictureDAO.selectMemberActivityPictureList(memberActivityPictureReqDTO, pager);
			Long pCount = memberActivityPictureDAO.selectMemberActivityPictureCount(memberActivityPictureReqDTO);
			dataGrid.setRows(resList);
			dataGrid.setTotal(pCount);
			result.setResult(dataGrid);

		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			logger.error(
					"MessageId:{} 调用方法MaterielDownloadServiceimpl.selectMemberActivityPicture出现异常 request：{}异常信息：{}",
					w.toString());
			result.setCode(ResultCodeEnum.ERROR.getCode());
			result.setErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public MemberActivityPictureResDTO delMemberActivityPicture(Long id) {
		MemberActivityPictureResDTO memberActivityPictureResDTO = new MemberActivityPictureResDTO();
		try {
			int delNum = memberActivityPictureDAO.deleteByPrimaryKey(id);
			if (delNum == 1) {
				memberActivityPictureResDTO.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
				memberActivityPictureResDTO.setResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
				return memberActivityPictureResDTO;
			} else {
				memberActivityPictureResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
				memberActivityPictureResDTO.setResponseMsg(ResultCodeEnum.ERROR.getMsg());
				logger.error(
						"MessageId:{} 调用方法MaterielDownloadServiceimpl.delMemberActivityPicture出现异常 request：{}异常信息：{}",
						"id:", id, "没有删除或删除多条");
				return memberActivityPictureResDTO;
			}

		} catch (Exception e) {
			memberActivityPictureResDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			memberActivityPictureResDTO.setResponseCode(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			logger.error("MessageId:{} 调用方法MaterielDownloadServiceimpl.delMemberActivityPicture出现异常 request：{}异常信息：{}",
					"id:", id, w.toString());
			return memberActivityPictureResDTO;
		}
	}

	@Override
	public ExecuteResult<List<ActivityPictureInfoResDTO>> selectMaterielDownloadByMemberCode(String memberCode,
			String pictureType, String messageid, Pager<ActivityPictureInfoResDTO> pager) {
		List<ActivityPictureInfoResDTO> resList = new ArrayList<ActivityPictureInfoResDTO>();
		ExecuteResult<List<ActivityPictureInfoResDTO>> result = new ExecuteResult<List<ActivityPictureInfoResDTO>>();
		try {
			/*Map<String, String> map = new HashMap<String, String>();
			map.put("memberCode", memberCode);
			map.put("pictureType", pictureType);*/
			resList = activityPictureInfoDAO.selectMaterielDownloadByMemberCode(pictureType, memberCode, pager);
			result.setResult(resList);
			result.setCode(ResultCodeEnum.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCodeEnum.ERROR.getCode());
			result.setErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public ExecuteResult<DataGrid<ActivityPictureMemberDetailResDTO>> selectMaterielDownloadMember(String pictureID,
			Pager<ActivityPictureMemberDetailResDTO> pager, String messageid) {
		if (pager == null) {
			pager = new Pager<ActivityPictureMemberDetailResDTO>();
		}
		if (pager.getPage() < 1) {
			pager.setPage(1);
		}
		if (pager.getRows() < 1) {
			pager.setRows(10);
		}
		DataGrid<ActivityPictureMemberDetailResDTO> dataGrid = new DataGrid<ActivityPictureMemberDetailResDTO>();
		List<ActivityPictureMemberDetailResDTO> resList = new ArrayList<ActivityPictureMemberDetailResDTO>();
		ExecuteResult<DataGrid<ActivityPictureMemberDetailResDTO>> result = new ExecuteResult<DataGrid<ActivityPictureMemberDetailResDTO>>();
		try {
			resList = activityPictureMemberDetailDAO.selectMaterielDownloadMember(pictureID, pager);
			Long pCount = activityPictureMemberDetailDAO.selectMaterielDownloadMemberCount(pictureID);
			dataGrid.setRows(resList);
			dataGrid.setTotal(pCount);
			result.setResult(dataGrid);
			result.setCode(ResultCodeEnum.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCodeEnum.ERROR.getCode());
			result.setErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public String saveMaterielDownloadImg(BaseImageDTO bid, String messageid, int type) {

		if (type == 3) {
			Map<String, Integer> info = baseImageMagickService.getImgInfo(bid.getMainImageUrl());
			String newbg = baseImageMagickService.margeImgHeight(bid.getMainImageUrl(),
					(int) (info.get("height") * 1.5));
			bid.setMainImageUrl(newbg);
			return baseImageMagickService.margeImage(bid);
		}
		return baseImageMagickService.margeImage(bid);
	}

	@Override
	public void saveMaterielDownloadImgHis(MemberActivityPictureReqDTO memberActivityPictureReqDTO) {
		memberActivityPictureDAO.add(memberActivityPictureReqDTO);
	}

}
