package com.interax.ftp.genericftp;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;

public abstract class GenericFtp {

	protected boolean isPassive = true;
	protected boolean isBinary = true;

	/**
	 * Connection given to use by this ftp.
	 */
	public abstract void connect(String user, String password, String remoteHost, int remotePort);

	/**
	 * Disconnect a connection from this ftp.
	 */
	public abstract void quit();

	/**
	 * Remotely change a directory
	 */
	public abstract void changeDir(String fileRemote);

	/**
	 * List of files found on remote server.
	 */
	public abstract List<RemoteFile> searchByFile(String keyword, SearchType type);

	/**
	 * List of files found on remote server.
	 */
	public abstract List<RemoteFile> searchByFile(String remoteDirectoryName, String keyword, SearchType type);

	/**
	 * List of files by dates found on remote server.
	 */
	public abstract List<RemoteFile> searchByDate(Date dateA, Date dateB, SearchTypeDate type);

	/**
	 * List of files by dates found on remote server.
	 */
	public abstract List<RemoteFile> searchByDate(String remoteDirectoryName, Date dateA, Date dateB, SearchTypeDate type);

	/**
	 * Downloads a remote file to local machine.
	 */
	public abstract void download(OutputStream outputStream, String remoteFileName);

	/**
	 * Current path on remote server.
	 */
	public abstract String currentPath();

	/**
	 * Renaming file into remote server.
	 */
	public abstract void rename(String oldpath,String newpath);

	//	public abstract boolean delete(String remoteFileName);
	//	public abstract boolean upload(String remoteFileName);

	public GenericFtp(){

	}

}