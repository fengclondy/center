package cn.htd.marketcenter.service;

import java.util.List;

import cn.htd.marketcenter.common.exception.MarketCenterBusinessException;
import cn.htd.marketcenter.domain.BuyerCheckInfo;
import cn.htd.marketcenter.dto.BuyerInfoDTO;
import cn.htd.marketcenter.dto.PromotionAccumulatyDTO;
import cn.htd.marketcenter.dto.PromotionInfoDTO;
import cn.htd.marketcenter.dto.PromotionSellerRuleDTO;
import cn.htd.marketcenter.dto.PromotionValidDTO;
import cn.htd.membercenter.dto.MemberGroupDTO;
import cn.htd.membercenter.dto.SellerBelongRelationDTO;

public interface PromotionBaseService {
    /**
     * 删除促销活动
     *
     * @param validDTO
     * @return
     * @throws MarketCenterBusinessException
     * @throws Exception
     */
    public PromotionInfoDTO deletePromotionInfo(PromotionValidDTO validDTO) throws MarketCenterBusinessException, Exception;

    /**
     * 插入促销活动表
     *
     * @param promotionInfo
     * @return
     * @throws MarketCenterBusinessException
     * @throws Exception
     */
    public PromotionInfoDTO insertPromotionInfo(PromotionInfoDTO promotionInfo)
            throws MarketCenterBusinessException, Exception;


    /**
     * 查询促销活动信息
     *
     * @param promotionId
     * @param levelCodeArr
     * @return
     * @throws MarketCenterBusinessException
     * @throws Exception
     */
    public PromotionInfoDTO queryPromotionInfo(String promotionId, String... levelCodeArr)
            throws MarketCenterBusinessException, Exception;


    /**
     * 更新促销活动表
     *
     * @param promotionInfo
     * @return
     * @throws MarketCenterBusinessException
     * @throws Exception
     */
    public PromotionInfoDTO updatePromotionInfo(PromotionInfoDTO promotionInfo)
            throws MarketCenterBusinessException, Exception;

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
    public PromotionInfoDTO convertSingleAccumulatyPromotion2DTO(PromotionAccumulatyDTO promotionAccuDTO);

    /**
     * 查询促销活动信息,并将一个层级内容扁平化或者根据指定层级编码取得促销活动
     *
     * @param promotionId
     * @param levelCode
     * @return
     * @throws MarketCenterBusinessException
     * @throws Exception
     */
    public PromotionAccumulatyDTO querySingleAccumulatyPromotionInfo(String promotionId, String... levelCode)
            throws MarketCenterBusinessException, Exception;

    /**
     * 插入只有一个层级的促销活动信息
     *
     * @param promotionAccuDTO
     * @return
     * @throws MarketCenterBusinessException
     * @throws Exception
     */
    public PromotionAccumulatyDTO insertSingleAccumulatyPromotionInfo(PromotionAccumulatyDTO promotionAccuDTO)
            throws MarketCenterBusinessException, Exception;

    /**
     * 更新只有一个层级的促销活动信息
     *
     * @param promotionAccuDTO
     * @return
     * @throws MarketCenterBusinessException
     * @throws Exception
     */
    public PromotionAccumulatyDTO updateSingleAccumulatyPromotionInfo(PromotionAccumulatyDTO promotionAccuDTO)
            throws MarketCenterBusinessException, Exception;

    /**
     * 检查优惠活动会员规则
     *
     * @param promotionInfoDTO
     * @param buyerInfo
     * @return
     */
    public boolean checkPromotionBuyerRule(PromotionInfoDTO promotionInfoDTO, BuyerCheckInfo buyerInfo);


    /**
     * 根据促销活动的有效期间设定促销活动状态
     *
     * @param promotionInfo
     * @return
     */
    public String setPromotionStatusInfo(PromotionInfoDTO promotionInfo);

    /**
     * 校验会员是否在指定卖家的分组内,存在时获取分组ID
     *
     * @param messageId
     * @param buyerInfo
     * @return
     */
    public MemberGroupDTO getBuyerGroupRelationship(String messageId, BuyerInfoDTO buyerInfo);

    //----- add by jiangkun for 2017活动需求商城无敌券 on 20170930 start -----

    /**
     * 删除促销活动中无效的数据
     *
     * @param promotionInfo
     */
    public void deletePromotionUselessInfo(PromotionInfoDTO promotionInfo);

    /**
     * 删除促销买家规则中无效的数据
     *
     * @param promotionInfo
     */
    public void deleteBuyerUselessInfo(PromotionInfoDTO promotionInfo);

    /**
     * 删除促销卖家规则中无效的数据
     *
     * @param promotionInfo
     */
    public void deleteSellerUselessInfo(PromotionInfoDTO promotionInfo);

    /**
     * 删除促销商品规则中无效的数据
     *
     * @param promotionInfo
     */
    public void deleteCategoryUselessInfo(PromotionInfoDTO promotionInfo);
    //----- add by jiangkun for 2017活动需求商城无敌券 on 20170930 end -----
    //----- add by jiangkun for 2017活动需求商城优惠券激活 on 20171030 start -----
    /**
     * 校验卖家规则是否是取得归属平台信息
     *
     * @param sellerRule
     * @return
     */
    public boolean isBelongSellerRule(PromotionSellerRuleDTO sellerRule);

    /**
     * 取得会员归属关系信息
     *
     * @param buyerCode
     * @return
     */
    public SellerBelongRelationDTO getBuyerBelongRelationship(String buyerCode);

    /**
     * 取得会员归属关系信息
     *
     * @param buyerCodeList
     * @return
     */
    public List<SellerBelongRelationDTO> getBuyerBelongRelationship(List<String> buyerCodeList);
    //----- add by jiangkun for 2017活动需求商城优惠券激活 on 20171030 end -----

}
