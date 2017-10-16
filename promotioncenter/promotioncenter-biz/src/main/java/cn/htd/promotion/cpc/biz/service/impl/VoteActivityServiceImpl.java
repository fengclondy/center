package cn.htd.promotion.cpc.biz.service.impl;

import cn.htd.promotion.cpc.biz.dao.VoteActivityDAO;
import cn.htd.promotion.cpc.biz.dao.VoteActivityFansVoteDAO;
import cn.htd.promotion.cpc.biz.dao.VoteActivityMemberDAO;
import cn.htd.promotion.cpc.biz.service.VoteActivityFansVoteService;
import cn.htd.promotion.cpc.biz.service.VoteActivityService;
import cn.htd.promotion.cpc.common.constants.RedisConst;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;
import cn.htd.promotion.cpc.dto.response.VoteActivityFansVoteResDTO;
import cn.htd.promotion.cpc.dto.response.VoteActivityMemberResDTO;
import cn.htd.promotion.cpc.dto.response.VoteActivityMemberVoteDetailDTO;
import cn.htd.promotion.cpc.dto.response.VoteActivityResDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 投票活动相关Service
 * @author zhengy
 * @date 2017-10-16
 */
@Service("voteActivityService")
public class VoteActivityServiceImpl implements VoteActivityService {

    private Logger logger = LoggerFactory.getLogger(VoteActivityServiceImpl.class);


    @Resource
    private VoteActivityDAO voteActivityDAO;

    /***
     * 查询当前活动
     * @return
     */
    public VoteActivityResDTO selectCurrentActivity(){
        return voteActivityDAO.selectCurrentActivity();
    }
}
