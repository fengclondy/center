package cn.htd.goodscenter.dto;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

public class ItemSearchResultDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String status;

    private List<ItemSearchItemDataDTO> dataList;

    private List<ItemSearchItemDataDTO> newItem;

    private List<ItemSearchItemDataDTO> popularityItem;

    private List<ItemSearchItemDataDTO> itemConnection;

    private LinkedHashMap<String, Object> screenMap;

    private int total = 0;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ItemSearchItemDataDTO> getDataList() {
        return dataList;
    }

    public void setDataList(List<ItemSearchItemDataDTO> dataList) {
        this.dataList = dataList;
    }

    public List<ItemSearchItemDataDTO> getNewItem() {
        return newItem;
    }

    public void setNewItem(List<ItemSearchItemDataDTO> newItem) {
        this.newItem = newItem;
    }

    public List<ItemSearchItemDataDTO> getPopularityItem() {
        return popularityItem;
    }

    public void setPopularityItem(List<ItemSearchItemDataDTO> popularityItem) {
        this.popularityItem = popularityItem;
    }

    public List<ItemSearchItemDataDTO> getItemConnection() {
        return itemConnection;
    }

    public void setItemConnection(List<ItemSearchItemDataDTO> itemConnection) {
        this.itemConnection = itemConnection;
    }

    public LinkedHashMap<String, Object> getScreenMap() {
        return screenMap;
    }

    public void setScreenMap(LinkedHashMap<String, Object> screenMap) {
        this.screenMap = screenMap;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}