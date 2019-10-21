package com.interax.persistence.datamanagers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;

import javax.sql.DataSource;

import com.interax.persistence.utils.ServiceLocator;


/**
 * NullDatamanager is used to control transactions across several real Datamanagers  
 * @author vicente
 */
public abstract class NullDataManager extends GenericDataManager
{
	
	/**
	 * A factory for connections to the physical data source that this DataSource object represents.
	 */
	protected DataSource dataSource;
	
	/**
	 * Connection given to use by this manager (controlledMode)
	 */
	protected Connection controlledConnection;

	
	/** TODO Check this constructor */
	protected NullDataManager(){
		//Dummy Constructor for child implementation
	}
	
	/**
	 * Create an instance that connects to the specified dataSourceName
	 * @param String dataSourceName
	 */
	public NullDataManager(String dataSourceName)
	{
		dataSource = ServiceLocator.getInstance().getDataSource(dataSourceName); 
		setLogger(getClassLogger(this.getClass())); //LoggerFactory.getLogger(this.getClass());
	}
	
	/**
	 * Create an instance that connects to the specified connection
	 * @param Connection controlledConnection
	 */
	public NullDataManager(Connection controlledConnection)
	{
		this.controlledConnection = controlledConnection;
		setLogger(getClassLogger(this.getClass())); //LoggerFactory.getLogger(this.getClass());
	}

	/**
	 * Closes database operation resources
	 */
	protected void close(Connection conn, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch(Exception ex) {
            }
        }
            
        if (stmt != null) {
            try {
                stmt.close();
            } catch(Exception ex) {
            }
        }
            
        if (conn != null) {
            try {
                conn.close();
            } catch(Exception ex) {
            	getLogger().log(Level.SEVERE, "Couldn't close connection... in close method ", ex);
            }
        }
    }
	
}
