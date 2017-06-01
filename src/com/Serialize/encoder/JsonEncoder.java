package com.Serialize.encoder;


/**
 * Author: Ankit Luv Mittal
 * Created: 05/31/2017
 * Description: Encode any object to json
 * Dependency:Libraries
 * 				1. Gson Library: Provided by google for converting objects to json
 */

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.lang.model.type.PrimitiveType;

import com.Serialize.Interface.JsonSerializable;
import com.google.gson.Gson;
import com.test.encoder.Logger;

public class JsonEncoder {
	static private Map <String, Field []> classRegistry;
	static private Gson gson;
	private static int test =0;
	
	static{
		classRegistry = new HashMap<>();
		gson =  new Gson();
	}
	
	
	//Public Method called by user
	public static String serializeToJson(Object obj) {
		// TODO Auto-generated method stub
		return toJson(defaultProcessor(obj));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static String defaultProcessor(Object obj) {
		// TODO Auto-generated method stub
		String result="";
		if(obj == null){
			return null;
		}
		if(isPrimitiveOrWrapper(obj.getClass())){
			result = String.valueOf(obj);
		}
		else if(obj instanceof String){
			result = obj.toString();
		}
		else if(obj instanceof Collection || obj instanceof Map){
			Logger.debug(Logger.getMethodTrace() + " "+  test ++ +" "+  obj);
			Object temp_result;
			if(obj instanceof Map){
				Logger.debug(Logger.getMethodTrace() + " "+ test ++ +" "+   obj);
					temp_result = new HashMap<>();
				((Map) obj).forEach((k,v) -> {
					//if key is not a string ignore it
					((Map) temp_result).put(defaultProcessor(k), defaultProcessor(v));
				});
			}
			else{
				Logger.debug(Logger.getMethodTrace() + " "+ test ++ +" "+  obj);
				temp_result = new ArrayList<>();
				((Collection) obj).forEach((value)->{
					((Collection) temp_result).add(defaultProcessor(value));
				});
			}
			result = temp_result.toString();
		}
		else{
			Logger.debug(Logger.getMethodTrace() + " "+ test ++ +" "+ obj);
			result = encodeObject(obj);
		}
		
		return result;
	}
	
	
	private static boolean isPrimitiveOrWrapper(Class type) {
		if((type.isPrimitive() && type != void.class) ||
		        type == Double.class || type == Float.class || type == Long.class ||
		        type == Integer.class || type == Short.class || type == Character.class ||
		        type == Byte.class || type == Boolean.class || type == String.class){
			
				return true;
		}
		return false;
	}

	public static String encodeObject(Object obj) {
		Object result = null;

		if(obj == null){
			return "";
		}
		
		if(obj instanceof JsonSerializable){
			Logger.debug(Logger.getMethodTrace() + " "+  test ++ +" checking interfaceImpl "+  obj);
			result = ((JsonSerializable) obj).prepareJSON();
		}
		else if(implements_toString(obj)){
			Logger.debug(Logger.getMethodTrace() + " "+  test ++ +" checking toString "+  obj);
			result = obj.toString();
		}
		else{
			Logger.debug(Logger.getMethodTrace() + " "+  test ++ +" all conditions false reflection use "+  obj);
			result = JsonEncoder.serializedFieldsUsingReflection(obj);
//			result = defaultProcessor(result);
		}
		
		return defaultProcessor(result);
	}

	private static boolean implements_toString(Object obj) {
		// TODO Auto-generated method stub
		Method method = null;
		try {
				method = obj.getClass().getDeclaredMethod("toString", null);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			return false;
		}
		if(method != null)
			return true;
		
		return false;
	}
	
	@SuppressWarnings("rawtypes")
	private static Map<String, Object> serializedFieldsUsingReflection(Object obj) {
		// TODO Auto-generated method stub
		Field [] fields;
		if(classRegistry.containsKey(obj.getClass().getName())){
			fields= classRegistry.get(obj.getClass().getName());
		}
		else{
			Class c = obj.getClass();
			fields = c.getDeclaredFields();
		}
		Map<String, Object> map = new HashMap<>();
		
		for(Field d: fields){
			d.setAccessible(true);
			try {
				map.put(d.getName(), encodeObject(d.get(obj)));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				map.put("exception", e);
			}
		}
		return map;
	}
	
	private static String toJson(Object obj) {
		// TODO Auto-generated method stub
		return gson.toJson(obj);
	}

	
	public static void main(String[] args) {
		JsonEncoder jsonEncoder =  new JsonEncoder();
		System.out.println(JsonEncoder.implements_toString(jsonEncoder));
	}
}
