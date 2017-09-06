package cn.htd.promotion.cpc.biz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.promotion.cpc.biz.dmo.TimelimitedInfoDMO;
import cn.htd.promotion.cpc.dto.request.TimelimitedInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.PromotionSellerDetailDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedInfoResDTO;


public interface PromotionTimelimitedInfoDAO {

    List<TimelimitedInfoResDTO> selectTimelimitedInfo(@Param("buyerCode") String buyerCode);
    
    List<PromotionSellerDetailDTO> selectPromotionSellerDetailInfo(@Param("promotionId") String promotionId,@Param("buyerCode") String buyerCode);

}