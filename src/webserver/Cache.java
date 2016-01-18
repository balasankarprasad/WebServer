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
public class Cache {
    public static String[] Files=new String[50];
    public static String[] FilesData=new String[50];
    public int size=5;
    public static int initialized=0;
    
    public Cache( )
    {
       if(initialized==0)
       {
          
         for(int i=0;i<50;i++)
         {
            Files[i]=" ";
            FilesData[i]=" ";
            
         }
         initialized=1;
       }
    }
    
    public int FindFile(String FileName) 
    {
        int i=0;
        for(int j=0;j<5;j++)
        {
          if(Files[j].equals(FileName))
                
         {
              i=1;
              String tempfile=Files[j];
              String tempfiledata=FilesData[j];
                
            for (int k=j;k>0;k++)
            {
          
              Files[k]=Files[k-1];
           
              FilesData[k]=FilesData[k-1];
            }
           
            Files[0]=tempfile;
            FilesData[0]=tempfiledata;
            break;
        
         }
       }
        return i;
    }
    
    public String GetData(String FileName)
    {
     
          for(int i=0;i<5;i++)
        {
            if(Files[i].equals(FileName))
                
            {
                return FilesData[i];
            }
          
        }
        
        return "";
        
    }
    public void InsertFile(String FileName,String Data)
    {
      
            for (int j=4;j>0;j--)
        {
          
           Files[j]=Files[j-1];
           
           FilesData[j]=FilesData[j-1];
        }
           
        Files[0]=FileName;
        FilesData[0]=Data;
        
        
    }
    
}
