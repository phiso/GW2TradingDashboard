/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GW2Api.Threads;

import GW2Api.GW2Api;
import GW2Api.GW2Objects.GW2TradeItem;
import GW2Api.LogMngr;
import com.google.gson.JsonObject;
import gw2tradedashboard.GWTSettings;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import org.apache.http.client.ClientProtocolException;

/**
 *
 * @author Philipp
 */
public class GW2ItemPriceListener extends Thread {
    
    private SimpleIntegerProperty buyPriceProperty;
    private SimpleIntegerProperty sellPriceProperty;
    private SimpleIntegerProperty refreshCounter;
    private SimpleIntegerProperty buyQuantityProperty;
    private SimpleIntegerProperty sellQuantityProperty;
    
    private final Integer itemId;
    private Integer delay;
    private Boolean alive;
    private Boolean running;
    
    public GW2ItemPriceListener(Integer itemId, Integer delay) {
        super(itemId.toString());
        this.itemId = itemId;
        this.delay = delay;
        alive = true;
        running = true;
        buyPriceProperty = new SimpleIntegerProperty(0);
        sellPriceProperty = new SimpleIntegerProperty(0);
        refreshCounter = new SimpleIntegerProperty(0);
        buyQuantityProperty = new SimpleIntegerProperty(0);
        sellQuantityProperty = new SimpleIntegerProperty(0);
    }
    
    @Override
    public void run() {
        GW2Api api = null;
        try {
            api = new GW2Api(GWTSettings.getSetting("API.api_key"));
        } catch (ClientProtocolException ex) {
            LogMngr.logWarning("Error loading API!", ex);            
        }
        while (true) {
            while (alive && running && api != null) {
                LogMngr.log(Level.FINEST, "Refreshing prices on Item " + itemId);
                try {
                    JsonObject obj = api.getTradeitem(itemId.toString());
                    GW2TradeItem tItem = new GW2TradeItem(obj);
                    buyPriceProperty.set(tItem.getRawBuyprice());
                    sellPriceProperty.set(tItem.getRawSellPrice());
                    buyQuantityProperty.set(tItem.getBuyQuantity());
                    sellQuantityProperty.set(tItem.getSellQuantity());
                    refreshCounter.set(refreshCounter.get() + 1);
                } catch (URISyntaxException | IOException ex) {
                    LogMngr.logError("Error getting Tradeitem for ID: " + itemId, ex);
                }
            }
            if (!alive) {
                Thread.currentThread().interrupt();
                return;
            } else if (api == null) {
                LogMngr.logError("Thread stopped due to missing API!");
                return;
            }
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                LogMngr.logError("Error pausing Thread on item " + itemId.toString(), ex);
            }
        }
    }
    
    public SimpleIntegerProperty getBuyPriceProperty() {
        return buyPriceProperty;
    }
    
    public SimpleIntegerProperty getSellpriceProperty() {
        return sellPriceProperty;
    }
    
    public int getSellPrice() {
        return sellPriceProperty.get();
    }
    
    public int getBuyPrice() {
        return buyPriceProperty.get();
    }
    
    public Boolean isRunning() {
        return alive;
    }
    
    public void stopExecution() {
        alive = false;
    }
    
    public void resumeExecution() {
        alive = true;
    }
    
    public void kill() {
        alive = false;
        running = false;
    }
    
    public void setDelay(Integer delay){
        this.delay = delay;
    }
}
