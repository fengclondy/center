package cn.htd.promotion.cpc.api.impl;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.api.VoteActivityAPI;
import cn.htd.promotion.cpc.biz.dao.VoteActivityDAO;
import cn.htd.promotion.cpc.biz.dao.VoteActivityFansForwardDAO;
import cn.htd.promotion.cpc.biz.dao.VoteActivityFansVoteDAO;
import cn.htd.promotion.cpc.biz.dao.VoteActivityMemberDAO;
import cn.htd.promotion.cpc.biz.service.VoteActivityService;
import cn.htd.promotion.cpc.common.util.DTOValidateUtil;
import cn.htd.promotion.cpc.common.util.ValidateResult;
import cn.htd.promotion.cpc.dto.response.VoteActivityListResDTO;
import cn.htd.promotion.cpc.dto.response.VoteActivityResDTO;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 投票活动相关API
 * @author zhengy
 * @date 2017-10-16
 */
@Service("voteActivityAPI")
public class VoteActivityAPIImpl implements VoteActivityAPI {

    private Logger logger = LoggerFactory.getLogger(VoteActivityAPIImpl.class);

    @Resource
    private VoteActivityService voteActivityService;

    @Override
    public ExecuteResult<String> saveVoteActivity(VoteActivityResDTO voteActivityResDTO) {
        return voteActivityService.saveVoteActivity(voteActivityResDTO);
    }


    @Override
    public ExecuteResult<VoteActivityResDTO> queryVoteActivityById(Long voteId) {
        return voteActivityService.queryVoteActivityById(voteId);
    }

    @Override
    public ExecuteResult<DataGrid<VoteActivityListResDTO>> queryVoteActivityList(Pager page) {
        return voteActivityService.queryVoteActivityList(page);
    }

    /***
     * 查询当前活动
     * @return
     */
    public VoteActivityResDTO selectCurrentActivity(){
        return voteActivityService.selectCurrentActivity();
    }
}
