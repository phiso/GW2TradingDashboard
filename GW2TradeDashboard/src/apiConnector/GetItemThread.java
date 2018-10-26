/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apiConnector;

import GW2Objects.GW2InvItem;
import GW2Objects.GW2Item;
import gw2tradedashboard.ItemInfoController;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author Philipp
 */
public class GetItemThread extends Thread {

    private String itemId;
    private GW2InvItem item;
    //private SimpleObjectProperty<GW2Item> itemProperty;
    private ItemInfoController controller;

    public GetItemThread(String id, ItemInfoController controller) throws URISyntaxException, IOException {
        super();
        itemId = id;
        //itemProperty = new SimpleObjectProperty<>();
        GW2ApiConnector.getInstance();
        this.controller = controller;
    }

    @Override
    public void run() {
        try {
            item = new GW2InvItem(itemId, 0, 0);
            //itemProperty.set(item);
            controller.setItem(item);
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(GetItemThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public GW2Item getItem() {
        return item;
    }

    /*public SimpleObjectProperty<GW2Item> itemProperty(){
        return itemProperty;
    }*/
    public void setController(ItemInfoController controller) {
        this.controller = controller;
    }
}
