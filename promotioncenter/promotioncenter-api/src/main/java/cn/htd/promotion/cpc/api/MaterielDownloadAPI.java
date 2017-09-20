package cn.htd.promotion.cpc.api;

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
	public String viewMaterielDownload(String activityPictureInfoReqDTO);

	/**
	 * 一览
	 * @param activityPictureInfoReqDTO
	 * @param pager
	 * @return
	 */
	public String selectMaterielDownload(String activityPictureInfoReqDTO, String pager);

}
