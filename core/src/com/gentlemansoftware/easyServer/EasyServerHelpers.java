package com.gentlemansoftware.easyServer;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class EasyServerHelpers {
	public static String getOwnIP() {
		InetAddress add;
		try {
			add = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "UnknownHostIP";
		}
		return add.getHostAddress();
	}
	

	private static final String defaultPort = "3452";
	
	public static String getPort(){
		return defaultPort;
	}

	public static String getHostname() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "UnknownHostname";
		}
	}

	public static String getUsername() {
		return System.getProperty("user.name");
	}

	public static String getTimeNow() {
		return "" + new Date().getTime();
	}

}