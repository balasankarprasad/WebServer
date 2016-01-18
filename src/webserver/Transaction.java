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
public class Transaction {
    public  String[] user=new String[50];
   public  String[] Operation=new String[50];
      public String[] filename=new String[50];
     public String[] value=new String[50];
   public int i=0;
   
     public Transaction( String us,String Op,String fname,String val)
    {
         this.user[i]=us;
        this.Operation[i]=Op;
        this.filename[i]=fname;
        this.value[i]=val;
        
    }
     public void AddTransaction(String us,String Op,String fname,String val)
     {
         for(int i=0;i<50;i++)
         {
             if(Operation[i]==null)
             {
                   this.Operation[i]=Op;
                   this.filename[i]=fname;
                   this.value[i]=val;
                   this.user[i]=us;
                   
                   break;
             }
         }
     }
}
