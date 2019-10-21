package com.interax.ejbs;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
//import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.SessionContext;

import com.interax.logging.LoggerFactory;
import com.interax.persistence.audict.ActionType;
import com.interax.persistence.audict.AdminAction;
import com.interax.persistence.audict.AdminActionManager;
import com.interax.persistence.datamanagers.GenericDataManager;
import java.util.HashMap;

public abstract class GenericBean 
{
	static Logger log = LoggerFactory.getLogger(GenericBean.class); 

	@Resource SessionContext ctx; 
	protected String objectType;
	@SuppressWarnings("unchecked")
	protected Class[] managersClasses;
	public abstract void declareManagersClasses();
	
	protected static HashMap<String,GenericDataManager> managers;
	protected static HashMap<String,String> dataSources;
	protected static HashMap<String,AdminActionManager> actionManagers;
	protected String dataSource;
	protected boolean multiDataSource;
		
	
	public GenericBean(){
		if (managers == null){
			dataSources = new HashMap<String, String>();
			managers = new HashMap<String, GenericDataManager>();
			actionManagers = new HashMap<String, AdminActionManager>();
		}
		this.multiDataSource = true;
		this.declareManagersClasses(); // FIXME: THIS IS A PATCH BECAUSE THIS CONSTRUCTOR IS CALLED WHEN DONE: cacheController.get(key)
	}
	
	
	public GenericBean(String dataSource){
	    if (managers == null){
			dataSources = new HashMap<String, String>();
			managers = new HashMap<String, GenericDataManager>();
			actionManagers = new HashMap<String, AdminActionManager>();
		}
		this.multiDataSource = false;
		this.initialize(null, dataSource);
	}
	
	
//	public void initializeByDatasource(String dataSource) throws Exception
//	{
//		String caller = getCaller();
//		if(dataSources.get(caller)==null)
//			dataSources.put(caller, dataSource);
//		this.initialize(dataSource, dataSource);
//	}
	
	
	@SuppressWarnings("unchecked")
	protected void initialize(String key, String dataSource)
	{
		int i =0;
		this.declareManagersClasses();
		GenericDataManager objMgr;
		if(managersClasses != null){
			for (Class managerClass: managersClasses) {
							
				String completeKey;
				if(key!= null)
					completeKey = managerClass.getSimpleName() + "-" + key;
				else
					completeKey = managerClass.getSimpleName();
				
				boolean lostInstace = false;				
				if (managers.get(completeKey)!=null && !managers.get(completeKey).getClass().equals(managerClass))
					lostInstace = true;
								
				if(managers.get(completeKey)==null || lostInstace){//|| !instaceOf){
					
					if(i==0)
						objMgr = instanceDataManager(managerClass, dataSource, true);
					else
						objMgr = instanceDataManager(managerClass, dataSource, false);
					managers.put(completeKey, objMgr);
					i++;
					AdminActionManager actionMgr = new AdminActionManager(dataSource);
					actionManagers.put(completeKey, actionMgr);
				}
				//NOTA: Se hace la llamada al instanceDataManager con el booleano en true para setear la variable objectType, debido a que otros EJB 
				//	    crean la instancia de otros Manager, los cuales no tienen el valor i == 0, lo que origina que no se setee el objectType. Ademas
				//	    se crea una entrada en el hash de managers, y cuando se hace esta instruccion managers.get(completeKey)==null me retorna false 
				//		con i == 0 y el valor objectType queda en null
					    
				if(managers.get(completeKey)!=null && i==0){
					objMgr = instanceDataManager(managerClass, dataSource, true);
					managers.put(completeKey, objMgr);
					i++;
					AdminActionManager actionMgr = new AdminActionManager(dataSource);
					actionManagers.put(completeKey, actionMgr);
				}
			}
		}
	}
	
	
	@SuppressWarnings("unchecked")
	private GenericDataManager instanceDataManager(Class managerClass, String dataSource, boolean initializeObjectType){
		
		GenericDataManager objMgr;
		Class[] proto = new Class[1];
		proto[0] = String.class;

		Object[] params = new Object[1];
		params[0] = dataSource;
		
		try {
			Constructor ct = managerClass.getConstructor(proto);
			objMgr = (GenericDataManager)ct.newInstance(params);
			if(initializeObjectType){
				Method meth = null;
				try{
					meth = managerClass.getMethod("getObject");
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
				if(meth != null)
					objectType = (meth.invoke(objMgr)).getClass().getSimpleName();
			}
		} catch (SecurityException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			return null;
		} catch (InstantiationException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
		return objMgr;

	}
	
	
	//	protected GenericDataManager getDefaultManager(long customnerId){
//		return managers.get(getDefaultManagerKey());
//	}
//	
//	protected GenericDataManager getManager(int index, long customnerId){
//		return managers.get(getManagerKey(index));
//	}
//	
//	protected GenericDataManager getDefaultManager(){
//		return managers.get(getDefaultManagerKey());
//	}
//	
//	protected GenericDataManager getManager(int index){
//		return managers.get(getManagerKey(index));
//	}

//	protected String getDataSource(String caller) {
//	return dataSources.get(caller);
//}
//
//protected String getDataSource() {
//	if(isMultiDatasource()){
//		String caller = ctx.getCallerPrincipal().getName();
//		return dataSources.get(caller);
//	}
//	else{
//		return dataSource;
//	}
//}
	
	public boolean echo() throws Exception{
		return true;
	}
	
	protected String getDefaultManagerKey() {
		return this.getDefaultManagerClass().getSimpleName();
	}

	@SuppressWarnings("unchecked")
	protected String getManagerKey(Class clazz) {
		return clazz.getSimpleName();
	}

	@SuppressWarnings("unchecked")
	protected Class getDefaultManagerClass(){
		return this.managersClasses[0];
	}

	protected String getCaller() {
		return ctx.getCallerPrincipal().getName();
	}
	
	protected GenericDataManager getDefaultManager(){
		return managers.get(getDefaultManagerKey());
	}
	
	@SuppressWarnings("unchecked")
	protected GenericDataManager getManager(Class clazz){
		return managers.get(getManagerKey(clazz));
	}

	protected AdminActionManager getActionManager()
	{
		return actionManagers.get(getDefaultManagerKey());
	}
	
	@SuppressWarnings("unchecked")
	protected AdminActionManager getActionManager(Class clazz)
	{
		return actionManagers.get(getManagerKey(clazz));
	}
		
	public void registerAction(String objectType, String objectIds, String userLogin, ActionType actionType)
	{
		try
		{
			AdminAction action = new AdminAction();
			action.setObjectType(objectType);
			action.setObjectIds(objectIds);
			action.setActionType(actionType.toString());
			action.setUserLogin(userLogin);
			action.setOperationTime(new Date());
			AdminActionManager actionMgr = getActionManager();
			actionMgr.insert(action);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE,"Error registering action",e);
		}		
	}
	
	public void registerAction(String objectType, String objectIds, String userLogin, ActionType actionType, String actionInfo)
	{
		try
		{
			AdminAction action = new AdminAction();
			action.setObjectType(objectType);
			action.setObjectIds(objectIds);
			action.setActionType(actionType.toString());
			action.setActionInfo(actionInfo);
			action.setUserLogin(userLogin);
			action.setOperationTime(new Date());
			AdminActionManager actionMgr = getActionManager();
			actionMgr.insert(action);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE,"Error registering action",e);
		}		
	}

	public boolean isMultiDatasource() {
		return multiDataSource;
	}

	public void setMultiDatasource(boolean isMultiDatasource) {
		this.multiDataSource = isMultiDatasource;
	}
}
