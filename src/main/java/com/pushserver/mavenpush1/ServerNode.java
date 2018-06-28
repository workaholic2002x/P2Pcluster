/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pushserver.mavenpush1;

//import com.sun.xml.internal.ws.commons.xmlutil.Converter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author workaholic
 */
   
class ServerNode extends Thread
{
    
//Socket ClientSoc;
 CNode _node;
byte[] InputData;
   private PropertyChangeSupport changes = new PropertyChangeSupport(this);
CachedData chdata=new CachedData();

long TotalFile=0;
String CLIENTIP="";
   // DataInputStream din;
   // DataOutputStream dout;
 InputStream Bdin;
 OutputStream Bdout;

Timer COMtimer = new Timer();
Timer TimerDel = new Timer();

     public void addPropertyChangeListener(PropertyChangeListener l)
     {
        
        changes.addPropertyChangeListener(l);
     }
      
     public void removePropertyChangeListener(PropertyChangeListener l) 
     {
      changes.removePropertyChangeListener(l);
     }

     static boolean Com_Flg=false;
     private boolean SendToClient(String msg)
     {
         
        /* 
         if(hflag)
         {
             try
             {
          sendhttp(msg);
             }catch(Exception ex){}
             Com_Flg=false;
         return true;
         }
         */
         
         if(Com_Flg)
         {
            // if(_node.LinkedClient==null)
            // return false;
            // else
                 return true;
         }
         Com_Flg=true;
         
        
         try
         {
           byte[] b=msg.getBytes();
            Bdout.write(b);
            Bdout.flush();
            Com_Flg=false;
            return true;
         }catch(Exception ex){
         Com_Flg=false;
         return false;}
     }
     
     
     private boolean SendToClient(byte[] msg)
     {
          if(Com_Flg)
             return false;
         Com_Flg=true;
         try
         {
            Bdout.write(msg);
            //_node.soc.
            
            Com_Flg=false;
            return true;
         }catch(Exception ex){
         Com_Flg=false;
         return false;}
     }
     
       private void ClientFlush() 
     {
         try
         {
         Bdout.flush();
         }catch(Exception ex){}
     }
       
     private void SetNoDelay(boolean stat)
     {
         try
         {
         _node.soc.setTcpNoDelay(stat);
         }catch(Exception ex){}
     }
     private void SetRawBuffer(boolean stat)
     {
         
         try
         {
             if(stat)
             {
         _node.soc.setSendBufferSize(1024*1024);
         _node.soc.setReceiveBufferSize(1024*1024);
             }
             else
             {
                 _node.soc.setSendBufferSize(1*1024);
         _node.soc.setReceiveBufferSize(1*1024);
             }
                 
         }catch(Exception ex){}
     }
     
     private byte[] GetFromClient(int timeout)
     {
         if(timeout==0 )
             timeout=_node.COMCheck;//5000;
     try {
         _node.soc.setSoTimeout(timeout);//(_node.TimeOutSec.intValue()));
     } catch (SocketException ex) {
         Logger.getLogger(ServerNode.class.getName()).log(Level.SEVERE, null, ex);
     }
         
       byte[] indata=new byte[0];
       byte[] indata1=new byte[0];
      long _timeoutQuantum=(long)1;
      if(timeout<_timeoutQuantum)
          _timeoutQuantum=timeout;
      int i=0;
      if(timeout==0)
          timeout=1;
        
    //  _node.soc.setSoTimeout(timeout);
      /*******   New Server code ***********************/
      InputStream Sdin;
      //OutputStream Sdout;
      
          // byte[] Response=new byte[1];
            try
            {
               
               int Data_avail=0;
                byte[] _ba1=new byte[2];
                ByteArrayOutputStream outb = new ByteArrayOutputStream();
                    //outb.write(arr_1);
                    //outb.write(arr_2);
                    //byte[] arr_combined = out.toByteArray();
                    
                while ( Data_avail<1)
                {
                    _ba1[0]=(byte)Bdin.read();//.readByte();
                   Data_avail=Bdin.available(); 
                    _ba1[1]=(byte)Bdin.read();//.readByte();
                    Data_avail=Bdin.available(); 
                     if(Data_avail==0)
                     {
                        if(! isClientLive())
                        {
                          ignoreClient("COM_BREAK");
                         COMtimer.cancel();
                        }
                    
                        break;
                     }
                  // Thread.sleep(_RxPollTime);
                }
                
                Data_avail=Bdin.available();
               byte[] _ba2=new byte[Data_avail];
                Bdin.read(_ba2);//.readFully(_ba2);
                
                if(_ba1!=null) outb.write(_ba1);
                if(_ba2!=null) outb.write(_ba2);
                indata=outb.toByteArray();
              //System.arraycopy(outb.toByteArray(), 0, indata,0 ,outb.size());
            
                
              }
            catch(Exception ex)
            {
                indata=new byte[]{-1};
               System.out.println("Error:"+ex);
               if(!ex.toString().toUpperCase().contains("TIMEOUT"))
               {
                         ignoreClient("COM_BREAK");
                         COMtimer.cancel();
               }
                   
                int io=0;
               /* if(ex.toString().toUpperCase().contains("EOFEXCEPTION")| ex.toString().toUpperCase().contains(" RESET"))
                {
                    try {
                        if(isSSL)
                        {
                        ServSocSSL.close();
                         ClientList.remove(ServSocSSL);
                        }
                        else
                             {
                        ServSocN.close();
                         ClientList.remove(ServSocN);
                        }
               System.out.println("Total Transmitter:["+ClientList.size() +"] ;Recent Closed Client IP="+SMO.IPInfo);
                    } catch (IOException ex1) {
                        Logger.getLogger(RxTxLib.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    
                    
                    
                   _CallbackP.TriggerCallbackGen(FAIL+"CLIENTCLOSE:"+ex);
                    break;
                }
                        SMO.obj=FAIL+ex.toString();
                 */
            }
           
      
      
      /************ New Server Code ends here ************************/
      
      /*
      
      try
        {
            int avail=0;
            do{
                
                avail=din.available();
                Thread.sleep(_timeoutQuantum);
                if(timeout!=0)
                timeout-=_timeoutQuantum;
                    
            }while(avail==0 && timeout>0);
            if(avail>0)
            {
                
           indata=new byte[(int)avail];
           din.readFully(indata);//.readUnsignedByte();;
          //  Thread.sleep(10);
         
          avail=din.available();
          
          do
          {
          avail=din.available();
           if(avail>0)
           {
               try
               {
               i=indata.length;
               indata1=new byte[i+avail];
                System.arraycopy(indata, 0, indata1,0 ,indata.length);
                indata=new byte[avail];
               din.readFully(indata);
               System.arraycopy(indata, 0, indata1,i,indata.length);
               indata=indata1;
                i=0;
               }
               catch(Exception ex){
               i=0;
               }
           }
          }while(avail>0);
           
           
            }
            else
                indata=new byte[]{-1};
        }catch(IOException | InterruptedException ex){
        i=0;
        }
        
      */
      
      
      
    
        return indata;
     }
     
     
     
  //  ServerNode(Socket soc)
      ServerNode(CNode _nodex)
    {
     
        try
        {
            _node=_nodex;
         //   _node.soc.setSoTimeout(_node.COMCheck-100);//(_node.TimeOutSec.intValue()));
           
         //  _node=new CNode();
         //  changes.firePropertyChange("usersOnline", 10, 20);
         //  _node.soc=soc;                        
            
            //din=new DataInputStream(_node.soc.getInputStream());
            //dout=new DataOutputStream(_node.soc.getOutputStream());
            Bdin=_node.soc.getInputStream();
            Bdout=_node.soc.getOutputStream();
            
            System.out.println("PUSH Client Connected:["+SERVER.AllClient+"] :Client IP="+_node.IP +"["+SERVER.GetDateTime()+"]");// CLIENTIP);
      
           _node.RND= getRandomHexString();//"12345678";
           // SendToClient("UbiPush-TOKEN:"+_node.RND);
          //   SendToClient("Welcome to UbiPush");

        //run();
        // _node.IP=_node.soc.getRemoteSocketAddress().toString();
            
        /******************** Check Client Validity *****************/
       validity_thread(_node.TimeOutSec);
           
        }
        catch(Exception ex)
        {
        }        
    }

      
      private void validity_thread(int _time)
      {
              TimerDel.schedule(new TimerTask() {
                  int ltime=_time;
                  @Override
                  public void run() {
                     //validity chechk
                    
                     if(_node.isActive)
                     {
                           if(ClientValidityCheck(_time))
                              {
                              _node.CreatedON= System.currentTimeMillis();
                              CLIENTIP=_node.IP;
                              ltime=_node.COMCheck;

                              /*********** Client Communication Check at periodic interval ****************/
                              ClientComCheck_thread();

                              start();
                              }
                              else
                              {
                                    ignoreClient("Timeout|Close");
                              }
                     }
                     else
                         TimerDel.cancel();
                   }
                },50);
          
      }
    
      public boolean isClientLive()
      {
         // if(_node.LinkedClient==null) //disabled because old client zombie issue at server
          {
            if(SendToClient("[SYNC:" ))
            {
             if( SendToClient(SERVER.GetDateTime()+"]"))
                 return true;
            else
                 return false;
            }
            else
                return false;
           }
         // else
          //    return true;
      }
      
      int NODEID_COUNTER=0;
      int KEY_RETRY=0;
      boolean COMCHK_FLAG=false;
      private void ClientComCheck_thread()
      {
              COMtimer.scheduleAtFixedRate(new TimerTask() {
                  @Override
                  public void run() {
                    
                      if(_node.ID.length()==0)
                        NODEID_COUNTER++;
                     
                    // ClientComCheck();
                    // Your database code here
                    if(COMCHK_FLAG==false)
                    {
                     if(! isClientLive() )
                     {
                         ignoreClient("COM_BREAK");
                         COMtimer.cancel();
                      }
                    }
                     COMCHK_FLAG=false;
                     
                     if(NODEID_COUNTER>1 )
                     {
                         ignoreClient("NOID");
                         COMtimer.cancel();
                      }
                      
                     if(!_node.isActive)
                         COMtimer.cancel();
                     
                  }
                  
                },  _node.COMCheck, _node.COMCheck);
            

      
      }
      
    private boolean ClientValidityCheck(int timeout)
    {
        //long timeout=(long)50000; //in mS
        try
        {
       byte[] xin=  GetFromClient(timeout);
        if(xin.length==1)
        {
            if(xin[0]==-1)
                return false;
        }
        else
                InputData=xin;//new String(xin, "UTF-8");;//xin.toString();
        }
        catch(Exception ex){
        return false;
        }
       
       return true;
    }

    public void ignoreClient(String msg)
    {
       // SendToClient(msg);
        try
        {
            if(_node.ID.length()>0)//SERVER.AllClientList.get(_node.ID)._node.ID.length()>0)
            SERVER.AllClientList.remove(_node.KEY+":"+_node.ID);
            if(SERVER.AllClient>0)
            SERVER.AllClient--;
            try
            {
                SERVER.TempKeyList.remove(_node.KEY+":"+_node.ID);
            }catch(Exception ex){}
        System.out.println("Total Client:"+SERVER.AllClient+":Client["+_node.IP+"] closed for : "+msg + "["+SERVER.GetDateTime()+"]");
        _node.isActive=false;
        _node.soc.close();
        
         
        }catch(Exception ex){
       
        }
        
    }
    
  public boolean SendFile() throws Exception
    {        
    /*
        String filename=din.readUTF();
        //filename=DES_filename;
        File f=new File(filename);
        if(!f.exists())
        {
            dout.writeUTF("NOT_FOUND");
            return false;
        }
        else
        {
            dout.writeUTF("READY");
            FileInputStream fin=new FileInputStream(f);
            int ch;
            do
            {
                ch=fin.read();
                dout.writeUTF(String.valueOf(ch));
            }
            while(ch!=-1);    
            fin.close();    
            dout.writeUTF("OK");  //FIle sent                            
        }
        */
        return true;
    }
    
   public void  ReceiveFile() throws Exception
    {
                return;
            
    }

   
   
    public void run()
    {
        while(true && _node.isActive)
        {
            try
            {
             //   System.out.println("I am live: "+SERVER.GetDateTime()+"\n");
                        //  GetFromServer();
            //System.out.println("Waiting for Command ...");
                //InputData=din.readUTF();
                if(InputData==null || InputData.length==0 || (InputData.length==1 && InputData[0]==-1))
                {
                InputData=GetFromClient(0);
                }
                
                if(InputData==null || InputData.length==0 || (InputData.length==1 && InputData[0]==-1) )
                continue;
                
                //For high speed communication
                if(InputData!=null && _node.LinkedClient!=null)
                {
                     //  Push_MsgRawSend(InputData);
                     if( !_node.LinkedClient.SendToClient(InputData))
                     {
                        _node.LinkedClient=null;
                        SendToClient("[FAIL:REM_Link]");
                     }
                       
                      InputData=new byte[0];
                       continue;
                }
                
                String Command="";//InputData;//din.readUTF();
                PACKET PACKET=new PACKET();
               if(InputData!=null)
                   if(InputData.length>0)
                   if(InputData[0]!=-1)    
                   {
                       COMCHK_FLAG=true;
                   PACKET.GPACKET= PACKET.PACKET_parse(InputData);
                   }
               
               //special case for TLS/SSL
           //    if(PACKET.GPACKET.CMD==null)
               
                  if( PACKET.PACKET_isSSL(InputData))
                  {
                  PACKET.GPACKET.CMD="SSL";
                  }
               
               
               InputData=null;
               
               if(PACKET.GPACKET.CMD!=null)
               {
                   switch(PACKET.GPACKET.CMD) {
                
                   //case "TCPRAW":
                    //    Push_MsgRawSend(PACKET.GPACKET.DATA);
                   //break;
                               
                   case "DSTCP":  //Dynamic Secure TCP
                     processDSTCPCommand(PACKET.GPACKET.DATA,0);
                   break; // optional

                   case "STCP":     //secure TCP
                     processSTCPCommand(PACKET.GPACKET.DATA,0);
                   break; // optional

                   case "TCP":
                    PreProcessTCPCommand(PACKET.GPACKET.DATA,0);
                   break; // optional

                   case "GET":
                       processGETCommand(PACKET.GPACKET.DATA);
                     break;
                  
                      case "SSL":
                       processSSLCommand(PACKET.GPACKET.DATA);
                     break;
                  
                      case "":
                     SendToClient("[FAIL:New Command Found!]");
                   break; // optional
                   
                // You can have any number of case statements.
                default : // Optional
                    SendToClient("[FAIL:Unknown Command]");
                   // Statements
             }
            }
               else
                   SendToClient("[FAIL:Unknown Command]");

               
            if(Command.compareTo("GETID:")==0)
            {
              //  System.out.println("\tGET Command Received ...");
                SendFile();
                continue;
            }
            else if(Command.compareTo("PUSH:")==0)
            {
              //  System.out.println("\tSEND Command Received ...");                
                ReceiveFile();
               continue;
              // break;
               
            }
            else if(Command.compareTo("DISCONNECT")==0)
            {
                
                System.out.println("\tDisconnect Command Received ...");
                //System.exit(1);
                _node.soc.close();
            }
             else if(Command.compareTo("SYNC")==0)
            {
                String sync="["+ SERVER.GetDateTime()+"]";//"SYNC";
                Bdout.write(sync.getBytes());
                System.out.println("\tSYNC from:"+CLIENTIP);
                //System.exit(1);
             
            }
             else if(Command.compareTo("INFO")==0)
            {/*
                String cmd =din.readUTF();
                if(cmd.compareTo("TID")==0)
                {
                    String _TID=din.readUTF();
              //  ClientList.put(CLIENTIP,_TID);
                System.out.println("Terminal ID Receiced: "+_TID);
                }
                */
                System.out.println("Bypassed INFO: ");
            }
            
            }
            catch(Exception ex)
            {
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(ServerNode.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

   private String getRandomHexString(){
       int numchars=8;
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while(sb.length() < numchars){
            sb.append(Integer.toHexString(r.nextInt()));
        }

        return sb.toString().substring(0, numchars).toUpperCase();
    }
   
   String Generate_SessionKey(String _tempkey)
   {
     
       String _SKEY="";
     //  String _tempkey=_node.KEY+RndNo;
   
        try
                {
                   byte[]  bytesOfMessage =_tempkey.getBytes("UTF-8");
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    //MD5 = md.digest(bytesOfMessage);
                   _SKEY= DatatypeConverter.printHexBinary(md.digest(bytesOfMessage));
                 }catch(Exception ex){}
       
       return _SKEY;
   }
   
   
      String  processDSTCPCommand(byte[] data, int mode) //mode =0 if direct, 1 if indirect
    {
          
           String Response="";
           //Response=processSTCPCommand(data,mode);
           try
           {
           String DATAstr=new String(data,"UTF-8");
           String UID=DATAstr.split(":")[0].toUpperCase();
           String KEY="";
           String KEYWORD=DATAstr.split(":")[1];
           String CLIRND="";
           String ServerSesKey=""; //Server generates the session key based on client random no
           try  // Client wants Server Verification
           {
           CLIRND=DATAstr.split(":")[2];
           }catch(Exception ex){
           CLIRND="";
           }
           
           
            if(KEYWORD.equals("ACTIVATE"))
            {
              if(SERVER.AllKeyUID.containsValue(UID))  
              {
                 for(String _key:SERVER.AllKeyUID.keySet())
                 {
                     if(SERVER.AllKeyUID.get(_key).equals(UID))
                     {
                         KEY=_key;
                         break;
                     }
                 }
                 
                 if(CLIRND.length()>=8)  //Server verification needed
                 {
                     ServerSesKey=":"+Generate_SessionKey(KEY+CLIRND);
                 }
                 else
                     ServerSesKey="";
                 
                    //if(_node.DKEY.length()==0)
                    //_node.DKEY=Generate_SessionKey(KEY+_node.RND);
                    _node.isSTCP =false;//true;
                    _node.isDSTCP=true;
                    _node.AKEY=KEY;
                   
                    Response="[OK:"+_node.RND+ServerSesKey+"]";
                 
              }
              else
                  Response="[FAIL:INVALID_USERID]";
                  
            }
            else if(KEYWORD.equals("DEACTIVATE"))
            {
                _node.RND="";
                _node.SKEY="";
                _node.isSTCP =false;
                _node.isDSTCP =false;
                Response="[OK:DSTCP_DEACTIVATED]";
            }
            else
                 Response="[FAIL:INVALID_FORMAT]";
//           DATAstr=DATAstr;
           }catch(Exception ex){
               Response="[FAIL:INVALID_FORMAT]";
           }
         
           SendToClient(Response);
           return Response;
     }
    
   
   
       String  processSTCPCommand(byte[] data, int mode) //mode =0 if direct, 1 if indirect
    {
           String Response="";
           try
           {
           String DATAstr=new String(data,"UTF-8");
           String UID=DATAstr.split(":")[0].toUpperCase();
           String KEY="";
           String KEYWORD=DATAstr.split(":")[1];
           String CLIRND="";
           String ServerSesKey=""; //Server generates the session key based on client random no
           try  // Client wants Server Verification
           {
           CLIRND=DATAstr.split(":")[2];
           }catch(Exception ex){
           CLIRND="";
           }
           
            if(KEYWORD.equals("ACTIVATE"))
            {
              if(SERVER.AllKeyUID.containsValue(UID))  
              {
                 for(String _key:SERVER.AllKeyUID.keySet())
                 {
                     if(SERVER.AllKeyUID.get(_key).equals(UID))
                     {
                         KEY=_key;
                         break;
                     }
                 }
                 
                 if(CLIRND.length()>=8)
                 {
                     ServerSesKey=":"+Generate_SessionKey(KEY+CLIRND);
                 }
                 else
                     ServerSesKey="";
                 
                    if(_node.SKEY.length()==0)
                    _node.SKEY=Generate_SessionKey(KEY+_node.RND);
                    _node.isSTCP =true;
                    _node.AKEY=KEY;
                   
                    Response="[OK:"+_node.RND+ServerSesKey+"]";
                 
              }
              else
                  Response="[FAIL:INVALID_USERID]";
                  
            }
            else if(KEYWORD.equals("DEACTIVATE"))
            {
                _node.RND="";
                _node.SKEY="";
                _node.isSTCP =false;
                  Response="[OK:STCP_DEACTIVATED]";
            }
            else
                 Response="[FAIL:INVALID_FORMAT]";
//           DATAstr=DATAstr;
           }catch(Exception ex){
               Response="[FAIL:INVALID_FORMAT]";
           }
         
           SendToClient(Response);
           return Response;
     }
       
       
       String PreProcessTCPCommand(byte[] data, int mode)
       {
           
        if(data[data.length-2]==13 && data[data.length-1]==10)
        data=Arrays.copyOfRange(data,0,data.length-2);
       // Login _Login=new Login();
        String Response="";
        PACKET PACKET=new PACKET();
        String CMD="";
        String KEY="";
        byte[] DATA;
        byte[] DATA1;


        String DATAstr="";
               
        try
        {
               PACKET.GPACKET= PACKET.PACKET_parse(data);
                  CMD=PACKET.GPACKET.CMD;
                  DATA1=PACKET.GPACKET.DATA;
                  
                 // if(!_node.isKeyVerified)
                 // {
                      PACKET.GPACKET= PACKET.PACKET_parse(DATA1);
                  
                    if(PACKET.GPACKET.CMD!=null && PACKET.GPACKET.DATA!=null)
                    {
                    KEY=PACKET.GPACKET.CMD;
                    DATA=PACKET.GPACKET.DATA;
                    DATAstr=new String(DATA,"UTF-8");
                    }
                    else
                    {
                        KEY=new String(DATA1,"UTF-8");
                        DATA=new byte[]{0};
                    }
                //  }
                /*  else
                  {
                   KEY=_node.KEY;
                   DATA=DATA1;//PACKET.GPACKET.CMD;
                      
                  }
                  */
                  
                  if(_node.isSTCP)
                  {
                      if(KEY.equals(_node.SKEY))
                          KEY=_node.AKEY;
                      else 
                          KEY=null;
                  }
                  
                   if(_node.isDSTCP)
                  {
                     String _ldata="";
                      try
                      {
                          if(_node.ID.length()==0)
                          _ldata=DATAstr.split(":")[0];
                          else
                              _ldata=DATAstr.split(":")[1];
                              
                      }catch(Exception ex){
                      _ldata="";
                      }
                      
                      String _DSTCPKEY=Generate_SessionKey(_node.AKEY+_node.RND+_ldata);
                      //_node.DKEY
                      if(KEY.equals(_DSTCPKEY))
                          KEY=_node.AKEY;
                      else 
                          KEY=null;
                  }
                  
                  
                   boolean NewNode=true;
       
                   if(_node.isKeyVerified && KEY==null)
                       KEY=_node.KEY;
                   
                  //key to uid conversion
                  if(KEY!=null)// && !_node.isKeyVerified)
                  {
                    if(SERVER.KeyDevice.KeytoUID(KEY)!=null)
                        KEY=SERVER.KeyDevice.KeytoUID(KEY);
                    else
                      KEY=null;
                  }
                  
                  if(KEY!=null)
                  {
                   if(/*(_node.ID.equals(DATAstr)) &&*/ (_node.KEY.equals(KEY)))
                       NewNode=false;
                        else
                       NewNode=true;
                  }
        
        
                  
        Response=processTCPCommand(CMD,KEY,DATA,NewNode);
        
        
        }
        catch(Exception ex){}
        
           if(mode==0)
             SendToClient(Response);
                  else
               return Response;
        
        return "OK";
       }
       
      String  processTCPCommand(String CMD,String KEY,byte[] DATA, boolean NewNode)//byte[] data, int mode) //mode =0 if direct, 1 if indirect
    {
        String Response="FAIL";
        String DATAstr="";
         PACKET PACKET=new PACKET();
           try
        {
            DATAstr=new String(DATA,"UTF-8");
                   
                  if(CMD!=null && KEY!=null && DATA.length>0)
                  {
                      Message _Message=new Message();
                       switch(CMD)
                       {
               
                       
                   case "ID":
                        
     
                       if(NewNode)
                       {
                           
                           
                            int Result=_Message.REGID(KEY, new String(DATA,"UTF-8"));
                            if(Result==0 || Result==2)
                            {
                            KEY_RETRY=0;
                            DATAstr=DATAstr.toUpperCase();
                                    if(SERVER.AllClientList.containsKey(KEY+":"+DATAstr)) //ID Exist
                                    {
                                       if(SERVER.AllClientList.get(KEY+":"+DATAstr).isClientLive())  //Other ID alive
                                       {
                                             SERVER.AllClientList.put(KEY+":"+DATAstr,SERVER.AllClientList.get(KEY+":"+DATAstr));
                                        Response="[FAIL:NODEEXIST]";     
                                        SendToClient(Response);
                                        Thread.sleep(100);
                                           ignoreClient("[ID_Exists]");                        //Close myself
                                           break;
                                       }
                                       else                                             //Other ID not live
                                       {
                                           if(_node.KEY.length()==0 || _node.isSTCP)
                                       Response=AddthisNode(KEY,DATAstr,Result);
                                           else
                                              Response="[FAIL:KEY_OVERRIDE]";
                                       }
                                    }
                                    else                                                    //Node is added first time
                                    {
                                           if(_node.KEY.length()==0 || _node.isSTCP )
                                   Response=AddthisNode(KEY,DATAstr,Result);
                                    else
                                          Response="[FAIL:KEY_OVERRIDE]";  
                                    }
                                 }
                            else 
                            {
                                if(Result==1)
                                {
                                    KEY_RETRY++;
                                    if(KEY_RETRY>5)
                                    {
                                     ignoreClient("[KEY_RETRY LIMIT]");
                                        COMtimer.cancel();
                                    }
                                Response="[FAIL:KEY]";
                                }
                            else
                                Response="[FAIL:ID]";
                                
                             SendToClient(Response);
                              ignoreClient(Response);     
                             break;
                            }
                        //Search Node having same ID and delete it
                       }
                       else//Old Node //if(_node.ID.equals(new String(DATA,"UTF-8")))
                       {
                           if(_node.ID.equals(DATAstr))
                           Response="[OK:"+DATAstr+"]";  
                           else
                               Response="[FAIL:"+_node.ID+"]";
                       }
                         
                   break; // optional


                   
                   case "GID":
                   
                        
                       if(NewNode)
                      Response= ifNewNode();
                       else
                      Response="[OK:"+_node.ID+"]"; 
                       
                       break;
                   
                       
                    case "REMID":
                        
                       if(NewNode)
                      Response= ifNewNode(); 
                       else
                       {
                          SERVER.AllClientList.remove(_node.KEY+":"+ _node.ID, this);   //Remove me from the pull
                          _node.KEY="";
                          _node.ID="";
                          try
                            {
                                SERVER.TempKeyList.remove(_node.KEY+":"+_node.ID);
                            }catch(Exception ex){}
                           Response= "[OK:NODEREMOVED]"; 
                       }
                        break;
                   
                   
                        
                    case "PUSH":
                     if(NewNode)
                      Response= ifNewNode(); 
                       else
                        {
                         //KEY=PACKET.GPACKET.CMD;
                        //DATA=PACKET.GPACKET.DATA;
                        //DATAstr=new String(DATA,"UTF-8");
                            PACKET.GPACKET= PACKET.PACKET_parse(DATA);
                          String REMID=PACKET.GPACKET.CMD;
                          byte[] MSGBYTE=PACKET.GPACKET.DATA;
                          //String MSG=new String(MSGBYTE,"UTF-8");
                          if(REMID!=null)
                          {
                              if(REMID.equals("FRIENDS"))
                            {
                               Response= Push_Friends(MSGBYTE);
                            }
                              else if(REMID.equals("ALLCHILDS"))
                            {
                               Response= Push_AllChild(MSGBYTE);
                            }
                              else if(REMID.equals("CHILDS"))
                            {
                               Response= Push_Child(MSGBYTE);
                            }
                            else if(REMID.equals("PARENT"))
                            {
                                Response= Push_Parent(MSGBYTE);
                            }
                            else if(REMID.equals("RID"))
                            {
                                PACKET.GPACKET= PACKET.PACKET_parse(MSGBYTE);
                                REMID=PACKET.GPACKET.CMD;
                                MSGBYTE=PACKET.GPACKET.DATA;
                          
                            Response= Push_Msg(REMID,MSGBYTE);
                            }
                            else if(REMID.equals("RO"))
                            {
                                 PACKET.GPACKET= PACKET.PACKET_parse(MSGBYTE);
                                REMID=PACKET.GPACKET.CMD;
                                MSGBYTE=PACKET.GPACKET.DATA;
                              Response=  Push_Router(KEY+":"+REMID,MSGBYTE) ;
                            }
                            else if(REMID.equals("RAW"))
                            {
                              //  PACKET.GPACKET= PACKET.PACKET_parse(MSGBYTE);
                              //  REMID=PACKET.GPACKET.CMD;
                                //MSGBYTE=PACKET.GPACKET.DATA;
                          
                            Response= Push_MsgRawInit(new String(MSGBYTE,"UTF-8"));
                            }
                            
                              else
                            {
                                Response= "[FAIL:UNKNOWN_CMD]";
                            }
                          }
                          else
                              Response="[FAIL:Invalid Format]";
                          }
                     
                        break;
                      
                    
                      case "EMAIL":
                             if(NewNode)
                      Response= ifNewNode(); 
                       else
                        {
                                PACKET.GPACKET= PACKET.PACKET_parse(DATA);
                          String REMMAILID=PACKET.GPACKET.CMD;
                          byte[] temp=PACKET.GPACKET.DATA;
                            PACKET.GPACKET= PACKET.PACKET_parse(temp);
                            String SUBJECT=PACKET.GPACKET.CMD;
                          String MSG=new String(PACKET.GPACKET.DATA,"UTF-8");
                          if(REMMAILID!=null && SUBJECT!=null && MSG!=null)
                          {
                              if(!REMMAILID.contains("@")|| !REMMAILID.contains("."))
                                  Response="[FAIL:INVALID ID]";
                              else
                              {
                                   email email=new email(null);
                                 
                                //String  _KEY= SERVER.DBlogin.GetKeyDetails(Username);
                                
                                  email.sendmaily(REMMAILID,SUBJECT,MSG );
                                
                                Response="[OK:MAIL_SENT]";
                              }
                              
                          }
                          else
                              Response="[FAIL:INVAILD_FORMAT]";
                        }
                            break;
                            
                     case "RETDATA":
                     if(NewNode)
                      Response= ifNewNode(); 
                       else
                       Response=GetCachedInfo(DATAstr); 
                     break;
                     
                            
                   case "TIME":
                   
                       if(NewNode)
                      Response= ifNewNode();
                       else
                      Response=GetTimeInfo(DATAstr); 
                       
                       break;
                   
                   case "GOOGLESHEET":
                        if(NewNode)
                      Response= ifNewNode();
                       else
                      Response=SendGoogleSheet(DATAstr); 
                       
                       break;
                        
                   default:
                     Response="[FAIL:Invalid Command]";
                   break; // optional
                   
                       }

                  }
                  else
                  {
                      //CMD!=null && KEY!=null && DATA.length>0
                      if(CMD==null)
                       Response="[FAIL:INVALID_CMD]";
                      if(KEY==null)
                       Response="[FAIL:INVALID_KEY]";
                      if(DATA.length==0)
                       Response="[FAIL:INVALID_DATA]";
                  }
                  
               
                      return Response;
        
          //  _node.soc.close();
         //   ignoreClient("Browser_COM");
        }catch(Exception ex){
        int i=0;
        SendToClient("[FAIL:FORMAT EXCEPTION!]");
        }
        int i=0;
        return "OK";
    }
   
       
      private String SendGoogleSheet(String Data)
      {
        GoogleSheet GS=new GoogleSheet();  
        return GS.sendToGoogleSheet(null, Data);
      }
      private String GetTimeInfo(String offset)
      {
          String timeinfo="";
         timeinfo= SERVER.TIME.getTime(offset);
          return timeinfo;
      }
      
      private String GetCachedInfo(String ID)
      {
          String cachedInfo="";
       
         cachedInfo= chdata.Get_CacheString(_node,Integer.parseInt(ID));
          return cachedInfo;
      }
      
      
      String AddthisNode(String KEY,String DATAstr, int NodeType)
      {
          String Response="";
          _node.ID=DATAstr;
          _node.KEY=KEY;
          _node.isKeyVerified=true;
          
          SERVER.AllClientList.put(KEY+":"+DATAstr, this);
          
          if(NodeType==2) //Temporary Node
          {
           if(!SERVER.TempKeyList.contains(KEY+":"+DATAstr))
           SERVER.TempKeyList.add(KEY+":"+DATAstr);
          }
          
          _updateNodeProperty(KEY, DATAstr);
         
          int UserAccessLevel=0;
          try
          {
          UserAccessLevel=SERVER.UserPro.GetUserPropertyNo(KEY)-1;
          }catch(Exception ex){UserAccessLevel=0;}
          try
          {
           _node.UserP._1TREE= toBoolean(SERVER.UserP.TREE.get(UserAccessLevel));
           _node.UserP._2NODEPERTREE= Integer.parseInt(SERVER.UserP.NODEPERTREE.get(UserAccessLevel));
           _node.UserP._3TIMERPUSH= toBoolean(SERVER.UserP.TIMERPUSH.get(UserAccessLevel));
           _node.UserP._4HOLDPUSH= toBoolean(SERVER.UserP.HOLDPUSH.get(UserAccessLevel));
           _node.UserP._5HOLDBUFFER= Integer.parseInt(SERVER.UserP.HOLDBUFFER.get(UserAccessLevel));
           _node.UserP._6MAILPUSH= toBoolean(SERVER.UserP.MAILPUSH.get(UserAccessLevel));
           _node.UserP._7SMSPUSH= toBoolean(SERVER.UserP.SMSPUSH.get(UserAccessLevel));
           _node.UserP._8INTSMSPUSH= toBoolean(SERVER.UserP.INTSMSPUSH.get(UserAccessLevel));
           _node.UserP._9SSLPUSH= toBoolean(SERVER.UserP.SSLPUSH.get(UserAccessLevel));
           _node.UserP._10MAXNODE=Long.parseLong(SERVER.UserP.MAXNODE.get(UserAccessLevel));
           
          
          Response="[OK:"+DATAstr+"]";
        
          }catch(Exception ex){ Response="[OK:PROP_FAIL";}
        
          return Response;
      }
      
      public void _updateNodeProperty(String KEY,String NodeID)
      {

          NodeID=_node.ID; // I am overriding inputs. Just need to update myself
             if(!KEY.contains("@"))
          {
            KEY=  SERVER.AllKeyUID.get(KEY);
      
          }
     
          if(!KEY.equals(_node.KEY) || !NodeID.equals(_node.ID) )
              return;
          String response="";
         try
         {
             if(SERVER.KeyDevice.IsChildExist(KEY, NodeID))
              SERVER.TempKeyList.remove(KEY+":"+NodeID);
             else
             {
                 if(!SERVER.TempKeyList.contains(KEY+":"+NodeID))
                 SERVER.TempKeyList.add(KEY+":"+NodeID);
             }
              
             String xx=SERVER.KeyDevice.GetMyAllChildIDs(KEY,NodeID);
         _node.AllChilds= (List) Arrays.asList(SERVER.KeyDevice.GetMyAllChildIDs(KEY,NodeID).split("\\|"));
         _node.Childs= (List) Arrays.asList(SERVER.KeyDevice.GetMyChildIDs(KEY,NodeID).split("\\,"));
         _node.Parent= SERVER.KeyDevice.GetMyParentID(KEY,NodeID);
         if(_node.Parent.toUpperCase().contains("ERROR"))  //Only for Temporary Nodes
             _node.Parent="ROOT";
         
         if(_node.Parent.equals("ROOT"))
          {
          if( !SERVER.ROOTKeyList.contains(KEY+":"+_node.ID) )
         SERVER.ROOTKeyList.add(KEY+":"+_node.ID);
          }
         else
           SERVER.ROOTKeyList.remove(KEY+":"+_node.ID);  
         
        String MyParent= SERVER.KeyDevice.GetMyParentID(KEY,NodeID);
        _node.Friends= (List) Arrays.asList(SERVER.KeyDevice.GetMyChildIDs(KEY,MyParent).split(","));
             
        int i=0;
         }catch(Exception ex){}
           
          
          
      }
      
      
      boolean toBoolean(String Info)
      {
          Info=Info.toUpperCase();
          if(Info.equals("TRUE"))
              return true;
          else
              return false;
       }
      
      
      String Push_Friends(byte[] RemData)
      {
          String Response="[FAIL:SERVER]";
          int Total=0;//_node.Friends.size()+SERVER.TempKeyList.size();
          int Pass=0;
          
          
          if(_node.Parent.equals("ROOT"))    //I am root. Temporary nodes are my friends
          {
              Total=SERVER.ROOTKeyList.size();
            for(String RemID: SERVER.ROOTKeyList)  //All Temp nodes are my Friends
            {
                if(RemID.length()==0)
                Total--;
                String _TKey=RemID.split(":")[0].toUpperCase();
                RemID=RemID.split(":")[1];
                if(!RemID.equals(_node.ID) && _node.KEY.equals(_TKey) )
                {

                  if(Push_Msg(RemID,RemData).toUpperCase().contains("OK"))
                      Pass++;
                  else
                      Total--;
                 }
                else
                    Total--;
            }
           }
          else   //I am not root
          {
              Total=_node.Friends.size();//+SERVER.TempKeyList.size();
                    for(String RemID:_node.Friends)
                  {
                      if(RemID.length()==0)
                      Total--;

                      if(!RemID.equals(_node.ID))
                      {
                        if(Push_Msg(RemID,RemData).toUpperCase().contains("OK"))
                            Pass++;
                      }
                      else
                          Total--;
                  }

          }
          
          if(Pass==Total)
          {
              if(Pass!=0)
              Response="[OK:ALL_"+String.valueOf(Total)+"]";
              else
                  Response="[FAIL:NO_ID]";
          }
          else if(Pass>0 && Pass<Total)
              Response="[OK:"+String.valueOf(Pass)+"/"+String.valueOf(Total)+"]";
          else 
          Response="[FAIL:NO_ID]";
          
        return Response;
        }
      
      
      String Push_AllChild(byte[] RemData)
      {
          String Response="[FAIL:SERVER]";
          int Total=_node.AllChilds.size();
          int Pass=0;
          for(String RemID:_node.AllChilds)
          {
            if(Push_Msg(RemID,RemData).toUpperCase().contains("OK"))
                Pass++;
          }
          if(Pass==Total)
              Response="[OK:ALL_"+String.valueOf(Total)+"]";
          else if(Pass>0 && Pass<Total)
              Response="[OK:"+String.valueOf(Pass)+"/"+String.valueOf(Total)+"]";
          else 
          Response="[FAIL:NO_ID]";
          
        return Response;
        }
      
  
          String Push_Child(byte[] RemData)
      {
          String Response="[FAIL:SERVER]";
          int Total=_node.Childs.size();
          int Pass=0;
          for(String RemID:_node.Childs)
          {
            if(Push_Msg(RemID,RemData).toUpperCase().contains("OK"))
                Pass++;
          }
          if(Pass==Total)
              Response="[OK:ALL_"+String.valueOf(Total)+"]";
          else if(Pass>0 && Pass<Total)
              Response="[OK:"+String.valueOf(Pass)+"/"+String.valueOf(Total)+"]";
          else 
          Response="[FAIL:NO_ID]";
          
        return Response;
        }
      
  
      
      String Push_Parent(byte[] RemData)
      {
          String Response="[FAIL:SERVER]";
          String RemID=_node.Parent;
          if(RemID.equals("ROOT"))
          {
              Response="[FAIL:NO_PARENT]";
              return Response;
          }
        Response=  Push_Msg(RemID,RemData);
        return Response;
        }
      
      
      
      String Push_MsgRawSend(byte[] Data)
      {
          
          String Response="[FAIL:SERVER]";
          if(_node.LinkedClient!=null)
          {
             if( _node.LinkedClient.SendToClient(Data))
                      Response="[OK:RAW]";
                else
             {
                    Response="[FAIL:RAW_REM_LINK]";
                    if(_node.REMID.length()>0)
                    Response=Response+Push_MsgRawInit(_node.REMID);
             }
           }
          else
              Response="[FAIL:RAW_REM_ID]";
          
          return Response;
      }
     
     
      String Push_MsgRawInit(String RemID)
      {
          
          String Response="[FAIL:SERVER]";
          if(SERVER.AllClientList.containsKey(_node.KEY+":"+RemID))
          {
              if(SERVER.AllClientList.get(_node.KEY+":"+RemID).SendToClient("PUSH"))
              {
                if( SERVER.AllClientList.get(_node.KEY+":"+RemID).SendToClient("RAW:"))
                {
                    _node.LinkedClient=SERVER.AllClientList.get(_node.KEY+":"+RemID);
                    _node.REMID=RemID;
                    
                    //increase buffer size
                    SetRawBuffer(true);
                    SERVER.AllClientList.get(_node.KEY+":"+RemID).SetRawBuffer(true);
                    
                    //fast transfer
                    SERVER.AllClientList.get(_node.KEY+":"+RemID).SetNoDelay(true);
                    SetNoDelay(true);
                   
                 
                   Response="[OK:LINK_SYNC]";
                }
                else
                    Response="[FAIL:REM_LINK]";
              }
             else
                 Response="[FAIL:REM_LINK]";
          }
          else
              Response="[FAIL:REM_ID]";
          
          return Response;
      }
     
      
     String Push_Msg(String RemID,byte[] RemData)
      {
          
          String Response="[FAIL:SERVER]";
          if(SERVER.AllClientList.containsKey(_node.KEY+":"+RemID))
          {
              if(SERVER.AllClientList.get(_node.KEY+":"+RemID).SendToClient("PUSH:"))
              {
                if( SERVER.AllClientList.get(_node.KEY+":"+RemID).SendToClient(RemData))
                {
                    chdata.Add_Cache(_node, RemData);  //add byte data to cache
                   Response="[OK:DATA_SENT]";
                }
                else
                    Response="[FAIL:REM_LINK]";
              }
             else
                 Response="[FAIL:REM_LINK]";
          }
          else
              Response="[FAIL:REM_ID]";
          
          return Response;
      }
     
     String Push_Router(String ROID,byte[] MSGBYTE)
      {
          String Response="[FAIL:ROUTER]";
     
         
          List<String> nodelist = new ArrayList<String>();
          if(DataBase.IntRouter.containsKey(ROID))
          {
              nodelist = (List) Arrays.asList(DataBase.IntRouter.get(ROID).split(","));
          }
          
          // List<String> KeySubSet = (List) Arrays.asList(_xtemp);
         if(!nodelist.contains(_node.ID))
             return "FAIL:NO_ROUTER";
          
          int Total=nodelist.size()-1;
          int Pass=0;
          for(String RemID:nodelist)
          {
            if(!RemID.equals(_node.ID)) 
            {
            if(Push_Msg(RemID,MSGBYTE).toUpperCase().contains("OK"))
                Pass++;
            }
          }
          if(Pass==Total)
              Response="[OK:ALL_"+String.valueOf(Total)+"]";
          else if(Pass>0 && Pass<Total)
              Response="[OK:"+String.valueOf(Pass)+"/"+String.valueOf(Total)+"]";
          else 
          Response="[FAIL:NO_ID]";
          
               return Response;
     
      }
     
      
      String ifNewNode()
      {
          
            if(_node.ID.length()==0)
                      return "FAIL:NOID"; 
                           else
                        return "FAIL:NOKEY"; 
          
      }
    
    void processSSLCommand(byte[] data)
    {
        Login _Login=new Login();
        String SData="";
        try
        {
     
           String Response="SSL is not supported now.";
                sendhttps1(Response);
            // SendToClient(SOut);
        
          //  _node.soc.close();
        //    ignoreClient("Browser_COM");
        }catch(Exception ex){
        int i=0;
        }
        int i=0;
    }
    
    
    
    
    void processGETCommand(byte[] data) throws UnsupportedEncodingException
    {
        Login _Login=new Login();
        String SData="";
        String Response=Info.APIlist;
        
        try
        {
        SData=new String(data,"UTF-8");
        SData=SData.split(" ")[0];
          
        /*
        int loc=Data.indexOf("&DATA=[");
                        if(loc>0 && Data.length()>6)
                        {
                        String Xdata=Data.substring(loc+6);
        */
        
        int loc=SData.indexOf("?");
        
        if(loc>0)
        {
        String parsedData0=SData.substring(0,loc);
        String parsedData1=SData.substring(loc+1);
        
      //  String[] parsedData=SData.split("\\?");
        
        //if(parsedData.length>1)
        //Leave '/' character
        //Response=  _Login.ProcessUser(parsedData[0],parsedData[1]);
      Response=  _Login.ProcessUser(parsedData0,parsedData1);
        }
        
        // String DataStr=new String(data, "UTF-8");//data.toString();
       
       if(SData.startsWith("/TCP:"))
           Response=SData;
       if(Response.toUpperCase().startsWith("/TCP:"))
       {
           
          byte[] _dataB=new byte[Response.length()-5];
         System.arraycopy(Response.getBytes(), 5, _dataB, 0, _dataB.length);// Arrays.copyOfRange(Response.getBytes(), 2, Response.length());
        Response= PreProcessTCPCommand(_dataB,0);//"The Messaging Service over REST API is not enabled! Please use Raw TCP API"; //processTCPCommand(_dataB,1);
          
       }
       Response=Response+" ["+ SERVER.GetDateTime()+"]";
       // SendToClient(Response);
 
        
        String SOut="";
                /*"HTTP/1.1 200 OK\n" +
                    "Date: Fri, 31 Dec 1999 23:59:59 GMT\n" +
                    "Connection: Close\n"+
                    "Content-Type: text\n" +
                    "Content-Length: " +Response.length()+"\n"+
                    "OK";
                     //Response;
*/
      //      SendToClient(Response);
           sendhttp(Response);
        //        sendhttp("Hello");
    
    //      ignoreClient("Browser_COM");
        }catch(Exception ex){
        int i=0;
          sendhttp(Response);
        }
        int i=0;
    }
    
    
    boolean hflag=false;
  void sendhttpHeader() throws UnsupportedEncodingException
  {
      try
      {
      String res=  "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: multipart/x-mixed-replace; boundary=" +
                    "--boundary"+//this.Boundary +
                    "\r\n";
      hflag=true;
      Bdout.write(res.getBytes());
        Bdout.flush();
       }catch(Exception ex){}
      
  }


  void sendhttpmulti(String msg) throws UnsupportedEncodingException
  {
      
      if(!hflag)
          sendhttpHeader();
          /*
           sb.AppendLine();
                sb.AppendLine(this.Boundary);
                sb.AppendLine("Content-Type: text/plain;charset=iso-8859-1");//image/jpeg");
                sb.AppendLine("Content-Length: " + dt.Length);// imageStream.Length.ToString());
                sb.AppendLine("Content-Transfer-Encoding: quoted-printable");
                sb.AppendLine();

                string x = sb.ToString();
                Write(sb.ToString());
                //imageStream.WriteTo(this.Stream);
                Write(dt);
                Write("\r\n");
          */
            try
            {
            String res = "\r\n"
                    +"--boundary"
                    +"Content-Type: text/plain;charset=iso-8859-1"
                    +"Content-Length: " + msg.length()
                    +"Content-Transfer-Encoding: quoted-printable"
                    +"\r\n";
                    Bdout.write(res.getBytes());
                    Bdout.write(msg.getBytes());
                    res="\r\n";
                    Bdout.write(res.getBytes());
                    /*
               + "Date: "+format.format(new java.util.Date())+"\n"
         + "Content-type: text/html; charset=UTF-8\n"
               + "Content-Length: "+ Integer.toString(26+msg.length())+"\n\n"
               + "<html><body>"+msg+"
                    </body></html>";
      dout.write(res.getBytes());
     
                    */
                     Bdout.flush();
                    
            }
            catch(Exception ex){
            int i=0;
            }
 
                   
  }
    
  
    
  void sendhttp(String msg) throws UnsupportedEncodingException
  {
      
        
      
      try
      {
      String res = "HTTP/1.0 200 OK\n"
         + "Server: HTTP server/0.1\n"
         + "Date: "+format.format(new java.util.Date())+"\n"
             // +"User-agent: Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)\n"
   + "Content-type: text/html; charset=UTF-8\n"
         + "Content-Length: "+ Integer.toString(26+msg.length())+"\n\n"
         + "<html><body>"+msg+"</body></html>";
        
         
         //din=new DataInputStream(_node.soc.getInputStream());
            
    //DataOutputStream dout=new DataOutputStream(_node.soc.getOutputStream());
   
    Bdout.write(res.getBytes());
     Bdout.flush();
      }
      catch(Exception ex){
      int i=0;
      }
 //     while( !SendToClient(s));
                   
   //                 ClientFlush();
                   
                   
  }
    
    
  
  
  
  void sendhttps1(String msg) throws UnsupportedEncodingException
  {
      try
      {
      String res = "HTTP/1.1 403 SSL Required\n"
      //   + "Upgrade: TLS/1.0, HTTP/1.1\n"
      //   + "Connection: Upgrade\n"
   //+ "Content-type: text/html; charset=UTF-8\n"
   //      + "Content-Length: "+ Integer.toString(26+msg.length())+"\n\n"
   //      + "<html><body>"+msg+"</body></html>"
              ;
        Bdout.write(res.getBytes());
        Bdout.flush();
      }
      catch(Exception ex){
      int i=0;
      }
 //     while( !SendToClient(s));
                   
   //                 ClientFlush();
                   
                   
  }
    
private SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:Ss z");
    
}

