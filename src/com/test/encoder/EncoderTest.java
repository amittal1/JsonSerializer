package com.test.encoder;

import java.lang.reflect.InvocationTargetException;

/**
 * Author: Ankit Luv Mittal
 * Created: 05/31/2017
 * Description: Testing Serialize Project
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Serialize.encoder.JsonEncoder;
import com.google.gson.Gson;
import com.test.testUtils.Message;

public class EncoderTest {
	Map<Integer, Object> map = new HashMap<>();
	
	public static void printFromJsonEncoder(Object obj){
		System.out.println("here");
		System.out.println(JsonEncoder.serializeToJson(obj));;
	}
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Gson gson =  new Gson();
		EncoderTest e = new EncoderTest();
		//Map<Integer, Object> map = new HashMap<>();
		for(int i=0; i<5; i++){
			List<Object> list = new ArrayList<>();
			list.add("hello "+i);
			e.map.put(i++, list);
		}
		System.out.println(e.map.toString());
		System.out.println(JsonEncoder.serializeToJson(e));
		System.out.println("gson json: "+gson.toJson(e));
		System.out.println(gson.toJson(JsonEncoder.serializeToJson(e)));
		
		
		
		System.out.println(JsonEncoder.serializeToJson(e.map));
		Message m= new Message("test");
		m.add("hello", "world");
		m.add("key", "value");
		m.add("mittal", "ankit" );
		Message m2= new Message("test2");
		m2.add("hello2", "world2");
		m2.add("key2", "value2");
		m2.add("mittal2", "ankit2" );
		m.add("anotherMessage", m2);
		System.out.println(JsonEncoder.serializeToJson(m));

		
		
		//Tested over these classes but are not part of the project, thus commented and just for reading		
		/*JsonEncoder.registerClass(OrderState.class, "m_status",  "m_initMargin", 
						"m_maintMargin", "m_equityWithLoan", "m_commission", "m_minCommission", 
						"m_maxCommission", "m_commissionCurrency", "m_warningText");
		
		OrderState cmm = OrderState.class.getConstructor(null).newInstance(null);
		cmm.commission(100);
		cmm.warningText("yoooooo");
		cmm.initMargin("initMarginVal");
		cmm.maintMargin("mainVal");;
		cmm.maxCommission(100.00);
		
		System.out.println(JsonEncoder.serializeToJson(cmm));
		*/
	
	}
	
}
