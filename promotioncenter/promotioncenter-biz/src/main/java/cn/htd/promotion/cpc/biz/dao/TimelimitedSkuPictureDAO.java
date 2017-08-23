package cn.htd.promotion.cpc.biz.dao;

import cn.htd.promotion.cpc.biz.dmo.TimelimitedSkuPictureDMO;
import cn.htd.promotion.cpc.dto.request.TimelimitedSkuPictureReqDTO;

public interface TimelimitedSkuPictureDAO {
    int deleteByPrimaryKey(Long pictureId);

    int insert(TimelimitedSkuPictureReqDTO timelimitedSkuPictureReqDTO);

    int insertSelective(TimelimitedSkuPictureReqDTO timelimitedSkuPictureReqDTO);

    TimelimitedSkuPictureDMO selectByPrimaryKey(Long pictureId);

    int updateByPrimaryKeySelective(TimelimitedSkuPictureReqDTO timelimitedSkuPictureReqDTO);

    int updateByPrimaryKey(TimelimitedSkuPictureReqDTO timelimitedSkuPictureReqDTO);
    
    
}