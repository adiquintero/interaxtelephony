package org.asteriskjava.manager.action;

import java.io.Serializable;

import org.asteriskjava.manager.action.AbstractManagerAction;
import org.asteriskjava.manager.action.ManagerAction;

public class StreamAction extends AbstractManagerAction implements Serializable, ManagerAction {

	private static final long serialVersionUID = 1L;
	private String channel;
	private String file;
	
	@Override
	public String getAction() {
		return "StreamFile";
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
	
}
