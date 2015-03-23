/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helps;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 *
 * @author Obaro
 */
public class Machine {
    
  private static InetAddress inet;
  
  public static String getIP()
  {
      
    String ip = (Machine.getLocalAddress()!=null)?Machine.getLocalAddress().getHostAddress():"";    
    return ip;
  }
  
  
  

private static InetAddress getLocalAddress(){
        try {
            Enumeration<NetworkInterface> b = NetworkInterface.getNetworkInterfaces();
            while( b.hasMoreElements()){
                for ( InterfaceAddress f : b.nextElement().getInterfaceAddresses())
                {
                    if ( f.getAddress().isSiteLocalAddress())
                        return f.getAddress();
                    InetAddress ip =f.getAddress();
                    
                    //Console.WriteLn("Ip :"+ ip.toString());
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }



}
