import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;




public class Main{
    public static void main(String[] args) throws IOException{

        //file to List string
        List<String> lines = Files.readAllLines(Paths.get("./tabular/qwe.txt"));         
        System.out.println(lines.size());
        System.out.println(lines.get(1));

        //all to string
        String fileToStringall = Files.readString(Paths.get("./tabular/qwe.txt"));         
        System.out.println(fileToStringall);

        //scanner
        Scanner scanner = null;
        try{
            scanner = new Scanner(new File("./tabular/qwe.txt"));   
            scanner.useDelimiter("\n");
        }catch(Exception e){
            e.printStackTrace();
        }
              
        while (scanner.hasNext()) {            
            String str = scanner.next();            
            //System.out.println(str);        
        }
        scanner.close();

        //split
        String str = "a,b,c,d,e,f,g,h";
        String[] strArr = str.split(",");

        //string contains, compare
        System.out.println(str.contains("a"));
        System.out.println(str.compareTo("a,b,c,d,e,f,g,h") == 0);


        for (int i=0; i<strArr.length;i++){
            System.out.println(strArr[i]);
        }



        //strToInt
        String a = "123111";
        int aa = Integer.parseInt(a);
        String aaa = Integer.toString(aa);
        System.out.println(aaa);
        
        //gson
        Gson gson = new Gson();
    	JsonObject jsonObject = new JsonObject();
    	jsonObject.addProperty("name", "anna");
    	jsonObject.addProperty("id", 1);
    	String jsonStr = gson.toJson(jsonObject);
    	System.out.println(jsonStr);
    	
    	String jsonedStr = "{\"id\":1,\"name\":\"Anna\"}";
    	Student student = gson.fromJson(jsonedStr, Student.class);
    	System.out.println(student.toString());


    }   
        //read function
    public static String[] getTxtFromFile(String path) throws Exception{
    	List<String> lines = Files.readAllLines(Paths.get(path));
    	String[] array = lines.toArray(new String[0]);
    	return array;
    }
    
    //getlist function
    public static String[] getFilesList(String path) throws Exception{
    	List<String> results = new ArrayList<String>();
    	File[] files = new File(path).listFiles();
    	for (File file : files) {
    	    if (file.isFile()) {
    	        results.add(file.getName());
    	    }
    	}
    	String[] array = results.toArray(new String[0]);
    	return array;
    }
}
