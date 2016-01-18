/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.net.*;
import java.util.*;
/**
 *
 * @author Bala
 */
public class Resolver implements Runnable{
 
    String user;
    String Operation;
    String filename;
    String value;
    String tid;
    long Timestamp;
 
     public Resolver(String trid,long timestamp,String line) 
 {
      
            String delims = "-";
            String[] tokens = line.split(delims);
             this.user=tokens[0];
           this.Operation =tokens[1];
           if(tokens[1].equals("PUT"))
                   {
           this.value=tokens[3];
                   }
           else
           {
               this.value="GET";
           }
            this.filename=tokens[2];  
            this.tid=trid;
            this.Timestamp=timestamp;
            
            
             
 }
    public void run()
    {
       
             
             boolean ok=true;
             
            try{
           // fid=GetFileIdentifier(filename,Operation);
             
              ok= CheckPermissions(user,Operation,filename);
            }
            catch(Exception e){
                System.out.println("Permissions or Groups File Not Found");
            }
                    
           if(ok)
           {
                                             
              Log log= new Log(this.tid,this.Timestamp,this.user,this.Operation,this.value,this.filename);
                 
            //Worker w=new Worker(this.user,this.Operation,this. value,this.filename);
    
               //Create new thread to process
              try
              {
                           
                
              }
              catch(Exception e)
              {
                  System.out.println("File not found");
              }
                      
           }
                else
                {
                    //aborting transaction
                    Log.map1.remove(tid);
                    Log.map.remove(tid);
                    System.out.print("Transaction "+tid+" aborted because "+user+" doesn't have permission to "+Operation+ " " +filename);
                                             
                }
            
    }
    
   /* public int GetFileIdentifier(String FileName,String operation) throws Exception
    {
        int fid1=0;
        String line;
        int maxfid=0;
        FileReader file = new FileReader("fileIdentifier.txt");
        BufferedReader br = new BufferedReader(file);
        while((line = br.readLine()) != null)
           {
               String[] splited = line.split(" ");
               if(FileName==splited[0])
               {              
                fid1= Integer.parseInt(splited[1]);
               }
               if(maxfid<Integer.parseInt(splited[1]))
               {
                   maxfid=Integer.parseInt(splited[1]);
               }
           }
        if(operation=="GET")
        {
            return fid1;
        }
        else
        {
            return maxfid;
        }
                
       
    }*/
   public boolean CheckPermissions(String user,String Operation,String filepath) throws Exception
    {
      boolean ok=true;
      
      String delims1="/";
      String delims2="-";
      
      String[] directories=filepath.split(delims1);
      String fname=directories[directories.length-1];
      //String[] txtfile=fname1.split(delims2);
     // String fname=txtfile[0];
     
      if(Operation.equals("PUT"))
      {
       
        
       if(directories[1].equals("Courses")||directories[1].equals("Students"))
       {
                  ok=false;
       }
        else
        {
                  
           if(directories[1].equals("NewStudents")|| directories[1].equals("NewCourses"))
             {
                FileReader file = new FileReader("NewFiles.txt");
                 String Line;      
                BufferedReader br = new BufferedReader(file);
                while((Line = br.readLine()) != null)
                {
                  String[] perms=Line.split(delims2);
                  if(perms[0].equals(filepath))
                  {
                      
                     for(int j=1;j<perms.length;j++)
                     {
                        if(user.equals(perms[j]))
                       {
                         
                           ok=true;
                           break;
                       }
                       else
                       {
                           ok=false;
                       }
                     }
                     
                    break;
                   
                 }
                  else
                  {
                      ok=true;
                  }
                  
                  
                }      
              }
          }
          
      }
      if(Operation.equals("GET"))
      {
          if(directories[1].equals("Students"))
          {
             
                FileReader file = new FileReader("permission.txt");
                 String Line;      
                BufferedReader br = new BufferedReader(file);
                  
                while((Line = br.readLine()) != null)
                {
                   
                  String[] perms=Line.split(delims2);
                  
                  
                 
                     
                  if(perms[0].equals(user))
                  {
                      
                    
                     for(int j=1;j<perms.length;j++)
                     {
                       
                        if(fname.equals(perms[j]))
                       {
                           
                           ok=true;
                           break;
                       }
                       else
                       {
                           ok=false;
                       }
                     }
                     break;
                   
                 }  
                }      
          }
          
          
           if(directories[1].equals("NewStudents")|| directories[1].equals("NewCourses"))
          {
                FileReader file = new FileReader("NewFiles.txt");
                 String Line;      
                BufferedReader br = new BufferedReader(file);
                while((Line = br.readLine()) != null)
                {
                  String[] perms=Line.split(delims2);
                  if(perms[0].equals(filepath))
                  {
                      
                     for(int j=1;j<perms.length;j++)
                     {
                        if(user.equals(perms[j]))
                       {
                         
                           ok=true;
                           break;
                       }
                       else
                       {
                           ok=false;
                       }
                     }
                     
                    break;
                   
                 }  
                }      
          }
          
          
      }
     return ok;
   }
    
 }