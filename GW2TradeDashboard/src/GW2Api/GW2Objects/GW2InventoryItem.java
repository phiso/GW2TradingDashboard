/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GW2Api.GW2Objects;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.ClientProtocolException;

/**
 *
 * @author Philipp
 */
public class GW2InventoryItem {

    private Integer id;
    private Integer count;
    private Integer charges;
    private String binding;
    private String boundTo;
    private Integer skin;
    private List<Integer> upgrades;
    private JsonObject srcObject;
    private GW2Item correspondingItem;

    public GW2InventoryItem(JsonObject srcObject) {
        this.srcObject = srcObject;
        upgrades = new ArrayList<>();
        boundTo = "";
        if (srcObject != null){
            parseJson();
        }        
    }

    private void parseJson() {
        id = srcObject.get("id").getAsInt();
        count = srcObject.get("count").getAsInt();
        charges = srcObject.get("charges") != null ? srcObject.get("charges").getAsInt() : 0;
        binding = srcObject.get("binding") != null ? srcObject.get("binding").getAsString() : "";
        skin = srcObject.get("skin") != null ? srcObject.get("skin").getAsInt() : -1;
        if (!binding.equals("") && !binding.equals("Account")) {
            boundTo = srcObject.get("bound_to").getAsString();
        }
        if (srcObject.get("upgrades") != null) {
            for (JsonElement elem : srcObject.getAsJsonArray("upgrades")) {
                upgrades.add(elem.getAsInt());
            }
        }
    }

    public Integer getId() {
        return id;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getCharges() {
        return charges;
    }

    public String getBinding() {
        return binding;
    }

    public String getBoundTo() {
        return boundTo;
    }

    public Integer getSkin() {
        return skin;
    }

    public List<Integer> getUpgrades() {
        return upgrades;
    }

    public JsonObject getSrcObject() {
        return srcObject;
    }
    
    public void setCorrItem(JsonObject obj) throws IOException, ClientProtocolException, URISyntaxException{
        this.correspondingItem = new GW2Item(obj);
    }
    
    public GW2Item getItem(){
        return correspondingItem;
    }
}
