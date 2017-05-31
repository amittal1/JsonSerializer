package com.Serialize.Encoder;


/**
 * Author: Ankit Luv Mittal
 * Created: 05/31/2017
 * Description: Encode any objecct to json
 */

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.Serialize.Interface.Serializable;

public class JsonEncoder {
	Map <String, List<Object>> classRegistry;
	
	private String toJson(Object obj) {
		// TODO Auto-generated method stub

		return "";
	}

	@SuppressWarnings("rawtypes")
	private Map<String, Object> parseFields(Object obj) {
		// TODO Auto-generated method stub
		Class c = obj.getClass();
		Field [] f = c.getDeclaredFields();
		Map<String, Object> map = new HashMap<>();
		
		for(Field d: f){
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
	
	public String encodeObject(Object obj) {
		Object result = null;

		if(obj == null){
			result = obj;
		}
		
		if(obj instanceof Serializable){
			result = ((Serializable) obj).prepareJSON();
		}
		if(obj){
			fields = parseFields(obj);
		}
		fields.forEach((k,v) -> {
			v=toJson(v);
		});
		
		
		
		return "";
	}
}
