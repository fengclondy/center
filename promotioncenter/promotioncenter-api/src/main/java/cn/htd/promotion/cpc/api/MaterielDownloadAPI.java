package cn.htd.promotion.cpc.api;

import cn.htd.common.DataGrid;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.response.MemberActivityPictureResDTO;

public interface MaterielDownloadAPI {

	/**
	 * 新增
	 * @param activityPictureInfoReqDTO
	 * @return
	 */
	public String addMaterielDownload(String activityPictureInfoReqDTO);

	/**
	 * 编辑
	 * @param activityPictureInfoReqDTO
	 * @return
	 */
	public String editMaterielDownload(String activityPictureInfoReqDTO);

	/**
	 * 查看
	 * @param activityPictureInfoReqDTO
	 * @return
	 */
	public String viewMaterielDownload(String activityPictureInfoReqID);
	
	/**
	 * 删除
	 * @param activityPictureInfoReqDTO
	 * @return
	 */
	public String delMaterielDownload(String activityPictureInfoReqID);

	/**
	 * 一览
	 * @param activityPictureInfoReqDTO
	 * @param pager
	 * @return
	 */
	public String selectMaterielDownload(String activityPictureInfoReqDTO, String pager);

	/**
	 * 物料历史记录查询
	 * @param memberActivityPictureReqDTO
	 * @param pager
	 * @return
	 */
	public ExecuteResult<DataGrid<MemberActivityPictureResDTO>> selectMemberActivityPicture(String memberActivityPictureReqDTO,String messageID);
	
	/**
	 * 物料历史记录删除
	 * @param memberActivityPictureReqDTO
	 * @param pager
	 * @return
	 */
	public String delMemberActivityPictureById(String memberActivityPictureReqDTO);
}
