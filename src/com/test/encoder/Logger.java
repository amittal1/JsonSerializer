package com.test.encoder;

/**
 * Author: Ankit Luv Mittal
 * Created: 05/20/2017
 * Description: Log msgs on console and logFile
 */

public class Logger {
	private static boolean debugOn = true;

	public static boolean isDebugOn() {
		return debugOn;
	}

	public static void setDebugOn(boolean debugOn) {
		Logger.debugOn = debugOn;
	}

	public static String getMethodTrace() {
		return Thread.currentThread().getStackTrace()[2].getClassName()+
				" "+Thread.currentThread().getStackTrace()[2].getMethodName();
	}
	
	public static void log(String msg){
		System.out.println(msg);
	}
	
	public static void debug(String msg){
		if(debugOn)
			System.out.println(msg);
	}
	
}
