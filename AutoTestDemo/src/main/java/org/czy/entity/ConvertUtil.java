package org.czy.entity;

import java.util.List;

public class ConvertUtil {
	private List<Description> descList;

	public List<Description> getDescList() {
		return descList;
	}

	public void setDescList(List<Description> descList) {
		this.descList = descList;
	}
	
	public ConvertUtil(){
		super();
	}
	
	public ConvertUtil(List<Description> desclist){
		super();
		this.descList = desclist;
	}
}
