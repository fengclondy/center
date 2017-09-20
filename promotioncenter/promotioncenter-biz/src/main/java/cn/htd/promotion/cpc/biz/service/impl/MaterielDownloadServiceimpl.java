package cn.htd.promotion.cpc.biz.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.Pager;
import cn.htd.promotion.cpc.biz.dao.ActivityPictureInfoDAO;
import cn.htd.promotion.cpc.biz.dao.ActivityPictureMemberDetailDAO;
import cn.htd.promotion.cpc.biz.service.MaterielDownloadService;
import cn.htd.promotion.cpc.dto.request.ActivityPictureInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.ActivityPictureInfoResDTO;

@Service("materielDownloadService")
public class MaterielDownloadServiceimpl implements MaterielDownloadService {
	private static final Logger logger = LoggerFactory.getLogger(MaterielDownloadServiceimpl.class);

	@Resource
    private ActivityPictureInfoDAO activityPictureInfoDAO;
	
	@Resource
    private ActivityPictureMemberDetailDAO activityPictureMemberDetailDAO;
	
	@Override
	public ActivityPictureInfoResDTO addMaterielDownload(ActivityPictureInfoReqDTO activityPictureInfoReqDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActivityPictureInfoResDTO editMaterielDownload(ActivityPictureInfoReqDTO activityPictureInfoReqDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActivityPictureInfoResDTO viewMaterielDownload(String activityPictureInfoId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ActivityPictureInfoResDTO> selectMaterielDownload(ActivityPictureInfoReqDTO activityPictureInfoReqDTO,
			Pager<ActivityPictureInfoResDTO> pager) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActivityPictureInfoResDTO delMaterielDownload(String activityPictureInfoReqID) {
		// TODO Auto-generated method stub
		return null;
	}

}
