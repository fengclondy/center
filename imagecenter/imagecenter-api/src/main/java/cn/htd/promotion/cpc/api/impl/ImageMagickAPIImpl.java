package cn.htd.promotion.cpc.api.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.htd.promotion.cpc.api.ImageMagickAPI;
import cn.htd.promotion.cpc.biz.service.ImageMagickService;
import cn.htd.promotion.cpc.dto.request.BaseImageDTO;

@Service("imageMagickAPI")
public class ImageMagickAPIImpl implements ImageMagickAPI {
	@Resource
	private ImageMagickService imageMagickService;

	@Override
	public String margeImage(BaseImageDTO images) {
		return imageMagickService.margeImage(images);
	}

	@Override
	public String saveMaterielDownloadImg(BaseImageDTO bid, String messageid, int type) {

		if (type == 3) {
			Map<String, Integer> info = imageMagickService.getImgInfo(bid.getMainImageUrl());
			String newbg = imageMagickService.margeImgHeight(bid.getMainImageUrl(),
					(int) (info.get("height") * 1.5));
			bid.setMainImageUrl(newbg);
			return imageMagickService.margeImage(bid);
		}
		return imageMagickService.margeImage(bid);
	}

}
