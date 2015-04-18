/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import controllers.GeoController;
import controllers.IController;
import helps.ISubject;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Obaro
 */
public class GeoModel implements ISubject{

    private GeoController controller;
    @Override
    public void attach(IController controller) {
       this.controller =(GeoController)controller;    
      
       
    }

    public String getCurrentAddress()
    {
     String json= GeoModel.getCurrentLocation();
     JSONParser parser= new JSONParser();
     
     try
     {
        // Console.WriteLn(json);
      JSONObject response=(JSONObject)parser.parse(json);
      
      
      if(!response.isEmpty())
      {
        
          JSONObject content =(JSONObject)response.get("content");
          
          StringBuilder builder = new StringBuilder();
          
          String city = (String)content.get("city");
          String country =(String)content.get("country");
          String ip =(String)content.get("ip");
          String lat =(String)content.get("lat");
          String lng =(String)content.get("lng");
          builder.append((city==null || city=="null")?"":city);
          builder.append(",");
          builder.append((country==null || country =="null")?"":country);
          
          this.controller.xhsAsyncSearch(builder.toString(), 0);
          this.controller.xhsUpdateGeoPosition(builder.toString(),lat,lng);
         // Console.WriteLn("IP "+ip);
          return ip;      
      
      }     
     
     }catch(Exception err)
     {
        err.printStackTrace();
         
     }
     
     return null;
    }
    
   private  ArrayList<Address> search(String places)
    {
        //parser rhe return JSON
        ArrayList<Address> addresses= new ArrayList<Address>();
       JSONParser parser= new JSONParser();       
       
        String json = this.searh(places);
        
        try
        {
              JSONObject response  =(JSONObject) parser.parse(json);
              
              JSONObject contentList = (JSONObject)response.get("content");
              
              for(int i=0; i < contentList.size(); i++)
              {
                     JSONObject context =(JSONObject) contentList.get(String.valueOf(i));
                     
                     JSONObject postage =(JSONObject)context.get("postage");
                     String address=(String)context.get("address");
                     JSONObject location =(JSONObject)context.get("location");
                     
                    Address addr; 
                  addr = new  Address();
                  
                  addr.addr = address;
                  addr.postcode= postage.get("postcode").toString();
                  addr.town =postage.get("town").toString();
                  try{
                  addr.lat= Double.parseDouble(location.get("lat").toString());
                  addr.lng=Double.parseDouble(location.get("lng").toString());
                  }catch(NullPointerException err)
                  {
                      err.printStackTrace();
                  }
                  addresses.add(addr);
                  
                     
              }
        
         
        
        
        }catch(Exception err)
        {
            err.printStackTrace();
        }
        
        
        
        
        return addresses;
        
    }

    private static String getCurrentLocation() {
        obaro.webservices.Geo_Service service = new obaro.webservices.Geo_Service();
        obaro.webservices.Geo port = service.getGeoPort();
        return port.getCurrentLocation();
    }

    public synchronized void makeSearch(String text) {
   
       ArrayList<Address> addresses =   this.search(text);
       if(addresses !=null)
            this.controller.xhsUpdateView(addresses);
       
    }
    

    
   public static class Address
   {
       public double lat;
       public double lng;
       public String town;
       public String Country;
       public  String postcode;
       public String addr;
   }

    private  String searh(java.lang.String location) {
        obaro.webservices.Geo_Service service = new obaro.webservices.Geo_Service();
        obaro.webservices.Geo port = service.getGeoPort();
        return port.searh(location);
    }

}
