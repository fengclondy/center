package cn.htd.zeus.tc.dto.resquest;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class OrderPayValidateIsBargainCancleReqDTO  extends GenricReqDTO implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1051838058200818944L;

	/**
     * 中台订单号
     */
	private List<OrderPayValidateIsBargainCancleListReqDTO> orderPayValidateIsBargainCancleListReqDTO;

	public List<OrderPayValidateIsBargainCancleListReqDTO> getOrderPayValidateIsBargainCancleListReqDTO() {
		return orderPayValidateIsBargainCancleListReqDTO;
	}

	public void setOrderPayValidateIsBargainCancleListReqDTO(
			List<OrderPayValidateIsBargainCancleListReqDTO> orderPayValidateIsBargainCancleListReqDTO) {
		this.orderPayValidateIsBargainCancleListReqDTO = orderPayValidateIsBargainCancleListReqDTO;
	}
}
