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
public class TransactionLog {
    long Timestamp;
    String[] FilesRequired=new String[50];
    
    public TransactionLog(long timestamp,String fname )
    {
        this.Timestamp=timestamp;
        for(int i=0;i<50;i++)
        {
            if(FilesRequired[i]==null)
            {
                FilesRequired[i]=fname;
                break;
            }
        }
    }
    public void AddFile(String fname)
    {
         for(int i=0;i<50;i++)
        {
            if(FilesRequired[i]==null)
            {
                FilesRequired[i]=fname;
                break;
            }
        }
    }
    
}
