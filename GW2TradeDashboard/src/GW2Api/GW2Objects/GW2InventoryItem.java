/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GW2Api.GW2Objects;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;

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
        charges = srcObject.get("charges").getAsInt();
        binding = srcObject.get("binding") != null ? srcObject.get("binding").getAsString() : "";
        skin = srcObject.get("skin") != null ? srcObject.get("skin").getAsInt() : -1;
        if (binding != "" && binding != "Account") {
            boundTo = srcObject.get("boundTo").getAsString();
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
    
    
}
