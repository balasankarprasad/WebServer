/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver;
import java.io.*;
import java.net.*;
import java.util.*;
import java.nio.channels.*;
/**
 *
 * @author Bala
 */
public class ExecuteTransaction implements Runnable{
    String TransactionName;
     String[] fi=new String[50];
       FileLock[] filocks=new FileLock[50];
      static Map<String,String[]> TrLocks=new HashMap<>();
      static Map<String,String> FlTrLocks=new HashMap<>();
       static Map<String,FileLock> FlLocks=new HashMap<>();
      static int c=0;
    
    public ExecuteTransaction(String TN)
    {
        this.TransactionName=TN;
    }
    
    public void run()
    {
        int i=0;
       
    try {
      while(Log.map.get(this.TransactionName).FilesRequired[i]!=null)
      {
        String filname=Log.map.get(this.TransactionName).FilesRequired[i];
        boolean lockAcquired;
         do{
            lockAcquired=getFileLock(filname,i);
           
           }while(!lockAcquired);      
             FlTrLocks.put(Log.map.get(this.TransactionName).FilesRequired[i],this.TransactionName);
            FlLocks.put(Log.map.get(this.TransactionName).FilesRequired[i],this.filocks[i]);
        i++;
      }
      
     TrLocks.put(this.TransactionName, fi);
      i=0;
      while(Log.map1.get(this.TransactionName).Operation[i]!=null)
      {
          Worker w =new Worker(this.TransactionName,Log.map1.get(this.TransactionName).user[i],Log.map1.get(this.TransactionName).Operation[i],Log.map1.get(this.TransactionName).value[i],Log.map1.get(this.TransactionName).filename[i]);   
           Thread thread = new Thread(w); 
          thread.start();
           //System.out.print(Log.map1.get(this.TransactionName).filename[i]);
           i++;
      }
      for(int k=0;k<50;k++)
      {
       if(filocks[k]!=null)
       {
           filocks[k].release();
       }
      }
       } catch (Exception e)
         {
             System.out.println(e);
         }
        
    }
    
    
    public boolean getFileLock(String fn,int l)
    {
        boolean gotlock=false;
        try
        {
        File file = new File(fn);
       
        FileChannel channel = new RandomAccessFile(file, "rw").getChannel();
        FileLock lock = channel.lock();
       
        gotlock=true;
        fi[l]=fn;
        filocks[l]=lock;
        
        }catch(Exception e)
        {
         Set<String> keySet9= FlTrLocks.keySet();
         for(String i:keySet9)
          {
             if(i==fn)
             {
                 String LockedTransacton=FlTrLocks.get(i);
                 if(Log.map.get(LockedTransacton).Timestamp>Log.map.get(this.TransactionName).Timestamp)
                 {
                     FileLock flock=FlLocks.get(i);
                     try
                     {
                     flock.release();
                     }catch(Exception e1)
                     {
                         System.out.print(e1);
                     }
                     gotlock=true;
                      fi[l]=fn;
                      filocks[l]=flock;
                 }
             }
             
          }
          return gotlock;
        }
         return gotlock;
    }
}
