/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pushserver.mavenpush1;

/**
 *
 * @author workaholic
 */
public class Message {
    
    public int REGID(String Key, String ID) //0=Success; 1=Invalid key; 2=Invalid ID
    {
        
       //if( SERVER.KeyDevice.isKeyExist(Key))
       {
        
           if(SERVER.KeyDevice.IsChildExist(Key, ID)) //ID Present in Registered list
           return 0;
           else
           {
              // SERVER.TempKeyList.add(Key);
              // SERVER.KeyDevice.AddNode(Key, ID, true, "nothing");
               //return 0;
               return 2;      //NODE added
           }
       }
      // else 
      //     return 1;
        
        
        //return 0;
    }
    
    
}
