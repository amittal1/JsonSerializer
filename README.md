# JsonSerializer

## SYNOPSIS
Serialize any JAVA object into JSON format.

## CODE EXAMPLE
Message class contains a method called add() with two parameters key and value stored in a map internally.
		Message message1= new Message("test");
		message1.add("hello", "world");
		message1.add("key", "value");
		message1.add("mittal", "ankit" );
		Message message2= new Message("test2");
		message2.add("message2", "Hello World!");
		message2.add("EnclosingObject", "message1" );
		message1.add("anotherMessage", m2);
		System.out.println(JsonEncoder.serializeToJson(m));

## MOTIVATION
Working on my project Iyka Trading System, I felt need of a Json Serializer in Java which doen't provide support for it.
I tried some online libraries and examples but it was not clean to work with them and I had to do some complex things. 
I prefer clean code to make it readable and easily debuggable. Therefore, I decided to create this open-source project. 
I will keep updating it with improvements with time.

## INSTALLATION
	1.	Download the zip file and unzip it
	2.	Add it to your project build path and start using it.

## USES
Its an open-source library to be used in cases such as:
  1. Serialize any object into Json format without any cumbersome work.
  2. Serialize all fields of an object.
  3. Serialize selected fields of an object.
  4. Use it for transfer an object as a JSON object over network.
  5. If you need to serialize a class object at multiple places in your project with specific fields, 
     just implement the interface JsonSerializable and its done.

## Getting Started with the JsonSerializer: 
  1.  If you don't want all the fields in your object to be serialized
          i.  Implement interface JsonSerializable and return an object of fields (with field names or without as needed) 
              from method prepareJSON(). 
  2.  Call JsonEncoder.serializeToJson() method with your object as parameter and get result.

## CONTRIBUTE
Contributions are always welcome! Please go back and read the contribution guidelines first.


### Version 1.0
