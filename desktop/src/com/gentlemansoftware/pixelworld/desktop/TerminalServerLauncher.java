package com.gentlemansoftware.pixelworld.desktop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.gentlemansoftware.easyGameNetwork.EasyGameLogMessages;
import com.gentlemansoftware.easyGameNetwork.EasyGameServer;

public class TerminalServerLauncher {

	public static String title = "God of Forest";

	public static EasyGameLogMessages logMessages;
	public static EasyGameServer gameServer;
	public static boolean running = true;
	
	/**
	 * Main for a Desktop game
	 * 
	 * @param arg
	 */
	public static void main(String[] arg) {
		System.out.println("Welcome to Terminal Server");
		System.out.println("### " + title + " ####");
		logMessages = new EasyGameLogMessages();
		gameServer = new EasyGameServer(logMessages);
		while(running){
			menuLoop();
		}
	}
	
	public static void menuLoop(){
		for(int i=0;i<5;i++){
			System.out.println("");
		}
		System.out.println("LA: All Log Messages");
		System.out.println("L: Last 10 Log Messages");
		System.out.println("S: Start Server");
		System.out.println("Q: Stop Server");
		System.out.println("Quit: Quits");
		String input = waitForInput();
		if(input!=null){
			if(input.equals("LA")){
				showLogMessages(logMessages.getLogMessages().size()-1);
			}if(input.equals("L")){
				showLogMessages(10);
			}
			if(input.equals("S")){
				gameServer.startServer();
			}
			if(input.equals("Q")){
				gameServer.stopServer();
			}
			if(input.equals("Quit")){
				running = false;
			}
		}
	}
	
	public static void showLogMessages(int amount){
		List<Object> messages = logMessages.getLogMessages();
		int size = messages.size();
		for(int i=size-amount-1; i<size;i++){
			System.out.println(messages.get(i));
		}
	}

	public static String waitForInput() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Choose: --> ");
		try {
			return br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

}
