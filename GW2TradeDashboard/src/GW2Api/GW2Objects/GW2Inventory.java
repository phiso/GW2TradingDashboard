/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GW2Api.GW2Objects;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import GW2Api.LogMngr;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author Philipp
 */
public class GW2Inventory {

    private List<GW2InventoryItem> items;
    private JsonObject srcObject;

    public GW2Inventory(JsonObject srcObject) {
        this.srcObject = srcObject;
        items = new ArrayList<>();
        if (srcObject != null) {
            parseJson();
        }
    }

    private void parseJson() {
        JsonArray arr = srcObject.getAsJsonArray("bags");
        for (JsonElement elem : arr) {
            if (elem != null) {
                try {
                    JsonArray items = elem.getAsJsonObject().getAsJsonArray("inventory");
                    for (JsonElement jItem : items) {
                        if (jItem != null) {
                            try {
                                GW2InventoryItem item = new GW2InventoryItem(jItem.getAsJsonObject());
                                this.items.add(item);
                            } catch (IllegalStateException ex) {
                                LogMngr.log(Level.FINE, "Found empty Inventory slot.");
                            }
                        }
                    }
                } catch (IllegalStateException ex) {
                    LogMngr.log(Level.FINE, "Found an empty bag slot.");
                }
            }
        }
    }

    public GW2InventoryItem getItem(int index) {
        return items.get(index);
    }

    public List<GW2InventoryItem> getAllItems() {
        return items;
    }

    public int getItemCount() {
        return items.size();
    }
}
