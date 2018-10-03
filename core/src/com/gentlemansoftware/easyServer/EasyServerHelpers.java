package com.gentlemansoftware.easyServer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class EasyServerHelpers {

	public static EasyServerInformation getLocalHost() {
		return getDefaultServerInformationForIP(getOwnIP());
	}

	public static EasyServerInformation getDefaultServerInformationForIP(String ip) {
		String displayName = EasyServerHelpers.getHostname();
		String port = EasyServerHelpers.getPort();
		String owner = EasyServerHelpers.getUsername();
		String uniqueID = EasyServerHelpers.getOwnIP();
		String time = EasyServerHelpers.getTimeNow();

		EasyServerInformation serverInformation = new EasyServerInformation(uniqueID, displayName, ip, port, time,
				owner);

		return serverInformation;
	}

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

	public static String getPort() {
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