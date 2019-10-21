package com.interax.telephony.service.ippbx.data;

import java.io.Serializable;

import sun.util.calendar.BaseCalendar.Date;


public class ProvisioningFile implements Serializable{
	private static final long serialVersionUID = 1L;

	private long id;
	private String fileName;
	private String[] contents;
	private String version;
	private long creationDate;
	private Date modificationDate;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String[] getContents() {
		return contents;
	}
	public void setContents(String[] contents) {
		this.contents = contents;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public long getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(long creationDate) {
		this.creationDate = creationDate;
	}
	public Date getModificationDate() {
		return modificationDate;
	}
	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}
	
	
	
}