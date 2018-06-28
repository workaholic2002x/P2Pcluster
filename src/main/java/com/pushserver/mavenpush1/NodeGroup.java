/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pushserver.mavenpush1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author workaholic
 */
public class NodeGroup{
    
    static NG DEFAULT=new NG();
    
    
    public static class NodeInfo implements Serializable 
    {
    
        boolean hasSubNode;
        String NInfo;
        //HashMap<String,Object>Group_members;//=new HashMap<String,NodeInfo>();// ID, Property
        public  NodeInfo()
        {
            hasSubNode=false;
            NInfo="";
        }
        
        
        
    }
    
    
    
    public static class NG implements Serializable 
        {
    
            Map<String,NodeInfo>Members;//=new HashMap<String,NodeInfo>();// ID, Property
            public NG()//String key, String master,List<String> Members)
             {
             Members=new HashMap<String,NodeInfo>();
            }

        }
    
}

