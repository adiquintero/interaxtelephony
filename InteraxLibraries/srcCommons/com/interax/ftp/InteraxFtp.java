package com.interax.ftp;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPFile;
import com.enterprisedt.net.ftp.FTPMessageCollector;
import com.enterprisedt.net.ftp.FTPTransferType;
import com.interax.ftp.genericftp.GenericFtp;
import com.interax.ftp.genericftp.RemoteFile;
import com.interax.ftp.genericftp.SearchType;
import com.interax.ftp.genericftp.SearchTypeDate;

public class InteraxFtp extends GenericFtp{

	private FTPClient ftp;
	private FTPMessageCollector listener;

	@Override
	public void changeDir(String fileRemote) {

		try {
			ftp.chdir(fileRemote);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void connect(String user, String password, String remoteHost,
			int remotePort) {

		listener = new FTPMessageCollector();
		ftp = new FTPClient();

		ftp.setMessageListener(listener);

		try{

			ftp.setRemoteHost(remoteHost);
			ftp.connect();
			ftp.login(user, password);

			if(this.isBinary){
				ftp.setType(FTPTransferType.BINARY);
			}
			else{
				ftp.setType(FTPTransferType.ASCII);
			}
			if(this.isPassive){
				ftp.setConnectMode(FTPConnectMode.PASV);
			}
			else{
				ftp.setConnectMode(FTPConnectMode.ACTIVE);
			}

		}catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public void download(OutputStream destStream, String remoteFileName) {
		try {
			ftp.get(destStream, remoteFileName);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void quit() {

		try {
			ftp.quit();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public List<RemoteFile> searchByFile(String keyWord, SearchType type) {
		return this.searchByFile(".", keyWord, type);
	}

	@Override
	public List<RemoteFile> searchByFile(String remoteDirectoryName, String keyWord, SearchType type) {

		try {
			List<RemoteFile> files = new ArrayList<RemoteFile>();
			FTPFile[] ftpFile = ftp.dirDetails(remoteDirectoryName);
			for (int i = 0; i < ftpFile.length; i++) {
				
				RemoteFile remoteFile = new RemoteFile();
				switch (type) {
				case BEGIN:
					if(ftpFile[i].getName().toUpperCase().startsWith(keyWord)){
						remoteFile.setNameFile(ftpFile[i].getName());
						remoteFile.setCreated(ftpFile[i].created());
						remoteFile.setSize(ftpFile[i].size());
						remoteFile.setLocation(ftpFile[i].getPath());
						remoteFile.setLastModified(ftpFile[i].lastModified());
						files.add(remoteFile);
					}
					break;
				case END:
					if(ftpFile[i].getName().toUpperCase().endsWith(keyWord)){
						remoteFile.setNameFile(ftpFile[i].getName());
						remoteFile.setCreated(ftpFile[i].created());
						remoteFile.setSize(ftpFile[i].size());
						remoteFile.setLocation(ftpFile[i].getPath());
						remoteFile.setLastModified(ftpFile[i].lastModified());
						files.add(remoteFile);
					}
					break;
				case WORD:
					if(ftpFile[i].getName().toUpperCase().contains(keyWord)){
						remoteFile.setNameFile(ftpFile[i].getName());
						remoteFile.setCreated(ftpFile[i].created());
						remoteFile.setSize(ftpFile[i].size());
						remoteFile.setLocation(ftpFile[i].getPath());
						remoteFile.setLastModified(ftpFile[i].lastModified());
						files.add(remoteFile);
					}
					break;
				case EQUAL:
					if(ftpFile[i].getName().toUpperCase().equalsIgnoreCase(keyWord)){
						remoteFile.setNameFile(ftpFile[i].getName());
						remoteFile.setCreated(ftpFile[i].created());
						remoteFile.setSize(ftpFile[i].size());
						remoteFile.setLocation(ftpFile[i].getPath());
						remoteFile.setLastModified(ftpFile[i].lastModified());
						files.add(remoteFile);
					}
					break;
				default:
					break;
				}

			}
			return files;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;

	}

	@Override
	public String currentPath() {
		try {
			return ftp.pwd();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void rename(String oldpath,String newpath) {
		try {
			ftp.rename(oldpath, newpath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isBinary() {
		return this.isBinary;
	}

	public void setBinary(boolean binary) {
		this.isBinary = binary;
	}

	public boolean isPassive() {
		return this.isPassive;
	}

	public void setPassive(boolean passive) {
		this.isPassive = passive;
	}

	@Override
	public List<RemoteFile> searchByDate(Date dateA, Date dateB, SearchTypeDate type) {
		return this.searchByDate(".", dateA, dateB, type);
	}

	@Override
	public List<RemoteFile> searchByDate(String remoteDirectoryName, Date dateA, Date dateB, SearchTypeDate type) {
		try {
			List<RemoteFile> files = new ArrayList<RemoteFile>();
			FTPFile[] ftpFile = ftp.dirDetails(remoteDirectoryName);
			Date fileDate = null;

			for (int i = 0; i < ftpFile.length; i++) {

				fileDate = ftpFile[i].lastModified();
				RemoteFile remoteFile = new RemoteFile();
				System.out.println("ftpFile[i].lastModified()"+fileDate);
				switch (type) {
				case GREATER:
					if(dateA.after(fileDate)){
						remoteFile.setNameFile(ftpFile[i].getName());
						remoteFile.setCreated(ftpFile[i].created());
						remoteFile.setSize(ftpFile[i].size());
						remoteFile.setLocation(ftpFile[i].getPath());
						remoteFile.setLastModified(ftpFile[i].lastModified());
						files.add(remoteFile);
					}
					break;
				case LESS:
					if(dateA.before(fileDate)){
						remoteFile.setNameFile(ftpFile[i].getName());
						remoteFile.setCreated(ftpFile[i].created());
						remoteFile.setSize(ftpFile[i].size());
						remoteFile.setLocation(ftpFile[i].getPath());
						remoteFile.setLastModified(ftpFile[i].lastModified());
						files.add(remoteFile);
					}
					break;
				case BETWEEN:
					if(dateA.after(fileDate) && dateB.before(fileDate)){
						remoteFile.setNameFile(ftpFile[i].getName());
						remoteFile.setCreated(ftpFile[i].created());
						remoteFile.setSize(ftpFile[i].size());
						remoteFile.setLocation(ftpFile[i].getPath());
						remoteFile.setLastModified(ftpFile[i].lastModified());
						files.add(remoteFile);
					}
					break;
				case EQUAL:
					if(dateA.equals(fileDate)){
						remoteFile.setNameFile(ftpFile[i].getName());
						remoteFile.setCreated(ftpFile[i].created());
						remoteFile.setSize(ftpFile[i].size());
						remoteFile.setLocation(ftpFile[i].getPath());
						remoteFile.setLastModified(ftpFile[i].lastModified());
						files.add(remoteFile);
					}
					break;
				default:
					break;
				}

			}
			return files;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}