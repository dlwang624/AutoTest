package org.czy.util;



import bsh.This;

public class Path {
	public static String projectPath = "";
	public static String getProjectPath(String projectName){
		String path = This.class.getResource("/").getPath();
		path = path.substring(0,path.indexOf(projectName)+projectName.length());
		return path;
	}
	
	
	public static void main(String[] args) {
		
	}
}
