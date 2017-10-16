package cn.htd.promotion.cpc.biz.service.impl;

import cn.htd.promotion.cpc.biz.dao.VoteActivityDAO;
import cn.htd.promotion.cpc.biz.dao.VoteActivityFansVoteDAO;
import cn.htd.promotion.cpc.biz.dao.VoteActivityMemberDAO;
import cn.htd.promotion.cpc.biz.service.VoteActivityFansVoteService;
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
import java.util.Map;

/**
 * 投票活动-粉丝投票相关服务
 * @author chenkang
 * @date 2017-10-12
 */
@Service("voteActivityFansVoteService")
public class VoteActivityFansVoteServiceImpl implements VoteActivityFansVoteService {
    /**
     * KEY分隔符
     */
    private static final String REDIS_SEPARATOR = ":";

    private static final SimpleDateFormat SP = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private PromotionRedisDB promotionRedisDB;

    @Resource
    private VoteActivityDAO voteActivityDAO;

    @Resource
    private VoteActivityMemberDAO voteActivityMemberDAO;

    @Resource
    private VoteActivityFansVoteDAO voteActivityFansVoteDAO;

    private Logger logger = LoggerFactory.getLogger(VoteActivityFansVoteServiceImpl.class);

    /**
     * REDIS缓存 粉丝投门店数记录哈希
     * 格式： {
     *          KEY ：  前缀+voteActivityId+粉丝ID+日期
     *          filed : memberCode
     *          value ： 投票次数
     *        }
     */

    /**
     * 查询粉丝当日投此门店票数
     * @param voteActivityId 投票活动ID
     * @param fansId 粉丝ID
     * @param memberCode 会员店编码
     * @param date 日期
     * @return
     */
    @Override
    public int queryFansVoteNumByDayAndStore(Long voteActivityId, Long fansId, String memberCode, Date date) {
        String key = RedisConst.FANS_VOTE_HASH_PREFIX + REDIS_SEPARATOR + voteActivityId + REDIS_SEPARATOR + fansId + SP.format(date);
        String field = memberCode;
        int count = 0;
        if (promotionRedisDB.exists(key) && promotionRedisDB.existsHash(key, field)) {
            String value = promotionRedisDB.getHash(key, field);
            count = Integer.valueOf(value);
        }
        // TODO :查询数据库
        return count;
    }

    @Override
    public int queryFansVoteStoreNumByDay(Long voteActivityId, Long fansId, Date date) {
        String key = RedisConst.FANS_VOTE_HASH_PREFIX + REDIS_SEPARATOR + voteActivityId + REDIS_SEPARATOR + fansId + SP.format(date);
        int count = 0;
        if (promotionRedisDB.exists(key)) {
            count = promotionRedisDB.getHLen(key);
        }
        // TODO :查询数据库
        return count;
    }

    @Override
    public boolean validateFansVoteNumByDayAndStore(Long voteActivityId, Long fansId, String memberCode, Date date) {
        VoteActivityResDTO voteActivityResDTO = this.voteActivityDAO.selectByPrimaryKey(voteActivityId);
        if (voteActivityResDTO != null) {
            Integer voteNumPAccountPDayPStoreLimit = voteActivityResDTO.getVoteNumPAccountPDayPStore(); // 粉丝当前单个门店投票数量上限
            int voteNumByDayAndStore = this.queryFansVoteNumByDayAndStore(voteActivityId, fansId, memberCode, date);
            // 投票前校验：当前投票数小于限制数，返回校验通过
            if (voteNumByDayAndStore < voteNumPAccountPDayPStoreLimit) {
                return true;
            }
        } else {
            logger.error("voteActivityResDTO is null");
            throw new RuntimeException("voteActivityResDTO is null, voteActivityId : " + voteActivityId);
        }
        return false;
    }

    @Override
    public boolean validateFansVoteStoreNumByDay(Long voteActivityId, Long fansId, Date date) {
        VoteActivityResDTO voteActivityResDTO = this.voteActivityDAO.selectByPrimaryKey(voteActivityId);
        if (voteActivityResDTO != null) {
            Integer voteSotreNumPAccountPDayLimit = voteActivityResDTO.getVoteSotreNumPAccountPDay(); // 粉丝投门店数量上限
            int voteSotreNumByDay = this.queryFansVoteStoreNumByDay(voteActivityId, fansId, date);
            // 投票前校验：当前投票数小于限制数，返回校验通过
            if (voteSotreNumByDay < voteSotreNumPAccountPDayLimit) {
                return true;
            }
        } else {
            logger.error("voteActivityResDTO is null");
            throw new RuntimeException("voteActivityResDTO is null, voteActivityId : " + voteActivityId);
        }
        return false;
    }

    @Transactional
    @Override
    public boolean voteByFans(Long voteActivityId, Long fansId, String memberCode) {
        // 根据voteActivityId和memberCode查询voteActivityMemberResDTO
        VoteActivityMemberResDTO voteActivityMemberResDTO = this.voteActivityMemberDAO.selectByVoteIdAndMemberCode(voteActivityId, memberCode);
        if (voteActivityMemberResDTO == null) {
            throw new RuntimeException("根据活动ID:" + voteActivityId+ "和memberCode:" + memberCode + "查询不到会员店报名信息");
        }
        // 在数据库记录投票
        Long voteMemberId = voteActivityMemberResDTO.getVoteMemberId();
        Date date = new Date();
        VoteActivityFansVoteResDTO record = new VoteActivityFansVoteResDTO();
        record.setFansId(fansId);
        record.setVoteMemberId(voteMemberId); // 关联某个会员店和活动
        record.setFansSignUpTime(date);
        record.setCreateId(fansId);
        record.setCreateName(""); //TODO
        record.setCreateTime(date);
        this.voteActivityFansVoteDAO.insert(record);
        // 更新最近投票时间BY voteMemberId
        VoteActivityMemberResDTO voteActivityMemberResDTOUpdate = new VoteActivityMemberResDTO();
        voteActivityMemberResDTOUpdate.setVoteMemberId(voteMemberId);
        voteActivityMemberResDTOUpdate.setMemberVoteLastTime(date);
        this.voteActivityMemberDAO.updateByPrimaryKeySelective(voteActivityMemberResDTOUpdate);
        // 在REIDS插入投票记录
        String key = RedisConst.FANS_VOTE_HASH_PREFIX + REDIS_SEPARATOR + voteActivityId + REDIS_SEPARATOR + fansId + SP.format(date);
        String field = memberCode;
        if (promotionRedisDB.exists(key) && promotionRedisDB.existsHash(key, field)) {
            promotionRedisDB.incrHash(key, field);
        } else {
            promotionRedisDB.setHash(key, field, "1");
        }
        return false;
    }

    @Override
    public boolean isShowVoteActivityByMemberCode(String memberCode) {
        // 有没有所处当前时间的投票活动
        VoteActivityResDTO voteActivityResDTO = this.voteActivityDAO.selectCurrentActivity();
        if (voteActivityResDTO != null) {
            Long voteId = voteActivityResDTO.getVoteId();
            // 校验会员店报名情况，是否审核通过
            VoteActivityMemberResDTO voteActivityMemberResDTO = this.voteActivityMemberDAO.selectByVoteIdAndMemberCode(voteId, memberCode);
            // 状态是否是已审核和已报名
            if (voteActivityMemberResDTO != null && voteActivityMemberResDTO.getAuditStatus() == 1 && voteActivityMemberResDTO.getSignStatus() == 1) {
                return true;
            } else {
                logger.info("isShowVoteActivityByMemberCode, voteActivityMemberResDTO不满足展示条件, result:false, voteActivityMemberResDTO:{}", voteActivityMemberResDTO);
                return false;
            }
        } else {
            logger.info("isShowVoteActivityByMemberCode, voteActivityResDTO is null, result:false");
            return false;
        }
    }

    @Override
    public ExecuteResult<VoteActivityMemberVoteDetailDTO> getMemberVoteDetail(Long voteActivityId, String memberCode) {
        ExecuteResult<VoteActivityMemberVoteDetailDTO> executeResult = new ExecuteResult<>();
        try {
            VoteActivityResDTO voteActivityResDTO = this.voteActivityDAO.selectByPrimaryKey(voteActivityId);
            if (voteActivityResDTO == null) {
                executeResult.setCode("");//TODO:
                executeResult.setResultMessage("查询不到该活动，voteActivityId：" + voteActivityId);
                return executeResult;
            }
            VoteActivityMemberResDTO voteActivityMemberResDTO = this.voteActivityMemberDAO.selectByVoteIdAndMemberCode(voteActivityId, memberCode);
            if (voteActivityMemberResDTO == null) {
                executeResult.setCode("");//TODO:
                executeResult.setResultMessage("查询不到该会员店的报名信息，voteActivityId：" + voteActivityId + ",memberCode:" + memberCode);
                return executeResult;
            }
            // 获取投票排名top10
            List<HashMap<String, String>> rankingList = this.voteActivityMemberDAO.selectMemberRankingTop10(voteActivityId);
            // 获取排名
            int ranking = this.voteActivityMemberDAO.selectMemberRankingByMemberCode(voteActivityId, memberCode);


        } catch (Exception e) {

        }
        return null;
    }
}
