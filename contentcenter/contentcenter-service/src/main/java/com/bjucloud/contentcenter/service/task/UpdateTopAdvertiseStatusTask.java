package com.bjucloud.contentcenter.service.task;

import cn.htd.common.Pager;
import com.alibaba.fastjson.JSONObject;
import com.bjucloud.contentcenter.dao.HTDAdvertisementDAO;
import com.bjucloud.contentcenter.dto.HTDAdvertisementDTO;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by thinkpad on 2017/3/14.
 */
public class UpdateTopAdvertiseStatusTask implements IScheduleTaskDealMulti<HTDAdvertisementDTO> {

    @Resource
    private HTDAdvertisementDAO htdAdvertisementDAO;

    private static final Logger logger = LoggerFactory.getLogger(UpdateTopAdvertiseStatusTask.class);



    @Override
    public Comparator<HTDAdvertisementDTO> getComparator() {
        return new Comparator<HTDAdvertisementDTO>() {
            public int compare(HTDAdvertisementDTO o1, HTDAdvertisementDTO o2) {
                Long id1 = o1.getId();
                Long id2 = o2.getId();
                return id1.compareTo(id2);
            }
        };
    }

    /**
     * 执行给定的任务数组。因为泛型不支持new 数组,只能传递OBJECT[]
     *
     * @param tasks
     *            任务数组
     * @param ownSign
     *            当前环境名称
     * @return
     * @throws Exception
     */
    @Override
    public boolean execute(HTDAdvertisementDTO[] tasks, String ownSign) throws Exception {
        logger.info("\n 方法:[{}],入参:[{}][{}]", "UpdateTopAdvertiseStatusTask-execute",
                JSONObject.toJSONString(tasks), "ownSign:" + ownSign);

        boolean result = true;
        Date nowDt = new Date();
        List<HTDAdvertisementDTO> htdAdvertisementDTOList = new ArrayList<HTDAdvertisementDTO>();
        String status = "";
        String timeStatus = "";
        try{
            if(tasks != null && tasks.length > 0){
                for(HTDAdvertisementDTO advertisementDTO : tasks){
                    status = advertisementDTO.getStatus();
                    if(nowDt.before(advertisementDTO.getStart_time())){
                        timeStatus = "0";
                    }else if(!nowDt.before(advertisementDTO.getStart_time())
                            && !nowDt.after(advertisementDTO.getEnd_time())){
                        timeStatus = "1";
                    }else {
                        timeStatus= "2";
                    }
                    if (timeStatus.equals(status)) {
                        continue;
                    }
                    advertisementDTO.setStatus(timeStatus);
                    htdAdvertisementDTOList.add(advertisementDTO);
                }
                for(HTDAdvertisementDTO temAdvertise : htdAdvertisementDTOList){
                    if(!nowDt.before(temAdvertise.getStart_time())
                            && !nowDt.after(temAdvertise.getEnd_time())){
                        HTDAdvertisementDTO old = new HTDAdvertisementDTO();
                        old.setId(temAdvertise.getId());
                        old.setStatus("2");
                        old.setIs_handoff("1");
                        old.setModify_id(0l);
                        old.setModify_name("sys");
                        this.htdAdvertisementDAO.updateStatus(old);
                    }
                    updateStatus(temAdvertise);
                }
            }
        }catch (Exception e){
            result = false;
            logger.error("\n 方法:[{}],异常:[{}]", "UpdateTopAdvertiseStatusTask-execute",
                    e.getStackTrace());
        }finally {
            logger.info("\n 方法:[{}],出参:[{}]", "UpdateTopAdvertiseStatusTask-execute",
                    JSONObject.toJSONString(result));
        }
        return result;
    }

    /**
     * 根据条件,查询当前调度服务器可处理的任务
     *
     * @param taskParameter
     *            任务的自定义参数
     * @param ownSign
     *            当前环境名称
     * @param taskQueueNum
     *            当前任务类型的任务队列数量
     * @param taskQueueList
     *            当前调度服务器,分配到的可处理队列
     * @param eachFetchDataNum
     *            每次获取数据的数量
     * @return
     * @throws Exception
     */
    @Override
    public List<HTDAdvertisementDTO> selectTasks(String taskParameter, String ownSign, int taskQueueNum, List<TaskItemDefine> taskQueueList, int eachFetchDataNum) throws Exception {
        logger.info("\n 方法:[{}],入参:[{}][{}][{}][{}][{}]", "UpdateTopAdvertiseStatusTask-selectTasks",
                "taskParameter:" + taskParameter, "ownSign:" + ownSign, "taskQueueNum:" + taskQueueNum,
                JSONObject.toJSONString(taskQueueList), "eachFetchDataNum:" + eachFetchDataNum);

        HTDAdvertisementDTO condition = new HTDAdvertisementDTO();
        Pager<HTDAdvertisementDTO> pager = null;
        List<HTDAdvertisementDTO> htdAdvertisementDTOList = new ArrayList<HTDAdvertisementDTO>();
        List<String> taskIdList = new ArrayList<String>();
        if (eachFetchDataNum > 0) {
            pager = new Pager<HTDAdvertisementDTO>();
            pager.setPageOffset(0);
            pager.setRows(eachFetchDataNum);
        }
        try{
            if (taskQueueList != null && taskQueueList.size() > 0) {
                for (TaskItemDefine taskItem : taskQueueList) {
                    taskIdList.add(taskItem.getTaskItemId());
                }
                condition.setTaskQueueNum(taskQueueNum);
                condition.setTaskIdList(taskIdList);
                htdAdvertisementDTOList = htdAdvertisementDAO.queryMallAdList4Task(condition,pager);
            }
        }catch (Exception e){
            logger.error("\n 方法:[{}],异常:[{}]", "UpdateTopAdvertiseStatusTask-selectTasks",
                    e.getStackTrace());
        }finally {
            logger.info("\n 方法:[{}],出参:[{}]", "UpdateTopAdvertiseStatusTask-selectTasks",
                    JSONObject.toJSONString(htdAdvertisementDTOList));
        }

        return htdAdvertisementDTOList;
    }

    /**
     * 更新顶通广告栏状态
     *
     * @param htdAdvertisementDTO
     * @throws Exception
     */
    public void updateStatus(HTDAdvertisementDTO htdAdvertisementDTO){
        HTDAdvertisementDTO historyDTO = new HTDAdvertisementDTO();
        historyDTO.setId(htdAdvertisementDTO.getId());
        historyDTO.setIs_handoff("0");
        historyDTO.setStatus(htdAdvertisementDTO.getStatus());
        historyDTO.setModify_id(0l);
        historyDTO.setModify_name("sys");
        this.htdAdvertisementDAO.updateTopAdStatusById(historyDTO);

    }
}
