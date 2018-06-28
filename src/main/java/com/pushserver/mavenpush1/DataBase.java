/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pushserver.mavenpush1;

//import com.sun.xml.internal.ws.commons.xmlutil.Converter;
import com.pushserver.mavenpush1.NodeGroup.NodeInfo;
import java.io.File;
import java.io.Serializable;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import static javax.management.Query.gt;
import static javax.management.Query.lt;
import javax.xml.bind.DatatypeConverter;
import org.mapdb.BTreeMap;
import org.mapdb.HTreeMap;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
/**
 *
 * @author workaholic
 */

public class DataBase {
   
           
  static DB DBLogin;
  static DB DBKeys;
  static DB DBProp;
  
  static DB Group;
  static DB DBROUTER;
  
  static DB DBWALLET;
  static DB DBWALLET_TRANS;
   static DB DBWALLET_ADMIN;

  static DB DBVEHECLE;
  static DB DBCREDENTIALS;
  static DB DBTOLLINFO;
  
  static DB DBVAHAN;
  
  String MAP_LOGIN;
  String MAP_KEYS;
  String MAP_PROP;
  
  String MAP_ROUTER;
  String GROUP_KEYS;
  
 
String MAP_WALLET;
String MAP_WALLET_TRANS;
String MAP_WALLET_ADMIN;

String MAP_VEHECLE;
String MAP_CREDENTIALS;

String MAP_TOLLINFO;
String MAP_VAHAN;

    public static List<String> UIDList=new ArrayList<String>();
   public static HashMap<String,String>KeyUser=new HashMap<String,String>();
   public static HashMap<String,String>IntRouter=new HashMap<String,String>();
   public static HashMap<String,String>ExtRouter=new HashMap<String,String>();
    
    
     public DataBase(){
         
         try
         {
             File f = new File("KEYGROUP.DB");// File.createTempFile("KEYGROUP","DB");
		 Group = DBMaker.appendFileDB(f)
                        .asyncWriteEnable()
                        .fileMmapEnable()
                        
				.make(); 
                 
                 GROUP_KEYS="KEY_GROUP";
     
                 
     Map<String,NodeGroup.NG> dbMap = Group.hashMap(GROUP_KEYS);
     //   ConcurrentNavigableMap<Integer,String> map = DBLogin.treeMap(MAP_LOGIN);
    
         
        NodeGroup.NG Group1=new NodeGroup.NG();//"000001","1000000",x) ;//
       //  Group1.Members.Group_Master="1000000";
         
       NodeGroup.NodeInfo _NInfo=new NodeGroup.NodeInfo();
         _NInfo.NInfo="Hello 1";
        // _NInfo.Group_members.put("OK1", _NInfo(""));
         Group1.Members.put("1",_NInfo);
         Group1.Members.put("2",_NInfo);
         
         dbMap.put("123456",Group1);
       Group.commit();
         
         
         NodeGroup.NG data=dbMap.get("123456");
        
         Group.close();
              
                 
                 
             int k=0;
         }catch (Exception ex){
         int i=0;
         }
         

         
         try
         {
              File f = new File("ROUTER.DB");
            DBROUTER=DBMaker
               .appendFileDB(f)
               .fileMmapEnable()
               //.closeOnJvmShutdown()
               //.transactionEnable()
                    
               .make();
              
              MAP_ROUTER="ROUTER";
         }catch(Exception ex){}
     
         
         try
         {
              File f = new File("Login.DB");
            DBLogin=DBMaker
               .appendFileDB(f)
               .fileMmapEnable()
               //.closeOnJvmShutdown()
               //.transactionEnable()
                    
               .make();
              
              MAP_LOGIN="Login";
         }catch(Exception ex){}
     
         try
         {
             File f = new File("KeyDevices.DB");
              DBKeys=DBMaker
               .appendFileDB(f)
               .fileMmapEnable()
               //.closeOnJvmShutdown()
               //.transactionEnable()
               .make();
              
              MAP_KEYS="DBKEYS";
         
         }catch(Exception ex){}
     
         try
         {
             File f = new File("KeyProperties.DB");
              DBProp=DBMaker
               .appendFileDB(f)
               .fileMmapEnable()
               //.closeOnJvmShutdown()
               //.transactionEnable()
               .make();
              
              MAP_PROP="DBPROP";
         
         }catch(Exception ex){}

  
         
    //For toll project
         try
         {
             File f = new File("WALLET.DB");
              DBWALLET=DBMaker
               .appendFileDB(f)
               .fileMmapEnable()
               //.closeOnJvmShutdown()
               //.transactionEnable()
               .make();
              
              MAP_WALLET="DBWALLET";
         
         }catch(Exception ex){}

         
         try
         {
             File f = new File("WALLET_TRANS.DB");
              DBWALLET_TRANS=DBMaker
               .appendFileDB(f)
               .fileMmapEnable()
               //.closeOnJvmShutdown()
               //.transactionEnable()
               .make();
              
              MAP_WALLET_TRANS="DBWALLET_TRANS";
         
         }catch(Exception ex){}

         try
         {
             File f = new File("WALLET_ADMIN.DB");
              DBWALLET_ADMIN=DBMaker
               .appendFileDB(f)
               .fileMmapEnable()
               //.closeOnJvmShutdown()
               //.transactionEnable()
               .make();
              
              MAP_WALLET_ADMIN="DBWALLET_ADMIN";
         
         }catch(Exception ex){}
        
         
         
         try
         {
             File f = new File("VEHECLE.DB");
              DBVEHECLE=DBMaker
               .appendFileDB(f)
               .fileMmapEnable()
               //.closeOnJvmShutdown()
               //.transactionEnable()
               .make();
              
              MAP_VEHECLE="DBVEHECLE";
         
         }catch(Exception ex){}

         
         try
         {
             File f = new File("CREDENTIALS.DB");
              DBCREDENTIALS=DBMaker
               .appendFileDB(f)
               .fileMmapEnable()
               //.closeOnJvmShutdown()
               //.transactionEnable()
               .make();
              
              MAP_CREDENTIALS="DBCREDENTIALS";
         
         }catch(Exception ex){}

         
         try
         {
             File f = new File("TOLLINFO.DB");
              DBTOLLINFO=DBMaker
               .appendFileDB(f)
               .fileMmapEnable()
               //.closeOnJvmShutdown()
               //.transactionEnable()
               .make();
              
              MAP_TOLLINFO="DBTOLLINFO";
         
         }catch(Exception ex){}

         
                  try
         {
             File f = new File("VAHAN.DB");
              DBVAHAN=DBMaker
               .appendFileDB(f)
               .fileMmapEnable()
               //.closeOnJvmShutdown()
               //.transactionEnable()
               .make();
              
              MAP_VAHAN="DBVAHAN";
         
         }catch(Exception ex){}

         
       
     }

       
public class VahanInfo
{
    
     String GetVehecleInfoVahan(String CarNo)
     {
        
         if(CarNo==null)
         {
          //_KEY="UNKNOWN";
         return "Error";
         }
         
          Map<String,String> MapVahan = DBVAHAN.hashMap(MAP_VAHAN);
          String VelecleInfo=MapVahan.get(CarNo);
          if(VelecleInfo==null)
          {
              VelecleInfo="0";
          }
              
           if(VelecleInfo.length()==0)
              VelecleInfo="0";
         //DevProp.put(UserID,String.valueOf(PropertyNo));
         return VelecleInfo;// Integer.parseInt(DevProp.put(UserID,String.valueOf(PropertyNo)));
     }
    
     
     String GetAllVehecleInfoVahan()
     {
         String allData="";
          Map<String,String> MapVahan = DBVAHAN.hashMap(MAP_VAHAN);
         for(String _UID:MapVahan.keySet())
         {
           
            String VehecleInfo=MapVahan.get(_UID);
            if(VehecleInfo==null)
                VehecleInfo="0";
             if(VehecleInfo.length()==0)
                VehecleInfo="0";
             
             allData+=_UID+":"+VehecleInfo+"#";
         }
         
          //DevProp.put(UserID,String.valueOf(PropertyNo));
         return allData;// Integer.parseInt(DevProp.put(UserID,String.valueOf(PropertyNo)));
     }
    
     
     String ClearAllVehecleInfoVahan()
     {
         String allData="";
          Map<String,String> MapVahan = DBVAHAN.hashMap(MAP_VAHAN);
          MapVahan.clear();
          DBVAHAN.commit();
          //DevProp.put(UserID,String.valueOf(PropertyNo));
         return "OK";// Integer.parseInt(DevProp.put(UserID,String.valueOf(PropertyNo)));
     }
    
     
     
     String UpdateVehecleInfoVahan(String VehecleNo, String VehecleInfo)
     {
        
         if(VehecleNo==null)
         {
          //_KEY="UNKNOWN";
         return "Error";
         }
         
          Map<String,String> MapVahan = DBVAHAN.hashMap(MAP_VAHAN);
          //String VelecleInfo=MapVehecle.get(_KEY);
         String VInfo="";
          try
         {
          String stat=MapVahan.get(VehecleNo);
         String Mob_No="";
          if(VehecleInfo.contains(":"))
         {
             
            VInfo= VehecleInfo.split(":")[0];
          Mob_No=VehecleInfo.split(":")[1];
         }
          else
              VInfo=VehecleInfo;
          
          try
          {
          MapVahan.put(VehecleNo,VInfo);
          DBVAHAN.commit();
          }catch(Exception ex){return "ERROR";}
          
          
          if(Mob_No.length()>1)
          {
          email email=new email(null);
                                 
          
          //url="http://198.24.149.4/API/pushsms.aspx?loginID=CHANDANMTOLL&password=kulgachia&mobile=" + mobileno + "&text=" + _otpstr + "&senderid=MOTOLL&route_id=2&Unicode=0";
				
                                //String  _KEY= SERVER.DBlogin.GetKeyDetails(Username);
                                  String MSG="User Request for Car registration:\n"+
                                          "Vehecle_NO:"+VehecleNo+"\n"+
                                          "Current Status: "+stat+"\n"+
                                          "Request Status: "+VInfo+"\n"+
                                           "Mobile No: "+Mob_No+"\n"+
                                          "\nApprove Link \nhttp://push.ubikeys.com:8083/EDITVEHECLEVAHAN?CARNO="+VehecleNo+ "&Info=1"+
                                          "\n   Approve SMS: \n http://198.24.149.4/API/pushsms.aspx?loginID=CHANDANMTOLL&password=kulgachia&mobile=" +Mob_No + "&text=Hello User, Your request for Vehecle No ["+
                                            VehecleNo + "] has been Verified. Use TollPe to Pay Toll&senderid=MOTOLL&route_id=2&Unicode=0" +
                                          "\n\n Discard Link\n"+
                                          "http://push.ubikeys.com:8083/EDITVEHECLEVAHAN?CARNO=[" +VehecleNo+ "]&Info=100"+
                                          "\n   Approve SMS: \n http://198.24.149.4/API/pushsms.aspx?loginID=CHANDANMTOLL&password=kulgachia&mobile=" +Mob_No + "&text=Hello User, Your request for Vehecle No ["+
                                            VehecleNo + "] has been Discarded. Use TollPe to Pay Toll&senderid=MOTOLL&route_id=2&Unicode=0" +
                                         
                                          "\n\nDelete Link\nhttp://push.ubikeys.com:8083/DELVEHECLEVAHAN?CARNO="+VehecleNo;
                                  email.sendmaily("chandan2002x@gmail.com","Vehecle Request:"+VehecleNo,MSG );
          }
          }
         catch(Exception ex){}
                                
          //DevProp.put(UserID,String.valueOf(PropertyNo));
         return "OK:"+VInfo;// Integer.parseInt(DevProp.put(UserID,String.valueOf(PropertyNo)));
     }
    
     String RemoveVehecleInfoVahan(String VehecleInfo)
     {
         if(VehecleInfo==null)
               return "Error";
         
           Map<String,String> MapVahan = DBVAHAN.hashMap(MAP_VAHAN);
          
          try
          {
          MapVahan.remove(VehecleInfo);
          DBVAHAN.commit();
          }catch(Exception ex){return "ERROR";}
          //DevProp.put(UserID,String.valueOf(PropertyNo));
         return "OK";// Integer.parseInt(DevProp.put(UserID,String.valueOf(PropertyNo)));
     }



         
    
}
     

public class TollInfo
{
    String GetNearbyTolls(String sLat, String sLon, String sHowMany)
    {
            //Map<String,String> MapToll = DBTOLLINFO.hashMap(MAP_TOLLINFO);
    Map<String,Double> TollID_dis=new HashMap<String, Double>();
    Map<String,String> MapToll = DBTOLLINFO.hashMap(MAP_TOLLINFO);
    
    TOLLPARAMS TPM;
    double lat1=Double.parseDouble(sLat);
    double lon1=Double.parseDouble(sLon);
    double lat2=0.0;
    double lon2=0.0;
    double sdis=0.0;
    String _AllInfo="";
    try
    {
        for(String Tid : SERVER.AllTollFare.keySet())
        {
            TPM=SERVER.AllTollFare.get(Tid);
            lat2=TPM.TPARAMS.loc.latitude;
            lon2=TPM.TPARAMS.loc.longitude;
            sdis=Get_distance(lat1,lat2,lon1,lon2,0.0,0.0);
            TollID_dis.put(Tid, Double.parseDouble(String.format ("%.1f", sdis)));
        }
        //MapUtil MU=new MapUtil(TollID_dis);
        //TreeMap<String, Double> sorted_map = new TreeMap<String, Double>(MU);
        
        int HowMany=Integer.parseInt(sHowMany);
       TollID_dis=MapUtil.sortByValue(TollID_dis);
       if(TollID_dis.size()<HowMany)
           HowMany=TollID_dis.size();
       
       
       for(int i=0;i<HowMany;i++)
       {
           String _tid=(String)TollID_dis.keySet().toArray()[i];
           _AllInfo+="["+MapToll.get(_tid) +"]";//SERVER.AllTollFare.get(io).TPARAMS.AllInfo+"#";
       }
    }catch(Exception ex){}  
       
        return _AllInfo;
    }
    
        double Get_distance(double lat1, double lat2, double lon1,
                 double lon2, double el1, double el2) {

             final int R = 6371; // Radius of the earth

             double latDistance = Math.toRadians(lat2 - lat1);
             double lonDistance = Math.toRadians(lon2 - lon1);
             double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                     + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                     * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
             double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
             double distance = R * c * 1000; // convert to meters

             double height = el1 - el2;

             distance = Math.pow(distance, 2) + Math.pow(height, 2);

             return Math.sqrt(distance);
         }
    
    boolean Load_TollFare()
    {
      Map<String,String> MapToll = DBTOLLINFO.hashMap(MAP_TOLLINFO);
      Set<String> AllKeys= MapToll.keySet();
      String Temp="";
      TOLLPARAMS _TPARAMS; 
       SERVER.AllTollFare.clear();
      try
      {
      for(String eachkey:AllKeys)
      {
       Temp=MapToll.get(eachkey);
       _TPARAMS=new TOLLPARAMS(Temp);
       SERVER.AllTollFare.put(eachkey,_TPARAMS);
      }
      }
      catch(Exception ex){}
      
        return true;
    }
    
    String GetTollInfo(String _TollId)
    {
         Map<String,String> MapToll = DBTOLLINFO.hashMap(MAP_TOLLINFO);
         
        String Info="";         
          try
          {
          Info=MapToll.get(_TollId);//,_TollInfo);
          //DBTOLLINFO.commit();
          }catch(Exception ex){return Info;}
          //DevProp.put(UserID,String.valueOf(PropertyNo));
                
        return Info;
    }
    
    String AddTollInfo(String _TollId, String _TollInfo)
    {
          Map<String,String> MapToll = DBTOLLINFO.hashMap(MAP_TOLLINFO);
         
          if(_TollInfo.split("\\|").length<4)
             return "Invalid Data";
         
          try
          {
              TOLLPARAMS _TPARAMS; 
          MapToll.put(_TollId,_TollInfo);
          _TPARAMS=new TOLLPARAMS(_TollInfo);
          SERVER.AllTollFare.put(_TollId,_TPARAMS);
          DBTOLLINFO.commit();
          }catch(Exception ex){return "Invalid input data";}
          //DevProp.put(UserID,String.valueOf(PropertyNo));
         return "OK:TOLL_ID Inserted-"+_TollId;//
        
    }
    
    boolean DeleteTollInfo(String _TollId)
    {
         Map<String,String> MapToll = DBTOLLINFO.hashMap(MAP_TOLLINFO);
         
         
          try
          {
          MapToll.remove(_TollId);//,_TollInfo);
          DBTOLLINFO.commit();
          }catch(Exception ex){return false;}
          //DevProp.put(UserID,String.valueOf(PropertyNo));
         return true;//
        
    }
    
    
}     
     
public class VehecleInfo
{
    
     String GetVehecleInfo(String UserID)
     {
         String _KEY="UNKNOWN";
         UserID=UserID.toUpperCase();
         
         if(!UserID.contains("@") && !UserID.contains("."))
         {
         //_KEY=SERVER.DBlogin.GetKeyDetails(UserInfo);
             _KEY=DataBase.KeyUser.get(UserID);
         }
         else
             _KEY=UserID;
         
         if(_KEY==null)
         {
          //_KEY="UNKNOWN";
         return "Error";
         }
         
          Map<String,String> MapVehecle = DBVEHECLE.hashMap(MAP_VEHECLE);
          String VelecleInfo=MapVehecle.get(_KEY);
          if(VelecleInfo==null)
          {
              VelecleInfo="0";
          }
              
           if(VelecleInfo.length()==0)
              VelecleInfo="0";
         //DevProp.put(UserID,String.valueOf(PropertyNo));
         return VelecleInfo;// Integer.parseInt(DevProp.put(UserID,String.valueOf(PropertyNo)));
     }
    
     
     String GetAllVehecleInfo()
     {
         String _KEY="UNKNOWN";
         /*
         UserID=UserID.toUpperCase();
         
         if(!UserID.contains("@") && !UserID.contains("."))
         {
         //_KEY=SERVER.DBlogin.GetKeyDetails(UserInfo);
             _KEY=DataBase.KeyUser.get(UserID);
         }
         else
             _KEY=UserID;
         
         DataBase.
         
         if(_KEY==null)
         {
          //_KEY="UNKNOWN";
         return "Error";
         }
         */
         String allData="";
          Map<String,String> MapVehecle = DBVEHECLE.hashMap(MAP_VEHECLE);
         for(String _UID:UIDList)
         {
           
            String VehecleInfo=MapVehecle.get(_UID);
            if(VehecleInfo==null)
                VehecleInfo="";
             if(VehecleInfo.length()==0)
                VehecleInfo="";
             
             allData+=VehecleInfo;
         }
         
          //DevProp.put(UserID,String.valueOf(PropertyNo));
         return allData;// Integer.parseInt(DevProp.put(UserID,String.valueOf(PropertyNo)));
     }
    
     
     String UpdateVehecleInfo(String UserID, String VehecleNo, String VehecleType)
     {
         String _KEY="UNKNOWN";
         UserID=UserID.toUpperCase();
         
         if(!UserID.contains("@") && !UserID.contains("."))
         {
         //_KEY=SERVER.DBlogin.GetKeyDetails(UserInfo);
             _KEY=DataBase.KeyUser.get(UserID);
         }
         else
             _KEY=UserID;
         
         if(_KEY==null)
         {
          //_KEY="UNKNOWN";
         return "Error";
         }
         
          Map<String,String> MapVehecle = DBVEHECLE.hashMap(MAP_VEHECLE);
          //String VelecleInfo=MapVehecle.get(_KEY);
          String _info="["+VehecleNo+"|"+VehecleType+"]";
          try
          {
          MapVehecle.put(_KEY,_info);
          DBVEHECLE.commit();
          }catch(Exception ex){return "ERROR";}
          //DevProp.put(UserID,String.valueOf(PropertyNo));
         return "OK";// Integer.parseInt(DevProp.put(UserID,String.valueOf(PropertyNo)));
     }
    
     String RemoveVehecleInfo(String UserID)
     {
         String _KEY="UNKNOWN";
         UserID=UserID.toUpperCase();
         
         if(!UserID.contains("@") && !UserID.contains("."))
         {
         //_KEY=SERVER.DBlogin.GetKeyDetails(UserInfo);
             _KEY=DataBase.KeyUser.get(UserID);
         }
         else
             _KEY=UserID;
         
         if(_KEY==null)
         {
          //_KEY="UNKNOWN";
         return "Error";
         }
         
          Map<String,String> MapVehecle = DBVEHECLE.hashMap(MAP_VEHECLE);
          
          try
          {
          MapVehecle.remove(_KEY);
          DBVEHECLE.commit();
          }catch(Exception ex){return "ERROR";}
          //DevProp.put(UserID,String.valueOf(PropertyNo));
         return "OK";// Integer.parseInt(DevProp.put(UserID,String.valueOf(PropertyNo)));
     }



     
     String AddVehecleInfo(String UserID, String VehecleNo, String VehecleType)
     {
         String _KEY="UNKNOWN";
         UserID=UserID.toUpperCase();
         
         if(!UserID.contains("@") && !UserID.contains("."))
         {
         //_KEY=SERVER.DBlogin.GetKeyDetails(UserInfo);
             _KEY=DataBase.KeyUser.get(UserID);
         }
         else
             _KEY=UserID;
         
         if(_KEY==null)
         {
          //_KEY="UNKNOWN";
         return "Error";
         }
         
          Map<String,String> MapVehecle = DBVEHECLE.hashMap(MAP_VEHECLE);
          //String VelecleInfo=MapVehecle.get(_KEY);
          String _info=/*MapVehecle.get(_KEY)+*/"["+VehecleNo+"|"+VehecleType+"]";
          
          try
          {
          MapVehecle.put(_KEY,_info);
          DBVEHECLE.commit();
          }catch(Exception ex){return "ERROR";}
          //DevProp.put(UserID,String.valueOf(PropertyNo));
         return "OK";// Integer.parseInt(DevProp.put(UserID,String.valueOf(PropertyNo)));
     }
     
     
     
    
}
     
     
public class UserWallet
{
    
     String GetUserWallet(String UserID)
     {
         String _KEY="UNKNOWN";
         UserID=UserID.toUpperCase();
         
         if(!UserID.contains("@") && !UserID.contains("."))
         {
         //_KEY=SERVER.DBlogin.GetKeyDetails(UserInfo);
             _KEY=DataBase.KeyUser.get(UserID);
         }
         else
             _KEY=UserID;
         
         if(_KEY==null)
         {
          //_KEY="UNKNOWN";
         return "Error";
         }
         
          Map<String,String> MapWallet = DBWALLET.hashMap(MAP_WALLET);
          String initMoney=MapWallet.get(_KEY);
          if(initMoney==null)
          {
              UserCreditWallet(UserID,"0","CREATE");
              initMoney="0";
          }
              
           if(initMoney.length()==0)
              initMoney="0";
         //DevProp.put(UserID,String.valueOf(PropertyNo));
         return initMoney;// Integer.parseInt(DevProp.put(UserID,String.valueOf(PropertyNo)));
     }
    
     //00000000,0.0,REFUND
     //USER_ID,0.0,REFUND
     //USER_ID,100,RECHARGE
     
    String UserCreditWallet_Recharge(String UserID,String MoneyToAdd)
     {
       return  UserCreditWallet(UserID,MoneyToAdd, "RECHARGE");
     }
      String UserCreditWallet_USERREFUND(String UserID)
     {
       return  UserCreditWallet(UserID,"0", "USERREFUND");
     }
      
       String UserCreditWallet_ADMINREFUND(String UserID)
     {
       return  UserCreditWallet(UserID,"0", "ADMINREFUND");
     }
       
    private String UserCreditWallet(String UserID,String MoneyToAdd, String reason) //reason=recharge,refund,bonus
     {
         String _KEY="UNKNOWN";
         boolean isAdmin=false;
         UserID=UserID.toUpperCase();
         if(UserID.equals("0000000000"))
             isAdmin=true;
         if(!UserID.contains("@") && !UserID.contains("."))
         {
         //_KEY=SERVER.DBlogin.GetKeyDetails(UserInfo);
             _KEY=DataBase.KeyUser.get(UserID);
         }
         else
             _KEY=UserID;
         
         if(_KEY==null&& !isAdmin)
         {
          //_KEY="UNKNOWN";
         return "Error";
         }
         
          Map<String,String> MapWallet = DBWALLET.hashMap(MAP_WALLET);
          Map<String,String> MapWallet_Trans = DBWALLET_TRANS.hashMap(MAP_WALLET_TRANS);
            Double initMoney=0.0;   
          
         if(reason.toUpperCase().equals("RECHARGE") ||reason.toUpperCase().equals("CREATE"))
         {
         //DevProp.put(_KEY,String.valueOf(PropertyNo));
         //DBProp.commit();
           try
           {
            initMoney=Double.parseDouble(MapWallet.get(_KEY));// Integer.parseInt(DevProp.put(UserID,String.valueOf(PropertyNo)));
           }catch(Exception ex){initMoney=0.0;}
            initMoney+=Double.parseDouble(MoneyToAdd);
           
           MapWallet.put(_KEY,String.valueOf(initMoney));
           DBWALLET.commit();
           UserAccount("[CREDIT:"+_KEY+":"+MoneyToAdd+":"+initMoney+":"+reason+":"+SERVER.GetDateTime()+ "]");
          
         }
         else if(reason.toUpperCase().endsWith("REFUND"))
         {
               Double initMoney_Trans=0.0;
           try
           {
            initMoney_Trans=Double.parseDouble(MapWallet_Trans.get(_KEY));// Integer.parseInt(DevProp.put(UserID,String.valueOf(PropertyNo)));
           }catch(Exception ex){initMoney_Trans=0.0;}
           try
           {
            initMoney=Double.parseDouble(MapWallet.get(_KEY));// Integer.parseInt(DevProp.put(UserID,String.valueOf(PropertyNo)));
           }catch(Exception ex){initMoney=0.0;}
           
           if(initMoney_Trans>0.0)
           {
            initMoney+=initMoney_Trans;
            MapWallet.put(_KEY,String.valueOf(initMoney));
            DBWALLET.commit();
           
            MapWallet_Trans.put(_KEY,"0");
            DBWALLET_TRANS.commit();
           //if(isAdmin)
           UserAccount("[CREDIT:"+_KEY+":"+initMoney_Trans+":"+initMoney+":"+reason+":"+SERVER.GetDateTime()+ "]");
           
           TransAccount("[DEBIT:"+_KEY+":"+initMoney_Trans+":"+reason+":"+SERVER.GetDateTime()+ "]");
           return String.valueOf(initMoney_Trans);
           }
           else
               return "ERROR:NO_TRANS_MONEY";
           
         }
         else // No reason mtches
             return "ERROR:NO_REASON";
           
           return String.valueOf(initMoney);
     }
    
    
    
    String UserDebitWallet(String UserID,String MoneyToDebit, String reason)//reason=toll-ID charge
     {
         String _KEY="UNKNOWN";
         UserID=UserID.toUpperCase();
         if(!UserID.contains("@") && !UserID.contains("."))
         {
         //_KEY=SERVER.DBlogin.GetKeyDetails(UserInfo);
             _KEY=DataBase.KeyUser.get(UserID);
         }
         else
             _KEY=UserID;
         
         if(_KEY==null)
         {
          //_KEY="UNKNOWN";
         return "Error";
         }
         
          Map<String,String> MapWallet = DBWALLET.hashMap(MAP_WALLET);
          Map<String,String> MapWallet_Trans = DBWALLET_TRANS.hashMap(MAP_WALLET_TRANS);
         //DevProp.put(_KEY,String.valueOf(PropertyNo));
         //DBProp.commit();
         
           Double initMoney=0.0;
           Double initMoney_Trans=0.0;
           
           try
           {
            initMoney_Trans=Double.parseDouble(MapWallet_Trans.get(_KEY));// Integer.parseInt(DevProp.put(UserID,String.valueOf(PropertyNo)));
           }catch(Exception ex){initMoney_Trans=0.0;}
           try
           {
            initMoney=Double.parseDouble(MapWallet.get(_KEY));// Integer.parseInt(DevProp.put(UserID,String.valueOf(PropertyNo)));
           }catch(Exception ex){initMoney=0.0;}
           
           
          if(initMoney_Trans==0.0)
          {
           if(initMoney>Double.parseDouble(MoneyToDebit))
           {
           initMoney-=Double.parseDouble(MoneyToDebit);
           MapWallet.put(_KEY,String.valueOf(initMoney));
           DBWALLET.commit();
           MapWallet_Trans.put(_KEY,String.valueOf(MoneyToDebit));
           DBWALLET_TRANS.commit();
           
           UserAccount("[DEBIT:"+_KEY+":"+MoneyToDebit+":"+initMoney+":"+reason+":"+SERVER.GetDateTime()+ "]");
           TransAccount("[CREDIT:"+_KEY+":"+MoneyToDebit+":"+reason+":"+SERVER.GetDateTime()+ "]");
           }
           else
               return "ERROR:LOW_BAL>"+initMoney;
          }else
              return "ERROR:PENDING_TRANS_MONEY";
           
           
           return String.valueOf(initMoney);
     }
    
    
    
    
     
     //USER_ID,REASON=TOLL_ID
     
    String AdminCreditWallet(String UserID,String reason) //reason=recharge,refund,bonus
     {
         String _KEY="UNKNOWN";
         
         UserID=UserID.toUpperCase();
         if(!UserID.contains("@") && !UserID.contains("."))
         {
         //_KEY=SERVER.DBlogin.GetKeyDetails(UserInfo);
             _KEY=DataBase.KeyUser.get(UserID);
         }
         else
             _KEY=UserID;
         
         if(_KEY==null)
         {
          //_KEY="UNKNOWN";
         return "Error";
         }
         
          Map<String,String> MapWallet_Admin = DBWALLET_ADMIN.hashMap(MAP_WALLET_ADMIN);
          Map<String,String> MapWallet_Trans = DBWALLET_TRANS.hashMap(MAP_WALLET_TRANS);
            Double TransMoney=0.0;   
            Double AdminMoney=0.0;   
          
         //DevProp.put(_KEY,String.valueOf(PropertyNo));
         //DBProp.commit();
           try
           {
            TransMoney=Double.parseDouble(MapWallet_Trans.get(_KEY));// Integer.parseInt(DevProp.put(UserID,String.valueOf(PropertyNo)));
           }catch(Exception ex){TransMoney=0.0;}
           
           try
           {
            AdminMoney=Double.parseDouble(MapWallet_Admin.get(_KEY));// Integer.parseInt(DevProp.put(UserID,String.valueOf(PropertyNo)));
           }catch(Exception ex){AdminMoney=0.0;}
           
           if(TransMoney==0.0)
               return "ERROR:NO_TRANS_MONEY";
           else
           {
               AdminMoney+=TransMoney;
           
           MapWallet_Admin.put(_KEY,String.valueOf(AdminMoney));
           DBWALLET_ADMIN.commit();
           MapWallet_Trans.put(_KEY,"0");
           DBWALLET_TRANS.commit();
           
           AdminAccount("[CREDIT:"+_KEY+":"+TransMoney+":"+AdminMoney+":"+reason+":"+SERVER.GetDateTime()+ "]");
           TransAccount("[DEBIT:"+_KEY+":"+TransMoney+":"+reason+":"+SERVER.GetDateTime()+ "]");
          
               
           }
           
           
           return String.valueOf(TransMoney);
     }
    
    
    
    
    String UserAccount(String Details)
    {
        String _KEY="0000000000";
          Map<String,String> MapWallet = DBWALLET.hashMap(MAP_WALLET);
          String AllDetails="";
          try
          {
          AllDetails=MapWallet.get(_KEY);
          }catch(Exception ex){AllDetails="NIL";}

        if(AllDetails==null)
        AllDetails="";
          if(Details.length()==0)
              return AllDetails;
          else
          {
          MapWallet.put(_KEY,AllDetails+ Details);
           DBWALLET.commit();
          }
        return "OK";
    }
    
    String DeleteUserWallet(String key)
    {
     //String _KEY="0000000000";
       
       
        Map<String,String> MapWallet = DBWALLET.hashMap(MAP_WALLET);
          
        if(key==null)
            return "ERROR!";
        if(key.length()<1)
           return "ERROR!";
        
         try
         {
          MapWallet.remove(key) ;
           DBWALLET.commit();
          }
         catch(Exception ex){}
        return "OK";
    }
    
    String TransAccountCurrent() //if details="", return all data
    {
        
        String allData="";
        String VehecleInfo="";
          Map<String,String> MapVehecle = DBVEHECLE.hashMap(MAP_VEHECLE);
              
        Map<String,String> MapWallet_TRANS = DBWALLET_TRANS.hashMap(MAP_WALLET_TRANS);
        String Details="";
        for(String KEY:MapWallet_TRANS.keySet())
        {
            try
            {
                if(KEY.contains("@")) //KEY not is 0000000000
                {
                    if( Double.parseDouble(MapWallet_TRANS.get(KEY))>0.0)
                    {
                         VehecleInfo=MapVehecle.get(KEY);
                            if(VehecleInfo==null)  VehecleInfo="X";
                            if(VehecleInfo.length()==0) VehecleInfo="X";
             
                    Details=Details+KEY.split("@")[0]+VehecleInfo+":";
                    }
                }
            }catch(Exception ex){
            int i=0;
            }
        }
               return Details;
    }
    
    String TransAccount(String Details) //if details="", return all data
    {
        String _KEY="0000000000";
          Map<String,String> MapWallet_TRANS = DBWALLET_TRANS.hashMap(MAP_WALLET_TRANS);
          String AllDetails="";
          try
          {
          AllDetails=MapWallet_TRANS.get(_KEY);
          }catch(Exception ex){AllDetails="NIL";}
            if(AllDetails==null)
            AllDetails="";
        
          if(Details.length()==0)
              return AllDetails;
          else
          {
          MapWallet_TRANS.put(_KEY,AllDetails+ Details);
           DBWALLET_TRANS.commit();
          }
        return "OK";
    }
    
    String TransAccountUser(String UserID) //if details="", return all data
    {
         String _KEY="UNKNOWN";
         
         UserID=UserID.toUpperCase();
         if(!UserID.contains("@") && !UserID.contains("."))
         {
         //_KEY=SERVER.DBlogin.GetKeyDetails(UserInfo);
             _KEY=DataBase.KeyUser.get(UserID);
         }
         else
             _KEY=UserID;
         
         if(_KEY==null)
         {
          //_KEY="UNKNOWN";
         return "Error";
         }
        
          Map<String,String> MapWallet_TRANS = DBWALLET_TRANS.hashMap(MAP_WALLET_TRANS);
          
          
          String AllDetails="";
          try
          {
          AllDetails=MapWallet_TRANS.get(_KEY);
          }catch(Exception ex){AllDetails="NIL";}
            if(AllDetails==null)
            AllDetails="0";
        
              return AllDetails;
          
       
    }
    
    
    
    String AdminAccount(String Details) //if details="", return all data
    {
        String _KEY="0000000000";
          Map<String,String> MapWallet_ADMIN = DBWALLET_ADMIN.hashMap(MAP_WALLET_ADMIN);
          String AllDetails="";
          try
          {
          AllDetails=MapWallet_ADMIN.get(_KEY);
          }catch(Exception ex){AllDetails="NIL";}
            
          if(AllDetails==null)
            AllDetails="";
        
          if(Details.length()==0)
              return AllDetails;
          else
          {
          MapWallet_ADMIN.put(_KEY,AllDetails+ Details);
           DBWALLET_ADMIN.commit();
          }
        return "OK";
    }
    
    
}


public class UserPro
{
     String Separator="|";
     int GetUserPropertyNo(String UserID)
     {
         String _KEY="UNKNOWN";
         UserID=UserID.toUpperCase();
         if(!UserID.contains("@") && !UserID.contains("."))
         {
         //_KEY=SERVER.DBlogin.GetKeyDetails(UserInfo);
             _KEY=DataBase.KeyUser.get(UserID);
         }
         else
             _KEY=UserID;
         
         if(_KEY==null)
             _KEY="UNKNOWN";
         
          Map<String,String> DevProp = DBProp.hashMap(MAP_PROP);
         //DevProp.put(UserID,String.valueOf(PropertyNo));
         return Integer.parseInt(DevProp.get(_KEY));// Integer.parseInt(DevProp.put(UserID,String.valueOf(PropertyNo)));
     }
     
     int SetUserPropertyNo(String UserID,int PropertyNo)
     {
         String _KEY="UNKNOWN";
         UserID=UserID.toUpperCase();
         if(!UserID.contains("@") && !UserID.contains("."))
         {
         //_KEY=SERVER.DBlogin.GetKeyDetails(UserInfo);
             _KEY=DataBase.KeyUser.get(UserID);
         }
         else
             _KEY=UserID;
         
         
         if(_KEY==null)
             _KEY="UNKNOWN";
         
          Map<String,String> DevProp = DBProp.hashMap(MAP_PROP);
         //DevProp.put(UserID,String.valueOf(PropertyNo));
         DevProp.put(_KEY,String.valueOf(PropertyNo));
         DBProp.commit();
         return PropertyNo;
     }
      
     String SetUserPropertyCustom(String UserKey,String Property)
     {
         
         return "";
     }
}
     
public class ROUTER
{
    
    public  HashMap<String,String> LoadRouter(String Token)
         {
             Map<String,String> RouterMap = DBROUTER.hashMap(MAP_ROUTER);
             for(String UID:RouterMap.keySet())
             {
                 String _KEY=UID.split(":")[0]+":"+UID.split(":")[2];
                 if(!UID.split(":")[1].equals(Token))
                     continue;
                 String devlist=RouterMap.get(UID);
                 if(Token.equals("I"))
                 IntRouter.put(_KEY,devlist);
                 if(Token.equals("E"))
                 ExtRouter.put(_KEY,devlist);
                 int i=0;
             }
             DBROUTER.commit();
             if(Token.equals("I"))
             return IntRouter;
             else
                 return ExtRouter;
         }
        
    
     public String RemoveRouter(String UID, String RouterID, String Token)
     {
         UID=UID.toUpperCase();
           if(!UID.contains("@"))
          {
          LoginDB _lDB=new LoginDB();
         UID=_lDB.GetUserID(UID);
          }
          
          Map<String,String> RouterMap = DBROUTER.hashMap(MAP_ROUTER);
          RouterMap.remove(UID+":"+Token+":"+RouterID);
           DBROUTER.commit();
          String Result=GetAllRouterList(UID,Token);
          LoadRouter(Token);
         return Result;
     }
    
     
     public String RemoveInternalRouterNodes(String UID, String RouterID,String DEV_ID)
     {
         UID=UID.toUpperCase();
           if(!UID.contains("@"))
          {
          LoginDB _lDB=new LoginDB();
         UID=_lDB.GetUserID(UID);
          }
           String Devidlist="";
          
           if(DEV_ID==null) DEV_ID="";
          if(DEV_ID.length()>0)
          {
           Map<String,String> RouterMap = DBROUTER.hashMap(MAP_ROUTER);
           if(RouterMap.containsKey(UID+":I:"+RouterID))
           {
             List<String> alreadyNodes= new ArrayList<>(Arrays.asList(GetRouterNodeList(UID, RouterID,"I").split(",")));
            if(alreadyNodes.contains(DEV_ID))
            {
                alreadyNodes.remove(DEV_ID);
                String NewNodeList="";
                for(String nid:alreadyNodes)
                {
                    if(NewNodeList.length()==0)
                        NewNodeList=nid;
                    else
                        NewNodeList=NewNodeList+","+nid;
                }
                
                RouterMap.put(UID+":I:"+RouterID, NewNodeList);
             DBROUTER.commit();
                LoadRouter("I");
            }
            else
                return "FAIL:NO_ID";
           }
           else
               return "FAIL:NO_ROUTER";
           
          }
          else
              return "FAIL:NO_INPUT_ID";
              
              Devidlist="OK:"+GetRouterNodeList(UID, RouterID,"I");
          
          return Devidlist;
     }
     
     
     public String AddInternalRouterAndNodes(String UID, String RouterID, List<String>DEV_ID)
     {
         UID=UID.toUpperCase();
           if(!UID.contains("@"))
          {
          LoginDB _lDB=new LoginDB();
         UID=_lDB.GetUserID(UID);
          }
           String Devidlist="";
          Map<String,String> RouterMap = DBROUTER.hashMap(MAP_ROUTER);
          if(DEV_ID!=null)
            for(String devid:DEV_ID)
            {
                if(Devidlist.length()==0)
                    Devidlist=devid;
                else
                Devidlist=Devidlist+","+devid;
            }
          
     if(!RouterMap.containsKey(UID+":I:"+RouterID)) //The router does not exist
     {
          RouterMap.put(UID+":I:"+RouterID, Devidlist);
             DBROUTER.commit();
     }
     else  //router exist
     {
         if(Devidlist!=null)
         {
             boolean newnode=false;
            // List<String> alreadyNodes= new ArrayList<>(Arrays.asList(GetInternalRouterNodeList(UID, RouterID).split(",")));
            String alreadyNodes=GetRouterNodeList(UID, RouterID,"I");
             for( String tobeaddedNodeid:DEV_ID)
            {
                if(!alreadyNodes.equals(tobeaddedNodeid) && !alreadyNodes.startsWith(tobeaddedNodeid+",") && !alreadyNodes.contains(","+tobeaddedNodeid+",") && !alreadyNodes.contains(","+tobeaddedNodeid))
                {
                    newnode=true;
                    if(alreadyNodes.length()==0)
                        alreadyNodes=tobeaddedNodeid;
                    else
                        alreadyNodes=alreadyNodes+","+tobeaddedNodeid;
                }
            }
             
             if(newnode)
             {
              RouterMap.put(UID+":I:"+RouterID, alreadyNodes);
             //DBROUTER.commit();
             }
         }
         
          
         
     }
           
      DBROUTER.commit();
     
     
       
         String Result=GetAllRouterList(UID,"I");
         LoadRouter("I");
         return Result;
     }
     
     public String GetAllRouterList(String UID, String Token)
     {
         UID=UID.toUpperCase();
           if(!UID.contains("@"))
          {
          LoginDB _lDB=new LoginDB();
         UID=_lDB.GetUserID(UID);
          }
           
           String Devidlist="";
          Map<String,String> RouterMap = DBROUTER.hashMap(MAP_ROUTER);
            for(String routerkeys:RouterMap.keySet())
            {
                if(routerkeys.startsWith(UID+":"+Token+":"))
                {
                    if(Devidlist.length()==0)
                        Devidlist=routerkeys.split(":")[2];
                    else
                     Devidlist=Devidlist+","+routerkeys.split(":")[2];
                }
               
            }
            
          List<String> ALLRO= new ArrayList<>(Arrays.asList(Devidlist.split(",")));
          
          for (String _ROID:ALLRO)
          {
               Devidlist=Devidlist+"\r\n";  
              Devidlist=Devidlist+_ROID+"->"+GetRouterNodeList(UID, _ROID, Token);
          }
           
         DBROUTER.commit();
         return Devidlist;
     }
     
     
     public String GetRouterNodeList(String UID, String RouterID, String Token)
     {
         UID=UID.toUpperCase();
           if(!UID.contains("@"))
          {
          LoginDB _lDB=new LoginDB();
         UID=_lDB.GetUserID(UID);
          }
           
             String Devidlist="";
          Map<String,String> RouterMap = DBROUTER.hashMap(MAP_ROUTER);
            for(String routerkeys:RouterMap.keySet())
            {
                if(routerkeys.equals(UID+":"+Token+":"+RouterID))
                {
                        Devidlist=RouterMap.get(routerkeys);
                        try
                        {
                        if(Token.equals("E"))
                            Devidlist=Devidlist.split(":")[1];
                        }catch (Exception ex){Devidlist="";}
                  break;
                }
               
            }
          DBROUTER.commit();
         return Devidlist;
     }
     
     
     
     
     
     /*******************External Router *****************/
     
          private String getHash(String inputHash)
          {
                 String MD5="";

                try
                {
                   byte[]  bytesOfMessage = inputHash.getBytes("UTF-8");
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    //MD5 = md.digest(bytesOfMessage);
                   MD5= DatatypeConverter.printHexBinary(md.digest(bytesOfMessage));
                 }catch(Exception ex){}
        return MD5;
          }
     
    private String strRev(String input)
          {
           StringBuilder input1 = new StringBuilder();
           input1.append(input);
           input=input1.reverse().toString(); 
              return input;
          }
         
     public String GenerateKey ()
         {
           String KeySeed= new SimpleDateFormat("dd.MM.yyyy_HH:mm:ss:SSS").format(Calendar.getInstance().getTime());
           KeySeed=  getHash(strRev(KeySeed));
           return KeySeed;
         }
       
     public String AddExternalRouterAndNodes(String UID, String RouterID, List<String>DEV_ID)
     {
         UID=UID.toUpperCase();
           if(!UID.contains("@"))
          {
          LoginDB _lDB=new LoginDB();
         UID=_lDB.GetUserID(UID);
          }
           
           String Devidlist="";
           String EROKEY="";
          Map<String,String> RouterMap = DBROUTER.hashMap(MAP_ROUTER);
          if(DEV_ID!=null)
            for(String devid:DEV_ID)
            {
                if(Devidlist.length()==0)
                    Devidlist=devid;
                else
                Devidlist=Devidlist+","+devid;
            }
          
          
     if(!RouterMap.containsKey(UID+":E:"+RouterID)) //The router does not exist
     {
         EROKEY=GenerateKey();
          RouterMap.put(UID+":E:"+RouterID,EROKEY+":"+Devidlist);
             DBROUTER.commit();
     }
     else  //router exist
     {
         if(Devidlist!=null)
         {
             boolean newnode=false;
            // List<String> alreadyNodes= new ArrayList<>(Arrays.asList(GetInternalRouterNodeList(UID, RouterID).split(",")));
            String alreadyNodes=GetRouterNodeList(UID, RouterID,"E");
            EROKEY=GetExtRouterKey(UID, RouterID);
            
             for( String tobeaddedNodeid:DEV_ID)
            {
                if(!alreadyNodes.equals(tobeaddedNodeid) && !alreadyNodes.startsWith(tobeaddedNodeid+",") && !alreadyNodes.contains(","+tobeaddedNodeid+",") && !alreadyNodes.contains(","+tobeaddedNodeid))
                {
                    newnode=true;
                    if(alreadyNodes.length()==0)
                        alreadyNodes=tobeaddedNodeid;
                    else
                        alreadyNodes=alreadyNodes+","+tobeaddedNodeid;
                }
            }
             
             if(newnode)
             {
              RouterMap.put(UID+":E:"+RouterID,EROKEY+":"+ alreadyNodes);
             //DBROUTER.commit();
             }
         }
     }
           
      DBROUTER.commit();
         String Result=GetAllRouterList(UID,"E");//.split(":")[1];
         LoadRouter("E");
         return Result;
     }
     
     
     public String RemoveExternalRouterNodes(String UID, String RouterID,String DEV_ID)
     {
         UID=UID.toUpperCase();
           if(!UID.contains("@"))
          {
          LoginDB _lDB=new LoginDB();
         UID=_lDB.GetUserID(UID);
          }
           String Devidlist="";
          
           if(DEV_ID==null) DEV_ID="";
          if(DEV_ID.length()>0)
          {
           Map<String,String> RouterMap = DBROUTER.hashMap(MAP_ROUTER);
           if(RouterMap.containsKey(UID+":E:"+RouterID))
           {
              String _key=GetExtRouterKey(UID, RouterID);//_roNodelist.split(":")[0];
             List<String> alreadyNodes= new ArrayList<>(Arrays.asList(GetRouterNodeList(UID, RouterID,"E").split(",")));
            if(alreadyNodes.contains(DEV_ID))
            {
                alreadyNodes.remove(DEV_ID);
                String NewNodeList="";
                for(String nid:alreadyNodes)
                {
                    if(NewNodeList.length()==0)
                        NewNodeList=nid;
                    else
                        NewNodeList=NewNodeList+","+nid;
                }
                
                RouterMap.put(UID+":E:"+RouterID,_key+":"+NewNodeList);
             DBROUTER.commit();
                LoadRouter("E");
            }
            else
                return "FAIL:NO_ID";
           }
           else
               return "FAIL:NO_ROUTER";
           
          }
          else
              return "FAIL:NO_INPUT_ID";
              
              Devidlist="OK:"+GetRouterNodeList(UID, RouterID,"E");
          
          return Devidlist;
     }
     
     
     
     
     public String GetExtRouterKey(String UID, String RouterID)
     {
         UID=UID.toUpperCase();
           if(!UID.contains("@"))
          {
          LoginDB _lDB=new LoginDB();
         UID=_lDB.GetUserID(UID);
          }
           
             String Devidlist="";
          Map<String,String> RouterMap = DBROUTER.hashMap(MAP_ROUTER);
            for(String routerkeys:RouterMap.keySet())
            {
                if(routerkeys.equals(UID+":E:"+RouterID))
                {
                        Devidlist=RouterMap.get(routerkeys);
                        try
                        {
                        Devidlist=Devidlist.split(":")[0];
                        }catch (Exception ex){Devidlist="";}
                  break;
                }
               
            }
          DBROUTER.commit();
         return Devidlist;
     }
     
     
     
     
     
     
}

public class KeyDevice
{
    public KeyDevice()
    {
        
    }
    String Separator="|";
    
      /********************KEY DB *****************/
    public boolean isKeyExist(String MASTER_KEY)
    {

        if(DataBase.KeyUser
                .containsKey(MASTER_KEY))
            return true;
        else
                return false;
    }

    public String KeytoUID(String KEY)
    {
        
        if(DataBase.KeyUser.containsKey(KEY))
            return DataBase.KeyUser.get(KEY);
        else
                return null;
    }


    /*
    public List<String> getKeys()
    {
          Map<String,NodeGroup.NG> DevMap = DBKeys.hashMap(MAP_KEYS);
        //    Set<String> KeyCollection=DevMap.keySet();
     List<String> KeyList = new ArrayList<String>(DevMap.keySet());
    
          return KeyList;
         
    }
  */  
    private int totalChild(String MASTER_KEY, String ChildNodeID)
    {
          if(!MASTER_KEY.contains("@"))
          {
          LoginDB _lDB=new LoginDB();
         MASTER_KEY=_lDB.GetUserID(MASTER_KEY);
          }
          
        if(MASTER_KEY.equals(ChildNodeID))
            return 0;
        
        Map<String,NodeGroup.NG> DevMap = DBKeys.hashMap(MAP_KEYS);
        NodeGroup.NG _NG=new NodeGroup.NG();
        //NodeGroup.NodeInfo NINF=new NodeGroup.NodeInfo();
      List<String> AllChildNode=new ArrayList<String>();
        
        Set<String> KeyCollection=DevMap.keySet();
        for(String eachnodekey:KeyCollection)
        {
            if(eachnodekey.startsWith(MASTER_KEY))
            {
                    _NG=  DevMap.get(eachnodekey);
                if(_NG!=null)
                if(_NG.Members.containsKey(ChildNodeID) || eachnodekey.equals(ChildNodeID))
                {
                    if(eachnodekey.length()>0)
                  AllChildNode.add(eachnodekey);
                    else
                        DevMap.remove("");
                }
                
            }
        }
        // DBKeys.commit();
         
       return AllChildNode.size();
    }
    
    public boolean IsChildExist(String MASTER_KEY, String ChildNodeID)
    {
    
        if(totalChild(MASTER_KEY, ChildNodeID) >0)
            return true;// "FAIL:"+AllChildNode.size();
        else
            return false;//"SUCCESS:0";
        
        //return "";
    }
    
    
    public boolean IsMultipleChildExist(String MASTER_KEY, String ChildNodeID)
    {
        if(totalChild(MASTER_KEY, ChildNodeID)>1)
            return true;// "FAIL:"+AllChildNode.size();
        else
            return false;//"SUCCESS:0";
        
        //return "";
    }
    
    
    
     public String UpdateNode(String MASTER_KEY, String OldNodeID, String NewNodeID)//, NodeGroup.NodeInfo NewNodeInfo)
     {
          if(!MASTER_KEY.contains("@"))
          {
          LoginDB _lDB=new LoginDB();
         MASTER_KEY=_lDB.GetUserID(MASTER_KEY);
          }
          
        if(IsChildExist(MASTER_KEY,NewNodeID))
         return "FAIL:New node exists";
          
           String UID="0000000000";
          LoginDB _lDB=new LoginDB();
          UID=_lDB.GetUserID(MASTER_KEY);
          
      NodeGroup.NodeInfo NINF=new NodeGroup.NodeInfo();
      String _PKey= GetMyParentKey(/*MASTER_KEY*/UID, OldNodeID);
      String _MyCurKey=_PKey+"|"+OldNodeID;
      String _MyUpdatedKey=_PKey+"|"+NewNodeID;
        Map<String,NodeGroup.NG> DevMap = DBKeys.hashMap(MAP_KEYS);
        
        ////Operation with Parent Node
        //the node may have multiple parent
        NodeGroup.NG _NG=new NodeGroup.NG();
        
        Set<String> KeyCollection=DevMap.keySet();
        for(String eachnodekey:KeyCollection)
        {
            if(eachnodekey.startsWith(MASTER_KEY))
            {

                _NG=  DevMap.get(eachnodekey);
                if(_NG!=null)
                if(_NG.Members.containsKey(OldNodeID))
                {
                    //NINF.hasSubNode=_NG.Members.get(OldNodeID).hasSubNode;  // extract old data info
                    NINF=_NG.Members.get(OldNodeID);  // extract old data info

                    _NG.Members.remove(OldNodeID);
                    _NG.Members.put(NewNodeID,NINF);
                      DevMap.put(eachnodekey, _NG);

                }
           }
        }
         DBKeys.commit();
         
        
        String _tempKey="";
        for(String eachkey:DevMap.keySet())
        {
        if(eachkey.startsWith(MASTER_KEY))
            {
            
            _tempKey="";
            String[] _xtemp=eachkey.split("\\|");
              List<String> KeySubSet = (List) Arrays.asList(_xtemp);
             //Set<String> KeySubSet = new HashSet<String>(Arrays.asList(_xtemp));
            
             
             if(KeySubSet.contains(OldNodeID))//if(eachkey.contains("|"+OldNodeID+"|")||eachkey.endsWith("|"+OldNodeID))
            {
                      for(String _subKey:KeySubSet)
                 {
                     if(_tempKey.length()>0)
                          _tempKey=_tempKey+"|";
                     if(_subKey.equals(OldNodeID))
                         _subKey=NewNodeID;
                     
                     _tempKey=_tempKey+_subKey;
                 }
            
                
            _NG=DevMap.get(eachkey);
            DevMap.remove(eachkey);
           DevMap.put(_tempKey,_NG);

            }
         }
        }
        
        DBKeys.commit();
                
         String _info=GetNodeTree(MASTER_KEY, "");
         return "SUCCESS:"+_info;
     }
    public String DeleteNodeTree(String MASTER_KEY, String FromChildID)
    {  
        if(!MASTER_KEY.contains("@"))
          {
          LoginDB _lDB=new LoginDB();
         MASTER_KEY=_lDB.GetUserID(MASTER_KEY);
          }
          
        
        
        if(IsMultipleChildExist(MASTER_KEY,FromChildID))
         return "FAIL:Multiple Child exist";
        
        if(!IsChildExist(MASTER_KEY,FromChildID))
         return "FAIL:No Child";
         
        String _NodeTree=GetNodeTree(MASTER_KEY,FromChildID);
        String[] _nodeStr=_NodeTree.split("\n");
        List<String> _NodeList=new ArrayList<String>();
        
          //Clear Subnodes
        for(int _i=0;_i<_nodeStr.length ;_i++)
        {
            if(_nodeStr[_i].contains("->"))
            {
                try{
                   String[]  _splitinfo=_nodeStr[_i].split("->")[1].split("\\,");
                     for(int _j=0;_j<_splitinfo.length;_j++)
                      _NodeList.add(_splitinfo[_j]);
                }
                catch(Exception ex){}
            }
        }
        
        
        Map<String,NodeGroup.NG> DevMap = DBKeys.hashMap(MAP_KEYS);
         
        String _ParentKet="";
          NodeGroup.NG _NG=new   NodeGroup.NG();
        
       for(String _child:_NodeList)
        {
            try
            {
                Set<String> SS=DevMap.keySet();
          _ParentKet=GetMyParentKey(MASTER_KEY, _child);    
              _NG=DevMap.get(_ParentKet);
          boolean stat=  DevMap.remove(_ParentKet,_NG);
           _NG=  DevMap.remove(_ParentKet);
     
            }catch(Exception ex){}
        }
       DBKeys.commit();
      
       
       //Clear if node is child
        _ParentKet=GetMyParentKey(MASTER_KEY, FromChildID);    
          _NG=DevMap.get(_ParentKet);
          NodeInfo NI= _NG.Members.get(_ParentKet);
          
          //CHANDAN@UBIKEYS.COM|123|123_1
       DevMap.remove(_ParentKet);
       _NG.Members.remove(FromChildID);
       if(_NG.Members.size()>0)
           DevMap.put(_ParentKet, _NG);
            DBKeys.commit();
       
       
       //Clear if Node is root
            _NG=DevMap.get(MASTER_KEY);
         if(_NG.Members.containsKey(FromChildID))
         {
             _NG.Members.remove(FromChildID);
             DevMap.replace(MASTER_KEY, _NG);
             
            DBKeys.commit();
         }
      
      
      String _allChild=GetNodeTree(MASTER_KEY,"");
        return "SUCCESS:"+_allChild;
    }
     
    public int GetDepth(String MASTER_KEY)
         {
          
             if(!MASTER_KEY.contains("@"))
          {
          LoginDB _lDB=new LoginDB();
         MASTER_KEY=_lDB.GetUserID(MASTER_KEY);
          }
          
            Map<String,NodeGroup.NG> DevMap = DBKeys.hashMap(MAP_KEYS);
            Set<String> AllKey= DevMap.keySet();
            if(!DevMap.containsKey(MASTER_KEY))
            {
                DBKeys.commit();
                 return 0;
            }
               
            int Count=0;
             for(String eachkey:DevMap.keySet())
             {
                 if(eachkey.startsWith(MASTER_KEY))
                Count++;
              }
             DBKeys.commit();
             return Count;
         }
     
    
    public String GetNodeTree(String MASTER_KEY, String FromChildID)
         {
            
             if(!MASTER_KEY.contains("@"))
          {
          LoginDB _lDB=new LoginDB();
         MASTER_KEY=_lDB.GetUserID(MASTER_KEY);
          }
            
          
            Map<String,NodeGroup.NG> DevMap = DBKeys.hashMap(MAP_KEYS);
            Set<String> AllKey= DevMap.keySet();
     
            Set<String> NodeIDList=new HashSet<String>();
            
            if(FromChildID==null)
                FromChildID="";
            
             if(!DevMap.containsKey(MASTER_KEY))
                 return "ERROR! Invalid KEY";
               
             String AllInfo="";
             String ShortInfo="";
             //Set<String> _ss=DevMap.keySet();
             List<String> Keylist = new ArrayList<String>(DevMap.keySet());
            Keylist.sort((s1, s2) -> Math.abs(s1.length() - "intelligent".length()) - Math.abs(s2.length() - "intelligent".length()));
            
             for(String eachkey:Keylist)
             {
                 if(eachkey.startsWith(MASTER_KEY))
                 {
                     
                   int _count= eachkey.split("\\|").length;
                   String[] _substring=eachkey.split("\\|");
                  List<String> _Keylist = (List) Arrays.asList(_substring);
                  if(!_Keylist.contains(FromChildID) && FromChildID.length()>0)
                  continue;
                    
                   //if(!_substring[_count-1].equals(FromChildKey))
                   //continue;
                   
                   AllInfo=AllInfo+ _substring[_count-1];
                   if(!NodeIDList.contains(_substring[_count-1]))
                     NodeIDList.add(_substring[_count-1]);
                        if(DevMap.get(eachkey)!=null)
                        {
                            AllInfo=AllInfo+"->";
                            for(String _subset: DevMap.get(eachkey).Members.keySet())
                            {
                               if(!NodeIDList.contains(_subset))
                                NodeIDList.add(_subset);
                            AllInfo=AllInfo+ _subset+",";
                            }
                        }
                     
                     int ind = AllInfo.lastIndexOf(",");
                    if( ind>=0 )
                     AllInfo = new StringBuilder(AllInfo).replace(ind, ind+1,"").toString();
                    
                    
                     AllInfo=AllInfo+"\n";
                 }
                 
             }
            
             try
             {
                 NodeIDList.remove(FromChildID);
                  NodeIDList.remove(MASTER_KEY);
                 for(String _child:NodeIDList)
                 {
                     
                     ShortInfo=ShortInfo+_child+"|";
                 }
                 int ind = ShortInfo.lastIndexOf("|");
                    if( ind>=0 )
                     ShortInfo = new StringBuilder(ShortInfo).replace(ind, ind+1,"").toString();
                    
                 if(ShortInfo.length()>0)
                 ShortInfo="{"+ShortInfo+"}\n";
             }catch(Exception ex){}
             
             DBKeys.commit();
             return ShortInfo+AllInfo;
         }
     
     public String GetMyChildIDs(String MASTER_KEY, String myID)
    {
        
          if(!MASTER_KEY.contains("@"))
          {
          LoginDB _lDB=new LoginDB();
         MASTER_KEY=_lDB.GetUserID(MASTER_KEY);
          }
          
        String ChildIDs="";
        String ALLChildIDs="";
        //String Filter1="";
        try
        {
            if(myID.toUpperCase().equals("ROOT"))
                myID="";
            String[] AllInfoList=GetNodeTree(MASTER_KEY,myID).split("\n");
            Set<String> AllInfoSet = new HashSet<String>(Arrays.asList(AllInfoList));
            
            for (String info:AllInfoSet)
            {
                if(myID.length()>0 && !myID.toUpperCase().equals("ROOT"))
                {
                    if(info.startsWith(myID+"->"))
                    ChildIDs=info.split(">")[1];
                 }
                else
                {
                    if(info.startsWith(MASTER_KEY+"->"))
                    {
                        ChildIDs=info.split(">")[1];
                        int i=0;
                    }
                }
            }
            }catch(Exception ex){}
        
                    int ind = ChildIDs.lastIndexOf(",");
                    if( ind>=0 )
                    {
                     ChildIDs = new StringBuilder(ChildIDs).replace(ind, ind+1,"").toString();
                    
                    }

        
        return ChildIDs;
    }
    
    
     public String GetMyAllChildIDs(String MASTER_KEY, String myID)
    {
          if(!MASTER_KEY.contains("@"))
          {
          LoginDB _lDB=new LoginDB();
         MASTER_KEY=_lDB.GetUserID(MASTER_KEY);
          }
          
        String ChildIDs="";
        //String Filter1="";
        try
        {
            String AllInfo=GetNodeTree(MASTER_KEY,myID);
             if(AllInfo.contains("{")&& AllInfo.contains("}"))
            ChildIDs=AllInfo.split("\\}")[0].split("\\{")[1];
             
             
            //   int ind = ChildIDs.lastIndexOf("|");
            //    if( ind>=0 )
            //        ChildIDs = new StringBuilder(ChildIDs).replace(ind, ind+1,"").toString();
             
            }catch(Exception ex){}
        return ChildIDs;
    }
    
    private String GetMyParentKey(String MASTER_KEY, String ChildNodeID)
    {
        
          if(!MASTER_KEY.contains("@"))
          {
          LoginDB _lDB=new LoginDB();
         MASTER_KEY=_lDB.GetUserID(MASTER_KEY);
          }
          
        Map<String,NodeGroup.NG> DevMap = DBKeys.hashMap(MAP_KEYS);
            String MyParent="";
         Collection<String> _keys=DevMap.keySet();
             for(String _IndividualKey:_keys)  //get all Keys under Group_keys and take one by one key
            {
                 if(_IndividualKey.startsWith(MASTER_KEY))     //Filter only keys that Starts with masterkey
                 {
                      
                     try
                     {
                    if(DevMap.get(_IndividualKey).Members.get(ChildNodeID).hasSubNode)
                    {
                        MyParent=_IndividualKey;
                        break;
                    }
                     }catch(Exception ex){
                     //MyParent="";
                     }

                 }
                 
             }
           DBKeys.commit(); 
        return MyParent;
    }
    
     
     public String GetMyParentID(String MASTER_KEY, String ChildNodeID)
    {
          if(!MASTER_KEY.contains("@"))
          {
          LoginDB _lDB=new LoginDB();
         MASTER_KEY=_lDB.GetUserID(MASTER_KEY);
          }
          if(MASTER_KEY.equals(ChildNodeID))
              return "[ERROR:Invalid Child ID]";
          
        Map<String,NodeGroup.NG> DevMap = DBKeys.hashMap(MAP_KEYS);
            String MyParentID="";
         Collection<String> _keys=DevMap.keySet();
             for(String _IndividualKey:_keys)  //get all Keys under Group_keys and take one by one key
            {
                 if(_IndividualKey.startsWith(MASTER_KEY))     //Filter only keys that Starts with masterkey
                 {
                      
                     try
                     {
                    if(DevMap.get(_IndividualKey).Members.get(ChildNodeID).hasSubNode)
                    {
                          int _count= _IndividualKey.split("\\|").length;
                          String[] _substring=_IndividualKey.split("\\|");
                 
                        MyParentID=_substring[_count-1];
                        break;
                    }
                    
                       
                     }catch(Exception ex){
                      //MyParentID="[ERROR:child does not exist]";
                     }

                 }
                 
             }
     
                if(MASTER_KEY.equals(MyParentID))
              return "ROOT";
                
             if (MyParentID.length()==0)
                MyParentID="[ERROR:Parent does not exist]";
            
     DBKeys.commit();
        return MyParentID;
    }
    
     
     public String AddNode(String MASTER_KEY, String NodeID, boolean hasChildSubNode, String ChildNodeInfo)
     {
           if(!MASTER_KEY.contains("@"))
          {
          LoginDB _lDB=new LoginDB();
         MASTER_KEY=_lDB.GetUserID(MASTER_KEY);
          }
          
           
         String Result=AddSubNode(MASTER_KEY,null,NodeID,hasChildSubNode,ChildNodeInfo);
         return Result;
     }
     
     public String AddSubNode(String MASTER_KEY, String ParentNodeID, String ChildNodeID, boolean hasChildSubNode, String ChildNodeInfo)
         {
            
           
       
          if(!MASTER_KEY.contains("@"))
          {
          LoginDB _lDB=new LoginDB();
         MASTER_KEY=_lDB.GetUserID(MASTER_KEY);
          }
          
               
                if(IsChildExist(MASTER_KEY,ChildNodeID))
         return "FAIL:Child exists,Cant add";
                
             try
             {
           // ConcurrentNavigableMap<String,String> DevMap = DBLogin.treeMap(MAP_KEYS);
            Map<String,NodeGroup.NG> DevMap = DBKeys.hashMap(MAP_KEYS);
            if(ParentNodeID==null)
                ParentNodeID="";
           
        //    String ParentOfParentNodeID="";
            Set<String> _set= DevMap.keySet();
            
    
         
            
            /***************** Is Adding Child allowed *******************/
            boolean isChildEnable=false;
            String myparent=GetMyParentKey(MASTER_KEY, ParentNodeID);
                    if(ParentNodeID.length()>0)
                    if(DevMap.get(myparent).Members.get(ParentNodeID).hasSubNode)
                        isChildEnable=true;
                 
            
          if(!isChildEnable && !ParentNodeID.equals(""))
              return"ERROR! Permission not given at ParentNode["+ParentNodeID+"]";
    
          
          
                  
          String Rootstring="";
          if(myparent.length()==0)
            Rootstring=MASTER_KEY+ParentNodeID;
          else
             Rootstring=myparent+"|"+ParentNodeID;
              
          NodeGroup.NG _NG=DevMap.get(Rootstring);
          
            if(_NG==null)
            _NG=new NodeGroup.NG();
            
            NodeGroup.NodeInfo NIF=new NodeGroup.NodeInfo();
            NIF.hasSubNode=hasChildSubNode;
            NIF.NInfo=ChildNodeInfo;
            
            /**************** is Child already exist? *******************/
            if(_NG.Members.get(ChildNodeID)!=null)
                return "ERROR! Node Already Exists";
    
            //Write logic if Adding Child node allowed
           
            
            _NG.Members.put(ChildNodeID,NIF);
            
            DevMap.put(Rootstring,_NG);
            
            _NG= DevMap.get(Rootstring);
            DBKeys.commit();
            
            
             }
             catch (Exception ex){
             return "ERROR";
             }
    
             String Childs=GetNodeTree(MASTER_KEY, ParentNodeID);
             return Childs;
         }
      

     
     
     
     
     
    
}





      public class LoginDB
      {
          //ConcurrentMap map;//=DBLogin.hashMap("map").createOrOpen();
        
          public LoginDB()
          {
            //ConcurrentMap map=DBLogin.hashMap("map").createOrOpen();
        
          }
          private String getHash(String inputHash)
          {
                 String MD5="";

                try
                {
                   byte[]  bytesOfMessage = inputHash.getBytes("UTF-8");
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    //MD5 = md.digest(bytesOfMessage);
                   MD5= DatatypeConverter.printHexBinary(md.digest(bytesOfMessage));
                 }catch(Exception ex){}
        return MD5;
          }
          
          private String strRev(String input)
          {
           StringBuilder input1 = new StringBuilder();
           input1.append(input);
           input=input1.reverse().toString(); 
              return input;
          }
         
         public String GenerateKey ()
         {
           String KeySeed= new SimpleDateFormat("dd.MM.yyyy_HH:mm:ss:SSS").format(Calendar.getInstance().getTime());
           KeySeed=  getHash(strRev(KeySeed));
           return KeySeed;
         }
        
         
         public String GetUserID(String KEY)
         {
             String _UID="";
              ConcurrentNavigableMap<String,String> map = DBLogin.treeMap(MAP_LOGIN);
             for(String UID:map.keySet())
             {
                 String Val=map.get(UID).split(":")[1];
                if(Val.toUpperCase().equals(KEY.toUpperCase()))
                    _UID=UID;
                
             }
             
             return _UID;
         }
         
         
         public  HashMap<String,String> LoadUserKey()
         {
             ConcurrentNavigableMap<String,String> map = DBLogin.treeMap(MAP_LOGIN);
             UIDList.addAll(map.keySet());
             for(String UID:map.keySet())
             {
                 
                 String _KEY=map.get(UID).split(":")[1];
                 KeyUser.put(_KEY,UID);
                 int i=0;
             }
             DBLogin.commit();
             return KeyUser;
         }
        
        
         public boolean isUserValid(String username)
         {
             ConcurrentNavigableMap<String,String> map = DBLogin.treeMap(MAP_LOGIN);
              
             username=username.toUpperCase();
            // ConcurrentMap map=DBLogin.hashMap(MAP_LOGIN);//.createOrOpen();
             
              String Value=(String)map.get(username);
              DBLogin.commit();
                if(Value==null)
                    return false;
                else
                    return true;
         }
         
         public boolean deleteLoginDetails(String Username, String Pass)
         {
             ConcurrentNavigableMap<String,String> map = DBLogin.treeMap(MAP_LOGIN);
             ConcurrentMap<String,String> DevMap = DBKeys.hashMap(MAP_KEYS);
             Username=Username.toUpperCase();
            // ConcurrentMap map=DBLogin.hashMap(MAP_LOGIN).createOrOpen();
            //ConcurrentMap DevMap=DBKeys.hashMap(MAP_KEYS).createOrOpen();
           String Key="";
           String val="";
           boolean stat=false;
            try{
            Key= GetKeyDetails(Username);
             }catch(Exception ex){}
            try{
            val=DevMap.get(Key).toString();
             }catch(Exception ex){}
            try{
            stat=DevMap.remove(Key,val);
            }catch(Exception ex){}
            try{
            DevMap.remove(Key);
            }catch(Exception ex){}

            try
            {
            SERVER.WALLET.DeleteUserWallet(Key);
            }
            catch(Exception ex){}
             try
            {
            SERVER.VEHECLE.RemoveVehecleInfo(Key);
            }
            catch(Exception ex){}
             
            
           try
           {
                KeyUser.remove(Key);
           }
            catch(Exception ex){}
            
            
            try{
             val=map.get(Username).toString();
           stat= map.remove(Username,val);
            }catch(Exception ex){}
            
            try{
              map.remove(Username);   
                }catch(Exception ex){}
            
            DBLogin.commit();
            DBKeys.commit();
           
            LoadUserKey();
            
            return true;
         }
         
         
         public String insertLoginDetails(String Username, String Pass)
         {
         // Database data format Username,Pass:KEY
            //ConcurrentMap map=DBLogin.hashMap(MAP_LOGIN).createOrOpen();
            //ConcurrentMap DevMap=DBKeys.hashMap(MAP_KEYS).createOrOpen();
            ConcurrentNavigableMap<String,String> map = DBLogin.treeMap(MAP_LOGIN);
    
           // ConcurrentNavigableMap<String,String> DevMap = DBLogin.treeMap(MAP_KEYS);
            Map<String,NodeGroup.NG> DevMap = DBKeys.hashMap(MAP_KEYS);
     
           
            Username=Username.toUpperCase();
            String _KEY=GenerateKey ();
            map.put(Username,PASS_ALGO1(Pass)+":"+_KEY);
            //DevMap.put(_KEY,NodeGroup.DEFAULT);
            DevMap.put(Username,NodeGroup.DEFAULT);
            DBLogin.commit();
            DBKeys.commit();
            LoadUserKey();
            return _KEY;
         }

         public String updateKeyDetails(String Username, String Pass)
         {
            //ConcurrentMap map=DBLogin.hashMap(MAP_LOGIN).createOrOpen();
            //ConcurrentMap DevMap=DBKeys.hashMap(MAP_KEYS).createOrOpen();
            ConcurrentNavigableMap<String,String> map = DBLogin.treeMap(MAP_LOGIN);
            // ConcurrentNavigableMap<String,String> DevMap = DBLogin.treeMap(MAP_KEYS);
             //Map<String,NodeGroup.NG> DevMap = DBLogin.hashMap(MAP_KEYS);
     
             //Map<String,String> Propmap = DBProp.hashMap(MAP_PROP);
            
             Username=Username.toUpperCase();
             String _KEY=GenerateKey ();
           
             //String OldKey=map.get(Username);
             //String OldProp="0";
             map.replace(Username,PASS_ALGO1(Pass)+":"+_KEY);
            //UserKey.replace(Username,_KEY);
           
            /*
            if(OldKey!=null)
            {
               DevMap.remove(OldKey);
               OldProp=Propmap.get(OldKey);
               if(OldProp!=null)
                Propmap.remove(OldKey);
               }
             DevMap.put(_KEY,NodeGroup.DEFAULT);
             Propmap.put (_KEY,OldProp);
             */
             
            DBLogin.commit();
            //DBKeys.commit();
            return _KEY;
         }
         
         public String updatePassDetails(String Username, String Pass, String NewPass)
         {
             
             ConcurrentNavigableMap<String,String> map = DBLogin.treeMap(MAP_LOGIN);
             //Map<String,NodeGroup.NG> DevMap = DBKeys.hashMap(MAP_KEYS);
            Username=Username.toUpperCase();
            
             String _KEY=GetKeyDetails(Username);
            map.put(Username,PASS_ALGO1(NewPass)+":"+_KEY);
            //DevMap.put(_KEY,"");
            DBLogin.commit();
            //DBKeys.commit();
            return _KEY;
            
         }
         
         private String PASS_ALGO1(String Pass)
         {
          return (getHash(strRev(Pass)));   
         }
         
         
        public String GetLoginDetails(String Username)
         {
             ConcurrentNavigableMap<String,String> map = DBLogin.treeMap(MAP_LOGIN);
            Username=Username.toUpperCase();
            
        //ConcurrentMap map=DBLogin.hashMap(MAP_LOGIN).createOrOpen();
        //map.put(Username,Pass );
        String Val=(String)map.get(Username);
        // DBLogin.close(); 
         return Val;
         }
        
        public String GetKeyDetails(String Username)
         {
             Username=Username.toUpperCase();
             if(KeyUser.size()==0)
                 SERVER.LoadKeyList();
             String _Key="";
            if( KeyUser.containsValue(Username))
                {
                 for(String _K:KeyUser.keySet())
                 {
                     if(Username.equals(KeyUser.get(_K)))
                     {
                         _Key=_K;
                     break;
                     }
                 }
                }
            else
                return "";

            return _Key;
            /*
        //ConcurrentMap map=DBLogin.hashMap(MAP_LOGIN).createOrOpen();
        ConcurrentNavigableMap<String,String> map = DBLogin.treeMap(MAP_LOGIN);
            Username=Username.toUpperCase();
          
            
            
        //map.put(Username,Pass );
        String Val=(String)map.get(Username);
        if(Val==null)
            return "";
        String[] parts = Val.split(":");   // Val contains-> Pass:KEY
        if(parts.length!=2)
            return "0";
        else
        // DBLogin.close(); 
         return parts[1];
        */
        
         }
        
        
        
        
        public boolean VerifyLoginDetails(String Username, String Pass)
         {
        //ConcurrentMap map=DBLogin.hashMap(MAP_LOGIN).createOrOpen();
        //map.put(Username,Pass );
        
         ConcurrentNavigableMap<String,String> map = DBLogin.treeMap(MAP_LOGIN);
            Username=Username.toUpperCase();
            
        String Valx=(String)map.get(Username);
        String[] Val = Valx.split(":"); 
        
        String HPass=PASS_ALGO1(Pass);//getHash(strRev(Pass));
        // DBLogin.close(); 
         boolean result=false;
         if (HPass.equals(Val[0]))
             result=true;
         else
             result=false;
         return result;
         }
        
        public String GetAllUser()
        {
            String Users="";
         ConcurrentNavigableMap<String,String> map = DBLogin.treeMap(MAP_LOGIN);
        
         for(String UID:map.keySet())
         {
             Users=Users+"["+UID+"]";
             
         }
            
            
            return Users;
        }
        
          
      }
         
         
      
      
}
