package com.test.encoder;

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

public class EncoderTest {
	Map<Integer, Object> map = new HashMap<>();
	
	
	public static void main(String[] args) {
		EncoderTest e = new EncoderTest();
		//Map<Integer, Object> map = new HashMap<>();
		for(int i=0; i<5; i++){
			List<Object> list = new ArrayList<>();
			list.add("hello "+i);
			e.map.put(i++, list);
		}
		//System.out.println(map.toString());
		
		System.out.println(JsonEncoder.serializeToJson(e.map));
	}
	
}
