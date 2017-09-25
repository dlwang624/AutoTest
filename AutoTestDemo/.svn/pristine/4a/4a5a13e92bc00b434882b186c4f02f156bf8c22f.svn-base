package org.czy.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class GetFolderFileNames {
	private final static Logger LOG = Logger.getLogger(GetFolderFileNames.class);
	
	public static void main(String[] args){
		getFileName("E:\\ConvertBean\\org");
	}
	public static List<String> getFileName(String path) {
		List<String> list = new ArrayList<String>();
	    File f = new File(path);
	    if (!f.exists()) {
	        System.out.println(path + " not exists");
	        return null;
	    }

	    File fa[] = f.listFiles();
	    for (int i = 0; i < fa.length; i++) {
	        File fs = fa[i];
	        if (fs.isDirectory()) {
	            LOG.info(fs.getName() + " [文件夹目录]");
	        } else {
	            list.add(fs.getName());
	        }
	    }
	    return list;
	}
}
