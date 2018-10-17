/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GW2Objects;

import apiConnector.GW2ApiConnector;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 *
 * @author Philipp
 */
public class GW2Item {

    private String name;
    private String id;
    private String description;
    private String type;
    private String level;
    private String rarity;
    private String vendorValue;
    private String iconPath;
    private Boolean accountBound = false;
    private Boolean noSell = false;
    private Integer charges = 0;

    public void GW2Item(String id) throws URISyntaxException, IOException {
        this.id = id;
        Gson gson = new Gson();
        GW2ApiConnector.getInstance();
        JsonObject obj = GW2ApiConnector.getItem(id);
        name = obj.get("name").getAsString();
        description = obj.get("description").getAsString();
        type = obj.get("type").getAsString();
        level = obj.get("level").getAsString();
        rarity = obj.get("rarity").getAsString();
        vendorValue = obj.get("vendor_value").getAsString();
        iconPath = obj.get("icon").getAsString();
        JsonArray flags = obj.getAsJsonArray("flags");
        for (JsonElement elem : flags) {
            switch (elem.getAsString()) {
                case "NoSell":
                    noSell = true;
                    break;
                case "SoulBindOnUse":
                    break;
            }
        }
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getLevel() {
        return level;
    }

    public String getRarity() {
        return rarity;
    }

    public String getVendorValue() {
        return vendorValue;
    }

    public String getIconPath() {
        return iconPath;
    }

    public Boolean getAccountBound() {
        return accountBound;
    }

    public Boolean getNoSell() {
        return noSell;
    }

    public Integer getCharges() {
        return charges;
    }
}
