package com.test.encoder;

/**
 * Author: Ankit Luv Mittal
 * Created: 05/31/2017
 * Modified: 06/23/2017
 * Description: Testing Serialize Project
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Serialize.encoder.JsonEncoder;
import com.google.gson.Gson;
import com.test.testUtils.CachedCommand;
import com.test.testUtils.CommissionReport;
import com.test.testUtils.JsonSerializableExample;
import com.test.testUtils.Message;
import com.test.testUtils.OrderState;

public class EncoderTest {
	Map<Integer, Object> map = new HashMap<>();
	
	public static void printFromJsonEncoder(Object obj){
		System.out.println("here");
		System.out.println(JsonEncoder.serializeToJson(obj));;
	}
	
	public static void main(String[] args) throws Exception {
		EncoderTest e = new EncoderTest();
		for(int i=0; i<5; i++){
			List<Object> list = new ArrayList<>();
			list.add("hello "+i);
			e.map.put(i++, list);
		}
		System.out.println("EncoderTest Object");
		System.out.println(JsonEncoder.serializeToJson(e));
		System.out.println("Map");
		System.out.println(JsonEncoder.serializeToJson(e.map));
	    
		Message m= new Message("test");
		m.add("hello", "world");
		m.add("key", "value");
		m.add("fruit", "apple" );
		Message m2= new Message("test2");
		m2.add("hello2", "world2");
		m2.add("key2", "value2");
		m2.add("fruit2", "apple2" );
		m.add("anotherMessage", m2);
		System.out.println("Nested Messages");
		System.out.println(JsonEncoder.serializeToJson(m));

		//Registering a class with classRegistry of JsonEncoder with specific fields 
		//to get those only
		JsonEncoder.registerClass(OrderState.class, "m_status",  "m_initMargin", 
						"m_maintMargin", "m_equityWithLoan", "m_commission", "m_minCommission", 
						"m_maxCommission", "m_commissionCurrency", "m_warningText");
		
		OrderState orderState = new OrderState();
		orderState.commission(100);
		orderState.warningText("warning");
		orderState.initMargin("initMarginVal");
		orderState.maintMargin("mainVal");;
		orderState.maxCommission(100.00);
		System.out.println("OrderState");
		System.out.println(JsonEncoder.serializeToJson(orderState));
		
		JsonEncoder.registerClass(CommissionReport.class, "m_execId", "m_commission", 
				"m_currency", "m_realizedPNL", "m_yield", "m_yieldRedemptionDate");
		
		CommissionReport commissionReport = new CommissionReport();
		commissionReport.m_execId = "1";
		commissionReport.m_commission = 100.0;
		commissionReport.m_currency = "USD";
	    commissionReport.m_realizedPNL = 10.0;
	    commissionReport.m_yield = 1.2;
	    commissionReport.m_yieldRedemptionDate = 06232017;
	    System.out.println("CommissionReport");
		System.out.println(JsonEncoder.serializeToJson(commissionReport));
		
		JsonSerializableExample example = new JsonSerializableExample(212, "Mark", "Manager");
		example.setSalary(10000);
		System.out.println("JsonSerializableExample");
		System.out.println(JsonEncoder.serializeToJson(example));
		
	
	}
	
}
