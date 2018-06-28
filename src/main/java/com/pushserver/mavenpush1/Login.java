/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pushserver.mavenpush1;

//import com.sun.xml.internal.ws.commons.xmlutil.Converter;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import javax.xml.bind.DatatypeConverter;


/*
Create user:
/USER?username=abcd&password=xyz

if( username does not exists)
Returns: Invalid user

if( username and pass does not match)
Returns: Invalid credentials

if( username and pass valid)
Returns: key


/USERRESET?username=abcd&password=xyz&newpassword=abcdef
Returns: Success


*/

/**
 *
 * @author workaholic
 */
public class Login {
  
      email email;//=new email();
      
        public void Login()
        {
            email=new email(null);
        //   DataBase _DB=new DataBase();
        //      DataBase.LoginDB _dblogin=_DB.new LoginDB();  
        }

        /*
        Create user:
/USER?username=abcd&password=xyz

if( username does not exists)
Returns:Mail will be sent for activation and link creation.
	/USERAUTH?... link is to be activated 
	

if( username and pass does not match)
Returns: Invalid credentials

if( username and pass valid)
Returns: Credentials mailed





        
        */
        
        
    public String ProcessUser(String Command, String Data)
    {
        String INVALID_FORMAT="Invalid Format";
        String INVALID_KEYWORDS="Invalid Keywords";
        String INVALID_CREDENTIAL="Invalid Credential";
        String[] parts;
        if(Command.startsWith("/"))
           Command= Command.replaceFirst("/","");
        
        try
        {

            
            switch(Command) {
                
                case "USER":
                    parts=Data.split("\\&"); //Expected parts[username=abcd&password=xyz]
                    if(parts.length<1 || parts.length>2)
                        return INVALID_FORMAT;
                    else if(!parts[0].toUpperCase().contains("USERNAME") )//|| !parts[1].toUpperCase().contains("PASSWORD"))
                        return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                        if(parts.length==2)
                        return UsernamePassVerification(parts[0].split("\\=")[1], parts[1].split("\\=")[1]);
                        else
                            return UsernamePassVerification(parts[0].split("\\=")[1], "");

                  case "USERDEL":
                    parts=Data.split("\\&"); //Expected parts[username=abcd&password=xyz]
                    if(parts.length!=2)
                        return INVALID_FORMAT;
                    else if(!parts[0].toUpperCase().contains("USERNAME") || !parts[1].toUpperCase().contains("PASSWORD"))
                        return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                        return UserDelete(parts[0].split("\\=")[1], parts[1].split("\\=")[1]);

                     case "USERAUTH":
                    parts=Data.split("\\&"); //Expected parts[username=abcd&password=xyz]
                    if(parts.length!=3)
                        return INVALID_FORMAT;
                    else if(!parts[0].toUpperCase().contains("USERNAME") || !parts[1].toUpperCase().contains("PASSWORD")|| !parts[2].toUpperCase().contains("TOKEN"))
                        return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                        return UserAuth(parts[0].split("\\=")[1], parts[1].split("\\=")[1], parts[2].split("\\=")[1]);
                   
                      case "USERPASSRESET":
                    parts=Data.split("\\&"); //Expected parts[username=abcd&password=xyz]
                    if(parts.length!=3)
                        return INVALID_FORMAT;
                    else if(!parts[0].toUpperCase().contains("USERNAME") || !parts[1].toUpperCase().contains("PASSWORD")|| !parts[2].toUpperCase().contains("NEWPASSWORD"))
                        return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                        return UserPassReset(parts[0].split("\\=")[1], parts[1].split("\\=")[1],parts[2].split("\\=")[1]);

                        
                     case "USERKEYRESET":
                    parts=Data.split("\\&"); //Expected parts[username=abcd&password=xyz]
                    if(parts.length!=2)
                        return INVALID_FORMAT;
                    else if(!parts[0].toUpperCase().contains("USERNAME") || !parts[1].toUpperCase().contains("PASSWORD"))
                        return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                        return UserKeyReset(parts[0].split("\\=")[1], parts[1].split("\\=")[1]);
                    
                    
                    /*************************** NODE / TREE ************************/
                    
                     case "ADDNODE":
                    parts=Data.split("\\&"); //Expected parts[username=abcd&password=xyz]
                    if(parts.length<2)
                        return INVALID_FORMAT;
                    else if(!parts[0].toUpperCase().contains("KEY") && !(parts[1].toUpperCase().contains("PNODE")))
                        return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                        if(parts.length==2) //add root node
                        {
                            String response=ADDNODE(parts[0].split("\\=")[1],null, parts[1].split("\\=")[1]);
                            Update_all_nodes(parts[0].split("\\=")[1], parts[1].split("\\=")[1]);
                        return response;
                        }
                        else if(parts.length==3)
                        {
                        String response= ADDNODE(parts[0].split("\\=")[1], parts[1].split("\\=")[1], parts[2].split("\\=")[1]);
                         Update_all_nodes(parts[0].split("\\=")[1], parts[1].split("\\=")[1]);
                         Update_all_nodes(parts[0].split("\\=")[1], parts[2].split("\\=")[1]);
                        return response;
                        }
                            else
                            return "INVALID FORMAT; TOO MANY PARAMETER";
                    
                    
                    
                       case "GETTREE":
                    parts=Data.split("\\&"); //Expected parts[username=abcd&password=xyz]
                    if(!(parts.length==1 || parts.length==2))
                        return INVALID_FORMAT;
                    else if(!parts[0].toUpperCase().contains("KEY"))// || !parts[1].toUpperCase().contains("NODE"))
                        return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                    {
                        if(parts.length==1)
                        return GETTREE(parts[0].split("\\=")[1], null);
                        if(parts[1].toUpperCase().contains("NODE"))
                        {
                        if(parts.length==2)
                        return GETTREE(parts[0].split("\\=")[1], parts[1].split("\\=")[1]);
                        }else
                          return INVALID_KEYWORDS;  
                    }
                        
                    
                    
                       case "DELNODE":
                    parts=Data.split("\\&"); //Expected parts[username=abcd&password=xyz]
                    if(parts.length!=2)
                        return INVALID_FORMAT;
                    else if(!parts[0].toUpperCase().contains("KEY") || !parts[1].toUpperCase().contains("NODE"))
                        return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                    {
                        
                         String response= DELNODETREE(parts[0].split("\\=")[1], parts[1].split("\\=")[1]);
                         //update to delnode
                         Update_all_nodes(parts[0].split("\\=")[1], parts[1].split("\\=")[1]);
                         //update to parent
                         
                         //update childs
                        return response;
                      
                    }
                       
                    
                    case "UPDATENODE":
                    parts=Data.split("\\&"); //Expected parts[username=abcd&password=xyz]
                    if(parts.length!=3)
                        return INVALID_FORMAT;
                    else if(!parts[0].toUpperCase().contains("KEY") || !parts[1].toUpperCase().contains("PNODE")|| !parts[2].toUpperCase().contains("NODE"))
                        return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                    {
                     
                          String response= UPDATENODE(parts[0].split("\\=")[1], parts[1].split("\\=")[1],parts[2].split("\\=")[1]);
                          
                         Update_all_nodes(parts[0].split("\\=")[1], parts[1].split("\\=")[1]);
                         Update_all_nodes(parts[0].split("\\=")[1], parts[2].split("\\=")[1]);
                        return response;
                        
                    }
                    
                    case "DEPTH":
                    parts=Data.split("\\&"); //Expected parts[username=abcd&password=xyz]
                    if(parts.length!=1)
                        return INVALID_FORMAT;
                    else if(!parts[0].toUpperCase().contains("KEY"))
                        return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                        return DEPTH(parts[0].split("\\=")[1]);
                    
                   
                    case "PARENT":
                    parts=Data.split("\\&"); //Expected parts[username=abcd&password=xyz]
                    if(!(parts.length==1 || parts.length==2))
                        return INVALID_FORMAT;
                    else if(!parts[0].toUpperCase().contains("KEY"))// || !parts[1].toUpperCase().contains("NODE"))
                        return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                    {
                        if(parts.length==1)
                        return PARENT(parts[0].split("\\=")[1], null);
                        if(parts.length==2)
                        {
                           if(parts[1].split("\\=")[0].equals("NODE"))
                        return PARENT(parts[0].split("\\=")[1], parts[1].split("\\=")[1]);
                           else
                             return INVALID_KEYWORDS;  
                        
                        }
                    }
                    
                    
                    
                    case "CHILD":
                    parts=Data.split("\\&"); //Expected parts[username=abcd&password=xyz]
                    if(!(parts.length==1 || parts.length==2))
                        return INVALID_FORMAT;
                    else if(!parts[0].toUpperCase().contains("KEY"))// || !parts[1].toUpperCase().contains("NODE"))
                        return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                    {
                        if(parts.length==1)
                        return CHILD(parts[0].split("\\=")[1], null);
                        if(parts.length==2)
                        {
                           if(parts[1].split("\\=")[0].equals("NODE"))
                        return CHILD(parts[0].split("\\=")[1], parts[1].split("\\=")[1]);
                           else
                             return INVALID_KEYWORDS;  
                        
                        }
                    }

                    /***************ADMIN ***************************/
                    case "ADMIN":
                        parts=Data.split("\\&"); //Expected parts[username=abcd&password=xyz]
                    if(parts.length!=2)
                        return INVALID_FORMAT;
                    else if(!parts[0].toUpperCase().startsWith("KEY"))
                        return INVALID_KEYWORDS;
                    else  
                    {
                        if(parts[1].toUpperCase().startsWith("USERLIST"))
                        return GetUserList();
                         if(parts[1].toUpperCase().startsWith("LIVE"))
                        return GetLiveList();
                    }

                    
                    /*****************Message related ***************************/
                    
                    case "REGID":
                    parts=Data.split("\\&"); //Expected parts[username=abcd&password=xyz]
                    if(parts.length!=2)
                        return INVALID_FORMAT;
                    else if(!parts[0].toUpperCase().startsWith("KEY") && !parts[1].toUpperCase().startsWith("ID"))
                        return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                        return "TCP:ID:"+parts[0].split("\\=")[1]+":"+parts[1].split("\\=")[1];
                   
                    
                    case "PUSH":
                        parts=Data.split("\\&"); //Expected parts[username=abcd&password=xyz]
                    if(parts.length!=3)
                        return INVALID_FORMAT;
                    else if(!parts[0].toUpperCase().startsWith("KEY") && !parts[1].toUpperCase().startsWith("RNODE")&& !parts[2].toUpperCase().startsWith("DATA"))
                        return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                        return "TCP:PUSH:"+parts[0].split("\\=")[1]+":"+parts[1].split("\\=")[1]+":"+parts[2].split("\\=")[1];
                   
                    //KEY=1234567890&RNODE=ALLCHILDS&MSG=Hello
                    //TCP:PUSH:A876B688C52DD96980CD298CEA55BBDE:ALLCHILDS:Hello

                 /***************** ROUTER ***************************/
                    
                    case "GETINTROUTER":
                        parts=Data.split("\\&"); //Expected parts[username=abcd&password=xyz]
                    if(parts.length!=1)
                        return INVALID_FORMAT;
                    else if(!parts[0].toUpperCase().startsWith("KEY"))// && !parts[1].toUpperCase().startsWith("RNODE")&& !parts[2].toUpperCase().startsWith("DATA"))
                        return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                        return GetIntRouter(parts[0].split("\\=")[1]);
                   
                    case "ADDINTROUTER":
                        parts=Data.split("\\&"); //Expected parts[username=abcd&password=xyz]
                    if(parts.length!=2)
                        return INVALID_FORMAT;
                    else if(!parts[0].toUpperCase().startsWith("KEY") && !parts[1].toUpperCase().startsWith("ROID"))
                        return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                        return AddRouter(parts[0].split("\\=")[1],parts[1].split("\\=")[1],null);
        
                    
                    case "DELINTROUTER":
                        parts=Data.split("\\&"); //Expected parts[username=abcd&password=xyz]
                    if(parts.length!=2)
                        return INVALID_FORMAT;
                    else if(!parts[0].toUpperCase().startsWith("KEY") && !parts[1].toUpperCase().startsWith("ROID"))
                        return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                        return DelRouter(parts[0].split("\\=")[1],parts[1].split("\\=")[1]);
        
                  
                     case "ADDNODEINTROUTER":
                        parts=Data.split("\\&"); //Expected parts[username=abcd&password=xyz]
                    if(parts.length!=3)
                        return INVALID_FORMAT;
                    else if(!parts[0].toUpperCase().startsWith("KEY") && !parts[1].toUpperCase().startsWith("ROID") && !parts[1].toUpperCase().startsWith("NOID") )
                        return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                        return AddRouter(parts[0].split("\\=")[1],parts[1].split("\\=")[1],parts[2].split("\\=")[1]);
        
                     case "DELNODEINTROUTER":
                        parts=Data.split("\\&"); //Expected parts[username=abcd&password=xyz]
                    if(parts.length!=3)
                        return INVALID_FORMAT;
                    else if(!parts[0].toUpperCase().startsWith("KEY") && !parts[1].toUpperCase().startsWith("ROID") && !parts[1].toUpperCase().startsWith("NOID") )
                        return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                        return DelRouterNode(parts[0].split("\\=")[1],parts[1].split("\\=")[1],parts[2].split("\\=")[1]);
     
                    
                    //TOSHEET?KEY=1234567890&DATA=1234
                     case "GOOGLESHEET":
                        parts=Data.split("\\&"); //Expected parts[username=abcd&password=xyz]
                    if(parts.length<2)
                        return INVALID_FORMAT;
                    else if(!parts[0].toUpperCase().startsWith("KEY") && !parts[1].toUpperCase().startsWith("DATA"))
                        return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                    {
                        int loc=Data.indexOf("&DATA=[");
                        if(loc>0 && Data.length()>6)
                        {
                        String Xdata=Data.substring(loc+6);
                        if(Xdata.startsWith("[")&&Xdata.endsWith("]"))
                            {
                            Xdata=Xdata.substring(1,Xdata.length()-1);
                            return ToGooSheet(parts[0].split("\\=")[1],Xdata);
                            }
                            else
                                return INVALID_FORMAT;
                         }
                        else
                          return INVALID_FORMAT;  
                    }
        
                    
                    
                    
                    /******************************** TOLL *****************************/
                    
                    /*****************WALLET ***************************/
                    
                    case "GETUSERWALLET":
                        parts=Data.split("\\&"); //Expected parts[KEY=abcd]
                    if(parts.length!=1)
                        return INVALID_FORMAT;
                    else if(!parts[0].toUpperCase().startsWith("KEY"))
                        return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                        return SERVER.WALLET.GetUserWallet(parts[0].split("\\=")[1]);
         
                    case "GETUSERTRANSWALLET":
                        parts=Data.split("\\&"); //Expected parts[KEY=abcd]
                    if(parts.length!=1)
                        return INVALID_FORMAT;
                    else if(!parts[0].toUpperCase().startsWith("KEY"))
                        return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                        return SERVER.WALLET.TransAccountUser(parts[0].split("\\=")[1]);
         
                    
                    case "GETUSERACCOUNT":
                        parts=Data.split("\\&"); //Expected parts[KEY=abcd]
                    if(parts.length!=1)
                        return INVALID_FORMAT;
                    //else if(!parts[0].toUpperCase().startsWith("KEY"))
                    //    return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                        return SERVER.WALLET.UserAccount("");
        
                    case "GETTRANSACCOUNT":
                        parts=Data.split("\\&"); //Expected parts[KEY=abcd]
                    if(parts.length!=1)
                        return INVALID_FORMAT;
                    //else if(!parts[0].toUpperCase().startsWith("KEY"))
                    //    return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                        return SERVER.WALLET.TransAccountCurrent();//.TransAccount("");
        
                     case "GETTRANSACCOUNTLOG":
                        parts=Data.split("\\&"); //Expected parts[KEY=abcd]
                    if(parts.length!=1)
                        return INVALID_FORMAT;
                    //else if(!parts[0].toUpperCase().startsWith("KEY"))
                    //    return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                        return SERVER.WALLET.TransAccount("");
        
                     case "GETADMINACCOUNT":
                        parts=Data.split("\\&"); //Expected parts[KEY=abcd]
                    if(parts.length!=1)
                        return INVALID_FORMAT;
                    //else if(!parts[0].toUpperCase().startsWith("KEY"))
                    //    return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                        return SERVER.WALLET.AdminAccount("");
     
                    
                    
                    /**********************************credit-debit************************/
                      case "CREDITUSERWALLET_RECHARGE":
                        parts=Data.split("\\&"); //Expected parts[KEY=abcd]
                    if(parts.length!=2)
                        return INVALID_FORMAT;
                    else if(!parts[0].toUpperCase().startsWith("KEY") &&!parts[1].toUpperCase().startsWith("MONEY") )
                        return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                        return SERVER.WALLET.UserCreditWallet_Recharge(parts[0].split("\\=")[1],parts[1].split("\\=")[1]);
        
                      case "CREDITUSERWALLET_ADMINREFUND":
                        parts=Data.split("\\&"); //Expected parts[KEY=abcd]
                    if(parts.length!=1)
                        return INVALID_FORMAT;
                    else if(!parts[0].toUpperCase().startsWith("KEY"))// &&!parts[1].toUpperCase().startsWith("MONEY") )
                        return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                        return SERVER.WALLET.UserCreditWallet_ADMINREFUND(parts[0].split("\\=")[1]);//,parts[1].split("\\=")[1]);
        
                        case "CREDITUSERWALLET_USERREFUND":
                        parts=Data.split("\\&"); //Expected parts[KEY=abcd]
                    if(parts.length!=1)
                        return INVALID_FORMAT;
                    else if(!parts[0].toUpperCase().startsWith("KEY"))// &&!parts[1].toUpperCase().startsWith("MONEY") )
                        return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                        return SERVER.WALLET.UserCreditWallet_USERREFUND(parts[0].split("\\=")[1]);//,parts[1].split("\\=")[1]);
        
                    
                       case "DEBITUSERWALLET":
                        parts=Data.split("\\&"); //Expected parts[KEY=abcd]
                    if(parts.length!=3)
                        return INVALID_FORMAT;
                    else if(!parts[0].toUpperCase().startsWith("KEY") &&!parts[1].toUpperCase().startsWith("MONEY")&&!parts[2].toUpperCase().startsWith("REASON") )
                        return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                        return SERVER.WALLET.UserDebitWallet(parts[0].split("\\=")[1],parts[1].split("\\=")[1],parts[2].split("\\=")[1]);//DebitWallet(parts[0].split("\\=")[1],parts[1].split("\\=")[1]);
        
                    
                       case "CREDITADMINWALLET":
                        parts=Data.split("\\&"); //Expected parts[KEY=abcd]
                    if(parts.length!=2)
                        return INVALID_FORMAT;
                    else if(!parts[0].toUpperCase().startsWith("KEY") &&!parts[1].toUpperCase().startsWith("REASON") )
                        return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                        return SERVER.WALLET.AdminCreditWallet(parts[0].split("\\=")[1],parts[1].split("\\=")[1]);//,parts[2].split("\\=")[1]);//DebitWallet(parts[0].split("\\=")[1],parts[1].split("\\=")[1]);
        
                 /*******************VAHAN VEHECLE INFO ******************************/   
           
                    
                    case "DELVEHECLEVAHAN":
                        parts=Data.split("\\&"); //Expected parts[KEY=abcd]
                    if(parts.length!=1)
                        return INVALID_FORMAT;
                    else if(!parts[0].toUpperCase().startsWith("VEHECLENO"))// &&!parts[1].toUpperCase().startsWith("REASON") )
                        return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                        return SERVER.VAHANINFO.RemoveVehecleInfoVahan(parts[0].split("\\=")[1]);//,parts[1].split("\\=")[1]);/
                    
                    case "GETVEHECLEVAHAN":
                        parts=Data.split("\\&"); //Expected parts[KEY=abcd]
                    if(parts.length!=1)
                        return INVALID_FORMAT;
                    else if(!parts[0].toUpperCase().startsWith("VEHECLENO"))// &&!parts[1].toUpperCase().startsWith("REASON") )
                        return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                        return SERVER.VAHANINFO.GetVehecleInfoVahan(parts[0].split("\\=")[1]);//,parts[1].split("\\=")[1]);//,parts[2].split("\\=")[1]);//DebitWallet(parts[0].split("\\=")[1],parts[1].split("\\=")[1]);
        
                      case "GETALLVEHECLEVAHAN":
                        parts=Data.split("\\&"); //Expected parts[KEY=abcd]
                    if(parts.length!=1)
                        return INVALID_FORMAT;
                    //else if(!parts[0].toUpperCase().startsWith("KEY"))// &&!parts[1].toUpperCase().startsWith("REASON") )
                     //   return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                        return SERVER.VAHANINFO.GetAllVehecleInfoVahan();//
                    
                     case "EDITVEHECLEVAHAN":
                        parts=Data.split("\\&"); //Expected parts[KEY=abcd]
                    if(parts.length!=2)
                        return INVALID_FORMAT;
                    else if(!parts[1].toUpperCase().startsWith("CARNO") &&!parts[1].toUpperCase().startsWith("INFO"))
                        return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                        return SERVER.VAHANINFO.UpdateVehecleInfoVahan(parts[0].split("\\=")[1],parts[1].split("\\=")[1]);//,parts[2].split("\\=")[1]);//DebitWallet(parts[0].split("\\=")[1],parts[1].split("\\=")[1]);

                 /******************* VEHECLE INFO ******************************/   
              
                       case "GETVEHECLE":
                        parts=Data.split("\\&"); //Expected parts[KEY=abcd]
                    if(parts.length!=1)
                        return INVALID_FORMAT;
                    else if(!parts[0].toUpperCase().startsWith("KEY"))// &&!parts[1].toUpperCase().startsWith("REASON") )
                        return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                        return SERVER.VEHECLE.GetVehecleInfo(parts[0].split("\\=")[1]);//,parts[1].split("\\=")[1]);//,parts[2].split("\\=")[1]);//DebitWallet(parts[0].split("\\=")[1],parts[1].split("\\=")[1]);
        
                        case "GETALLVEHECLE":
                        parts=Data.split("\\&"); //Expected parts[KEY=abcd]
                    if(parts.length!=1)
                        return INVALID_FORMAT;
                    //else if(!parts[0].toUpperCase().startsWith("KEY"))// &&!parts[1].toUpperCase().startsWith("REASON") )
                     //   return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                        return SERVER.VEHECLE.GetAllVehecleInfo();//
                    
                    /*
                    case "ADDVEHECLE":
                        parts=Data.split("\\&"); //Expected parts[KEY=abcd]
                    if(parts.length!=3)
                        return INVALID_FORMAT;
                    else if(!parts[0].toUpperCase().startsWith("KEY") &&!parts[1].toUpperCase().startsWith("CARNO") &&!parts[1].toUpperCase().startsWith("TYPE"))
                        return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                        return SERVER.VEHECLE.AddVehecleInfo(parts[0].split("\\=")[1],parts[1].split("\\=")[1],parts[2].split("\\=")[1]);//DebitWallet(parts[0].split("\\=")[1],parts[1].split("\\=")[1]);
                    */
                    
                    case "EDITVEHECLE":
                        parts=Data.split("\\&"); //Expected parts[KEY=abcd]
                    if(parts.length!=3)
                        return INVALID_FORMAT;
                    else if(!parts[0].toUpperCase().startsWith("KEY") &&!parts[1].toUpperCase().startsWith("CARNO") &&!parts[1].toUpperCase().startsWith("TYPE"))
                        return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                        return SERVER.VEHECLE.UpdateVehecleInfo(parts[0].split("\\=")[1],parts[1].split("\\=")[1],parts[2].split("\\=")[1]);//DebitWallet(parts[0].split("\\=")[1],parts[1].split("\\=")[1]);

                        /**************************** TOLL ************************/
                        case "TOLLADD":
                        parts=Data.split("\\&"); //Expected parts[KEY=abcd]
                    if(parts.length!=2)
                        return INVALID_FORMAT;
                    else if(!parts[0].toUpperCase().startsWith("ID") &&!parts[1].toUpperCase().startsWith("INFO"))
                        return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                        return SERVER.TOLLINF.AddTollInfo(parts[0].split("\\=")[1],parts[1].split("\\=")[1]);
                                
                    
                        case "TOLLGET":
                        parts=Data.split("\\&"); //Expected parts[KEY=abcd]
                    if(parts.length!=1)
                        return INVALID_FORMAT;
                    else if(!parts[0].toUpperCase().startsWith("ID"))// &&!parts[1].toUpperCase().startsWith("INFO"))
                        return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                        return SERVER.TOLLINF.GetTollInfo(parts[0].split("\\=")[1]);
                    
                    case "TOLLNEARBY":
                        parts=Data.split("\\&"); //Expected parts[KEY=abcd]
                    if(parts.length!=3)
                        return INVALID_FORMAT;
                    else if(!parts[0].toUpperCase().startsWith("MYLAT") &&!parts[1].toUpperCase().startsWith("MYLON")&&!parts[2].toUpperCase().startsWith("TOTAL"))
                        return INVALID_KEYWORDS;
                    else  //all ok. Verify username, Password
                        return SERVER.TOLLINF.GetNearbyTolls(parts[0].split("\\=")[1],parts[1].split("\\=")[1],parts[2].split("\\=")[1]);
                    
                    
                                
                    
                    
                   default:
                       return Info.APIlist;//"Invalid Command";
                      // break;
          }
        }
        catch(Exception ex){
            return "Invalid Request format";
        }        
       // return "OK";
    }
    
    GoogleSheet GS=new GoogleSheet();
    String ToGooSheet(String _Key, String Data)
    {
        GS=new GoogleSheet();
        return GS.sendToGoogleSheet(_Key, Data);
    }
    
    String GetWallet( String _KEY)
    {
        return "";//SERVER.WALLET.GetWallet(_KEY);
    }
    
    
    String CreditWallet( String _KEY, String Money)
    {
        return "";//SERVER.WALLET.CreditWallet(_KEY, Money);
    }
    
    
    String DebitWallet( String _KEY, String Money)
    {
        return "";//SERVER.WALLET.DebitWallet(_KEY, Money);
    }
    
    
    void Update_all_nodes(String KEY, String UID)//, boolean isParentToUpdate, boolean isChildsToUpdate)
    {
        
       for(ServerNode SN:  SERVER.AllClientList.values())
       {
                SN._updateNodeProperty(KEY, null);
           
       }
      
    }
    
    /********** ROUTER *******************/
    private String GetIntRouter(String UID)
    {
        String Response="FAIL:NO_ROUTER";
        String res= SERVER.ROUTER.GetAllRouterList(UID,"I").split("\r\n")[0];
        if(res.length()>0)
                Response="INT_ROUTER->"+res+"\r\n";
        List<String> ROlist=(List) Arrays.asList(res.split("\\,"));
        String _Sublist="";
        for(String ROid:ROlist)
        {
            _Sublist=GetNodeList(UID,ROid);
            Response=Response+ROid+"->"+_Sublist+"\r\n";
            
        }
      Response=Response.replace("\n", "<br>");
        //sTRING X=GetNodeList();
        return Response;
    }
    
     private String AddRouter(String UID,String ROID,String NID)
    {
        
        List<String> DEV_ID=new ArrayList<String>();
        if(NID==null)
            NID="";
        if(NID.length()>0)
            DEV_ID.add(NID);
        String res=SERVER.ROUTER.AddInternalRouterAndNodes(UID, ROID, DEV_ID);
        return res;
    }
     
       private String DelRouter(String UID,String ROID)
    {
        String res=SERVER.ROUTER.RemoveRouter(UID, ROID,"I");
        return res;
    }
       
       private String GetNodeList(String UID,String ROID)
    {
        String res=SERVER.ROUTER.GetRouterNodeList(UID, ROID,"I");
        return res;
    }
   
         private String DelRouterNode(String UID,String ROID,String NID)
    {
        if(NID==null)
            NID="";
        String res=SERVER.ROUTER.RemoveInternalRouterNodes(UID, ROID, NID);
        return res;
    }
   
       
    /**************ADMIN ***************************/
    private String GetLiveList()
    {
        String UIDList="";
       for (String UID: SERVER.AllClientList.keySet())
       {
           UIDList=UIDList+"["+UID+"]";
       }
        

        return UIDList;
    }
    private String GetUserList()
    {
        String Users="";
        
        Users=SERVER.DBlogin.GetAllUser();
        return Users;
        
    }
    
    /**************** TREE **********************************/
    
    private String CHILD(String MASTERKEY, String NODE)
    {
      
      String info= SERVER.KeyDevice.GetMyChildIDs(MASTERKEY, NODE);//.UpdateNode(MASTERKEY, PNODE, NODE);
        //boolean authPass= SERVER.DBlogin.VerifyLoginDetails(Username, Pass);
         info=info.replace("\n", "<br>");
    return info;
    }
    
    private String PARENT(String MASTERKEY, String NODE)
    {
      
      String info= SERVER.KeyDevice.GetMyParentID(MASTERKEY, NODE);//.UpdateNode(MASTERKEY, PNODE, NODE);
        //boolean authPass= SERVER.DBlogin.VerifyLoginDetails(Username, Pass);
         info=info.replace("\n", "<br>");
    return info;
    }
    
    private String DEPTH(String MASTERKEY)
    {
      
      String info= Integer.toString(SERVER.KeyDevice.GetDepth(MASTERKEY));
        //boolean authPass= SERVER.DBlogin.VerifyLoginDetails(Username, Pass);
         info=info.replace("\n", "<br>");
    return info;
    }
    
    private String UPDATENODE(String MASTERKEY, String PNODE, String NODE)
    {
      
      String info= SERVER.KeyDevice.UpdateNode(MASTERKEY, PNODE, NODE);
        //boolean authPass= SERVER.DBlogin.VerifyLoginDetails(Username, Pass);
         info=info.replace("\n", "<br>");
    return info;
    }
    
     private String DELNODETREE(String MASTERKEY, String NODE)
    {
      
      String info= SERVER.KeyDevice.DeleteNodeTree(MASTERKEY, NODE);
        //boolean authPass= SERVER.DBlogin.VerifyLoginDetails(Username, Pass);
         info=info.replace("\n", "<br>");
    return info;
    }
   
     
    private String ADDNODE(String MASTERKEY, String PNODE, String CNODE)
    {
      
      String info= SERVER.KeyDevice.AddSubNode(MASTERKEY, PNODE, CNODE, true, "No info for:"+PNODE);
        //boolean authPass= SERVER.DBlogin.VerifyLoginDetails(Username, Pass);
         info=info.replace("\n", "<br>");
    return info;
    }
    
     private String GETTREE(String MASTERKEY, String NODE)
    {
      String info= SERVER.KeyDevice.GetNodeTree(MASTERKEY, NODE);
        //boolean authPass= SERVER.DBlogin.VerifyLoginDetails(Username, Pass);
        info=info.replace("\n", "<br>");
        return info;
    }
    
    
    
    /*************** LOGIN *************************************************/
    
    
      private String UserKeyReset(String Username, String Pass)
    {
       boolean authPass= SERVER.DBlogin.VerifyLoginDetails(Username, Pass);
                             String KEY="";
                             if(authPass)
                             {
                                 String _oldKey= SERVER.DBlogin.GetKeyDetails(Username);
                                KEY= SERVER.DBlogin.updateKeyDetails(Username, Pass);
                                 
                                 email=new email(null);
                                 
                                //String  _KEY= SERVER.DBlogin.GetKeyDetails(Username);
                                  String MSG="Hello Push User,\n Your New Push Credentials:\n"+
                                          "Username:"+Username+"\n"+
                                          "Password:"+Pass+"\n"+
                                          "New_KEY:"+KEY+"\n\n\n Regards,\n Admin, P2PCluster";
                                  email.sendmaily(Username,"Push KEY Reset",MSG );
                                
                                  return "SUCCESS. New KEY has been generated. Credentials have been sent to user ["+Username+"]";
                             }
                             else
                                 return "FAIL.Credentials do not match.";
    }
      
     private String UserPassReset(String Username, String Pass, String NewPass)
    {
        boolean authPass= SERVER.DBlogin.VerifyLoginDetails(Username, Pass);
                             String KEY="";
                             if(authPass)
                             {
                                 SERVER.DBlogin.updatePassDetails(Username, Pass, NewPass);
                                 
                                 email=new email(null);
                                String  _KEY= SERVER.DBlogin.GetKeyDetails(Username);
                                  String MSG="Hello Push User,\n Your New Push Credentials:\n"+
                                          "Username:"+Username+"\n"+
                                          "New_Password:"+NewPass+"\n"+
                                          "KEY:"+_KEY+"\n\n\n Regards,\n Admin, P2PCluster";
                                  email.sendmaily(Username,"Push Passowrd Reset",MSG );
                                
                                  return "SUCCESS. New password has been set.";
                             }
                             else
                                 return "FAIL.Credentials do not match";
                             
     
        
    }
     
     private String UserDelete(String Username, String Pass)
    {
        try
        {
                             if(SERVER.DBlogin.isUserValid(Username)) //user already authorized
                          {
                             boolean authPass= SERVER.DBlogin.VerifyLoginDetails(Username, Pass);
                             String KEY="";
                             if(authPass)
                             {
                                SERVER.DBlogin.deleteLoginDetails(Username, Pass);
                                email=new email(null);
                                 boolean result=email.sendmaily(Username,"Thanks for using P2PCluster","Your Account has been suspended");//Send_Mail("chandan2002x@gmail.com", "Hello");
                                 return "[SUCCESS] User ID ["+ Username+"] account is deleted from Push Service";
          
                             }
                             else
                                 return "[FAIL] Account exists but credentials do not matched.";
                             
                          }
                             return "[FAIL] Account-id ["+Username+"] Does not exist";
        }
       catch(Exception ex){
       int i=0;
        return "Server Error!";
       }
       // return "";
    }
     
     
    private String UserAuth(String Username, String Pass, String Token)
    {
        
        
                          if(SERVER.DBlogin.isUserValid(Username)) //user already exist
                          {
                             boolean authPass= SERVER.DBlogin.VerifyLoginDetails(Username, Pass);
                             String KEY="";
                             if(authPass)
                             {
                                      email=new email(null);
                                  String  _KEY= SERVER.DBlogin.GetKeyDetails(Username);
                                  String MSG="Hello Push User,\n Your Push Credentials:\n"+
                                          "Username:"+Username+"\n"+
                                          "Password:"+Pass+"\n"+
                                          "KEY:"+_KEY+"\n\n\n Regards,\nAdmin, P2PCluster";
                                  email.sendmaily(Username,"Push Credentials",MSG );
                                
                                    try
                            {
                            if((new StringBuffer(Username.split("@")[0]).reverse().toString()).equals(Token));
                                return "OK:"+Username+":"+Pass+":"+_KEY+":";
                            }catch(Exception ex){}

                                  return "[SUCCESS} Account is already activated. Credentials have been mailed to ["+Username+"]";

                             }
                             else
                                 return "[FAIL] Account is already activated but credentials not matched.";
                             
                          }
                      boolean result=  isUserLinkVerified(Username,Pass,Token);
                      if(result)
                      {
                            SERVER.DBlogin.insertLoginDetails(Username, Pass);
                     
                                                              email=new email(null);
                                  String  _KEY= SERVER.DBlogin.GetKeyDetails(Username);
                                  String MSG="Hello Push User,\n Your Credentials:\n"+
                                          "Username:"+Username+"\n"+
                                          "Password:"+Pass+"\n"+
                                          "KEY:"+_KEY+"\n\n\n Regards,\nAdmin, P2PCluster";
                                  email.sendmaily(Username,"Push Credentials",MSG );
    
                          
                             try
                            {
                            if((new StringBuffer(Username.split("@")[0]).reverse().toString()).equals(Token));
                                return "OK:"+Username+":"+Pass+":"+_KEY+":";
                            }catch(Exception ex){}

                             return "[SUCCESS] Great! Account ID is verified. Push credentials will be sent to mail id: "+Username +"["+_KEY+"]";
                      
                        //Send_Mail();
                      }
                       else
                      return "[FAIL] Sorry! It seems the link is expired. Regenerate Token again.";
                          
                    

    }
    
    private String UsernamePassVerification(String Username, String pass)
    {
        if(!Username.contains("@"))
            return "Username should be mail-id which is unique";
        try
        {
    
      
        if(! SERVER.DBlogin.isUserValid(Username)) //user does not exist
        {
           email=new email(null);
           
           String _MSG=MakeQuery(Username, pass);
            boolean result=email.sendmaily(Username,"Welcome to P2PCluster",_MSG);//Send_Mail("chandan2002x@gmail.com", "Hello");
           //email.send_Mail();
           if(result)
           return "e-mail has been sent to "+Username +" for activation";
           else
               return "Please ensure the username mail-id";
               
        }
        else //user exists
        {
            if(pass.equals(""))
                pass="UNKNOWN";
            if(SERVER.DBlogin.isUserValid(Username))
           // if(SERVER.DBlogin.VerifyLoginDetails(Username, pass))
            {
                email=new email(null);
                String  KEY= SERVER.DBlogin.GetKeyDetails(Username);
                String MSG="Hello P2PCluster User,\n Your Credentials:\n"+
                        "Username:"+Username+"\n"+
                        "Password:"+pass+"\n"+
                        "KEY:"+KEY+"\n\n\n Regards,\nAdmin, P2PCluster";
                email.sendmaily(Username,"P2PCluster Credentials",MSG );
                 return "Your account is already activated. Credentials have been mailed to ["+Username+"]"+"["+KEY+"]";
            }
            else //user exist, password wrong
                         
              return "Your account exists, but credential mismatch";
            
        }
        
        }
        catch (Exception ex){
            int i=0;
        }
        return "";
    }
    
    
    private String MakeQuery(String Username, String Pass)
    {
        String MY_IP=SERVER.MYIP;//"127.0.0.1";
           String MY_PORT=SERVER._ServerPort;//"8084";
           String TOKEN=GenerateToken(Username, Pass);
         
        String _MSG="  Hello P2PCluster user,\n"+
                    "  Your P2PCluster account needs to be verified. Please verify the username and Password.\n"+
                    "  Use below link for activation of account and to retreive the KEY. \n"+
                    "  The link is valid for one hour.\n\n"+
                   "http://"+MY_IP+":"+MY_PORT+ "/USERAUTH?username="+ Username+ "&password="+Pass+"&TOKEN="+TOKEN;
           
        return _MSG;
    }
   
    
     private boolean isUserLinkVerified(String Username, String Pass,String Token)
    {
        try
        {
        String  revUser= new StringBuffer(Username.split("@")[0]).reverse().toString();
        if(revUser.equals(Token))
            return true;
        }catch(Exception ex){}
        
           String KeySeedBase= new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime());
           String KeySeedNow=new SimpleDateFormat(".HH").format(Calendar.getInstance().getTime());
            Integer KeySeedNextInt=Integer.parseInt(new SimpleDateFormat("HH").format(Calendar.getInstance().getTime()))+1;
           String KeySeedNext="."+KeySeedNextInt.toString();
          
           Integer KeySeedOldInt=Integer.parseInt(new SimpleDateFormat("HH").format(Calendar.getInstance().getTime()))-1;
           if(KeySeedOldInt<0) KeySeedOldInt=23;
           String KeySeedOld="."+KeySeedOldInt.toString();
         
           
           String KeySeedMasterNowNext=Username+":"+Pass+":"+KeySeedBase+KeySeedNow+":"+KeySeedBase+KeySeedNext;
        String KeySeedNowNext=  getHash(KeySeedMasterNowNext);

             String KeySeedMasterOldNow=Username+":"+Pass+":"+KeySeedBase+KeySeedOld+":"+KeySeedBase+KeySeedNow;
        String KeySeedOldNow=  getHash(KeySeedMasterOldNow);

       // boolean result=New String(Token).
        if(Token.equals(KeySeedNowNext)||Token.equals(KeySeedOldNow))
        return true;
        else
          return  false;
        
    }
    
    
     public String GenerateToken (String Username, String Pass)
         {
           String KeySeedBase= new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime());
           String KeySeed1=new SimpleDateFormat(".HH").format(Calendar.getInstance().getTime());
            Integer KeySeed2Int=Integer.parseInt(new SimpleDateFormat("HH").format(Calendar.getInstance().getTime()))+1;
           String KeySeed2="."+KeySeed2Int.toString();
         
           String KeySeedMaster=Username+":"+Pass+":"+KeySeedBase+KeySeed1+":"+KeySeedBase+KeySeed2;
            String KeySeed=  getHash(KeySeedMaster);
           return KeySeed;
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
          
         
    
    
}
