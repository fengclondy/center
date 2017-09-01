package cn.htd.promotion.cpc.biz.handle;

import java.util.Map;

import javax.annotation.Resource;

import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.promotion.cpc.common.constants.RedisConst;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;
import cn.htd.promotion.cpc.dto.response.PromotionExtendInfoDTO;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("promotionLotteryRedisHandle")
public class PromotionLotteryRedisHandle {

    protected static transient Logger LOGGER = LoggerFactory.getLogger(PromotionLotteryRedisHandle.class);

    @Resource
    private DictionaryUtils dictionary;

    @Resource
    private PromotionRedisDB promotionRedisDB;


}
