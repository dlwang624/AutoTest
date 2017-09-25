package org.czy.util;

import javax.servlet.http.HttpServletRequest;

public class Logout {

	public static void SessionInit(HttpServletRequest req){
		req.getSession().removeAttribute("uid");
		req.getSession().removeAttribute("nickname");
//		req.getSession().removeAttribute("userAddress");
//		req.getSession().removeAttribute("localAddress");
		req.getSession().removeAttribute("QCDB");
	}
}
