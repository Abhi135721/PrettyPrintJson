import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

public class sampleCheck {
	public static void main(String [] args){
		JSONParser parser = new JSONParser();
		String indent;
		indent = "  ";
		boolean compact = false;
		if(compact)
			indent = "";
		try
        {
			Object object = parser.parse(new FileReader("src/sample.json"));
            if(object.getClass().getName().equals("org.json.simple.JSONArray")){
            	JSONArray A = (JSONArray)object;
            	//System.out.println(A);
            	if(compact)
            		System.out.print("[");
            	else
            		System.out.println("[");
            	
            	for(int i = 0 ; i < A.size() ; i++){
            		System.out.print(indent);
            		pretty_object((JSONObject)A.get(i) , indent , 1 , compact);
            		if(i != A.size() - 1){
            			if(compact)
                    		System.out.print(",");
            			else
            			System.out.println(",");
            		}
            		else{
            			if(!compact)
            				System.out.println();
            		}
            	}
            	if(compact)
            		System.out.print("]");
            	else
            		System.out.println("]");
            }
            
			else{
            	
	            //convert Object to JSONObject
	            JSONObject jsonObject = (JSONObject)object;
	            
	            pretty_object(jsonObject , indent , 0 , compact);
            }
        }
		catch(FileNotFoundException fe){
			fe.printStackTrace();
			System.out.println("There is no such file");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		//File file = new File("src/sample1.json");
		try{
			PrintStream printStream = new PrintStream(new FileOutputStream("sample1.json"));
			System.setOut(printStream);
		}
		catch(FileNotFoundException fe){
			System.out.println("There is no such file to write");
		}
	}
	public static void pretty_object(JSONObject jsonObject, String indent , int check_type , boolean compact){
		Set<String> keys = jsonObject.keySet();
        String s = jsonObject.toJSONString();
        if(compact)
    		System.out.print("{");
        else
        	System.out.println('{');
        int num_keys = keys.size();
        int no_key = 0;
        String x= indent;
        if(check_type == 1)
        	x = indent + indent;
        for(String i : keys){
        	if(jsonObject.get(i).getClass().getName().equals("org.json.simple.JSONArray")){
        		if(compact)
        			System.out.print(x + "\"" + i + "\"" + ":" );
        		else
        			System.out.print(x + "\"" + i + "\"" + " : " );
        		printArray((JSONArray)jsonObject.get(i) , x , compact);
        	}
        	else if (jsonObject.get(i).getClass().getName().equals("java.lang.String")){
        		if(compact)
        			System.out.print(x + "\"" + i + "\"" + ":" +"\"" +  jsonObject.get(i) + "\"" );
        		else
        			System.out.print(x + "\"" + i + "\"" + " : " +"\"" +  jsonObject.get(i) + "\"" );
        	}
        	else{
        		if(compact)
        			System.out.print(x + "\"" + i + "\"" + ":" +  jsonObject.get(i));
        		else
        			System.out.print(x + "\"" + i + "\"" + " : " +  jsonObject.get(i));
        	}
        	no_key++;
        	if(no_key != num_keys){
        		if(compact)
            		System.out.print(",");
        		else
        			System.out.println(",");
        	}
        	else
        		if(!compact){
        			System.out.println();
        		}
        }
        if(check_type == 0)
        	System.out.print('}');
        else
        	System.out.print(indent + "}");
        //System.out.println(jsonObject);
	}
	public static void printArray(JSONArray A, String indent , boolean compact){
		if(compact)
			System.out.print("[");
		else
			System.out.print(" [");
		for(int i = 0 ; i < A.size() ; i++){
			if(!compact)
				System.out.println();
			if(A.get(i).getClass().getName().equals("java.lang.String")){
				A.set(i, "\"" + A.get(i) + "\"");
			}
			if(i != A.size() - 1)
				System.out.print(indent + indent + A.get(i) + ",");
			else
				System.out.print(indent + indent + A.get(i));
		}
		if(!compact)
			System.out.println();
		System.out.print(indent + "]");
	}
}
