package cn.htd.storecenter.emums;



	/**
	 * 
	 * <p>
	 * Description: [店铺经营类目状态]
	 * </p>
	 */
	public enum ShopModifyColumnEnum {

		SHOP_NAME("店铺名称"),
		SHOP_STATUS("店铺状态"),
		SHOP_INTRODUCE("店铺简介"),
		SHOP_TYPE("店铺类型"),
		BUSINESS_TYPE("经营类型"), 
		DISCLAIMER("免责声明");

		private String label;

		ShopModifyColumnEnum(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}

	}

