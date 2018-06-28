/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pushserver.mavenpush1;

import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

/**
 *
 * @author workaholic
 */

class CNode {


class UserProps{
 boolean _1TREE;//=new ArrayList<String>();//FALSE,TRUE,TRUE,TRUE
//Node Per Tree
int _2NODEPERTREE;//=new ArrayList<String>();//1,5,100,1000
//Delayed 
boolean _3TIMERPUSH;//=new ArrayList<String>();//FALSE,FALSE,TRUE,TRUE
//Hold Message
boolean _4HOLDPUSH;//=new ArrayList<String>();//FALSE,FALSE,TRUE,TRUE
//HOLD message Buffer
int _5HOLDBUFFER;//=new ArrayList<String>();//0,1,5,1000
//e-mail enabled
boolean _6MAILPUSH;//=new ArrayList<String>();//FALSE,FALSE,TRUE,TRUE
//SMS
boolean _7SMSPUSH;//=new ArrayList<String>();//FALSE,FALSE,TRUE,TRUE
//International SMS
boolean _8INTSMSPUSH;//=new ArrayList<String>();//FALSE,FALSE,FALSE,FALSE
//SSL LAYER
boolean _9SSLPUSH;//=new ArrayList<String>();//FALSE,FALSE,TRUE,TRUE
//Max Node
long _10MAXNODE;
}

    
        Socket soc;
        Long CreatedON;
        String ID;
        int TimeOutSec; //Timeout for validity Check; Time wait to get command from Client
        String IP;
        String KEY;
        int COMCheck;   //Interval of Communication channel check
        int Property;
        UserProps UserP;
        List<String> Childs;//=new ArrayList<String>();
        List<String> AllChilds;
        List<String> Friends;
        String Parent;
        boolean isActive;
        boolean isKeyVerified;
        
        boolean isSTCP;    //Secure TCP
        String RND="";
        String SKEY="";
        String AKEY="";
        
        boolean isDSTCP; // Dynamic Secure TCP
        //String DKEY="";
        
        ServerNode LinkedClient=null;//=new ServerNode();
        String REMID="";
        
        //List<String> STORE_DATA;
        
        LinkedList<byte[]> STORE_DATA;
        public CNode()
        {
            
            CreatedON=(long)0;
            ID="";
            TimeOutSec=0;
            IP="";
            KEY="";
            COMCheck=0;
            Property=0;
            Childs=new ArrayList<String>();
            AllChilds=new ArrayList<String>();
            Friends=new ArrayList<String>();
            Parent="";
            isActive=false;
            isKeyVerified=false;
            isSTCP=false;
            SKEY="";
            AKEY="";
            
            isDSTCP=false;
            //DKEY="";
            REMID="";
            
            STORE_DATA=new LinkedList<byte[]>();//new ArrayList<String>();
            
            
            UserP=new UserProps();
            UserP._5HOLDBUFFER=0;
            UserP._4HOLDPUSH=false;
            UserP._8INTSMSPUSH=false;
            UserP._6MAILPUSH=false;
            UserP._2NODEPERTREE=1;
            UserP._7SMSPUSH=false;
            UserP._9SSLPUSH=false;
            UserP._3TIMERPUSH=false;
            UserP._1TREE=false;
            UserP._10MAXNODE=1;
            
            //LinkedClient=new ServerNode();
            try
            {
            soc.setReceiveBufferSize(1*1024);
            soc.setSendBufferSize(1*1024);
            
            }catch(Exception ex){}
        }
        
}

