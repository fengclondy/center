package cn.htd.promotion.cpc.biz.service;

import java.util.Map;

import cn.htd.promotion.cpc.dto.request.BaseImageDTO;

public interface BaseImageMagickService {

	public String margeImage(BaseImageDTO images);

	String margeImgHeight(String oldimg, int newHeight);

	Map<String, Integer> getImgInfo(String downimg);

}
