package cn.htd.promotion.cpc.biz.service;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.request.ActivityPictureInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.ActivityPictureInfoResDTO;

public interface MaterielDownloadService {
	/**
	 * 新增
	 * 
	 * @param activityPictureInfoReqDTO
	 * @return
	 */
	public ActivityPictureInfoResDTO addMaterielDownload(ActivityPictureInfoReqDTO activityPictureInfoReqDTO);

	/**
	 * 编辑
	 * 
	 * @param activityPictureInfoReqDTO
	 * @return
	 */
	public ActivityPictureInfoResDTO editMaterielDownload(ActivityPictureInfoReqDTO activityPictureInfoReqDTO);

	/**
	 * 查看
	 * 
	 * @param activityPictureInfoReqDTO
	 * @return
	 */
	public ActivityPictureInfoResDTO viewMaterielDownload(String activityPictureInfoId);

	/**
	 * 一览
	 * 
	 * @param activityPictureInfoReqDTO
	 * @param pager
	 * @return
	 */
	public ExecuteResult<DataGrid<ActivityPictureInfoResDTO>> selectMaterielDownload(ActivityPictureInfoReqDTO activityPictureInfoReqDTO,
			Pager<ActivityPictureInfoResDTO> pager);

	public ActivityPictureInfoResDTO delMaterielDownload(String activityPictureInfoReqID);
}
