package cn.htd.tradecenter.domain;

import java.util.List;

/**
 * Created by jiangkun on 2017/4/18.
 */
public class TraderOrderItemsCondition {

    private int cancelStatus;

    private int taskQueueNum;

    private List<String> taskIdList;

    public int getCancelStatus() {
        return cancelStatus;
    }

    public void setCancelStatus(int cancelStatus) {
        this.cancelStatus = cancelStatus;
    }

    public int getTaskQueueNum() {
        return taskQueueNum;
    }

    public void setTaskQueueNum(int taskQueueNum) {
        this.taskQueueNum = taskQueueNum;
    }

    public List<String> getTaskIdList() {
        return taskIdList;
    }

    public void setTaskIdList(List<String> taskIdList) {
        this.taskIdList = taskIdList;
    }
}
