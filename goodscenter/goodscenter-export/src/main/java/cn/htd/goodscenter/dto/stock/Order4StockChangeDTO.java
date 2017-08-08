package cn.htd.goodscenter.dto.stock;

import java.io.Serializable;
import java.util.List;

/**
 * 库存变动DTO -  需求订单信息
 * 由订单中心调用
 *
 *  订单数据结构：订单号
 *                 变动类型(扣减、释放、回滚、锁定)
 *                 订单来源
 *                     订单行1 - （SKUID, 库存id, 商品数量）
 *                     订单行2 - （SKUID, 库存id, 商品数量）
 *                     ……
 * @author chenkang
 * @date 2016-11-23
 */
public class Order4StockChangeDTO implements Serializable {

	private static final long serialVersionUID = 1721021886667215566L;

    /**
     * 接口调用消息ID，每次调用唯一
     */
    private String messageId;

	/**
     * 订单编码
     */
    private String orderNo;

    /**
     * 订单来源
     * 商城 ：mall
     * 超级供应商 : vms
     *
     */
    private String orderResource;

    /**
     * 订单行信息
     */
    List<Order4StockEntryDTO> orderEntries;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderResource() {
        return orderResource;
    }

    public void setOrderResource(String orderResource) {
        this.orderResource = orderResource;
    }

    public List<Order4StockEntryDTO> getOrderEntries() {
        return orderEntries;
    }

    public void setOrderEntries(List<Order4StockEntryDTO> orderEntries) {
        this.orderEntries = orderEntries;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Override
    public String toString() {
        return "Order4StockChangeDTO{" +
                "messageId='" + messageId + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", orderResource='" + orderResource + '\'' +
                ", orderEntries=" + orderEntries +
                '}';
    }

}
