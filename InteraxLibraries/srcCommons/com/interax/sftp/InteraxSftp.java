package com.interax.sftp;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.interax.ftp.genericftp.GenericFtp;
import com.interax.ftp.genericftp.RemoteFile;
import com.interax.ftp.genericftp.SearchType;
import com.interax.ftp.genericftp.SearchTypeDate;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.UserInfo;

public class InteraxSftp extends GenericFtp {

	private Session session = null;
	private ChannelSftp c = null;

	@Override
	public void changeDir(String fileRemote) {

		try {
			c.cd(fileRemote);
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void connect(String user, String password, String remoteHost,
			int remotePort) {

		try{
			JSch jsch=new JSch();
			session=jsch.getSession(user, remoteHost, remotePort);
			session.setPassword(password);
			UserInfo userInfo = new MyUserInfo();
			session.setUserInfo(userInfo);
			session.connect();
			Channel channel = session.openChannel("sftp");
			channel.connect();
			c = (ChannelSftp) channel;
		}catch(Exception e){
			// TODO: handle exception
		}

	}

	@Override
	public void download(OutputStream outputStream, String remoteFileName) {

		try {
			c.get(remoteFileName, outputStream);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public void quit() {
		session.disconnect();
		c.quit();

	}

	@Override
	public List<RemoteFile> searchByFile(String keyWord, SearchType type) {
		return this.searchByFile(".", keyWord, type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RemoteFile> searchByFile(String remoteDirectoryName, String keyword, SearchType type) {

		List<RemoteFile> files = new ArrayList<RemoteFile>();

		try{
			Vector vv = c.ls(remoteDirectoryName);
			Date fileDate = null;
			System.out.println("vv.size="+vv.size());
			if (vv != null) {
				for (int ii = 0; ii < vv.size(); ii++) {
					
					RemoteFile remoteFile = new RemoteFile();
					Object obj = vv.elementAt(ii);
					if (obj instanceof com.jcraft.jsch.ChannelSftp.LsEntry) {
						com.jcraft.jsch.ChannelSftp.LsEntry lsEntry = ((com.jcraft.jsch.ChannelSftp.LsEntry) obj);
						com.jcraft.jsch.SftpATTRS sftpATTRS  = lsEntry.getAttrs();
						fileDate = new Date(((long)sftpATTRS.getMTime())*1000);

						switch (type) {
						case BEGIN:
							if(lsEntry.getFilename().startsWith(keyword)){
								remoteFile.setNameFile(lsEntry.getFilename());
								remoteFile.setCreated(fileDate);
								remoteFile.setSize(sftpATTRS.getSize());
								files.add(remoteFile);
							}
							break;
						case END:
							if(lsEntry.getFilename().endsWith(keyword)){
								remoteFile.setNameFile(lsEntry.getFilename());
								remoteFile.setCreated(fileDate);
								remoteFile.setSize(sftpATTRS.getSize());
								files.add(remoteFile);
							}
							break;
						case WORD:
							if(lsEntry.getFilename().contains(keyword)){
								remoteFile.setNameFile(lsEntry.getFilename());
								remoteFile.setCreated(fileDate);
								remoteFile.setSize(sftpATTRS.getSize());
								files.add(remoteFile);
							}
							break;
						case EQUAL:
							if(lsEntry.getFilename().equalsIgnoreCase(keyword)){
								remoteFile.setNameFile(lsEntry.getFilename());
								remoteFile.setCreated(fileDate);
								remoteFile.setSize(sftpATTRS.getSize());
								files.add(remoteFile);
							}
							break;
						default:
							break;
						}

					}
				}
			}
			return files;
		}catch (Exception e) {
			// TODO: handle exception
		}
		return null;

	}


	private class MyUserInfo implements UserInfo {
		String passwd;
		public String getPassphrase() {return "";}
		public String getPassword() {return passwd;}
		public boolean promptPassphrase(String message) {return true;}
		public boolean promptPassword(String message) {return true;}
		public boolean promptYesNo(String message) {return true;}
		public void showMessage(String message) {}
	}


	@Override
	public String currentPath() {
		return c.pwd();
	}

	@Override
	public void rename(String oldpath,String newpath) {
		try {
			c.rename(oldpath, newpath);
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<RemoteFile> searchByDate(Date dateA, Date dateB, SearchTypeDate type) {
		return this.searchByDate(".", dateA, dateB, type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RemoteFile> searchByDate(String remoteDirectoryName, Date dateA, Date dateB, SearchTypeDate type) {
		List<RemoteFile> files = new ArrayList<RemoteFile>();

		try{
			Vector vv = c.ls(remoteDirectoryName);
			Date fileDate = null;

			if (vv != null) {
				for (int ii = 0; ii < vv.size(); ii++) {
					
					RemoteFile remoteFile = new RemoteFile();
					Object obj = vv.elementAt(ii);
					if (obj instanceof com.jcraft.jsch.ChannelSftp.LsEntry) {
						com.jcraft.jsch.ChannelSftp.LsEntry lsEntry = ((com.jcraft.jsch.ChannelSftp.LsEntry) obj);
						com.jcraft.jsch.SftpATTRS sftpATTRS  = lsEntry.getAttrs();
						fileDate = new Date(((long)sftpATTRS.getMTime())*1000);

						switch (type) {
						case GREATER:
							if(dateA.after(fileDate)){
								remoteFile.setNameFile(lsEntry.getFilename());
								remoteFile.setCreated(fileDate);
								remoteFile.setSize(sftpATTRS.getSize());
								files.add(remoteFile);
							}
							break;
						case LESS:
							if(dateA.before(fileDate)){
								remoteFile.setNameFile(lsEntry.getFilename());
								remoteFile.setCreated(fileDate);
								remoteFile.setSize(sftpATTRS.getSize());
								files.add(remoteFile);
							}
							break;
						case BETWEEN:
							if(dateA.after(fileDate) && dateB.before(fileDate)){
								remoteFile.setNameFile(lsEntry.getFilename());
								remoteFile.setCreated(fileDate);
								remoteFile.setSize(sftpATTRS.getSize());
								files.add(remoteFile);
							}
							break;
						case EQUAL:
							if(dateA.equals(fileDate)){
								remoteFile.setNameFile(lsEntry.getFilename());
								remoteFile.setCreated(fileDate);
								remoteFile.setSize(sftpATTRS.getSize());
								files.add(remoteFile);
							}
							break;
						default:
							break;
						}

					}
				}
			}
			return files;
		}catch (Exception e) {
			// TODO: handle exception
		}
		return null;

	}
}
