package cn.htd.goodscenter.domain;

import java.io.Serializable;
import java.util.List;

public class CategoryAttrImport implements Serializable {

    private String categoryName;

    private Long categoryId;

    private String attrebuteName;

    private List<String> attrebuteValueList;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getAttrebuteName() {
        return attrebuteName;
    }

    public void setAttrebuteName(String attrebuteName) {
        this.attrebuteName = attrebuteName;
    }

    public List<String> getAttrebuteValueList() {
        return attrebuteValueList;
    }

    public void setAttrebuteValueList(List<String> attrebuteValueList) {
        this.attrebuteValueList = attrebuteValueList;
    }

    @Override
    public String toString() {
        return "CategoryAttrImport{" +
                "categoryName='" + categoryName + '\'' +
                ", categoryId=" + categoryId +
                ", attrebuteName='" + attrebuteName + '\'' +
                ", attrebuteValueList=" + attrebuteValueList +
                '}';
    }
}
