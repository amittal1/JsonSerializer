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
import com.ib.client.CommissionReport;
import com.ib.client.OrderState;

public class EncoderTest {
	Map<Integer, Object> map = new HashMap<>();
	
	public static void printFromJsonEncoder(Object obj){
		System.out.println("here");
		System.out.println(JsonEncoder.serializeToJson(obj));;
	}
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		EncoderTest e = new EncoderTest();
		//Map<Integer, Object> map = new HashMap<>();
		for(int i=0; i<5; i++){
			List<Object> list = new ArrayList<>();
			list.add("hello "+i);
			e.map.put(i++, list);
		}
		System.out.println(e.map.toString());
		System.out.println(JsonEncoder.serializeToJson(e));
		
		
		
		
		//Tested over these classes but are not part of the project, thus commented and just for reading
		/*System.out.println(JsonEncoder.serializeToJson(e.map));
		
		//JsonEncoder.registerClass(IBMessage.class, "values", "messagekey_");

		IBMessage m= new IBMessage("test");
		m.add("hello", "world");
		m.add("key", "value");
		m.add("mittal", "ankit" );
		System.out.println(JsonEncoder.serializeToJson(m));

*/		
		
		
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
		/*
		 * 		JsonEncoder.registerClass(CommissionReport.class, "m_execId", "m_commission", 
				"m_currency","m_realizedPNL", "m_yield", "m_yieldRedemptionDate");
		
		CommissionReport cmm = new CommissionReport();
		cmm.m_commission = 100;
		cmm.m_currency = "10";
		cmm.m_execId= "1";
		cmm.m_realizedPNL = 10.988;
		cmm.m_yield = 1.1;
		cmm.m_yieldRedemptionDate = 06012017;
		
		System.out.println(JsonEncoder.serializeToJson(cmm));

		 */
	}
	
}
