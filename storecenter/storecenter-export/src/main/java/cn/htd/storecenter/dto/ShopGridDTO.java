package cn.htd.storecenter.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Description: [店铺自定义装修布局中的格子（即模块集合）无DB （页面布局）]
 * </p>
 * 
 */
public class ShopGridDTO implements Serializable {

	private static final long serialVersionUID = -7330962316195659363L;

	/**
	 * 类型 当前有 5e\3e\2e
	 */
	private String gType;

	/**
	 * 里面的模块集合
	 */
	private List<ShopModuleDTO> modules = new ArrayList<ShopModuleDTO>();

	public ShopGridDTO() {
		super();
	}

	public ShopGridDTO(String gType, List<ShopModuleDTO> modules) {
		super();
		this.gType = gType;
		this.modules = modules;
	}

	public String getgType() {
		return gType;
	}

	public void setgType(String gType) {
		this.gType = gType;
	}

	public List<ShopModuleDTO> getModules() {
		return modules;
	}

	public void setModules(List<ShopModuleDTO> modules) {
		this.modules = modules;
	}

}
