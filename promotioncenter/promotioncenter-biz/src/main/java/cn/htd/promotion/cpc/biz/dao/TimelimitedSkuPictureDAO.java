package cn.htd.promotion.cpc.biz.dao;

import cn.htd.promotion.cpc.dto.request.TimelimitedSkuPictureReqDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedSkuPictureResDTO;

public interface TimelimitedSkuPictureDAO {
	
    int insert(TimelimitedSkuPictureReqDTO timelimitedSkuPictureReqDTO);

    int insertSelective(TimelimitedSkuPictureReqDTO timelimitedSkuPictureReqDTO);
    
    int updateByPrimaryKey(TimelimitedSkuPictureReqDTO timelimitedSkuPictureReqDTO);
    
    int updateByPrimaryKeySelective(TimelimitedSkuPictureReqDTO timelimitedSkuPictureReqDTO);
    
    int pseudoDelete(TimelimitedSkuPictureReqDTO timelimitedSkuPictureReqDTO);
    
    int deleteByPrimaryKey(Long pictureId);

    TimelimitedSkuPictureResDTO selectByPrimaryKey(Long pictureId);




    
    
}