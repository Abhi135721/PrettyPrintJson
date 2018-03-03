import java.io.FileReader;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

public class sampleCheck {
	public static void main(String [] args){
		JSONParser parser = new JSONParser();
		try
        {
            Object object = parser.parse(new FileReader("src/sample.json"));
            
            //convert Object to JSONObject
            JSONObject jsonObject = (JSONObject)object;
            Set<String> keys = jsonObject.keySet();
            String s = jsonObject.toJSONString();
            String indent = "    ";
            if(s.charAt(0) == '{')
            	System.out.println('{');
            
            for(String i : keys){
            	if(jsonObject.get(i).getClass().getName().equals("org.json.simple.JSONArray")){
            		System.out.print(indent + "\"" + i + "\"" + " : " );
            		printArray((JSONArray)jsonObject.get(i) , indent);
            	}
            	else if (jsonObject.get(i).getClass().getName().equals("java.lang.String")){
            		System.out.print(indent + "\"" + i + "\"" + " : " +"\"" +  jsonObject.get(i) + "\"" );
            	}
            	else{
            		System.out.print(indent + "\"" + i + "\"" + " : " +  jsonObject.get(i));
            	}
            	System.out.println();
            }
            System.out.println('}');
            System.out.println(jsonObject);
        }
		catch(FileNotFoundException fe){
			fe.printStackTrace();
			System.out.println("There is no such file");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void printArray(JSONArray A, String indent){
		System.out.print(" [");
		for(int i = 0 ; i < A.size() ; i++){
			System.out.println();
			if(A.get(i).getClass().getName().equals("java.lang.String")){
				A.set(i, "\"" + A.get(i) + "\"");
			}
			if(i != A.size() - 1)
				System.out.print(indent + indent + A.get(i) + ",");
			else
				System.out.print(indent + indent + A.get(i));
		}
		System.out.println();
		System.out.print(indent + "],");
	}
}
