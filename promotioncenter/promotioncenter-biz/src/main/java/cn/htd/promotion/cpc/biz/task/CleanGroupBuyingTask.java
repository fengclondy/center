package cn.htd.promotion.cpc.biz.task;

import cn.htd.common.Pager;
import cn.htd.common.util.SysProperties;
import cn.htd.promotion.cpc.biz.dao.GroupbuyingInfoDAO;
import cn.htd.promotion.cpc.biz.handle.PromotionGroupbuyingRedisHandle;
import cn.htd.promotion.cpc.biz.service.GroupbuyingService;
import cn.htd.promotion.cpc.common.util.ExceptionUtils;
import cn.htd.promotion.cpc.common.util.KeyGeneratorUtils;
import cn.htd.promotion.cpc.dto.request.GroupbuyingInfoCmplReqDTO;
import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;
import net.sf.json.JSONArray;
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

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by tangjiayong on 2017/10/31.
 *
 * 1.把商品标记从团购商品变成普通商品;
 * 2.清除互动结束的团购信息
 */
public class CleanGroupBuyingTask implements IScheduleTaskDealMulti<GroupbuyingInfoCmplReqDTO> {

    protected static transient Logger logger = LoggerFactory.getLogger(CleanGroupBuyingTask.class);

    @Resource
    private KeyGeneratorUtils keyGeneratorUtils;

    @Resource
    private GroupbuyingInfoDAO groupbuyingInfoDAO;

    @Resource
    private PromotionGroupbuyingRedisHandle promotionGroupbuyingRedisHandle;

    /**
     * 执行给定的任务数组。因为泛型不支持new 数组,只能传递OBJECT[]
     *
     * @param tasks   任务数组
     * @param ownSign 当前环境名称
     * @return
     * @throws Exception
     */
    @Override
    public boolean execute(GroupbuyingInfoCmplReqDTO[] tasks, String ownSign) throws Exception {
        logger.info("\n 方法:[{}],入参:[{}][{}]", "CleanGroupBuyingTask-execute",
                JSONObject.toJSONString(tasks), "ownSign:" + ownSign);
        boolean result = true;
        try {
            if (tasks != null && tasks.length > 0) {
                List<Map<String,String>> list = null;
                String messageId = keyGeneratorUtils.generateMessageId();
                for (GroupbuyingInfoCmplReqDTO dto : tasks) {
                    //根据promotionid 清除redis
                    Boolean deleteResult = promotionGroupbuyingRedisHandle.removeGroupbuyingInfoCmpl2Redis(dto.getPromotionId());
                    if(deleteResult){
                        int updateResult = groupbuyingInfoDAO.updateHasRedisClean(dto.getPromotionId());
                        logger.info("CleanGroupBuyingTask - execute - promotionId: "+dto.getPromotionId() +" ,updateResult: "+updateResult );
                        Map<String,String> map = new HashMap<>();
                        map.put("spxxno",dto.getSkuCode());
                        map.put("orgid",dto.getSellerCode());
                        list.add(map);
                    }else{
                        logger.error("CleanGroupBuyingTask - execute - promotionId: "+dto.getPromotionId()+"清除redis团购信息失败!");
                    }
                }
                //更新汇掌柜sptag、上下架状态
                int num = list.size()%1000 == 0 ? list.size()%1000 :list.size()%1000+1;
//                for(int i=0 ;i<num; i++){
//                    List<Map<String,String>> subList = list.subList()
//                     changeShelves(list);
//                }

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
     * @return
     */
    private synchronized String changeShelves(List<Map<String,String>> list) {
        String responseMsg = "";
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // 1.构造HttpClient的实例
        CloseableHttpClient httpClient = httpClientBuilder.build();

        String url = SysProperties.getProperty("HTDBOSS_ADDRESS") + "/groupBuy/changeGroupBuyFlags.htm";

        // 2.构造PostMethod的实例
        HttpPost httppost = new HttpPost(url);

        // 3.把参数值放入到PostMethod对象中
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("list", JSONArray.fromObject(list).toString()));

        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            // 4.执行postMethod,调用http接口
            httppost.setEntity(uefEntity);

            // 5.读取内容
            CloseableHttpResponse response = httpClient.execute(httppost);
            HttpEntity entity = response.getEntity();
            responseMsg = EntityUtils.toString(entity, "UTF-8").trim();
            // 6.处理返回的内容
            logger.info("update sptag 结果-->" + responseMsg);
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
    public List<GroupbuyingInfoCmplReqDTO> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
                                                       List<TaskItemDefine> taskQueueList, int eachFetchDataNum) throws Exception {
        logger.info("\n 方法:[{}],入参:[{}][{}][{}][{}][{}]", "CleanExpiredPromotionScheduleTask-selectTasks",
                "taskParameter:" + taskParameter, "ownSign:" + ownSign, "taskQueueNum:" + taskQueueNum,
                JSONObject.toJSONString(taskQueueList), "eachFetchDataNum:" + eachFetchDataNum);
        GroupbuyingInfoCmplReqDTO condition = new GroupbuyingInfoCmplReqDTO();
        Pager<GroupbuyingInfoCmplReqDTO> pager = null;
        List<String> taskIdList = new ArrayList<String>();
        List<GroupbuyingInfoCmplReqDTO> groupbuyingDTOList = null;
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

    @Override
    public Comparator<GroupbuyingInfoCmplReqDTO> getComparator() {
        return new Comparator<GroupbuyingInfoCmplReqDTO>() {
            public int compare(GroupbuyingInfoCmplReqDTO o1, GroupbuyingInfoCmplReqDTO o2) {
                Long id1 = o1.getGroupbuyingId();
                Long id2 = o2.getGroupbuyingId();
                return id1.compareTo(id2);
            }
        };
    }
}
