/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
/*
 *
 * @author Bala
 */
public class Worker implements Runnable {
  
    String user;
    String operation;
    String value;
    String Path;
    String Trname;
    
    public Worker(String tn,String user,String operation,String value,String Path)
    {
    this.user=user;
    this.operation=operation;
    
    this.value=value;
    this.Path=Path;
    this.Trname=tn;
        
    }
    
    public void run() 
    {    
               long StartTime = System.nanoTime();  
            
               String FilePath=this.Path;
               String Line;
               String Data="From Cache:";
               
               Cache cache=new Cache(); 
               
            if(this.operation.equals("GET"))
            {
                       
             if(1==0)
             {
                  String CacheData;
                  CacheData=cache.GetData(FilePath);
                  System.out.println(CacheData);
                   
             }
             else{
                   FileReader file=null;            
               try{
                  
                 file = new FileReader(FilePath);
               }catch(Exception e){
                       
                   System.out.println("File Not Found");
                 }
             
               BufferedReader br = new BufferedReader(file);
               
               try
               {              
                   while((Line = br.readLine()) != null)
               {
                   System.out.println(this.Trname+":"+this.Path+":"+Line);
                   Data=Data+" "+Line;
                   
               }
                   cache.InsertFile(FilePath, Data);
                  
               }
               catch(Exception e)
               {
                    System.out.println(e);
               }
              
             } 
              
           }
          else if(this.operation.equals("PUT"))
          {
            try
               {
                   String Delim="/";
                   String content=value;
                   String[] files=this.Path.split(Delim);
                   String[] filpath=this.Path.split(files[files.length-1]);
                  int q= method(filpath[0],files[files.length-1],content);
                  if(q!=0)
                  {
                   AddPermissions( this.Path,this.user);
                  }
                  
               }
            catch(Exception e)
            {
                System.out.println(e);
            }
          }
            
             long EndTime = System.nanoTime();
                long time=EndTime-StartTime;
               
        
    }
    
    
    public int method(String path,String filename,String content) throws IOException
    {
     int i=1;
     String complete=path +"/"+filename;  
     File fpath=new File(path);
     if(!fpath.exists())
     {
         fpath.mkdirs();
         
         System.out.println(" directories "+path+" is created");
     }
     else
     {
        
     }
   
     File pfilename = new File(complete);
      if(pfilename.createNewFile())
      {
          System.out.print(this.Trname+":"+complete+" File Created");
         
           
        try{
            BufferedWriter out = new BufferedWriter(new FileWriter(complete)); 
            out.write(content);
            out.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
      }
     else
     {
            i=0;
          
        try{
           FileWriter out = new FileWriter(complete);
           out.write(content);
           out.close();
           }catch(IOException e){
        //Oh, no! Failed to create PrintWriter
        System.out.println("cannot be updated");
            }

    //After successful creation of PrintWriter
           System.out.println(this.Trname+":"+complete+" File Updated");
          
    }
      return i;
    }

    public void AddPermissions(String path1,String user1) throws IOException
    {
        
        String Delimits="-";
         FileReader file = new FileReader("Groups.txt");
         
         String Line;   
         String GroupLine=null;
                BufferedReader br = new BufferedReader(file);
                  
                while((Line = br.readLine()) != null)
                {
                String[] group=Line.split(Delimits);
                for(int k=0;k<group.length;k++)
                {
                    if(group[k].equals(user1))
                    {
                        GroupLine=Line;
                    }
                }
                }
                
                String text=path1+"-"+GroupLine;
           PrintWriter appendout = null;
            BufferedWriter bufWriter;
           

    try{
        bufWriter =Files.newBufferedWriter(
                Paths.get("NewFiles.txt"),
                Charset.forName("UTF8"),
                StandardOpenOption.WRITE, 
                StandardOpenOption.APPEND,
                StandardOpenOption.CREATE);
        appendout = new PrintWriter(bufWriter, true);
    }
    catch(IOException e){
        //Oh, no! Failed to create PrintWriter
        System.out.println("cannot be appended");
    }

    //After successful creation of PrintWriter
    appendout.println(text);
 appendout.close();
    }
}
