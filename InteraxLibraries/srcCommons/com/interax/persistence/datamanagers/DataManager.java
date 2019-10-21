package com.interax.persistence.datamanagers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
//import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
//import java.util.Vector;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import com.interax.date.InteraxDate;
import com.interax.persistence.common.AttributeInfo;
import com.interax.persistence.common.DBObject;
import com.interax.persistence.utils.ServiceLocator;
import com.interax.utils.InteraxSerializedObject;


/**
 * Map a database table and implements CRUD operations
 * @author nelio
 */
public abstract class DataManager extends GenericDataManager
{
	
	/**
	 * A factory for connections to the physical data source that this DataSource object represents.
	 */
	protected DataSource dataSource;
	
	/**
	 * Stores the MySQL DESCRIBE output
	 */
	protected List<AttributeInfo> attributes;

	/**
	 * Connection given to use by this manager (controlledMode)
	 */
	protected Connection controlledConnection;

	/**
	 * Indicates if datamanager should replace negative values in unsigned fields without original Object modification when inserting 
	 */
	protected boolean allowInsertUnsignedNegatives;
	
	
	/**
	 * Must provide the where clause for load a unique java object (Example: id='1')
	 * @param Object obj
	 * @return the primary key clause for where sentence
	 */
	public abstract String getPrimaryKey(Object obj);
	
	/**
	 * Must provide the database table name
	 * @return the database table name
	 */
	public abstract String getTableName();
	
	/**
	 * Must provide the database table name
	 * @param String joinType (Example: "LEFT JOIN")
	 * @param String joinTable (Example: "table2")
	 * @param String joinCondition (Example: "table1.id=table2.id")
	 * @return the database table name
	 */
	public String getJoinClause(String joinType, String joinTable, String joinCondition){
		
		String joinClause = "";
		if(joinType!=null && !joinType.trim().equals("")){
			
			String joinTableAlias = "";
			if(joinTable.contains(" ")){
				String joinTableFields[] = joinTable.split(" ");
				if(joinTableFields.length==2){
					joinTable = joinTableFields[0];
					joinTableAlias = joinTableFields[1];
				}
			}
			joinClause += joinType + " `" + joinTable + "` " + joinTableAlias + " ON (" + joinCondition + ")" ;
		}
		return joinClause;
	}
	
	/**
	 * Must provide the data bean that maps a database row
	 * @return an instance of the java object that maps a row
	 */
	public abstract Object getObject();
	
	/** TODO Check this constructor */
	public DataManager(){
		//Dummy Constructor for child implementation
	}
	
	/**
	 * Create an instance that connects to the specified dataSourceName
	 * @param String dataSourceName
	 */
	public DataManager(String dataSourceName)
	{
		attributes = new ArrayList<AttributeInfo>();
		dataSource = ServiceLocator.getInstance().getDataSource(dataSourceName);
		Connection conn = null;
		Statement s = null;
		ResultSet rs = null;
		
		try
		{
			setLogger(getClassLogger(this.getClass())); //LoggerFactory.getLogger(this.getClass());
			conn = dataSource.getConnection();
			s = conn.createStatement();
			s.executeQuery ("DESCRIBE `"+getTableName()+"`");
			rs = s.getResultSet();
			while(rs.next())
			{
				AttributeInfo attrInfo = new AttributeInfo();
				attrInfo.setName(rs.getString("field"));
				attrInfo.setType(rs.getString("type"));
				attrInfo.setAllowNull(rs.getBoolean("null"));
				attrInfo.setKey(rs.getString("key"));
				attrInfo.setDefaultValue(rs.getString("default"));
				attrInfo.setExtraInfo(rs.getString("extra"));
				attributes.add(attrInfo);
			}
			this.controlledConnection = null;
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error creating DataManager", e);
		}
		finally
		{
			close(conn,s,rs);
		}
	}

	/**
	 * Create an instance that connects to a DB using the given connection (controlledMode: commits/rollbacks must be controlled by user)
	 * @param Connection controlledConnection
	 */
	public DataManager(Connection controlledConnection)
	{
		attributes = new ArrayList<AttributeInfo>();
		Statement s = null;
		ResultSet rs = null;
		try
		{
			setLogger(getClassLogger(this.getClass())); //LoggerFactory.getLogger(this.getClass());
			s = controlledConnection.createStatement();
			s.executeQuery ("DESCRIBE `"+getTableName()+"`");
			rs = s.getResultSet();
			while(rs.next())
			{
				AttributeInfo attrInfo = new AttributeInfo();
				attrInfo.setName(rs.getString("field"));
				attrInfo.setType(rs.getString("type"));
				attrInfo.setAllowNull(rs.getBoolean("null"));
				attrInfo.setKey(rs.getString("key"));
				attrInfo.setDefaultValue(rs.getString("default"));
				attrInfo.setExtraInfo(rs.getString("extra"));
				attributes.add(attrInfo);
			}
		   this.controlledConnection = controlledConnection;
		   this.dataSource = null;
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error creating DataManager (controlledMode)", e);
		}
	}

	
	/**
	 * Load the Object from the database identified by id
	 * @param Object id
	 * @return the object identified by the id
	 * @throws Exception
	 */
	public Object load(Object id) throws Exception
	{
		try
		{			 
			String query = "SELECT * FROM `"+getTableName()+"` WHERE "+getPrimaryKey(id);
			List<Object> list = executeQuery(query);
			if(list!=null && list.size()==1)
				return list.get(0);
			else if(list!=null && list.size()>1)
				throw new Exception("Error loading Object: Query return multiple rows");
			else
				return null;
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error loading Object on table " + getTableName(), e);
			throw e;
		}
	}
	
	/**
	 * Count the Objects from the database specified by whereClause
	 * @param String whereClause (Example: "field1='value1' AND field2='value2'")
	 * @return a long representing the row count
	 * @throws Exception
	 */
	public long listCount(String whereClause) throws Exception
	{
		return listJoinCount(whereClause, null, null, null);
	}

	/**
	 * Count the Objects from the database specified by whereClause
	 * @param String whereClause (Example: "field1='value1' AND field2='value2'")
	 * @param String joinType (Example: "LEFT JOIN")
	 * @param String joinTable (Example: "table2")
	 * @param String joinCondition (Example: "table1.id=table2.id")
	 * @return a long representing the row count
	 * @throws Exception
	 */
	public long listJoinCount(String whereClause, String joinType, String joinTable, String joinCondition) throws Exception
	{
		try
		{
			whereClause = excludeSystemObjects(whereClause);
			String query = "SELECT COUNT(*) FROM `"+getTableName()+"`";
			String joinClause = getJoinClause(joinType, joinTable, joinCondition);
			String where = (whereClause!=null && !whereClause.trim().equals("")) ? " WHERE "+whereClause : "";
			return executeScalar(query+joinClause+where);
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error counting Objects on table " + getTableName(), e);
			throw e;
		}
	}
	
	
	
	/**
	 * Sum the field from the database specified by whereClause
	 * @param String field (Example: "field1")
	 * @param String whereClause (Example: "field1='value1' AND field2='value2'")
	 * @return a Double representing the Sum Value or null in other case
	 * @throws Exception
	 */
	public Double listSum(String field, String whereClause) throws Exception
	{
		try
		{
			List<String> fields = new ArrayList<String>();
			fields.add(field);
			HashMap<String, Double> ret = listSum(fields, whereClause);
			
			if(ret == null)
				return null;
			else
				return ret.get(field);
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error SUMing Objects on table " + getTableName(), e);
			throw e;
		}
	}
	
	/**
	 * Sum the field from the database specified by whereClause
	 * @param String field (Example: "field1")
	 * @param String whereClause (Example: "field1='value1' AND field2='value2'")
	 * @param int begin 
	 * @param int count 
	 * @return a Double representing the Sum Value or null in other case
	 * @throws Exception
	 */
	public Double listSum(String field, String whereClause, int begin, int count) throws Exception
	{
		return this.listSum(field, whereClause, null, begin, count);
	}
	
	public Double listSum(String field, String whereClause, String orderClause, int begin, int count) throws Exception
    {
        try
        {
            List<String> fields = new ArrayList<String>();
            fields.add(field);
            HashMap<String, Double> ret = listSum(fields, whereClause, orderClause, begin, count);
           
            if(ret == null)
                return null;
            else
                return ret.get(field);
        }
        catch(Exception e)
        {
            getLogger().log(Level.SEVERE, "Error SUMing Objects on table " + getTableName(), e);
            throw e;
        }
    }
	
	
	
	/**
	 * Sum the field from the database specified by whereClause
	 * @param Vector<String> fields, Vector of fields elements that want to get the SUM 
	 * @param String whereClause (Example: "field1='value1' AND field2='value2'")
	 * @return Hashtable<String, Double> representing the SUM value of every field or null in other case
	 * @throws Exception
	 */
	public HashMap<String, Double> listSum(List<String> fields, String whereClause) throws Exception
	{
		try
		{
			return listSum(fields, whereClause, -1, -1);
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error SUMing Objects on table " + getTableName(), e);
			throw e;
		}
	}
	
	/**
	 * Sum the field from the database specified by whereClause
	 * @param Vector<String> fields, Vector of fields elements that want to get the SUM 
	 * @param String whereClause (Example: "field1='value1' AND field2='value2'")
	 * @param int begin 
	 * @param int count 
	 * @return Hashtable<String, Double> representing the SUM value of every field or null in other case
	 * @throws Exception
	 */
	public HashMap<String, Double> listSum(List<String> fields, String whereClause, int begin, int count) throws Exception
	{
		return this.listSum(fields, whereClause, null, begin, count);
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Double> listSum(List<String> fields, String whereClause,String orderClause, int begin, int count) throws Exception
    {
        try
        {
            whereClause = excludeSystemObjects(whereClause);
           
            if(begin>=0 && count>=0){
                String limit = "";
               
                String outerQuery = " SELECT ", innerQuery = " SELECT ", separator = "";
                for (Iterator iter = fields.iterator(); iter.hasNext();) {
                    String field = (String) iter.next();
                    outerQuery += separator + " SUM("+"temp."+field+") as "+field;
                    innerQuery += separator + " "+field+" ";
                    separator = ",";
                }
                innerQuery += " FROM `"+getTableName()+"`";
                String where = (whereClause!=null && !whereClause.trim().equals("")) ? " WHERE "+whereClause : "";
               
                limit = " LIMIT "+begin+","+count;
               
                if(orderClause==null) orderClause = "";
                if(orderClause!=null) orderClause = " ORDER BY "+orderClause;
                
                outerQuery += " FROM (" + innerQuery+where+orderClause+limit + " ) as temp";
                           
                return executeScalarSum(fields, outerQuery);
               
            }else{
           
                String query = "SELECT ", separator = "";
                for (Iterator iter = fields.iterator(); iter.hasNext();) {
                    String field = (String) iter.next();
                    query += separator + " SUM("+field+") as "+field;
                    separator = ",";
                }
                query += " FROM `"+getTableName()+"`";
                String where = (whereClause!=null && !whereClause.trim().equals("")) ? " WHERE "+whereClause : "";
           
                return executeScalarSum(fields, query+where);
            }
           
        }
        catch(Exception e)
        {
            getLogger().log(Level.SEVERE, "Error SUMing Objects on table " + getTableName(), e);
            throw e;
        }
    }
	
	/**
	 * Counts objects that match with specified keyWords 
	 * @param String keyWords (Example: red shoes)
	 * @return a long representing the row count
	 * @throws Exception
	 */
	public long searchCount(String keyWords) throws Exception
	{
		try
		{
			String query = "SELECT COUNT(*) FROM `"+getTableName()+"`";
			String whereClause = getWhereClause(keyWords);
			whereClause = excludeSystemObjects(whereClause);
			String where = (whereClause!=null && !whereClause.trim().equals("")) ? " WHERE "+whereClause : "";
			return executeScalar(query+where);
			// TODO test whit: return searchCount(keyWords, null);
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error counting search Objects on table " + getTableName(), e);
			throw e;
		}
	}
	
	/**
	 * Counts objects that match with specified keyWords 
	 * @param String keyWords (Example: red shoes)
	 * @param String whereClause (Example: "field1='value1' AND field2='value2'")
	 * @return a long representing the row count
	 * @throws Exception
	 */
	public long searchCount(String keyWords, String whereClause) throws Exception
	{
		try
		{
			String where1 = (whereClause==null) ? "" : whereClause;
			String where2 = getWhereClause(keyWords);
			String where = "";
			if(where2!=null && !where2.trim().equals("") && where1!=null && !where1.trim().equals(""))
				where = where1 + " AND " + where2;
			else if(where2!=null && !where2.trim().equals(""))
				where = where2;
			else if(where1!=null && !where1.trim().equals(""))
				where = where1;
			
			String query = "SELECT COUNT(*) FROM `"+getTableName()+"`";
			where = excludeSystemObjects(where);
			where = (where!=null && !where.trim().equals("")) ? " WHERE "+where : "";
			return executeScalar(query+where);
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error counting search Objects on table " + getTableName(), e);
			throw e;
		}
	}
	
	
	
	/**
	 * SUM objects that match with specified keyWords
	 * @param String field (Example: "field1") 
	 * @param String keyWords (Example: red shoes)
	 * @return a long representing the row count
	 * @throws Exception
	 */
	public Double searchSum(String field, String keyWords) throws Exception
	{
		try
		{
			List<String> fields = new ArrayList<String>();
			fields.add(field);
			
			HashMap<String, Double> ret = searchSum(fields, keyWords, null);
			if(ret == null)
				return null;
			else
				return ret.get(field);
			
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error counting search Objects on table " + getTableName(), e);
			throw e;
		}
	}
	
	/**
	 * SUM objects that match with specified keyWords
	 * @param String field (Example: "field1") 
	 * @param String keyWords (Example: red shoes)
	 * @param String whereClause (Example: "field1='value1' AND field2='value2'")
	 * @return a long representing the row count
	 * @throws Exception
	 */
	public Double searchSum(String field, String keyWords, String whereClause) throws Exception
	{
		try
		{
			List<String> fields = new ArrayList<String>();
			fields.add(field);
			
			HashMap<String, Double> ret = searchSum(fields, keyWords, whereClause);
			if(ret == null)
				return null;
			else
				return ret.get(field);
			
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error counting search Objects on table " + getTableName(), e);
			throw e;
		}
	}
	
	/**
	 * SUM objects that match with specified keyWords
	 * @param String field (Example: "field1") 
	 * @param String keyWords (Example: red shoes)
	 * @param String whereClause (Example: "field1='value1' AND field2='value2'")
	 * @return a long representing the row count
	 * @throws Exception
	 */
	public Double searchSum(String field, String keyWords, String whereClause, int begin, int count) throws Exception
	{
		try
		{
			List<String> fields = new ArrayList<String>();
			fields.add(field);
			
			HashMap<String, Double> ret = searchSum(fields, keyWords, whereClause, begin, count);
			if(ret == null)
				return null;
			else
				return ret.get(field);
			
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error counting search Objects on table " + getTableName(), e);
			throw e;
		}
	}
	
	
	/**
	 * SUM objects that match with specified keyWords and whereClause
	 * @param Vector<String> fields, Vector of fields elements that want to get the SUM 
	 * @param String keyWords (Example: red shoes)
	 * @return a long representing the row count
	 * @throws Exception
	 */
	public HashMap<String, Double> searchSum(List<String> fields, String keyWords) throws Exception
	{
		try
		{
			return searchSum(fields, keyWords, null, -1, -1);
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error counting search Objects on table " + getTableName(), e);
			throw e;
		}
	}
	
	/**
	 * SUM objects that match with specified keyWords and whereClause
	 * @param Vector<String> fields, Vector of fields elements that want to get the SUM 
	 * @param String keyWords (Example: red shoes)
	 * @param String whereClause (Example: "field1='value1' AND field2='value2'")
	 * @return a long representing the row count
	 * @throws Exception
	 */
	public HashMap<String, Double> searchSum(List<String> fields, String keyWords, String whereClause) throws Exception
	{
		try
		{
			return searchSum(fields, keyWords, whereClause, -1, -1);
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error counting search Objects on table " + getTableName(), e);
			throw e;
		}
	}
	
	/**
	 * SUM objects that match with specified keyWords and whereClause
	 * @param Vector<String> fields, Vector of fields elements that want to get the SUM 
	 * @param String keyWords (Example: red shoes)
	 * @param String whereClause (Example: "field1='value1' AND field2='value2'")
	 * @return a long representing the row count
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Double> searchSum(List<String> fields, String keyWords, String whereClause, int begin, int count) throws Exception
	{
		try
		{
			String where1 = (whereClause==null) ? "" : whereClause;
			String where2 = getWhereClause(keyWords);
			String where = "";
			if(where2!=null && !where2.trim().equals("") && where1!=null && !where1.trim().equals(""))
				where = where1 + " AND " + where2;
			else if(where2!=null && !where2.trim().equals(""))
				where = where2;
			else if(where1!=null && !where1.trim().equals(""))
				where = where1;
			
			where = excludeSystemObjects(where);
			where = (where!=null && !where.trim().equals("")) ? " WHERE "+where : "";
			
			if(begin>=0 && count>=0){
				String limit = "";
				
				String outerQuery = " SELECT ", innerQuery = " SELECT ", separator = "";
				for (Iterator iter = fields.iterator(); iter.hasNext();) {
					String field = (String) iter.next();
					outerQuery += separator + " SUM("+field+") as "+field;
					innerQuery += separator + " "+field+" ";
					separator = ",";
				}
				innerQuery += " FROM `"+getTableName()+"`";
				
				limit = " LIMIT "+begin+","+count;
				
				outerQuery += " FROM (" + innerQuery+where+limit + " ) as temp";
				
				return executeScalarSum(fields, outerQuery);
				
			}else{
			
				String query = "SELECT ", separator = "";
				for (Iterator iter = fields.iterator(); iter.hasNext();) {
					String field = (String) iter.next();
					query += separator + " SUM("+field+") as "+field;
					separator = ",";
				}
				query += " FROM `"+getTableName()+"`";
				
				return executeScalarSum(fields, query+where);
			}
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error counting search Objects on table " + getTableName(), e);
			throw e;
		}
	}
	
	
	
	/**
	 * List the Objects from the database specified by whereClause
	 * @param String whereClause (Example: "field1='value1' AND field2='value2'")
	 * @return a List containing related Objects
	 * @throws Exception
	 */
	public List<Object> list(String whereClause) throws Exception
	{
		return listJoin(whereClause, null, null, null);
	}

	
	
	/**
	 * List the Objects from the database specified by whereClause
	 * @param String whereClause (Example: "field1='value1' AND field2='value2'")
	 * @param String joinType (Example: "LEFT JOIN")
	 * @param String joinTable (Example: "table2")
	 * @param String joinCondition (Example: "table1.id=table2.id")
	 * @return a List containing related Objects
	 * @throws Exception
	 */
	public List<Object> listJoin(String whereClause, String joinType, String joinTable, String joinCondition) throws Exception
	{
		try
		{
			whereClause = excludeSystemObjects(whereClause);
			String query = "SELECT * FROM `"+getTableName()+"`";
			String joinClause = getJoinClause(joinType, joinTable, joinCondition);
			String where = (whereClause!=null && !whereClause.trim().equals("")) ? " WHERE "+whereClause : "";
			return executeQuery(query+joinClause+where);
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error listing Objects on table " + getTableName(), e);
			throw e;
		}
	}
	
	
	/**
	 * List the Objects from the database specified by whereClause ordered by orderClause
	 * @param String whereClause (Example: "field1='value1' AND field2='value2'")
	 * @param String orderClause (Example: "field3 DESC, field4 ASC")
	 * @return a List containing related Objects
	 * @throws Exception
	 */
	public List<Object> list(String whereClause, String orderClause) throws Exception
	{
		return listJoin(whereClause, orderClause, null, null, null);
	}

		
	/**
	 * List the Objects from the database specified by whereClause ordered by orderClause
	 * @param String whereClause (Example: "field1='value1' AND field2='value2'")
	 * @param String orderClause (Example: "field3 DESC, field4 ASC")
	 * @param String joinType (Example: "LEFT JOIN")
	 * @param String joinTable (Example: "table2")
	 * @param String joinCondition (Example: "table1.id=table2.id")
	 * @return a List containing related Objects
	 * @throws Exception
	 */
	public List<Object> listJoin(String whereClause, String orderClause, String joinType, String joinTable, String joinCondition) throws Exception
	{
		try
		{
			whereClause = excludeSystemObjects(whereClause);
			String query = "SELECT * FROM `"+getTableName()+"`";
			String joinClause = getJoinClause(joinType, joinTable, joinCondition);
			String where = (whereClause!=null && !whereClause.trim().equals("")) ? " WHERE "+whereClause : "";
			String order = (orderClause!=null && !orderClause.trim().equals("")) ? " ORDER BY "+orderClause : "";
			return executeQuery(query+joinClause+where+order);
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error listing Objects on table " + getTableName(), e);
			throw e;
		}
	}
	
	/**
	 * List the Objects from the database specified by whereClause group by groupClause ordered by orderClause
	 * @param String whereClause (Example: "field1='value1' AND field2='value2'")
	 * @param String groupClause (Example: "field3,field4")
	 * @param String orderClause (Example: "field5 DESC, field6 ASC")
	 * @return a List containing related Objects
	 * @throws Exception
	 */
	public List<Object> list(String whereClause, String groupClause, String orderClause) throws Exception
	{
		return listJoin(whereClause, groupClause, orderClause, null, null, null);
	}

	/**
	 * List the Objects from the database specified by whereClause group by groupClause ordered by orderClause
	 * @param String whereClause (Example: "field1='value1' AND field2='value2'")
	 * @param String groupClause (Example: "field3,field4")
	 * @param String orderClause (Example: "field5 DESC, field6 ASC")
	 * @param String joinType (Example: "LEFT JOIN")
	 * @param String joinTable (Example: "table2")
	 * @param String joinCondition (Example: "table1.id=table2.id")
	 * @return a List containing related Objects
	 * @throws Exception
	 */
	public List<Object> listJoin(String whereClause, String groupClause, String orderClause, String joinType, String joinTable, String joinCondition) throws Exception
	{
		try
		{
			whereClause = excludeSystemObjects(whereClause);
			String query = "SELECT * FROM `"+getTableName()+"`";
			String joinClause = getJoinClause(joinType, joinTable, joinCondition);
			String where = (whereClause!=null && !whereClause.trim().equals("")) ? " WHERE "+whereClause : "";
			String group = (groupClause!=null && !groupClause.trim().equals("")) ? " GROUP BY "+groupClause : "";
			String order = (orderClause!=null && !orderClause.trim().equals("")) ? " ORDER BY "+orderClause : "";			
			return executeQuery(query+joinClause+where+group+order);
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error listing Objects on table " + getTableName(), e);
			throw e;
		}
	}
	
	/**
	 * List the Objects from the database specified by whereClause group by groupClause ordered by orderClause
	 * @param String fieldClause (Example: "field1,field2,COUNT(*) field3")
	 * @param String whereClause (Example: "field4='value1' AND field5='value2'")
	 * @param String groupClause (Example: "field6,field7")
	 * @param String orderClause (Example: "field8 DESC, field9 ASC")
	 * @return a List containing related Objects
	 * @throws Exception
	 */
	public List<Object> list(String fieldClause, String whereClause, String groupClause, String orderClause) throws Exception
	{
		return listJoin(fieldClause, whereClause, groupClause, orderClause, null, null, null);
	}

		
	/**
	 * List the Objects from the database specified by whereClause group by groupClause ordered by orderClause
	 * @param String fieldClause (Example: "field1,field2,COUNT(*) field3")
	 * @param String whereClause (Example: "field4='value1' AND field5='value2'")
	 * @param String groupClause (Example: "field6,field7")
	 * @param String orderClause (Example: "field8 DESC, field9 ASC")
	 * @param String joinType (Example: "LEFT JOIN")
	 * @param String joinTable (Example: "table2")
	 * @param String joinCondition (Example: "table1.id=table2.id")
	 * @return a List containing related Objects
	 * @throws Exception
	 */
	public List<Object> listJoin(String fieldClause, String whereClause, String groupClause, String orderClause, String joinType, String joinTable, String joinCondition) throws Exception
	{
		try
		{			
			whereClause = excludeSystemObjects(whereClause);
			String field = (fieldClause!=null && !fieldClause.trim().equals("")) ? fieldClause : "*";
			String query = "SELECT "+field+" FROM `"+getTableName()+"`";
			String joinClause = getJoinClause(joinType, joinTable, joinCondition);
			String where = (whereClause!=null && !whereClause.trim().equals("")) ? " WHERE "+whereClause : "";
			String group = (groupClause!=null && !groupClause.trim().equals("")) ? " GROUP BY "+groupClause : "";
			String order = (orderClause!=null && !orderClause.trim().equals("")) ? " ORDER BY "+orderClause : "";			
			return executeQuery(query+joinClause+where+group+order);
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error listing Objects on table " + getTableName(), e);
			throw e;
		}
	}	
	
	/**
	 * List the Objects from the database specified by whereClause using MYSQL LIMIT sentence
	 * @param String whereClause (Example: "field1='value1' AND field2='value2'")
	 * @param int begin
	 * @param int count
	 * @return a List containing related Objects
	 * @throws Exception
	 */
	public List<Object> list(String whereClause, int begin, int count) throws Exception
	{
		return listJoin(whereClause, begin, count, null, null, null);
	}
	
	/**
	 * List the Objects from the database specified by whereClause using MYSQL LIMIT sentence
	 * @param String whereClause (Example: "field1='value1' AND field2='value2'")
	 * @param int begin
	 * @param int count
	 * @param String joinType (Example: "LEFT JOIN")
	 * @param String joinTable (Example: "table2")
	 * @param String joinCondition (Example: "table1.id=table2.id")
	 * @return a List containing related Objects
	 * @throws Exception
	 */
	public List<Object> listJoin(String whereClause, int begin, int count, String joinType, String joinTable, String joinCondition) throws Exception
	{
		try
		{
			whereClause = excludeSystemObjects(whereClause);
			String query = "SELECT * FROM `"+getTableName()+"`";
			String joinClause = getJoinClause(joinType, joinTable, joinCondition);
			String where = (whereClause!=null && !whereClause.trim().equals("")) ? " WHERE "+whereClause : "";
			String limit = " LIMIT "+begin+","+count;
			return executeQuery(query+joinClause+where+limit);
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error listing Objects on table " + getTableName(), e);
			throw e;
		}
	}

	/**
	 * List the Objects from the database specified by whereClause using MYSQL LIMIT sentence
	 * @param String whereClause (Example: "field1='value1' AND field2='value2'")
	 * @param int begin
	 * @param int count
	 * @return a List containing related Objects
	 * @throws Exception
	 */
	public List<Object> list(String whereClause, String orderClause, int begin, int count) throws Exception
	{
		return listJoin(whereClause, orderClause, begin, count, null, null, null);
	}

	/**
	 * List the Objects from the database specified by whereClause using MYSQL LIMIT sentence
	 * @param String whereClause (Example: "field1='value1' AND field2='value2'")
	 * @param int begin
	 * @param int count
	 * @param String joinType (Example: "LEFT JOIN")
	 * @param String joinTable (Example: "table2")
	 * @param String joinCondition (Example: "table1.id=table2.id")
	 * @return a List containing related Objects
	 * @throws Exception
	 */
	public List<Object> listJoin(String whereClause, String orderClause, int begin, int count, String joinType, String joinTable, String joinCondition) throws Exception
	{
		try
		{
			whereClause = excludeSystemObjects(whereClause);
			String query = "SELECT * FROM `"+getTableName()+"`";
			String joinClause = getJoinClause(joinType, joinTable, joinCondition);
			String where = (whereClause!=null && !whereClause.trim().equals("")) ? " WHERE "+whereClause : "";
			String order = (orderClause!=null && !orderClause.trim().equals("")) ? " ORDER BY "+orderClause : "";
			String limit = " LIMIT "+begin+","+count;
			return executeQuery(query+joinClause+where+order+limit);
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error listing Objects on table " + getTableName(), e);
			throw e;
		}
	}
	


	//FIXME: DOCUMENTAR ESTOS MÉTODOS
	
	public List<Object> listField(String field, String whereClause) throws Exception{
		return this.listField(field, whereClause, null, null, null, null);
	}
	
	public List<Object> listField(String field, String whereClause, String groupClause, String orderClause) throws Exception{
		return this.listField(field, whereClause, groupClause, orderClause, null, null);
	}

	public List<Object> listField(String field, String whereClause, String groupClause, String orderClause, int count) throws Exception{
		return this.listField(field, whereClause, groupClause, orderClause, null, count);
	}
	
	public List<Object> listField(String field, String whereClause, String groupClause, String orderClause, int begin, int count) throws Exception{
		return this.listField(field, whereClause, groupClause, orderClause, begin, count);
	}

	private List<Object> listField(String field, String whereClause, String groupClause, String orderClause, Integer begin, Integer count) throws Exception{
		try{
			whereClause = excludeSystemObjects(whereClause);
			
			String query = "SELECT "+field+" FROM `"+getTableName()+"`";
			String where = (whereClause!=null && !whereClause.trim().equals("")) ? " WHERE "+whereClause : "";
			String group = (groupClause!=null && !groupClause.trim().equals("")) ? " GROUP BY "+orderClause : "";
			String order = (orderClause!=null && !orderClause.trim().equals("")) ? " ORDER BY "+orderClause : "";	
			String limit = "";
			if(count!=null){
				if(begin!=null){
					limit = " LIMIT "+begin+","+count;
				}
				else{
					limit = " LIMIT "+count;
				}
			}
			
			query += where+group+order+limit;
			
			List<Object> ret = new ArrayList<Object>();
			Connection conn = null;
			Statement s = null;
			ResultSet rs = null;

			if(this.controlledConnection==null) conn = dataSource.getConnection();
			else conn = this.controlledConnection;
				
			s = conn.createStatement();
			getLogger().log(Level.FINE, query);
			s.executeQuery (query);
			rs = s.getResultSet();
			if(rs!=null){
				ret = new ArrayList<Object>();
				while(rs.next()){
					ret.add(rs.getObject(1));
				}
			}
			
			return ret;
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error listing Objects on table " + getTableName(), e);
			throw e;
		}
	}
	
	/**
	 * Search objects that match with specified keyWords 
	 * @param String keyWords (Example: red shoes)
	 * @param String orderClause
	 * @param int begin
	 * @param int count
	 * @return a List containing related Objects
	 */
	public List<Object> search(String keyWords) throws Exception
	{
		return search(keyWords, null, -1, -1);
	}
	
	/**
	 * Search objects that match with specified keyWords 
	 * @param String keyWords (Example: red shoes)
	 * @param String orderClause
	 * @param int begin
	 * @param int count
	 * @return a List containing related Objects
	 */
	public List<Object> search(String keyWords, String orderClause) throws Exception
	{
		return search(keyWords, orderClause, -1, -1);
	}
	
	/**
	 * Search objects that match with specified keyWords 
	 * @param String keyWords (Example: red shoes)
	 * @param String orderClause
	 * @param int begin
	 * @param int count
	 * @return a List containing related Objects
	 */
	public List<Object> search(String keyWords, int begin, int count) throws Exception
	{
		return search(keyWords, null, begin, count);
	}
	
	/**
	 * Search objects that match with specified keyWords 
	 * @param String keyWords (Example: red shoes)
	 * @param String orderClause
	 * @param int begin
	 * @param int count
	 * @return a List containing related Objects
	 */
	public List<Object> search(String keyWords, String orderClause, int begin, int count) throws Exception
	{
		return search(keyWords, null, orderClause, begin, count);
	}
	
	/**
	 * Search objects that match with specified keyWords and whereClause 
	 * @param String keyWords (Example: red shoes)
	 * @param String whereClause (Example: "field1='value1' AND field2='value2'"
	 * @param String orderClause
	 * @param int begin
	 * @param int count
	 * @return a List containing related Objects
	 */
	public List<Object> search(String keyWords, String whereClause, String orderClause, int begin, int count) throws Exception
	{
		try
		{
			String where1 = (whereClause==null) ? "" : whereClause;
			String where2 = getWhereClause(keyWords);
			String where = "";
			if(where2!=null && !where2.trim().equals("") && where1!=null && !where1.trim().equals(""))
				where = where1 + " AND " + where2;
			else if(where2!=null && !where2.trim().equals(""))
				where = where2;
			else if(where1!=null && !where1.trim().equals(""))
				where = where1;
			
			//where = excludeSystemObjects(where);

			if(orderClause!=null && begin>=0 && count>=0)
				return list(where, orderClause, begin, count);
			else if(orderClause==null && begin>=0 && count>=0)
				return list(where, begin, count);
			else if(orderClause!=null && (begin<0 || count<0))
				return list(where, orderClause);
			else
				return list(where);
				
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error searching Objects on table " + getTableName(), e);
			throw e;
		}
	}
	
	/**
	 * Inserts obj in the database
	 * @param Object obj
	 * @return the mysql last_insert_id 
	 * @throws Exception
	 */
	public long insert(Object obj) throws Exception
	{
		try
		{
			String query = "INSERT INTO `"+getTableName()+"` ("+getQueryFieldList()+") VALUES ("+getInsertValueList(obj)+")";
			return execute(query);
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error inserting Object on table " + getTableName(), e);
			throw e;
		}
	}
	
	
	/**
	 * Inserts List of objects in the database
	 * @param List<Object> objs to be inserted 
	 * @return the mysql last_insert_id 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public long insertBatch(List<Object> objs) throws Exception
	{
		if(objs.size()<=0)
			return -1;
		try
		{
			StringBuffer query = new StringBuffer("INSERT  INTO `"+getTableName()+"` ("+getQueryFieldList()+") VALUES ");
			
			for (Iterator iter = objs.iterator(); iter.hasNext();) {
				Object element = (Object) iter.next();
				query.append(" ("+getInsertValueList(element)+"), " );
			}
			
			return execute(query.substring(0, query.length()-2));
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error inserting List of Objects on table " + getTableName(), e);
			throw e;
		}
	}
	
	
	/**
	 * Inserts obj in the database ignoring the primary key value
	 * @param Object obj
	 * @return the mysql last_insert_id 
	 * @throws Exception
	 */
	public long insertAutoIncrement(Object obj) throws Exception
	{
		try
		{
			String query = "INSERT INTO `"+getTableName()+"` ("+getQueryFieldList(false)+") VALUES ("+getInsertValueList(obj, false)+")";
			return execute(query);
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error inserting Object on table " + getTableName(), e);
			throw e;
		}
	}
	
	/**
	 * Updates the attributes in keyPairs in the database specified by whereClause. 
	 * @param Map keyPairs
	 * @param String whereClause
	 * @return the number of rows affected 
	 * @throws Exception
	 */
	public int update(Map<String,Object> keyPairs, String whereClause) throws Exception
	{
		try
		{		
			String where = (whereClause!=null && !whereClause.trim().equals("")) ? " WHERE "+whereClause : "";
			String query = "UPDATE `"+getTableName()+"` SET "+getUpdateFieldList(keyPairs);
			return execute(query+where);
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error updating Object on table " + getTableName(), e);
			throw e;
		}
	}
	/**
	 * Updates the attributes in keyPairs in the database specified by id. 
	 * @param Map keyPairs
	 * @param Object id
	 * @return the number of rows affected 
	 * @throws Exception
	 */
	public int update(Map<String,Object> keyPairs, Object id) throws Exception
	{
		try
		{		
			String where = (id!=null) ? " WHERE "+getPrimaryKey(id) : "";
			String query = "UPDATE `"+getTableName()+"` SET "+getUpdateFieldList(keyPairs);
			return execute(query+where, getObjectsToSave(keyPairs));
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error updating Object on table " + getTableName(), e);
			throw e;
		}
	}
	
	/**
	 * Updates the attributes in keyPairs in List of objects in the database
	 * @param List keyPairs
	 * @param List ids
	 * @return the number of rows affected 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public long updateBatch(List<Map<String,Object>> keyPairs, List<Object> ids) throws Exception
	{
		int keyPairsCount = keyPairs.size();
		int idsCount = ids.size();
		
		if((keyPairsCount<=0) || (keyPairsCount!=idsCount))
			return -1;
		
		try
		{
			String querys[] = new String[keyPairsCount];
			
			int i=0;
			for (Iterator iter = ids.iterator(); iter.hasNext();) {
				Object id = (Object) iter.next();
				querys[i] = "UPDATE `" + getTableName() + "` SET " + getUpdateFieldList(keyPairs.get(i)) + " WHERE " + getPrimaryKey(id);
				i++;
			}
			
			return executeBatch(querys);
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error inserting List of Objects on table " + getTableName(), e);
			throw e;
		}
	}
	
	/**
	 * Updates ALL attributes of obj in the database specified by whereClause. 
	 * @param Object obj
	 * @param String whereClause
	 * @return the number of rows affected
	 * @throws Exception
	 */
	public int update(Object obj, String whereClause) throws Exception
	{
		try
		{	
			String where = (whereClause!=null && !whereClause.trim().equals("")) ? " WHERE "+whereClause : "";
			String query = "UPDATE `"+getTableName()+"` SET "+getUpdateFieldList(obj);
			return execute(query+where);
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error updating Object on table " + getTableName(), e);
			throw e;
		}
	}
	
	/**
	 * Updates ALL attributes of obj in the database specified by id. 
	 * @param Object obj
	 * @param Object id
	 * @return the number of rows affected
	 * @throws Exception
	 */
	public int update(Object obj, Object id) throws Exception
	{
		try
		{		
			String where = (id!=null) ? " WHERE "+getPrimaryKey(id) : "";
			String query = "UPDATE `"+getTableName()+"` SET "+getUpdateFieldList(obj);
			return execute(query+where);
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error updating Object on table " + getTableName(), e);
			throw e;
		}
	}

	/**
	 * Updates ALL attributes of obj in the database specified by id, optionally doing an insert if it doesn't exist.
	 * @param Object obj
	 * @param Object id
	 * @param boolean forceInsert
	 * @return the number of rows affected
	 * @throws Exception
	 */
	public int update(Object obj, Object id, boolean forceInsert) throws Exception
	{
	 int rows = update(obj, id);
	 
	 if(rows == 0 && forceInsert)
	  {
		 rows = (int)insert(obj);
	  }
	 
	 return rows;
	}
	
	/**
	 * Deletes the row that matches with id
	 * @param Object id
	 * @return the number of rows affected
	 * @throws Exception
	 */
	public int delete(Object id) throws Exception
	{
		try
		{
			String where = (id!=null) ? " WHERE "+getPrimaryKey(id) : "";
			String query = "DELETE FROM `"+getTableName()+"`";
			return execute(query+where);
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error deleting Object on table " + getTableName(), e);
			throw e;
		}
	}
	
	/**
	 * Deletes the rows that match with specified whereClause
	 * @param String whereClause
	 * @return the number of rows affected
	 * @throws Exception
	 */
	public int delete(String whereClause) throws Exception
	{
		try
		{			
			String where = (whereClause!=null && !whereClause.trim().equals("")) ? " WHERE "+whereClause : "";
			String query = "DELETE FROM `"+getTableName()+"`";
			return execute(query+where);
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error deleting Object on table " + getTableName(), e);
			throw e;
		}
	}
	
	//PROTECTED METHODS
	/**
	 * Executes the given SQL statement, which returns a List. 
	 * @param String query
	 * @return the List of Objects
	 * @throws Exception
	 */
	protected List<Object> executeQuery(String query) throws Exception
	{
		List<Object> ret = null;
		DBObject dbObj = null;
		Connection conn = null;
		Statement s = null;
		ResultSet rs = null;
		try
		{
			if(this.controlledConnection==null) conn = dataSource.getConnection();
			else conn = this.controlledConnection;
			
			s = conn.createStatement();
			getLogger().log(Level.FINE, query);
			s.executeQuery (query);
			rs = s.getResultSet();
			if(rs!=null)
			{
				ret = new ArrayList<Object>();
				while(rs.next())
				{
					Map<String,Object> result = new HashMap<String,Object>();
					int attributesSize = attributes.size();
					for(int i=0;i<attributesSize;i++)
					{
						AttributeInfo attrInfo = attributes.get(i);
						if(attrInfo.getType()!=null)
						{
							String attrType = attrInfo.getType().toLowerCase();
							if(attrType.contains("blob"))
								result.put(attributes.get(i).getName(), rs.getBlob(attributes.get(i).getName()));
							else if(attrType.contains("char(1)"))
								result.put(attrInfo.getName(), (rs.getString(attrInfo.getName()) != null ? rs.getString(attrInfo.getName()).charAt(0) : null));
							else if(attrType.contains("varchar") || attrType.contains("text") || attrType.contains("char"))
								result.put(attrInfo.getName(), rs.getString(attrInfo.getName()));
							else if(attrType.equals("tinyint(1)"))
								result.put(attrInfo.getName(), rs.getLong(attrInfo.getName())==1);
							else if(attrType.contains("bigint"))
								result.put(attrInfo.getName(), rs.getLong(attrInfo.getName()));
							else if(attrType.contains("int"))
								result.put(attrInfo.getName(), rs.getInt(attrInfo.getName()));
							else if(attrType.contains("decimal"))
								result.put(attrInfo.getName(), rs.getBigDecimal(attrInfo.getName()));
							else if(attrType.contains("float"))
								result.put(attrInfo.getName(), rs.getFloat(attrInfo.getName()));
							else if(attrType.contains("double"))
								result.put(attrInfo.getName(), rs.getDouble(attrInfo.getName()));

							else if(attrType.contains("date") || attrType.contains("time")){
								try{
									String timestampStr = rs.getTimestamp(attrInfo.getName()).toString();
									
									if(attrType.equals("date"))
									 {
									  timestampStr = timestampStr.substring(0, timestampStr.indexOf(" "));
									 }
									else if (attrType.equals("time"))
									 {
									  timestampStr = timestampStr.substring(timestampStr.indexOf(" ")+1);
									 }
									else
									 {
									  // Do nothing
									 }
									
									if(timestampStr.contains(".")){
										timestampStr = timestampStr.substring(0, timestampStr.indexOf("."));
									}

									
									result.put(attrInfo.getName(), timestampStr);
								}catch(Exception e){
									result.put(attrInfo.getName(), InteraxDate.getNullTimestamp());
								}
							}
						}
					}
					dbObj = new DBObject(attributes,result);
					ret.add(dbObj.convertToObj(getObject()));
				}
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			if(this.controlledConnection==null)	close(conn,s,rs);
		}
		return ret;
	}
	
	/**
	 * Executes the given SQL statement, which returns a long. 
	 * @param String query
	 * @return database count result
	 * @throws Exception
	 */
	protected long executeScalar(String query) throws Exception
	{
		long ret = 0;
		Connection conn = null;
		Statement s = null;
		ResultSet rs = null;
		try
		{
			if(this.controlledConnection==null) conn = dataSource.getConnection();
			else conn = this.controlledConnection;
			
			s = conn.createStatement();
			getLogger().log(Level.FINE, query);
			s.executeQuery (query);
			rs = s.getResultSet();
			if(rs!=null)
			{
				while(rs.next())
				{
					ret = rs.getLong(1);
				}
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			if(this.controlledConnection==null)	close(conn,s,rs);
		}
		return ret;
	}
	
	
	/**
	 * Executes the given SQL statement, which returns a Hashtable<String, Double>. 
	 * @param String query
	 * @return Hashtable<String, Double> with  database count result
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected HashMap<String, Double> executeScalarSum(List<String> fields, String query) throws Exception
	{
		HashMap<String, Double> ret = null;
		Connection conn = null;
		Statement s = null;
		ResultSet rs = null;
		try
		{
			if(this.controlledConnection==null) conn = dataSource.getConnection();
			else conn = this.controlledConnection;
			
			s = conn.createStatement();
			getLogger().log(Level.FINE, query);
			s.executeQuery (query);
			rs = s.getResultSet();
			if(rs!=null)
			{
				ret = new HashMap<String, Double>();
				while(rs.next())
				{
					for (Iterator iter = fields.iterator(); iter.hasNext();) {
						String field = (String) iter.next();
						ret.put(field, rs.getDouble(field));
					}
				}
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			if(this.controlledConnection==null)	close(conn,s,rs);
		}
		return ret;
	}
	
	
	
	/**
	 * Executes the given SQL statement, which may be an INSERT, UPDATE, or DELETE statement 
	 * @param String query
	 * @return the number of rows affected in the query
	 * @throws Exception
	 *//*    //---- OLD EXECUTE SINCE INTERAXSERIALIZER *************
	protected int execute(String query) throws Exception
	{
		
		int ret = 0;
		Connection conn = null;
		Statement s = null;
		ResultSet rs = null;
		try
		{
			if(this.controlledConnection==null) conn = dataSource.getConnection();
			else conn = this.controlledConnection;
			
			conn.setAutoCommit(false);
			s = conn.createStatement();
			getLogger().log(Level.FINE, query);
			ret = s.executeUpdate(query);
			
			if(query.trim().toLowerCase().startsWith("insert"))
			{
				PreparedStatement s1 = conn.prepareStatement("SELECT LAST_INSERT_ID()");
				s1.executeQuery();
				rs = s1.getResultSet();
				while (rs!=null && rs.next())
					ret = rs.getInt(1);
			}
			if(this.controlledConnection==null) 
				conn.commit();
		}
		catch(Exception e)
		{
			if(this.controlledConnection==null) 
				conn.rollback();
			throw e;
		}
		finally
		{
			if(this.controlledConnection==null) 
				close(conn,s,rs);
		}
		return ret;
	}*/
	
	/**
	 * Executes the given SQL statement, which may be an INSERT, UPDATE, or DELETE statement 
	 * @param String query
	 * @return the number of rows affected in the query
	 * @throws Exception
	 */
	protected int execute(String query) throws Exception
	{
		return execute(query, null);
	}
	
	/**
	 * Executes the given SQL statement, which may be an INSERT, UPDATE, or DELETE statement 
	 * @param String query
	 * @return the number of rows affected in the query
	 * @throws Exception
	 */
	protected int execute(String query, List<Object> obj) throws Exception
	{
		
		int ret = 0;
		Connection conn = null;
		PreparedStatement s = null;
		ResultSet rs = null;
		try
		{
			if(this.controlledConnection==null) conn = dataSource.getConnection();
			else conn = this.controlledConnection;
			
			conn.setAutoCommit(false);
			s = conn.prepareStatement(query);
			getLogger().log(Level.FINE, query);

			if(obj != null){
				for(Object o : obj)
					if(o instanceof InteraxSerializedObject){
						s.setObject(1, ((InteraxSerializedObject) o).getData());
					}else{
    				s.setObject(1, o);
					}
			}
			ret = s.executeUpdate();
			
			if(query.trim().toLowerCase().startsWith("insert"))
			{
				PreparedStatement s1 = conn.prepareStatement("SELECT LAST_INSERT_ID()");
				s1.executeQuery();
				rs = s1.getResultSet();
				while (rs!=null && rs.next())
					ret = rs.getInt(1);
			}
			if(this.controlledConnection==null) conn.commit();
		}
		catch(Exception e)
		{
			if(this.controlledConnection==null) conn.rollback();
			throw e;
		}
		finally
		{
			if(this.controlledConnection==null) close(conn,s,rs);
		}
		return ret;
	}
	
	
	/**
	 * Executes the given SQL statements, which may be an INSERT, UPDATE, or DELETE statements
	 * @param String querys
	 * @return the number of rows affected in the querys
	 * @throws Exception
	 */
	protected int executeBatch(String querys[]) throws Exception
	{
		
		int ret = 0;
		Connection conn = null;
		Statement s = null;
		ResultSet rs = null;
		try
		{
			if(this.controlledConnection==null) conn = dataSource.getConnection();
			else conn = this.controlledConnection;
			
			conn.setAutoCommit(false);
			s = conn.createStatement();

			int queryCount = querys.length;
			for(int i=0; i<queryCount; i++){
				getLogger().log(Level.FINE, querys[i]);
				s.addBatch(querys[i]);
			}

			int rets[] = s.executeBatch();
			for(int i=0; i<queryCount; i++){
				ret += rets[i];
			}

			if(this.controlledConnection==null) 
				conn.commit();
		}
		catch(Exception e)
		{
			if(this.controlledConnection==null) 
				conn.rollback();
			throw e;
		}
		finally
		{
			if(this.controlledConnection==null) 
				close(conn,s,rs);
		}
		return ret;
	}
	
	
	/**
	 * Parse the database table and get the fields list
	 * @return the list of table field names separated with comma (Example: field1,field2,...)
	 */
	protected String getQueryFieldList()
	{
	 return getQueryFieldList(true);
	}


	/**
	 * Parse the database table and get the fields list
	 * @return the list of table field names separated with comma (Example: field1,field2,...)
	 */
	protected String getQueryFieldList(boolean includePK)
	{
		String ret = "";
		String separator = "";
		for (int i=0;i<attributes.size();i++) 
		{
			if(includePK || !attributes.get(i).getKey().toLowerCase().contains("pri"))
			{
				ret += separator +"`"+ attributes.get(i).getName()+"`";
				separator = ",";
			}
		}
		return ret;
	}
	
	/**
	 * Parse the database table and the parameter obj to create the SQL statement for update
	 * @return the list of table field names and values (Example: field1='value1',field2='value2',...)
	 */
	@SuppressWarnings("unchecked")
	protected String getUpdateFieldList(Object obj) throws Exception
	{
		/* ready with InteraxDate */
		DBObject dbObj = new DBObject(attributes,obj);
		Map m = dbObj.getValues();
		String ret = "";
		String separator = "";
		for (int i=0;i<attributes.size();i++) 
		{
			AttributeInfo attrInfo = attributes.get(i);
            //Object value = (Object)m.get(attrInfo.getName());
            if(attrInfo.getType()!=null)
            {
            	String attrType = attrInfo.getType().toLowerCase();
				if(attrType.contains("date") || attrType.contains("time"))
            	{
					String pattern;
            		Format formatter;
            		
            	    if(attrType.equals("date"))
            	    	pattern = "yyyy-MM-dd";
            	    else if(attrType.equals("datetime") || attrType.equals("timestamp") )
            	    	pattern = "yyyy-MM-dd HH:mm:ss";
            	    else if(attrType.equals("time"))
            	    	pattern = "HH:mm:ss";
            	    else
            	    	pattern = "yyyyMMddHHmmss";
            	    
            	    Object dateObj = m.get(attrInfo.getName());
            	    if (dateObj instanceof InteraxDate) {
            	    	
            	    	InteraxDate value = (InteraxDate)m.get(attrInfo.getName());
            	    	if(value.isNull())
            	    		ret += separator + "`"+ attributes.get(i).getName() + "`"+ "='0000-00-00 00:00:00'";
            	    	else
            	    		ret += separator + "`"+ attributes.get(i).getName() + "`"+ "='"+ value.toMySqlString(pattern)+"'";
            	    	
					}else{
						
						formatter = new SimpleDateFormat(pattern);
						
						Date value = null; 
						if(dateObj instanceof Calendar){
							Calendar calendarValue = (Calendar) dateObj;
							if(calendarValue!=null)
								value = new Date(calendarValue.getTimeInMillis() - TimeZone.getDefault().getRawOffset());
						}
						else
							value = (Date) dateObj;
						
						try {
							if(value!=null)
								ret += separator + "`" + attributes.get(i).getName() + "`" + "='"+formatter.format(value)+"'";
							else
							{
	                	    	String defaultValue = attrInfo.getDefaultValue();
	                	    	if(defaultValue!=null && !defaultValue.equalsIgnoreCase("NULL"))
	                	    		ret += separator + "`" +attributes.get(i).getName() + "`" + "=" + "'"+defaultValue+"'";
	                	    	else
	                	    		ret += separator + "`" +attributes.get(i).getName() + "`" + "=NULL";
							}
								
								
						} catch(Exception e) {}
					}
            	}
            	else if(attrType.equals("tinyint(1)"))
         	    {
                	boolean booleanValue = (Boolean) m.get(attrInfo.getName()); 
                	if(booleanValue)
                		ret += separator + "`"+ attributes.get(i).getName() + "`"+ "='1'";
                	else
                		ret += separator + "`"+ attributes.get(i).getName() + "`"+ "='0'";
         	    }
				else if(attrType.contains("char") || attrType.contains("text"))
					if(m.get(attrInfo.getName())!=null)
            			ret += separator + "`"+ attributes.get(i).getName()+ "`"+"='"+m.get(attrInfo.getName()).toString().replace("'","\\'")+"'";
            		else
            			ret += separator + "`"+ attributes.get(i).getName()+ "`"+"=NULL";
				else if(attrType.contains("int"))
         	    {
					if(m.get(attrInfo.getName())!=null)
            			ret += separator + "`"+ attributes.get(i).getName()+ "`"+"='"+Long.parseLong("" + m.get(attrInfo.getName()))+"'";
            		else if(attrInfo.isAllowNull())
            			ret += separator + "`"+ attributes.get(i).getName()+ "`"+"=NULL";
            		else
            			ret += separator + "`"+ attributes.get(i).getName()+ "`"+"="+attrInfo.getDefaultValue();
         	    }
            	else
            		if(m.get(attrInfo.getName())!=null)
            			ret += separator + "`"+ attributes.get(i).getName()+ "`"+"='"+m.get(attrInfo.getName())+"'";
            		else if(attrInfo.isAllowNull())
            			ret += separator + "`"+ attributes.get(i).getName()+ "`"+"=NULL";
            		else
            			ret += separator + "`"+ attributes.get(i).getName()+ "`"+"='"+attrInfo.getDefaultValue()+"'";				
        	    separator = ",";
            }
			separator = ",";
		}
		return ret;
	}
	
	/**
	 * Parse the database table and the parameter keyPairs to create the SQL statement for update
	 * @return the list of table field names and values (Example: field1='value1',field2='value2',...)
	 */
	protected String getUpdateFieldList(Map<String,Object> keyPairs) throws Exception
	{
		/* ready with InteraxDate */
		String ret = "";
		String separator = "";
		for (int i=0;i<attributes.size();i++) 
		{
			AttributeInfo attrInfo = attributes.get(i);
			if(keyPairs.get(attrInfo.getName())!=null)
			{
				Object obj = keyPairs.get(attrInfo.getName());
				if(attrInfo.getType()!=null)
	            {
					String attrType = attrInfo.getType().toLowerCase();
					if(attrType.contains("date") || attrType.contains("time"))
	            	{
						String pattern;
	            		Format formatter;
	            		
	            	    if(attrType.equals("date"))
	            	    	pattern = "yyyy-MM-dd";
	            	    else if(attrType.equals("datetime") || attrType.equals("timestamp") )
	            	    	pattern = "yyyy-MM-dd HH:mm:ss";
	            	    else if(attrType.equals("time"))
	            	    	pattern = "HH:mm:ss";
	            	    else
	            	    	pattern = "yyyyMMddHHmmss";

	            	    try{
	            	    	if (obj instanceof InteraxDate) {
	            	    		
	            	    		InteraxDate temp = (InteraxDate)obj;
	                	    	if(temp.isNull())
	                	    		ret += separator + attributes.get(i).getName() + "='0000-00-00 00:00:00'";
	                	    	else
	                	    		ret += separator + attributes.get(i).getName() + "='"+ temp.toMySqlString(pattern)+"'";
	            	    		
	            	    	}else{
	            	    		
	            	    		formatter = new SimpleDateFormat(pattern);
	            	    		
	    						Date value = null; 
	    						if(obj instanceof Calendar){
	    							Calendar calendarValue = (Calendar) obj;
	    							if(calendarValue!=null)
	    								value = new Date(calendarValue.getTimeInMillis() - TimeZone.getDefault().getRawOffset());
	    						}
	    						else
	    							value = (Date) obj;
	    						
	            	    		if(value!=null)
	            	    			ret += separator + attributes.get(i).getName() + "='"+formatter.format(value)+"'";
	            	    		else
	            	    			ret += separator + attributes.get(i).getName() + "=NULL";
	            	    		
	            	    	}
	            	    } catch(Exception e) 
	            	    {
	            	    	if("CURRENT_TIMESTAMP".equals(attrInfo.getDefaultValue()))
	            	    		ret += separator + "NULL";
	            	    	else
	            	    		ret += separator +"'"+attrInfo.getDefaultValue()+"'";	
	            	    }
	            	   
	            	}
					else if (attrType.equals("blob") &&  ! (obj instanceof String)) {
						ret += separator + attributes.get(i).getName() + "=?";
					}
					else if(attrType.contains("char") || attrType.contains("text"))
						if(obj!=null)
	            			ret += separator + attributes.get(i).getName()+"='"+obj.toString().replace("'","\\'")+"'";
	            		else if(attrInfo.isAllowNull())
	            			ret += separator + attributes.get(i).getName()+"=NULL";
	            		else
	            			ret += separator + attributes.get(i).getName()+"='"+attrInfo.getDefaultValue()+"'";
					else if(attrType.equals("tinyint(1)"))
	            	{
	            		boolean booleanValue = (Boolean) obj; 
	            		if(booleanValue)
	                		ret += separator + attributes.get(i).getName() + "='1'";
	                	else
	                		ret += separator + attributes.get(i).getName() + "='0'";
	            	}
					else if(attrType.contains("int"))
	         	    {						
						if(obj!=null)
	            			ret += separator + attributes.get(i).getName()+"='"+Long.parseLong("" + obj)+"'";
	            		else if(attrInfo.isAllowNull())
	            			ret += separator + attributes.get(i).getName()+"=NULL";
	            		else
	            			ret += separator + attributes.get(i).getName()+"="+attrInfo.getDefaultValue();
	         	    }
	            	else
	            		if(obj!=null)
	            			ret += separator + attributes.get(i).getName()+"='"+obj+"'";
	            		else if(attrInfo.isAllowNull())
	            			ret += separator + attributes.get(i).getName()+"=NULL";
	            		else
	            			ret += separator + attributes.get(i).getName()+"='"+attrInfo.getDefaultValue()+"'";
	        	    separator = ",";
	            }
				separator = ",";
			}
		}
		return ret;
	}
	
	/**
	 * Parse the database table and the parameter obj to create the SQL statement for insertt
	 * @return the list of values to be inserted (Example: 'value1','value2',...)
	 */
	protected String getInsertValueList(Object obj) throws Exception
	{
		return getInsertValueList(obj, true);
	}

	
	/**
	 * Parse the database table and the parameter obj to create the SQL statement for insertt
	 * @return the list of values to be inserted (Example: 'value1','value2',...)
	 */
	@SuppressWarnings("unchecked")
	protected String getInsertValueList(Object obj, boolean includePK) throws Exception
	{
		/* ready with InteraxDate */
		
		DBObject dbObj = new DBObject(attributes,obj);
		Map m = dbObj.getValues();
		String ret = "";
		String separator = "";
		for (int i=0;i<attributes.size();i++) 
		{
			AttributeInfo attrInfo = attributes.get(i);
            //Object value = (Object)m.get(attrInfo.getName());

            if(attrInfo.getType()!=null && (includePK || !attributes.get(i).getKey().toLowerCase().contains("pri")))
            {
            	String attrType = attrInfo.getType().toLowerCase();
				if(attrType.contains("date") || attrType.contains("time"))
            	{
					String pattern;
            		Format formatter;
            		
            	    if(attrType.equals("date"))
            	    	pattern = "yyyy-MM-dd";
            	    else if(attrType.equals("datetime") || attrType.equals("timestamp") )
            	    	pattern = "yyyy-MM-dd HH:mm:ss";
            	    else if(attrType.equals("time"))
            	    	pattern = "HH:mm:ss";
            	    else
            	    	pattern = "yyyyMMddHHmmss";
            	    
            	    Object dateObj = m.get(attrInfo.getName());
            	    if (dateObj instanceof InteraxDate) {
            	    	InteraxDate value = (InteraxDate)m.get(attrInfo.getName());
            	    	if(value==null || value.isNull())
            	    		ret += separator + "'0000-00-00 00:00:00'";
            	    	else
            	    		ret += separator + "'"+ value.toMySqlString(pattern)+"'";
            	    	
					}else{
						formatter = new SimpleDateFormat(pattern);
						
						Date value = null; 
						if(dateObj instanceof Calendar){
							Calendar calendarValue = (Calendar) dateObj;
							if(calendarValue!=null)
								value = new Date(calendarValue.getTimeInMillis() - TimeZone.getDefault().getRawOffset());
						}
						else
							value = (Date) dateObj;
						
	            	    try {
	            	    	String formatedValue = formatter.format(value);
	            	    	if(formatedValue!=null)
	            	    		ret += separator + "'"+formatedValue+"'";
	            	    	else
	            	    		ret += separator + "NULL";
	            	    } catch(Exception e) {
	            	    	if("CURRENT_TIMESTAMP".equals(attrInfo.getDefaultValue()))
	                			ret += separator + "'0000-00-00 00:00:00'";
	                		else
	                		 {
	                	    	String defaultValue = attrInfo.getDefaultValue();
	                	    	if(defaultValue!=null && !defaultValue.equalsIgnoreCase("NULL"))
	                	    		ret += separator + "'"+defaultValue+"'";
	                	    	else
	                	    		ret += separator + "NULL";
	                		 }
	            	    }
					}
            	}
				else if(attrType.contains("char") || attrType.contains("text"))
					if(m.get(attrInfo.getName())!=null)
            			ret += separator + "'"+m.get(attrInfo.getName()).toString().replace("'","\\'")+"'";
            		else if(attrInfo.isAllowNull())
            			ret += separator + "NULL";
            		else
            			ret += separator + "'"+attrInfo.getDefaultValue()+"'";
            	else if(attrType.equals("tinyint(1)"))
            	   {
            		boolean booleanValue = (Boolean) m.get(attrInfo.getName()); 
            		if(booleanValue)
            			ret += separator + "'1'";
            		else
            			ret += separator + "'0'";
            	   }
            	else if(attrType.contains("int"))
            	{
            		Object objValue = m.get(attrInfo.getName()); 
            		if(objValue!=null){
            			Long longValue = Long.parseLong("" + objValue);
            			if(attrType.contains("unsigned") && longValue<0 && allowInsertUnsignedNegatives){
            				String defaultValue = attrInfo.getDefaultValue();
            				if(defaultValue == null || defaultValue.equals(""))
            					defaultValue = "0";
            				ret += separator + defaultValue;
            			}
            			else{
            				ret += separator + ""+longValue+"";
            			}
            		}
            		else if(attrInfo.isAllowNull())
            			ret += separator + "NULL";
            		else
            			ret += separator + attrInfo.getDefaultValue();
            	}
            	else
            		if(m.get(attrInfo.getName())!=null)
            			ret += separator + "'"+m.get(attrInfo.getName())+"'";
            		else if(attrInfo.isAllowNull())
            			ret += separator + "NULL";
            		else
            			ret += separator + "'"+attrInfo.getDefaultValue()+"'";
        	    separator = ",";
            }
        }
		return ret;
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
	
	public String getWhereClauseFromKeyWords(String keyWords) throws Exception
	{
		try
		{
			return getWhereClause(keyWords,null);
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error creating keyWords Where Clause " + getTableName(), e);
		 	throw e;
		}
		
	}
	
	protected String getWhereClause(String keyWords)
	{
		return getWhereClause(keyWords,null);
	}

	protected String getWhereClause(String keyWords, String alias)
	{
		if(alias != null && alias.length()>0){
			alias+=".";
		}
		else{
			alias="";
		}
		if(keyWords==null || "".equals(keyWords))
			return "";
		
		String[] words = keyWords.split(" ");
		String whereClause = " ";
		String and = " ";
		String or = " ";
		
		for(int j=0;words!=null&&j<words.length;j++){
			String subGroup = " ";
			or = " ";
			for(int i=0;i<attributes.size();i++)
			{
				AttributeInfo attrInfo = attributes.get(i);
	            //Object value = (Object)m.get(attrInfo.getName());
	            if(attrInfo.getType()!=null)
	            {
	            	String attrType = attrInfo.getType().toLowerCase();
					if(attrType.contains("char") || attrType.contains("text"))
	            	{
						subGroup += or+alias+attributes.get(i).getName()+" LIKE '%"+words[j]+"%'";
						or = " OR ";
	            	}
					else if(attrType.contains("int"))
					{
						try {
							subGroup += or+alias+attributes.get(i).getName()+"='"+Long.parseLong(words[j])+"'";
							or = " OR ";
						}
						catch(Exception e){	}
					}
					else if(attrType.contains("float"))
					{
						try {
							subGroup += or+alias+attributes.get(i).getName()+"='"+Float.parseFloat(words[j])+"'";
							or = " OR ";
						}
						catch(Exception e){	}
						
					}
					else if(attrType.contains("double"))
					{
						try {
							subGroup += or+alias+attributes.get(i).getName()+"='"+Double.parseDouble(words[j])+"'";
							or = " OR ";
						}
						catch(Exception e){	}
					}
	            }
			}
			if(subGroup!=null)
			{
				whereClause += and+" ("+subGroup+") ";
				and = " AND ";
			}
		}
		return whereClause;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> getObjectsToSave(Object obj) throws Exception{
		List<Object> result = new ArrayList<Object>();
		
		Map m;
		if(obj instanceof Map){
			m = (Map<String, Object>)obj;
		}
		else{
		
			DBObject dbObj = new DBObject(attributes,obj);
			m = dbObj.getValues();
		}
		
		for (int i=0;i<attributes.size();i++) 
		{
			AttributeInfo attrInfo = attributes.get(i);
			if (attrInfo.getType().equals("blob") &&  m.containsKey(attrInfo.getName()) && !(m.get(attrInfo.getName()) instanceof String)) {
				result.add(m.get(attrInfo.getName()));
			}
		}
		
		return result;
	}
	
	
	
	/**
	 * Method to exclude objects with '0' id (System use objects)
	 * @param whereClause
	 * @return
	 */
	protected String excludeSystemObjects(String whereClause){
// TODO MEJORAR ESTA FUNCIÓN PARA HACERLA MULTI TIPO Y MULTI NOMBRE RESPECTO AL PK
		if(haveAttribute("id")){
			if(whereClause==null || whereClause.length()==0)
				whereClause = " "+getTableName()+".id<>0";
			else{
				
				Pattern pattern = Pattern.compile("(?i)GRoUP bY");
				Matcher matcher = pattern.matcher(whereClause);
				if (matcher.find()) {
					
					int index = whereClause.indexOf(matcher.group(0));
					StringBuffer temp = new StringBuffer();
					
					temp.append(whereClause.substring(0, index));
					temp.append(" AND "+getTableName()+".id<>0 ");
					temp.append(whereClause.substring(index));
					
					whereClause = temp.toString();
				}else{
					whereClause += " AND "+getTableName()+".id<>0";
				}
			}
		}
		
		return whereClause;
	}
	
	private boolean haveAttribute(String attributeName){
		
		for(int i=0;i<attributes.size();i++){
			if(attributes.get(i).getName().equals(attributeName)) return true;
		}
		
		return false;
	}

	
	/*public static void main(String[] args) {
		String whereClause = 
			"SELECT * FROM `newsletter_link` " ;
		
		String whereClause1 = 
			"SELECT * FROM `newsletter_link` " +
			"GrOUp By url ";
		
		String whereClause2 = 
			"SELECT * FROM `newsletter_link` " +
			"WHERE  contentType = 'WEB_NEWSLETTER' GrOUp By url ";
		
		String whereClause3 =
			"SELECT * FROM `newsletter_link` " +
			"WHERE  groUPID = 3 GrOUp By url ";
			
		System.out.println(DataManager.excludeSystemObjects(whereClause));
		System.out.println(DataManager.excludeSystemObjects(whereClause1));
		System.out.println(DataManager.excludeSystemObjects(whereClause2));
		System.out.println(DataManager.excludeSystemObjects(whereClause3));
	}*/
}
