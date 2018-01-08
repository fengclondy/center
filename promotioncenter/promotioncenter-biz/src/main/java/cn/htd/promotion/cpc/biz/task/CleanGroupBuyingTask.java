package cn.htd.promotion.cpc.biz.task;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.htd.promotion.cpc.biz.dao.PromotionInfoDAO;
import cn.htd.promotion.cpc.dto.response.GroupbuyingInfoResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionInfoDTO;
import net.sf.json.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

import cn.htd.common.Pager;
import cn.htd.common.util.SysProperties;
import cn.htd.promotion.cpc.biz.dao.GroupbuyingInfoDAO;
import cn.htd.promotion.cpc.biz.handle.PromotionGroupbuyingRedisHandle;
import cn.htd.promotion.cpc.common.util.ExceptionUtils;
import cn.htd.promotion.cpc.dto.request.GroupbuyingInfoCmplReqDTO;
import net.sf.json.JSONArray;

/**
 * Created by tangjiayong on 2017/10/31.
 *
 * 1.把商品标记从团购商品变成普通商品;
 * 2.清除互动结束的团购信息
 */
public class CleanGroupBuyingTask implements IScheduleTaskDealMulti<GroupbuyingInfoResDTO> {

    protected static transient Logger logger = LoggerFactory.getLogger(CleanGroupBuyingTask.class);

    @Resource
    private GroupbuyingInfoDAO groupbuyingInfoDAO;

    @Resource
    private PromotionInfoDAO promotionInfoDAO;

    @Resource
    private PromotionGroupbuyingRedisHandle promotionGroupbuyingRedisHandle;

    @Override
    public Comparator<GroupbuyingInfoResDTO> getComparator() {
        return new Comparator<GroupbuyingInfoResDTO>() {
            public int compare(GroupbuyingInfoResDTO o1, GroupbuyingInfoResDTO o2) {
                Long id1 = o1.getGroupbuyingId();
                Long id2 = o2.getGroupbuyingId();
                return id1.compareTo(id2);
            }
        };
    }

    /**
     * 根据条件,查询当前调度服务器可处理的任务
     *
     * @param taskParameter    任务的自定义参数
     * @param ownSign          当前环境名称
     * @param taskQueueNum     当前任务类型的任务队列数量
     * @param taskQueueList    当前调度服务器,分配到的可处理队列
     * @param eachFetchDataNum 每次获取数据的数量
     * @return
     * @throws Exception
     */
    @Override
    public List<GroupbuyingInfoResDTO> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
                                                   List<TaskItemDefine> taskQueueList, int eachFetchDataNum) throws Exception {
        logger.info("\n 方法:[{}],入参:[{}][{}][{}][{}][{}]", "CleanGroupBuyingTask-selectTasks",
                "taskParameter:" + taskParameter, "ownSign:" + ownSign, "taskQueueNum:" + taskQueueNum,
                JSONObject.toJSONString(taskQueueList), "eachFetchDataNum:" + eachFetchDataNum);
        GroupbuyingInfoCmplReqDTO condition = new GroupbuyingInfoCmplReqDTO();
        Pager<GroupbuyingInfoCmplReqDTO> pager = null;
        List<String> taskIdList = new ArrayList<String>();
        List<GroupbuyingInfoResDTO> groupbuyingDTOList = null;
        if (eachFetchDataNum > 0) {
            pager = new Pager<GroupbuyingInfoCmplReqDTO>();
            pager.setPageOffset(0);
            pager.setRows(eachFetchDataNum);
        }
        try {
            if (taskQueueList != null && taskQueueList.size() > 0) {
                for (TaskItemDefine taskItem : taskQueueList) {
                    taskIdList.add(taskItem.getTaskItemId());
                }
                condition.setTaskQueueNum(taskQueueNum);
                condition.setTaskIdList(taskIdList);
                groupbuyingDTOList = groupbuyingInfoDAO.queryNeedCleanGroupbuying4Task(condition, pager);
            }
        } catch (Exception e) {
            logger.error("\n 方法:[{}],异常:[{}]", "CleanGroupBuyingTask-selectTasks",
                    ExceptionUtils.getStackTraceAsString(e));
        } finally {
            logger.info("\n 方法:[{}],出参:[{}]", "CleanGroupBuyingTask-selectTasks",
                    JSONObject.toJSONString(groupbuyingDTOList));
        }
        return groupbuyingDTOList;
    }

    /**
     * 执行给定的任务数组。因为泛型不支持new 数组,只能传递OBJECT[]
     *
     * @param tasks   任务数组
     * @param ownSign 当前环境名称
     * @return
     * @throws Exception
     */
    @Override
    public boolean execute(GroupbuyingInfoResDTO[] tasks, String ownSign) throws Exception {
        logger.info("\n 方法:[{}],入参:[{}][{}]", "CleanGroupBuyingTask-execute",
                JSONObject.toJSONString(tasks), "ownSign:" + ownSign);
        boolean result = true;
        try {
            if (tasks != null && tasks.length > 0) {
                for (GroupbuyingInfoResDTO dto : tasks) {
                    //根据promotionid 清除redis
                    Boolean deleteResult = promotionGroupbuyingRedisHandle.removeGroupbuyingInfoCmpl2Redis(dto.getPromotionId());
                    logger.info("CleanGroupBuyingTask-execute-deleteResult: "+deleteResult);
                    //proumotion showstatus下架
                    PromotionInfoDTO countDto = new PromotionInfoDTO();
                    countDto.setShowStatus("4");
                    countDto.setModifyId(1L);
                    countDto.setModifyName("CleanGroupBuyingTask");
                    countDto.setPromotionId(dto.getPromotionId());
                    int promotionCount = promotionInfoDAO.savePromotionValidStatus(countDto);
                    logger.info("CleanGroupBuyingTask-execute-promotionCount: "+promotionCount);
                    //更新汇掌柜sptag、上下架状态
                    if(promotionCount > 0){
                        int updateResult = groupbuyingInfoDAO.updateHasRedisClean(dto.getPromotionId());
                        logger.info("CleanGroupBuyingTask - execute - promotionId: "+dto.getPromotionId() +" ,updateResult: "+updateResult );
                        changeShelves(dto.getSkuCode(),dto.getSellerCode(),dto.getPromotionId());
                    }else{
                        logger.error("CleanGroupBuyingTask - execute - promotionId: "+dto.getPromotionId()+" ,deleteResult: "+deleteResult+" ,promotionCount: "+promotionCount);
                    }
                }


            }
        } catch (Exception e) {
            result = false;
            logger.error("\n 方法:[{}],异常:[{}]", "CleanGroupBuyingTask-execute",
                    ExceptionUtils.getStackTraceAsString(e));
        } finally {
            logger.info("\n 方法:[{}],出参:[{}]", "CleanGroupBuyingTask-execute",
                    JSONObject.toJSONString(result));
        }
        return result;
    }


    /**
     * 通过http请求汇掌柜更新商品sptag和上下架状态
     * @param list<GroupbuyingInfoCmplReqDTO>
     * @param promotionId
     * @return
     */
    private String changeShelves(String skuCode, String sellerCode, String promotionId) {
        String responseMsg = "";
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // 1.构造HttpClient的实例
        CloseableHttpClient httpClient = httpClientBuilder.build();

        String url = SysProperties.getProperty("HTDBOSS_ADDRESS") + "/groupBuyTask/changeGroupBuyFlags.htm";

        // 2.构造PostMethod的实例
        HttpPost httppost = new HttpPost(url);

        // 3.把参数值放入到PostMethod对象中
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("skuCode", skuCode));
        formparams.add(new BasicNameValuePair("sellerCode", sellerCode));
        logger.info("********************formparams:"+ JSONObject.toJSONString(formparams));

        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            // 4.执行postMethod,调用http接口
            httppost.setEntity(uefEntity);

            // 5.读取内容
            CloseableHttpResponse response = httpClient.execute(httppost);
            logger.info("********************response:"+ JSONObject.toJSONString(response));

            HttpEntity entity = response.getEntity();
            responseMsg = EntityUtils.toString(entity, "UTF-8").trim();
            // 6.处理返回的内容
            logger.info("promotionId: "+promotionId+"  update sptag 结果-->" + responseMsg);
            //System.out.println(responseMsg);
            if (!StringUtils.isEmpty(responseMsg) && responseMsg.indexOf("status=ok")>0) {
                return "ok";
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return "";
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        } finally {
            // 关闭连接,释放资源
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

}
