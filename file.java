import java.util.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class Main{
    public static void main(String[] args) {

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
            System.out.println(str);        
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


    }   
}
