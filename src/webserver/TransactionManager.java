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
public class TransactionManager implements Runnable {
    String Transaction;
    public TransactionManager(String Trans)
    {
      this.Transaction=Trans;
    }
     public  void run()
     {
        
         long timestamp=System.nanoTime();
         String operations[]=Transaction.split("@");
         
         String tid=operations[0];
         for(int i=1;i<operations.length;i++)
         {
          Resolver request=new Resolver(tid,timestamp,operations[i]);
                      Thread thread = new Thread(request); 
                      try
                      {
                       thread.start();
                       thread.sleep(1000);
                      }
                       catch(Exception e)
                      {
                         System.out.println(e);
                      }
         }
         
         
      
     }
    
}
