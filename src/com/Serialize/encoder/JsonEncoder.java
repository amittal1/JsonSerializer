package com.Serialize.encoder;


/**
 * Author: Ankit Luv Mittal
 * Created: 05/31/2017
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
import java.util.Map;

import com.Serialize.Interface.JsonSerializable;
import com.google.gson.Gson;
import com.ib.client.CommissionReport;
import com.ib.client.Order;
import com.ib.client.OrderState;
import com.test.encoder.Logger;

public class JsonEncoder {
	static private Map <String, Field []> classRegistry;
	static private Gson gson;
	
	static{
		classRegistry = new HashMap<>();
		gson =  new Gson();
		
		//Registering CommissionReport class
		registerClass(CommissionReport.class, "m_execId", "m_commission", 
				"m_currency","m_realizedPNL", "m_yield", "m_yieldRedemptionDate");

		//Registering OrderState class
		registerClass(OrderState.class, "m_status",  "m_initMargin", 
				"m_maintMargin", "m_equityWithLoan", "m_commission", "m_minCommission", 
				"m_maxCommission", "m_commissionCurrency", "m_warningText");
		
		//Registering Order class
		registerClass(Order.class, "m_clientId", "m_orderId", "m_permId", 
					"m_parentId", "m_action", "m_totalQuantity", "m_displaySize", 
					"m_orderType", "m_lmtPrice", "m_auxPrice", "m_tif", "m_account");
	}
	
	//Register class in class registry
	//returns true if registered correctly 
	//false if any field is not found or already present in classRegistry
	@SuppressWarnings("rawtypes")
	public static boolean registerClass(Class c,  String ...fieldNames ) {

		Field [] fields = new Field [fieldNames.length];
		
		for (int i = 0; i < fieldNames.length; i++) {
			try {
				fields[i] = c.getDeclaredField(fieldNames[i]);
			} catch (NoSuchFieldException | SecurityException e) {
				// TODO Auto-generated catch block
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
		/*else if(obj instanceof String){
			result = obj.toString();
		}*/
		else if(obj instanceof Collection || obj instanceof Map){
			 
			Object temp_result;
			if(obj instanceof Map){
					temp_result = new HashMap<>();
				((Map) obj).forEach((k,v) -> {
					//if key is not a string ignore it
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
			result = temp_result.toString();
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

	private static String encodeObject(Object obj) {
		Object result = null;

		if(obj == null){
			return "";
		}
		
		if(obj instanceof JsonSerializable){
			result = ((JsonSerializable) obj).prepareJSON();
		}
		else if(implements_toString(obj)){
			result = obj.toString();
		}
		else{
			result = JsonEncoder.serializedFieldsUsingReflection(obj);
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
	
	private static Map<String, Object> serializedFieldsUsingReflection(Object obj) {
		// TODO Auto-generated method stub
		Field [] fields;
		if(classRegistry.containsKey(obj.getClass().getName())){
			Logger.log(Logger.getMethodTrace()+" From classRegistry");
			fields= classRegistry.get(obj.getClass().getName());
		}
		else{
			fields = getFields(obj.getClass());
		}
		Map<String, Object> map = new HashMap<>();
		
		for(Field d: fields){
			Logger.log(Logger.getMethodTrace()+" From for loop "+fields.length);
			Logger.log("field: "+d.getName());
			d.setAccessible(true);
			try {
				Logger.log(Logger.getMethodTrace()+" From try");
				map.put(d.getName(), defaultProcessor(d.get(obj)));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				Logger.log(Logger.getMethodTrace()+" From catcch");
				e.printStackTrace();
				map.put("exception", e);
			}
		}
		Logger.log(Logger.getMethodTrace()+" From end ");
		return map;
	}
	
	private static String toJson(Object obj) {
		// TODO Auto-generated method stub
		return gson.toJson(obj);
	}

}
