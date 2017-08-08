package cn.htd.searchcenter.domain;

import java.io.Serializable;
import java.util.Date;

public class SearchDictionary implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Integer type;
	
	private String word;
	
	private Integer status;
	
	private Date created;
	
	private Date modified;
	
	private Long creator;
	
	private Long editor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public Long getEditor() {
		return editor;
	}

	public void setEditor(Long editor) {
		this.editor = editor;
	}
}
