package cn.htd.promotion.cpc.biz.service;

import java.util.List;

import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.dto.response.PromotionAccumulatyDTO;
import cn.htd.promotion.cpc.dto.response.PromotionInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionValidDTO;


public interface PromotionBaseService {
    /**
     * 删除促销活动
     *
     * @param validDTO
     * @throws MarketCenterBusinessthrows  Exception
     * @throws throws  Exception
     */
    public void deletePromotionInfo(PromotionValidDTO validDTO) throws PromotionCenterBusinessException,Exception;

    /**
     * 插入促销活动表
     *
     * @param promotionInfo
     * @return
     * @throws MarketCenterBusinessthrows  Exception
     * @throws throws  Exception
     */
    public PromotionInfoDTO insertPromotionInfo(PromotionInfoDTO promotionInfo)throws PromotionCenterBusinessException,Exception;


    /**
     * 查询促销活动信息
     *
     * @param promotionId
     * @param levelCodeArr
     * @return
     * @throws MarketCenterBusinessthrows  Exception
     * @throws throws  Exception
     */
    public PromotionInfoDTO queryPromotionInfo(String promotionId, String... levelCodeArr)throws PromotionCenterBusinessException,Exception;


    /**
     * 更新促销活动表
     *
     * @param promotionInfo
     * @return
     * @throws MarketCenterBusinessthrows  Exception
     * @throws throws  Exception
     */
    public PromotionInfoDTO updatePromotionInfo(PromotionInfoDTO promotionInfo)throws PromotionCenterBusinessException,Exception;

    /**
     * 将只有一个层级的促销活动转换成一个对象
     *
     * @param promotionInfo
     * @return
     */
    public PromotionAccumulatyDTO convertSingleAccumulatyPromotion2Info(PromotionInfoDTO promotionInfo);

    /**
     * 将只有一个层级的促销活动转换成层级列表形式DTO
     *
     * @param promotionAccuDTO
     * @return
     */
    public PromotionInfoDTO convertSingleAccumulatyPromotion2DTO(List<PromotionAccumulatyDTO> promotionAccuDTOList);

    /**
     * 查询促销活动信息,并将一个层级内容扁平化或者根据指定层级编码取得促销活动
     *
     * @param promotionId
     * @param levelCode
     * @return
     * @throws MarketCenterBusinessthrows  Exception
     * @throws throws  Exception
     */
    public PromotionAccumulatyDTO querySingleAccumulatyPromotionInfo(String promotionId, String... levelCode)
             throws  Exception;

    /**
     * 插入多个层级的促销活动信息
     *
     * @param promotionAccuDTO
     * @return
     * @throws MarketCenterBusinessthrows  Exception
     * @throws throws  Exception
     */
    public PromotionAccumulatyDTO insertManyAccumulatyPromotionInfo(List<PromotionAccumulatyDTO> promotionAccuDTOList)
             throws  Exception;

    /**
     * 更新多个层级的促销活动信息
     *
     * @param promotionAccuDTO
     * @return
     * @throws MarketCenterBusinessthrows  Exception
     * @throws throws  Exception
     */
    public PromotionAccumulatyDTO updateSingleAccumulatyPromotionInfo(List<PromotionAccumulatyDTO> promotionAccuDTOList)
             throws  Exception;

    /**
     * 根据促销活动的有效期间设定促销活动状态
     *
     * @param promotionInfo
     * @return
     */
    public String setPromotionStatusInfo(PromotionInfoDTO promotionInfo);

}
