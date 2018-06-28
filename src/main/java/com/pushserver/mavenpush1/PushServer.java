/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pushserver.mavenpush1;

import static com.pushserver.mavenpush1.DataBase.DBProp;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentMap;
import javax.xml.bind.DatatypeConverter;
import org.mapdb.DB;
import org.mapdb.DBMaker;


/**
 *
 * @author workaholic
 */
public class PushServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
  
        
       TIME T=new TIME();
String _tt= T.getTime("+05:30");
     
 
System.out.println("GMT time: " +_tt);

 DataBase _DB=new DataBase();
 DataBase.LoginDB _dblogin=_DB.new LoginDB(); 
 DataBase.KeyDevice KD=_DB.new KeyDevice();

 DataBase.UserPro UP=_DB.new UserPro();
 DataBase.ROUTER RO=_DB.new ROUTER();
 
 
 
        SERVER _SERVER=new SERVER("CONFIG/Server.conf");
       email _email=new email("CONFIG/Server.conf");
        Info _Info=new Info("CONFIG/pushAPI.htm");
        _SERVER.SERVERSTART(true);
        
        
 
    }
    
}
