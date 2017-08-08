package cn.htd.goodscenter.dto.indto;

import java.io.Serializable;
import java.util.List;

import cn.htd.goodscenter.dto.ItemOldDTO;
import cn.htd.goodscenter.dto.ItemPictureDTO;

/**
 * 
 * <p>
 * Description: [添加修改二手商品]
 * </p>
 */
public class ItemOldInDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private ItemOldDTO itemOldDTO;// 二手商品信息
	private List<ItemPictureDTO> itemPictureDTO; // 二手商品图片

	public ItemOldDTO getItemOldDTO() {
		return itemOldDTO;
	}

	public void setItemOldDTO(ItemOldDTO itemOldDTO) {
		this.itemOldDTO = itemOldDTO;
	}

	public List<ItemPictureDTO> getItemPictureDTO() {
		return itemPictureDTO;
	}

	public void setItemPictureDTO(List<ItemPictureDTO> itemPictureDTO) {
		this.itemPictureDTO = itemPictureDTO;
	}

}
