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


public class TOLLPARAMS 
{
  
    class TollLoc 
    {
        public double latitude;
        public double longitude;

        TollLoc(String locinfo) {
            String[] loc=locinfo.split(":");
            latitude = Double.parseDouble(loc[0]);
            longitude = Double.parseDouble(loc[1]);;
        }
    }
    
    
    /*
    _fare[2].Add("1:CAR/JEEP/VAN",102.0);
				_fare[2].Add("2:LCV",160.0);
				_fare[2].Add("3.BUS/TRUCK",330.0);
				_fare[2].Add("4:Upto 3Axle Vehecle",360.0);
				_fare[2].Add("5:4-6 Axle Vehecle",520.0);
				_fare[2].Add("6:HCM/EME",520.0);
				_fare[2].Add("7:7 or more Axle",630.0);
    */
    class TollFare 
    {
        public double CAR_JEEP_VAN;
        public double LCV;
        public double BUS_TRUCK;
        public double UPTO_3AXLE;
        public double UPTO_4TO6AXLE;
        public double HCM_EME;
        public double MORE_7AXLE;
        
        TollFare(String Farelist) 
        {
            String[]Fares= Farelist.split(":");
                CAR_JEEP_VAN=Double.parseDouble(Fares[0]);
                LCV=Double.parseDouble(Fares[1]);
                BUS_TRUCK=Double.parseDouble(Fares[2]);
                UPTO_3AXLE=Double.parseDouble(Fares[3]);
                UPTO_4TO6AXLE=Double.parseDouble(Fares[4]);
                HCM_EME=Double.parseDouble(Fares[5]);
                MORE_7AXLE=Double.parseDouble(Fares[6]);
           
        }
    }
    
    
    
   public class _TOLLPARAMS
    {
       //public String AllInfo="";
       public int TOLLID;
       public String TollName="";
       public TollLoc loc;//=new TollLoc(0.0,0.0);
       //public String Info="";
       //public TollFare Fare;//=new ;
   
             
   }

   public  _TOLLPARAMS TPARAMS;// = new _TOLLPARAMS(); 
           public TOLLPARAMS(String allinfo)
           {
               TPARAMS = new _TOLLPARAMS();
               String[]parts=allinfo.split("\\|");
               //TPARAMS.AllInfo=allinfo;
               TPARAMS.TOLLID=Integer.parseInt(parts[0]);
               TPARAMS.TollName=parts[1];
               TPARAMS.loc=new TollLoc(parts[2]);
              // TPARAMS.Fare=new TollFare(parts[3]);
              // TPARAMS.Info=parts[4];
           }
    
}
