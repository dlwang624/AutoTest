package org.czy.entity;

public class Sidebar {
	private String aClass;
	private String aExpanded;
	private String divClass;
	private String divExpanded;
	private String divStyle;
	private int section;
	
	public Sidebar(int section){
		this.aClass = "collapsed";
		this.aExpanded = "false";
		this.divClass = "collapse";
		this.divExpanded = "false";
		this.divStyle = "height: 0px;";
		this.section = section;
	}
	
	public void open(){
		this.aClass = "active";
		this.aExpanded = "true";
		this.divClass = "collapse in";
		this.divExpanded = "true";
		this.divStyle = "";
	}
	
	public void close(){
		this.aClass = "collapsed";
		this.aExpanded = "false";
		this.divClass = "collapse";
		this.divExpanded = "false";
		this.divStyle = "height: 0px;";
	}
	
	public int getSection() {
		return section;
	}
	public void setSection(int section) {
		this.section = section;
	}
	public String getaClass() {
		return aClass;
	}
	public void setaClass(String aClass) {
		this.aClass = aClass;
	}
	public String getaExpanded() {
		return aExpanded;
	}
	public void setaExpanded(String aExpanded) {
		this.aExpanded = aExpanded;
	}
	public String getDivClass() {
		return divClass;
	}
	public void setDivClass(String divClass) {
		this.divClass = divClass;
	}
	public String getDivExpanded() {
		return divExpanded;
	}
	public void setDivExpanded(String divExpanded) {
		this.divExpanded = divExpanded;
	}
	public String getDivStyle() {
		return divStyle;
	}
	public void setDivStyle(String divStyle) {
		this.divStyle = divStyle;
	}

}
