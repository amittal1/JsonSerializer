package com.Serialize.encoder;


/**
 * Author: Ankit Luv Mittal
 * Created: 05/31/2017
 * Modified: 06/23/2017
 * Description: Encode any object to json
 * 				1. classRegistry is for specific classes which are registered with selected fields 
 * 					into this project.
 * Dependency:Libraries
 * 				1. Gson Library: Provided by google for converting objects to json
 */

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.Serialize.Interface.JsonSerializable;
import com.google.gson.Gson;

public class JsonEncoder {
	static private Map <String, Field []> classRegistry;
	static private Gson gson;
	
	static{
		classRegistry = new HashMap<>();
		gson =  new Gson();
	}
	
	//Register class in class registry
	//returns true if registered correctly or already present in classRegistry
	//false if any field is not found 
	@SuppressWarnings("rawtypes")
	public static boolean registerClass(Class c,  String ...fieldNames ) {

		Field [] fields = new Field [fieldNames.length];
		
		for (int i = 0; i < fieldNames.length; i++) {
			try {
				fields[i] = c.getDeclaredField(fieldNames[i]);
			} catch (NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
				return false;
			}
		}
		classRegistry.put(c.getName(), fields);
		return true;
	}
	
	@SuppressWarnings("rawtypes")
	private static Field [] getFields(Class c){
		return c.getDeclaredFields();
	}
	
	
	//Public Method called by user
	public static String serializeToJson(Object obj) {
		return toJson(defaultProcessor(obj));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Object defaultProcessor(Object obj) {
		Object result="";
		if(obj == null){
			return null;
		}
		if(isPrimitiveOrWrapper(obj.getClass())){
			result = String.valueOf(obj);
		}
		else if(obj instanceof Collection || obj instanceof Map){
			 
			Object temp_result;
			if(obj instanceof Map){
					temp_result = new LinkedHashMap<>();
				((Map) obj).forEach((k,v) -> {
					//key in map should always be primitive/String, if not then its ignored
					if(isPrimitiveOrWrapper(k.getClass()))
						((Map) temp_result).put(k, defaultProcessor(v));
				});
			}
			else{
				temp_result = new ArrayList<>();
				((Collection) obj).forEach((value)->{
					((Collection) temp_result).add(defaultProcessor(value));
				});
			}
			result = temp_result;
		}
		else{
			result = encodeObject(obj);
		}
		
		return result;
	}
	
	
	@SuppressWarnings("rawtypes")
	private static boolean isPrimitiveOrWrapper(Class type) {
		if((type.isPrimitive() && type != void.class) || type == String.class ||
		        type == Double.class || type == Float.class || type == Long.class ||
		        type == Integer.class || type == Short.class || type == Character.class ||
		        type == Byte.class || type == Boolean.class || type == String.class){
			
				return true;
		}
		return false;
	}

	private static Object encodeObject(Object obj) {
		Object result = null;

		if(obj == null){
			return "";
		}
		
		if(obj instanceof JsonSerializable){
			result = ((JsonSerializable) obj).prepareJSON();
		}
		else{
			result = JsonEncoder.serializedFieldsUsingReflection(obj);
		}
		
		return defaultProcessor(result);
	}

	@Deprecated
	private static boolean implements_toString(Object obj) {
		// TODO Auto-generated method stub
		Method method = null;
		try {
				method = obj.getClass().getDeclaredMethod("toString");
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			return false;
		}
		if(method != null)
			return true;
		
		return false;
	}
	
	private static Map<String, Object> serializedFieldsUsingReflection(Object obj) {
		Field [] fields;
		if(classRegistry.containsKey(obj.getClass().getName())){
			fields= classRegistry.get(obj.getClass().getName());
		}
		else{
			fields = getFields(obj.getClass());
		}
		Map<String, Object> map = new LinkedHashMap<>();
		
		for(Field d: fields){
			d.setAccessible(true);
			try {
				map.put(d.getName(), defaultProcessor(d.get(obj)));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
				map.put("exception in getting field: "+d.getName(), e);
			}
		}
		return map;
	}
	
	private static String toJson(Object obj) {
		return gson.toJson(obj);
	}

}
