/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GW2Api.GW2Objects;

import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class GW2Inventory {
    private List<GW2InventoryItem> items;
    private JsonObject srcObject;
    
    public GW2Inventory(JsonObject srcObject){
        this.srcObject = srcObject;
        items = new ArrayList<>();
    }
}
