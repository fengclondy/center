package cn.htd.goodscenter.dto.productplus;

import cn.htd.common.Pager;

import java.io.Serializable;
import java.util.List;

/**
 * 查询京东商品IN DTO - for superBoss
 */
public class JdProductQueryOutDTO implements Serializable {

    private String resultCode; // 返回结果码  00000 ： 成功； 99999：系统错误；其他错误码见接口附件

    private String resultMessage; // 返回结果消息

    private Pager pager; // 分页信息

    private List<JdProductQueryItemDTO> dataList;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public List<JdProductQueryItemDTO> getDataList() {
        return dataList;
    }

    public void setDataList(List<JdProductQueryItemDTO> dataList) {
        this.dataList = dataList;
    }

    @Override
    public String toString() {
        return "JdProductQueryOutDTO{" +
                "resultCode='" + resultCode + '\'' +
                ", resultMessage='" + resultMessage + '\'' +
                ", pager=" + pager +
                ", dataList=" + dataList +
                '}';
    }
}
