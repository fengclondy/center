package cn.htd.promotion.cpc.biz.service;

import java.util.List;
import java.util.Map;

import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.dto.request.BuyerCheckInfo;
import cn.htd.promotion.cpc.dto.response.PromotionAccumulatyDTO;
import cn.htd.promotion.cpc.dto.response.PromotionExtendInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionValidDTO;


public interface PromotionBaseService {

    /**
     * 读取字典信息
     *
     * @param dictMap
     * @param dictKey
     */
    public void initDictionaryMap(Map<String, String> dictMap, String dictKey);

    /**
     * 初始化促销活动用字典信息
     *
     * @return
     */
    public Map<String, String> initPromotionDictMap();

    /**
     * 删除促销活动
     *
     * @param validDTO
     * @throws PromotionCenterBusinessException
     * @throws Exception
     */
    public void deletePromotionInfo(PromotionValidDTO validDTO) throws PromotionCenterBusinessException, Exception;

    /**
     * 插入促销活动表
     *
     * @param promotionInfo
     * @return
     * @throws PromotionCenterBusinessException
     * @throws Exception
     */
    public PromotionExtendInfoDTO insertPromotionInfo(PromotionExtendInfoDTO promotionInfo)
            throws PromotionCenterBusinessException, Exception;


    /**
     * 查询促销活动信息
     *
     * @param promotionId
     * @param levelCodeArr
     * @return
     * @throws PromotionCenterBusinessException
     * @throws Exception
     */
    public PromotionExtendInfoDTO queryPromotionInfo(String promotionId, String... levelCodeArr)
            throws PromotionCenterBusinessException, Exception;


    /**
     * 更新促销活动表
     *
     * @param promotionInfo
     * @return
     * @throws PromotionCenterBusinessException
     * @throws Exception
     */
    public PromotionExtendInfoDTO updatePromotionInfo(PromotionExtendInfoDTO promotionInfo)
            throws PromotionCenterBusinessException, Exception;

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
     * @param promotionAccuDTOList
     * @return
     */
    public PromotionInfoDTO convertSingleAccumulatyPromotion2DTO(List<PromotionAccumulatyDTO> promotionAccuDTOList);

    /**
     * 查询促销活动信息,并将一个层级内容扁平化或者根据指定层级编码取得促销活动
     *
     * @param promotionId
     * @param levelCode
     * @return
     * @throws PromotionCenterBusinessException
     * @throws Exception
     */
    public PromotionAccumulatyDTO querySingleAccumulatyPromotionInfo(String promotionId, String... levelCode)
            throws PromotionCenterBusinessException, Exception;

    /**
     * 根据促销活动的有效期间设定促销活动状态
     *
     * @param promotionInfo
     * @return
     */
    public String setPromotionStatusInfo(PromotionInfoDTO promotionInfo);

    /**
     * 校验促销活动会员规则
     *
     * @param promotionInfoDTO
     * @param buyerInfo
     * @param dictMap
     * @return
     */
    public boolean checkPromotionBuyerRule(PromotionInfoDTO promotionInfoDTO, BuyerCheckInfo buyerInfo,
            Map<String, String> dictMap);

    /**
     * 校验促销活动卖家规则
     *
     * @param promotionInfoDTO
     * @param sellerCode
     * @param dictMap
     * @return
     */
    public boolean checkPromotionSellerRule(PromotionInfoDTO promotionInfoDTO, String sellerCode,
            Map<String, String> dictMap);
}
