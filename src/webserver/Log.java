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
public class Log {
    
    //public static String[][] Files=new String[50][50];
    
    
    String user;
    String Operation;
    String filename;
    String value;
    String tid;
    long Timestamp;
     
    static Map<String,TransactionLog> map=new HashMap<>();
     static Map<String,Transaction> map1=new HashMap<>();
    
    
    public Log(String trid,long timestamp,String u,String Op,String Val,String fname) 
    {
           this.user=u;
           this.Operation =Op;           
           this.value=Val;                   
           this.filename=fname;  
           this.tid=trid;
           this.Timestamp=timestamp;    
           
           if(map.containsKey(this.tid))
           {
              map.get(this.tid).AddFile(this.filename);
           }
           else
           {
            TransactionLog TL=new TransactionLog(this.Timestamp,this.filename);
            map.put(this.tid,TL);
           }
           
           if(map1.containsKey(this.tid))
           {
             map1.get(this.tid).AddTransaction(this.user,this.Operation,this.filename,this.value);
             
           }
           else
           {
            Transaction T=new Transaction(this.user,this.Operation,this.filename,this.value);
            map1.put(this.tid,T);
           }
                         
    }
    
    public void RunLog()
    {
        
    }
   /* public void SaveFiles()
    {
       for(int i=0;i<50;i++)
       {
            if(Files[i][0]==null)
           {
               Files[i][0]=this.filename;
               Files[i][1]=this.tid;
               break;
           }
           else if(Files[i][0].equals(this.filename))
           {
               for(int j=1;j<50;j++)
               {
                   if(Files[i][j]!=null)
                   {
                      if( map.get(Files[i][j]).Timestamp>this.Timestamp)
                      {
                          for(int k=50;k>j;k--)
                          {
                              if(Files[i][k-1]!=null)
                              {
                              Files[i][k]=Files[i][k-1];
                              }
                          }
                          Files[i][j]=this.tid;
                          
                          break;
                      }
                   }
                   else 
                   {
                       Files[i][j]=this.tid;
                       break;
                   }
               }
               break;
           }
                
       }
    }*/  
}
