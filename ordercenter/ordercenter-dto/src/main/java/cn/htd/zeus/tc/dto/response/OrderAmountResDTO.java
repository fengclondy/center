package cn.htd.zeus.tc.dto.response;

import cn.htd.zeus.tc.dto.resquest.GenricReqDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangxiaolong on 17/9/4.
 */
public class OrderAmountResDTO extends GenricReqDTO implements Serializable{
   //会员编码
    private String memberCode;
    //每天的支出明细列表
    private List<OrderDayAmountResDTO> orderDayAmountResDTOList;
    //总支出
    private String totalAmount;

    //查询状态：0失败 1成功
    private Integer status;

    private String errorMsg;

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public List<OrderDayAmountResDTO> getOrderDayAmountResDTOList() {
        return orderDayAmountResDTOList;
    }

    public void setOrderDayAmountResDTOList(List<OrderDayAmountResDTO> orderDayAmountResDTOList) {
        this.orderDayAmountResDTOList = orderDayAmountResDTOList;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
