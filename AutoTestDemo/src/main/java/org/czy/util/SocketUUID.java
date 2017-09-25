package org.czy.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class SocketUUID {

	public static String getUUID(){
		String uuid = "";
		uuid = new SimpleDateFormat("yyyyMMdd").format(new Date());
		uuid += UUID.randomUUID().toString().replaceAll("-", "");
		return uuid;
	}
}
