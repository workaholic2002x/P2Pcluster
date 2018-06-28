/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pushserver.mavenpush1;


//import br.net.woodstock.rockframework.core.net.mail.MailHelper;
//import com.dumbster.smtp.SimpleSmtpServer;
//import com.dumbster.smtp.SmtpMessage;

import FileParser.ParseFile;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
//import static junit.framework.Assert.assertTrue;


import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Iterator;
import FileParser.ParseFile;


//import ju com.java2novice.junit.samples.Employee;

//import static org.junit.Assert.*;

//import static org.junit.Assert.*;

/**
 *
 * @author workaholic
 */
public class email {

    Timer TimerMail = new Timer();
    String _Uname="";
    String _Pass="";
    String mail_smtp_host="";
    String mail_transport_protocol="";
    String mail_smtp_auth="";
    String mail_smtp_port="";
    
    static String GPath="";
    
    public  email(String FilePath)
    {
        if(FilePath!=null)
            GPath=FilePath;
        else
            FilePath=GPath;
        
         HashMap<String, String> HM=new HashMap<String,String>();
            HM=ParseFile.GetVal(FilePath, "EMAIL");
            String _temp="";
            try
            {
               mail_smtp_host=HM.get("EMAIL.mail_smtp_host");
               mail_transport_protocol=HM.get("EMAIL.mail_transport_protocol");
               mail_smtp_auth=HM.get("EMAIL.mail_smtp_auth");
               mail_smtp_port=HM.get("EMAIL.mail_smtp_port");
               
               _Uname=HM.get("EMAIL.USERNAME");
               _Pass=HM.get("EMAIL.PASSWORD");
                
            }
            catch(Exception ex)
            {
                
            }
            
    }
    
    
    
    public boolean sendmaily(String _TO, String _SUB, String _MSG)
    {

    TimerMail=new Timer(); 
    TimerMail.schedule(new TimerTask() {
                  @Override
                  public void run() {
                     //validity chechk
                sendmailAny(_TO, _SUB, _MSG);
                         
                   }
                },100);
          return true;
    }



    static int _count=0;
    private boolean sendmailAny(String _TO, String _SUB, String _MSG)
    {
        if(_count>10)
            return false;
        _count++;
            String to=_TO;//change accordingly  

          //Get the session object  
          Properties props = new Properties();  
          props.put("mail.smtp.host",mail_smtp_host);  
          props.put("mail.transport.protocol", mail_transport_protocol);
          
         // props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");  
          
         props.put("mail.smtp.auth", mail_smtp_auth);  
         props.put("mail.smtp.port", mail_smtp_port);  
           
          Session session = Session.getDefaultInstance(props,  
           new javax.mail.Authenticator() {  
           protected PasswordAuthentication getPasswordAuthentication() {  
           //change accordingly  
            return new PasswordAuthentication(_Uname,_Pass);//change accordingly  
           }  
          });  

          //compose message  
          try {  
           MimeMessage message = new MimeMessage(session);  
           message.setFrom(new InternetAddress(_Uname));
//change accordingly  
           message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
           message.setSubject(_SUB);//("Hello");  
           message.setText(_MSG);//("Testing.......");  

           Transport transport = session.getTransport();

           transport.connect();

           transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
           transport.close();
           //send message  
           //Transport.send(message);  

           _count--;
           System.out.println("message sent successfully");  
           return true;
          } catch (MessagingException e) {
            _count--;  
              return false;
             // throw new RuntimeException(e);
           
          }  
         
  }
    
    
        
        
}
