package cn.htd.promotion.cpc.api.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.htd.promotion.cpc.api.BaseImageMagickAPI;
import cn.htd.promotion.cpc.biz.service.BaseImageMagickService;
import cn.htd.promotion.cpc.dto.request.BaseImageDTO;

@Service("baseImageMagickAPI")
public class BaseImageMagickAPIImpl implements BaseImageMagickAPI {
	@Resource
	private BaseImageMagickService baseImageMagickService;

	@Override
	public String margeImage(BaseImageDTO images) {
		return baseImageMagickService.margeImage(images);
	}

}
