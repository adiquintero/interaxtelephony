package com.interax.db.connectionpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//import java.util.Hashtable;
//import java.util.Vector;
import java.util.logging.Level;

import com.interax.logging.GenericLogger;
import com.interax.logging.LoggerFactory;
import com.mysql.jdbc.Driver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class InteraxConnectionPool implements Runnable 
{   
	// Id del demonio
	private int daemonId = 0;
	// Referencia del proyecto que lo invoco
	private String projectReferenceName = "";
	
	// ConnectionPool VARS
	// if thread have to be alive
	private boolean isAlive = true;
	private boolean isDebug = false;
	// Time that wait for cleanner process.
	private int cleannerCheckTimeOut = 500;
	// The cleanup thread
	private Thread m_CleanupThread = null;

	// Minimal quantity of connections.
	private int m_MinConnectionCount = 0;
	// Maximal quantity of connections.
	private int m_MaxConnectionCount = 0;
	// Represents timeOut that a connection could be idle in milliseconds.
	private int idleTimeOut = 0;
	// Time in milliseconds
	private int watingForConnection = 0;

	// cache para tener la fecha de creacion de las conecciones
	private HashMap<String, Long> createdTimeMillis = new HashMap<String, Long>();
	// A list of available connections for use.
	@SuppressWarnings("unchecked")
	private List m_AvailableConnections = new ArrayList();
	// A list of connections being used currently.
	@SuppressWarnings("unchecked")
	private List m_UsedConnections = new ArrayList();

	// DATABASE VARS
	// The URL string used to connect to the database
	private String m_URLString = null;
	// The username used to connect to the database
	private String m_UserName = null;   
	// The password used to connect to the database
	private String m_Password = null;   
	// Driver to create new connections
	private Driver driver = null;
	// LOGGER
	private GenericLogger logger = null;

	public InteraxConnectionPool(String urlString, String user, String password, Integer m_MinConnectionCount, Integer m_MaxConnectionCount) throws Exception
	{
		this(urlString,user,password,30000,2000,m_MinConnectionCount, m_MaxConnectionCount, false);
	}

	public InteraxConnectionPool(String urlString, String user, String password, Integer m_MinConnectionCount, Integer m_MaxConnectionCount, boolean debug) throws Exception
	{
		this(urlString,user,password,30000,2000,m_MinConnectionCount, m_MaxConnectionCount, debug);
	}

	@SuppressWarnings("unchecked")
	public InteraxConnectionPool(String urlString, String user, String password, Integer idleTimeOut, Integer watingForConnection, Integer m_MinConnectionCount, Integer m_MaxConnectionCount, boolean debug) throws Exception
	{
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.m_URLString = urlString;
		this.m_UserName = user;
		this.m_Password = password;
		this.idleTimeOut = idleTimeOut;
		this.watingForConnection = watingForConnection;
		this.m_MinConnectionCount = m_MinConnectionCount;
		this.m_MaxConnectionCount = m_MaxConnectionCount;
		this.isDebug = debug;

		this.logger.log(Level.INFO, daemonId +"|"+projectReferenceName + "|START CONSTRUCTOR");
		
		if ((idleTimeOut | watingForConnection | m_MinConnectionCount | m_MaxConnectionCount ) < 0)
			throw new Exception("Integer parameters can not be less than zero");
		if (m_MinConnectionCount > m_MaxConnectionCount)
			throw new Exception("The minimum can not be greater than the maximum");
		if (m_URLString == null || m_UserName == null || m_Password == null )
			throw new Exception("The string parameter can not be null");

		
		Class c = Class.forName("com.mysql.jdbc.Driver");
		driver = (Driver)c.newInstance();

		for(int i=0; i < m_MinConnectionCount; i++)
		{
			this.logger.log(Level.INFO, daemonId +"|"+projectReferenceName + "|Creando connection "+ i +" pool");
			createConnectionInPool(m_AvailableConnections);
		}

		this.logger.log(Level.INFO, daemonId +"|"+projectReferenceName + "|Pool created with: " + availableCount());
		m_CleanupThread = new Thread(this);
		m_CleanupThread.start();
		this.logger.log(Level.INFO, daemonId +"|"+projectReferenceName + "|END CONSTRUCTOR");

	}

	@SuppressWarnings("unchecked")
	private synchronized Connection createConnectionInPool(List vectorConnections) throws SQLException
	{
		this.logger.log(Level.INFO, daemonId +"|"+projectReferenceName + "|START createConnectionInPool");
		Connection conn = getRealConnection();
		createdTimeMillis.put(conn.toString(), System.currentTimeMillis());
		vectorConnections.add(conn);
		this.logger.log(Level.INFO, daemonId +"|"+projectReferenceName + "|END createConnectionInPool");
		return conn;
	}

	@SuppressWarnings("unchecked")
	private synchronized void removeConnectionFromPool(List vectorConnections, Connection conn)
	{
		createdTimeMillis.remove(conn.toString());
		vectorConnections.remove(conn);
	}

	protected synchronized void removeUsedConnectionFromPool(Connection conn)
	{
		removeConnectionFromPool(m_UsedConnections,conn);
		setAvailableConnectionInPool(conn);
		this.notify();
	}

	@SuppressWarnings("unchecked")
	private synchronized Connection setAvailableConnectionInPool(Connection conn)
	{
		createdTimeMillis.put(conn.toString(), System.currentTimeMillis());
		m_AvailableConnections.add(conn);
		return conn;
	}

	private java.sql.Connection getRealConnection() throws SQLException
	{   
		this.logger.log(Level.INFO, daemonId +"|"+projectReferenceName + "|START REAL CONNECTION");
		DriverManager.registerDriver(driver);
		//System.out.println(" REAL CONNECTION ENTRADA 2 m_URLString: "+m_URLString+" m_UserName: "+m_UserName+" m_Password: "+m_Password+" ************************");
		Connection conn = (Connection) DriverManager.getConnection(m_URLString, m_UserName, m_Password);
		InteraxConnection c = new InteraxConnection(conn, this);
		this.logger.log(Level.INFO, daemonId +"|"+projectReferenceName + "|END REAL CONNECTION");
		return c;
	}

	@SuppressWarnings("unchecked")
	public synchronized Connection getConnection() throws Exception
	{
		this.logger.log(Level.INFO, daemonId +"|"+projectReferenceName + "|START GET CONNECTION");
		InteraxConnection conn = null;
		long waittime = System.currentTimeMillis();

		// TODO mejorar este parche para esperar porq que se libere una connection
		while( (m_UsedConnections.size() == m_MaxConnectionCount)  && (System.currentTimeMillis() - waittime) < watingForConnection){
			this.wait(1);
		}

		if(m_UsedConnections.size() == m_MaxConnectionCount){
			this.logger.log(Level.SEVERE, daemonId +"|"+projectReferenceName + "|Max connection reached, could not allocate more connections");
			throw new Exception("Max connection reached, could not allocate more connections");
		}else{
			if (m_AvailableConnections.size() > 0){
				conn = (InteraxConnection)m_AvailableConnections.get(m_AvailableConnections.size() - 1); //Last element
				m_AvailableConnections.remove(conn);
				createdTimeMillis.put(conn.toString(), System.currentTimeMillis());
				m_UsedConnections.add(conn);           
			}else {
				conn = (InteraxConnection) createConnectionInPool(m_UsedConnections);
			}
		}
		this.logger.log(Level.INFO, daemonId +"|"+projectReferenceName + "|END GET CONNECTION");
		return conn;
	}

	private boolean validate(Connection conn) {

		try {
			conn.getMetaData();
		}catch (Exception e) {
			return false;
		}
		return true;
	}

	public int availableCount()
	{
		return m_AvailableConnections.size();
	}
	public int usedCount()
	{
		return m_UsedConnections.size();
	}

	public void run()
	{
		try
		{
			if (isDebug) this.logger.log(Level.INFO, daemonId +"|"+projectReferenceName + "|Pool is Alive");
			while(true && isAlive)
			{
				synchronized(this)
				{
					if (isDebug){
						this.logger.log(Level.INFO, daemonId +"|"+projectReferenceName + "|CLEANUP : Available Connections : " + availableCount());
						this.logger.log(Level.INFO, daemonId +"|"+projectReferenceName + "|STATE : Used Connections : " + usedCount());
					}
					int availableSize = availableCount();
					for (int i = 0; i < availableSize; i++) {

						InteraxConnection c = (InteraxConnection) m_AvailableConnections.get(i);
						com.mysql.jdbc.Connection conn = (com.mysql.jdbc.Connection)c.getConnection();

						if (isDebug) this.logger.log(Level.INFO, daemonId +"|"+projectReferenceName + "|Evaluating connection: " + c.toString()+ " conn.getIdleFor(): " + conn.getIdleFor()); 
						if (conn.getIdleFor() >= idleTimeOut || conn == null || conn.isClosed() || !validate(conn)){
							if (isDebug){
								this.logger.log(Level.INFO, daemonId +"|"+projectReferenceName + "|Remove from available cause: conn.getIdleFor() >= idleTimeOut");
								this.logger.log(Level.INFO, daemonId +"|"+projectReferenceName + "|A: conn.getIdleFor(): " +conn.getIdleFor());
							}
							conn.close();
							removeConnectionFromPool(m_AvailableConnections, c);
							i--;
							availableSize = availableCount();
						}
					}

					int usedSize = usedCount();
					for (int i = 0; i < usedSize; i++) {

						InteraxConnection c = (InteraxConnection) m_UsedConnections.get(i);
						com.mysql.jdbc.Connection conn = (com.mysql.jdbc.Connection)c.getConnection();

						if ((conn.getIdleFor() != 0 && conn.getIdleFor() >= idleTimeOut) || conn == null || conn.isClosed() || !validate(conn)){
							if (isDebug){
								this.logger.log(Level.INFO, daemonId +"|"+projectReferenceName + "|Remove from used cause: conn.getIdleFor() != 0 && conn.getIdleFor() >= idleTimeOut");
								this.logger.log(Level.INFO, daemonId +"|"+projectReferenceName + "|U: conn.getIdleFor(): " +conn.getIdleFor());
							}
							conn.close();
							removeConnectionFromPool(m_UsedConnections, c);
							i--;
							usedSize = usedCount();
						}
					}
					if (isDebug){
						this.logger.log(Level.INFO, daemonId +"|"+projectReferenceName + "|CLEANUP 2: Available Connections : " + availableCount());
						this.logger.log(Level.INFO, daemonId +"|"+projectReferenceName + "|STATE 2: Used Connections : " + usedCount());
					}
					while(availableCount() + usedCount()  < m_MinConnectionCount)
					{
						Connection conn = createConnectionInPool(m_AvailableConnections);
						if (isDebug) this.logger.log(Level.INFO, daemonId +"|"+projectReferenceName + "|Cree connection: " + conn.toString()); 
						
					}
				}
				if (isDebug){
					this.logger.log(Level.INFO, daemonId +"|"+projectReferenceName + "|CLEANUP 3: Available Connections : " + availableCount());
					this.logger.log(Level.INFO, daemonId +"|"+projectReferenceName + "|STATE 3: Used Connections : " + usedCount());
				}
				Thread.sleep(cleannerCheckTimeOut);
			}
			if (isDebug) this.logger.log(Level.INFO, daemonId +"|"+projectReferenceName + "|Pool finished" ); 
		}
		catch(Exception e)
		{
			this.logger.log(Level.INFO, daemonId +"|"+projectReferenceName + "|Something is wrong with the pool, see details: " + e.getMessage() );
			try {
				throw e;
			} catch (Exception e1) { e1.printStackTrace();}
		}finally{
			if (isDebug) this.logger.log(Level.INFO, daemonId +"|"+projectReferenceName + "|Cleanning pooled connections " ); 
			try {
				int availableSize = availableCount();
				for (int i = 0; i < availableSize; i++) {

					InteraxConnection c = (InteraxConnection) m_AvailableConnections.get(i);
					com.mysql.jdbc.Connection conn = (com.mysql.jdbc.Connection)c.getConnection();
					if (conn != null )conn.close();
					removeConnectionFromPool(m_AvailableConnections, c);
					i--;
					availableSize = availableCount();
				}
				int usedSize = usedCount();
				for (int i = 0; i < usedSize; i++) {

					InteraxConnection c = (InteraxConnection) m_UsedConnections.get(i);
					com.mysql.jdbc.Connection conn = (com.mysql.jdbc.Connection)c.getConnection();
					if (conn != null)conn.close();
					removeConnectionFromPool(m_UsedConnections, c);
					i--;
					usedSize = usedCount();
				}
			}catch (Exception e) {}
			if (isDebug) this.logger.log(Level.INFO, daemonId +"|"+projectReferenceName + "|Pooled connections cleanned " ); 
		}
	}

	public synchronized void close()
	{
		this.isAlive = false;
	}
	

}