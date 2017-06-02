/**
 * Created by atul on 5/2/2017.
 * Modified on 05/12/2017 by Ankit Luv Mittal
 * Description: This is to wraps the response from IBGateway in one format and pass it to Client/User.
 */

package com.test.testUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Serialize.encoder.JsonEncoder;
import com.google.gson.Gson;

public class Message {
    private Object messagekey_=null;
    private Map <String, List<Object>> values_ ;
    private static final Gson gson = new Gson();
    private static Message emptyMessage = new Message("empty");

    static{
    	JsonEncoder.registerClass(Message.class, "messagekey_", "values_");
    }
    
    public static Message getEmptyMessage() {
    	emptyMessage.clear();
		return emptyMessage;
	}

	public Message(Object messagekey){
    	this.messagekey_ = messagekey;
    	this.values_ = new HashMap<String, List<Object>>();
    }

	/*public static Message getCustomExceptionMessage(IykaException iykaException){
		emptyMessage.add("error", iykaException);
		return emptyMessage;
	}*/
	
	public Map<String, List<Object>> getMap(){
		return values_;
	}

    public Object getKey(){
        return messagekey_;
    }
    public void addAll(Object ... args){
    	int i =0;
    	while( i < args.length-1){
    		add((String)args[i++], args[i++]);
    	}
    }
    
    public void clear(){
    	values_.clear();
    }

    @SuppressWarnings("rawtypes")
	private void removeOldValues(Object ... args) throws Exception{
    	//Getting the index of argument in the list of values, based on which it will be updated
    	if( !values_.containsKey(args[0]) || !((List)values_.get(args[0])).contains(args[1]))
    		return;
    	int index = values_.get(args[0]).indexOf(args[1]);
    	//Removing all values at the same index
    	for (Map.Entry<String, List<Object>> entry: values_.entrySet() ) {
			List<Object> temp = entry.getValue();
			if(temp.size()>index)
				temp.remove(index);
		}
	}
    
    public void updateAll(Object ... args){
    	try {
			removeOldValues(args);

			//Adding new values
	    	int i =0;
	    	while( i < args.length-1){
	    		add((String)args[i++], args[i++]);
	    	}
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public boolean containsPair(String key, Object value) {
    	if(!values_.containsKey(key))
    		return false;
 
    	return values_.get(key).contains(value);

	}
    
    public void update(String key, Object value){
    	if(values_.containsKey(key)){
    		values_.get(key).get(0);
    		values_.get(key).add(0, value);
    		return;
		}
    	add(key, value);
	}
	
	/*public String toString(){
		return values_.toString();
	}*/
	
	@SuppressWarnings("unchecked")
	public void setMap(String json){
		values_ = gson.fromJson(json, Map.class);
	}
	
 	public String toJSON() {
 		return gson.toJson(values_).toString();
 	}
 	
	public void add(String key, Object value){
		if (values_.containsKey(key))
			values_.get(key).add(value);
		else{
			List<Object> list = new ArrayList<Object>();
			list.add(value);
			values_.put(key, list);
			}
	}

	public List<Object> get(String key) {
		// TODO Auto-generated method stub
		if(!values_.containsKey(key))
			return null;
		
		return values_.get(key);
	}

}
