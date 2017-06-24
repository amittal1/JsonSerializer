package com.test.testUtils;



public class CachedCommand {

	private String cmdName;
	private String channelName;
	
	
	public CachedCommand( String cmdName, String channelName) {
		setCmdName(cmdName);
		setChannelName(channelName);
	}
	
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getCmdName() {
		return cmdName;
	}
	public void setCmdName(String cmdName) {
		this.cmdName = cmdName;
	}
	
	
}
