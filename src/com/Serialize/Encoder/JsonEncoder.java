package com.Serialize.Encoder;


/**
 * Author: Ankit Luv Mittal
 * Created: 05/31/2017
 * Description: Encode any object to json
 * Dependency:Libraries
 * 				1. Gson Library: Provided by google for converting objects to json
 */

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.lang.model.type.PrimitiveType;

import com.Serialize.Interface.Serializable;
import com.google.gson.Gson;

public class JsonEncoder {
	static private Map <String, Field []> classRegistry;
	static private Gson gson =  new Gson();
	
	private static String toJson(Object obj) {
		// TODO Auto-generated method stub
		
		return "";
	}

	public static String defaultProcessor(Object obj) {
		// TODO Auto-generated method stub
		if(obj instanceof PrimitiveType){
			return toJson(String.valueOf(obj));
		}
		return "";
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
				map.put(d.getName(), d.get(obj));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				map.put("exception", e);
			}
		}
		return map;
	}
	
	public static String encodeObject(Object obj) {
		Object result = null;

		if(obj == null){
			result = obj;
		}
		
		if(obj instanceof Serializable){
			result = ((Serializable) obj).prepareJSON();
		}
		else if(implementToString(obj)){
			result = obj.toString();
		}
		else{
			result = JsonEncoder.serializedFieldsUsingReflection(obj);
		}
		
		return toJson(result);
	}

	private static boolean implementToString(Object obj) {
		// TODO Auto-generated method stub
		Method method;
		try {
			method = obj.getClass().getDeclaredMethod("toString", null);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			return false;
		}
		return true;
	}
	
	
	public static void main(String[] args) {
		JsonEncoder jsonEncoder =  new JsonEncoder();
		System.out.println(JsonEncoder.implementToString(jsonEncoder));
	}
}
