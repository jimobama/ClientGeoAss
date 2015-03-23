/*

 */
package geoclient;
import controllers.GeoController;
import static helps.Machine.getIP;

import  models.GeoModel;
import views.GeoView;


/**
 *
 * @author Obaro
 */
public class GeoClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  {
       
     
     
        GeoController controller = new GeoController(new GeoModel(),new GeoView("Location TcpIp: "+getIP()));
        controller.exec();
    }
    
}
