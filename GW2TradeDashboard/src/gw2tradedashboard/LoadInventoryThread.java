/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gw2tradedashboard;

import GW2Objects.GW2Inventory;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author Philipp
 */
public class LoadInventoryThread extends Thread {

    private final SimpleBooleanProperty done;
    private final String character;
    private GW2Inventory inventory;

    public LoadInventoryThread(String character) {
        super();
        done = new SimpleBooleanProperty(false);
        this.character = character;
    }

    @Override
    public void run() {
        try {
            inventory = new GW2Inventory(character);
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(LoadInventoryThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        done.set(true);
        try {
            MainControllerWrapper.getController().fillInventory(inventory);
        } catch (IOException ex) {
            Logger.getLogger(LoadInventoryThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public SimpleBooleanProperty doneProperty(){
        return done;
    }
    
    public GW2Inventory getInventory(){
        return inventory;
    }
}
