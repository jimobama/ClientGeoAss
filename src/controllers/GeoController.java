/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.IController;
import java.util.ArrayList;
import models.GeoModel;
import views.GeoView;

/**
 *
 * @author Obaro
 */
public class GeoController implements IController {

    //private fields
    private GeoModel model;
    private GeoView view;
 private  Thread thread =null;
    //parameterised controllers
    public GeoController(GeoModel aModel, GeoView aView) {
        this.model = aModel;
        this.view = aView;

        //add an observer
        this.model.attach(this);
        this.view.attach(this);
        this.view.connect(this.model.getCurrentAddress());

    }

    @Override
    public void exec() {

        this.view.exec();
    }

    public void xhsAsyncSearch(String text,int n) {

        if(thread !=null)
        {
            this.terminiteAllPreviousThread();
        }
      thread = new Thread() {

            public void run() {
                model.makeSearch(text);
            }
        };

        this.handleThread(thread);

    }

    private void handleThread(Thread t) {
        try {
            t.join();
            t.start();
        } catch (Exception err) {
       
        }
    }

    public void xhsUpdateView(ArrayList<GeoModel.Address> addresses) {
         this.view.Update(addresses);        
        }

    private void terminiteAllPreviousThread() {
      if(this.thread.isAlive())
      {
          Thread.getAllStackTraces().keySet().stream().filter((t) -> (t.getState()==Thread.State.RUNNABLE)).forEach((t) -> {
              t.interrupt();
          }); 

      }
    }

    public void xhsUpdateGeoPosition(String address,String lat, String lng) {
        this.view.updateGeoPosition(address,lat,lng);
        
       }

}
