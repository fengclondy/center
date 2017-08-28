package cn.htd.promotion.cpc.biz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.promotion.cpc.biz.dmo.TimelimitedInfoDMO;
import cn.htd.promotion.cpc.dto.request.TimelimitedInfoReqDTO;


public interface PromotionTimelimitedInfoDAO {

    List<TimelimitedInfoDMO> selectTimelimitedInfo(@Param("skuCode") String skuCode);

}