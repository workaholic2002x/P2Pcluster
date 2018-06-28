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
public class CachedData extends Thread
{
    
     public void Add_Cache(CNode _node, byte[] cacheData)
      {
         
          if(_node.STORE_DATA.size()>=5)
              _node.STORE_DATA.removeFirst();
          _node.STORE_DATA.add(cacheData);
          
      }
    
     
     public void Clear_Cache(CNode _node)
     {
         _node.STORE_DATA.clear();
         
     }
     
   public  byte[] Get_Cache(CNode _node, Integer ID)
     {
       if(  ID>=_node.STORE_DATA.size() && ID>0)
           ID=ID-1;
       
         return _node.STORE_DATA.get(ID);
       }
     
     public String Get_CacheString(CNode _node, Integer ID)
     {
         try
         {
         if(ID>0)
         {
            if(  ID<=_node.STORE_DATA.size())
            {
                ID=ID-1;

               
            return  new String(_node.STORE_DATA.get(ID), "UTF-8");//_node.STORE_DATA.get(ID).toString();
            }
            else
                return "";
          }
         else
         {
             String _data="";
             for(int i=0;i<_node.STORE_DATA.size();i++)
             {
                 
                 _data=_data+">"+new String(_node.STORE_DATA.get(i), "UTF-8");
             }
             return _data;
         }
         }
         catch(Exception ex){
         return "Err";
         }
         
     }
}
