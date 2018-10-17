/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apiConnector;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Philipp
 */
public class GW2ApiPriceListener extends Thread {    

    private SimpleIntegerProperty sellPriceProperty;
    private SimpleIntegerProperty buyPriceProperty;
    private String itemId;
    private Boolean continuos;
    private SimpleIntegerProperty aliveCounter;
    
    public GW2ApiPriceListener(String itemId){
        super();
        aliveCounter = new SimpleIntegerProperty(0);
        this.itemId = itemId;
        sellPriceProperty = new SimpleIntegerProperty(0);
        buyPriceProperty = new SimpleIntegerProperty(0);
        continuos = true;
    }
    
    @Override
    public void run(){
        while(continuos){
            try {
                JsonObject obj = GW2ApiConnector.getPrice(itemId);
                if (obj != null){
                    Integer buyPrice = obj.getAsJsonObject("buys").get("unit_price").getAsInt();
                    Integer sellPrice = obj.getAsJsonObject("sells").get("unit_price").getAsInt();
                    sellPriceProperty.set(sellPrice);
                    buyPriceProperty.set(buyPrice);
                    aliveCounter.set(aliveCounter.get()+1);
                    Thread.sleep(10000);
                }
            } catch (URISyntaxException | IOException | InterruptedException ex) {
                Logger.getLogger(GW2ApiPriceListener.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    public SimpleIntegerProperty getSellPriceProperty(){
        return sellPriceProperty;
    }
    
    public SimpleIntegerProperty getBuyPriceProperty(){
        return buyPriceProperty;
    }
    
    public Integer getSellPrice(){
        return sellPriceProperty.get();
    }
    
    public Integer getBuyPrice(){
        return buyPriceProperty.get();
    }
    
    public SimpleIntegerProperty getAliveCounterProperty(){
        return aliveCounter;
    }
    
    public void stopThread(){
        continuos = false;
    }
    public String getItemId(){
        return itemId;
    }
}
