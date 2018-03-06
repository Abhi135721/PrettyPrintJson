import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class sampleCheck {
  public static void main(String[] args) {

    File dummy = new File("src/sampletwo.json");

    JSONParser parser = new JSONParser();
    String indent = "  ";
    boolean compact = false;
    boolean tab = false;
    int tabs = 0;
    boolean replace = false;
    String file = "";
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("--compact")) compact = true;
      else if (args[i].equals("--tab")) tab = true;
      else if (args[i].contains("=")) {
        if (args[i].substring(0, args[i].indexOf("=")).equals("--indent")) {
          tabs =
              Integer.valueOf(
                  (args[i].substring(args[i].indexOf('=') + 1, args[i].length()))
                      .replaceAll(" ", ""));
        }
        if (args[i].substring(0, args[i].indexOf("=")).equals("--from-file")) {
          file = args[i].substring(args[i].indexOf('=') + 1, args[i].length());
          System.out.println(file);
        }
      } else if (args[i].equals("--replace")) replace = true;
      else {
        indent = "  ";
      }
    }
    indent = get_indent(tab, tabs);
    if (tabs == 0) indent = "  ";
    if (compact) indent = "";
    if (replace)
      try {
        PrintStream myConsole = new PrintStream(new FileOutputStream(dummy));
        System.setOut(myConsole);
        myConsole.print("");
      } catch (FileNotFoundException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
    try {
      Object object = parser.parse(new FileReader(file));
      if (object.getClass().getName().equals("org.json.simple.JSONArray")) {
        JSONArray A = (JSONArray) object;
        // System.out.println(A);
        if (compact) System.out.print("[");
        else System.out.println("[");

        for (int i = 0; i < A.size(); i++) {
          System.out.print(indent);
          pretty_object((JSONObject) A.get(i), indent, 1, compact);
          if (i != A.size() - 1) {
            if (compact) System.out.print(",");
            else System.out.println(",");
          } else {
            if (!compact) System.out.println();
          }
        }
        if (compact) System.out.print("]");
        else System.out.println("]");
      } else {

        // convert Object to JSONObject
        JSONObject jsonObject = (JSONObject) object;

        pretty_object(jsonObject, indent, 0, compact);
      }
    } catch (FileNotFoundException fe) {
      fe.printStackTrace();
      System.out.println("There is no such file");
    } catch (Exception e) {
      e.printStackTrace();
    }
    // File file = new File("src/sample1.json");
    if (replace) {
      try {

        FileInputStream instream = new FileInputStream(dummy);
        FileOutputStream outstream = new FileOutputStream("src/sample.json");

        byte[] buffer = new byte[1024];

        int length;
        /*copying the contents from input stream to
         * output stream using read and write methods
         */
        while ((length = instream.read(buffer)) > 0) {
          outstream.write(buffer, 0, length);
        }

        // Closing the input/output file streams
        instream.close();
        outstream.close();

        dummy.delete();
        System.out.println("File copied successfully!!");

      } catch (IOException ioe) {
        ioe.printStackTrace();
      }
    }
  }

  public static String get_indent(boolean tab, int tabs) {
    String indent = "";
    String add_no_of_spaces;
    if (!tab) add_no_of_spaces = " ";
    else add_no_of_spaces = "  ";
    for (int k = 0; k < tabs; k++) {
      indent = indent + add_no_of_spaces;
    }
    return indent;
  }

  public static void pretty_object(
      JSONObject jsonObject, String indent, int check_type, boolean compact) {
    Set<String> keys = jsonObject.keySet();
    String s = jsonObject.toJSONString();
    if (compact) {
      System.out.print("{");
      // myConsole.print("{");
    } else System.out.println('{');
    int num_keys = keys.size();
    int no_key = 0;
    String x = indent;
    if (check_type == 1) x = indent + indent;
    for (String i : keys) {
      if (jsonObject.get(i).getClass().getName().equals("org.json.simple.JSONArray")) {
        if (compact) System.out.print(x + "\"" + i + "\"" + ":");
        else System.out.print(x + "\"" + i + "\"" + " : ");
        printArray((JSONArray) jsonObject.get(i), x, compact);
      } else if (jsonObject.get(i).getClass().getName().equals("java.lang.String")) {
        if (compact) System.out.print(x + "\"" + i + "\"" + ":" + "\"" + jsonObject.get(i) + "\"");
        else System.out.print(x + "\"" + i + "\"" + " : " + "\"" + jsonObject.get(i) + "\"");
      } else {
        if (compact) System.out.print(x + "\"" + i + "\"" + ":" + jsonObject.get(i));
        else System.out.print(x + "\"" + i + "\"" + " : " + jsonObject.get(i));
      }
      no_key++;
      if (no_key != num_keys) {
        if (compact) System.out.print(",");
        else System.out.println(",");
      } else if (!compact) {
        System.out.println();
      }
    }
    if (check_type == 0) System.out.print('}');
    else System.out.print(indent + "}");
    // System.out.println(jsonObject);
  }

  public static void printArray(JSONArray A, String indent, boolean compact) {
    if (compact) System.out.print("[");
    else System.out.print(" [");
    for (int i = 0; i < A.size(); i++) {
      if (!compact) System.out.println();
      if (A.get(i).getClass().getName().equals("java.lang.String")) {
        A.set(i, "\"" + A.get(i) + "\"");
      }
      if (i != A.size() - 1) System.out.print(indent + indent + A.get(i) + ",");
      else System.out.print(indent + indent + A.get(i));
    }
    if (!compact) System.out.println();
    System.out.print(indent + "]");
  }
}
