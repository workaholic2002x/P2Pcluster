/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pushserver.mavenpush1;
import FileParser.ParseFile;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author workaholic
 */


    
   
    
 public class SERVER extends Thread  implements PropertyChangeListener {
    
   
     
    public static  String _ServerPort="";
    String DES_PATH="";
    String DUP_PATH="";
    //
   //public static HashMap<String,CNode> AllClientList=new HashMap<String,CNode>(); 
   
    static long AllClient=0;
    static long AllValidClient=0;
   static String MYIP="push.ubikeys.com:8083";
     
    public static HashMap<String,ServerNode> AllClientList=new HashMap<String,ServerNode>(); 
   public static HashMap<String, String> HMUSER=new HashMap<String,String>();
   public static HashMap<String,String> AllKeyUID=new HashMap<String,String>();
   // public static HashMap<String,String> UserKey=new HashMap<String,String>(); 
           
   //Un-registered Key User List
     public static List<String> TempKeyList=new ArrayList<String>(); 
     public static List<String> ROOTKeyList=new ArrayList<String>(); 

     public static HashMap<String,String> AllIntRouter=new HashMap<String,String>();
      public static HashMap<String,String> AllExtRouter=new HashMap<String,String>();
      
      public static HashMap<String,TOLLPARAMS> AllTollFare=new HashMap<String,TOLLPARAMS>();
    //    public static List<TOLLPARAMS> AllTollFare=new ArrayList<TOLLPARAMS>();
      
   boolean STARTFLG=false;
//   public HashMap<String,String> ClientListS=AllClientList;
   String AUTO_START="";
   long MAXLOAD=0;
       static boolean OVERLOAD_FLG=false;
   long VELOCITY=100;
        static boolean VELOCITY_FLG=false;
  
   int NODE_WAITFORCMD=5000;
   int NODE_COMCHK=300;
   
   
private static DataBase DB=new DataBase();
public static DataBase.LoginDB DBlogin= DB.new LoginDB(); 
public static DataBase.KeyDevice KeyDevice= DB.new KeyDevice(); 
public static DataBase.UserPro UserPro= DB.new UserPro(); 
public static DataBase.ROUTER ROUTER= DB.new ROUTER(); 
public static DataBase.UserWallet WALLET=DB.new UserWallet();
public static DataBase.VehecleInfo VEHECLE=DB.new VehecleInfo();
public static DataBase.TollInfo TOLLINF=DB.new TollInfo();
        
public static DataBase.VahanInfo VAHANINFO=DB.new VahanInfo();
public static TIME TIME=new TIME();
public static UserProperty UserP=new UserProperty();
Timer VelocityTimer = new Timer();


        SERVER(String FilePath)
    {
        boolean ParamCheck=true;
        
            HashMap<String, String> HM=new HashMap<String,String>();
            HM=ParseFile.GetVal(FilePath, "PServer");
            String _temp="";
            
             try
             {
                _ServerPort=HM.get("PServer.ServerPort");
                MYIP=HM.get("PServer.DNS");
                DES_PATH=HM.get("PServer.DESPATH");
                DUP_PATH=HM.get("PServer.ALTPATH");
                AUTO_START=HM.get("PServer.AUTOSTART_SERVER");
            
                _temp= HM.get("PServer.MAXLOAD");
                MAXLOAD= Long.parseLong(_temp);
                _temp= HM.get("PServer.VELOCITY");
                VELOCITY= Long.parseLong(_temp);
              
             }catch(Exception ex){System.out.println("PServer params config error\r\n");}
             
             
            //Node Config
            HashMap<String, String> HMNODE=new HashMap<String,String>();
            HMNODE=ParseFile.GetVal(FilePath, "NODE");
            
             try
             {
                 _temp= HMNODE.get("NODE.WAIT_FOR_CMD");
                 NODE_WAITFORCMD= Integer.parseInt(_temp);
                  _temp= HMNODE.get("NODE.COM_CHK");
                 NODE_COMCHK= Integer.parseInt(_temp);
             }catch(Exception ex){System.out.println("NODE params config error\r\n");}
             
               
           // HashMap<String, String> HMUSER=new HashMap<String,String>();
            HMUSER=ParseFile.GetVal(FilePath, "USER");
           //UserProperty UserP=new UserProperty();
               try
               {
                   UserP.MAXNODE=(List) Arrays.asList( HMUSER.get("USER.10MAXNODE").split(","));
                   UserP.TREE=(List) Arrays.asList( HMUSER.get("USER.1TREE").split(","));
                   UserP.NODEPERTREE=(List) Arrays.asList( HMUSER.get("USER.2NODEPERTREE").split(","));
                   UserP.TIMERPUSH=(List) Arrays.asList( HMUSER.get("USER.3TIMERPUSH").split(","));
                   UserP.HOLDPUSH=(List) Arrays.asList( HMUSER.get("USER.4HOLDPUSH").split(","));
                   UserP.HOLDBUFFER =(List) Arrays.asList( HMUSER.get("USER.5HOLDBUFFER").split(","));
                   UserP.MAILPUSH=(List) Arrays.asList( HMUSER.get("USER.6MAILPUSH").split(","));
                   UserP.SMSPUSH=(List) Arrays.asList( HMUSER.get("USER.7SMSPUSH").split(","));
                   UserP.INTSMSPUSH=(List) Arrays.asList( HMUSER.get("USER.8INTSMSPUSH").split(","));
                   UserP.SSLPUSH=(List) Arrays.asList( HMUSER.get("USER.9SSLPUSH").split(","));
                  
               }catch (Exception ex){System.out.println("USER params config error\r\n");}
             
             int i=0;
        if(_ServerPort==null || DES_PATH==null || DUP_PATH==null || AUTO_START==null)
            ParamCheck=false;
        
        if(ParamCheck==true && _ServerPort.compareTo("")!=0 && DES_PATH.compareTo("")!=0 && DUP_PATH.compareTo("")!=0 && AUTO_START.compareTo("")!=0)
        ParamCheck=true;
        else
            ParamCheck=false;
        
        //Load active KeyList
        LoadKeyList();
        LoadInRouterList();
        LoadExRouterList();
                
//Load user-key list
        //LoadUserKeyList();
        
        if(ParamCheck && (AUTO_START.compareTo("TRUE")==0))
       SERVERSTART(true);    
        
        
    }
        public void SERVERSTART(boolean START1_STOP0)
        {
            if(START1_STOP0)
            {
                STARTFLG=true;
                this.start();
            }
            else
                STARTFLG=false;
            
        }

        static long _TempVelocity=0;
        private void  Velocity_Check_thread()
        {
             VelocityTimer.scheduleAtFixedRate(new TimerTask() {
                  @Override
                  public void run()
                  {
                      if((AllClient-_TempVelocity)> VELOCITY)
                          VELOCITY_FLG=true;
                      else
                          VELOCITY_FLG=false;
                      
                    _TempVelocity=AllClient;
                  }
                }, 1, 60*1000);       //call at every 60 seconds
        }
        
       public void run()
        {
            try
        {
          Velocity_Check_thread();
          CNode  _node;//=new CNode();
         ServerSocket soc=new ServerSocket(Integer.parseInt(_ServerPort));
       // MYIP= InetscheduleAtFixedRateAddress.getLocalHost().getHostAddress();
        System.out.println("Push Server Started on Port Number:"+_ServerPort);
        do
        {
           // soc=new ServerSocket(Integer.parseInt(_ServerPort));
            System.out.println("Waiting for new Connections ...");
            
            
            
       //     ServerNode SNode=new ServerNode(soc.accept());
            
            
            //Server Overload Protection
           if(AllClient>=MAXLOAD)
                    OVERLOAD_FLG=true;
                else
                    OVERLOAD_FLG=false;
            
          
           

            if(!OVERLOAD_FLG && !VELOCITY_FLG)
        {
            
            _node=new CNode();
          
              _node.TimeOutSec=NODE_WAITFORCMD*1000;
           // _node.soc.setSoTimeout(_node.TimeOutSec-100);//(_node.TimeOutSec.intValue()));
            _node.soc=soc.accept();
            _node.isActive=true;
            _node.IP=_node.soc.getRemoteSocketAddress().toString();
          
            _node.COMCheck=NODE_COMCHK*1000;
               SERVER.AllClient++;
            ServerNode SNode=new ServerNode(_node);
         }
            else // Connection not allowed to Server
        {
           //soc.setr
           soc.accept().close();
           if(!OVERLOAD_FLG)
             System.out.println("Server Overloaded. Connection Refused ["+GetDateTime()+"]");
           if(!VELOCITY_FLG)
                System.out.println("Server attacked:VELOCITY. Connection Refused ["+GetDateTime()+"]");
        }
               
            Thread.sleep(1);
                   
                
        }while(STARTFLG);
        }catch(Exception ex){
        }
        
        }
        
       
       public static void LoadInRouterList()
       {
        AllIntRouter=ROUTER.LoadRouter("I");
       }
       
        public static void LoadExRouterList()
       {
        AllExtRouter=ROUTER.LoadRouter("E");
       }
        
       public static void LoadKeyList()
       {
           AllKeyUID= DBlogin.LoadUserKey();// KeyDevice.getKeys();
           int i=0;
       }
       

    /*
       public void LoadUserKeyList()
       {
           
         UserKey=DBlogin.LoadUserKey();
           int i=0;
       }
      */ 
      
       public static String GetDateTime()
       {
           return new SimpleDateFormat("dd.MM.yyyy_HH:mm:ss").format(Calendar.getInstance().getTime());
           
       }
       
    public byte[] hexStringToByteArray(String s)
   {
    int len = s.length();
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                             + Character.digit(s.charAt(i+1), 16));
    }
    return data;
   }
   
   public void createFile(String HEXSTRING) throws FileNotFoundException
   {
    String filenamex="";
        byte[] inbuf=hexStringToByteArray(HEXSTRING);
       
    File f=new File(filenamex);   //FIle already exist??
      
               FileOutputStream fout=new FileOutputStream(f);

               int ch;
                        int ctr=0;
                try
                {
                do
                {
                    
                    ch=(int)inbuf[ctr];
                    if(ch!=-1)
                    {
                        fout.write(ch);                    
                    }
                    ctr++;
                }while(ch!=-1);
                fout.close();
                }catch(Exception ex){}
       
   }
   
 
   
     @Override
    public void propertyChange(PropertyChangeEvent evt) {
        
        String property = evt.getPropertyName();
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
   
       
    
}






