package cn.htd.storecenter.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Description: [店铺自定义装修块布局 无DB （页面布局）]
 * </p>
 * 
 */
public class ShopLayoutDTO implements Serializable {

	private static final long serialVersionUID = 4419068959522397348L;

	/**
	 * 类型 当前有 5e\3e2e\2e3e
	 */
	private String lType;

	private List<ShopGridDTO> grids = new ArrayList<ShopGridDTO>();

	public ShopLayoutDTO() {
		super();
	}

	public ShopLayoutDTO(String lType, List<ShopGridDTO> grids) {
		super();
		this.lType = lType;
		this.grids = grids;
	}

	public String getlType() {
		return lType;
	}

	public void setlType(String lType) {
		this.lType = lType;
	}

	public List<ShopGridDTO> getGrids() {
		return grids;
	}

	public void setGrids(List<ShopGridDTO> grids) {
		this.grids = grids;
	}
}
