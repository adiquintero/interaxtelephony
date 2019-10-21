package com.interax.utils;

public class ArrayUtils 
{
	public static boolean contains( Object[] array, Object obj)
    {
		boolean ret = false;
		
		if(array==null) 
			return ret;
		
		for(int i=0;i<array.length;i++)
		{
			if(obj==null && array[i]==null)
				return true;
			else if(obj==array[i])
				return true;
			else if(obj!=null && obj.equals(array[i]))
				return true;
		}
		return ret;
    }
}
