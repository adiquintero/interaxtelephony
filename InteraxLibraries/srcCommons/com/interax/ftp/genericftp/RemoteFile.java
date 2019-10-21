package com.interax.ftp.genericftp;

import java.util.Date;

public class RemoteFile {
	
	private String nameFile;
	private Date fileCreationDate;
	private long size;
	private String location;
	private Date fileModificationDate;
	
	public String getNameFile() {
		return nameFile;
	}
	public void setNameFile(String nameFile) {
		this.nameFile = nameFile;
	}
	public Date getCreated() {
		return fileCreationDate;
	}
	public void setCreated(Date created) {
		this.fileCreationDate = created;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Date getLastModified() {
		return fileModificationDate;
	}
	public void setLastModified(Date lastModified) {
		this.fileModificationDate = lastModified;
	}

}
