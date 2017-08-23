package cn.htd.promotion.api.impl;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.promotion.api.AwardRecordAPI;
import cn.htd.promotion.cpc.biz.service.AwardRecordService;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.request.PromotionAwardReqDTO;
import cn.htd.promotion.cpc.dto.response.PromotionAwardDTO;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

/**
 * Created by tangjiayong on 2017/8/22.
 */
@Service("awardRecordAPI")
public class AwardRecordAPIImpl implements AwardRecordAPI {

    private static final Logger logger = LoggerFactory.getLogger(AwardRecordAPIImpl.class);

    @Resource
    AwardRecordService awardRecordService;

    @Override
    public ExecuteResult<DataGrid<PromotionAwardDTO>> getAwardRecordByPromotionId(PromotionAwardReqDTO dto) {
        ExecuteResult<DataGrid<PromotionAwardDTO>> result = new ExecuteResult<DataGrid<PromotionAwardDTO>>();

        try{
            if(!StringUtils.isEmpty(dto.getPromotionId())){
                DataGrid<PromotionAwardDTO> awardRecordList = awardRecordService.getAwardRecordByPromotionId(dto);
                result.setResult(awardRecordList);
                result.setCode(ResultCodeEnum.SUCCESS.getCode());
                if(awardRecordList.getSize() == 0|| awardRecordList ==null){
                    result.setResultMessage(ResultCodeEnum.NORESULT.getMsg());
                }else{
                    result.setResultMessage(ResultCodeEnum.SUCCESS.getMsg());
                }
            }else{
                result.setCode(ResultCodeEnum.PROMOTION_PARAM_IS_NULL.getCode());
                result.setErrorMessage(ResultCodeEnum.PROMOTION_PARAM_IS_NULL.getMsg());
            }

            logger.info("promotionId{}:AwardRecordAPIImpl.getAwardRecordByPromotionId（）方法,出参{}", dto.getPromotionId(), JSON.toJSONString(result));
        }catch (Exception e) {
            result.setCode(ResultCodeEnum.ERROR.getMsg());
            StringWriter w = new StringWriter();
            e.printStackTrace(new PrintWriter(w));
            logger.error("MessageId:{} 调用方法AwardRecordAPIImpl.getAwardRecordByPromotionId出现异常{}",
                    dto.getPromotionId() + ":" +  w.toString());
        }
        return result;
    }
}
