package cn.htd.promotion.cpc.api;

import cn.htd.promotion.cpc.dto.request.BaseImageDTO;

public interface ImageMagickAPI {
	public String margeImage(BaseImageDTO images);

	public String saveMaterielDownloadImg(BaseImageDTO bid, String messageid, int i);

}
