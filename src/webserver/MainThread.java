/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver;
import java.io.*;
import java.net.*;
import java.util.*;


/**
 *
 * @author Bala
 */
public class MainThread {
    
       public static HashMap<String,Long> sorted=new HashMap<>();
   public static void main(String argv[]) throws Exception
 {
      
         Scanner in = new Scanner(System.in);
         String path=in.next();
         String line;
         String tid;
         String Transaction="";
        FileReader file = new FileReader(path);
        BufferedReader br = new BufferedReader(file);
        List<Thread> threadList = new ArrayList<Thread>();
        
        while((line = br.readLine()) != null)
                {
                    if(line.contains("start"))
                    {
                      Transaction="";
                      String starttrans[]=line.split(":");
                      tid=starttrans[0];
                      Transaction=tid;
                    }
                    else if(line.contains("end"))
                    {
                      
                      TransactionManager request=new TransactionManager(Transaction);
                      Thread thread = new Thread(request); 
                       threadList.add(thread);
                      thread.start(); 
                      Thread.sleep(1000);
                     
                     
                    }
                    else
                    {
                    Transaction=Transaction+"@"+line;
                  
                    }
                
                }
        
          br.close();
          
     
         for(Thread t : threadList)
         {
          // waits for this thread to die
          t.join();
         }
         
            
             HashMap<String,Long> map2=new HashMap<>();
             Set<String> keySet= Log.map.keySet();
             
          for(String i:keySet)          
          {
             map2.put(i,Log.map.get(i).Timestamp);             
             
          }
          
               Set<String> keySet9= Log.map.keySet();
     /*     for(String i:keySet9)
          {
              System.out.println(i);
              System.out.println(Log.map.get(i).Timestamp);
                 for(int j=0;j<5;j++)
              {
                  System.out.print("\t");
                  System.out.print(Log.map.get(i).FilesRequired[j]);
              }
                 System.out.print("\n");
          }
          
           Set<String> keySet10= Log.map1.keySet();
          for(String i:keySet10)
          {
              System.out.println(i);
             
                 for(int j=0;j<5;j++)
              {
                  System.out.print("\t");
                  System.out.print(Log.map1.get(i).filename[j]);
                   System.out.print("\t");
                  System.out.print(Log.map1.get(i).Operation[j]);
                   System.out.print("\t");
                  System.out.print(Log.map1.get(i).value[j]);
                  System.out.print("\n");
              }
                 System.out.print("\n");
          }
          */
         sorted = sortHashMapByValuesD(map2);    
         
         Set<String> keySet1= sorted.keySet();
           for(String i:keySet1)          
          {
             ExecuteTransaction ET=new ExecuteTransaction(i);     
             
             Thread T=new Thread(ET);
             T.start();
             T.sleep(2000);
          }
                           
     }
   
 public static LinkedHashMap sortHashMapByValuesD(HashMap passedMap) {
   List mapKeys = new ArrayList(passedMap.keySet());
   List mapValues = new ArrayList(passedMap.values());
   Collections.sort(mapValues);
   Collections.sort(mapKeys);

   LinkedHashMap sortedMap = new LinkedHashMap();

   Iterator valueIt = mapValues.iterator();
   while (valueIt.hasNext()) {
       Object val = valueIt.next();
       Iterator keyIt = mapKeys.iterator();

       while (keyIt.hasNext()) {
           Object key = keyIt.next();
           String comp1 = passedMap.get(key).toString();
           String comp2 = val.toString();

           if (comp1.equals(comp2)){
               passedMap.remove(key);
               mapKeys.remove(key);
               sortedMap.put((String)key, (Long)val);
               break;
           }

       }

   }
   return sortedMap;
}
 }

    
