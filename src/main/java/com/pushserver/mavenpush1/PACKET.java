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




class _PACKET {
   String CMD;
    String KEY;
    byte[] DATA;  
}

public class PACKET {
  
    public _PACKET GPACKET;
   
    public boolean PACKET_isSSL(byte[] data)
    {
        //https://www.ibm.com/support/knowledgecenter/en/SSB23S_1.1.0.8/com.ibm.ztpf-ztpfdf.doc_put.08/gtps5/s5rcd.html
         long len=0;
        
        boolean isTLS=false;
        if(data[0]==20 ||data[0]==21 ||data[0]==22 ||data[0]==23)
        {
            if(data[1]==03)
            if(data[2]==01 ||data[2]==00)
                {
                  len= ( ((long)data[3]<<8) & 0xffffL) |((long)data[4] & 0xffL); 
                   
                }
        }
        if((int)len==data.length-5)
            isTLS=true;
        
    return isTLS;
    }
    
    public _PACKET PACKET_parse(byte[] data)
    {
        _PACKET _PACK=new _PACKET();
        char Separator=' ';
        try
        {
        String dummyString=new String(data, "UTF-8");
            if(dummyString.startsWith("GET"))
                Separator=' ';
            else
                Separator=':';

        int _SepPos=dummyString.indexOf( Separator); // filter by space
        if(_SepPos>0)
        {
               byte[] _cmdb=new byte[_SepPos];
                System.arraycopy(data,0,_cmdb, 0,_SepPos);
                _PACK.CMD=new String(_cmdb,"UTF-8").toUpperCase();
                byte[] _datab=new byte[data.length-_SepPos-1];
                 System.arraycopy(data,_SepPos+1,_datab,0, _datab.length);
                 _PACK.DATA=_datab;
        }
        
        }catch(Exception ex){}
        
        return _PACK;
    }
   
}
