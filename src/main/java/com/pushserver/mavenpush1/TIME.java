/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pushserver.mavenpush1;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 *
 * @author workaholic
 */
public class TIME {
    
    Map<String, String> OffsetZone = new HashMap<>();

    public TIME()
    {
        getAllZoneIds();
    }
    
    public String getTime(String OffestTime)
    {
        String _time="";
        if(OffsetZone.containsKey(OffestTime))
            _time=OffsetZone.get(OffestTime);
        
           final Date currentTime = new Date();

        final SimpleDateFormat sdf = new SimpleDateFormat("z:(EEE)(d,MM,yyyy)(HH:mm:ss)");

        // Give it to me in GMT time.
            sdf.setTimeZone(TimeZone.getTimeZone(_time));
        String DT=sdf.format(currentTime);

        
        long epoch = 0;
        String Epoch = "";   // UTC

        try
        {
        Date datenew = sdf.parse(DT);
        Epoch = "("+String.valueOf(datenew.getTime()/1000)+")";
        }catch (Exception ex){}

        if(Epoch.length()>0)
            DT="["+DT+Epoch+"]";
        
        return DT;
    }
    
    
    private Map<String, String> getAllZoneIds() {

        List<String> zoneList= (List<String>)new ArrayList<String>(ZoneId.getAvailableZoneIds());
        
        LocalDateTime dt = LocalDateTime.now();

        for (String zoneId : zoneList) {

            ZoneId zone = ZoneId.of(zoneId);
            ZonedDateTime zdt = dt.atZone(zone);
            ZoneOffset zos = zdt.getOffset();

            //replace Z to +00:00
            String offset = zos.getId().replaceAll("Z", "+00:00");

            OffsetZone.put( offset,zone.toString());

        }

        return OffsetZone;

    }
    
}
