/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pushserver.mavenpush1;

import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author workaholic
 */
public class Info {
 
   static String IPaddress=SERVER.MYIP;// "127.0.0.1:8084";
   public static String APIlist="oF coUrse something Crashed! Knock it again!";
   
   Info(String FPath)
   {
        File f=new File(FPath);
        if(!f.exists())
             return;
        
        
        byte[] _rawdata=FiletoByteArray(FPath);
       try {
           APIlist=new String(_rawdata,"UTF-8");
       } catch (UnsupportedEncodingException ex) {
           Logger.getLogger(Info.class.getName()).log(Level.SEVERE, null, ex);
       }
        
        /*
       
        try
        {
         FileInputStream fin=new FileInputStream(f);
          int ch;
          byte[] by={0};
          APIlist="";
        
        do
        {
            ch=fin.read();
            //fin.read(by);
            by[0]=(byte)ch;
            String DATAstr=new String(by,"UTF-8");
            APIlist=APIlist+DATAstr;//String.valueOf(ch);
            //dout.writeUTF(String.valueOf(ch));
        }
        while(ch!=-1);
        fin.close();
        }
        catch (Exception ex){APIlist="oF coUrse something Crashed! Knock it again!";}
        */
        return;
         
   }
   
   
   
      private byte[] FiletoByteArray(String _path)
    {
        
        byte[] data=null;
       boolean ReadSuccesss=false;
        
       /*
        //Paths.get(LocalConfigurationDir+"\\"+ _fname).toString();
        Path path = Paths.get(_path).toAbsolutePath();
        try {Thread.sleep(100);} catch (InterruptedException ex) {  }
        
        try {
             data = Files.readAllBytes(path);
             ReadSuccesss=true;
        } catch (IOException ex) {
            System.out.println("File Read retry");
            try {
                Thread.sleep(500);
             data = Files.readAllBytes(path);
             ReadSuccesss=true;
        } catch (Exception exo) {
             System.out.println("File Read Fail:"+exo);
        }

        }

        */
       
      if(ReadSuccesss==false || data.length==0)
      {
        RandomAccessFile f=null;
        try {
            Thread.sleep(250);
            f = new RandomAccessFile(_path, "r");
            data = new byte[(int)f.length()];
            
            f.readFully(data);
             f.close();
        } catch (Exception ex) {
           
            
            //Logger.getLogger(ServerMsgObj.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("FAIL: No File found for:"+_path);
            data=("API List Missing").getBytes();
            
        
        }
        
        
      }
      
      
        
        return data;
    }
   
   
   
}
