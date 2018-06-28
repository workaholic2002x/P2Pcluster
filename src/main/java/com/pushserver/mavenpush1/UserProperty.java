/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pushserver.mavenpush1;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author workaholic
 */
public class UserProperty {
 List<String> MAXNODE=new ArrayList<String>();//FALSE,TRUE,TRUE,TRUE

List<String> TREE=new ArrayList<String>();//FALSE,TRUE,TRUE,TRUE
//Node Per Tree
List<String> NODEPERTREE=new ArrayList<String>();//1,5,100,1000
//Delayed 
List<String> TIMERPUSH=new ArrayList<String>();//FALSE,FALSE,TRUE,TRUE
//Hold Message
List<String> HOLDPUSH=new ArrayList<String>();//FALSE,FALSE,TRUE,TRUE
//HOLD message Buffer
List<String> HOLDBUFFER=new ArrayList<String>();//0,1,5,1000
//e-mail enabled
List<String> MAILPUSH=new ArrayList<String>();//FALSE,FALSE,TRUE,TRUE
//SMS
List<String> SMSPUSH=new ArrayList<String>();//FALSE,FALSE,TRUE,TRUE
//International SMS
List<String> INTSMSPUSH=new ArrayList<String>();//FALSE,FALSE,FALSE,FALSE
//SSL LAYER
List<String> SSLPUSH=new ArrayList<String>();//FALSE,FALSE,TRUE,TRUE
//
}


