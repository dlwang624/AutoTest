package org.czy.entity;

import java.util.List;

public class TreeFolder {
	private String text;
	private boolean checked;
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	private List<TreeTest> children;
	private String state = null;
	
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<TreeTest> getChildren() {
		return children;
	}
	public void setChildren(List<TreeTest> children) {
		this.children = children;
	}
}
