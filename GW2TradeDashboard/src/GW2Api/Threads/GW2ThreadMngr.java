/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GW2Api.Threads;

import gw2tradedashboard.GWTSettings;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Philipp
 */
public class GW2ThreadMngr {
    private static GW2ThreadMngr instance;
    private static Map<Integer, GW2ItemPriceListener> threadList;
    
    private GW2ThreadMngr(){
        super();
        threadList = new HashMap<>();
    }
    
    public static GW2ThreadMngr getInstance(){
        if (GW2ThreadMngr.instance == null){
            GW2ThreadMngr.instance = new GW2ThreadMngr();
        }
        return GW2ThreadMngr.instance;
    }
    
    public static GW2ItemPriceListener getListener(Integer itemId){
        return threadList.get(itemId);
    }
    
    public static GW2ItemPriceListener addListenerOnItem(Integer itemId) throws URISyntaxException, IOException{
        GW2ItemPriceListener newListener = new GW2ItemPriceListener(itemId, Integer.parseInt(GWTSettings.getSetting("TRADING.refresh_rate")));
        threadList.put(itemId, newListener);
        newListener.start();
        return newListener;
    }
    
    public static GW2ItemPriceListener addOrResumeListener(Integer itemId) throws URISyntaxException, IOException{
        GW2ItemPriceListener listener = threadList.getOrDefault(itemId, null);
        if (listener == null){
           listener = addListenerOnItem(itemId);
        } else {
            listener.resumeExecution();
        }
        return listener;
    }
    
    public static void stopListening(Integer itemId){
        GW2ItemPriceListener listener = threadList.getOrDefault(itemId, null);
        if (listener != null){
            listener.stopExecution();
        }
    }
    
    public static void killListener(Integer itemId){
        GW2ItemPriceListener listener = threadList.getOrDefault(itemId, null);
        if (listener != null){
            listener.kill();
        }
    }
    
    public static void changeDelay(Integer delay){
        threadList.keySet().forEach((it) -> {
            threadList.get(it).setDelay(delay);
        });
    }
    
    public static void changeDelay(Integer itemId, Integer delay){
        threadList.get(itemId).setDelay(delay);
    }
    
    public static void killAllListeners(){
        threadList.keySet().forEach((it) -> {
            threadList.get(it).kill();
        });
    }
}
