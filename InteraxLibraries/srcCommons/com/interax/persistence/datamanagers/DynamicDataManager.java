package com.interax.persistence.datamanagers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.Vector;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import com.interax.date.InteraxDate;
import com.interax.persistence.common.AttributeInfo;
import com.interax.persistence.common.DBObject;
import com.interax.persistence.common.DynamicAttribute;
import com.interax.persistence.common.DynamicAttributeManager;
import com.interax.persistence.common.DynamicObject;
import com.interax.persistence.utils.ServiceLocator;


/**
 * Map a database table and implements CRUD operations
 * @author nelio
 */
public abstract class DynamicDataManager extends GenericDataManager
{
	
	/**
	 * Data source name.
	 */
	protected String dataSourceName;
	
	/**
	 * A factory for connections to the physical data source that this DataSource object represents.
	 */
	protected DataSource dataSource;
	
	/**
	 * Connection given to use by this manager (controlledMode)
	 */
	protected Connection controlledConnection;
	
	/**
	 * Stores the MySQL DESCRIBE output
	 */
	protected List<AttributeInfo> attributes;

	/**
	 * Stores dynamic attributes by field name
	 */	
	protected Map<String,DynamicAttribute> dynamicAttributes;
	
	/**
	 * Cache for obtain names giving displayNames
	 */	
	protected Map<String, String> displayNamesToNames;
	
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
	
	public String getTableName(String tableAlias){
		if(tableAlias!=null && tableAlias.trim().length()>0){
			return "`" + this.getTableName() + "` " + tableAlias;
		}
		else{
			return this.getTableName();
		}
	}

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
	public abstract DynamicObject getObject();
	
	
	/**
	 * Must provide the prefix field name 
	 * @return the prefix field name
	 */
	public abstract String getDynamicPrefixFieldName();
	
	/**
	 * Create an instance that connects to the specified dataSourceName
	 * @param String dataSourceName
	 */
	@SuppressWarnings("unchecked")
	public DynamicDataManager(String dataSourceName)
	{
		try
		{
			setLogger(getClassLogger(this.getClass())); //LoggerFactory.getLogger(this.getClass());
			this.dataSourceName = dataSourceName;
			attributes = new ArrayList<AttributeInfo>();
			dynamicAttributes = new HashMap<String,DynamicAttribute>();
			displayNamesToNames = new HashMap<String, String>();
			dataSource = ServiceLocator.getInstance().getDataSource(dataSourceName);
			if(dataSource!=null)
			{
				Connection conn = null;
				Statement s = null;
				ResultSet rs = null;
				try
				{
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
				}
				catch(Exception e)
				{
					throw e;
				}
				finally
				{
					close(conn,s,rs);
				}
				DynamicAttributeManager dynAttrMgr = new DynamicAttributeManager(dataSourceName);
				List<DynamicAttribute> dynAttrs = (List)dynAttrMgr.list("tableName='"+getTableName()+"'");
				if(dynAttrs!=null)
				{
					for(int i=0;i<dynAttrs.size();i++)
					{
						DynamicAttribute dynAttr = dynAttrs.get(i);
						dynamicAttributes.put(dynAttr.getName(), dynAttr);
						displayNamesToNames.put(dynAttr.getDisplayName(), dynAttr.getName());
					}
				}
			}
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error creating DynamicDataManager", e);
		}
	}
	
	/**
	 * Create an instance that connects to a DB using the given connection (controlledMode: commits/rollbacks must be controlled by user)
	 * @param Connection controlledConnection
	 */
	@SuppressWarnings("unchecked")
	public DynamicDataManager(Connection controlledConnection)
	{
		attributes = new ArrayList<AttributeInfo>();
		dynamicAttributes = new HashMap<String,DynamicAttribute>();
		displayNamesToNames = new HashMap<String,String>();
		
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
		   DynamicAttributeManager dynAttrMgr = new DynamicAttributeManager(controlledConnection);
		   List<DynamicAttribute> dynAttrs = (List)dynAttrMgr.list("tableName='"+getTableName()+"'");
		   if(dynAttrs!=null)
		   {
			   for(int i=0;i<dynAttrs.size();i++)
			   {
				   DynamicAttribute dynAttr = dynAttrs.get(i);
				   dynamicAttributes.put(dynAttr.getName(), dynAttr);
				   displayNamesToNames.put(dynAttr.getDisplayName(), dynAttr.getName());
			   }
		   }
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error creating DynamicDataManager (controlledMode)", e);
		}
	}

	@SuppressWarnings("unchecked")
	public void refresh() throws Exception{
		
		if(dataSource!=null)
		{
			Connection conn = null;
			Statement s = null;
			ResultSet rs = null;
			try
			{
				conn = dataSource.getConnection();
				s = conn.createStatement();
				s.executeQuery ("DESCRIBE `"+getTableName()+"`");
				rs = s.getResultSet();
				attributes.clear();
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
			}
			catch(Exception e)
			{
				throw e;
			}
			finally
			{
				close(conn,s,rs);
			}
			DynamicAttributeManager dynAttrMgr = new DynamicAttributeManager(dataSourceName);
			List<DynamicAttribute> dynAttrs = (List)dynAttrMgr.list("tableName='"+getTableName()+"'");
			if(dynAttrs!=null)
			{
				dynamicAttributes.clear();
				displayNamesToNames.clear();
				for(int i=0;i<dynAttrs.size();i++)
				{
					DynamicAttribute dynAttr = dynAttrs.get(i);
					dynamicAttributes.put(dynAttr.getName(), dynAttr);
					displayNamesToNames.put(dynAttr.getDisplayName(), dynAttr.getName());
				}
			}
		}
	}
	
	
	public DynamicObject getDynamicAttributes() throws Exception
	{
		DynamicObject dynObj = getObject();
		dynObj.setProperties(dynamicAttributes);
		return dynObj;
	}
	
	/**
	 * Load the Object from the database identified by id
	 * @param Object id
	 * @return the DynamicObject identified by the id
	 * @throws Exception
	 */
	public DynamicObject load(Object id) throws Exception
	{
		try
		{			 
			String query = "SELECT * FROM "+getTableName()+" WHERE "+getPrimaryKey(id);
			List<DynamicObject> list = executeQuery(query);
			if(list!=null && list.size()==1)
				return list.get(0);
			else if(list!=null && list.size()>1)
				throw new Exception("Error loading DynamicObject: Query return multiple rows");
			else
				return null;
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error loading DynamicObject on table " + getTableName(), e);
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
	
	public long listCount(String whereClause, String tableAlias) throws Exception
	{
		return listJoinCount(whereClause, null, null, null, tableAlias);
	}
	
	public long listJoinCount(String whereClause, String joinType, String joinTable, String joinCondition) throws Exception
	{
		return listJoinCount(whereClause, joinType, joinTable, joinCondition, null) ;
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
	public long listJoinCount(String whereClause, String joinType, String joinTable, String joinCondition, String tableAlias) throws Exception
	{
		try
		{
			whereClause = excludeSystemObjects(whereClause, tableAlias);
			String query = "SELECT COUNT(*) FROM " + getTableName(tableAlias);
			String joinClause = getJoinClause(joinType, joinTable, joinCondition);
			String where = (whereClause!=null && !whereClause.trim().equals("")) ? " WHERE "+whereClause : "";
			return executeScalar(query+joinClause+where);
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error counting DynamicObjects on table " + getTableName(), e);
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
			String query = "SELECT COUNT(*) FROM "+getTableName();
			String whereClause = getWhereClause(keyWords);
			whereClause = excludeSystemObjects(whereClause);
			String where = (whereClause!=null && !whereClause.trim().equals("")) ? " WHERE "+whereClause : "";
			return executeScalar(query+where);
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error counting DynamicObjects on table " + getTableName(), e);
			throw e;
		}
	}
	
	/**
	 * Counts objects that match with specified keyWords 
	 * @param String keyWords (Example: red shoes)
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
			
			String query = "SELECT COUNT(*) FROM "+getTableName();
			where = excludeSystemObjects(where);
			where = (where!=null && !where.trim().equals("")) ? " WHERE "+where : "";
			
			return executeScalar(query+where);
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error counting DynamicObjects on table " + getTableName(), e);
			throw e;
		}
	}

	/**
	 * List the Objects from the database specified by whereClause
	 * @param String whereClause (Example: "field1='value1' AND field2='value2'")
	 * @return a List containing related Objects
	 * @throws Exception
	 */
	public List<DynamicObject> list(String whereClause) throws Exception
	{
		try
		{
			whereClause = excludeSystemObjects(whereClause);
			String query = "SELECT * FROM "+getTableName();
			String where = (whereClause!=null && !whereClause.trim().equals("")) ? " WHERE "+whereClause : "";
			return executeQuery(query+where);
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error listing DynamicObjects on table " + getTableName(), e);
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
	public List<DynamicObject> list(String whereClause, String orderClause) throws Exception
	{
		try
		{
			whereClause = excludeSystemObjects(whereClause);
			String query = "SELECT * FROM "+getTableName();
			String where = (whereClause!=null && !whereClause.trim().equals("")) ? " WHERE "+whereClause : "";
			String order = (orderClause!=null && !orderClause.trim().equals("")) ? " ORDER BY "+orderClause : "";
			return executeQuery(query+where+order);
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error listing DynamicObject on table " + getTableName(), e);
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
	public List<DynamicObject> list(String whereClause, int begin, int count) throws Exception
	{
		try
		{
			whereClause = excludeSystemObjects(whereClause);
			String query = "SELECT * FROM "+getTableName();
			String where = (whereClause!=null && !whereClause.trim().equals("")) ? " WHERE "+whereClause : "";
			String limit = " LIMIT "+begin+","+count;
			return executeQuery(query+where+limit);
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error listing DynamicObject on table " + getTableName(), e);
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
	public List<DynamicObject> list(String whereClause, String orderClause, int begin, int count) throws Exception
	{
		return listJoin(whereClause, orderClause, begin, count, null, null, null);
	}
	
	public List<DynamicObject> list(String whereClause, String orderClause, int begin, int count, String tableAlias) throws Exception
	{
		return listJoin(whereClause, orderClause, begin, count, null, null, null, tableAlias);
	}
	
	
	public List<DynamicObject> listJoin(String whereClause, String orderClause, int begin, int count, String joinType, String joinTable, String joinCondition) throws Exception
	{
		return listJoin(whereClause, orderClause, begin, count, joinType, joinTable, joinCondition, null);
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
	public List<DynamicObject> listJoin(String whereClause, String orderClause, int begin, int count, String joinType, String joinTable, String joinCondition, String tableAlias) throws Exception
	{
		try
		{
			whereClause = excludeSystemObjects(whereClause, tableAlias);
			String query = "SELECT * FROM "+getTableName(tableAlias);
			String joinClause = getJoinClause(joinType, joinTable, joinCondition);
			String where = (whereClause!=null && !whereClause.trim().equals("")) ? " WHERE "+whereClause : "";
			String order = (orderClause!=null && !orderClause.trim().equals("")) ? " ORDER BY "+orderClause : "";
			String limit = "";
			if(count > 0)
			limit = " LIMIT "+begin+","+count;
			return executeQuery(query+joinClause+where+order+limit);
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error listing DynamicObject on table " + getTableName(), e);
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
	public List<DynamicObject> search(String keyWords) throws Exception
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
	public List<DynamicObject> search(String keyWords, String orderClause) throws Exception
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
	public List<DynamicObject> search(String keyWords, int begin, int count) throws Exception
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
	public List<DynamicObject> search(String keyWords, String orderClause, int begin, int count) throws Exception
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
	public List<DynamicObject> search(String keyWords, String whereClause, String orderClause, int begin, int count) throws Exception
	{
		try
		{
			String where1 = (whereClause==null) ? "" : whereClause;
			String where2 = getWhereClause(keyWords);
			String where = "";
			//where = excludeSystemObjects(where);
			if(where2!=null && !where2.trim().equals("") && where1!=null && !where1.trim().equals(""))
				where = where1 + " AND " + where2;
			else if(where2!=null && !where2.trim().equals(""))
				where = where2;
			else if(where1!=null && !where1.trim().equals(""))
				where = where1;
			

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
			getLogger().log(Level.SEVERE, "Error searching DynamicObjects on table " + getTableName(), e);
			throw e;
		}
	}
	
	/**
	 * Inserts obj in the database
	 * @param DynamicObject obj
	 * @return the mysql last_insert_id 
	 * @throws Exception
	 */
	public long insert(DynamicObject obj) throws Exception
	{
		try
		{
			String query = "INSERT INTO "+getTableName()+" ("+getQueryFieldList()+") VALUES ("+getInsertValueList(obj)+")";
			return execute(query);
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error inserting DynamicObject on table " + getTableName(), e);
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
			String query = "UPDATE "+getTableName()+" SET "+getUpdateFieldList(keyPairs);
			return execute(query+where);
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error updating DynamicObject on table " + getTableName(), e);
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
			String query = "UPDATE "+getTableName()+" SET "+getUpdateFieldList(keyPairs);
			return execute(query+where);
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error updating DynamicObject on table " + getTableName(), e);
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
	public int update(DynamicObject dynObj, String whereClause) throws Exception
	{
		try
		{
			String where = (whereClause!=null && !whereClause.trim().equals("")) ? " WHERE "+whereClause : "";
			String query = "UPDATE "+getTableName()+" SET "+getUpdateFieldList(dynObj);
			return execute(query+where);
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error updating DynamicObject on table " + getTableName(), e);
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
	public int update(DynamicObject dynObj, Object id) throws Exception
	{
		try
		{
			String where = (id!=null) ? " WHERE "+getPrimaryKey(id) : "";
			String query = "UPDATE "+getTableName()+" SET "+getUpdateFieldList(dynObj);
			return execute(query+where);
		}
		catch(Exception e)
		{
			getLogger().log(Level.SEVERE, "Error updating DynamicObject on table " + getTableName(), e);
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
	public int update(DynamicObject dynObj, Object id, boolean forceInsert) throws Exception
	{
	 int rows = update(dynObj, id);
	 
	 if(rows == 0 && forceInsert)
	  {
		 rows = (int)insert(dynObj);
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
			String query = "DELETE FROM "+getTableName();
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
			String query = "DELETE FROM "+getTableName();
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
	 * @return the List of DynamicObject
	 * @throws Exception
	 */
	protected List<DynamicObject> executeQuery(String query) throws Exception
	{
		List<DynamicObject> ret = null;
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
				ret = new ArrayList<DynamicObject>();
				while(rs.next())
				{
					Map<String,Object> result = new HashMap<String,Object>();
					for(int i=0;i<attributes.size();i++)
					{
						AttributeInfo attrInfo = attributes.get(i);
						if(attrInfo.getType()!=null)
						{
							String attrType = attrInfo.getType().toLowerCase();
							if(attrType.contains("varchar") || attrType.contains("text") || attrType.contains("char"))
								result.put(attributes.get(i).getName(), rs.getString(attributes.get(i).getName()));
							else if(attrType.contains("int"))
								result.put(attributes.get(i).getName(), rs.getLong(attributes.get(i).getName()));
							else if(attrType.contains("decimal"))
								result.put(attributes.get(i).getName(), rs.getBigDecimal(attributes.get(i).getName()));
							else if(attrType.contains("float"))
								result.put(attributes.get(i).getName(), rs.getFloat(attributes.get(i).getName()));
							else if(attrType.contains("double"))
								result.put(attributes.get(i).getName(), rs.getDouble(attributes.get(i).getName()));
							else if(attrType.contains("date") || attrType.contains("time"))
								try{
									result.put(attributes.get(i).getName(), rs.getTimestamp(attributes.get(i).getName()));
									}catch(Exception e){
										result.put(attributes.get(i).getName(), InteraxDate.getNullTimestamp());
									}
						}
					}
					dbObj = new DBObject(attributes,result);
					DynamicObject dynObj = getObject();
					dynObj.setProperties(dynamicAttributes);
					dynObj.setDisplayNamesToNames(displayNamesToNames);
					ret.add(dbObj.convertToDynObj(dynObj));
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
	 * Executes the given SQL statement, which may be an INSERT, UPDATE, or DELETE statement 
	 * @param String query
	 * @return the number of rows affected in the query
	 * @throws Exception
	 */
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
	 * Parse the database table and get the fields list
	 * @return the list of table field names separated with comma (Example: field1,field2,...)
	 */
	protected String getQueryFieldList()
	{
		String ret = "";
		String separator = "";
		for (int i=0;i<attributes.size();i++) 
		{
			ret += separator + attributes.get(i).getName();
			separator = ",";
		}
		return ret;
	}
	
	/**
	 * Parse the database table and the parameter obj to create the SQL statement for update
	 * @return the list of table field names and values (Example: field1='value1',field2='value2',...)
	 */
	@SuppressWarnings("unchecked")
	protected String getUpdateFieldList(DynamicObject dynObj) throws Exception
	{
		DBObject dbObj = new DBObject(attributes,dynObj);
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
            	    
            	    if (m.get(attrInfo.getName()) instanceof InteraxDate) {
            	    	
            	    	InteraxDate value = (InteraxDate)m.get(attrInfo.getName());
            	    	if(value.isNull())
            	    		ret += separator + attributes.get(i).getName() + "='0000-00-00 00:00:00'";
            	    	else
            	    		ret += separator + attributes.get(i).getName() + "='"+ value.toMySqlString(pattern)+"'";
            	    	
					}else{
						
						formatter = new SimpleDateFormat(pattern);
						Date value = (Date)m.get(attrInfo.getName());

						try {
							if(value!=null)
								ret += separator + attributes.get(i).getName() + "='"+formatter.format(value)+"'";
							else
								ret += separator + attributes.get(i).getName() + "=NULL";
						} catch(Exception e) {}
					}
            	}
            	else if(attrType.equals("tinyint(1)"))
            	{
            		// FIXME esto se hace temporalmente, porq se debe unificar en todos lados que siempre se debe guardar un boolean
            		Object obj = m.get(attrInfo.getName());
            		if(obj!=null){
            			if (obj instanceof Boolean) {
            				Boolean booleanValue = (Boolean) obj;
            				if(booleanValue)
            					ret += separator + attributes.get(i).getName() + "='1'";
            				else
            					ret += separator + attributes.get(i).getName() + "='0'";
            			}else if (obj instanceof Number) {
            				Number new_name = (Number) obj;
            				if(new_name.intValue() == 0 || new_name.intValue() == 1)
            					ret += separator + attributes.get(i).getName() + "='"+new_name.intValue()+"'";
            			}
            		}else {
            			ret += separator + attributes.get(i).getName() + "='"+ "'"+attrInfo.getDefaultValue()+"'";
            		}
            	}
				else if(attrType.contains("char") || attrType.contains("text"))
					if(m.get(attrInfo.getName())!=null)
            			ret += separator + attributes.get(i).getName()+"='"+m.get(attrInfo.getName()).toString().replace("'","\\'")+"'";
            		else
            			ret += separator + attributes.get(i).getName()+"=NULL";
				else if(attrType.contains("int"))
         	    {
					Long val = null;
					if(m.get(attrInfo.getName())!=null)
						val = Long.parseLong("" + m.get(attrInfo.getName()));
					
					if(m.get(attrInfo.getName())!=null && val!=0)
            			ret += separator + attributes.get(i).getName()+"='"+val+"'";
            		else if(attrInfo.isAllowNull())
            			ret += separator + attributes.get(i).getName()+"=NULL";
            		else
            			ret += separator + attributes.get(i).getName()+"="+attrInfo.getDefaultValue();
         	    }
            	else
            		if(m.get(attrInfo.getName())!=null)
            			ret += separator + attributes.get(i).getName()+"='"+m.get(attrInfo.getName())+"'";
            		else if(attrInfo.isAllowNull())
            			ret += separator + attributes.get(i).getName()+"=NULL";
            		else
            			ret += separator + attributes.get(i).getName()+"='"+attrInfo.getDefaultValue()+"'";				
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
	            	    		Date value = (Date)obj;

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
					else if(attrType.contains("char") || attrType.contains("text"))
						if(obj!=null)
	            			ret += separator + attributes.get(i).getName()+"='"+obj.toString().replace("'","\\'")+"'";
	            		else
	            			ret += separator + attributes.get(i).getName()+"=NULL";
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
						Long val = null;
						if(obj!=null)
								val = Long.parseLong("" + obj);
						
						if(obj!=null && val!=0)
	            			ret += separator + attributes.get(i).getName()+"='"+val+"'";
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
	@SuppressWarnings("unchecked")
	protected String getInsertValueList(DynamicObject dynObj) throws Exception
	{
		DBObject dbObj = new DBObject(attributes,dynObj);
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
            	    
            	    if (m.get(attrInfo.getName()) instanceof InteraxDate) {
            	    	InteraxDate value = (InteraxDate)m.get(attrInfo.getName());
            	    	if(value.isNull())
            	    		ret += separator + attributes.get(i).getName() + "='0000-00-00 00:00:00'";
            	    	else
            	    		ret += separator + "'"+ value.toMySqlString(pattern)+"'";
            	    	
					}else{
						formatter = new SimpleDateFormat(pattern);
						 Date value = (Date)m.get(attrInfo.getName());
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
		                	    	if(!defaultValue.equalsIgnoreCase("NULL"))
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
            		else
            			ret += separator + "NULL";
            	else if(attrType.equals("tinyint(1)"))
            	   {
            		//FIXME esto se hace temporalmente, porq se debe unificar en todos lados que siempre se debe guardar un boolean
            		boolean booleanValue = false;
            		if(m.get(attrInfo.getName())!=null){
            			Object obj = m.get(attrInfo.getName());
            			if(obj instanceof Boolean) {
            				booleanValue = (Boolean) obj;
                    		if(booleanValue)
                    			ret += separator + "'1'";
                    		else
                    			ret += separator + "'0'";
            			}else if (obj instanceof Number) {
            				Number new_name = (Number) obj;
            				if(new_name.intValue() == 0 || new_name.intValue() == 1)
            					ret += separator + "'"+new_name.intValue()+"'";
            			}
            		}else{
            			ret += separator + "'"+attrInfo.getDefaultValue()+"'";
            		}
            	   }
            	else if(attrType.contains("int"))
            	{
					Long val = null;
					if(m.get(attrInfo.getName())!=null)
						val = Long.parseLong("" + m.get(attrInfo.getName()));
					if(m.get(attrInfo.getName())!=null && val!=0)
            			ret += separator + "'"+val+"'";
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
	
	protected String getWhereClause(String keyWords)
	{
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
						subGroup += or+attributes.get(i).getName()+" LIKE '%"+words[j]+"%'";
						or = " OR ";
	            	}
					else if(attrType.contains("int"))
					{
						try {
							subGroup += or+attributes.get(i).getName()+"='"+Long.parseLong(words[j])+"'";
							or = " OR ";
						}
						catch(Exception e){	}
					}
					else if(attrType.contains("float"))
					{
						try {
							subGroup += or+attributes.get(i).getName()+"='"+Float.parseFloat(words[j])+"'";
							or = " OR ";
						}
						catch(Exception e){	}
						
					}
					else if(attrType.contains("double"))
					{
						try {
							subGroup += or+attributes.get(i).getName()+"='"+Double.parseDouble(words[j])+"'";
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
	
	
	
	/**
	 * Genera el siguiente fieldName para atributos dinamicos, buscando el ultimo fieldName e incrementandole el indice en 1
	 * @return String con el nombre
	 * @throws Exception
	 */
	public String getNewDynamicFieldName() throws Exception
	{
		
		
		Connection conn = null;
		Statement s = null;
		ResultSet rs = null;
		try{
			
			if(this.controlledConnection == null) conn = dataSource.getConnection();
			else conn = this.controlledConnection;
			
			s = conn.createStatement();		// TODO FIX 8 for bdField.length
			String query = "SELECT name, LENGTH(SUBSTRING(name,8)) as length, SUBSTRING(name,8) currentId " +
							"FROM dynamic_attribute " +
							"WHERE tablename = '"+getTableName()+"' " +
							"ORDER by length desc, currentId desc " +
							"LIMIT 0,1 ";
			getLogger().log(Level.FINE, query);
			s.executeQuery(query);
			rs = s.getResultSet();
			if(rs != null) {
				while(rs.next()) {
					String currentId = rs.getString("currentId");
					int value = Integer.parseInt(currentId)+1;
					return getDynamicPrefixFieldName()+(value); //"bdField"+(value);
				}
			}
			return getDynamicPrefixFieldName()+0;

		}catch(Exception e){
			getLogger().log(Level.SEVERE, "Error loading DynamicObject on table " + getTableName(), e);
			throw e;
		}finally{
			if(this.controlledConnection==null)	close(conn,s,rs);
		}
	}
	
	// Method to exclude objects with '0' id (System use objects)
	//TODO MEJORAR ESTA FUNCIÓN PARA HACERLA MULTI TIPO Y MULTI NOMBRE RESPECTO AL PK
	private String excludeSystemObjects(String whereClause){
		return excludeSystemObjects(whereClause, null);
	}

	private String excludeSystemObjects(String whereClause, String tableAlias){

		this.logger.info("OLD TABLE ALIAS IS: " + tableAlias);
		if(tableAlias==null || tableAlias.equals("")){
			tableAlias = getTableName();
		}
		this.logger.info("NEW TABLE ALIAS IS: " + tableAlias);
		
		if(haveAttribute("id")){
			if(whereClause==null || whereClause.length()==0)
				whereClause = " "+tableAlias+".id<>0";
			else{
				Pattern pattern = Pattern.compile("(?i)GRoUP bY");
				Matcher matcher = pattern.matcher(whereClause);
				if (matcher.find()) {
					
					int index = whereClause.indexOf(matcher.group(0));
					StringBuffer temp = new StringBuffer();
					
					temp.append(whereClause.substring(0, index));
					temp.append(" AND "+tableAlias+".id<>0 ");
					temp.append(whereClause.substring(index));
					
					whereClause = temp.toString();
				}else{
					whereClause += " AND "+tableAlias+".id<>0";
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
}
