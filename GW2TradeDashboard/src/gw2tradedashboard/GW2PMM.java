/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gw2tradedashboard;

import apiConnector.GW2ApiPriceListener;
import java.util.ArrayList;

/**
 *
 * @author Philipp
 * PriceMonitoringManager
 */
public class GW2PMM {
    private static GW2PMM instance;
    private static ArrayList<GW2ApiPriceListener> listeners;
    
    private GW2PMM(){
        listeners = new ArrayList<>();
    }
    
    public static GW2PMM getInstance(){
        if (GW2PMM.instance == null){
            GW2PMM.instance = new GW2PMM();
        }
        return GW2PMM.instance;
    }
    
    public static GW2ApiPriceListener addListener(String itemId){
        GW2ApiPriceListener listener = new GW2ApiPriceListener(itemId);
        listeners.add(listener);        
        return listener;
    }
    
    public static GW2ApiPriceListener getListener(int index){
        return listeners.get(index);
    }
}
