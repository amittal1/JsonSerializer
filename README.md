# JsonSerializer

## SYNOPSIS
Serialize ANY JAVA object into JSON format.

## CODE EXAMPLE
Message class contains a method called add() with two parameters key and value stored in a map internally.
```
		//Using objects
		Message m= new Message("test");
		m.add("hello", "world");
		m.add("key", "value");
		m.add("fruit", "apple" );
		Message m2= new Message("test2");
		m2.add("hello2", "world2");
		m2.add("key2", "value2");
		m2.add("fruit2", "apple2" );
		m.add("anotherMessage", m2);
		
		//Execute this single serializeToJson method with the object, you want to serialize
		System.out.println("Nested Messages");
		System.out.println(JsonEncoder.serializeToJson(m));
```

## MOTIVATION
Working on my project Iyka Trading System, I felt need of a JSON Serializer in Java which doen't provide support for it.
I tried some online libraries and examples but it was not clean to work with them and they pass some other internal objects of the library.So, to avoid it, I had to do some complex things. 
I prefer clean code to make it readable and easily debuggable. Therefore, I decided to create this open-source project. 
I will keep updating it with improvements with time.

## INSTALLATION
	1.	Download jar file and zip of source code.
	2.	Add it to your project build path, link it with the source code and start using it.

## USES
Its an open-source library to be used in cases such as:
  1. Serialize any object into JSON format without any cumbersome work.
  2. Serialize all fields of an object.
  3. Serialize selected fields of an object.
  4. Transfer an object as a JSON object over network.
  5. If you need to serialize a class object at multiple places in your project with specific fields, 
     just implement the interface JsonSerializable and its done.

## Getting Started with the JsonSerializer: 
  1.  If you don't want all the fields in your object to be serialized
 	i.  Implement interface JsonSerializable and return an object of fields (with field names or without as needed) 
            from method prepareJSON(). 
	      
```
	public class JsonSerializableExample implements JsonSerializable{

		private static int count=0;

		private int id;
		private String position;
		private String name;
		private double salary;

		private String otherField;
		private int nonReqdField;
		private char [] inheritedNonReqdField;

		public JsonSerializableExample(int id, String name, String position) {
			count++;
			this.id =id;
			this.name =name;
			this.position =position;
		}

		@Override
		public Object prepareJSON() {
			Map<String, Object> map = new HashMap<>();
			//Add fields you need with their name as key
			map.put("position", position);
			map.put("id", id);
			map.put("name", name);
			map.put("salary", salary);
			return map;
		}
	}
```
	ii. Use registerClass method of JsonEncoder to use only selected fields by passing the same's name as parameters to the method.
```
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
```

  2.  Call JsonEncoder.serializeToJson() method with your object as parameter and get result as String formatted as JSON Object.
## NOTE: 
* If You face this "exception in getting field: FieldName" as one of the keys in your JSON Object, then it means you passed the wrong FieldName while registering the class with registerClass method. Correct it.

## CONTRIBUTE
Contributions are always welcome! Please go back and read the [CONTRIBUTION.md](https://github.com/amittal1/JsonSerializer/blob/master/CONTRIBUTION.md) guidelines first.


### Version 2.0
