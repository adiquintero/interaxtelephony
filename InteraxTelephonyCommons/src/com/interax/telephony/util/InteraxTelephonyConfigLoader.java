package com.interax.telephony.util;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public abstract class InteraxTelephonyConfigLoader {

	
	@SuppressWarnings("unchecked")
	public static InteraxTelephonyConfig loadConfig(String filename, Class clazz)
	{
		return loadConfig(filename, null, clazz);
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static InteraxTelephonyConfig loadConfig(String filename, String configPath, Class clazz)
	{
		try
		{
			ResourceBundle rb = null;
			if (configPath!=null){
				URL urls[] = new URL[1];
				urls[0] = new File(configPath).toURL();
				URLClassLoader uClassLoader = new URLClassLoader(urls);
				rb = ResourceBundle.getBundle(filename, Locale.getDefault(), uClassLoader);
			}
			else{
				rb = ResourceBundle.getBundle(filename);
			}
			
				
			Class[] proto = new Class[1];
			proto[0] = ResourceBundle.class;

			Object[] params = new Object[1];
			params[0] = rb;
			Constructor ct = clazz.getConstructor(proto);
			InteraxTelephonyConfig config = (InteraxTelephonyConfig)ct.newInstance(params);			
			return config;
		}
		catch(MissingResourceException mre)
		{
			//this.logger.error("The config file doesn't exist or is incomplete");
			return null;
		}
		catch(NumberFormatException mre)
		{
			//this.logger.error("The config file is corrupted or malformed");
			return null;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			return null;
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			return null;
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			return null;
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			return null;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			return null;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			return null;
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	
	
}
