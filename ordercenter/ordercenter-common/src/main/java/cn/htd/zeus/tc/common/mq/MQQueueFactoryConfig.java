package cn.htd.zeus.tc.common.mq;

public class MQQueueFactoryConfig {

	//订单下行ERP补偿接口-5合1下行接口队列名称(发送中间件队列)
	private String ordercenterMiddlewareCompensate;

	//订单行状态上行接口-(监听中间件队列)
	private String middlewareErpDistribution;

	//预售下行(发送中间件队列)
	private String middlewareErpPostPreorder;

	//京东抛单(发送中间件队列)
	private String middlewareJDSubmitOrder;

	//收付款(发送中间件队列)
	private String middlewarePostStrikeaBalance;

	public String getMiddlewarePostStrikeaBalance() {
			return middlewarePostStrikeaBalance;
		}

	public void setMiddlewarePostStrikeaBalance(String middlewarePostStrikeaBalance) {
		this.middlewarePostStrikeaBalance = middlewarePostStrikeaBalance;
	}

	public String getOrdercenterMiddlewareCompensate() {
		return ordercenterMiddlewareCompensate;
	}

	public void setOrdercenterMiddlewareCompensate(
			String ordercenterMiddlewareCompensate) {
		this.ordercenterMiddlewareCompensate = ordercenterMiddlewareCompensate;
	}

	public String getMiddlewareErpDistribution() {
		return middlewareErpDistribution;
	}

	public void setMiddlewareErpDistribution(String middlewareErpDistribution) {
		this.middlewareErpDistribution = middlewareErpDistribution;
	}

	public String getMiddlewareErpPostPreorder() {
		return middlewareErpPostPreorder;
	}

	public void setMiddlewareErpPostPreorder(String middlewareErpPostPreorder) {
		this.middlewareErpPostPreorder = middlewareErpPostPreorder;
	}

	public String getMiddlewareJDSubmitOrder() {
		return middlewareJDSubmitOrder;
	}

	public void setMiddlewareJDSubmitOrder(String middlewareJDSubmitOrder) {
		this.middlewareJDSubmitOrder = middlewareJDSubmitOrder;
	}

}
