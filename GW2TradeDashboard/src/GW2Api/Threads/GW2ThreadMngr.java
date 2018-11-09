/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GW2Api.Threads;

import gw2tradedashboard.GWTSettings;
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
    
    public static GW2ItemPriceListener getThread(Integer itemId){
        return threadList.get(itemId);
    }
    
    public static void addListenerOnItem(Integer itemId){
        GW2ItemPriceListener newListener = new GW2ItemPriceListener(itemId, Integer.parseInt(GWTSettings.getSetting("TRADING.refresh_rate")));
        threadList.put(itemId, newListener);
        newListener.start();
    }
}
