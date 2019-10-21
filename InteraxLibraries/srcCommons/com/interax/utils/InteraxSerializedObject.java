package com.interax.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class InteraxSerializedObject implements Serializable {

	private static final long serialVersionUID = 1L;
	private byte [] data;
//	private ClassLoader classLoader;
	
	public InteraxSerializedObject(Object obj) {
		super();
		this.data = _serialize(obj);
//		this.classLoader = this.getClass().getClassLoader();
	}
	
	public InteraxSerializedObject(byte [] data) {
		super();
		this.data = data;
//		this.classLoader = this.getClass().getClassLoader();
	}
	

	public Object _deserialize(InteraxSerializedObject tt, ClassLoader classLoader){
		// TODO sacar el data del tt y crear el object
		
		Object object = null;
		
		try {

			ByteArrayInputStream stream = new ByteArrayInputStream (tt.getData());
			ObjectInputStream input = new ClassLoaderObjectInputStream(classLoader, stream);
			
			object = input.readObject();

			//object = (Object) new java.io.ObjectInputStream(new java.io.ByteArrayInputStream(data)).readObject();
			return object;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
//	public Object deserialize(){
//		return _deserialize(this);
//	}
//	
//	public Object deserialize(InteraxSerializedObject tt){
//		return _deserialize(tt);
//	}
	
	
	public InteraxSerializedObject serialize(Object obj){
		return new InteraxSerializedObject(obj);
	}
	
	private byte[] _serialize(Object obj){
		// TODO pasar clase a arreglo de bytes
	      ByteArrayOutputStream bos = new ByteArrayOutputStream();
	      ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(bos);
		      oos.writeObject(obj);
		      oos.flush();
		      oos.close();
		      bos.close();
		      return bos.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	      return null;
	}
	
	

	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}

//	public ClassLoader getClassLoader() {
//		return classLoader;
//	}
//
//	public void setClassLoader(ClassLoader classLoader) {
//		this.classLoader = classLoader;
//	}
//	
//	
	
/**
 * TODO for make tests.
 * 
 * WEB:
 * ------------
 * strutsForm.setX(HOLA)
 * tt = new TT(strutsForm);
 * tt2 = clientEjb.method(tt)
 * strutsForm2 = (StrutsForm) tt2.deserialize;
 * syso(strutsForm2.getX())
 * 
 * 
 * CLIENT EJB:
 * ------------
 *  * bytes1 = TT.getData();
 *  --> TO DATABASE
 *  <-- FROM DATABASE
 * tt2 = new TT(bytes1);
 * return tt2;
 * 
 */

}
