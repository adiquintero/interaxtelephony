package com.interax.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
//import java.util.Hashtable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
//import java.util.Vector;

public class DowngradeUtils {

	
	@SuppressWarnings("unchecked")
	public static Object downgrade(Object childObject, Class clazz) throws Exception {

		Object[] emptyParameterList = new Object[0];
		Object parentObject = clazz.getConstructor(new Class[0]).newInstance(emptyParameterList);

		List<Method> allGetters = new ArrayList<Method>();
		HashMap<String, Method> allSetters = new HashMap<String, Method>();
		Method[] methods = clazz.getMethods();
		int methodCount = methods.length;
		for(int i=0; i<methodCount; i++){
			Method method =  methods[i];
			String methodName = method.getName();
			if(methodName.startsWith("get") && method.getParameterTypes().length == 0){
				allGetters.add(method);
			}
			else if(methodName.startsWith("set") && method.getParameterTypes().length == 1){
				allSetters.put(methodName + "-" + method.getParameterTypes()[0].getCanonicalName(), method);
			}
		}
		
		HashMap<String, Method> getters = new HashMap<String, Method>();
		HashMap<String, Method> setters = new HashMap<String, Method>();
		int allGetterCount = allGetters.size();
		for(int i=0; i<allGetterCount; i++){
			Method currentGetter = allGetters.get(i);
			String matchingSetterName = "set" + currentGetter.getName().substring(3) + "-" + currentGetter.getReturnType().getCanonicalName();
			Method matchingSetter = allSetters.get(matchingSetterName);
			
			if(matchingSetter!=null){
				getters.put(currentGetter.getName(), currentGetter);
				setters.put(matchingSetter.getName(), matchingSetter);
			}
		}
		
		Field[] fields = clazz.getDeclaredFields();
		int fieldCount = fields.length;
		for(int i=0; i<fieldCount; i++){
			String fieldName = fields[i].getName();
			fieldName = fieldName.toUpperCase().charAt(0) + fieldName.substring(1);
			
			Method getter = getters.get("get" + fieldName);
			Method setter = setters.get("set" + fieldName);
			
			if(getter!=null && setter != null){
				Object value = getter.invoke(childObject, emptyParameterList);
				
				if(value!=null){
					Class valueExpectedClazz = fields[i].getType();
					Class valueClazz = value.getClass();

					boolean simpleDowngrade = false;
					boolean isPrimitive = valueExpectedClazz.isPrimitive();
					boolean isInterface = valueExpectedClazz.isInterface();;
					boolean collectionDowngrade = false;
					
					if(isPrimitive){
						// Do Nothing
					}
					else if(isInterface){
						collectionDowngrade = Collection.class.isAssignableFrom(valueClazz);
					}
					else{
						simpleDowngrade = !(valueExpectedClazz.getCanonicalName().equals(valueClazz.getCanonicalName()));
					}
					
					if(collectionDowngrade){
						Collection collection = (Collection) value;
						Collection downgradedCollection = (Collection) valueClazz.getConstructor(new Class[0]).newInstance(emptyParameterList);
						
						Class expectedElementClazz = Object.class;
						String collectionType = fields[i].getGenericType().toString();
						if(collectionType.contains("<") && collectionType.endsWith(">")){
							String expectedElementClazzName = collectionType.substring(collectionType.indexOf("<") + 1);
							expectedElementClazzName = expectedElementClazzName.substring(0, expectedElementClazzName.length() - 1);
							expectedElementClazz = Class.forName(expectedElementClazzName);
						}
						Iterator iterator = collection.iterator();
						while(iterator.hasNext()){
							Object element = iterator.next();
							Class elementClazz = element.getClass();
							
							if(!elementClazz.getCanonicalName().equals(expectedElementClazz.getCanonicalName())){
								Object downgradedElement = downgrade(element, expectedElementClazz);
								downgradedCollection.add(downgradedElement);
							}else{
								downgradedCollection.add(element);
							}
						}
						
						setter.invoke(parentObject, downgradedCollection);
					}
					else if(simpleDowngrade){
						Object downgradedValue = downgrade(value, valueExpectedClazz);
						setter.invoke(parentObject, downgradedValue);
					}
					else{
						setter.invoke(parentObject, value);
					}
				}
			}
		}
		
	   return parentObject;
	}

	
}
