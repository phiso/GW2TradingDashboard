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
    private final String itemId;
    private Boolean continuos;
    private SimpleIntegerProperty aliveCounter;
    private Integer delay;

    public GW2ApiPriceListener(String itemId, int delay) {
        super();
        aliveCounter = new SimpleIntegerProperty(0);
        this.itemId = itemId;
        sellPriceProperty = new SimpleIntegerProperty(0);
        buyPriceProperty = new SimpleIntegerProperty(0);
        continuos = true;
        this.delay = delay;
    }

    public GW2ApiPriceListener(String itemId) {
        this(itemId, 10000);
    }

    @Override
    public void run() {
        System.out.println("listening... (item id: " + itemId + ")");
        while (continuos) {
            System.out.println("Refreshing prices on item " + itemId);
            try {
                JsonObject obj = GW2ApiConnector.getPrice(itemId);
                if (obj != null) {
                    Integer buyPrice = obj.getAsJsonObject("buys").get("unit_price").getAsInt();
                    Integer sellPrice = obj.getAsJsonObject("sells").get("unit_price").getAsInt();
                    sellPriceProperty.set(sellPrice);
                    buyPriceProperty.set(buyPrice);
                    aliveCounter.set(aliveCounter.get() + 1);
                    Thread.sleep(getDelay());
                }
            } catch (URISyntaxException | IOException | InterruptedException ex) {
                Logger.getLogger(GW2ApiPriceListener.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public SimpleIntegerProperty sellPriceProperty() {
        return sellPriceProperty;
    }

    public SimpleIntegerProperty buyPriceProperty() {
        return buyPriceProperty;
    }

    public Integer getSellPrice() {
        return sellPriceProperty.get();
    }

    public Integer getBuyPrice() {
        return buyPriceProperty.get();
    }

    public SimpleIntegerProperty getAliveCounterProperty() {
        return aliveCounter;
    }

    public void stopThread() {
        continuos = false;
    }
        
    public void resumeThread(){
        continuos = true;
    }

    public String getItemId() {
        return itemId;
    }

    /**
     * @return the delay
     */
    public Integer getDelay() {
        return delay;
    }

    /**
     * @param delay the delay to set
     */
    public void setDelay(Integer delay) {
        this.delay = delay;
    }
}
