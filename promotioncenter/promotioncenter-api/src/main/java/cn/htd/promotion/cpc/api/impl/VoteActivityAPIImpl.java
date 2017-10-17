package cn.htd.promotion.cpc.api.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.api.VoteActivityAPI;
import cn.htd.promotion.cpc.biz.service.VoteActivityService;
import cn.htd.promotion.cpc.dto.response.VoteActivityListResDTO;
import cn.htd.promotion.cpc.dto.response.VoteActivityResDTO;

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
    public ExecuteResult<DataGrid<VoteActivityListResDTO>> queryVoteActivityList(Pager page,String voteActName,String actStatus) {
        return voteActivityService.queryVoteActivityList(page,voteActName,actStatus);
    }

    /***
     * 查询当前活动
     * @return
     */
    public VoteActivityResDTO selectCurrentActivity(){
        return voteActivityService.selectCurrentActivity();
    }
}
