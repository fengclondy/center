package cn.htd.goodscenter.common.constants;

public enum VenusErrorCodes {
	E1040001("商品图片为空"),

	E1040002("商品描述为空"),

	E1040003("数据库中不存在该商品"),
	
	E1040004("商品品类为空"),
	
	E1040005("商品品牌为空"),
	
	E1040006("商品单位为空"),
	
	E1040007("当前平台公司已经存在相同名称商品"),
	
	E1040008("没有查询到数据"),
	
	E1040009("分页查询参数不正确"),
	
	E1040010("大B帐号参数不正确"),
	E1040011("skuId参数不正确"),
	E1040012("shelfType参数不正确"),
	E1040013("%s查询上架信息为空"),
	E1040014("%s参数数据异常"),
	E1040015("上架产品的可视数比实际库存大，请重新输入"),
	E1040016("商品状态不正确，不能进行上架操作"),
	E1040017("%s商品没有上架信息，不能进行下架"),
	E1040018("%s验证价格查询价格接口不成功"),
	E1040019("上架产品的可视数比实际库存大，请重新输入"),
	E1040020("税率发生变更，请修改商品名称");

	private String errorMsg;

	private VenusErrorCodes(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**
	 * 获取错误码的错误消息
	 * 
	 * @return
	 */
	public String getErrorMsg() {
		return this.errorMsg;
	}

	/**
	 * 更加详细的，可附加消息的错误消息.可以在消息中添加占位符，增加消息的多样性。
	 *
	 * @param appendMsg
	 *            , 附加错误消息
	 * @return
	 */
	public String getErrorMsg(String... appendMsg) {
		// 如果定义了占位符，用appendMsg替换{}；否则使用默认getErrorMsg()；
		return String.format(this.errorMsg, appendMsg);
	}

}
