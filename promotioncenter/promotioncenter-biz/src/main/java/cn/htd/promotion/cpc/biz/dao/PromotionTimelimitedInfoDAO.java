package cn.htd.promotion.cpc.biz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.promotion.cpc.biz.dmo.TimelimitedInfoDMO;
import cn.htd.promotion.cpc.dto.request.TimelimitedInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.PromotionInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionSellerDetailDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedInfoResDTO;


public interface PromotionTimelimitedInfoDAO {

    List<TimelimitedInfoResDTO> selectTimelimitedInfo(@Param("buyerCode") String buyerCode);
    
    List<PromotionSellerDetailDTO> selectPromotionSellerDetailInfo(@Param("promotionId") String promotionId,@Param("buyerCode") String buyerCode);

    public List<PromotionInfoDTO> queryPromotionList(@Param("entity") PromotionInfoDTO condition, @Param("page") Pager<PromotionInfoDTO> page);
    
    public void updatePromotionShowStatusById(PromotionInfoDTO promotionInfo);

}